/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.JavaBean;

public interface Role
extends JavaBean {
    public static final String PROPERTYNAME_FULL_NAME = "fullName";
    public static final String PROPERTYNAME_ID = "id";
    public static final String PROPERTYNAME_UNIX_GROUP_NAME = "unixGroupName";

    public String getFullName();

    public String getId();

    public String getUnixGroupName();

    public void setFullName(String var1);

    public void setId(String var1);

    public void setUnixGroupName(String var1);
}

