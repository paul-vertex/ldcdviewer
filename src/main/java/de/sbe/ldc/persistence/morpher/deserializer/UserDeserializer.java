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
import de.sbe.ldc.domain.User;
import de.sbe.ldc.domain.repository.UserRepository;
import java.lang.reflect.Type;
import java.util.Iterator;

public class UserDeserializer
implements JsonDeserializer<User> {
    private final UserRepository users;

    public static User deserialize(UserRepository _users, String _id) {
        String[] splits;
        String id = _id;
        if (id.indexOf(92) > 0 && (splits = id.split("\\\\")).length > 1) {
            id = splits[1];
        }
        return (User)_users.getByIdOrInstantiate(id);
    }

    public UserDeserializer(UserRepository _users) {
        this.users = _users;
    }

    public User deserialize(JsonElement _json, Type _typeOfT, JsonDeserializationContext _context) throws JsonParseException {
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
        return UserDeserializer.deserialize(this.users, id);
    }

    public String toString() {
        return UserDeserializer.class.getSimpleName();
    }
}

