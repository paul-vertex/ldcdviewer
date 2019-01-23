/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.comparator;

import java.util.Comparator;

public abstract class DiskUsageComparator
implements Comparator<int[]> {
    public static final DiskUsageComparator DEFAULT = new DiskUsageComparator(){

        @Override
        public int compare(int[] _i1, int[] _i2) {
            int result = 0;
            if (_i1 != null && _i2 == null) {
                result = 1;
            } else if (_i1 == null && _i2 != null) {
                result = -1;
            } else if (_i1 != null && _i2 != null) {
                Double p1 = _i1[0] > 0 ? (double)_i1[2] / (double)_i1[0] : -1.0;
                Double p2 = _i2[0] > 0 ? (double)_i2[2] / (double)_i2[0] : -1.0;
                return p1.compareTo(p2);
            }
            return result;
        }
    };

    DiskUsageComparator() {
    }

}

