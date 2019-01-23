/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.utils.logging;

import de.sbe.utils.FileUtils;
import java.io.File;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public abstract class LogUtils {
    private static File generateLogDir(String _pattern) {
        String name = _pattern;
        name = name.replaceAll("%h", System.getProperty("user.home").replaceAll("\\\\", "/"));
        name = name.replaceAll("%t", FileUtils.TMP_DIR.getPath().replaceAll("\\\\", "/"));
        return new File(name).getParentFile();
    }

    public static Filter getFilterProperty(String _property, Filter _default) {
        String value = LogManager.getLogManager().getProperty(_property);
        if (value == null) {
            return _default;
        }
        try {
            return (Filter)ClassLoader.getSystemClassLoader().loadClass(value).newInstance();
        }
        catch (Exception ex) {
            return _default;
        }
    }

    public static Formatter getFormatterProperty(String _property, Formatter _default) {
        String value = LogManager.getLogManager().getProperty(_property);
        if (value == null) {
            return _default;
        }
        try {
            return (Formatter)ClassLoader.getSystemClassLoader().loadClass(value).newInstance();
        }
        catch (Exception _e) {
            return _default;
        }
    }

    public static int getIntProperty(String _property, int _default) {
        String value = LogManager.getLogManager().getProperty(_property);
        if (value == null) {
            return _default;
        }
        try {
            return Integer.parseInt(value.trim());
        }
        catch (Exception _e) {
            return _default;
        }
    }

    public static Level getLevelProperty(String _property, Level _default) {
        String value = LogManager.getLogManager().getProperty(_property);
        if (value == null) {
            return _default;
        }
        try {
            return Level.parse(value.trim());
        }
        catch (Exception _e) {
            return _default;
        }
    }

    public static Logger getLogger(Class<?> _class) {
        return Logger.getLogger(_class.getName());
    }

    public static String getStringProperty(String _property, String _default) {
        String value = LogManager.getLogManager().getProperty(_property);
        if (value == null) {
            return _default;
        }
        return value.trim();
    }

    public static void mklogdirs() {
        String fileHandlerPattern;
        String extFileHandlerPattern = LogUtils.getStringProperty("de.sbe.utils.logging.ExtFileHandler.pattern", null);
        if (extFileHandlerPattern != null) {
            FileUtils.mkdirs(new File(FileUtils.TMP_DIR.getPath()), LogUtils.generateLogDir(extFileHandlerPattern));
        }
        if ((fileHandlerPattern = LogUtils.getStringProperty("java.util.logging.FileHandler.pattern", null)) != null) {
            FileUtils.mkdirs(new File(FileUtils.TMP_DIR.getPath()), LogUtils.generateLogDir(fileHandlerPattern));
        }
    }

    private LogUtils() {
    }
}

