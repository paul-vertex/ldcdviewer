/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.utils.logging;

import de.sbe.utils.logging.LogUtils;
import java.io.IOException;
import java.util.logging.FileHandler;

public class ExtFileHandler
extends FileHandler {
    public ExtFileHandler() throws IOException, SecurityException {
    }

    public ExtFileHandler(String _pattern) throws IOException, SecurityException {
        super(_pattern);
    }

    public ExtFileHandler(String _pattern, boolean _append) throws IOException, SecurityException {
        super(_pattern, _append);
    }

    public ExtFileHandler(String _pattern, int _limit, int _count) throws IOException, SecurityException {
        super(_pattern, _limit, _count);
    }

    public ExtFileHandler(String _pattern, int _limit, int _count, boolean _append) throws IOException, SecurityException {
        super(_pattern, _limit, _count, _append);
    }

    static {
        LogUtils.mklogdirs();
    }
}

