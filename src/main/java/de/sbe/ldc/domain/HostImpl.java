/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.AbstractLdapBeanImpl;
import de.sbe.ldc.domain.Host;
import de.sbe.ldc.domain.HostInfo;
import de.sbe.ldc.domain.Room;
import de.sbe.ldc.domain.comparator.RoomComparator;
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

public class HostImpl
extends AbstractLdapBeanImpl
implements Host {
    private static final long serialVersionUID = 268789693365992218L;
    @SerializableProperty
    private String cn;
    private final transient HostInfo hostInfo = new HostInfo();
    @SerializableProperty(ldPrefix=true)
    private Host.HostType objectSubType = Host.HostType.COMPUTER;
    private transient Set<Room> rooms;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void addRoom(Room _room) {
        this.getLock().lock();
        try {
            Set<Room> current = this.getRoomsDirectly();
            if (current.contains(_room)) {
                return;
            }
            ArrayList<Room> oldRooms = Collections.list(Collections.enumeration(current));
            current.add(_room);
            this.firePropertyChange("rooms", oldRooms, this.getRooms());
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
    public HostInfo getHostInfo() {
        return this.hostInfo;
    }

    @Override
    public Host.HostType getObjectSubType() {
        return this.objectSubType == null ? Host.HostType.COMPUTER : this.objectSubType;
    }

    @Override
    public List<Room> getRooms() {
        return Collections.list(Collections.enumeration(this.getRoomsDirectly()));
    }

    private Set<Room> getRoomsDirectly() {
        this.getLock().lock();
        try {
            if (this.rooms == null) {
                this.rooms = new HashSet<Room>();
            }
        }
        finally {
            this.getLock().unlock();
        }
        return this.rooms;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void removeRoom(Room _room) {
        this.getLock().lock();
        try {
            Set<Room> current = this.getRoomsDirectly();
            if (current.contains(_room)) {
                ArrayList<Room> oldRooms = Collections.list(Collections.enumeration(current));
                current.remove(_room);
                this.firePropertyChange("rooms", oldRooms, this.getRooms());
            }
        }
        finally {
            this.getLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void revalidateRooms(List<Room> _new) {
        TreeSet<Room> newRooms = new TreeSet<Room>(RoomComparator.CN);
        newRooms.addAll(_new == null ? Room.EMPTY_ROOM_LIST : _new);
        TreeSet<Room> oldRooms = new TreeSet<Room>(RoomComparator.CN);
        oldRooms.addAll(this.getRooms());
        if (oldRooms.size() > 0) {
            for (Room room : oldRooms) {
                if (newRooms.contains(room)) continue;
                room.removeMember(this);
            }
            for (Room room : newRooms) {
                if (oldRooms.contains(room)) continue;
                room.addMember(this);
            }
        } else {
            for (Room room : newRooms) {
                room.addMember(this);
            }
        }
        this.getLock().lock();
        try {
            this.rooms = newRooms;
        }
        finally {
            this.getLock().unlock();
        }
    }

    @Override
    public void setCn(String name) {
        String old = this.cn;
        this.cn = name;
        this.firePropertyChange("cn", (Object)old, (Object)this.cn);
    }

    @Override
    public void setObjectSubType(Host.HostType _objectSubType) {
        Host.HostType old = this.objectSubType;
        this.objectSubType = _objectSubType;
        this.firePropertyChange("objectSubType", (Object)old, (Object)this.objectSubType);
    }

    @Override
    public void setRooms(List<Room> _rooms) {
        List<Room> old = this.getRooms();
        this.revalidateRooms(_rooms);
        this.firePropertyChange("rooms", old, _rooms);
    }
}

