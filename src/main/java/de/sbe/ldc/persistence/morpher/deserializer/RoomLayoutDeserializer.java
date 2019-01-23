/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonParseException
 */
package de.sbe.ldc.persistence.morpher.deserializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import de.sbe.ldc.domain.RoomLayout;
import de.sbe.ldc.persistence.morpher.Decoder;
import java.lang.reflect.Type;

public class RoomLayoutDeserializer
implements JsonDeserializer<RoomLayout> {
    public RoomLayout deserialize(JsonElement _json, Type _typeOfT, JsonDeserializationContext _context) throws JsonParseException {
        if (_json.isJsonNull()) {
            return null;
        }
        RoomLayout layout = null;
        layout = _json.isJsonArray() ? (RoomLayout)new GsonBuilder().create().fromJson(new Decoder().decode(_json.getAsString()), RoomLayout.class) : new RoomLayout();
        return layout;
    }

    public String toString() {
        return RoomLayoutDeserializer.class.getSimpleName();
    }
}

