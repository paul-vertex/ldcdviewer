/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.AbstractBean;

public class CharsetDetector
extends AbstractBean {
    public static final String PROPERTYNAME_CONFIDENCE = "confidence";
    public static final String PROPERTYNAME_ENCODING = "encoding";
    private static final long serialVersionUID = 8332651275009655214L;
    private float confidence;
    private String encoding;

    public float getConfidence() {
        return this.confidence;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void setConfidence(float _confidence) {
        float old = this.confidence;
        this.confidence = _confidence;
        this.firePropertyChange(PROPERTYNAME_CONFIDENCE, old, this.confidence);
    }

    public void setEncoding(String _encoding) {
        String old = this.encoding;
        this.encoding = _encoding;
        this.firePropertyChange(PROPERTYNAME_ENCODING, (Object)old, (Object)this.encoding);
    }
}

