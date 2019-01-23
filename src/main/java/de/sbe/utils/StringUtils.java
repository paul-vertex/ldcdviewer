/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.utils;

import de.sbe.utils.StringConverter;
import de.sbe.utils.logging.Execution;
import java.util.ArrayList;
import java.util.List;

public abstract class StringUtils {
    public static final String COLON = ":";
    public static final String COMMA = ",";
    public static final String CURLY_BRACKET_CLOSE = "}";
    public static final String CURLY_BRACKET_OPEN = "{";
    public static final String DOT = ".";
    public static final String ELLIPSIS = "...";
    public static final String EMPTY = "";
    public static final String EQUALITY_SIGN = "=";
    public static final String EXCLAMATION_MARK = "!";
    public static final String HASH = "#";
    public static final String LEFT_PARENTHESIS = "(";
    public static final String MINUS = "-";
    public static final String NEWLINE = "\n";
    public static final String NULL = "null";
    public static final String PERIOD = ".";
    public static final String PIPE = "|";
    public static final String PLUS = "+";
    public static final String RIGHT_PARENTHESIS = ")";
    public static final String SEMICOLON = ";";
    public static final String SLASH = "/";
    public static final String SPACE = " ";
    public static final String SQUARED_BRACKET_CLOSE = "]";
    public static final String SQUARED_BRACKET_OPEN = "[";
    public static final String UNDERSCORE = "_";

    @Execution
    public static <T> List<String> convert(List<T> _list) {
        return StringUtils.convert(_list, new StringConverter<T>(){

            @Override
            public String convert(T _obj) {
                return _obj == null ? null : _obj.toString();
            }
        });
    }

    @Execution
    public static <T> List<String> convert(List<T> _list, StringConverter<T> _converter) {
        ArrayList<String> list = new ArrayList<String>(_list.size());
        for (T obj : _list) {
            list.add(_converter.convert(obj));
        }
        return list;
    }

    @Execution
    public static boolean isEmptyString(String _text) {
        return _text == null || _text.trim().isEmpty();
    }

    @Execution
    public static <T> String morph(T _t) {
        return StringUtils.morph(_t, new StringConverter<T>(){

            @Override
            public String convert(T _obj) {
                return _obj == null ? StringUtils.EMPTY : _obj.toString();
            }
        });
    }

    @Execution
    public static <T> String morph(T _t, StringConverter<T> _converter) {
        return _converter.convert(_t);
    }

}

