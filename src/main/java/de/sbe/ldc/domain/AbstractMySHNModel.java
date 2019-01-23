/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.AbstractBean;

public abstract class AbstractMySHNModel
extends AbstractBean {
    public static final String PROPERTYNAME_EXECUTE_IMMEDIATELY = "executeImmediately";
    private static final long serialVersionUID = -4033739986692338485L;
    private transient boolean executeImmediately = true;

    public boolean isExecuteImmediately() {
        return this.executeImmediately;
    }

    public void setExecuteImmediately(boolean _executeImmediately) {
        boolean old = this.isExecuteImmediately();
        this.executeImmediately = _executeImmediately;
        this.firePropertyChange(PROPERTYNAME_EXECUTE_IMMEDIATELY, old, this.isExecuteImmediately());
    }
}

