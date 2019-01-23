/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.google.gson.GsonBuilder
 */
package de.sbe.ldc.persistence.sync;

import com.google.gson.GsonBuilder;
import de.sbe.ldc.domain.Contact;
import de.sbe.ldc.domain.Host;
import de.sbe.ldc.domain.UserImpl;
import de.sbe.ldc.domain.repository.AbstractBeanRepository;
import de.sbe.ldc.domain.repository.HostRepository;
import de.sbe.ldc.domain.repository.RepositoryContext;
import de.sbe.ldc.domain.repository.SsmActionRepository;
import de.sbe.ldc.domain.repository.SsmEntryRepository;
import de.sbe.ldc.domain.rpc.Impersonator;
import de.sbe.ldc.domain.rpc.RpcObject;
import de.sbe.ldc.domain.ssm.SsmAction;
import de.sbe.ldc.domain.ssm.SsmEntry;
import de.sbe.ldc.persistence.morpher.Encoder;
import de.sbe.ldc.persistence.morpher.deserializer.EnumDeserializer;
import de.sbe.ldc.persistence.morpher.deserializer.HostDeserializer;
import de.sbe.ldc.persistence.morpher.deserializer.ImpersonatorDeserializer;
import de.sbe.ldc.persistence.morpher.serializer.ImpersonatorSerializer;
import de.sbe.ldc.persistence.protocol.ActionStatusLogger;
import de.sbe.ldc.persistence.protocol.Command;
import de.sbe.ldc.persistence.protocol.Request;
import de.sbe.ldc.persistence.sync.BeanSerializer;
import de.sbe.ldc.persistence.sync.DataLoader;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class SsmSync {
    public static final String MEMBER_OF = "member_of";
    public static final String OWNER = "default.owner";
    public static final String REQUEST_CREATE_OBJECT = "create_object";
    public static final String REQUEST_LIST_OBJECT = "list_object";
    public static final String REQUEST_UPDATE_OBJECT = "update_object";
    public static final String TYPE_SSM_ACTION = "ssm_action";
    public static final String TYPE_SSM_ENTRY = "ssm_entry";

    public static GsonBuilder buildSsmEntryDeserializer(AbstractBeanRepository<SsmEntry, Long> _repo) {
        GsonBuilder builder = DataLoader.buildDefaultDeserializer();
        builder.registerTypeAdapter(Host.class, (Object)new HostDeserializer(_repo.getContext().getHosts()));
        builder.registerTypeAdapter(Impersonator.class, (Object)new ImpersonatorDeserializer(_repo.getContext()));
        builder.registerTypeAdapter(SsmEntry.Category.class, (Object)new EnumDeserializer());
        builder.registerTypeAdapter(SsmEntry.Priority.class, (Object)new EnumDeserializer());
        builder.registerTypeAdapter(SsmEntry.Severity.class, (Object)new EnumDeserializer());
        builder.registerTypeAdapter(SsmEntry.State.class, (Object)new EnumDeserializer());
        builder.registerTypeAdapter(SsmEntry.Subcategory.class, (Object)new EnumDeserializer());
        return builder;
    }

    public static SsmEntryRepository load(Host _host) {
        final SsmEntryRepository ser = new SsmEntryRepository();
        Request requestEntry = new Request(Command.RPC);
        requestEntry.putEncodedData("rpc_version", "1.0");
        requestEntry.putEncodedData("type", TYPE_SSM_ENTRY);
        requestEntry.putEncodedData("rpc_request", REQUEST_LIST_OBJECT);
        requestEntry.putEncodedData(OWNER, BeanSerializer.toArray(_host.getCn()));
        BeanSerializer.commit(requestEntry, new DataLoader.AbstractRpcProcessor<SsmEntry>((AbstractBeanRepository)ser){

            @Override
            protected GsonBuilder buildGsonBuilder() {
                return SsmSync.buildSsmEntryDeserializer(this.getRepo());
            }

            @Override
            public void setUp() {
                super.setUp();
                this.getRepo().clear();
            }

            @Override
            public void tearDown() {
                super.tearDown();
                for (SsmEntry se : ser.getBeans()) {
                    Request requestAction = new Request(Command.RPC);
                    requestAction.putEncodedData("rpc_version", "1.0");
                    requestAction.putEncodedData("type", SsmSync.TYPE_SSM_ACTION);
                    requestAction.putEncodedData("rpc_request", SsmSync.REQUEST_LIST_OBJECT);
                    requestAction.putEncodedData(SsmSync.MEMBER_OF, BeanSerializer.toArray(se.getId()));
                    BeanSerializer.commit(requestAction, new DataLoader.AbstractRpcProcessor<SsmAction>((AbstractBeanRepository)se.getActions()){

                        @Override
                        protected GsonBuilder buildGsonBuilder() {
                            GsonBuilder builder = DataLoader.buildDefaultDeserializer();
                            builder.registerTypeAdapter(Impersonator.class, (Object)new ImpersonatorDeserializer(this.getRepo().getContext()));
                            return builder;
                        }
                    });
                }
            }

        });
        return ser;
    }

    public static SsmEntryRepository load(SsmEntryRepository _repo) {
        Request requestEntry = new Request(Command.RPC);
        requestEntry.putEncodedData("rpc_version", "1.0");
        requestEntry.putEncodedData("type", TYPE_SSM_ENTRY);
        requestEntry.putEncodedData("rpc_request", REQUEST_LIST_OBJECT);
        BeanSerializer.commit(requestEntry, new DataLoader.AbstractRpcProcessor<SsmEntry>((AbstractBeanRepository)_repo){

            @Override
            protected GsonBuilder buildGsonBuilder() {
                return SsmSync.buildSsmEntryDeserializer(this.getRepo());
            }

            @Override
            public void setUp() {
                super.setUp();
                this.getRepo().clear();
            }
        });
        return _repo;
    }

    public static boolean sync(SsmAction _action) {
        if (_action.getId() == null) {
            Request request = new Request(Command.RPC);
            request.putEncodedData("rpc_version", "1.0");
            request.putEncodedData("type", TYPE_SSM_ACTION);
            request.putEncodedData("rpc_request", REQUEST_CREATE_OBJECT);
            request.putEncodedData(BeanSerializer.getAllWritableProperties(_action));
            request.putEncodedData(MEMBER_OF, BeanSerializer.toArray(_action.getEntry().getId()));
            request.setEncoder(new SsmEncoder());
            BeanSerializer.commit(request, new SsmProcessor(_action));
        }
        return true;
    }

    public static boolean sync(SsmEntry _entry) {
        Request request = new Request(Command.RPC);
        request.putEncodedData("rpc_version", "1.0");
        request.putEncodedData("type", TYPE_SSM_ENTRY);
        if (_entry.getId() == null) {
            request.putEncodedData("rpc_request", REQUEST_CREATE_OBJECT);
        } else {
            request.putEncodedData("rpc_request", REQUEST_UPDATE_OBJECT);
        }
        if (_entry.getId() != null) {
            request.putEncodedData("id", BeanSerializer.toArray(_entry.getId()));
        }
        request.putEncodedData(BeanSerializer.getAllWritableProperties(_entry));
        request.setEncoder(new SsmEncoder());
        BeanSerializer.commit(request, new SsmProcessor(_entry));
        for (SsmAction action : _entry.getActions().getBeans(new Comparator<SsmAction>(){

            @Override
            public int compare(SsmAction _o1, SsmAction _o2) {
                return Long.valueOf(_o1.getTsCreated()).compareTo(_o2.getTsCreated());
            }
        })) {
            SsmSync.sync(action);
        }
        return true;
    }

    private static class SsmProcessor
    extends ActionStatusLogger {
        private final RpcObject object;

        SsmProcessor(RpcObject _object) {
            this.object = _object;
        }

        @Override
        protected void doTearDown() {
            super.doTearDown();
            this.getObject().setId(this.getId());
        }

        public RpcObject getObject() {
            return this.object;
        }
    }

    private static class SsmEncoder
    extends Encoder {
        SsmEncoder() {
        }

        @Override
        protected GsonBuilder buildGsonBuilder() {
            GsonBuilder builder = super.buildGsonBuilder();
            builder.registerTypeAdapter(Contact.class, (Object)new ImpersonatorSerializer());
            builder.registerTypeAdapter(UserImpl.class, (Object)new ImpersonatorSerializer());
            return builder;
        }
    }

}

