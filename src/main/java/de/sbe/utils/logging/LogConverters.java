/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.utils.logging;

import de.sbe.utils.StringConverter;
import java.util.List;

public abstract class LogConverters<T>
implements StringConverter<T> {
    public static final LogConverters<List<? extends Object>> HTML_LIST_CONVERTER = new LogConverters<List<? extends Object>>(){

        @Override
        public String convert(List<? extends Object> _obj) {
            if (_obj == null || _obj.isEmpty()) {
                return "";
            }
            StringBuilder builder = new StringBuilder();
            builder.append("<ul>");
            for (Object object : _obj) {
                if (object == null) continue;
                builder.append("<li>").append(object.toString()).append("</li>");
            }
            builder.append("</ul>");
            return builder.toString();
        }
    };

}

