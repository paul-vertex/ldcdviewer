/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.persistence.morpher;

import de.sbe.ldc.persistence.morpher.RpcScope;
import de.sbe.ldc.persistence.morpher.SerializableProperty;
import de.sbe.utils.StringUtils;
import de.sbe.utils.filter.Filter;
import de.sbe.utils.filter.Filters;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SerializableProperties {
    private static final Map<Class<?>, Map<String, Field>> CACHE = new HashMap();
    private static final Map<Field, String> FIELD_TO_NAME_CACHE = new HashMap<Field, String>();

    public static List<Field> getSerializablePropertiesAsList(Class<?> _class) {
        return Collections.list(Collections.enumeration(SerializableProperties.getSerializablePropertiesAsMap(_class).values()));
    }

    public static List<Field> getSerializablePropertiesAsList(Class<?> _class, Filter<Field> _filter) {
        return Filters.filterAsList(SerializableProperties.getSerializablePropertiesAsList(_class), _filter);
    }

    public static synchronized Map<String, Field> getSerializablePropertiesAsMap(Class<?> _class) {
        if (!CACHE.containsKey(_class)) {
            HashMap allFields = new HashMap();
            for (Class<?> clazz = _class; clazz != null; clazz = clazz.getSuperclass()) {
                HashMap<String, Field> classFields = new HashMap<String, Field>();
                for (Field field : clazz.getDeclaredFields()) {
                    if (!field.isAnnotationPresent(SerializableProperty.class)) continue;
                    classFields.put(field.getName().toLowerCase(), field);
                }
                allFields.putAll(classFields);
            }
            CACHE.put(_class, Collections.unmodifiableMap(allFields));
        }
        return CACHE.get(_class);
    }

    public static Field getSerializableProperty(Class<?> _class, String _fieldName) {
        return SerializableProperties.getSerializablePropertiesAsMap(_class).get(_fieldName.toLowerCase());
    }

    public static String getSerializedName(Class<?> _class, String _fieldName) {
        return SerializableProperties.getSerializedName(SerializableProperties.getSerializablePropertiesAsMap(_class).get(_fieldName.toLowerCase()));
    }

    public static String getSerializedName(Field _field) {
        if (FIELD_TO_NAME_CACHE.containsKey(_field)) {
            return FIELD_TO_NAME_CACHE.get(_field);
        }
        String name = _field.getName();
        if (_field.isAnnotationPresent(SerializableProperty.class) && _field.getAnnotation(SerializableProperty.class).ldPrefix()) {
            name = "ld" + name;
        }
        if (_field.isAnnotationPresent(RpcScope.class)) {
            StringBuilder builder = new StringBuilder();
            String scope = _field.getAnnotation(RpcScope.class).scope();
            String tmp = null;
            if (!StringUtils.isEmptyString(scope)) {
                builder.append(scope);
                builder.append(".");
                tmp = name.replaceFirst(scope, "");
            } else {
                tmp = name;
            }
            builder.append(tmp.charAt(0));
            for (int i = 1; i < tmp.length(); ++i) {
                String sub = tmp.substring(i, i + 1);
                if (sub.toUpperCase().equals(sub)) {
                    builder.append("_");
                }
                builder.append(sub);
            }
            name = builder.toString();
        }
        name = name.toLowerCase();
        FIELD_TO_NAME_CACHE.put(_field, name);
        return name;
    }

    public static boolean isSerializableProperty(Class<?> _class, String _fieldName) {
        return SerializableProperties.getSerializablePropertiesAsMap(_class).containsKey(_fieldName.toLowerCase());
    }

    public static boolean isSerializableProperty(Class<?> _class, String _fieldName, Filter<Field> _filter) {
        return SerializableProperties.isSerializableProperty(SerializableProperties.getSerializablePropertiesAsMap(_class).get(_fieldName.toLowerCase()), _filter);
    }

    public static boolean isSerializableProperty(Field _field) {
        return SerializableProperties.isSerializableProperty(_field.getDeclaringClass(), _field.getName());
    }

    public static boolean isSerializableProperty(Field _field, Filter<Field> _filter) {
        return _field == null ? false : SerializableProperties.getSerializablePropertiesAsList(_field.getDeclaringClass(), _filter).contains(_field);
    }

    private SerializableProperties() {
    }
}

