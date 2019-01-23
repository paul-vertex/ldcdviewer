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
import de.sbe.ldc.utils.FormatUtils;
import de.sbe.utils.StringUtils;
import de.sbe.utils.logging.LogUtils;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

public class DateDeserializer
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
            date = new Date(_json.getAsLong() * 1000L);
        } else if (value.matches("[0-9]{1,2}\\.[0-9]{1,2}\\.[0-9]{2,4}")) {
            try {
                date = FormatUtils.DATE_FORMAT.parse(value);
            }
            catch (ParseException _pe) {
                LogUtils.getLogger(this.getClass()).log(Level.SEVERE, "", _pe);
            }
        } else if (value.matches("[0-9]{4}/[0-9]{2}/[0-9]{2}\\s+[0-9]{2}:[0-9]{2}:[0-9]{2}")) {
            try {
                date = FormatUtils.NORMAN_DATE_TIME_FORMAT.parse(value.trim().replaceAll("\\s+", " "));
            }
            catch (ParseException _pe) {
                LogUtils.getLogger(this.getClass()).log(Level.SEVERE, "", _pe);
            }
        }
        return date;
    }

    public String toString() {
        return DateDeserializer.class.getSimpleName();
    }
}

