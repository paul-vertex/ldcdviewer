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
import de.sbe.ldc.domain.Quota;
import java.lang.reflect.Type;

public class QuotaSerializer
implements JsonSerializer<Quota> {
    public JsonElement serialize(Quota _src, Type _typeOfSrc, JsonSerializationContext _context) {
        if (_src.isUnlimited()) {
            return new JsonPrimitive("unlimited");
        }
        return new JsonPrimitive(_src.getSize() + _src.getUnit().name());
    }

    public String toString() {
        return QuotaSerializer.class.getSimpleName();
    }
}

