/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 */
package de.sbe.ldc.persistence.sync;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.sbe.ldc.domain.Defaults;
import de.sbe.ldc.domain.Exam;
import de.sbe.ldc.domain.Host;
import de.sbe.ldc.domain.HostInfo;
import de.sbe.ldc.domain.Power;
import de.sbe.ldc.domain.Role;
import de.sbe.ldc.domain.RoleImpl;
import de.sbe.ldc.domain.Room;
import de.sbe.ldc.domain.RoomInfo;
import de.sbe.ldc.domain.Switch;
import de.sbe.ldc.domain.User;
import de.sbe.ldc.domain.UserImpl;
import de.sbe.ldc.domain.UserInfo;
import de.sbe.ldc.domain.repository.RepositoryContext;
import de.sbe.ldc.persistence.protocol.JsonProcessorAdapter;
import de.sbe.ldc.persistence.sync.DataLoader;
import de.sbe.ldc.resources.I18N;
import de.sbe.utils.ConcurrentUtils;
import de.sbe.utils.StringUtils;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class TickerProcessor
extends JsonProcessorAdapter {
    public static final Pattern PATTERN_FALSE = Pattern.compile("false|off", 2);
    public static final Pattern PATTERN_TRUE = Pattern.compile("true|on", 2);
    public static final String PROPERTY_HOST = "host";
    public static final String PROPERTY_ID = "id";
    public static final String PROPERTY_ROOM = "room";
    public static final String PROPERTY_STATE = "state";
    public static final String PROPERTY_STATE_ID = "stateId";
    public static final String PROPERTY_TYPE = "type";
    public static final String PROPERTY_USER = "user";
    final Queue<JsonObject> cache = new LinkedBlockingQueue<JsonObject>();
    final Condition condition = this.lock.newCondition();
    final ReentrantLock lock = new ReentrantLock();

    public TickerProcessor() {
        ConcurrentUtils.newSingleDaemonThreadExecutor().execute(new Worker());
        RepositoryContext.getDefaultHosts().addPropertyChangeListener("loaded", (PropertyChangeListener)new LoadedHandler());
        RepositoryContext.getDefaultRooms().addPropertyChangeListener("loaded", (PropertyChangeListener)new LoadedHandler());
        RepositoryContext.getDefaultUsers().addPropertyChangeListener("loaded", (PropertyChangeListener)new LoadedHandler());
    }

    @Override
    public String getDescription() {
        return I18N.getLocalizedString("view.progress.state_get");
    }

    @Override
    public void processJson(JsonObject _object) {
        if (_object == null) {
            return;
        }
        this.lock.lock();
        try {
            this.cache.offer(_object);
            this.condition.signal();
        }
        finally {
            this.lock.unlock();
        }
    }

    private class Worker
    extends Thread {
        Worker() {
        }

        private User createLocalUser(String _uid) {
            UserImpl user = new UserImpl();
            user.setRole(RoleImpl.LOCAL_USER_ROLE);
            user.setUid(this.getUserName(_uid));
            return user;
        }

        public String getDomainName(String _uid) {
            return _uid.contains("\\") ? _uid.replaceFirst("\\\\.*$", "") : "";
        }

        private User getUser(String _hostCn, JsonElement _element) {
            return this.getUser(_hostCn, _element.getAsString());
        }

        private User getUser(String _hostCn, String _uid) {
            if (StringUtils.isEmptyString(_uid)) {
                return null;
            }
            User user = null;
            if (StringUtils.isEmptyString(this.getDomainName(_uid)) || _hostCn.equalsIgnoreCase(this.getDomainName(_uid))) {
                user = this.createLocalUser(_uid);
            } else {
                user = (User)RepositoryContext.getDefaultUsers().getById(this.getUserName(_uid));
                if (user == null) {
                    user = this.createLocalUser(_uid);
                }
            }
            return user;
        }

        public String getUserName(String _uid) {
            return _uid.replaceFirst("^.*\\\\", "");
        }

        private void processTypeHost(JsonObject _object) {
            JsonElement id = _object.get(TickerProcessor.PROPERTY_ID.toLowerCase());
            if (id == null) {
                TickerProcessor.this.logger.warning(I18N.getLocalizedString("logging.persistence.sync.ticker.id_missing", new Object[]{_object, id}));
                return;
            }
            Host host = (Host)RepositoryContext.getDefaultHosts().getById(id.getAsString());
            if (host == null) {
                TickerProcessor.this.logger.warning(I18N.getLocalizedString("logging.persistence.sync.ticker.object_missing", new Object[]{id}));
                return;
            }
            JsonElement state = _object.get(TickerProcessor.PROPERTY_STATE.toLowerCase());
            JsonElement stateId = _object.get(TickerProcessor.PROPERTY_STATE_ID.toLowerCase());
            if (stateId == null) {
                TickerProcessor.this.logger.warning(I18N.getLocalizedString("logging.persistence.sync.ticker.unknown_state", new Object[]{_object}));
                return;
            }
            if ("agentVersion".equalsIgnoreCase(stateId.getAsString())) {
                host.getHostInfo().setAgentVersion(state.getAsString());
            } else if ("exam".equalsIgnoreCase(stateId.getAsString())) {
                if (state.isJsonObject()) {
                    host.getHostInfo().setExam((Exam)DataLoader.buildExamDeserializer(RepositoryContext.getInstance()).create().fromJson(state, Exam.class));
                } else {
                    host.getHostInfo().setExam(null);
                }
            } else if ("idletime".equalsIgnoreCase(stateId.getAsString())) {
                if (state.isJsonObject()) {
                    HashMap<User, Long> idletime = new HashMap<User, Long>();
                    for (Map.Entry entry : state.getAsJsonObject().entrySet()) {
                        User user = this.getUser(host.getCn(), (String)entry.getKey());
                        if (user == null) continue;
                        idletime.put(user, ((JsonElement)entry.getValue()).getAsLong());
                    }
                    host.getHostInfo().setIdletime(idletime.isEmpty() ? null : idletime);
                } else {
                    host.getHostInfo().setIdletime(null);
                }
            } else if ("input".equalsIgnoreCase(stateId.getAsString())) {
                String value;
                String string = value = state == null ? null : state.getAsString();
                if (StringUtils.isEmptyString(value)) {
                    host.getHostInfo().setInput(Defaults.getInstance().getInput(host));
                } else {
                    host.getHostInfo().setInput(Switch.forString(value));
                }
            } else if ("internet".equalsIgnoreCase(stateId.getAsString())) {
                String value;
                String string = value = state == null ? null : state.getAsString();
                if (StringUtils.isEmptyString(value)) {
                    host.getHostInfo().setIntranet(Defaults.getInstance().getInternet(host));
                } else {
                    host.getHostInfo().setInternet(Switch.forString(value));
                }
            } else if ("intranet".equalsIgnoreCase(stateId.getAsString())) {
                String value;
                String string = value = state == null ? null : state.getAsString();
                if (StringUtils.isEmptyString(value)) {
                    host.getHostInfo().setIntranet(Defaults.getInstance().getIntranet(host));
                } else {
                    host.getHostInfo().setIntranet(Switch.forString(value));
                }
            } else if ("ip".equalsIgnoreCase(stateId.getAsString())) {
                host.getHostInfo().setIp(state.getAsString());
            } else if ("osArch".equalsIgnoreCase(stateId.getAsString())) {
                host.getHostInfo().setOsArch(state.getAsString());
            } else if ("osName".equalsIgnoreCase(stateId.getAsString())) {
                host.getHostInfo().setOsName(state.getAsString());
            } else if ("osVersion".equalsIgnoreCase(stateId.getAsString())) {
                host.getHostInfo().setOsVersion(state.getAsString());
            } else if ("power".equalsIgnoreCase(stateId.getAsString())) {
                if (StringUtils.isEmptyString(state.getAsString())) {
                    host.getHostInfo().setPower(Power.OFF);
                } else {
                    try {
                        host.getHostInfo().setPower(Power.valueOf(state.getAsString().toUpperCase()));
                    }
                    catch (IllegalArgumentException _iae) {
                        host.getHostInfo().setPower(Power.OFF);
                    }
                }
            } else if ("printer".equalsIgnoreCase(stateId.getAsString())) {
                String value;
                String string = value = state == null ? null : state.getAsString();
                if (StringUtils.isEmptyString(value)) {
                    host.getHostInfo().setPrinter(Defaults.getInstance().getPrinter(host));
                } else {
                    host.getHostInfo().setPrinter(Switch.forString(value));
                }
            } else if ("screen".equalsIgnoreCase(stateId.getAsString())) {
                String value;
                String string = value = state == null ? null : state.getAsString();
                if (StringUtils.isEmptyString(value)) {
                    host.getHostInfo().setScreen(Defaults.getInstance().getScreen(host));
                } else {
                    host.getHostInfo().setScreen(Switch.forString(value));
                }
            } else if ("ssm".equalsIgnoreCase(stateId.getAsString())) {
                for (Map.Entry entry : state.getAsJsonObject().entrySet()) {
                    if (!((String)entry.getKey()).equalsIgnoreCase("open")) continue;
                    host.getHostInfo().setSsm(((JsonElement)entry.getValue()).getAsInt());
                }
            } else if ("swap".equalsIgnoreCase(stateId.getAsString())) {
                String value;
                String string = value = state == null ? null : state.getAsString();
                if (StringUtils.isEmptyString(value)) {
                    host.getHostInfo().setSwap(Defaults.getInstance().getSwap(host));
                } else {
                    host.getHostInfo().setSwap(Switch.forString(value));
                }
            } else if ("uptime".equalsIgnoreCase(stateId.getAsString())) {
                String value;
                String string = value = state == null ? null : state.getAsString();
                if (value == null || StringUtils.isEmptyString(value) || value.matches("[^0-9]+")) {
                    host.getHostInfo().setUptime(-1L);
                } else {
                    host.getHostInfo().setUptime(Long.parseLong(value));
                }
            } else if ("usb".equalsIgnoreCase(stateId.getAsString())) {
                String value;
                String string = value = state == null ? null : state.getAsString();
                if (StringUtils.isEmptyString(value)) {
                    host.getHostInfo().setUsb(Defaults.getInstance().getUsb(host));
                } else {
                    host.getHostInfo().setUsb(Switch.forString(value));
                }
            } else if ("users".equalsIgnoreCase(stateId.getAsString())) {
                if (state.isJsonArray()) {
                    ArrayList<User> users = new ArrayList<User>();
                    for (JsonElement element : state.getAsJsonArray()) {
                        User user = this.getUser(host.getCn(), element);
                        if (user == null) continue;
                        users.add(user);
                    }
                    host.getHostInfo().setUsers(users.isEmpty() ? null : users);
                } else if (state.isJsonPrimitive() && !state.isJsonNull()) {
                    User user = this.getUser(host.getCn(), state);
                    if (user == null) {
                        host.getHostInfo().setUsers(null);
                    } else {
                        host.getHostInfo().setUsers(Collections.singleton(user));
                    }
                } else {
                    TickerProcessor.this.logger.warning(I18N.getLocalizedString("logging.persistence.sync.ticker.unknown_state", new Object[]{_object}));
                    host.getHostInfo().setUsers(null);
                }
            } else if ("webfilter".equalsIgnoreCase(stateId.getAsString())) {
                String value;
                String string = value = state == null ? null : state.getAsString();
                if (StringUtils.isEmptyString(value)) {
                    host.getHostInfo().setWebfilter(Defaults.getInstance().getWebfilter(host));
                } else {
                    host.getHostInfo().setWebfilter(Switch.forString(value));
                }
            } else {
                TickerProcessor.this.logger.warning(I18N.getLocalizedString("logging.persistence.sync.ticker.unknown_state", new Object[]{_object}));
            }
        }

        private void processTypeRoom(JsonObject _object) {
            JsonElement id = _object.get(TickerProcessor.PROPERTY_ID.toLowerCase());
            if (id == null) {
                TickerProcessor.this.logger.warning(I18N.getLocalizedString("logging.persistence.sync.ticker.id_missing", new Object[]{_object, id}));
                return;
            }
            Room room = (Room)RepositoryContext.getDefaultRooms().getById(id.getAsString());
            if (room == null) {
                TickerProcessor.this.logger.warning(I18N.getLocalizedString("logging.persistence.sync.ticker.object_missing", new Object[]{id}));
                return;
            }
            JsonElement state = _object.get(TickerProcessor.PROPERTY_STATE.toLowerCase());
            JsonElement stateId = _object.get(TickerProcessor.PROPERTY_STATE_ID.toLowerCase());
            if (stateId == null) {
                TickerProcessor.this.logger.warning(I18N.getLocalizedString("logging.persistence.sync.ticker.unknown_state", new Object[]{_object}));
                return;
            }
            if ("exam".equalsIgnoreCase(stateId.getAsString())) {
                if (state.isJsonObject()) {
                    room.getRoomInfo().setExam((Exam)DataLoader.buildExamDeserializer(RepositoryContext.getInstance()).create().fromJson(state, Exam.class));
                } else {
                    room.getRoomInfo().setExam(null);
                }
            } else if ("input".equalsIgnoreCase(stateId.getAsString())) {
                String value;
                String string = value = state == null ? null : state.getAsString();
                if (StringUtils.isEmptyString(value)) {
                    room.getRoomInfo().setInput(Defaults.getInstance().getInput(room));
                } else {
                    room.getRoomInfo().setInput(Switch.forString(value));
                }
            } else if ("internet".equalsIgnoreCase(stateId.getAsString())) {
                String value;
                String string = value = state == null ? null : state.getAsString();
                if (StringUtils.isEmptyString(value)) {
                    room.getRoomInfo().setInternet(Defaults.getInstance().getInternet(room));
                } else {
                    room.getRoomInfo().setInternet(Switch.forString(value));
                }
            } else if ("intranet".equalsIgnoreCase(stateId.getAsString())) {
                String value;
                String string = value = state == null ? null : state.getAsString();
                if (StringUtils.isEmptyString(value)) {
                    room.getRoomInfo().setIntranet(Defaults.getInstance().getIntranet(room));
                } else {
                    room.getRoomInfo().setIntranet(Switch.forString(value));
                }
            } else if ("printer".equalsIgnoreCase(stateId.getAsString())) {
                String value;
                String string = value = state == null ? null : state.getAsString();
                if (StringUtils.isEmptyString(value)) {
                    room.getRoomInfo().setPrinter(Defaults.getInstance().getPrinter(room));
                } else {
                    room.getRoomInfo().setPrinter(Switch.forString(value));
                }
            } else if ("screen".equalsIgnoreCase(stateId.getAsString())) {
                String value;
                String string = value = state == null ? null : state.getAsString();
                if (StringUtils.isEmptyString(value)) {
                    room.getRoomInfo().setScreen(Defaults.getInstance().getScreen(room));
                } else {
                    room.getRoomInfo().setScreen(Switch.forString(value));
                }
            } else if ("swap".equalsIgnoreCase(stateId.getAsString())) {
                String value;
                String string = value = state == null ? null : state.getAsString();
                if (StringUtils.isEmptyString(value)) {
                    room.getRoomInfo().setSwap(Defaults.getInstance().getSwap(room));
                } else {
                    room.getRoomInfo().setSwap(Switch.forString(value));
                }
            } else if ("webfilter".equalsIgnoreCase(stateId.getAsString())) {
                String value;
                String string = value = state == null ? null : state.getAsString();
                if (StringUtils.isEmptyString(value)) {
                    room.getRoomInfo().setWebfilter(Defaults.getInstance().getWebfilter(room));
                } else {
                    room.getRoomInfo().setWebfilter(Switch.forString(value));
                }
            } else if ("usb".equalsIgnoreCase(stateId.getAsString())) {
                String value;
                String string = value = state == null ? null : state.getAsString();
                if (StringUtils.isEmptyString(value)) {
                    room.getRoomInfo().setUsb(Defaults.getInstance().getUsb(room));
                } else {
                    room.getRoomInfo().setUsb(Switch.forString(value));
                }
            } else {
                TickerProcessor.this.logger.warning(I18N.getLocalizedString("logging.persistence.sync.ticker.unknown_state", new Object[]{_object}));
            }
        }

        private void processTypeUser(JsonObject _object) {
            JsonElement id = _object.get(TickerProcessor.PROPERTY_ID.toLowerCase());
            if (id == null) {
                TickerProcessor.this.logger.warning(I18N.getLocalizedString("logging.persistence.sync.ticker.id_missing", new Object[]{_object, id}));
                return;
            }
            User user = (User)RepositoryContext.getDefaultUsers().getById(id.getAsString());
            if (user == null) {
                TickerProcessor.this.logger.warning(I18N.getLocalizedString("logging.persistence.sync.ticker.object_missing", new Object[]{id}));
                return;
            }
            JsonElement state = _object.get(TickerProcessor.PROPERTY_STATE.toLowerCase());
            JsonElement stateId = _object.get(TickerProcessor.PROPERTY_STATE_ID.toLowerCase());
            if (stateId == null) {
                TickerProcessor.this.logger.warning(I18N.getLocalizedString("logging.persistence.sync.ticker.unknown_state", new Object[]{_object}));
                return;
            }
            if ("ssm".equalsIgnoreCase(stateId.getAsString())) {
                for (Map.Entry entry : state.getAsJsonObject().entrySet()) {
                    if (!((String)entry.getKey()).equalsIgnoreCase("open")) continue;
                    user.getUserInfo().setSsm(((JsonElement)entry.getValue()).getAsInt());
                }
            }
        }

        /*
         * Exception decompiling
         */
        @Override
        public void run() {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 1[TRYBLOCK]
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:432)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:692)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:182)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:127)
            // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
            // org.benf.cfr.reader.entities.Method.analyse(Method.java:396)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:890)
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:773)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:870)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:792)
            // org.benf.cfr.reader.Driver.doJar(Driver.java:128)
            // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:63)
            // org.benf.cfr.reader.Main.main(Main.java:48)
            throw new IllegalStateException("Decompilation failed");
        }
    }

    private class LoadedHandler
    implements PropertyChangeListener {
        LoadedHandler() {
        }

        @Override
        public void propertyChange(PropertyChangeEvent _evt) {
            if ("loaded".equals(_evt.getPropertyName()) && ((Boolean)_evt.getNewValue()).booleanValue()) {
                TickerProcessor.this.lock.lock();
                try {
                    TickerProcessor.this.condition.signal();
                }
                finally {
                    TickerProcessor.this.lock.unlock();
                }
            }
        }
    }

}

