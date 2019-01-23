/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.AbstractBean;

public final class Server
extends AbstractBean {
    private static final long serialVersionUID = 1262955349544322005L;
    private final String hostname;
    private final String ip;

    public Server(String _hostname, String _ip) {
        this.hostname = _hostname;
        this.ip = _ip;
    }

    public boolean equals(Object _obj) {
        boolean equal = false;
        if (_obj != null && _obj instanceof Server && this.getIp() != null && ((Server)_obj).getIp() != null) {
            equal = this.getIp().equals(((Server)_obj).getIp());
        }
        return equal;
    }

    public String getHostname() {
        return this.hostname;
    }

    public String getIp() {
        return this.ip;
    }

    public int hashCode() {
        return this.getIp() != null ? this.getIp().hashCode() : super.hashCode();
    }

    public String toString() {
        return this.hostname != null ? this.hostname + " (" + this.ip + ")" : this.ip;
    }
}

