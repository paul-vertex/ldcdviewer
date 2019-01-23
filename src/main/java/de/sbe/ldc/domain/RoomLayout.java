/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.Expose
 */
package de.sbe.ldc.domain;

import com.google.gson.annotations.Expose;
import de.sbe.ldc.domain.AbstractBean;
import de.sbe.ldc.domain.Copyable;
import de.sbe.ldc.domain.Host;
import de.sbe.utils.Settings;
import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RoomLayout
extends AbstractBean
implements Copyable {
    public static final String LOCATION_CHANGED = "locationChanged";
    public static final String PROPERTYNAME_HOST_RATIO = "hostRatio";
    private static final long serialVersionUID = 382831928451721033L;
    @Expose
    private int hostRatio = -1;
    @Expose
    private final Map<String, Location> locations = new HashMap<String, Location>();
    private transient Dimension roomSize;
    private transient boolean serializable;

    @Override
    public RoomLayout clone() {
        return this.copy();
    }

    @Override
    public RoomLayout copy() {
        RoomLayout copy = new RoomLayout();
        copy.setHostRatio(this.hostRatio);
        for (Map.Entry<String, Location> entry : this.locations.entrySet()) {
            copy.putLocation(entry.getKey(), entry.getValue());
        }
        return copy;
    }

    private Point decode(Location _location) {
        int x = (int)Math.abs(_location.getX() * this.roomSize.getWidth() / 100.0);
        int y = (int)Math.abs(_location.getY() * this.roomSize.getHeight() / 100.0);
        return new Point(x, y);
    }

    private Location encode(Point _point) {
        double x = this.roomSize.width == 0 ? 0.0 : Math.abs(_point.getX() * 100.0 / this.roomSize.getWidth());
        double y = this.roomSize.height == 0 ? 0.0 : Math.abs(_point.getY() * 100.0 / this.roomSize.getHeight());
        return new Location(x, y);
    }

    public int getHostRatio() {
        return this.hostRatio;
    }

    private String getId(Host _host) {
        return _host.getCn();
    }

    public Point getLocation(Host _host) {
        if (this.roomSize == null) {
            return null;
        }
        return this.locations.containsKey(this.getId(_host)) ? this.decode(this.locations.get(this.getId(_host))) : null;
    }

    public Map<String, Location> getLocations() {
        return this.locations;
    }

    public Dimension getRoomSize() {
        return this.roomSize;
    }

    public boolean isSerializable() {
        return this.serializable;
    }

    private void putLocation(String _id, Location _location) {
        if (this.serializable || !this.locations.containsKey(_id)) {
            this.locations.put(_id, _location);
        }
        this.firePropertyChange(LOCATION_CHANGED, null, (Object)_id);
    }

    public void revalidateHostRatio(int _number) {
        if (this.hostRatio == -1) {
            if (10 >= _number) {
                this.hostRatio = 10;
            } else if (10 < _number && 20 >= _number) {
                this.hostRatio = 7;
            } else if (20 < _number && 30 >= _number) {
                this.hostRatio = 5;
            } else if (30 < _number) {
                this.hostRatio = 3;
            }
        }
    }

    public void setHostRatio(int _size) {
        int old = this.getHostRatio();
        this.hostRatio = _size > Settings.getInt("view.host_ratio.max") ? Settings.getInt("view.host_ratio.max") : (_size < Settings.getInt("view.host_ratio.min") ? Settings.getInt("view.host_ratio.min") : _size);
        this.firePropertyChange(PROPERTYNAME_HOST_RATIO, old, this.getHostRatio());
    }

    public void setLocation(Host _host, Point _point) {
        if (this.roomSize == null) {
            return;
        }
        this.putLocation(this.getId(_host), this.encode(_point));
    }

    public void setRoomSize(Dimension _bounds) {
        this.roomSize = _bounds;
    }

    public void setSerializable(boolean _serializable) {
        this.serializable = _serializable;
    }

    public static class Location {
        @Expose
        private double x;
        @Expose
        private double y;

        public Location() {
        }

        public Location(double _x, double _y) {
            this.x = _x;
            this.y = _y;
        }

        public double getX() {
            return this.x;
        }

        public double getY() {
            return this.y;
        }

        public void setX(double _x) {
            this.x = _x;
        }

        public void setY(double _y) {
            this.y = _y;
        }
    }

}

