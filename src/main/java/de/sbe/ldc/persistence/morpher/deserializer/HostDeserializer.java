/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonParseException
 */
package de.sbe.ldc.persistence.morpher.deserializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import de.sbe.ldc.domain.Host;
import de.sbe.ldc.domain.repository.HostRepository;
import java.lang.reflect.Type;
import java.util.Iterator;

public class HostDeserializer
implements JsonDeserializer<Host> {
    private final HostRepository hosts;

    public HostDeserializer(HostRepository _hosts) {
        this.hosts = _hosts;
    }

    public Host deserialize(JsonElement _json, Type _typeOfT, JsonDeserializationContext _context) throws JsonParseException {
        if (_json.isJsonNull()) {
            return null;
        }
        String id = null;
        if (_json.isJsonArray()) {
            Iterator it = _json.getAsJsonArray().iterator();
            if (it.hasNext()) {
                id = ((JsonElement)it.next()).getAsString();
            }
        } else if (_json.isJsonPrimitive()) {
            id = _json.getAsString();
        }
        return this.deserialize(id);
    }

    private Host deserialize(String _id) {
        return (Host)this.hosts.getByIdOrInstantiate(_id);
    }

    public String toString() {
        return HostDeserializer.class.getSimpleName();
    }
}

