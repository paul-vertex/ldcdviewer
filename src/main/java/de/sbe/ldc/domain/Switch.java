/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Switch {
    OFF,
    ON;
    
    public static final Pattern PATTERN_ON;

    public static Switch forString(String _text) {
        return _text == null ? OFF : (PATTERN_ON.matcher(_text).matches() ? ON : OFF);
    }

    static {
        PATTERN_ON = Pattern.compile("on|true|1", 2);
    }
}

