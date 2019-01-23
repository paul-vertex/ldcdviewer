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
import de.sbe.ldc.domain.Quota;
import de.sbe.utils.StringUtils;
import java.lang.reflect.Type;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuotaDeserializer
implements JsonDeserializer<Quota> {
    private static final Pattern FULL_SIZE_PATTERN = Pattern.compile("^[0-9]+\\s*[g,m,t]b$", 2);
    private static final Pattern SIZE_PATTERN = Pattern.compile("^[0-9]+", 2);
    private static final Pattern UNIT_PATTERN = Pattern.compile("[g,m,t]b$", 2);
    private static final Pattern UNLIMITED_PATTERN = Pattern.compile("unlimited", 2);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Quota deserialize(JsonElement _json, Type _typeOfT, JsonDeserializationContext _context) throws JsonParseException {
        Quota quota;
        if (_json.isJsonNull()) {
            return null;
        }
        quota = new Quota();
        String text = _json.getAsString();
        if (StringUtils.isEmptyString(text)) {
            text = "unlimited";
        }
        if (UNLIMITED_PATTERN.matcher(text).matches()) {
            quota.setUnlimited(true);
        } else if (FULL_SIZE_PATTERN.matcher(text).matches()) {
            quota.setUnlimited(false);
            Scanner scanner = null;
            try {
                scanner = new Scanner(text);
                try {
                    quota.setSize(Long.parseLong(scanner.findInLine(SIZE_PATTERN)));
                }
                catch (NumberFormatException _nfe) {
                    quota.setSize(0L);
                }
                try {
                    quota.setUnit(Quota.UNIT.valueOf(scanner.findInLine(UNIT_PATTERN).toUpperCase()));
                }
                catch (IllegalArgumentException _iae) {
                    quota.setUnit(Quota.UNIT.DEFAULT);
                }
            }
            finally {
                if (scanner != null) {
                    scanner.close();
                }
            }
        }
        return quota;
    }

    public String toString() {
        return QuotaDeserializer.class.getSimpleName();
    }
}

