/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.persistence.morpher.converter;

import de.sbe.ldc.persistence.morpher.SerializableProperties;
import de.sbe.utils.StringConverter;
import java.lang.reflect.Field;

public class FieldToStringConverter
implements StringConverter<Field> {
    @Override
    public String convert(Field _obj) {
        return SerializableProperties.getSerializedName(_obj);
    }
}

