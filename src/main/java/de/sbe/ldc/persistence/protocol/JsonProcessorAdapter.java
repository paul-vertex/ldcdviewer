/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonParser
 */
package de.sbe.ldc.persistence.protocol;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import de.sbe.ldc.domain.User;
import de.sbe.ldc.persistence.protocol.JsonProcessor;
import de.sbe.ldc.persistence.protocol.ProcessorAdapter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JsonProcessorAdapter
extends ProcessorAdapter
implements JsonProcessor {

    @Override
    public void processJson(JsonArray _array) {
    }

    @Override
    public void processJson(JsonObject _object) {
    }

    @Override
    public void processLine(String _line) {
        try {
            JsonElement element = new JsonParser().parse(_line);
            if (element.isJsonObject()) {
                this.processJson(element.getAsJsonObject());
            } else if (element.isJsonArray()) {
                this.processJson(element.getAsJsonArray());
            }
        }
        catch (JsonParseException _jpe) {
        }
    }
}

