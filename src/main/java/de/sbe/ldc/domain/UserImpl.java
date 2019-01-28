/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.AbstractLdapBeanImpl;
import de.sbe.ldc.domain.GenderType;
import de.sbe.ldc.domain.Group;
import de.sbe.ldc.domain.Option;
import de.sbe.ldc.domain.Pykota;
import de.sbe.ldc.domain.Quota;
import de.sbe.ldc.domain.Role;
import de.sbe.ldc.domain.User;
import de.sbe.ldc.domain.UserInfo;
import de.sbe.ldc.domain.ZarafaEntity;
import de.sbe.ldc.domain.filter.GroupFilter;
import de.sbe.ldc.persistence.morpher.SerializableProperty;
import de.sbe.utils.filter.Filters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class UserImpl
extends AbstractLdapBeanImpl
implements User {
    private static final long serialVersionUID = 6940792631055393126L;
    private static final Date ZERO_DATE = new Date(-3600000L);
    @SerializableProperty(writable=false)
    private int _dq_f_hard;
    @SerializableProperty(writable=false)
    private int _dq_f_soft;
    @SerializableProperty(writable=false)
    private int _dq_f_used;
    @SerializableProperty(writable=false)
    private int _dq_kb_hard;
    @SerializableProperty(writable=false)
    private int _dq_kb_soft;
    @SerializableProperty(writable=false)
    private int _dq_kb_used;
    @SerializableProperty(ldPrefix=true)
    private Option allowVPN;
    @SerializableProperty(ldPrefix=true)
    private Option allowWLAN;
    @SerializableProperty(ldPrefix=true)
    private Date birthday;
    @SerializableProperty
    private List<String> cn;
    @SerializableProperty(ldPrefix=true)
    private Quota diskQuota;
    @SerializableProperty
    private String displayName;
    @SerializableProperty(ldPrefix=true)
    private GenderType gender;
    @SerializableProperty
    private String givenname;
    @SerializableProperty
    private Set<Group> groups;
    @SerializableProperty(ldPrefix=true)
    private String initialPassword;
    @SerializableProperty
    private Collection<String> ldMailForward;
    @SerializableProperty
    private List<String> mail;
    @SerializableProperty(ldPrefix=true)
    private Quota mailQuota;
    @SerializableProperty(readable=false)
    private String password;
    private transient String passwordConfirm;
    @SerializableProperty
    private double pykotaBalance;
    @SerializableProperty
    private Pykota.PykotaLimitBy pykotaLimitBy;
    @SerializableProperty(ldPrefix=true)
    private Role role;
    @SerializableProperty
    private String sambaAcctFlags;
    @SerializableProperty
    private long sambaPwdCanChange;
    @SerializableProperty
    private long sambaPwdLastSet;
    @SerializableProperty
    private long sambaPwdMustChange;
    @SerializableProperty
    private Option serverProfile;
    @SerializableProperty
    private String sn;
    @SerializableProperty(ldPrefix=true)
    private String title;
    @SerializableProperty
    private String uid;
    @SerializableProperty(ldPrefix=true)
    private String uniqueid;
    private final UserInfo userInfo = new UserInfo();
    @SerializableProperty(ldPrefix=true)
    private ZarafaEntity zarafaEntity;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void addGroup(Group _group) {
        this.getLock().lock();
        try {
            Set<Group> current = this.getGroupsDirectly();
            if (current.contains(_group)) {
                return;
            }
            ArrayList<Group> oldGroups = Collections.list(Collections.enumeration(current));
            current.add(_group);
            this.firePropertyChange("groups", oldGroups, this.getGroups());
        }
        finally {
            this.getLock().unlock();
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        UserImpl other = (UserImpl)obj;
        return !(this.uid == null ? other.uid != null : !this.uid.equals(other.uid));
    }

    @Override
    public int get_dq_f_hard() {
        return this._dq_f_hard;
    }

    @Override
    public int get_dq_f_soft() {
        return this._dq_f_soft;
    }

    @Override
    public int get_dq_f_used() {
        return this._dq_f_used;
    }

    @Override
    public int get_dq_kb_hard() {
        return this._dq_kb_hard;
    }

    @Override
    public int get_dq_kb_soft() {
        return this._dq_kb_soft;
    }

    @Override
    public int get_dq_kb_used() {
        return this._dq_kb_used;
    }

    @Override
    public Option getAllowVPN() {
        return this.allowVPN;
    }

    @Override
    public Option getAllowWLAN() {
        return this.allowWLAN;
    }

    @Override
    public Date getBirthday() {
        return this.birthday == null ? new Date(0L) : (Date)this.birthday.clone();
    }

    @Override
    public List<Group> getClasses() {
        return Filters.filterAsList(this.getGroups(), GroupFilter.CLASSES);
    }

    @Override
    public List<String> getCn() {
        return this.cn;
    }

    @Override
    public List<Group> getCourses() {
        return Filters.filterAsList(this.getGroups(), GroupFilter.COURSES);
    }

    @Override
    public Quota getDiskQuota() {
        if (this.diskQuota == null) {
            this.diskQuota = new Quota();
        }
        return this.diskQuota;
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public GenderType getGender() {
        return this.gender;
    }

    @Override
    public String getGivenname() {
        return this.givenname;
    }

    @Override
    public List<Group> getGroups() {
        return Collections.list(Collections.enumeration(this.getGroupsDirectly()));
    }

    private Set<Group> getGroupsDirectly() {
        this.getLock().lock();
        try {
            if (this.groups == null) {
                this.groups = new HashSet<Group>();
            }
        }
        finally {
            this.getLock().unlock();
        }
        return this.groups;
    }

    @Override
    public Object getId() {
        return this.getUid();
    }

    @Override
    public String getInitialPassword() {
        return this.initialPassword;
    }

    public Collection<String> getLdMailForward() {
        if (this.ldMailForward == null) {
            this.ldMailForward = Collections.singletonList(this.getUid());
        }
        return this.ldMailForward;
    }

    @Override
    public List<String> getMail() {
        return this.mail;
    }

    @Override
    public Quota getMailQuota() {
        if (this.mailQuota == null) {
            this.mailQuota = new Quota();
        }
        return this.mailQuota;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getPasswordConfirm() {
        return this.passwordConfirm;
    }

    @Override
    public List<Group> getProjects() {
        return Filters.filterAsList(this.getGroups(), GroupFilter.PROJECTS);
    }

    @Override
    public double getPykotaBalance() {
        return this.pykotaBalance;
    }

    @Override
    public Pykota.PykotaLimitBy getPykotaLimitBy() {
        return this.pykotaLimitBy;
    }

    @Override
    public Role getRole() {
        return this.role;
    }

    @Override
    public String getSambaAcctFlags() {
        return this.sambaAcctFlags;
    }

    @Override
    public long getSambaPwdCanChange() {
        return this.sambaPwdCanChange;
    }

    @Override
    public long getSambaPwdLastSet() {
        return this.sambaPwdLastSet;
    }

    @Override
    public long getSambaPwdMustChange() {
        return this.sambaPwdMustChange;
    }

    @Override
    public Option getServerProfile() {
        return this.serverProfile;
    }

    @Override
    public String getSn() {
        return this.sn;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getUid() {
        return this.uid;
    }

    @Override
    public String getUniqueid() {
        return this.uniqueid;
    }

    @Override
    public UserInfo getUserInfo() {
        return this.userInfo;
    }

    @Override
    public ZarafaEntity getZarafaEntity() {
        return this.zarafaEntity == null ? ZarafaEntity.NONE : this.zarafaEntity;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.uid == null ? 0 : this.uid.hashCode());
        return result;
    }

    @Override
    public boolean isAccountAutoLocked() {
        return this.sambaAcctFlags == null ? false : this.sambaAcctFlags.toUpperCase().matches(".*L.*");
    }

    @Override
    public boolean isAccountDisabled() {
        return this.sambaAcctFlags == null ? false : this.sambaAcctFlags.toUpperCase().matches(".*D.*");
    }

    @Override
    public boolean isPwdCannotChange() {
        Date can = new Date(this.sambaPwdCanChange * 1000L);
        Date last = new Date(this.getSambaPwdLastSet() * 1000L);
        return (can.equals(last) || can.after(last)) && Calendar.getInstance().getTime().before(can);
    }

    @Override
    public boolean isPwdMustChange() {
        Date must = new Date(this.sambaPwdMustChange * 1000L);
        Date last = new Date(this.getSambaPwdLastSet() * 1000L);
        return (must.equals(last) || must.after(last)) && Calendar.getInstance().getTime().after(must);
    }

    @Override
    public boolean isPwdNeverExpires() {
        return this.sambaAcctFlags == null ? false : this.sambaAcctFlags.toUpperCase().matches(".*X.*");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void removeGroup(Group _group) {
        this.getLock().lock();
        try {
            Set<Group> current = this.getGroupsDirectly();
            if (current.contains(_group)) {
                ArrayList<Group> oldGroups = Collections.list(Collections.enumeration(current));
                current.remove(_group);
                this.firePropertyChange("groups", oldGroups, this.getGroups());
            }
        }
        finally {
            this.getLock().unlock();
        }
    }

    private void revalidateDisplayname() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.givenname == null ? "" : this.givenname);
        builder.append(builder.length() == 0 ? "" : " ");
        builder.append(this.sn == null ? "" : this.sn);
        this.setDisplayName(builder.toString());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void revalidateGroups(List<Group> _old, List<Group> _new) {
        HashSet<Group> allGroups = new HashSet<Group>(this.getGroupsDirectly());
        HashSet<Group> newGroups = new HashSet<Group>(_new == null ? Group.EMPTY_GROUP_LIST : _new);
        HashSet<Group> oldGroups = new HashSet<Group>(_old == null ? Group.EMPTY_GROUP_LIST : _old);
        for (Group group : oldGroups) {
            if (newGroups.contains(group)) continue;
            allGroups.remove(group);
            group.removeMember(this);
        }
        for (Group group : newGroups) {
            if (oldGroups.contains(group)) continue;
            allGroups.add(group);
            group.addMember(this);
        }
        this.getLock().lock();
        try {
            this.groups = allGroups;
        }
        finally {
            this.getLock().unlock();
        }
    }

    private void revalidateSambaAcctFlags(boolean _accountAutoLocked, boolean _accountDisabled, boolean _pwdNeverExpires) {
        String newSambaAcctFlags = this.sambaAcctFlags;
        if (newSambaAcctFlags == null) {
            newSambaAcctFlags = "";
        }
        newSambaAcctFlags = newSambaAcctFlags.toUpperCase();
        newSambaAcctFlags = newSambaAcctFlags.replaceAll("[^A-Z]", "");
        newSambaAcctFlags = newSambaAcctFlags.replaceAll("L", "");
        newSambaAcctFlags = newSambaAcctFlags.replaceAll("D", "");
        newSambaAcctFlags = newSambaAcctFlags.replaceAll("X", "");
        if (_accountAutoLocked) {
            newSambaAcctFlags = newSambaAcctFlags + "L";
        }
        if (_accountDisabled) {
            newSambaAcctFlags = newSambaAcctFlags + "D";
        }
        if (_pwdNeverExpires) {
            newSambaAcctFlags = newSambaAcctFlags + "X";
        }
        if (newSambaAcctFlags.length() > 0) {
            while (newSambaAcctFlags.length() < 11) {
                newSambaAcctFlags = newSambaAcctFlags + " ";
            }
            newSambaAcctFlags = "[" + newSambaAcctFlags;
            newSambaAcctFlags = newSambaAcctFlags + "]";
        }
        String old = this.sambaAcctFlags;
        this.sambaAcctFlags = newSambaAcctFlags;
        this.firePropertyChange("sambaAcctFlags", (Object)old, (Object)this.sambaAcctFlags);
    }

    @Override
    public void set_dq_f_hard(int __dq_f_hard) {
        this._dq_f_hard = __dq_f_hard;
    }

    @Override
    public void set_dq_f_soft(int __dq_f_soft) {
        this._dq_f_soft = __dq_f_soft;
    }

    @Override
    public void set_dq_f_used(int __dq_f_used) {
        this._dq_f_used = __dq_f_used;
    }

    @Override
    public void set_dq_kb_hard(int __dq_kb_hard) {
        this._dq_kb_hard = __dq_kb_hard;
    }

    @Override
    public void set_dq_kb_soft(int __dq_kb_soft) {
        this._dq_kb_soft = __dq_kb_soft;
    }

    @Override
    public void set_dq_kb_used(int __dq_kb_used) {
        this._dq_kb_used = __dq_kb_used;
    }

    @Override
    public void setAccountAutoLocked(boolean _accountAutoLocked) {
        boolean old = this.isAccountAutoLocked();
        this.revalidateSambaAcctFlags(_accountAutoLocked, this.isAccountDisabled(), this.isPwdNeverExpires());
        this.firePropertyChange("accountAutoLocked", old, this.isAccountAutoLocked());
    }

    @Override
    public void setAccountDisabled(boolean _accountDisabled) {
        boolean old = this.isAccountDisabled();
        this.revalidateSambaAcctFlags(this.isAccountAutoLocked(), _accountDisabled, this.isPwdNeverExpires());
        this.firePropertyChange("accountDisabled", old, this.isAccountDisabled());
    }

    @Override
    public void setAllowVPN(Option _allowVPN) {
        Option old = this.allowVPN;
        this.allowVPN = _allowVPN;
        this.firePropertyChange("allowVPN", (Object)old, (Object)this.allowVPN);
    }

    @Override
    public void setAllowWLAN(Option _allowWLAN) {
        this.allowWLAN = _allowWLAN;
        this.firePropertyChange("allowWLAN", (Object)this.allowWLAN, (Object)this.allowWLAN);
    }

    @Override
    public void setBirthday(Date _birthday) {
        if (this.birthday == null && ZERO_DATE.equals(_birthday)) {
            return;
        }
        Date old = this.birthday;
        this.birthday = _birthday != null ? (Date)_birthday.clone() : null;
        this.firePropertyChange("birthday", (Object)old, (Object)this.birthday);
    }

    @Override
    public void setClasses(List<Group> _classes) {
        List<Group> oldClasses = this.getClasses();
        List<Group> oldGroups = this.getGroups();
        this.revalidateGroups(oldClasses, _classes);
        this.firePropertyChange("classes", oldClasses, this.getClasses());
        this.firePropertyChange("groups", oldGroups, this.getGroups());
    }

    @Override
    public void setCn(List<String> _cn) {
        List<String> old = this.cn;
        this.cn = _cn;
        this.firePropertyChange("cn", old, this.cn);
    }

    @Override
    public void setCourses(List<Group> _courses) {
        List<Group> oldCourses = this.getCourses();
        List<Group> oldGroups = this.getGroups();
        this.revalidateGroups(oldCourses, _courses);
        this.firePropertyChange("courses", oldCourses, this.getCourses());
        this.firePropertyChange("groups", oldGroups, this.getGroups());
    }

    @Override
    public void setDiskQuota(Quota _diskQuota) {
        Quota old = this.diskQuota;
        this.diskQuota = _diskQuota;
        this.firePropertyChange("diskQuota", (Object)old, (Object)this.diskQuota);
    }

    @Override
    public void setDisplayName(String _displayName) {
        String old = this.displayName;
        this.displayName = _displayName;
        this.firePropertyChange("displayName", (Object)old, (Object)this.displayName);
    }

    @Override
    public void setGender(GenderType _gender) {
        GenderType old = this.gender;
        this.gender = _gender;
        this.firePropertyChange("gender", (Object)old, (Object)this.gender);
    }

    @Override
    public void setGivenname(String _givenname) {
        String old = this.givenname;
        this.givenname = _givenname;
        this.firePropertyChange("givenname", (Object)old, (Object)this.givenname);
        this.revalidateDisplayname();
    }

    @Override
    public void setGroups(List<Group> _groups) {
        List<Group> oldClasses = this.getClasses();
        List<Group> oldGroups = this.getGroups();
        List<Group> oldProjects = this.getProjects();
        this.revalidateGroups(oldGroups, _groups);
        this.firePropertyChange("classes", oldClasses, this.getClasses());
        this.firePropertyChange("groups", oldGroups, this.getGroups());
        this.firePropertyChange("projects", oldProjects, this.getProjects());
    }

    @Override
    public void setInitialPassword(String _initialPassword) {
        String old = this.initialPassword;
        this.initialPassword = _initialPassword;
        this.firePropertyChange("initialPassword", (Object)old, (Object)this.initialPassword);
    }

    public void setLdMailForward(Collection<String> _ldMailForward) {
        this.ldMailForward = _ldMailForward;
        this.firePropertyChange("ldMailForward", this.ldMailForward, this.ldMailForward);
    }

    @Override
    public void setMail(List<String> _mail) {
        List<String> old = this.mail;
        this.mail = _mail;
        this.firePropertyChange("mail", old, this.mail);
    }

    @Override
    public void setMailQuota(Quota _mailQuota) {
        Quota old = this.mailQuota;
        this.mailQuota = _mailQuota;
        this.firePropertyChange("mailQuota", (Object)old, (Object)this.mailQuota);
    }

    @Override
    public void setPassword(String _password) {
        String old = this.password;
        this.password = _password;
        this.firePropertyChange("password", (Object)old, (Object)this.password);
        this.setSambaPwdLastSet(System.currentTimeMillis() / 1000L);
    }

    @Override
    public void setPasswordConfirm(String _passwordConfirm) {
        String old = this.passwordConfirm;
        this.passwordConfirm = _passwordConfirm;
        this.firePropertyChange("passwordConfirm", (Object)old, (Object)this.passwordConfirm);
    }

    @Override
    public void setProjects(List<Group> _projects) {
        List<Group> oldGroups = this.getGroups();
        List<Group> oldProjects = this.getProjects();
        this.revalidateGroups(this.getProjects(), _projects);
        this.firePropertyChange("groups", oldGroups, this.getGroups());
        this.firePropertyChange("projects", oldProjects, this.getProjects());
    }

    @Override
    public void setPwdCannotChange(boolean _pwdCannotChange) {
        boolean oldPwdCannotChange = this.isPwdCannotChange();
        long oldSambaPwdCanChange = this.sambaPwdCanChange;
        this.sambaPwdCanChange = _pwdCannotChange ? Integer.MAX_VALUE : 0L;
        this.firePropertyChange("pwdCannotChange", oldPwdCannotChange, this.isPwdCannotChange());
        this.firePropertyChange("sambaPwdCanChange", oldSambaPwdCanChange, this.sambaPwdCanChange);
    }

    @Override
    public void setPwdMustChange(boolean _pwdMustChange) {
        boolean old = this.isPwdMustChange();
        if (_pwdMustChange) {
            this.setSambaPwdLastSet(0L);
            this.setSambaPwdMustChange(0L);
        } else {
            if (this.getSambaPwdLastSet() == 0L) {
                this.setSambaPwdLastSet(1L);
            }
            this.setSambaPwdMustChange(Integer.MAX_VALUE);
        }
        this.firePropertyChange("pwdMustChange", old, this.isPwdMustChange());
    }

    @Override
    public void setPwdNeverExpires(boolean _pwdNeverExpires) {
        boolean old = this.isPwdNeverExpires();
        this.revalidateSambaAcctFlags(this.isAccountAutoLocked(), this.isAccountDisabled(), _pwdNeverExpires);
        this.firePropertyChange("pwdNeverExpires", old, this.isPwdNeverExpires());
    }

    @Override
    public void setPykotaBalance(double _pykotaBalance) {
        double old = this.pykotaBalance;
        this.pykotaBalance = _pykotaBalance;
        this.firePropertyChange("pykotaBalance", old, this.pykotaBalance);
    }

    @Override
    public void setPykotaLimitBy(Pykota.PykotaLimitBy _pykotaLimitBy) {
        Pykota.PykotaLimitBy old = this.pykotaLimitBy;
        this.pykotaLimitBy = _pykotaLimitBy;
        this.firePropertyChange("pykotaLimitBy", (Object)old, (Object)this.pykotaLimitBy);
    }

    @Override
    public void setRole(Role _role) {
        Role old = this.role;
        this.role = _role;
        this.firePropertyChange("role", (Object)old, (Object)this.role);
    }

    @Override
    public void setSambaAcctFlags(String _sambaAcctFlags) {
        boolean oldAccountAutoLocked = this.isAccountAutoLocked();
        boolean oldAccountDisabled = this.isAccountDisabled();
        boolean oldPwdNeverExpires = this.isPwdNeverExpires();
        String oldSambaAcctFlags = this.sambaAcctFlags;
        this.sambaAcctFlags = _sambaAcctFlags;
        this.firePropertyChange("accountAutoLocked", oldAccountAutoLocked, this.isAccountAutoLocked());
        this.firePropertyChange("accountDisabled", oldAccountDisabled, this.isAccountDisabled());
        this.firePropertyChange("pwdNeverExpires", oldPwdNeverExpires, this.isPwdNeverExpires());
        this.firePropertyChange("sambaAcctFlags", (Object)oldSambaAcctFlags, (Object)this.sambaAcctFlags);
    }

    @Override
    public void setSambaPwdCanChange(long _sambaPwdCanChange) {
        boolean oldPwdCannotChange = this.isPwdCannotChange();
        long oldSambaPwdCanChange = this.sambaPwdCanChange;
        this.sambaPwdCanChange = _sambaPwdCanChange;
        this.firePropertyChange("pwdCannotChange", oldPwdCannotChange, this.isPwdCannotChange());
        this.firePropertyChange("sambaPwdCanChange", oldSambaPwdCanChange, this.sambaPwdCanChange);
    }

    @Override
    public void setSambaPwdLastSet(long _sambaPwdLastSet) {
        long old = this.sambaPwdLastSet;
        this.sambaPwdLastSet = _sambaPwdLastSet;
        this.firePropertyChange("sambaPwdLastSet", old, this.sambaPwdLastSet);
    }

    @Override
    public void setSambaPwdMustChange(long _sambaPwdMustChange) {
        boolean oldPwdMustChange = this.isPwdMustChange();
        long oldSambaPwdMustChange = this.sambaPwdMustChange;
        this.sambaPwdMustChange = _sambaPwdMustChange;
        this.firePropertyChange("sambaPwdMustChange", oldSambaPwdMustChange, this.sambaPwdMustChange);
        this.firePropertyChange("pwdMustChange", oldPwdMustChange, this.isPwdMustChange());
    }

    @Override
    public void setServerProfile(Option _serverProfile) {
        this.serverProfile = _serverProfile;
        this.firePropertyChange("serverProfile", (Object)this.serverProfile, (Object)this.serverProfile);
    }

    @Override
    public void setSn(String _sn) {
        String old = this.sn;
        this.sn = _sn;
        this.firePropertyChange("sn", (Object)old, (Object)this.sn);
        this.revalidateDisplayname();
    }

    @Override
    public void setTitle(String _title) {
        String old = this.title;
        this.title = _title;
        this.firePropertyChange("title", (Object)old, (Object)this.title);
    }

    @Override
    public void setUid(String _uid) {
        String old = this.uid;
        this.uid = _uid;
        this.firePropertyChange("uid", (Object)old, (Object)this.uid);
    }

    @Override
    public void setUniqueid(String _uniqueid) {
        this.uniqueid = _uniqueid;
    }

    @Override
    public void setZarafaEntity(ZarafaEntity _zarafaEntity) {
        this.zarafaEntity = _zarafaEntity;
        this.firePropertyChange("zarafaEntity", (Object)this.zarafaEntity, (Object)this.zarafaEntity);
    }

    public String toString() {
        return this.uid;
    }
}

