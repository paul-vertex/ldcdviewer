/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 */
package de.sbe.ldc.domain;

import com.google.gson.annotations.Expose;
import de.sbe.ldc.domain.AbstractBean;
import de.sbe.ldc.domain.Host;
import de.sbe.ldc.domain.HostFlags;
import de.sbe.ldc.domain.Room;
import de.sbe.ldc.domain.Switch;
import de.sbe.ldc.domain.User;
import de.sbe.ldc.utils.FormatUtils;
import de.sbe.utils.Settings;
import de.sbe.utils.StringUtils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Exam
extends AbstractBean {
    public static final String PROPERTYNAME_BEFORE = "before";
    public static final String PROPERTYNAME_CREATED = "created";
    public static final String PROPERTYNAME_EXPIRES = "expires";
    public static final String PROPERTYNAME_FLAGS = "flags";
    public static final String PROPERTYNAME_HOSTS = "hosts";
    public static final String PROPERTYNAME_ID = "id";
    public static final String PROPERTYNAME_NAME = "name";
    public static final String PROPERTYNAME_OWNER = "owner";
    public static final String PROPERTYNAME_ROOMS = "rooms";
    public static final String PROPERTYNAME_UPDATED = "updated";
    private static final long serialVersionUID = 124423614152133000L;
    private Before before;
    private Date created;
    @Expose
    private long expires;
    @Expose
    private HostFlags flags;
    @Expose
    private List<Host> hosts;
    @Expose
    private String id;
    private String name;
    private User owner;
    @Expose
    private List<Room> rooms;
    private Date updated;

    public Exam() {
        this.init();
    }

    public Before getBefore() {
        return this.before;
    }

    public Date getCreated() {
        return this.created;
    }

    public long getExpires() {
        return this.expires;
    }

    public HostFlags getFlags() {
        return this.flags;
    }

    public List<Host> getHosts() {
        return this.hosts;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public User getOwner() {
        return this.owner;
    }

    public List<Room> getRooms() {
        return this.rooms;
    }

    public Date getUpdated() {
        return this.updated;
    }

    private void init() {
        this.before = Before.NONE;
        this.created = Calendar.getInstance().getTime();
        this.expires = Settings.getLong("exam.default_expires_value");
        this.flags = new HostFlags();
        this.flags.setInternet(Switch.OFF);
        this.flags.setIntranet(Switch.OFF);
        this.flags.setPrinter(Switch.ON);
        this.flags.setSwap(Switch.OFF);
        this.flags.setUsb(Switch.OFF);
    }

    private void rebuildId() {
        this.id = (this.name == null ? "" : this.name) + "-" + (this.created == null ? "" : FormatUtils.yyyy_MM_dd.format(this.created));
    }

    public void revalidateId() {
        String current = this.id;
        if (StringUtils.isEmptyString(current)) {
            return;
        }
        int index = current.lastIndexOf("-" + FormatUtils.yyyy_MM_dd.format(this.created));
        this.name = index > -1 ? current.substring(0, index) : current;
    }

    public void setBefore(Before _before) {
        Before old = this.before;
        this.before = _before;
        this.firePropertyChange(PROPERTYNAME_BEFORE, (Object)old, (Object)this.before);
    }

    public void setCreated(Date _created) {
        Date old = this.created;
        this.created = _created;
        this.firePropertyChange(PROPERTYNAME_CREATED, (Object)old, (Object)this.created);
        this.rebuildId();
    }

    public void setExpires(long _end) {
        long old = this.expires;
        this.expires = _end;
        this.firePropertyChange(PROPERTYNAME_EXPIRES, old, this.expires);
    }

    public void setFlags(HostFlags _flags) {
        HostFlags old = this.flags;
        this.flags = _flags;
        this.firePropertyChange(PROPERTYNAME_FLAGS, (Object)old, (Object)this.flags);
    }

    public void setHosts(List<Host> _hosts) {
        List<Host> old = this.hosts;
        this.hosts = _hosts;
        this.firePropertyChange(PROPERTYNAME_HOSTS, old, this.hosts);
    }

    public void setId(String _id) {
        String old = this.id;
        this.id = _id;
        this.firePropertyChange(PROPERTYNAME_ID, (Object)old, (Object)this.id);
    }

    public void setName(String _name) {
        String old = this.name;
        this.name = _name;
        this.firePropertyChange(PROPERTYNAME_NAME, (Object)old, (Object)this.name);
        this.rebuildId();
    }

    public void setOwner(User _owner) {
        User old = this.owner;
        this.owner = _owner;
        this.firePropertyChange(PROPERTYNAME_OWNER, (Object)old, (Object)this.owner);
    }

    public void setRooms(List<Room> _rooms) {
        List<Room> old = this.rooms;
        this.rooms = _rooms;
        this.firePropertyChange(PROPERTYNAME_ROOMS, old, this.rooms);
    }

    public void setUpdated(Date _updated) {
        Date old = this.updated;
        this.updated = _updated;
        this.firePropertyChange(PROPERTYNAME_UPDATED, (Object)old, (Object)this.updated);
    }

    public static enum Before {
        LOGOFF,
        NONE,
        REBOOT;
        
    }

}

