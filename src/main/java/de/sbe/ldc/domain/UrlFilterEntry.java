/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.AbstractBean;
import de.sbe.ldc.domain.User;

public class UrlFilterEntry
extends AbstractBean {
    private static final long serialVersionUID = -1417995041855060907L;
    private long created;
    private long expires;
    private User owner;
    private String url;

    public long getCreated() {
        return this.created;
    }

    public long getExpires() {
        return this.expires;
    }

    public User getOwner() {
        return this.owner;
    }

    public String getUrl() {
        return this.url;
    }

    public void setCreated(long _created) {
        this.created = _created;
    }

    public void setExpires(long _expires) {
        this.expires = _expires;
    }

    public void setOwner(User _owner) {
        this.owner = _owner;
    }

    public void setUrl(String _url) {
        this.url = _url;
    }
}

