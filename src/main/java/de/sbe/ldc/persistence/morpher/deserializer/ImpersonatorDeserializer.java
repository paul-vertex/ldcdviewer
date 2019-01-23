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
import de.sbe.ldc.domain.repository.ContactRepository;
import de.sbe.ldc.domain.repository.RepositoryContext;
import de.sbe.ldc.domain.repository.UserRepository;
import de.sbe.ldc.domain.rpc.Impersonator;
import de.sbe.ldc.persistence.morpher.deserializer.HostDeserializer;
import java.lang.reflect.Type;
import java.util.Iterator;

public class ImpersonatorDeserializer
implements JsonDeserializer<Impersonator> {
    private final RepositoryContext context;

    public ImpersonatorDeserializer(RepositoryContext _context) {
        this.context = _context;
    }

    private Impersonator deserialize(JsonElement _id) {
        String id = _id.getAsString();
        return id.matches("^[0-9]+$") ? this.deserialize(Long.parseLong(id)) : this.deserialize(id);
    }

    public Impersonator deserialize(JsonElement _je, Type _t, JsonDeserializationContext _jdc) {
        if (_je.isJsonNull()) {
            return null;
        }
        JsonElement id = null;
        if (_je.isJsonArray()) {
            Iterator it = _je.getAsJsonArray().iterator();
            if (it.hasNext()) {
                id = (JsonElement)it.next();
            }
        } else if (_je.isJsonPrimitive()) {
            id = _je;
        }
        return this.deserialize(id);
    }

    private Impersonator deserialize(Long _id) {
        return (Impersonator)this.getContext().getContacts().getById(_id);
    }

    private Impersonator deserialize(String _id) {
        return (Impersonator)this.getContext().getUsers().getById(_id);
    }

    public RepositoryContext getContext() {
        return this.context;
    }

    public String toString() {
        return HostDeserializer.class.getSimpleName();
    }
}

