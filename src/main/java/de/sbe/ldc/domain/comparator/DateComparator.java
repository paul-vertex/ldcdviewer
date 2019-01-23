/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.comparator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public abstract class DateComparator
implements Comparator<String> {
    public static final DateComparator DATE;
    public static final SimpleDateFormat DATE_FORMAT;
    public static final DateComparator DATE_TIME;
    public static final SimpleDateFormat DATE_TIME_FORMAT;
    private final SimpleDateFormat format;

    private DateComparator(SimpleDateFormat _format) {
        this.format = _format;
    }

    int doCompare(String _o1, String _o2) {
        if (_o1 == null) {
            if (_o2 != null) {
                return -1;
            }
            return 0;
        }
        if (_o2 == null) {
            return 1;
        }
        try {
            Date d1 = this.format.parse(_o1);
            Date d2 = this.format.parse(_o2);
            return d1.compareTo(d2);
        }
        catch (ParseException _pe) {
            return 0;
        }
    }

    static {
        DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
        DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        DATE = new DateComparator(DATE_FORMAT){

            @Override
            public int compare(String _o1, String _o2) {
                return this.doCompare(_o1, _o2);
            }
        };
        DATE_TIME = new DateComparator(DATE_TIME_FORMAT){

            @Override
            public int compare(String _o1, String _o2) {
                return this.doCompare(_o1, _o2);
            }
        };
    }

}

