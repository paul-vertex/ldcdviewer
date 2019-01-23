/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 */
package de.sbe.ldc.persistence.protocol;

import com.google.gson.JsonObject;

public class ResponseStatus {
    public static final String PROPERTYNAME_DATA = "data";
    public static final String PROPERTYNAME_TEXT = "text";
    public static final String PROPERTYNAME_TYPE = "type";
    public static final ResponseStatus SUCCESS = new ResponseStatus(Type.OK);
    private JsonObject data;
    private String text;
    private Type type;

    public static boolean isError(ResponseStatus _status) {
        return _status != null && Type.ERROR.equals((Object)_status.getType());
    }

    public static boolean isFatal(ResponseStatus _status) {
        return _status != null && Type.FATAL.equals((Object)_status.getType());
    }

    public static boolean isInfo(ResponseStatus _status) {
        return _status != null && Type.INFO.equals((Object)_status.getType());
    }

    public static boolean isOk(ResponseStatus _status) {
        return _status != null && Type.OK.equals((Object)_status.getType());
    }

    public static boolean isWarning(ResponseStatus _status) {
        return _status != null && Type.WARNING.equals((Object)_status.getType());
    }

    public ResponseStatus() {
        this(null, null);
    }

    public ResponseStatus(Type _status) {
        this(_status, null);
    }

    public ResponseStatus(Type _status, String _text) {
        this.type = _status;
        this.text = _text;
    }

    public JsonObject getData() {
        return this.data;
    }

    public String getText() {
        return this.text;
    }

    public Type getType() {
        return this.type == null ? Type.ERROR : this.type;
    }

    public void setData(JsonObject _data) {
        this.data = _data;
    }

    public void setText(String _text) {
        this.text = _text;
    }

    public void setType(Type _status) {
        this.type = _status;
    }

    public static enum Type {
        ERROR(3000),
        FATAL(4000),
        INFO(1000),
        OK(0),
        WARNING(2000);
        
        private final int severity;

        private Type(int _severity) {
            this.severity = _severity;
        }

        public int getSeverity() {
            return this.severity;
        }
    }

}

