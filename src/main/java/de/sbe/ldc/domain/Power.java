/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

public enum Power {
    IDLE,
    MYSHN,
    OFF,
    ON,
    REBOOT,
    SHUTDOWN;
    

    public static boolean isOn(Power _power) {
        return _power == null ? false : IDLE.equals((Object)_power) || MYSHN.equals((Object)_power) || ON.equals((Object)_power);
    }
}

