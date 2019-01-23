/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.AbstractMySHNModel;

public class HostFormatModel
extends AbstractMySHNModel {
    public static final String PROPERTYNAME_FULL_RESTORE = "fullRestore";
    public static final String PROPERTYNAME_PURGE_CACHE = "purgeCache";
    private static final long serialVersionUID = -8245425147934327538L;
    private boolean fullRestore = true;
    private boolean purgeCache = false;

    public boolean isFullRestore() {
        return this.fullRestore;
    }

    public boolean isPurgeCache() {
        return this.purgeCache;
    }

    public void setFullRestore(boolean _fullRestore) {
        boolean old = this.fullRestore;
        this.fullRestore = _fullRestore;
        this.firePropertyChange(PROPERTYNAME_FULL_RESTORE, old, this.fullRestore);
    }

    public void setPurgeCache(boolean _purgeCache) {
        boolean old = this.purgeCache;
        this.purgeCache = _purgeCache;
        this.firePropertyChange(PROPERTYNAME_PURGE_CACHE, old, this.purgeCache);
    }
}

