/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.AbstractBean;

public class UserInfo
extends AbstractBean {
    public static final String PROPERTYNAME_SSM = "ssm";
    private static final long serialVersionUID = 1L;
    private int ssm;

    public int getSsm() {
        return this.ssm;
    }

    public void setSsm(int _ssm) {
        this.ssm = _ssm;
        this.firePropertyChange(PROPERTYNAME_SSM, this.ssm, this.ssm);
    }
}

