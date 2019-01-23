/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.utils;

import java.awt.Window;

public class WindowUtils {
    public static Window getProbableOwner() {
        Window owner = null;
        Window[] windows = Window.getWindows();
        if (windows != null) {
            int i = windows.length;
            while (--i >= 0) {
                if (!windows[i].isDisplayable()) continue;
                owner = windows[i];
                break;
            }
        }
        return owner;
    }
}

