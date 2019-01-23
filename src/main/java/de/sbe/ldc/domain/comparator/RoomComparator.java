/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.comparator;

import de.sbe.ldc.domain.Room;
import java.util.Comparator;

public abstract class RoomComparator
implements Comparator<Room> {
    public static final RoomComparator CN = new RoomComparator(){

        @Override
        public int compare(Room _r1, Room _r2) {
            int compareResult = 0;
            if (_r1.getCn() != null && _r2.getCn() != null) {
                compareResult = _r1.getCn().compareTo(_r2.getCn());
            }
            return compareResult;
        }
    };

}

