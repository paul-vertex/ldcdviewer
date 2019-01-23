/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonObject
 */
package de.sbe.ldc.persistence.protocol;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.sbe.ldc.persistence.protocol.Processor;

public interface JsonProcessor
extends Processor {
    public void processJson(JsonArray var1);

    public void processJson(JsonObject var1);
}

