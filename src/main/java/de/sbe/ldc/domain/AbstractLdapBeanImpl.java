/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.AbstractBean;
import de.sbe.ldc.domain.AbstractLdapBean;
import de.sbe.ldc.persistence.morpher.SerializableProperty;
import java.util.Date;

public class AbstractLdapBeanImpl
extends AbstractBean
implements AbstractLdapBean {
    private static final long serialVersionUID = 5324429725247841517L;
    @SerializableProperty(ldPrefix=true)
    private String comment;
    @SerializableProperty(ldPrefix=true, writable=false)
    private Date created;
    @SerializableProperty(ldPrefix=true, writable=false)
    private String creationMethod;
    @SerializableProperty(writable=false)
    private String dn;
    @SerializableProperty(ldPrefix=true, writable=false)
    private Date lastModified;
    @SerializableProperty(ldPrefix=true)
    private String objectType;

    @Override
    public String getComment() {
        return this.comment;
    }

    @Override
    public Date getCreated() {
        return this.created;
    }

    @Override
    public String getCreationMethod() {
        return this.creationMethod;
    }

    @Override
    public String getDn() {
        return this.dn;
    }

    @Override
    public Date getLastModified() {
        return this.lastModified;
    }

    @Override
    public String getObjectType() {
        return this.objectType;
    }

    @Override
    public void setComment(String _comment) {
        String old = this.comment;
        this.comment = _comment;
        this.firePropertyChange("comment", (Object)old, (Object)this.comment);
    }

    @Override
    public void setCreated(Date _created) {
        Date old = this.created;
        this.created = _created;
        this.firePropertyChange("created", (Object)old, (Object)this.created);
    }

    @Override
    public void setCreationMethod(String _creationMethod) {
        String old = this.creationMethod;
        this.creationMethod = _creationMethod;
        this.firePropertyChange("creationMethod", (Object)old, (Object)this.creationMethod);
    }

    @Override
    public void setDn(String _dn) {
        String old = this.dn;
        this.dn = _dn;
        this.firePropertyChange("dn", (Object)old, (Object)this.dn);
    }

    @Override
    public void setLastModified(Date _lastModified) {
        Date old = this.lastModified;
        this.lastModified = _lastModified;
        this.firePropertyChange("lastModified", (Object)old, (Object)this.lastModified);
    }

    @Override
    public void setObjectType(String _objectType) {
        String old = this.objectType;
        this.objectType = _objectType;
        this.firePropertyChange("objectType", (Object)old, (Object)this.objectType);
    }
}

