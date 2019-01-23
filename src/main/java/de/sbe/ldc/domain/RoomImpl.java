/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.AbstractLdapBeanImpl;
import de.sbe.ldc.domain.Host;
import de.sbe.ldc.domain.HostInfo;
import de.sbe.ldc.domain.Room;
import de.sbe.ldc.domain.RoomInfo;
import de.sbe.ldc.domain.RoomLayout;
import de.sbe.ldc.domain.comparator.HostComparator;
import de.sbe.ldc.persistence.morpher.SerializableProperty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.locks.ReentrantLock;

public class RoomImpl
extends AbstractLdapBeanImpl
implements Room {
    private static final long serialVersionUID = 3082443582697952347L;
    @SerializableProperty
    private String cn;
    @SerializableProperty(ldPrefix=true)
    private RoomLayout csettings = new RoomLayout();
    @SerializableProperty(ldPrefix=true)
    private Set<Host> member;
    private final transient RoomInfo roomInfo = new RoomInfo();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void addMember(Host _host) {
        this.getLock().lock();
        try {
            Set<Host> current = this.getMemberDirectly();
            if (current.contains(_host)) {
                return;
            }
            ArrayList<Host> oldMember = Collections.list(Collections.enumeration(current));
            current.add(_host);
            this.firePropertyChange("member", oldMember, this.getMember());
        }
        finally {
            this.getLock().unlock();
        }
    }

    @Override
    public boolean containsMember(Host _host) {
        if (_host == null) {
            return false;
        }
        this.getLock().lock();
        try {
            boolean bl = this.getMemberDirectly().contains(_host);
            return bl;
        }
        finally {
            this.getLock().unlock();
        }
    }

    @Override
    public String getCn() {
        return this.cn;
    }

    @Override
    public RoomLayout getCsettings() {
        return this.csettings;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<HostInfo> getHostInfos() {
        ArrayList<HostInfo> infos;
        infos = new ArrayList<HostInfo>();
        this.getLock().lock();
        try {
            List<Host> current = this.getMember();
            if (current != null) {
                for (Host host : current) {
                    if (!Host.HostType.COMPUTER.equals((Object)host.getObjectSubType()) && !Host.HostType.NOTEBOOK.equals((Object)host.getObjectSubType()) && !Host.HostType.NETBOOK.equals((Object)host.getObjectSubType()) && !Host.HostType.COMPUTER_NOSHOW.equals((Object)host.getObjectSubType()) && !Host.HostType.NOTEBOOK_NOSHOW.equals((Object)host.getObjectSubType()) && !Host.HostType.NETBOOK_NOSHOW.equals((Object)host.getObjectSubType())) continue;
                    infos.add(host.getHostInfo());
                }
            }
        }
        finally {
            this.getLock().unlock();
        }
        return infos;
    }

    @Override
    public List<Host> getMember() {
        return Collections.list(Collections.enumeration(this.getMemberDirectly()));
    }

    private Set<Host> getMemberDirectly() {
        this.getLock().lock();
        try {
            if (this.member == null) {
                this.member = new HashSet<Host>();
            }
        }
        finally {
            this.getLock().unlock();
        }
        return this.member;
    }

    @Override
    public RoomInfo getRoomInfo() {
        return this.roomInfo;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void removeMember(Host _host) {
        this.getLock().lock();
        try {
            Set<Host> current = this.getMemberDirectly();
            if (current.contains(_host)) {
                ArrayList<Host> oldMember = Collections.list(Collections.enumeration(current));
                current.remove(_host);
                this.firePropertyChange("member", oldMember, this.getMember());
            }
        }
        finally {
            this.getLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void revalidateMember(List<Host> _new) {
        TreeSet<Host> newMember = new TreeSet<Host>(HostComparator.CN);
        newMember.addAll(_new == null ? Host.EMPTY_HOST_LIST : _new);
        TreeSet<Host> oldMember = new TreeSet<Host>(HostComparator.CN);
        oldMember.addAll(this.getMember());
        if (oldMember.isEmpty()) {
            for (Host host : newMember) {
                host.addRoom(this);
            }
        } else {
            for (Host host : oldMember) {
                if (newMember.contains(host)) continue;
                host.removeRoom(this);
            }
            for (Host host : newMember) {
                if (oldMember.contains(host)) continue;
                host.addRoom(this);
            }
        }
        this.getLock().lock();
        try {
            this.member = newMember;
        }
        finally {
            this.getLock().unlock();
        }
    }

    @Override
    public void setCn(String _cn) {
        String old = this.cn;
        this.cn = _cn;
        this.firePropertyChange("cn", (Object)old, (Object)this.cn);
    }

    @Override
    public void setCsettings(RoomLayout _cSettings) {
        RoomLayout old = this.csettings;
        this.csettings = _cSettings == null ? new RoomLayout() : _cSettings;
        this.firePropertyChange("csettings", (Object)old, (Object)this.csettings);
    }

    @Override
    public void setMember(List<Host> _member) {
        List<Host> old = this.getMember();
        this.revalidateMember(_member);
        if (_member != null && !_member.isEmpty()) {
            this.getCsettings().revalidateHostRatio(_member.size());
        }
        this.firePropertyChange("member", old, _member);
    }
}

