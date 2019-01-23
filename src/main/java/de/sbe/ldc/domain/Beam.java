/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.AbstractBean;
import de.sbe.ldc.domain.Host;
import java.util.List;

public class Beam
extends AbstractBean {
    public static final String PROPERTYNAME_CLIENTS = "clients";
    public static final String PROPERTYNAME_PROXY = "proxy";
    private static final long serialVersionUID = 8952465984470773645L;
    private List<Host> clients;
    private Host proxy;

    public List<Host> getClients() {
        return this.clients;
    }

    public Host getProxy() {
        return this.proxy;
    }

    public void setClients(List<Host> _clients) {
        this.clients = _clients;
        this.firePropertyChange(PROPERTYNAME_CLIENTS, this.clients, this.clients);
    }

    public void setProxy(Host _proxy) {
        this.proxy = _proxy;
        this.firePropertyChange(PROPERTYNAME_PROXY, (Object)this.proxy, (Object)this.proxy);
    }
}

