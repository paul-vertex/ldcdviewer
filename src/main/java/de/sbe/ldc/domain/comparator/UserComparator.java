/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.comparator;

import de.sbe.ldc.domain.Group;
import de.sbe.ldc.domain.Role;
import de.sbe.ldc.domain.User;
import de.sbe.ldc.domain.comparator.GroupComparator;
import de.sbe.ldc.domain.filter.RoleFilter;
import de.sbe.utils.StringUtils;
import java.util.Comparator;
import java.util.List;

public abstract class UserComparator
implements Comparator<User> {
    public static final UserComparator DISPLAYNAME = new UserComparator(){

        @Override
        public int compare(User _u1, User _u2) {
            int compareResult = 0;
            if (_u1.getDisplayName() != null && _u2.getDisplayName() == null) {
                compareResult = 1;
            } else if (_u1.getDisplayName() == null && _u2.getDisplayName() != null) {
                compareResult = -1;
            } else if (_u1.getDisplayName() != null && _u2.getDisplayName() != null) {
                compareResult = _u1.getDisplayName().compareTo(_u2.getDisplayName());
            }
            return compareResult;
        }
    };
    public static final UserComparator EXPORT = new UserComparator(){

        @Override
        public int compare(User _user1, User _user2) {
            Group g2;
            Group g1;
            int compareResult = 0;
            Role role1 = _user1.getRole();
            Role role2 = _user2.getRole();
            if (role1 != null && role2 != null) {
                if (RoleFilter.isStudent(role1) && !RoleFilter.isStudent(role2)) {
                    compareResult = -1;
                } else if (!RoleFilter.isStudent(role1) && RoleFilter.isStudent(role2)) {
                    compareResult = 1;
                } else if (RoleFilter.isStudent(role1) && RoleFilter.isStudent(role2)) {
                    compareResult = 0;
                } else if (RoleFilter.isCourse(role1) && !RoleFilter.isCourse(role2)) {
                    compareResult = -1;
                } else if (!RoleFilter.isCourse(role1) && RoleFilter.isCourse(role2)) {
                    compareResult = 1;
                } else if (RoleFilter.isCourse(role1) && RoleFilter.isCourse(role2)) {
                    compareResult = 0;
                } else if (RoleFilter.isTeacher(role1) && !RoleFilter.isTeacher(role2) && !RoleFilter.isStudent(role2)) {
                    compareResult = -1;
                } else if (!RoleFilter.isTeacher(role1) && !RoleFilter.isStudent(role1) && RoleFilter.isTeacher(role2)) {
                    compareResult = 1;
                } else if (RoleFilter.isTeacher(role1) && RoleFilter.isTeacher(role2)) {
                    compareResult = 0;
                } else if (!(RoleFilter.isStudent(role1) || RoleFilter.isTeacher(role1) || RoleFilter.isStudent(role2) || RoleFilter.isTeacher(role2))) {
                    compareResult = role1.getId().compareTo(role2.getId());
                }
            }
            if (role1 != null && role2 == null) {
                compareResult = -1;
            }
            if (role1 == null && role2 != null) {
                compareResult = 1;
            }
            if (role1 == null && role2 == null) {
                compareResult = 0;
            }
            if (compareResult == 0 && role1 != null && role2 != null && RoleFilter.isStudent(role1) && RoleFilter.isStudent(role2)) {
                List<Group> classes1 = _user1.getClasses();
                List<Group> classes2 = _user2.getClasses();
                if (classes1 != null && classes2 != null && !classes1.isEmpty() && !classes2.isEmpty()) {
                    g1 = classes1.get(0);
                    g2 = classes2.get(0);
                    compareResult = GroupComparator.CN.compare(g1, g2);
                }
            }
            if (compareResult == 0 && role1 != null && role2 != null && RoleFilter.isCourse(role1) && RoleFilter.isCourse(role2)) {
                List<Group> courses1 = _user1.getCourses();
                List<Group> courses2 = _user2.getCourses();
                if (courses1 != null && courses2 != null && !courses1.isEmpty() && !courses2.isEmpty()) {
                    g1 = courses1.get(0);
                    g2 = courses2.get(0);
                    compareResult = GroupComparator.CN.compare(g1, g2);
                }
            }
            if (compareResult == 0 && _user1.getGivenname() != null && _user1.getSn() != null && _user2.getGivenname() != null && _user2.getSn() != null) {
                String givenname1 = StringUtils.morph(_user1.getGivenname());
                String sn1 = StringUtils.morph(_user1.getSn());
                String name1 = sn1 + (!givenname1.isEmpty() ? "," : "") + givenname1;
                String givenname2 = StringUtils.morph(_user2.getGivenname());
                String sn2 = StringUtils.morph(_user2.getSn());
                String name2 = sn2 + (!givenname2.isEmpty() ? "," : "") + givenname2;
                compareResult = name1.compareTo(name2);
            }
            if (compareResult == 0) {
                String uid1 = _user1.getUid();
                String uid2 = _user2.getUid();
                if (uid1 != null && uid2 != null) {
                    compareResult = uid1.compareTo(uid2);
                }
            }
            return compareResult;
        }
    };
    public static final UserComparator SN_GIVENNAME;
    public static final UserComparator UID;

    static {
        UID = new UserComparator(){

            @Override
            public int compare(User _u1, User _u2) {
                int compareResult = 0;
                if (_u1.getUid() != null && _u2.getUid() != null) {
                    compareResult = _u1.getUid().compareTo(_u2.getUid());
                }
                return compareResult;
            }
        };
        SN_GIVENNAME = new UserComparator(){

            @Override
            public int compare(User _u1, User _u2) {
                int compareResult = 0;
                if (_u1.getSn() != null && _u2.getSn() == null) {
                    compareResult = 1;
                } else if (_u1.getSn() == null && _u2.getSn() != null) {
                    compareResult = -1;
                } else if (_u1.getSn() != null && _u2.getSn() != null) {
                    compareResult = _u1.getSn().compareTo(_u2.getSn());
                }
                if (compareResult == 0) {
                    if (_u1.getGivenname() != null && _u2.getGivenname() == null) {
                        compareResult = 1;
                    } else if (_u1.getGivenname() == null && _u2.getGivenname() != null) {
                        compareResult = -1;
                    } else if (_u1.getGivenname() != null && _u2.getGivenname() != null) {
                        compareResult = _u1.getGivenname().compareTo(_u2.getGivenname());
                    }
                }
                return compareResult;
            }
        };
    }

}

