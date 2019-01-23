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
import de.sbe.ldc.domain.rpc.Impersonator;
import java.lang.reflect.Type;

public class ImpersonatorSerializer
implements JsonSerializer<Impersonator> {
    public JsonElement serialize(Impersonator _src, Type _typeOfSrc, JsonSerializationContext _context) {
        Object id = _src.getId();
        if (id instanceof Long) {
            return new JsonPrimitive((Number)((Long)_src.getId()));
        }
        if (id instanceof String) {
            return new JsonPrimitive((String)_src.getId());
        }
        return new JsonPrimitive(_src.getDisplayName());
    }

    public String toString() {
        return ImpersonatorSerializer.class.getSimpleName();
    }
}

