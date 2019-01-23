/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.converter;

import de.sbe.ldc.domain.Room;
import de.sbe.utils.StringConverter;

public abstract class RoomConverter
implements StringConverter<Room> {
    public static final RoomConverter CN = new RoomConverter(){

        @Override
        public String convert(Room _obj) {
            return _obj.getCn();
        }
    };

    RoomConverter() {
    }

}

