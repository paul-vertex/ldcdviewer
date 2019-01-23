/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.utils;

import de.sbe.utils.logging.Execution;
import de.sbe.utils.logging.LogUtils;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Settings {
    public static final String BOOLEAN_TRUE = "true|on|1|wahr|ein";
    public static final Pattern BOOLEAN_TRUE_PATTERN = Pattern.compile("true|on|1|wahr|ein", 2);
    public static final String DELIMITER = "\\s*,\\s*|\\s+";
    public static final Pattern DELIMITER_PATTERN = Pattern.compile("\\s*,\\s*|\\s+");
    private static final Properties PROPERTIES = new Properties();

    @Execution
    public static boolean getBoolean(String _key) {
        if (PROPERTIES.containsKey(_key)) {
            return BOOLEAN_TRUE_PATTERN.matcher(Settings.getString(_key).trim()).matches();
        }
        return false;
    }

    public static double getDouble(String _key) {
        return Settings.getDouble(_key, Double.MIN_VALUE);
    }

    @Execution
    public static double getDouble(String _key, double _default) {
        double result = _default;
        if (PROPERTIES.containsKey(_key)) {
            try {
                result = Double.parseDouble(PROPERTIES.getProperty(_key).trim());
            }
            catch (NumberFormatException _nfe) {
                LogUtils.getLogger(Settings.class).log(Level.WARNING, "", _nfe);
            }
        }
        return result;
    }

    @Execution
    public static Double[] getDoubles(String _key) {
        ArrayList<Double> result;
        result = new ArrayList<Double>();
        if (PROPERTIES.containsKey(_key)) {
            Scanner scanner = null;
            try {
                scanner = new Scanner(PROPERTIES.getProperty(_key));
                scanner.useDelimiter(DELIMITER_PATTERN);
                while (scanner.hasNextDouble()) {
                    result.add(scanner.nextDouble());
                }
            }
            finally {
                if (scanner != null) {
                    scanner.close();
                }
            }
        }
        return result.toArray(new Double[result.size()]);
    }

    public static float getFloat(String _key) {
        return Settings.getFloat(_key, Float.MIN_VALUE);
    }

    @Execution
    public static float getFloat(String _key, float _default) {
        float result = _default;
        if (PROPERTIES.containsKey(_key)) {
            try {
                result = Float.parseFloat(PROPERTIES.getProperty(_key).trim());
            }
            catch (NumberFormatException _nfe) {
                LogUtils.getLogger(Settings.class).log(Level.WARNING, "", _nfe);
            }
        }
        return result;
    }

    @Execution
    public static Float[] getFloats(String _key) {
        ArrayList<Float> result;
        result = new ArrayList<Float>();
        if (PROPERTIES.containsKey(_key)) {
            Scanner scanner = null;
            try {
                scanner = new Scanner(PROPERTIES.getProperty(_key));
                scanner.useDelimiter(DELIMITER_PATTERN);
                while (scanner.hasNextFloat()) {
                    result.add(Float.valueOf(scanner.nextFloat()));
                }
            }
            finally {
                if (scanner != null) {
                    scanner.close();
                }
            }
        }
        return result.toArray(new Float[result.size()]);
    }

    public static int getInt(String _key) {
        return Settings.getInt(_key, Integer.MIN_VALUE);
    }

    @Execution
    public static int getInt(String _key, int _default) {
        int result = _default;
        if (PROPERTIES.containsKey(_key)) {
            try {
                result = Integer.parseInt(PROPERTIES.getProperty(_key).trim());
            }
            catch (NumberFormatException _nfe) {
                LogUtils.getLogger(Settings.class).log(Level.WARNING, "", _nfe);
            }
        }
        return result;
    }

    @Execution
    public static Integer[] getInts(String _key) {
        ArrayList<Integer> result;
        result = new ArrayList<Integer>();
        if (PROPERTIES.containsKey(_key)) {
            Scanner scanner = null;
            try {
                scanner = new Scanner(PROPERTIES.getProperty(_key));
                scanner.useDelimiter(DELIMITER_PATTERN);
                while (scanner.hasNextInt()) {
                    result.add(scanner.nextInt());
                }
            }
            finally {
                if (scanner != null) {
                    scanner.close();
                }
            }
        }
        return result.toArray(new Integer[result.size()]);
    }

    public static long getLong(String _key) {
        return Settings.getLong(_key, Long.MIN_VALUE);
    }

    @Execution
    public static long getLong(String _key, long _default) {
        long result = _default;
        if (PROPERTIES.containsKey(_key)) {
            try {
                result = Long.parseLong(PROPERTIES.getProperty(_key).trim());
            }
            catch (NumberFormatException _nfe) {
                LogUtils.getLogger(Settings.class).log(Level.WARNING, "", _nfe);
            }
        }
        return result;
    }

    @Execution
    public static Long[] getLongs(String _key) {
        ArrayList<Long> result;
        result = new ArrayList<Long>();
        if (PROPERTIES.containsKey(_key)) {
            Scanner scanner = null;
            try {
                scanner = new Scanner(PROPERTIES.getProperty(_key));
                scanner.useDelimiter(DELIMITER_PATTERN);
                while (scanner.hasNextLong()) {
                    result.add(scanner.nextLong());
                }
            }
            finally {
                if (scanner != null) {
                    scanner.close();
                }
            }
        }
        return result.toArray(new Long[result.size()]);
    }

    @Execution
    public static String getString(String _key) {
        return Settings.getString(_key, "");
    }

    @Execution
    public static String getString(String _key, String _default) {
        return PROPERTIES.containsKey(_key) ? PROPERTIES.getProperty(_key) : _default;
    }

    @Execution
    public static void set(String _key, Object _value) {
        PROPERTIES.setProperty(_key, _value != null ? _value.toString() : "");
    }

    private Settings() {
    }

    static {
        InputStream is = null;
        is = Settings.class.getResourceAsStream("/settings.xml");
        if (is == null) {
            throw new IllegalStateException("Properties file not found.");
        }
        try {
            PROPERTIES.loadFromXML(is);
        }
        catch (Exception _e) {
            throw new IllegalStateException("Properties file is corrupt: " + _e.getMessage() + ".");
        }
        is = Settings.class.getResourceAsStream("/settings-custom.xml");
        if (is != null) {
            try {
                PROPERTIES.loadFromXML(is);
            }
            catch (Exception _e) {
                // empty catch block
            }
        }
        PROPERTIES.putAll(System.getProperties());
    }
}

