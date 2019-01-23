/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.GenderType;
import de.sbe.ldc.domain.JavaBean;

public interface Gender
extends JavaBean {
    public static final String PROPERTYNAME_GENDER = "gender";

    public GenderType getGender();

    public void setGender(GenderType var1);
}

