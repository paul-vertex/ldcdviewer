/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.JavaBean;
import de.sbe.ldc.domain.ZarafaEntity;

public interface Zarafa
extends JavaBean {
    public static final String PROPERTYNAME_ZARAFA_ENTITY = "zarafaEntity";

    public ZarafaEntity getZarafaEntity();

    public void setZarafaEntity(ZarafaEntity var1);
}

