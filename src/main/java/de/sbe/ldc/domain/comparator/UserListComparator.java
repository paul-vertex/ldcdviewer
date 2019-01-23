/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.comparator;

import de.sbe.ldc.domain.UserList;
import java.util.Comparator;

public abstract class UserListComparator
implements Comparator<UserList> {
    public static final UserListComparator ID = new UserListComparator(){

        @Override
        public int compare(UserList _ul1, UserList _ul2) {
            int compareResult = 0;
            if (_ul1.getId() != null && _ul2.getId() != null) {
                compareResult = _ul1.getId().compareTo(_ul2.getId());
            }
            return compareResult;
        }
    };

}

