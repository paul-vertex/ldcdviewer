/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.Host;
import de.sbe.ldc.domain.HostChooser;
import de.sbe.utils.Settings;
import java.util.List;

public class ExpiredHostChooser
extends HostChooser {
    public static final String PROPERTYNAME_EXPIRES = "expires";
    private static final long serialVersionUID = 5321516158666557435L;
    private Long expires = Settings.getLong("host.default_expires_value");

    public ExpiredHostChooser(List<Host> _hosts) {
        super(_hosts);
    }

    public Long getExpires() {
        return this.expires;
    }

    public void setExpires(Long _expires) {
        Long old = this.expires;
        this.expires = _expires;
        this.firePropertyChange(PROPERTYNAME_EXPIRES, (Object)old, (Object)this.expires);
        Settings.set("host.default_expires_value", this.expires);
    }
}

