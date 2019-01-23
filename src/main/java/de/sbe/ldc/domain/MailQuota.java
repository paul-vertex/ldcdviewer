/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.JavaBean;
import de.sbe.ldc.domain.Quota;

public interface MailQuota
extends JavaBean {
    public static final String PROPERTYNAME_MAIL_QUOTA = "mailQuota";

    public Quota getMailQuota();

    public void setMailQuota(Quota var1);
}

