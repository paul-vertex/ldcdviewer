/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonParseException
 */
package de.sbe.ldc.persistence.morpher.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import de.sbe.utils.StringUtils;
import java.lang.reflect.Type;
import java.util.Date;

public class DateFromLongDeserializer
implements JsonDeserializer<Date> {
    public Date deserialize(JsonElement _json, Type _typeOfT, JsonDeserializationContext _context) throws JsonParseException {
        if (_json.isJsonNull()) {
            return new Date(0L);
        }
        Date date = new Date(0L);
        String value = _json.getAsString();
        if (StringUtils.isEmptyString(value)) {
            return new Date(0L);
        }
        if (value.matches("^[0-9]+$")) {
            date = new Date(_json.getAsLong());
        }
        return date;
    }

    public String toString() {
        return DateFromLongDeserializer.class.getSimpleName();
    }
}

