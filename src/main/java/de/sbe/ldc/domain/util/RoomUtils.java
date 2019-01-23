/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.util;

import de.sbe.ldc.domain.Host;
import de.sbe.ldc.domain.Room;
import de.sbe.ldc.domain.Session;
import de.sbe.utils.CollectionUtils;
import java.util.List;

public abstract class RoomUtils {
    public static boolean areNeighbours(Host _h1, Host _h2) {
        if (_h1 == null || _h2 == null) {
            return false;
        }
        if (_h1.getRooms() == null || _h2.getRooms() == null) {
            return false;
        }
        for (Room room : _h1.getRooms()) {
            if (!room.containsMember(_h2)) continue;
            return true;
        }
        return false;
    }

    public static boolean isMyRoom(Room _r) {
        Host host = Session.getInstance().getThisHost();
        if (host == null) {
            return false;
        }
        List<Room> rooms = host.getRooms();
        if (CollectionUtils.isEmptyCollection(rooms)) {
            return false;
        }
        return rooms.contains(_r);
    }

    private RoomUtils() {
    }
}

