/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.AbstractLdapBeanImpl;
import de.sbe.ldc.domain.Clopen;
import de.sbe.ldc.domain.Group;
import de.sbe.ldc.domain.Quota;
import de.sbe.ldc.domain.User;
import de.sbe.ldc.persistence.morpher.SerializableProperty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class GroupImpl
extends AbstractLdapBeanImpl
implements Group {
    private static final long serialVersionUID = -1157631523179058741L;
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
    @SerializableProperty
    private String cn;
    @SerializableProperty(ldPrefix=true)
    private Quota diskQuota;
    private transient Set<User> member;
    @SerializableProperty(ldPrefix=true)
    private String objectSubType;
    @SerializableProperty(ldPrefix=true, writable=false)
    private User owner;
    @SerializableProperty(ldPrefix=true)
    private Clopen state = Clopen.OPEN;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void addMember(User _user) {
        this.getLock().lock();
        try {
            Set<User> current = this.getMemberDirectly();
            if (current.contains(_user)) {
                return;
            }
            ArrayList<User> oldMember = Collections.list(Collections.enumeration(current));
            current.add(_user);
            this.firePropertyChange("member", oldMember, this.getMember());
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
        GroupImpl other = (GroupImpl)obj;
        return !(this.cn == null ? other.cn != null : !this.cn.equals(other.cn));
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
    public String getCn() {
        return this.cn;
    }

    @Override
    public Quota getDiskQuota() {
        if (this.diskQuota == null) {
            this.diskQuota = new Quota();
        }
        return this.diskQuota;
    }

    @Override
    public List<User> getMember() {
        return Collections.list(Collections.enumeration(this.getMemberDirectly()));
    }

    @Override
    public List<User> getMember(Comparator<User> _comparator) {
        List<User> list = this.getMember();
        Collections.sort(list, _comparator);
        return list;
    }

    private Set<User> getMemberDirectly() {
        this.getLock().lock();
        try {
            if (this.member == null) {
                this.member = new HashSet<User>();
            }
        }
        finally {
            this.getLock().unlock();
        }
        return this.member;
    }

    @Override
    public String getObjectSubType() {
        return this.objectSubType;
    }

    @Override
    public User getOwner() {
        return this.owner;
    }

    @Override
    public Clopen getState() {
        return this.state;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.cn == null ? 0 : this.cn.hashCode());
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void removeMember(User _user) {
        this.getLock().lock();
        try {
            Set<User> current = this.getMemberDirectly();
            if (current.contains(_user)) {
                ArrayList<User> old = Collections.list(Collections.enumeration(current));
                current.remove(_user);
                this.firePropertyChange("member", old, this.getMember());
            }
        }
        finally {
            this.getLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void revalidateMember(List<User> _new) {
        HashSet<User> newMember = new HashSet<User>(_new == null ? User.EMPTY_USER_LIST : _new);
        HashSet<User> oldMember = new HashSet<User>(this.getMember());
        if (oldMember.isEmpty()) {
            for (User user : newMember) {
                user.addGroup(this);
            }
        } else {
            for (User user : oldMember) {
                if (newMember.contains(user)) continue;
                user.removeGroup(this);
            }
            for (User user : newMember) {
                if (oldMember.contains(user)) continue;
                user.addGroup(this);
            }
        }
        this.getLock().lock();
        try {
            this.member = newMember;
        }
        finally {
            this.getLock().unlock();
        }
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
    public void setCn(String _cn) {
        String old = this.cn;
        this.cn = _cn;
        this.firePropertyChange("cn", (Object)old, (Object)this.cn);
    }

    @Override
    public void setDiskQuota(Quota _diskQuota) {
        Quota old = this.diskQuota;
        this.diskQuota = _diskQuota;
        this.firePropertyChange("diskQuota", (Object)old, (Object)this.diskQuota);
    }

    @Override
    public void setMember(List<User> _member) {
        List<User> old = this.getMember();
        this.revalidateMember(_member);
        this.firePropertyChange("member", old, this.getMember());
    }

    @Override
    public void setObjectSubType(String _objectSubType) {
        String old = this.objectSubType;
        this.objectSubType = _objectSubType;
        this.firePropertyChange("objectSubType", (Object)old, (Object)this.objectSubType);
    }

    @Override
    public void setOwner(User _owner) {
        User old = _owner;
        this.owner = _owner;
        this.firePropertyChange("owner", (Object)old, (Object)this.getOwner());
    }

    @Override
    public void setState(Clopen _state) {
        Clopen old = this.state;
        this.state = _state;
        this.firePropertyChange("state", (Object)old, (Object)this.state);
    }

    public String toString() {
        return this.cn;
    }
}

