/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.SerializedName
 */
package de.sbe.ldc.domain;

import com.google.gson.annotations.SerializedName;
import de.sbe.ldc.domain.AbstractBean;

public final class DistributeInfo
extends AbstractBean {
    public static final String PROPERTYNAME_SIZE_HARDLIMIT = "hardLimit";
    public static final String PROPERTYNAME_SIZE_SOFTLIMIT = "softLimit";
    private static final long serialVersionUID = -8420729824113203716L;
    @SerializedName(value="size_hardlimit")
    private long hardLimit;
    @SerializedName(value="size_softlimit")
    private long softLimit;

    public long getHardLimit() {
        return this.hardLimit;
    }

    public long getSoftLimit() {
        return this.softLimit;
    }

    public void setHardLimit(long _hardLimit) {
        long old = this.hardLimit;
        this.hardLimit = _hardLimit;
        this.firePropertyChange(PROPERTYNAME_SIZE_HARDLIMIT, old, this.hardLimit);
    }

    public void setSoftLimit(long _softLimit) {
        long old = this.softLimit;
        this.softLimit = _softLimit;
        this.firePropertyChange(PROPERTYNAME_SIZE_SOFTLIMIT, old, this.softLimit);
    }
}

