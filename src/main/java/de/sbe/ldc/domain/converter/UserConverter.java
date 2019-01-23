/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.converter;

import de.sbe.ldc.domain.User;
import de.sbe.utils.StringConverter;

public abstract class UserConverter
implements StringConverter<User> {
    public static final UserConverter DISPLAY_NAME = new UserConverter(){

        @Override
        public String convert(User _obj) {
            return _obj.getDisplayName() == null ? _obj.getUid() : _obj.getDisplayName();
        }
    };
    public static final UserConverter UID = new UserConverter(){

        @Override
        public String convert(User _obj) {
            return _obj.getUid();
        }
    };

    UserConverter() {
    }

}

