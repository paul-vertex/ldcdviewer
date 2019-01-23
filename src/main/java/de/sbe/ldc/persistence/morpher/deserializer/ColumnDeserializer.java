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
import de.sbe.ldc.domain.UserList;
import de.sbe.utils.logging.LogUtils;
import java.lang.reflect.Type;
import java.util.Scanner;
import java.util.logging.Level;

public class ColumnDeserializer
implements JsonDeserializer<UserList.Column> {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public UserList.Column deserialize(JsonElement _json, Type _typeOfT, JsonDeserializationContext _context) throws JsonParseException {
        UserList.Column column;
        if (_json.isJsonNull()) {
            return null;
        }
        column = new UserList.Column(UserList.Column.ColumnType.SKIP_COLUMN, 0, false);
        Scanner scanner = null;
        try {
            scanner = new Scanner(_json.getAsString());
            scanner.useDelimiter("\\s");
            if (scanner.hasNext()) {
                try {
                    column.setColumnType(UserList.Column.ColumnType.valueOf(scanner.next().toUpperCase()));
                }
                catch (IllegalArgumentException _iae) {
                    LogUtils.getLogger(this.getClass()).log(Level.SEVERE, "", _iae);
                }
            }
            if (scanner.hasNextInt()) {
                column.setIndex(scanner.nextInt());
            }
            if (scanner.hasNext() && "PKEY".equalsIgnoreCase(scanner.next())) {
                column.setPrimaryKey(true);
            }
        }
        finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return column;
    }

    public String toString() {
        return ColumnDeserializer.class.getSimpleName();
    }
}

