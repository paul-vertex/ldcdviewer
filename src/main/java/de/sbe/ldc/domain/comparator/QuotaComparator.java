/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.comparator;

import de.sbe.ldc.domain.Quota;
import java.util.Comparator;

public abstract class QuotaComparator
implements Comparator<Quota> {
    public static final QuotaComparator DEFAULT = new QuotaComparator(){

        @Override
        public int compare(Quota _q1, Quota _q2) {
            int result = 0;
            if (_q1 != null && _q2 == null) {
                result = 1;
            } else if (_q1 == null && _q2 != null) {
                result = -1;
            } else if (_q1 != null && _q2 != null) {
                result = _q1.isUnlimited() || _q2.isUnlimited() ? Boolean.valueOf(_q1.isUnlimited()).compareTo(_q2.isUnlimited()) : Long.valueOf(_q1.getSize() * (long)_q1.getUnit().getMultiplier()).compareTo(_q2.getSize() * (long)_q2.getUnit().getMultiplier());
            }
            return result;
        }
    };

    QuotaComparator() {
    }

}

