/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.JavaBean;
import de.sbe.ldc.domain.Option;

public interface VPN
extends JavaBean {
    public static final String PROPERTYNAME_ALLOW_VPN = "allowVPN";

    public Option getAllowVPN();

    public void setAllowVPN(Option var1);
}

