/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.JavaBean;

public interface Pykota
extends JavaBean {
    public static final String PROPERTYNAME_PYKOTA_BALANCE = "pykotaBalance";
    public static final String PROPERTYNAME_PYKOTA_LIMIT_BY = "pykotaLimitBy";

    public double getPykotaBalance();

    public PykotaLimitBy getPykotaLimitBy();

    public void setPykotaBalance(double var1);

    public void setPykotaLimitBy(PykotaLimitBy var1);

    public static enum PykotaLimitBy {
        BALANCE,
        NOCHANGE,
        NOPRINT;
        
        public static final PykotaLimitBy DEFAULT;

        static {
            DEFAULT = NOCHANGE;
        }
    }

}

