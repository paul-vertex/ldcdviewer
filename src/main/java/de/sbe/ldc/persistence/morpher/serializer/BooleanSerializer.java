/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonPrimitive
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSerializer
 */
package de.sbe.ldc.persistence.morpher.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class BooleanSerializer
implements JsonSerializer<Boolean> {
    public JsonElement serialize(Boolean _src, Type _typeOfSrc, JsonSerializationContext _context) {
        return new JsonPrimitive(_src.toString());
    }

    public String toString() {
        return BooleanSerializer.class.getSimpleName();
    }
}

