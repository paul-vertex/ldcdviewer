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
import de.sbe.ldc.domain.KopanoEntity;
import de.sbe.utils.StringUtils;
import de.sbe.utils.logging.LogUtils;
import java.lang.reflect.Type;
import java.util.logging.Level;

public class KopanoEntityDeserializer
implements JsonDeserializer<KopanoEntity> {
    public KopanoEntity deserialize(JsonElement _json, Type _classOfT, JsonDeserializationContext _context) throws JsonParseException {
        if (_json.isJsonNull() || _json.isJsonPrimitive() && StringUtils.isEmptyString(_json.getAsString())) {
            return null;
        }
        KopanoEntity ko = null;
        try {
            ko = KopanoEntity.forString(_json.getAsString());
        }
        catch (IllegalArgumentException _iae) {
            LogUtils.getLogger(this.getClass()).log(Level.FINE, "", _iae);
        }
        return ko;
    }

    public String toString() {
        return KopanoEntityDeserializer.class.getSimpleName();
    }
}

