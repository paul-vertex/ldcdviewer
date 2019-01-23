/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.Host;
import de.sbe.ldc.domain.HostChooser;
import de.sbe.utils.Settings;
import java.util.List;

public class PowerHostChooser
extends HostChooser {
    public static final String PROPERTYNAME_CANCELABLE = "cancelable";
    public static final String PROPERTYNAME_TIMEOUT = "timeout";
    private static final long serialVersionUID = 4389703479548290861L;
    private boolean cancelable = Settings.getBoolean("host.default_cancelable");
    private int timeout = Settings.getInt("host.default_timeout");

    public PowerHostChooser(List<Host> _hosts) {
        super(_hosts);
    }

    public int getTimeout() {
        return this.timeout;
    }

    public boolean isCancelable() {
        return this.cancelable;
    }

    public void setCancelable(boolean _cancelable) {
        boolean old = this.cancelable;
        this.cancelable = _cancelable;
        this.firePropertyChange(PROPERTYNAME_CANCELABLE, old, this.cancelable);
        Settings.set("host.default_cancelable", this.cancelable);
    }

    public void setTimeout(int _timeout) {
        int old = this.timeout;
        this.timeout = _timeout;
        this.firePropertyChange(PROPERTYNAME_TIMEOUT, old, this.timeout);
        Settings.set("host.default_timeout", this.timeout);
    }
}

