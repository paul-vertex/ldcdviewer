/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.comparator;

import de.sbe.ldc.domain.Inventory;
import java.util.Comparator;

public abstract class EntryComparator
implements Comparator<Inventory.Value> {
    public static final EntryComparator KEY = new EntryComparator(){

        @Override
        public int compare(Inventory.Value _e1, Inventory.Value _e2) {
            int compareResult = 0;
            if (_e1.getAttribute_key() != null && _e2.getAttribute_key() != null) {
                compareResult = _e1.getAttribute_key().compareTo(_e2.getAttribute_key());
            }
            return compareResult;
        }
    };

}

