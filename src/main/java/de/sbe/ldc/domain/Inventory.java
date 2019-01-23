/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    public static final String EDITABLE_PROPERTYNAME_COMMISION_DATE = "commission_date";
    public static final String EDITABLE_PROPERTYNAME_INVENTORY_NUMBER = "inventory_number";
    public static final String EDITABLE_PROPERTYNAME_PURCHASE_DATE = "purchase_date";
    public static final String EDITABLE_PROPERTYNAME_SYSTEM_SERIAL = "system_serial";
    public static final String EDITABLE_PROPERTYNAME_VENDOR = "vendor";
    public static final String EDITABLE_PROPERTYNAME_WARANTY_END = "warranty_end";
    private Map<String, Value> content;

    public Map<String, Value> getContent() {
        if (this.content == null) {
            this.content = new HashMap<String, Value>();
        }
        return this.content;
    }

    public void putEntry(Value _entry) {
        this.getContent().put(_entry.getAttribute_key(), _entry);
    }

    public static class Value {
        private String attribute_key;
        private String attribute_source;
        private String attribute_value;
        private int data_id;
        private int device_id;
        private boolean is_stale;
        private int timestamp;

        public String getAttribute_key() {
            return this.attribute_key;
        }

        public String getAttribute_source() {
            return this.attribute_source;
        }

        public String getAttribute_value() {
            return this.attribute_value;
        }

        public int getData_id() {
            return this.data_id;
        }

        public int getDevice_id() {
            return this.device_id;
        }

        public int getTimestamp() {
            return this.timestamp;
        }

        public boolean isIs_stale() {
            return this.is_stale;
        }

        public void setAttribute_key(String _attribute_key) {
            this.attribute_key = _attribute_key;
        }

        public void setAttribute_source(String _attribute_source) {
            this.attribute_source = _attribute_source;
        }

        public void setAttribute_value(String _attribute_value) {
            this.attribute_value = _attribute_value;
        }

        public void setData_id(int _data_id) {
            this.data_id = _data_id;
        }

        public void setDevice_id(int _device_id) {
            this.device_id = _device_id;
        }

        public void setIs_stale(boolean _is_stale) {
            this.is_stale = _is_stale;
        }

        public void setTimestamp(int _timestamp) {
            this.timestamp = _timestamp;
        }
    }

}

