/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.JavaBean;
import de.sbe.ldc.domain.Option;

public interface WLAN
extends JavaBean {
    public static final String PROPERTYNAME_ALLOW_WLAN = "allowWLAN";

    public Option getAllowWLAN();

    public void setAllowWLAN(Option var1);
}

