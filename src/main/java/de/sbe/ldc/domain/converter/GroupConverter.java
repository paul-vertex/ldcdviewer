/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.converter;

import de.sbe.ldc.domain.Group;
import de.sbe.utils.StringConverter;

public abstract class GroupConverter
implements StringConverter<Group> {
    public static final GroupConverter CN = new GroupConverter(){

        @Override
        public String convert(Group _obj) {
            return _obj.getCn();
        }
    };

    GroupConverter() {
    }

}

