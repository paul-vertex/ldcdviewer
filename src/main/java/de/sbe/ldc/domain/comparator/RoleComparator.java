/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.comparator;

import de.sbe.ldc.domain.Role;
import java.util.Comparator;

public abstract class RoleComparator
implements Comparator<Role> {
    public static final RoleComparator ID = new RoleComparator(){

        @Override
        public int compare(Role _r1, Role _r2) {
            int compareResult = 0;
            if (_r1 != null && _r2 != null && _r1.getId() != null && _r2.getId() != null) {
                compareResult = _r1.getId().compareTo(_r2.getId());
            }
            return compareResult;
        }
    };

}

