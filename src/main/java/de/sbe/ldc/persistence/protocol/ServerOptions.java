/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.persistence.protocol;

public class ServerOptions {
    private boolean serverProfilesAllowed = true;
    private boolean sslSupported;

    public boolean isServerProfilesAllowed() {
        return this.serverProfilesAllowed;
    }

    public boolean isSSLSupported() {
        return this.sslSupported;
    }

    public void setServerProfilesAllowed(boolean _serverProfilesAllowed) {
        this.serverProfilesAllowed = _serverProfilesAllowed;
    }

    public void setSSLSupported(boolean _sslSupported) {
        this.sslSupported = _sslSupported;
    }
}

