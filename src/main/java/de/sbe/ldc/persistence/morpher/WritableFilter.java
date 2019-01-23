/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.persistence.morpher;

import de.sbe.ldc.persistence.morpher.SerializableProperty;
import de.sbe.utils.filter.Filter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class WritableFilter
implements Filter<Field> {
    @Override
    public boolean include(Field _obj) {
        return _obj.getAnnotation(SerializableProperty.class).writable();
    }
}

