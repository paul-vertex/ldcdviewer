/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.JavaBean;
import de.sbe.ldc.domain.KopanoEntity;

public interface Kopano
extends JavaBean {
    public static final String PROPERTYNAME_KOPANO_ENTITY = "kopanoEntity";

    public KopanoEntity getKopanoEntity();

    public void setKopanoEntity(KopanoEntity var1);
}

