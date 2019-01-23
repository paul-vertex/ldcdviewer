/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.AbstractBean;

public class DistributeActivateOption
extends AbstractBean {
    public static final String PROPERTYNAME_ALL = "all";
    public static final String PROPERTYNAME_BACKUP = "backup";
    public static final String PROPERTYNAME_TYPE = "type";
    private static final long serialVersionUID = 5767249937992250000L;
    private boolean all;
    private boolean backup = true;
    private Type type;

    public Type getType() {
        return this.type;
    }

    public boolean isAll() {
        return this.all;
    }

    public boolean isBackup() {
        return this.backup;
    }

    public void setAll(boolean _all) {
        boolean old = this.all;
        this.all = _all;
        this.firePropertyChange(PROPERTYNAME_ALL, old, this.all);
    }

    public void setBackup(boolean _backup) {
        boolean old = this.backup;
        this.backup = _backup;
        this.firePropertyChange(PROPERTYNAME_BACKUP, old, this.backup);
    }

    public void setType(Type _type) {
        Type old = this.type;
        this.type = _type;
        this.firePropertyChange(PROPERTYNAME_TYPE, (Object)old, (Object)this.type);
    }

    public static enum Type {
        ABORT,
        OVERWRITE,
        SKIP;
        
    }

}

