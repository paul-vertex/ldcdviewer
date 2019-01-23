/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.AbstractBean;

public class Domain
extends AbstractBean {
    private static final long serialVersionUID = -4886348852238359862L;
    private String domain;
    private long value;

    public String getDomain() {
        return this.domain;
    }

    public long getValue() {
        return this.value;
    }

    public void setDomain(String _domain) {
        this.domain = _domain;
    }

    public void setValue(long _value) {
        this.value = _value;
    }
}

