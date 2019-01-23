/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 */
package de.sbe.ldc.persistence.morpher.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

public class JsonObjectDeserializer
implements JsonDeserializer<JsonObject> {
    public JsonObject deserialize(JsonElement _json, Type _typeOfT, JsonDeserializationContext _context) throws JsonParseException {
        return (JsonObject)(_json.isJsonObject() ? _json : null);
    }

    public String toString() {
        return JsonObjectDeserializer.class.getSimpleName();
    }
}

