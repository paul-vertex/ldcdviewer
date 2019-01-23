/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.rpc;

import de.sbe.ldc.domain.AbstractBean;
import de.sbe.ldc.domain.rpc.Impersonator;
import de.sbe.ldc.persistence.morpher.RpcScope;
import de.sbe.ldc.persistence.morpher.SerializableProperty;

public abstract class RpcObject
extends AbstractBean {
    public static final String PROPERTYNAME_CREATOR_ID = "creatorId";
    public static final String PROPERTYNAME_CREATOR_NAME = "creatorName";
    public static final String PROPERTYNAME_ID = "id";
    public static final String PROPERTYNAME_MEMBER_OF = "memberOf";
    public static final String PROPERTYNAME_MEMBERS = "members";
    public static final String PROPERTYNAME_NAME = "name";
    public static final String PROPERTYNAME_PARENT_ID = "parentId";
    public static final String PROPERTYNAME_TS_CREATED = "tsCreated";
    public static final String PROPERTYNAME_TS_UPDATED = "tsUpdated";
    private static final long serialVersionUID = 1L;
    private Long creatorId;
    @SerializableProperty
    @RpcScope
    private Impersonator creatorName;
    @SerializableProperty(writable=false)
    @RpcScope
    private Long id;
    private Long[] memberOf;
    private Long[] members;
    private String name;
    @SerializableProperty
    @RpcScope
    private Long parentId;
    @SerializableProperty
    @RpcScope
    private long tsCreated;
    @SerializableProperty
    @RpcScope
    private long tsUpdated;

    public RpcObject() {
        this.tsUpdated = this.tsCreated = System.currentTimeMillis();
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
        RpcObject other = (RpcObject)obj;
        return !(this.id == null ? other.id != null : !this.id.equals(other.id));
    }

    public Long getCreatorId() {
        return this.creatorId;
    }

    public Impersonator getCreatorName() {
        return this.creatorName;
    }

    public Long getId() {
        return this.id;
    }

    public Long[] getMemberOf() {
        return this.memberOf;
    }

    public Long[] getMembers() {
        return this.members;
    }

    public String getName() {
        return this.name;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public long getTsCreated() {
        return this.tsCreated;
    }

    public long getTsUpdated() {
        return this.tsUpdated;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.id == null ? 0 : this.id.hashCode());
        return result;
    }

    public void setCreatorId(Long _creatorId) {
        this.creatorId = _creatorId;
        this.firePropertyChange(PROPERTYNAME_CREATOR_ID, (Object)this.creatorId, (Object)this.creatorId);
    }

    public void setCreatorName(Impersonator _creatorName) {
        this.creatorName = _creatorName;
        this.firePropertyChange(PROPERTYNAME_CREATOR_NAME, (Object)this.creatorName, (Object)this.creatorName);
    }

    public void setId(Long _id) {
        this.id = _id;
        this.firePropertyChange(PROPERTYNAME_ID, (Object)this.id, (Object)this.id);
    }

    public void setMemberOf(Long[] _memberOf) {
        this.memberOf = _memberOf;
        this.firePropertyChange(PROPERTYNAME_MEMBER_OF, (Object)this.memberOf, (Object)this.memberOf);
    }

    public void setMembers(Long[] _members) {
        this.members = _members;
        this.firePropertyChange(PROPERTYNAME_MEMBERS, (Object)this.members, (Object)this.members);
    }

    public void setName(String _name) {
        this.name = _name;
        this.firePropertyChange(PROPERTYNAME_NAME, (Object)this.name, (Object)this.name);
    }

    public void setParentId(Long _parentId) {
        this.parentId = _parentId;
        this.firePropertyChange(PROPERTYNAME_PARENT_ID, (Object)this.parentId, (Object)this.parentId);
    }

    public void setTsCreated(long _tsCreated) {
        this.tsCreated = _tsCreated;
        this.firePropertyChange(PROPERTYNAME_TS_CREATED, this.tsCreated, this.tsCreated);
    }

    public void setTsUpdated(long _tsUpdated) {
        this.tsUpdated = _tsUpdated;
        this.firePropertyChange(PROPERTYNAME_TS_UPDATED, this.tsUpdated, this.tsUpdated);
    }
}

