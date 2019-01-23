/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.comparator;

import de.sbe.ldc.domain.UserList;
import java.util.Comparator;

public abstract class ColumnComparator
implements Comparator<UserList.Column> {
    public static final ColumnComparator COLUMN_TYPE = new ColumnComparator(){

        @Override
        public int compare(UserList.Column _o1, UserList.Column _o2) {
            int result = 0;
            if (_o1 != null && _o2 == null) {
                result = 1;
            } else if (_o1 == null && _o2 != null) {
                result = -1;
            } else if (_o1 != null && _o2 != null) {
                if (_o1.getColumnType() != null && _o2.getColumnType() == null) {
                    result = 1;
                } else if (_o1.getColumnType() == null && _o2.getColumnType() != null) {
                    result = -1;
                } else if (_o1.getColumnType() != null && _o2.getColumnType() != null) {
                    result = _o1.getColumnType().compareTo(_o2.getColumnType());
                }
            }
            return result;
        }
    };
    public static final ColumnComparator INDEX = new ColumnComparator(){

        @Override
        public int compare(UserList.Column _o1, UserList.Column _o2) {
            int result = 0;
            if (_o1 != null && _o2 == null) {
                result = 1;
            } else if (_o1 == null && _o2 != null) {
                result = -1;
            } else if (_o1 != null && _o2 != null) {
                result = Integer.valueOf(_o1.getIndex()).compareTo(_o2.getIndex());
            }
            return result;
        }
    };

}

