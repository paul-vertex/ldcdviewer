/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.Exam;
import de.sbe.ldc.domain.HostFlags;
import de.sbe.ldc.domain.Inventory;
import de.sbe.ldc.domain.Power;
import de.sbe.ldc.domain.User;
import de.sbe.ldc.domain.comparator.UserComparator;
import de.sbe.ldc.utils.logging.SetterLogger;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class HostInfo
extends HostFlags {
    public static final String PROPERTYNAME_AGENT_VERSION = "agentVersion";
    public static final String PROPERTYNAME_BEAM = "beam";
    public static final String PROPERTYNAME_EXAM = "exam";
    public static final String PROPERTYNAME_IDLETIME = "idletime";
    public static final String PROPERTYNAME_IP = "ip";
    public static final String PROPERTYNAME_OS_ARCH = "osArch";
    public static final String PROPERTYNAME_OS_NAME = "osName";
    public static final String PROPERTYNAME_OS_VERSION = "osVersion";
    public static final String PROPERTYNAME_POWER = "power";
    public static final String PROPERTYNAME_SSM = "ssm";
    public static final String PROPERTYNAME_UPTIME = "uptime";
    public static final String PROPERTYNAME_USERS = "users";
    private static final long serialVersionUID = 1L;
    private String agentVersion;
    private Object beam;
    private Exam exam;
    private Map<User, Long> idletime;
    private final Inventory inventory = new Inventory();
    private String ip;
    private String osArch;
    private String osName;
    private String osVersion;
    private Power power = Power.OFF;
    private int ssm;
    private long uptime;
    @SetterLogger
    private Set<User> users;

    HostInfo() {
    }

    public String getAgentVersion() {
        return this.agentVersion;
    }

    public Object getBeam() {
        return this.beam;
    }

    public Exam getExam() {
        return this.exam;
    }

    public Map<User, Long> getIdletime() {
        return this.idletime;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public String getIp() {
        return this.ip;
    }

    public String getOsArch() {
        return this.osArch;
    }

    public String getOsName() {
        return this.osName;
    }

    public String getOsVersion() {
        return this.osVersion;
    }

    public Power getPower() {
        return this.power;
    }

    public int getSsm() {
        return this.ssm;
    }

    public long getUptime() {
        return this.uptime;
    }

    public List<User> getUsers() {
        if (this.users == null) {
            return Collections.emptyList();
        }
        return Collections.list(Collections.enumeration(this.users));
    }

    public void setAgentVersion(String _agentVersion) {
        String old = this.agentVersion;
        this.agentVersion = _agentVersion;
        this.firePropertyChange(PROPERTYNAME_AGENT_VERSION, (Object)old, (Object)this.agentVersion);
    }

    public void setBeam(Object _beam) {
        this.beam = _beam;
        this.firePropertyChange(PROPERTYNAME_BEAM, this.beam, this.beam);
    }

    public void setExam(Exam _exam) {
        Exam old = this.exam;
        this.exam = _exam;
        this.firePropertyChange(PROPERTYNAME_EXAM, (Object)old, (Object)this.exam);
    }

    public void setIdletime(Map<User, Long> _idletime) {
        Map<User, Long> old = this.idletime;
        this.idletime = _idletime;
        this.firePropertyChange(PROPERTYNAME_IDLETIME, old, this.idletime);
    }

    public void setIp(String _ip) {
        String old = this.ip;
        this.ip = _ip;
        this.firePropertyChange(PROPERTYNAME_IP, (Object)old, (Object)this.ip);
    }

    public void setOsArch(String _osArch) {
        String old = this.osArch;
        this.osArch = _osArch;
        this.firePropertyChange(PROPERTYNAME_OS_ARCH, (Object)old, (Object)this.osArch);
    }

    public void setOsName(String _os) {
        String old = this.osName;
        this.osName = _os;
        this.firePropertyChange(PROPERTYNAME_OS_NAME, (Object)old, (Object)this.osName);
    }

    public void setOsVersion(String _osVersion) {
        String old = this.osVersion;
        this.osVersion = _osVersion;
        this.firePropertyChange(PROPERTYNAME_OS_VERSION, (Object)old, (Object)this.osVersion);
    }

    public void setPower(Power _state) {
        Power old = this.power;
        this.power = _state;
        this.firePropertyChange(PROPERTYNAME_POWER, (Object)old, (Object)this.power);
    }

    public void setSsm(int _ssm) {
        this.ssm = _ssm;
        this.firePropertyChange(PROPERTYNAME_SSM, this.ssm, this.ssm);
    }

    public void setUptime(long _uptime) {
        long old = this.uptime;
        this.uptime = _uptime;
        this.firePropertyChange(PROPERTYNAME_UPTIME, old, this.uptime);
    }

    public void setUsers(Collection<User> _users) {
        List<User> old = this.getUsers();
        if (_users == null) {
            this.users = null;
        } else {
            this.users = new TreeSet<User>(UserComparator.DISPLAYNAME);
            this.users.addAll(_users);
        }
        this.firePropertyChange(PROPERTYNAME_USERS, old, this.getUsers());
    }
}

