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
import de.sbe.ldc.domain.ZarafaEntity;
import de.sbe.utils.StringUtils;
import de.sbe.utils.logging.LogUtils;
import java.lang.reflect.Type;
import java.util.logging.Level;

public class ZarafaEntityDeserializer
implements JsonDeserializer<ZarafaEntity> {
    public ZarafaEntity deserialize(JsonElement _json, Type _classOfT, JsonDeserializationContext _context) throws JsonParseException {
        if (_json.isJsonNull() || _json.isJsonPrimitive() && StringUtils.isEmptyString(_json.getAsString())) {
            return null;
        }
        ZarafaEntity za = null;
        try {
            za = ZarafaEntity.forString(_json.getAsString());
        }
        catch (IllegalArgumentException _iae) {
            LogUtils.getLogger(this.getClass()).log(Level.FINE, "", _iae);
        }
        return za;
    }

    public String toString() {
        return ZarafaEntityDeserializer.class.getSimpleName();
    }
}

