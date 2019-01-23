/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.AbstractMySHNModel;

public class HostMemtestModel
extends AbstractMySHNModel {
    public static final String PROPERTYNAME_MEMTEST = "memtest";
    private static final long serialVersionUID = 7374587986379334485L;
    private boolean memtest = true;

    public boolean isMemtest() {
        return this.memtest;
    }

    public void setMemtest(boolean _memtest) {
        boolean old = this.isMemtest();
        this.memtest = _memtest;
        this.firePropertyChange(PROPERTYNAME_MEMTEST, old, this.isMemtest());
    }
}

