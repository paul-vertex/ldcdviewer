/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.comparator;

import java.util.Comparator;

public class LongComparator
implements Comparator<Long> {
    @Override
    public int compare(Long _o1, Long _o2) {
        if (_o1 == null) {
            if (_o2 == null) {
                return 0;
            }
            return -1;
        }
        if (_o2 == null) {
            return 1;
        }
        return _o1.compareTo(_o2);
    }
}

