/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.comparator;

import de.sbe.utils.StringUtils;
import java.util.Comparator;

public class StringAsNumberComparator
implements Comparator<String> {
    @Override
    public int compare(String _o1, String _o2) {
        if (StringUtils.isEmptyString(_o1)) {
            if (StringUtils.isEmptyString(_o2)) {
                return 0;
            }
            return -1;
        }
        if (StringUtils.isEmptyString(_o2)) {
            return 1;
        }
        try {
            double d1 = Double.parseDouble(_o1);
            double d2 = Double.parseDouble(_o2);
            return Double.compare(d1, d2);
        }
        catch (NumberFormatException _nfe) {
            return 0;
        }
    }
}

