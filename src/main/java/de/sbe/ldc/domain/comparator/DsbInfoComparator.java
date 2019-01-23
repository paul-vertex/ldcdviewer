/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.comparator;

import de.sbe.ldc.domain.DsbInfo;
import java.util.Comparator;

public abstract class DsbInfoComparator
implements Comparator<DsbInfo> {
    public static final DsbInfoComparator ID = new DsbInfoComparator(){

        @Override
        public int compare(DsbInfo _di1, DsbInfo _di2) {
            int compareResult = 0;
            if (_di1.getId() != null && _di2.getId() != null) {
                compareResult = _di1.getId().compareTo(_di2.getId());
            }
            return compareResult;
        }
    };

    DsbInfoComparator() {
    }

}

