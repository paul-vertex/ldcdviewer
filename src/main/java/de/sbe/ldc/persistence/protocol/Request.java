/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.persistence.protocol;

import de.sbe.ldc.persistence.morpher.Encoder;
import de.sbe.ldc.persistence.protocol.Command;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class Request {
    public static final int DEFAULT_DATA_NUMBER = -1;
    private static long lastId = 0L;
    private static final ReentrantLock LOCK = new ReentrantLock();
    private final Command command;
    private int dataNumber = -1;
    private Object dataObject;
    private final Map<String, Object> encodedData;
    private Encoder encoder;
    private final long id;
    private String info;
    private InputStream is;
    private final Set<String> plainData;
    private RequestStatus status;

    private static long nextId() {
        long nextId;
        nextId = 0L;
        LOCK.lock();
        try {
            nextId = ++lastId;
        }
        finally {
            LOCK.unlock();
        }
        return nextId;
    }

    public Request(Command _command) {
        this(_command, null);
    }

    public Request(Command _command, InputStream _is) {
        this.command = _command;
        this.is = _is;
        this.encodedData = new HashMap<String, Object>();
        this.plainData = new HashSet<String>();
        this.id = Request.nextId();
    }

    public Command getCommand() {
        return this.command;
    }

    public int getDataNumber() {
        return this.dataNumber;
    }

    public Object getDataObject() {
        return this.dataObject;
    }

    public Map<String, Object> getEncodedData() {
        return Collections.unmodifiableMap(this.encodedData);
    }

    public Encoder getEncoder() {
        return this.encoder;
    }

    public long getId() {
        return this.id;
    }

    public String getInfo() {
        return this.info;
    }

    public InputStream getIs() {
        return this.is;
    }

    public List<String> getPlainData() {
        return Collections.unmodifiableList(Collections.list(Collections.enumeration(this.plainData)));
    }

    public RequestStatus getStatus() {
        return this.status;
    }

    public void putEncodedData(Map<String, Object> _data) {
        this.encodedData.putAll(_data);
    }

    public void putEncodedData(String _key, Object _value) {
        this.encodedData.put(_key, _value);
    }

    public void putPlainData(String _data) {
        this.plainData.add(_data);
    }

    public void putPlainData(String _key, List<String> _values) {
        StringBuilder builder = new StringBuilder();
        Iterator<String> it = _values.iterator();
        while (it.hasNext()) {
            builder.append(it.next());
            if (!it.hasNext()) continue;
            builder.append(",");
        }
        this.plainData.add(_key + "=" + builder.toString());
    }

    public void putPlainData(String _key, String _value) {
        this.plainData.add(_key + "=" + _value);
    }

    public void setDataNumber(int _dataNumber) {
        this.dataNumber = _dataNumber;
    }

    public void setDataObject(Object _dataObject) {
        this.dataObject = _dataObject;
    }

    public void setEncoder(Encoder _encoder) {
        this.encoder = _encoder;
    }

    void setInfo(String _info) {
        this.info = _info;
    }

    public void setIs(InputStream _is) {
        this.is = _is;
    }

    void setStatus(String _status) {
        this.status = RequestStatus.forName(_status);
    }

    public enum RequestStatus {
        AUTH("-ERR [ENOPERM]"),
        ERROR("-ERR"),
        OK("+OK");
        

        public static RequestStatus forName(String _name) {
            String key = _name != null ? _name.toLowerCase() : "";
            return Helper.MAPPING.containsKey(key) ? Helper.MAPPING.get(key) : ERROR;
        }

        private RequestStatus(String _key) {
            Helper.MAPPING.put(_key.toLowerCase(), this);
        }

        private static final class Helper {
            public static final Map<String, RequestStatus> MAPPING = new HashMap<String, RequestStatus>();

            private Helper() {
            }
        }

    }

}

