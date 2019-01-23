/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.AbstractBean;
import de.sbe.ldc.domain.Role;
import de.sbe.ldc.persistence.morpher.SerializableProperty;

public class RoleImpl
extends AbstractBean
implements Role {
    public static final Role LOCAL_USER_ROLE = new RoleImpl();
    private static final long serialVersionUID = 894776464139152950L;
    @SerializableProperty
    private String fullName;
    @SerializableProperty
    private String id;
    @SerializableProperty
    private String unixGroupName;

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
        RoleImpl other = (RoleImpl)obj;
        return !(this.id == null ? other.id != null : !this.id.equals(other.id));
    }

    @Override
    public String getFullName() {
        return this.fullName;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getUnixGroupName() {
        return this.unixGroupName;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.id == null ? 0 : this.id.hashCode());
        return result;
    }

    @Override
    public void setFullName(String _fullName) {
        String old = this.fullName;
        this.fullName = _fullName;
        this.firePropertyChange("fullName", (Object)old, (Object)this.fullName);
    }

    @Override
    public void setId(String _id) {
        String old = this.id;
        this.id = _id;
        this.firePropertyChange("id", (Object)old, (Object)this.id);
    }

    @Override
    public void setUnixGroupName(String _unixGroupName) {
        String old = this.unixGroupName;
        this.unixGroupName = _unixGroupName;
        this.firePropertyChange("unixGroupName", (Object)old, (Object)this.unixGroupName);
    }

    public String toString() {
        return this.id;
    }

    static {
        LOCAL_USER_ROLE.setId("local");
    }
}

