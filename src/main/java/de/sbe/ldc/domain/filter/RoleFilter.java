/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.filter;

import de.sbe.ldc.domain.Role;
import de.sbe.ldc.domain.RoleImpl;
import de.sbe.utils.filter.Filter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class RoleFilter
implements Filter<Role> {
    public static final RoleFilter ADMIN;
    public static final RoleFilter COMPUTER;
    public static final RoleFilter COURSE;
    public static final RoleFilter LOCAL;
    public static final RoleFilter MISC;
    public static final String NAME_ADMIN_ROLE = "admin";
    public static final String NAME_COMPUTER_ROLE = "computer";
    public static final String NAME_COURSE_ROLE = "course";
    public static final String NAME_MISC_ROLE = "misc";
    public static final String NAME_STUDENT_ROLE = "student";
    public static final String NAME_TEACHER_ROLE = "teacher";
    private static final Pattern PATTERN_ADMIN;
    private static final Pattern PATTERN_COMPUTER;
    private static final Pattern PATTERN_COURSE;
    private static final Pattern PATTERN_MISC;
    private static final Pattern PATTERN_STUDENT;
    private static final Pattern PATTERN_TEACHER;
    public static final RoleFilter STUDENT;
    public static final RoleFilter TEACHER;

    public static boolean isAdmin(Role _role) {
        return RoleFilter.matches(_role, PATTERN_ADMIN);
    }

    public static boolean isComputer(Role _role) {
        return RoleFilter.matches(_role, PATTERN_COMPUTER);
    }

    public static boolean isCourse(Role _role) {
        return RoleFilter.matches(_role, PATTERN_COURSE);
    }

    public static boolean isLocal(Role _role) {
        return RoleImpl.LOCAL_USER_ROLE == _role;
    }

    public static boolean isMisc(Role _role) {
        return RoleFilter.matches(_role, PATTERN_MISC);
    }

    public static boolean isStudent(Role _role) {
        return RoleFilter.matches(_role, PATTERN_STUDENT);
    }

    public static boolean isTeacher(Role _role) {
        return RoleFilter.matches(_role, PATTERN_TEACHER);
    }

    private static boolean matches(Role _role, Pattern _pattern) {
        return _role == null || _role.getId() == null ? false : _pattern.matcher(_role.getId()).matches();
    }

    static {
        PATTERN_ADMIN = Pattern.compile(NAME_ADMIN_ROLE, 2);
        PATTERN_COMPUTER = Pattern.compile(NAME_COMPUTER_ROLE, 2);
        PATTERN_COURSE = Pattern.compile(NAME_COURSE_ROLE, 2);
        PATTERN_MISC = Pattern.compile(NAME_MISC_ROLE, 2);
        PATTERN_STUDENT = Pattern.compile(NAME_STUDENT_ROLE, 2);
        PATTERN_TEACHER = Pattern.compile(NAME_TEACHER_ROLE, 2);
        ADMIN = new RoleFilter(){

            @Override
            public boolean include(Role _r) {
                return isAdmin(_r);
            }
        };
        COMPUTER = new RoleFilter(){

            @Override
            public boolean include(Role _r) {
                return isComputer(_r);
            }
        };
        COURSE = new RoleFilter(){

            @Override
            public boolean include(Role _r) {
                return isCourse(_r);
            }
        };
        LOCAL = new RoleFilter(){

            @Override
            public boolean include(Role _r) {
                return isLocal(_r);
            }
        };
        MISC = new RoleFilter(){

            @Override
            public boolean include(Role _r) {
                return isMisc(_r);
            }
        };
        STUDENT = new RoleFilter(){

            @Override
            public boolean include(Role _r) {
                return isStudent(_r);
            }
        };
        TEACHER = new RoleFilter(){

            @Override
            public boolean include(Role _r) {
                return isTeacher(_r);
            }
        };
    }

}

