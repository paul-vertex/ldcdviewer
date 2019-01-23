/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonPrimitive
 */
package de.sbe.ldc.persistence.morpher.deserializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import de.sbe.ldc.persistence.protocol.ServerOptions;
import java.lang.reflect.Type;

public class ServerOptionsDeserializer
implements JsonDeserializer<ServerOptions> {
    public ServerOptions deserialize(JsonElement _element, Type _type, JsonDeserializationContext _context) throws JsonParseException {
        if (_element.isJsonNull()) {
            return null;
        }
        ServerOptions options = new ServerOptions();
        if (_element.isJsonArray()) {
            for (JsonElement e : (JsonArray)_element) {
                if (!e.isJsonPrimitive()) continue;
                JsonPrimitive p = (JsonPrimitive)e;
                if (p.getAsString().toLowerCase().matches("ssl")) {
                    options.setSSLSupported(true);
                    continue;
                }
                if (!p.getAsString().toLowerCase().matches("noserverprofiles")) continue;
                options.setServerProfilesAllowed(false);
            }
        }
        return options;
    }

    public String toString() {
        return ServerOptionsDeserializer.class.getSimpleName();
    }
}

