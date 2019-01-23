/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.ssm;

import de.sbe.ldc.domain.rpc.Impersonator;
import de.sbe.ldc.domain.rpc.RpcObject;
import de.sbe.ldc.domain.ssm.SsmEntry;
import de.sbe.ldc.persistence.morpher.RpcScope;
import de.sbe.ldc.persistence.morpher.SerializableProperty;
import java.util.ArrayList;
import java.util.Collection;

public class SsmAction
extends RpcObject {
    public static final String PROPERTYNAME_DESCRIPTION = "description";
    public static final String PROPERTYNAME_ENTRY = "entry";
    public static final String PROPERTYNAME_NOTIFICATION_TYPE = "notificationType";
    public static final String PROPERTYNAME_SUBJECT = "subject";
    public static final String SCOPE_DEFAULT = "default";
    private static final long serialVersionUID = 1L;
    @SerializableProperty
    @RpcScope(scope="default")
    private String description;
    private SsmEntry entry;
    @SerializableProperty
    @RpcScope(scope="default")
    private Collection<NotificationType> notificationType;
    @SerializableProperty
    @RpcScope(scope="default")
    private String subject;

    public SsmAction clone() {
        SsmAction clone = new SsmAction();
        clone.setCreatorId(this.getCreatorId());
        clone.setCreatorName(this.getCreatorName());
        clone.setDescription(this.getDescription());
        clone.setName(this.getName());
        clone.setNotificationType((Collection<NotificationType>)(this.getNotificationType() == null ? null : new ArrayList<NotificationType>(this.getNotificationType())));
        clone.setSubject(this.getSubject());
        return clone;
    }

    public String getDescription() {
        return this.description;
    }

    public SsmEntry getEntry() {
        return this.entry;
    }

    public Collection<NotificationType> getNotificationType() {
        if (this.notificationType == null) {
            this.notificationType = new ArrayList<NotificationType>();
        }
        return this.notificationType;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setDescription(String _content) {
        this.description = _content;
        this.firePropertyChange(PROPERTYNAME_DESCRIPTION, (Object)this.description, (Object)this.description);
    }

    public void setEntry(SsmEntry _entry) {
        this.entry = _entry;
        this.firePropertyChange(PROPERTYNAME_ENTRY, (Object)this.entry, (Object)this.entry);
    }

    public void setNotificationType(Collection<NotificationType> _notificationType) {
        this.notificationType = _notificationType;
        this.firePropertyChange(PROPERTYNAME_NOTIFICATION_TYPE, this.notificationType, this.notificationType);
    }

    public void setSubject(String _subject) {
        this.subject = _subject;
        this.firePropertyChange(PROPERTYNAME_SUBJECT, (Object)this.subject, (Object)this.subject);
    }

    public static enum NotificationType {
        EMAIL,
        FAX,
        SMS;
        
    }

}

