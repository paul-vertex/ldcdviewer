/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.utils;

import de.sbe.utils.logging.Execution;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Pattern;

public abstract class Localization {
    private static final ResourceBundle bundle;
    private static final Pattern INTERN_VAR_PATTERN;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Execution
    public static /* varargs */ String getLocalizedString(String _key, Object ... _params) {
        String result;
        result = bundle.containsKey(_key) ? bundle.getString(_key) : "";
        Scanner scanner = null;
        try {
            scanner = new Scanner(result);
            String text = null;
            while ((text = scanner.findInLine(INTERN_VAR_PATTERN)) != null) {
                result = result.replaceFirst(INTERN_VAR_PATTERN.pattern(), Localization.getLocalizedString(text.substring(2, text.length() - 1), new Object[0]));
            }
        }
        finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        Object[] params = new Object[_params.length];
        int i = _params.length;
        while (--i >= 0) {
            if (_params[i] == null) {
                params[i] = "null";
                continue;
            }
            params[i] = _params[i];
        }
        return MessageFormat.format(result, params);
    }

    @Execution
    public static /* varargs */ String[] getLocalizedStrings(String ... _keys) {
        String[] columns = new String[_keys.length];
        int i = _keys.length;
        while (--i >= 0) {
            columns[i] = Localization.getLocalizedString(_keys[i], new Object[0]);
        }
        return columns;
    }

    private Localization() {
    }

    static {
        INTERN_VAR_PATTERN = Pattern.compile("\\$\\{.+?\\}");
        bundle = ResourceBundle.getBundle("i18n");
    }
}

