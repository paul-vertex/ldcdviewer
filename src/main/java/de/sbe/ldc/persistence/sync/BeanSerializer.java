/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  org.apache.commons.beanutils.BeanUtils
 *  org.apache.commons.beanutils.PropertyUtils
 */
package de.sbe.ldc.persistence.sync;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.sbe.ldc.domain.AbstractBean;
import de.sbe.ldc.domain.Contact;
import de.sbe.ldc.domain.Group;
import de.sbe.ldc.domain.Host;
import de.sbe.ldc.domain.JavaBean;
import de.sbe.ldc.domain.PropertyChangeMediator;
import de.sbe.ldc.domain.Role;
import de.sbe.ldc.domain.Room;
import de.sbe.ldc.domain.RoomImpl;
import de.sbe.ldc.domain.User;
import de.sbe.ldc.domain.UserImpl;
import de.sbe.ldc.domain.UserList;
import de.sbe.ldc.domain.filter.GroupFilter;
import de.sbe.ldc.domain.filter.RoleFilter;
import de.sbe.ldc.domain.repository.AbstractBeanRepository;
import de.sbe.ldc.domain.repository.ContactRepository;
import de.sbe.ldc.domain.repository.GroupRepository;
import de.sbe.ldc.domain.repository.HostRepository;
import de.sbe.ldc.domain.repository.RepositoryContext;
import de.sbe.ldc.domain.repository.UserListRepository;
import de.sbe.ldc.domain.repository.UserRepository;
import de.sbe.ldc.persistence.morpher.Encoder;
import de.sbe.ldc.persistence.morpher.SerializableProperties;
import de.sbe.ldc.persistence.morpher.WritableFilter;
import de.sbe.ldc.persistence.net.CommunicationManager;
import de.sbe.ldc.persistence.net.Worker;
import de.sbe.ldc.persistence.net.WorkerValidator;
import de.sbe.ldc.persistence.protocol.ActionStatusLogger;
import de.sbe.ldc.persistence.protocol.Command;
import de.sbe.ldc.persistence.protocol.Processor;
import de.sbe.ldc.persistence.protocol.Request;
import de.sbe.ldc.persistence.protocol.ResponseAction;
import de.sbe.ldc.persistence.sync.DataLoader;
import de.sbe.utils.StringUtils;
import de.sbe.utils.filter.AndFilter;
import de.sbe.utils.filter.Filter;
import de.sbe.utils.filter.Filters;
import de.sbe.utils.filter.TrueFilter;
import de.sbe.utils.logging.LogUtils;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

public class BeanSerializer
extends AbstractBean
implements PropertyChangeListener {
    public static final String PROPERTYNAME_COMMIT = "commit";
    public static final String PROPERTYNAME_REVERT = "revert";
    private static final long serialVersionUID = -3371196330611487722L;
    private static final List<PropertyChangeListener> STATIC_LISTENER = new ArrayList<PropertyChangeListener>();
    private final List<PropertyChangeEvent> changes = new ArrayList<PropertyChangeEvent>();
    private final Logger logger = LogUtils.getLogger(this.getClass());

    public static void addStaticPropertyChangeListener(PropertyChangeListener _listener) {
        STATIC_LISTENER.add(_listener);
    }

    public static void commit(Modifier _changer) {
        BeanSerializer serializer = new BeanSerializer();
        _changer.getJavaBean().addPropertyChangeListener(serializer);
        _changer.modify();
        _changer.getJavaBean().removePropertyChangeListener(serializer);
        serializer.commit();
    }

    public static void commit(Request _request) {
        BeanSerializer.commit(_request, null);
    }

    public static void commit(Request _request, Processor _processor) {
        Processor processor = _processor == null ? new ActionStatusLogger() : _processor;
        Worker worker = CommunicationManager.getInstance().request(_request, processor);
        WorkerValidator validator = new WorkerValidator();
        validator.validate(worker);
    }

    public static Map<String, Object> getAllWritableProperties(Object _obj) {
        return BeanSerializer.getAllWritableProperties(_obj, new TrueFilter<Field>());
    }

    public static Map<String, Object> getAllWritableProperties(Object _obj, Filter<Field> _filter) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        AndFilter<Field> filter = new AndFilter<Field>(_filter, new WritableFilter());
        for (Field field : SerializableProperties.getSerializablePropertiesAsList(_obj.getClass(), filter)) {
            map.put(SerializableProperties.getSerializedName(field), BeanSerializer.toArray(BeanSerializer.getValue(_obj, field)));
        }
        return map;
    }

    public static Object getValue(Object _obj, Field _field) {
        try {
            return PropertyUtils.getProperty((Object)_obj, (String)_field.getName());
        }
        catch (IllegalAccessException _iae) {
            throw new InternalError(_iae.getLocalizedMessage());
        }
        catch (InvocationTargetException _ite) {
            throw new InternalError(_ite.getLocalizedMessage());
        }
        catch (NoSuchMethodException _nsme) {
            throw new InternalError(_nsme.getLocalizedMessage());
        }
    }

    public static void removeStaticPropertyChangeListener(PropertyChangeListener _listener) {
        STATIC_LISTENER.remove(_listener);
    }

    public static Collection<?> toArray(Object _obj) {
        if (_obj instanceof Collection) {
            return (Collection)_obj;
        }
        if (_obj instanceof String && StringUtils.isEmptyString((String)_obj)) {
            return Collections.emptyList();
        }
        return _obj == null ? Collections.emptyList() : Collections.singletonList(_obj);
    }

    private List<PropertyChangeEvent> cleanupChanges(List<PropertyChangeEvent> _changes) {
        Object object;
        ArrayList<PropertyChangeEvent> lchanges = new ArrayList<PropertyChangeEvent>();
        HashSet<Object> removedObject = new HashSet<Object>();
        for (PropertyChangeEvent event : _changes) {
            object = event.getSource();
            Object oldValue = event.getOldValue();
            String property = event.getPropertyName();
            if (!(object instanceof AbstractBeanRepository) || !"removed".equals(property)) continue;
            removedObject.add(oldValue);
        }
        for (PropertyChangeEvent event : _changes) {
            object = event.getSource();
            String property = event.getPropertyName();
            if (object instanceof AbstractBeanRepository ? !"added".equals(property) && !"removed".equals(property) : !SerializableProperties.isSerializableProperty(object.getClass(), property, new WritableFilter()) || removedObject.contains(object)) continue;
            lchanges.add(event);
        }
        return lchanges;
    }

    public void commit() {
        if (this.changes.isEmpty()) {
            this.logger.warning("logging.persistence.sync.partial_empty");
            return;
        }
        for (PropertyChangeListener listener : STATIC_LISTENER) {
            this.addPropertyChangeListener(listener);
        }
        try {
            this.firePropertyChange(PROPERTYNAME_COMMIT, false, true);
            this.commitChanges(this.reorganizeChanges(this.changes));
        }
        finally {
            this.firePropertyChange(PROPERTYNAME_COMMIT, true, false);
            for (PropertyChangeListener listener : STATIC_LISTENER) {
                this.removePropertyChangeListener(listener);
            }
        }
    }

    private void commitChanges(Map<Class<?>, Map<Object, Map<String, EnumMap<State, Object>>>> _changes) {
        if (_changes.containsKey(ContactRepository.class)) {
            this.serializeContactRepository(_changes.get(ContactRepository.class));
            _changes.remove(ContactRepository.class);
        } else if (_changes.containsKey(GroupRepository.class)) {
            this.serializeGroupRepository(_changes.get(GroupRepository.class));
            _changes.remove(GroupRepository.class);
        } else if (_changes.containsKey(HostRepository.class)) {
            this.serializeHostRepository(_changes.get(HostRepository.class));
            _changes.remove(HostRepository.class);
        } else if (_changes.containsKey(UserListRepository.class)) {
            this.serializeUserListRepository(_changes.get(UserListRepository.class));
            _changes.remove(UserListRepository.class);
        } else if (_changes.containsKey(UserRepository.class)) {
            this.serializeUserRepository(_changes.get(UserRepository.class));
            _changes.remove(UserRepository.class);
        }
        for (Map.Entry<Class<?>, Map<Object, Map<String, EnumMap<State, Object>>>> entry : _changes.entrySet()) {
            if (Contact.class.isAssignableFrom(entry.getKey())) {
                this.serializeContactInstance(entry.getValue());
                continue;
            }
            if (Group.class.isAssignableFrom(entry.getKey())) {
                this.serializeGroupInstance(entry.getValue());
                continue;
            }
            if (Host.class.isAssignableFrom(entry.getKey())) {
                this.serializeHostInstance(entry.getValue());
                continue;
            }
            if (Room.class.isAssignableFrom(entry.getKey())) {
                this.serializeRoomInstance(entry.getValue());
                continue;
            }
            if (User.class.isAssignableFrom(entry.getKey())) {
                this.serializeUserInstance(entry.getValue());
                continue;
            }
            if (!UserList.class.isAssignableFrom(entry.getKey())) continue;
            this.serializeUserListInstance(entry.getValue());
        }
    }

    private Map<String, Object> getOnlyChangedProperties(Object _obj, Map<String, EnumMap<State, Object>> _values) {
        return this.getOnlyChangedProperties(_obj, _values, new TrueFilter<Map.Entry<String, EnumMap<State, Object>>>());
    }

    private Map<String, Object> getOnlyChangedProperties(Object _obj, Map<String, EnumMap<State, Object>> _values, Filter<Map.Entry<String, EnumMap<State, Object>>> _filter) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (Map.Entry<String, EnumMap<State, Object>> modification : Filters.filterAsSet(_values.entrySet(), _filter)) {
            map.put(SerializableProperties.getSerializedName(_obj.getClass(), modification.getKey()), BeanSerializer.toArray(modification.getValue().get((Object)State.NEW)));
        }
        return map;
    }

    @Override
    public void propertyChange(PropertyChangeEvent _evt) {
        this.changes.add(_evt);
    }

    private Map<Class<?>, Map<Object, Map<String, EnumMap<State, Object>>>> reorganizeChanges(List<PropertyChangeEvent> _changes) {
        HashMap reorganizedChanges = new HashMap();
        for (PropertyChangeEvent event : this.cleanupChanges(_changes)) {
            HashMap<String, EnumMap<State, Object>> objectChanges;
            EnumMap<State, Object> propertyChanges;
            HashMap classChanges = (HashMap)reorganizedChanges.get(event.getSource().getClass());
            if (classChanges == null) {
                classChanges = new HashMap();
                reorganizedChanges.put(event.getSource().getClass(), classChanges);
            }
            if ((objectChanges = (HashMap<String, EnumMap<State, Object>>)classChanges.get(event.getSource())) == null) {
                objectChanges = new HashMap<String, EnumMap<State, Object>>();
                classChanges.put(event.getSource(), objectChanges);
            }
            if ((propertyChanges = (EnumMap<State, Object>)objectChanges.get(event.getPropertyName())) == null) {
                propertyChanges = new EnumMap<State, Object>(State.class);
                objectChanges.put(event.getPropertyName(), propertyChanges);
            }
            propertyChanges.put(State.NEW, event.getNewValue());
            if (propertyChanges.get((Object)State.OLD) != null) continue;
            propertyChanges.put(State.OLD, event.getOldValue());
        }
        return reorganizedChanges;
    }

    private void serializeContactInstance(Map<Object, Map<String, EnumMap<State, Object>>> _changes) {
        for (Map.Entry<Object, Map<String, EnumMap<State, Object>>> objectChanges : _changes.entrySet()) {
            Contact contact = (Contact)objectChanges.getKey();
            Request request = new Request(Command.RPC);
            request.putEncodedData("rpc_version", "1.0");
            request.putEncodedData("rpc_request", "update_object");
            request.putEncodedData("type", "contact");
            request.putEncodedData("id", contact.getId());
            request.putEncodedData(this.getOnlyChangedProperties(contact, objectChanges.getValue()));
            BeanSerializer.commit(request);
        }
    }

    private void serializeContactRepository(Map<Object, Map<String, EnumMap<State, Object>>> _changes) {
        for (Map.Entry<Object, Map<String, EnumMap<State, Object>>> classChanges : _changes.entrySet()) {
            for (Map.Entry<String, EnumMap<State, Object>> objectChanges : classChanges.getValue().entrySet()) {
                Contact contact;
                Request request = new Request(Command.RPC);
                ActionStatusLogger processor = null;
                request.putEncodedData("rpc_version", "1.0");
                request.putEncodedData("type", "contact");
                if ("added".equals(objectChanges.getKey())) {
                    contact = (Contact)objectChanges.getValue().get((Object)State.NEW);
                    request.putEncodedData("rpc_request", "create_object");
                    request.putEncodedData(BeanSerializer.getAllWritableProperties(contact));
                    processor = new ActionStatusLogger(){

                        @Override
                        protected void doTearDown() {
                            super.doTearDown();
                            contact.setId(this.getId());
                        }
                    };
                } else {
                    contact = (Contact)objectChanges.getValue().get((Object)State.OLD);
                    request.putEncodedData("rpc_request", "remove_object");
                    request.putEncodedData("id", contact.getId());
                }
                BeanSerializer.commit(request, processor);
            }
        }
    }

    private void serializeGroupInstance(Map<Object, Map<String, EnumMap<State, Object>>> _changes) {
        for (Map.Entry<Object, Map<String, EnumMap<State, Object>>> objectChanges : _changes.entrySet()) {
            Group group = (Group)objectChanges.getKey();
            Request request = new Request(Command.GROUP_MODIFY);
            request.putPlainData("cn", group.getCn());
            request.putEncodedData(this.getOnlyChangedProperties(group, objectChanges.getValue()));
            BeanSerializer.commit(request);
        }
    }

    private void serializeGroupMember(User _member, List _old, List _new) {
        Set oldGroups = null;
        if (_old == null) {
            oldGroups = Collections.emptySet();
        } else {
            oldGroups = new HashSet(_old);
        }

        Set newGroups = null;
        if (_new == null) {
            newGroups = Collections.emptySet();
        } else {
            newGroups = new HashSet(_new);
        }

        if (RoleFilter.isStudent(_member.getRole())) {
            List oldClasses = Filters.filterAsList((Collection)oldGroups, GroupFilter.CLASSES);
            List newClasses = Filters.filterAsList((Collection)newGroups, GroupFilter.CLASSES);
            if (!oldClasses.isEmpty() && !newClasses.isEmpty()) {
                Group oldClass = (Group)oldClasses.get(0);
                Group newClass = (Group)newClasses.get(0);
                if (!newClass.equals(oldClass)) {
                    Request request = new Request(Command.GROUP_MOVEMEMBER);
                    request.putEncodedData("uid", _member.getUid());
                    request.putEncodedData("old_group", oldClass.getCn());
                    request.putEncodedData("new_group", newClass.getCn());
                    commit(request);
                    ((Set)oldGroups).removeAll(oldClasses);
                    ((Set)newGroups).removeAll(newClasses);
                }
            }
        }

        Iterator var11 = ((Set)oldGroups).iterator();

        Group group;
        Request request;
        while(var11.hasNext()) {
            group = (Group)var11.next();
            if (!((Set)newGroups).contains(group)) {
                request = new Request(Command.GROUP_DELMEMBER);
                request.putPlainData("cn", group.getCn());
                request.putEncodedData("member", _member.getUid());
                commit(request);
            }
        }

        var11 = ((Set)newGroups).iterator();

        while(var11.hasNext()) {
            group = (Group)var11.next();
            if (!((Set)oldGroups).contains(group)) {
                request = new Request(Command.GROUP_ADDMEMBER);
                request.putPlainData("cn", group.getCn());
                request.putEncodedData("member", _member.getUid());
                commit(request);
            }
        }

    }

    private void serializeGroupRepository(Map<Object, Map<String, EnumMap<State, Object>>> _changes) {
        for (Map.Entry<Object, Map<String, EnumMap<State, Object>>> classChanges : _changes.entrySet()) {
            for (Map.Entry<String, EnumMap<State, Object>> objectChanges : classChanges.getValue().entrySet()) {
                Group group = null;
                Request request = null;
                if ("added".equals(objectChanges.getKey())) {
                    group = (Group)objectChanges.getValue().get((Object)State.NEW);
                    request = new Request(Command.GROUP_ADD);
                } else {
                    group = (Group)objectChanges.getValue().get((Object)State.OLD);
                    request = new Request(Command.GROUP_DELETE);
                }
                request.putPlainData("cn", group.getCn());
                if ("added".equals(objectChanges.getKey())) {
                    request.putEncodedData(BeanSerializer.getAllWritableProperties(group));
                }
                BeanSerializer.commit(request);
            }
        }
    }

    private void serializeHostInstance(Map<Object, Map<String, EnumMap<State, Object>>> _changes) {
        for (Map.Entry<Object, Map<String, EnumMap<State, Object>>> objectChanges : _changes.entrySet()) {
            Host host = (Host)objectChanges.getKey();
            Request request = new Request(Command.HOST_MODIFY);
            request.putPlainData("cn", host.getCn());
            request.putEncodedData(this.getOnlyChangedProperties(host, objectChanges.getValue()));
            BeanSerializer.commit(request);
        }
    }

    private void serializeHostRepository(Map<Object, Map<String, EnumMap<State, Object>>> _changes) {
        for (Map.Entry<Object, Map<String, EnumMap<State, Object>>> classChanges : _changes.entrySet()) {
            for (Map.Entry<String, EnumMap<State, Object>> objectChanges : classChanges.getValue().entrySet()) {
                Host host = null;
                Request request = null;
                if ("added".equals(objectChanges.getKey())) {
                    host = (Host)objectChanges.getValue().get((Object)State.NEW);
                    request = new Request(Command.OBJECT_DEVICE_ADD);
                } else {
                    host = (Host)objectChanges.getValue().get((Object)State.OLD);
                    request = new Request(Command.OBJECT_DEVICE_DELETE);
                }
                request.putPlainData("dn", host.getDn());
                if ("added".equals(objectChanges.getKey())) {
                    request.putEncodedData(BeanSerializer.getAllWritableProperties(host));
                }
                BeanSerializer.commit(request);
            }
        }
    }

    private void serializeRoomInstance(Map<Object, Map<String, EnumMap<State, Object>>> _changes) {
        for (Map.Entry<Object, Map<String, EnumMap<State, Object>>> objectChanges : _changes.entrySet()) {
            Room room = (Room)objectChanges.getKey();
            Request request = new Request(Command.ROOM_MODIFY);
            request.putPlainData("cn", room.getCn());
            if (objectChanges.getValue().containsKey("csettings")) {
                Object csettings = objectChanges.getValue().get("csettings").get((Object)State.NEW);
                request.putEncodedData(SerializableProperties.getSerializedName(RoomImpl.class, "csettings"), BeanSerializer.toArray(new Encoder().encode(csettings)));
            }
            request.putEncodedData(this.getOnlyChangedProperties(room, objectChanges.getValue(), new Filter<Map.Entry<String, EnumMap<State, Object>>>(){

                @Override
                public boolean include(Map.Entry<String, EnumMap<State, Object>> _obj) {
                    return !"csettings".equals(_obj.getKey());
                }
            }));
            BeanSerializer.commit(request);
        }
    }

    private void serializeUserInstance(Map<Object, Map<String, EnumMap<State, Object>>> _changes) {
        for (Map.Entry<Object, Map<String, EnumMap<State, Object>>> objectChanges : _changes.entrySet()) {
            User user = (User)objectChanges.getKey();
            Request request = new Request(Command.USER_MODIFY);
            request.putPlainData("uid", user.getUid());
            request.putEncodedData(this.getOnlyChangedProperties(user, objectChanges.getValue(), new Filter<Map.Entry<String, EnumMap<State, Object>>>(){

                @Override
                public boolean include(Map.Entry<String, EnumMap<State, Object>> _obj) {
                    return !"groups".equals(_obj.getKey());
                }
            }));
            if (!request.getEncodedData().isEmpty()) {
                BeanSerializer.commit(request);
            }
            if (!objectChanges.getValue().containsKey("groups")) continue;
            EnumMap<State, Object> propertyChanges = objectChanges.getValue().get("groups");
            this.serializeGroupMember(user, (List)propertyChanges.get((Object)State.OLD), (List)propertyChanges.get((Object)State.NEW));
        }
    }

    private void serializeUserListInstance(Map<Object, Map<String, EnumMap<State, Object>>> _changes) {
        for (Map.Entry<Object, Map<String, EnumMap<State, Object>>> objectChanges : _changes.entrySet()) {
            UserList list = (UserList)objectChanges.getKey();
            Request request = new Request(Command.USERLIST_MODIFY);
            request.putPlainData("id", list.getId());
            request.putEncodedData(BeanSerializer.getAllWritableProperties(list));
            BeanSerializer.commit(request);
        }
    }

    private void serializeUserListRepository(Map<Object, Map<String, EnumMap<State, Object>>> _changes) {
        for (Map.Entry<Object, Map<String, EnumMap<State, Object>>> classChanges : _changes.entrySet()) {
            for (Map.Entry<String, EnumMap<State, Object>> objectChanges : classChanges.getValue().entrySet()) {
                UserList list = null;
                Request request = null;
                if ("added".equals(objectChanges.getKey())) {
                    list = (UserList)objectChanges.getValue().get((Object)State.NEW);
                    request = new Request(Command.USERLIST_ADD);
                } else {
                    list = (UserList)objectChanges.getValue().get((Object)State.OLD);
                    request = new Request(Command.USERLIST_DELETE);
                }
                request.putPlainData("id", list.getId());
                if ("added".equals(objectChanges.getKey())) {
                    request.putEncodedData(BeanSerializer.getAllWritableProperties(list));
                }
                BeanSerializer.commit(request);
            }
        }
    }

    private void serializeUserRepository(Map<Object, Map<String, EnumMap<State, Object>>> _changes) {
        for (Map.Entry<Object, Map<String, EnumMap<State, Object>>> classChanges : _changes.entrySet()) {
            for (Map.Entry<String, EnumMap<State, Object>> objectChanges : classChanges.getValue().entrySet()) {
                User user = null;
                ActionStatusLogger processor = null;
                Request request = null;
                if ("added".equals(objectChanges.getKey())) {
                    User u1;
                    user = u1 = (User)objectChanges.getValue().get((Object)State.NEW);
                    request = new Request(Command.USER_ADD);
                    processor = new ActionStatusLogger(){

                        @Override
                        protected void doProcessAction(ResponseAction _action) {
                            super.doProcessAction(_action);
                            if ("add_user".equals(_action.getAction()) && "create_ldap_entry".equals(_action.getSubAction()) && _action.getOrig().has("data")) {
                                JsonElement data = _action.getOrig().get("data");
                                Gson gson = DataLoader.buildUserDeserializer(RepositoryContext.getInstance()).create();
                                User u2 = (User)gson.fromJson(data, UserImpl.class);
                                try {
                                    BeanUtils.copyProperties((Object)u1, (Object)u2);
                                }
                                catch (IllegalAccessException | InvocationTargetException _e) {
                                    BeanSerializer.this.logger.log(Level.SEVERE, _e.getLocalizedMessage());
                                }
                            }
                        }
                    };
                } else {
                    user = (User)objectChanges.getValue().get((Object)State.OLD);
                    request = new Request(Command.USER_DELETE);
                }
                request.putPlainData("uid", user.getUid());
                if ("added".equals(objectChanges.getKey())) {
                    request.putEncodedData(BeanSerializer.getAllWritableProperties(user, new Filter<Field>(){

                        @Override
                        public boolean include(Field _obj) {
                            return !"groups".equals(_obj.getName());
                        }
                    }));
                }
                BeanSerializer.commit(request, processor);
                if (!"added".equals(objectChanges.getKey())) continue;
                this.serializeGroupMember(user, null, user.getGroups());
            }
        }
    }

    public static enum State {
        NEW,
        OLD;
        
    }

    public static interface Modifier {
        public JavaBean getJavaBean();

        public void modify();
    }

    public static abstract class DefaultModifier
    implements Modifier {
        @Override
        public JavaBean getJavaBean() {
            return RepositoryContext.getInstance().getPropertyChangeMediator();
        }
    }

}

