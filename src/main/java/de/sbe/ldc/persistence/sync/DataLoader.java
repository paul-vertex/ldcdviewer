/*
 * Decompiled with CFR 0.139.
 *
 * Could not load the following classes:
 *  com.google.gson.FieldNamingStrategy
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.InstanceCreator
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonParser
 *  com.google.gson.JsonPrimitive
 */
package de.sbe.ldc.persistence.sync;

import com.google.gson.CollectionDeserializer;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import de.sbe.ldc.domain.AbstractBean;
import de.sbe.ldc.domain.Clopen;
import de.sbe.ldc.domain.Contact;
import de.sbe.ldc.domain.Defaults;
import de.sbe.ldc.domain.DsbInfo;
import de.sbe.ldc.domain.Exam;
import de.sbe.ldc.domain.GenderType;
import de.sbe.ldc.domain.Group;
import de.sbe.ldc.domain.Host;
import de.sbe.ldc.domain.HostInfo;
import de.sbe.ldc.domain.Inventory;
import de.sbe.ldc.domain.InventoryMap;
import de.sbe.ldc.domain.JavaBean;
import de.sbe.ldc.domain.Option;
import de.sbe.ldc.domain.Pykota;
import de.sbe.ldc.domain.Quota;
import de.sbe.ldc.domain.Role;
import de.sbe.ldc.domain.Room;
import de.sbe.ldc.domain.RoomInfo;
import de.sbe.ldc.domain.RoomLayout;
import de.sbe.ldc.domain.Switch;
import de.sbe.ldc.domain.User;
import de.sbe.ldc.domain.UserList;
import de.sbe.ldc.domain.ZarafaEntity;
import de.sbe.ldc.domain.repository.AbstractBeanRepository;
import de.sbe.ldc.domain.repository.ContactRepository;
import de.sbe.ldc.domain.repository.DsbInfoRepository;
import de.sbe.ldc.domain.repository.ExamRepository;
import de.sbe.ldc.domain.repository.GroupRepository;
import de.sbe.ldc.domain.repository.HostRepository;
import de.sbe.ldc.domain.repository.RepositoryContext;
import de.sbe.ldc.domain.repository.RoleRepository;
import de.sbe.ldc.domain.repository.RoomRepository;
import de.sbe.ldc.domain.repository.UserListRepository;
import de.sbe.ldc.domain.repository.UserRepository;
import de.sbe.ldc.domain.rpc.Impersonator;
import de.sbe.ldc.domain.rpc.RpcObject;
import de.sbe.ldc.persistence.morpher.ReadableFilter;
import de.sbe.ldc.persistence.morpher.SerializableProperties;
import de.sbe.ldc.persistence.morpher.converter.FieldToStringConverter;
import de.sbe.ldc.persistence.morpher.deserializer.ColumnDeserializer;
import de.sbe.ldc.persistence.morpher.deserializer.DateDeserializer;
import de.sbe.ldc.persistence.morpher.deserializer.DateFromLongDeserializer;
import de.sbe.ldc.persistence.morpher.deserializer.EnumDeserializer;
import de.sbe.ldc.persistence.morpher.deserializer.GroupDeserializer;
import de.sbe.ldc.persistence.morpher.deserializer.HostDeserializer;
import de.sbe.ldc.persistence.morpher.deserializer.QuotaDeserializer;
import de.sbe.ldc.persistence.morpher.deserializer.RepositoryInstanceCreator;
import de.sbe.ldc.persistence.morpher.deserializer.RoleDeserializer;
import de.sbe.ldc.persistence.morpher.deserializer.RoomLayoutDeserializer;
import de.sbe.ldc.persistence.morpher.deserializer.UserDeserializer;
import de.sbe.ldc.persistence.morpher.deserializer.ZarafaEntityDeserializer;
import de.sbe.ldc.persistence.net.CommunicationManager;
import de.sbe.ldc.persistence.net.Worker;
import de.sbe.ldc.persistence.net.WorkerValidator;
import de.sbe.ldc.persistence.protocol.Command;
import de.sbe.ldc.persistence.protocol.JsonProcessorAdapter;
import de.sbe.ldc.persistence.protocol.Processor;
import de.sbe.ldc.persistence.protocol.Request;
import de.sbe.utils.StringUtils;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;

public class DataLoader
        extends AbstractBean {
    private static final DataLoader instance = new DataLoader();
    public static final String PROPERTYNAME_LOADED = "loaded";
    private static final long serialVersionUID = 1L;

    public static GsonBuilder buildContactDeserializer(RepositoryContext _context) {
        GsonBuilder builder = DataLoader.buildDefaultDeserializer();
        builder.registerTypeAdapter(GenderType.class, (Object) new EnumDeserializer());
        builder.registerTypeAdapter(Impersonator.class, (Object) new InstanceCreator<Impersonator>() {

            public Impersonator createInstance(Type _type) {
                return null;
            }
        });
        return builder;
    }

    public static GsonBuilder buildDefaultDeserializer() {
        GsonBuilder builder = new GsonBuilder();
        builder.setFieldNamingStrategy(new FieldNamingStrategy() {

            public String translateName(Field _f) {
                return SerializableProperties.getSerializedName(_f);
            }
        });
        builder.registerTypeAdapter(Collection.class, (Object) new CollectionDeserializer());
        builder.registerTypeAdapter(Date.class, (Object) new DateDeserializer());
        builder.registerTypeAdapter(List.class, (Object) new CollectionDeserializer());
        builder.registerTypeAdapter(Queue.class, (Object) new CollectionDeserializer());
        return builder;
    }

    public static GsonBuilder buildDsbInfoDeserializer(RepositoryContext _context) {
        GsonBuilder builder = DataLoader.buildDefaultDeserializer();
        builder.registerTypeAdapter(Group.class, (Object) new GroupDeserializer(_context.getGroups()));
        builder.registerTypeAdapter(User.class, (Object) new UserDeserializer(_context.getUsers()));
        return builder;
    }

    public static GsonBuilder buildExamDeserializer(RepositoryContext _context) {
        GsonBuilder builder = DataLoader.buildUserDeserializer(_context);
        builder.registerTypeAdapter(Switch.class, (Object) new EnumDeserializer());
        return builder;
    }

    public static GsonBuilder buildGroupDeserializer(RepositoryContext _context) {
        GsonBuilder builder = DataLoader.buildDefaultDeserializer();
        builder.registerTypeAdapter(Clopen.class, (Object) new EnumDeserializer());
        builder.registerTypeAdapter(Quota.class, (Object) new QuotaDeserializer());
        builder.registerTypeAdapter(User.class, (Object) new UserDeserializer(_context.getUsers()));
        return builder;
    }

    public static GsonBuilder buildHostDeserializer(RepositoryContext _context) {
        GsonBuilder builder = DataLoader.buildDefaultDeserializer();
        builder.registerTypeAdapter(Host.HostType.class, (Object) new EnumDeserializer());
        return builder;
    }

    public static GsonBuilder buildPhraseFilterDeserializer(RepositoryContext _context) {
        GsonBuilder builder = DataLoader.buildDefaultDeserializer();
        return builder;
    }

    public static GsonBuilder buildRoomDeserializer(RepositoryContext _context) {
        GsonBuilder builder = DataLoader.buildHostDeserializer(_context);
        builder.registerTypeAdapter(Host.class, (Object) new HostDeserializer(_context.getHosts()));
        builder.registerTypeAdapter(RoomLayout.class, (Object) new RoomLayoutDeserializer());
        return builder;
    }

    public static GsonBuilder buildSurfingDeserializer() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, (Object) new DateFromLongDeserializer());
        builder.registerTypeAdapter(User.class, (Object) new UserDeserializer(RepositoryContext.getDefaultUsers()));
        return builder;
    }

    public static GsonBuilder buildUrlFilterDeserializer(RepositoryContext _context) {
        GsonBuilder builder = DataLoader.buildDefaultDeserializer();
        builder.setFieldNamingStrategy(new FieldNamingStrategy() {

            public String translateName(Field _f) {
                return _f.getName().equals("sitesEnabled") ? "sites_enabled" : (_f.getName().equals("urlsEnabled") ? "urls_enabled" : _f.getName());
            }
        });
        return builder;
    }

    public static GsonBuilder buildUserDeserializer(RepositoryContext _context) {
        GsonBuilder builder = DataLoader.buildGroupDeserializer(_context);
        builder.registerTypeAdapter(GenderType.class, (Object) new EnumDeserializer());
        builder.registerTypeAdapter(Group.class, (Object) new GroupDeserializer(_context.getGroups()));
        builder.registerTypeAdapter(Option.class, (Object) new EnumDeserializer());
        builder.registerTypeAdapter(Pykota.PykotaLimitBy.class, (Object) new EnumDeserializer());
        builder.registerTypeAdapter(Quota.class, (Object) new QuotaDeserializer());
        builder.registerTypeAdapter(Role.class, (Object) new RoleDeserializer(_context.getRoles()));
        builder.registerTypeAdapter(ZarafaEntity.class, (Object) new ZarafaEntityDeserializer());
        return builder;
    }

    public static GsonBuilder buildUserListDeserializer(RepositoryContext _context) {
        GsonBuilder builder = DataLoader.buildDefaultDeserializer();
        builder.registerTypeAdapter(UserList.Column.class, (Object) new ColumnDeserializer());
        builder.registerTypeAdapter(UserList.Column.ColumnType.class, (Object) new EnumDeserializer());
        builder.registerTypeAdapter(UserList.FileType.class, (Object) new EnumDeserializer());
        builder.registerTypeAdapter(Option.class, (Object) new EnumDeserializer());
        builder.registerTypeAdapter(Quota.class, (Object) new QuotaDeserializer());
        builder.registerTypeAdapter(Role.class, (Object) new RoleDeserializer(_context.getRoles()));
        builder.registerTypeAdapter(ZarafaEntity.class, (Object) new ZarafaEntityDeserializer());
        return builder;
    }

    public static DataLoader getInstance() {
        return instance;
    }

    private DataLoader() {
    }

    public AbstractJsonProcessor<User, String> buildUserLoaderProcessor(final RepositoryContext _context) {
        return new AbstractJsonProcessor<User, String>((AbstractBeanRepository) _context.getUsers()) {

            @Override
            protected GsonBuilder buildGsonBuilder() {
                return DataLoader.buildUserDeserializer(_context);
            }

            @Override
            public String getDescription() {
                return "view.progress.user_list";
            }

            @Override
            public void tearDown() {
                for (User user : this.getRepo().getBeans()) {
                    List<Group> groups = user.getGroups();
                    user.setGroups(null);
                    user.setGroups(groups);
                    user.setSambaAcctFlags(user.getSambaAcctFlags());
                    user.setSambaPwdCanChange(user.getSambaPwdCanChange());
                    user.setSambaPwdMustChange(user.getSambaPwdMustChange());
                }
                super.tearDown();
            }
        };
    }

    private <B extends JavaBean, T> void load(Command _command, AbstractJsonProcessor<B, T> _processor, boolean _clear) {
        this.load(_command, null, _processor, _clear);
    }

    private <B extends JavaBean, T> void load(Command _command, String _filter, AbstractJsonProcessor<B, T> _processor, boolean _clear) {
        if (_clear) {
            _processor.getRepo().clear();
        }
        Request request = new Request(_command);
        request.putPlainData("attrs", StringUtils.convert(SerializableProperties.getSerializablePropertiesAsList(_processor.getRepo().getBeanClass(), new ReadableFilter()), new FieldToStringConverter()));
        if (_filter != null) {
            request.putPlainData(_filter);
        }
        new WorkerValidator().validate(CommunicationManager.getInstance().request(request, _processor));
    }

    private <B extends JavaBean, T> void load(String _type, AbstractJsonProcessor<B, T> _processor, boolean _clear) {
        if (_clear) {
            _processor.getRepo().clear();
        }
        Request request = new Request(Command.RPC);
        request.putEncodedData("rpc_version", "1.0");
        request.putEncodedData("rpc_request", "list_object");
        request.putEncodedData("type", _type);
        new WorkerValidator().validate(CommunicationManager.getInstance().request(request, _processor));
    }

    public void loadContacts() {
        this.loadContacts(RepositoryContext.getInstance(), true);
    }

    public void loadContacts(final RepositoryContext _context, boolean _clear) {
        this.load("contact", new AbstractRpcProcessor<Contact>((AbstractBeanRepository) _context.getContacts()) {

            @Override
            protected GsonBuilder buildGsonBuilder() {
                return DataLoader.buildContactDeserializer(_context);
            }

            @Override
            public String getDescription() {
                return "view.progress.contact_list";
            }
        }, _clear);
    }

    public void loadDefaults() {
        Request request = new Request(Command.STATE_DEFAULTS);
        new WorkerValidator().validate(CommunicationManager.getInstance().request(request, new DefaultsProcessor()));
    }

    public void loadDsbInfos() {
        this.loadDsbInfos(RepositoryContext.getInstance(), true);
    }

    public void loadDsbInfos(final RepositoryContext _context, boolean _clear) {
        this.load(Command.DSB_INFO, new AbstractJsonProcessor<DsbInfo, String>((AbstractBeanRepository) _context.getDsbInfos()) {

            @Override
            protected GsonBuilder buildGsonBuilder() {
                return DataLoader.buildDsbInfoDeserializer(_context);
            }

            @Override
            public String getDescription() {
                return "view.progress.dsb_info";
            }
        }, _clear);
    }

    public void loadExams() {
        this.loadExams(RepositoryContext.getInstance(), true);
    }

    public void loadExams(final RepositoryContext _context, boolean _clear) {
        this.load(Command.EXAM_LIST, new AbstractJsonProcessor<Exam, String>((AbstractBeanRepository) _context.getExams()) {

            @Override
            protected GsonBuilder buildGsonBuilder() {
                return DataLoader.buildExamDeserializer(_context);
            }

            @Override
            public String getDescription() {
                return "view.progress.exam_list";
            }

            @Override
            public void tearDown() {
                super.tearDown();
                for (Exam exam : this.getRepo().getBeans()) {
                    exam.revalidateId();
                }
            }
        }, _clear);
    }

    public void loadGroups() {
        this.loadGroups(RepositoryContext.getInstance(), true);
    }

    public void loadGroups(final RepositoryContext _context, boolean _clear) {
        this.load(Command.GROUP_LIST, "attrs=diskquota", new AbstractJsonProcessor<Group, String>((AbstractBeanRepository) _context.getGroups()) {

            @Override
            protected GsonBuilder buildGsonBuilder() {
                return DataLoader.buildGroupDeserializer(_context);
            }

            @Override
            public String getDescription() {
                return "view.progress.group_list";
            }
        }, _clear);
    }

    public void loadHosts() {
        this.loadHosts(RepositoryContext.getInstance(), true);
    }

    public void loadHosts(final RepositoryContext _context, boolean _clear) {
            this.load(Command.HOST_LIST, new AbstractJsonProcessor<Host, String>((AbstractBeanRepository) _context.getHosts()) {

                @Override
                protected GsonBuilder buildGsonBuilder() {
                    return DataLoader.buildHostDeserializer(_context);
                }

                @Override
                public String getDescription() {
                    return "view.progress.host_list";
                }
            }, _clear);
    }

    public void loadInventory() {
        this.loadInventory(RepositoryContext.getInstance());
    }

    public void loadInventory(final RepositoryContext _context) {
    }

    public void loadInventoryMap(final InventoryMap _imap) {
        GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        WorkerValidator validator = new WorkerValidator();
        Request request = new Request(Command.OBJECT_DEVICE_GET_INVENTORY_INFO);
        validator.validate(CommunicationManager.getInstance().request(request, new JsonProcessorAdapter() {

            @Override
            public void processJson(JsonObject _object) {
                InventoryMap imap = (InventoryMap) gson.fromJson((JsonElement) _object, InventoryMap.class);
                _imap.setColumns(imap.getColumns());
                _imap.setCollections(imap.getCollections());
            }
        }));
    }

    public void loadNeededContext() {
        this.loadNeededContext(RepositoryContext.getInstance());
    }

    public void loadNeededContext(RepositoryContext _context) {
        this.firePropertyChange(PROPERTYNAME_LOADED, true, false);
        this.loadDefaults();
        this.loadRoomContext(_context);
        this.loadUserContext(_context);
        this.loadContacts(_context, false);
        this.loadState();
        this.firePropertyChange(PROPERTYNAME_LOADED, false, true);
    }

    public void loadRoles() {
        this.loadRoles(RepositoryContext.getInstance(), true);
    }

    public void loadRoles(RepositoryContext _context, boolean _clear) {
        this.load(Command.ROLE_LIST, new AbstractJsonProcessor<Role, String>((AbstractBeanRepository) _context.getRoles()) {

            @Override
            protected GsonBuilder buildGsonBuilder() {
                return DataLoader.buildDefaultDeserializer();
            }

            @Override
            public String getDescription() {
                return "view.progress.role_list";
            }
        }, _clear);
    }

    public void loadRoomContext() {
        this.loadRoomContext(RepositoryContext.getInstance());
    }

    public void loadRoomContext(RepositoryContext _context) {
        _context.getHosts().clear();
        _context.getRooms().clear();
        this.loadHosts(_context, false);
        this.loadRooms(_context, false);
    }

    public void loadRooms() {
        this.loadRooms(RepositoryContext.getInstance(), true);
    }

    public void loadRooms(final RepositoryContext _context, boolean _clear) {
        this.load(Command.ROOM_LIST, new AbstractJsonProcessor<Room, String>((AbstractBeanRepository) _context.getRooms()) {

            @Override
            protected GsonBuilder buildGsonBuilder() {
                return DataLoader.buildRoomDeserializer(_context);
            }

            @Override
            public String getDescription() {
                return "view.progress.room_list";
            }

            @Override
            public void tearDown() {
                for (Room room : this.getRepo().getBeans()) {
                    List<Host> member = room.getMember();
                    room.setMember(null);
                    room.setMember(member);
                    room.getRoomInfo().setInput(Defaults.getInstance().getInput(room));
                    room.getRoomInfo().setInternet(Defaults.getInstance().getInternet(room));
                    room.getRoomInfo().setIntranet(Defaults.getInstance().getIntranet(room));
                    room.getRoomInfo().setPrinter(Defaults.getInstance().getPrinter(room));
                    room.getRoomInfo().setScreen(Defaults.getInstance().getScreen(room));
                    room.getRoomInfo().setSwap(Defaults.getInstance().getSwap(room));
                    room.getRoomInfo().setUsb(Defaults.getInstance().getUsb(room));
                    room.getRoomInfo().setWebfilter(Defaults.getInstance().getWebfilter(room));
                    for (Host host : room.getMember()) {
                        host.getHostInfo().setInput(Defaults.getInstance().getInput(host));
                        host.getHostInfo().setInternet(Defaults.getInstance().getInternet(host));
                        host.getHostInfo().setIntranet(Defaults.getInstance().getIntranet(host));
                        host.getHostInfo().setPrinter(Defaults.getInstance().getPrinter(host));
                        host.getHostInfo().setScreen(Defaults.getInstance().getScreen(host));
                        host.getHostInfo().setSwap(Defaults.getInstance().getSwap(host));
                        host.getHostInfo().setUsb(Defaults.getInstance().getUsb(host));
                        host.getHostInfo().setWebfilter(Defaults.getInstance().getWebfilter(host));
                    }
                }
                super.tearDown();
            }
        }, _clear);
    }

    public void loadState() {
        PropertyChangeListener listener = new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent _evt) {
                Request request = new Request(Command.STATE_GET);
                new WorkerValidator().validate(CommunicationManager.getInstance().request(request, new TickerProcessor()));
                RepositoryContext.getDefaultUsers().removePropertyChangeListener(DataLoader.PROPERTYNAME_LOADED, (PropertyChangeListener) this);
            }
        };
        RepositoryContext.getDefaultUsers().addPropertyChangeListener(PROPERTYNAME_LOADED, listener);
        if (RepositoryContext.getDefaultUsers().isLoaded()) {
            Request request = new Request(Command.STATE_GET);
            new WorkerValidator().validate(CommunicationManager.getInstance().request(request, new TickerProcessor()));
            RepositoryContext.getDefaultUsers().removePropertyChangeListener(PROPERTYNAME_LOADED, listener);
        }
    }

    public void loadUserContext() {
        this.loadUserContext(RepositoryContext.getInstance());
    }

    public void loadUserContext(RepositoryContext _context) {
        _context.getGroups().clear();
        _context.getRoles().clear();
        _context.getUsers().clear();
        this.loadGroups(_context, false);
        this.loadRoles(_context, false);
        this.loadUsers(_context, false);
        this.loadUserLists(_context, false);
    }

    public void loadUserLists() {
        this.loadUserLists(RepositoryContext.getInstance(), true);
    }

    public void loadUserLists(final RepositoryContext _context, boolean _clear) {
        this.load(Command.USERLIST_LIST, new AbstractJsonProcessor<UserList, String>((AbstractBeanRepository) _context.getUserLists()) {

            @Override
            protected GsonBuilder buildGsonBuilder() {
                return DataLoader.buildUserListDeserializer(_context);
            }

            @Override
            public String getDescription() {
                return "view.progress.userlist_list";
            }

            @Override
            public void processLine(String _line) {
                System.out.println(_line);
                try {
                    int start = _line.indexOf("\"list\":[");
                    int end = _line.indexOf("]", start);
                    String list = _line.substring(start + 9, end - 1);
                    String withoutList = _line.replaceFirst("\"list\":\\[.+?\\],?", "");
                    JsonElement element = new JsonParser().parse(withoutList);
                    if (element.isJsonObject()) {
                        JsonObject object = element.getAsJsonObject();
                        object.add("list", (JsonElement) new JsonPrimitive(list));
                        this.processJson(object);
                    }
                } catch (JsonParseException _jpe) {
                    _jpe.printStackTrace();
                }
            }
        }, _clear);
    }

    public void loadUsers() {
        this.loadUsers(RepositoryContext.getInstance(), true);
    }

    public void loadUsers(RepositoryContext _context, boolean _clear) {
        this.load(Command.USER_LIST, "attrs=diskquota", this.buildUserLoaderProcessor(_context), _clear);
    }

    private static class DefaultsProcessor
            extends JsonProcessorAdapter {
        DefaultsProcessor() {
        }

        @Override
        public String getDescription() {
            return "view.progress.state_defaults";
        }

        @Override
        public void processJson(JsonObject _object) {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Defaults.class, (Object) new InstanceCreator<Defaults>() {

                public Defaults createInstance(Type _type) {
                    return Defaults.getInstance();
                }
            });
            builder.registerTypeAdapter(Switch.class, (Object) new EnumDeserializer());
            builder.setFieldNamingStrategy(new FieldNamingStrategy() {

                public String translateName(Field _f) {
                    return _f.getName().equals("defaults") ? "default" : _f.getName();
                }
            });
            builder.create().fromJson((JsonElement) _object, Defaults.class);
        }

        @Override
        public void setUp() {
            Defaults.getInstance().setLoaded(false);
        }

        @Override
        public void tearDown() {
            Defaults.getInstance().setLoaded(true);
        }

    }

    public static abstract class AbstractRpcProcessor<R extends RpcObject>
            extends AbstractJsonProcessor<R, Long> {
        public AbstractRpcProcessor(AbstractBeanRepository<R, Long> _repo) {
            super(_repo);
        }

        @Override
        protected JsonObject morph(JsonObject _object) {
            JsonObject jo = new JsonObject();
            for (Map.Entry entry : _object.entrySet()) {
                if (((String) entry.getKey()).equals("attributes")) {
                    for (Map.Entry scope : ((JsonObject) entry.getValue()).entrySet()) {
                        for (Map.Entry attribute : ((JsonObject) scope.getValue()).entrySet()) {
                            jo.add((String) scope.getKey() + "." + (String) attribute.getKey(), (JsonElement) attribute.getValue());
                        }
                    }
                    continue;
                }
                jo.add((String) entry.getKey(), (JsonElement) entry.getValue());
            }
            return jo;
        }
    }

    public static abstract class AbstractJsonProcessor<B extends JavaBean, T>
            extends JsonProcessorAdapter {
        private GsonBuilder builder;
        private RepositoryInstanceCreator<B, T> creator;
        private Gson gson;
        private final AbstractBeanRepository<B, T> repo;
        private long startTime;

        AbstractJsonProcessor(AbstractBeanRepository<B, T> _repo) {
            this.repo = _repo;
        }

        protected abstract GsonBuilder buildGsonBuilder();

        public RepositoryInstanceCreator<B, T> getCreator() {
            if (this.creator == null) {
                this.creator = new RepositoryInstanceCreator<B, T>(this.repo);
            }
            return this.creator;
        }

        public Gson getGson() {
            if (this.gson == null) {
                this.gson = this.getGsonBuilder().create();
            }
            return this.gson;
        }

        public GsonBuilder getGsonBuilder() {
            if (this.builder == null) {
                this.builder = this.buildGsonBuilder();
                this.builder.registerTypeAdapter(this.getRepo().getBeanClass(), this.getCreator());
            }
            return this.builder;
        }

        public AbstractBeanRepository<B, T> getRepo() {
            return this.repo;
        }

        protected JsonObject morph(JsonObject _object) {
            return _object;
        }

        @Override
        public void processJson(JsonObject _object) {
            JsonObject jo = this.morph(_object);
            JsonElement elem = jo.get(this.getRepo().getIdName());
            Object id = this.getRepo().getIdClass().equals(Long.class) ? Long.valueOf(elem.getAsLong()) : elem.getAsString();
            this.getCreator().setNextId((T) id);
            try {
                JavaBean b = (JavaBean) this.getGson().fromJson((JsonElement) jo, this.repo.getBeanClass());
            } catch (JsonParseException _jpe) {
                _jpe.printStackTrace();
            }
        }

        @Override
        public void setUp() {
            this.startTime = System.currentTimeMillis();
        }

        @Override
        public void tearDown() {
            this.repo.setLoaded(true);
            System.out.println(this.getDescription() + (System.currentTimeMillis() - this.startTime) + " ms.");
        }
    }

}

