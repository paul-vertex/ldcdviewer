/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.filter;

import de.sbe.ldc.domain.Role;
import de.sbe.ldc.domain.User;
import de.sbe.ldc.domain.filter.RoleFilter;
import de.sbe.utils.filter.Filter;

public abstract class UserFilter
implements Filter<User> {
    public static final UserFilter ADMIN = new UserFilter(){

        @Override
        public boolean include(User _u) {
            return RoleFilter.isAdmin(_u.getRole());
        }
    };
    public static final UserFilter COURSE = new UserFilter(){

        @Override
        public boolean include(User _u) {
            return RoleFilter.isCourse(_u.getRole());
        }
    };
    public static final UserFilter LOCAL = new UserFilter(){

        @Override
        public boolean include(User _u) {
            return RoleFilter.isLocal(_u.getRole());
        }
    };
    public static final UserFilter MISC = new UserFilter(){

        @Override
        public boolean include(User _u) {
            return RoleFilter.isMisc(_u.getRole());
        }
    };
    public static final UserFilter STUDENT = new UserFilter(){

        @Override
        public boolean include(User _u) {
            return RoleFilter.isStudent(_u.getRole());
        }
    };
    public static final UserFilter TEACHER = new UserFilter(){

        @Override
        public boolean include(User _u) {
            return RoleFilter.isTeacher(_u.getRole());
        }
    };

}

