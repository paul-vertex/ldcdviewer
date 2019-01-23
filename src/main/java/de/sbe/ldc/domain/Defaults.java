/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.AbstractBean;
import de.sbe.ldc.domain.Host;
import de.sbe.ldc.domain.HostFlags;
import de.sbe.ldc.domain.Room;
import de.sbe.ldc.domain.Switch;
import java.util.List;
import java.util.Map;

public class Defaults
extends AbstractBean {
    public static final String DEFAULT = "default";
    private static final Defaults instance = new Defaults();
    public static final String PROPERTYNAME_LOADED = "loaded";
    private static final long serialVersionUID = 7636095636768714733L;
    private Map<String, HostFlags> defaults;
    private Map<String, HostFlags> groups;
    private Map<String, HostFlags> hosts;
    private boolean loaded;
    private Map<String, HostFlags> rooms;
    private Map<String, HostFlags> users;

    public static Defaults getInstance() {
        return instance;
    }

    private Defaults() {
    }

    public Map<String, HostFlags> getDefaults() {
        return this.defaults;
    }

    private Room getFirstOwner(Host _host) {
        return _host.getRooms() == null || _host.getRooms().isEmpty() ? null : _host.getRooms().get(0);
    }

    public Map<String, HostFlags> getGroups() {
        return this.groups;
    }

    public Map<String, HostFlags> getHosts() {
        return this.hosts;
    }

    public Switch getInput() {
        HostFlags flags;
        HostFlags hostFlags = flags = this.defaults == null ? null : this.defaults.get(DEFAULT);
        if (this.defaults == null || flags == null) {
            return Switch.ON;
        }
        return flags.getInput();
    }

    public Switch getInput(Host _host) {
        HostFlags flags = this.hosts == null ? null : this.hosts.get(_host.getCn());
        Room owner = this.getFirstOwner(_host);
        if (this.hosts == null || flags == null) {
            if (owner == null) {
                return this.getInput();
            }
            return this.getInput(owner);
        }
        return flags.getInput();
    }

    public Switch getInput(Room _room) {
        HostFlags flags;
        HostFlags hostFlags = flags = this.rooms == null ? null : this.rooms.get(_room.getCn());
        if (this.rooms == null || flags == null) {
            return this.getInput();
        }
        return flags.getInput();
    }

    public Switch getInternet() {
        HostFlags flags;
        HostFlags hostFlags = flags = this.defaults == null ? null : this.defaults.get(DEFAULT);
        if (this.defaults == null || flags == null) {
            return Switch.ON;
        }
        return flags.getInternet();
    }

    public Switch getInternet(Host _host) {
        HostFlags flags = this.hosts == null ? null : this.hosts.get(_host.getCn());
        Room owner = this.getFirstOwner(_host);
        if (this.hosts == null || flags == null) {
            if (owner == null) {
                return this.getInternet();
            }
            return this.getInternet(owner);
        }
        return flags.getInternet();
    }

    public Switch getInternet(Room _room) {
        HostFlags flags;
        HostFlags hostFlags = flags = this.rooms == null ? null : this.rooms.get(_room.getCn());
        if (this.rooms == null || flags == null) {
            return this.getInternet();
        }
        return flags.getInternet();
    }

    public Switch getIntranet() {
        HostFlags flags;
        HostFlags hostFlags = flags = this.defaults == null ? null : this.defaults.get(DEFAULT);
        if (this.defaults == null || flags == null) {
            return Switch.ON;
        }
        return flags.getIntranet();
    }

    public Switch getIntranet(Host _host) {
        HostFlags flags = this.hosts == null ? null : this.hosts.get(_host.getCn());
        Room owner = this.getFirstOwner(_host);
        if (this.hosts == null || flags == null) {
            if (owner == null) {
                return this.getIntranet();
            }
            return this.getIntranet(owner);
        }
        return flags.getIntranet();
    }

    public Switch getIntranet(Room _room) {
        HostFlags flags;
        HostFlags hostFlags = flags = this.rooms == null ? null : this.rooms.get(_room.getCn());
        if (this.rooms == null || flags == null) {
            return this.getIntranet();
        }
        return flags.getIntranet();
    }

    public Switch getPrinter() {
        HostFlags flags;
        HostFlags hostFlags = flags = this.defaults == null ? null : this.defaults.get(DEFAULT);
        if (this.defaults == null || flags == null) {
            return Switch.ON;
        }
        return flags.getPrinter();
    }

    public Switch getPrinter(Host _host) {
        HostFlags flags = this.hosts == null ? null : this.hosts.get(_host.getCn());
        Room owner = this.getFirstOwner(_host);
        if (this.hosts == null || flags == null) {
            if (owner == null) {
                return this.getPrinter();
            }
            return this.getPrinter(owner);
        }
        return flags.getPrinter();
    }

    public Switch getPrinter(Room _room) {
        HostFlags flags;
        HostFlags hostFlags = flags = this.rooms == null ? null : this.rooms.get(_room.getCn());
        if (this.rooms == null || flags == null) {
            return this.getPrinter();
        }
        return flags.getPrinter();
    }

    public Map<String, HostFlags> getRooms() {
        return this.rooms;
    }

    public Switch getScreen() {
        HostFlags flags;
        HostFlags hostFlags = flags = this.defaults == null ? null : this.defaults.get(DEFAULT);
        if (this.defaults == null || flags == null) {
            return Switch.ON;
        }
        return flags.getScreen();
    }

    public Switch getScreen(Host _host) {
        HostFlags flags = this.hosts == null ? null : this.hosts.get(_host.getCn());
        Room owner = this.getFirstOwner(_host);
        if (this.hosts == null || flags == null) {
            if (owner == null) {
                return this.getScreen();
            }
            return this.getScreen(owner);
        }
        return flags.getScreen();
    }

    public Switch getScreen(Room _room) {
        HostFlags flags;
        HostFlags hostFlags = flags = this.rooms == null ? null : this.rooms.get(_room.getCn());
        if (this.rooms == null || flags == null) {
            return this.getScreen();
        }
        return flags.getScreen();
    }

    public Switch getSwap() {
        HostFlags flags;
        HostFlags hostFlags = flags = this.defaults == null ? null : this.defaults.get(DEFAULT);
        if (this.defaults == null || flags == null) {
            return Switch.ON;
        }
        return flags.getSwap();
    }

    public Switch getSwap(Host _host) {
        HostFlags flags = this.hosts == null ? null : this.hosts.get(_host.getCn());
        Room owner = this.getFirstOwner(_host);
        if (this.hosts == null || flags == null) {
            if (owner == null) {
                return this.getSwap();
            }
            return this.getSwap(owner);
        }
        return flags.getSwap();
    }

    public Switch getSwap(Room _room) {
        HostFlags flags;
        HostFlags hostFlags = flags = this.rooms == null ? null : this.rooms.get(_room.getCn());
        if (this.rooms == null || flags == null) {
            return this.getSwap();
        }
        return flags.getSwap();
    }

    public Switch getUsb() {
        HostFlags flags;
        HostFlags hostFlags = flags = this.defaults == null ? null : this.defaults.get(DEFAULT);
        if (this.defaults == null || flags == null) {
            return Switch.ON;
        }
        return flags.getUsb();
    }

    public Switch getUsb(Host _host) {
        HostFlags flags = this.hosts == null ? null : this.hosts.get(_host.getCn());
        Room owner = this.getFirstOwner(_host);
        if (this.hosts == null || flags == null) {
            if (owner == null) {
                return this.getUsb();
            }
            return this.getUsb(owner);
        }
        return flags.getUsb();
    }

    public Switch getUsb(Room _room) {
        HostFlags flags;
        HostFlags hostFlags = flags = this.rooms == null ? null : this.rooms.get(_room.getCn());
        if (this.rooms == null || flags == null) {
            return this.getUsb();
        }
        return flags.getUsb();
    }

    public Map<String, HostFlags> getUsers() {
        return this.users;
    }

    public Switch getWebfilter() {
        HostFlags flags;
        HostFlags hostFlags = flags = this.defaults == null ? null : this.defaults.get(DEFAULT);
        if (this.defaults == null || flags == null) {
            return Switch.ON;
        }
        return flags.getWebfilter();
    }

    public Switch getWebfilter(Host _host) {
        HostFlags flags = this.hosts == null ? null : this.hosts.get(_host.getCn());
        Room owner = this.getFirstOwner(_host);
        if (this.hosts == null || flags == null) {
            if (owner == null) {
                return this.getWebfilter();
            }
            return this.getWebfilter(owner);
        }
        return flags.getWebfilter();
    }

    public Switch getWebfilter(Room _room) {
        HostFlags flags;
        HostFlags hostFlags = flags = this.rooms == null ? null : this.rooms.get(_room.getCn());
        if (this.rooms == null || flags == null) {
            return this.getWebfilter();
        }
        return flags.getWebfilter();
    }

    public boolean isLoaded() {
        return this.loaded;
    }

    public void setLoaded(boolean _loaded) {
        boolean old = this.loaded;
        this.loaded = _loaded;
        this.firePropertyChange(PROPERTYNAME_LOADED, old, this.loaded);
    }
}

