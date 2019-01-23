/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.utils.CollectionUtils;
import de.sbe.utils.StringUtils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InventoryMap {
    private static final Map<String, Column> MY_COLUMNS = new HashMap<String, Column>();
    public static final String REL_DISK_SPEED = "rel_rembo_disk_speed";
    public static final String REL_DISK_SPEEDS = "rel_rembo_disk_speeds";
    public static final String REL_NETWORK_MULTICAST_SPEED = "rel_rembo_network_recv_m";
    public static final String REL_NETWORK_SEND_SPEED = "rel_rembo_network_send";
    public static final String REL_NETWORK_SPEEDS = "rel_rembo_network_speeds";
    public static final String REL_NETWORK_UNICAST_SPEED = "rel_rembo_network_recv_u";
    public static final String REL_REMBO_DISK_HANDLERS = "rel_rembo_disk_handlers";
    public static final String REL_REMBO_DISK_OPTIONS = "rel_rembo_disk_options";
    private List<Collection> collections;
    private Map<String, Column> columns;
    private Map<Column, String> reverseColumns;

    public List<Collection> getCollections() {
        return this.collections;
    }

    public String getColumnIs_unit(String _key) {
        Column column;
        String unit = null;
        if (this.getColumns() != null && this.getColumns().containsKey(_key) && (column = this.getColumns().get(_key)) != null) {
            unit = column.getIs_unit();
        }
        return unit;
    }

    public Map<String, Column> getColumns() {
        return this.columns;
    }

    public Map<Column, String> getReverseColumns() {
        return this.reverseColumns;
    }

    public void setCollections(List<Collection> _collections) {
        this.collections = _collections;
        if (this.collections == null) {
            return;
        }
        for (Collection collection : this.collections) {
            if ("ld_disk".equalsIgnoreCase(collection.getSid())) {
                collection.getEnable().add(REL_DISK_SPEED);
                collection.getEnable().add(REL_DISK_SPEEDS);
                collection.getEnable().add(REL_REMBO_DISK_OPTIONS);
                collection.getEnable().add(REL_REMBO_DISK_HANDLERS);
                continue;
            }
            if (!"ld_network".equalsIgnoreCase(collection.getSid())) continue;
            collection.getEnable().add(REL_NETWORK_UNICAST_SPEED);
            collection.getEnable().add(REL_NETWORK_MULTICAST_SPEED);
            collection.getEnable().add(REL_NETWORK_SEND_SPEED);
            collection.getEnable().add(REL_NETWORK_SPEEDS);
        }
    }

    public void setColumns(Map<String, Column> _columns) {
        this.columns = _columns;
        this.reverseColumns = new HashMap<Column, String>();
        if (this.columns == null) {
            return;
        }
        this.columns.putAll(MY_COLUMNS);
        for (Map.Entry<String, Column> entry : this.columns.entrySet()) {
            this.reverseColumns.put(entry.getValue(), entry.getKey());
        }
    }

    public void setReverseColumns(Map<Column, String> _reverse) {
        this.reverseColumns = _reverse;
    }

    static {
        Column column = null;
        column = new Column();
        column.setLabel("rel. Rembo DiskSpeed");
        column.setIs_unit("%");
        column.setUnit("%");
        MY_COLUMNS.put(REL_DISK_SPEED, column);
        column = new Column();
        column.setLabel("rel. Rembo DiskSpeeds");
        column.setType("int");
        MY_COLUMNS.put(REL_DISK_SPEEDS, column);
        column = new Column();
        column.setLabel("rel.  Rembo Download (Mul)");
        column.setIs_unit("%");
        column.setUnit("%");
        MY_COLUMNS.put(REL_NETWORK_MULTICAST_SPEED, column);
        column = new Column();
        column.setLabel("rel. Rembo Upload");
        column.setIs_unit("%");
        column.setUnit("%");
        MY_COLUMNS.put(REL_NETWORK_SEND_SPEED, column);
        column = new Column();
        column.setLabel("rel. Rembo NetSpeeds");
        column.setType("int");
        MY_COLUMNS.put(REL_NETWORK_SPEEDS, column);
        column = new Column();
        column.setLabel("rel. Rembo Download (Uni)");
        column.setIs_unit("%");
        column.setUnit("%");
        MY_COLUMNS.put(REL_NETWORK_UNICAST_SPEED, column);
        column = new Column();
        column.setLabel("rel. Rembo Disk-Handlers");
        MY_COLUMNS.put(REL_REMBO_DISK_HANDLERS, column);
        column = new Column();
        column.setLabel("rel. Rembo Disk-Options");
        MY_COLUMNS.put(REL_REMBO_DISK_OPTIONS, column);
    }

    public static class Column {
        private String is_unit;
        private String label;
        private String type;
        private String unit;

        public String getIs_unit() {
            return this.is_unit;
        }

        public String getLabel() {
            return this.label;
        }

        public String getType() {
            return this.type;
        }

        public String getUnit() {
            return this.unit;
        }

        public void setIs_unit(String _is_unit) {
            this.is_unit = _is_unit;
        }

        public void setLabel(String _label) {
            this.label = _label;
        }

        public void setType(String _type) {
            this.type = _type;
        }

        public void setUnit(String _unit) {
            this.unit = _unit;
        }
    }

    public static class Collection {
        private String defaultstate;
        private List<String> disable;
        private Set<String> disableSet;
        private List<String> enable;
        private Set<String> enableSet;
        private String label;
        private String sid;

        public String getDefaultstate() {
            return this.defaultstate;
        }

        public List<String> getDisable() {
            return this.disable;
        }

        Set<String> getDisableSet() {
            if (this.disableSet == null) {
                this.disableSet = new HashSet<String>();
                if (this.disable != null) {
                    this.disableSet.addAll(this.disable);
                }
            }
            return this.disableSet;
        }

        public List<String> getEnable() {
            return this.enable;
        }

        Set<String> getEnableSet() {
            if (this.enableSet == null) {
                this.enableSet = new HashSet<String>();
                if (this.enable != null) {
                    this.enableSet.addAll(this.enable);
                }
            }
            return this.enableSet;
        }

        public String getLabel() {
            return this.label;
        }

        public String getSid() {
            return this.sid;
        }

        public boolean isEnabled(String _key) {
            if (StringUtils.isEmptyString(this.defaultstate)) {
                if (CollectionUtils.isEmptyCollection(this.getEnableSet())) {
                    return false;
                }
                return this.getEnableSet().contains(_key);
            }
            if (this.getDefaultstate().matches("enable")) {
                if (CollectionUtils.isEmptyCollection(this.getDisableSet())) {
                    return true;
                }
                return !this.getDisableSet().contains(_key);
            }
            if (this.getDefaultstate().matches("disable")) {
                if (CollectionUtils.isEmptyCollection(this.getEnableSet())) {
                    return false;
                }
                return this.getEnableSet().contains(_key);
            }
            return false;
        }

        public void setDefaultstate(String _defaultstate) {
            this.defaultstate = _defaultstate;
        }

        public void setDisable(List<String> _disable) {
            this.disable = _disable;
        }

        public void setEnable(List<String> _enable) {
            this.enable = _enable;
        }

        public void setLabel(String _label) {
            this.label = _label;
        }

        public void setSid(String _sid) {
            this.sid = _sid;
        }
    }

}

