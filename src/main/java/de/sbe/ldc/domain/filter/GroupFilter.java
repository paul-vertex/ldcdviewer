/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.filter;

import de.sbe.ldc.domain.Clopen;
import de.sbe.ldc.domain.Group;
import de.sbe.ldc.domain.Session;
import de.sbe.ldc.domain.User;
import de.sbe.utils.StringUtils;
import de.sbe.utils.filter.Filter;
import java.util.Collection;

public abstract class GroupFilter
implements Filter<Group> {
    public static final GroupFilter CLASSES = new GroupFilter(){

        @Override
        public boolean include(Group _g) {
            return _g != null && isGroupClass(_g);
        }
    };
    public static final GroupFilter COURSES = new GroupFilter(){

        @Override
        public boolean include(Group _g) {
            return _g != null && isGroupCourse(_g.getObjectType());
        }
    };
    public static final String NAME_ATTIC_GROUP = "atticgroup";
    public static final String NAME_BOARD_GROUP = "board";
    public static final String NAME_CLASS_GROUP = "classgroup";
    public static final String NAME_COMPUTER_GROUP = "computer";
    public static final String NAME_COURSE_GROUP = "coursegroup";
    public static final String NAME_MANAGE_GROUP = "managegroup";
    public static final String NAME_PGMADMINS_GROUP = "pgmadmins";
    public static final String NAME_PRIVACY_GROUP = "privacy";
    public static final String NAME_PROJECT_GROUP = "projectgroup";
    public static final String NAME_ROLE_GROUP = "rolegroup";
    public static final String NAME_STUDENTS_GROUP = "students|schueler";
    public static final String NAME_SYSADMINS_GROUP = "sysadmins";
    public static final String NAME_SYSTEM_GROUP = "systemgroup";
    public static final String NAME_TEACHERS_GROUP = "teachers|lehrer";
    public static final GroupFilter PROJECTS = new GroupFilter(){

        @Override
        public boolean include(Group _g) {
            return _g != null && isGroupProject(_g.getObjectType());
        }
    };
    public static final GroupFilter ROLE = new GroupFilter(){

        @Override
        public boolean include(Group _g) {
            return _g != null && isGroupRole(_g.getObjectType());
        }
    };
    public static final GroupFilter STUDENTS = new GroupFilter(){

        @Override
        public boolean include(Group _g) {
            return _g != null && isGroupStudents(_g.getCn());
        }
    };
    public static final GroupFilter SYSADMINS = new GroupFilter(){

        @Override
        public boolean include(Group _g) {
            return _g != null && isGroupSysadmins(_g.getCn());
        }
    };
    public static final GroupFilter TEACHERS = new GroupFilter(){

        @Override
        public boolean include(Group _g) {
            return _g != null && isGroupTeachers(_g.getCn());
        }
    };

    public static boolean hasGroupBoard(Collection<Group> _groups) {
        if (_groups == null) {
            return false;
        }
        for (Group group : _groups) {
            if (!GroupFilter.isGroupBoard(group)) continue;
            return true;
        }
        return false;
    }

    public static boolean hasGroupPrivacy(Collection<Group> _groups) {
        if (_groups == null) {
            return false;
        }
        for (Group group : _groups) {
            if (!GroupFilter.isGroupPrivacy(group)) continue;
            return true;
        }
        return false;
    }

    public static boolean hasProhibitedGroup(Collection<Group> _groups) {
        if (_groups == null) {
            return false;
        }
        for (Group group : _groups) {
            if (group.getState() != Clopen.CLOSED || Session.getInstance().getThisUser() == null || group.getOwner() == null || Session.getInstance().getThisUser().equals(group.getOwner())) continue;
            return true;
        }
        return false;
    }

    public static boolean isGroupAttic(Group _group) {
        return _group != null && GroupFilter.isGroupAttic(_group.getObjectType());
    }

    public static boolean isGroupAttic(String _objectType) {
        return _objectType != null && _objectType.matches(NAME_ATTIC_GROUP);
    }

    public static boolean isGroupBoard(Group _group) {
        return _group != null && GroupFilter.isGroupRole(_group.getObjectType()) && !StringUtils.isEmptyString(_group.getObjectSubType()) && _group.getObjectSubType().matches(NAME_BOARD_GROUP);
    }

    public static boolean isGroupClass(Group _group) {
        return _group != null && GroupFilter.isGroupClass(_group.getObjectType());
    }

    public static boolean isGroupClass(String _objectType) {
        return _objectType != null && _objectType.matches(NAME_CLASS_GROUP);
    }

    public static boolean isGroupCourse(Group _group) {
        return _group != null && GroupFilter.isGroupCourse(_group.getObjectType());
    }

    public static boolean isGroupCourse(String _objectType) {
        return _objectType != null && _objectType.matches(NAME_COURSE_GROUP);
    }

    public static boolean isGroupManage(Group _group) {
        return _group != null && GroupFilter.isGroupManage(_group.getObjectType());
    }

    public static boolean isGroupManage(String _objectType) {
        return _objectType != null && _objectType.matches(NAME_MANAGE_GROUP);
    }

    public static boolean isGroupPgmadmins(Group _group) {
        return _group != null && GroupFilter.isGroupPgmadmins(_group.getObjectType());
    }

    public static boolean isGroupPgmadmins(String _cn) {
        return _cn != null && _cn.matches(NAME_PGMADMINS_GROUP);
    }

    public static boolean isGroupPrivacy(Group _group) {
        return _group != null && GroupFilter.isGroupRole(_group.getObjectType()) && !StringUtils.isEmptyString(_group.getObjectSubType()) && _group.getObjectSubType().matches(NAME_PRIVACY_GROUP);
    }

    public static boolean isGroupProject(Group _group) {
        return _group != null && GroupFilter.isGroupProject(_group.getObjectType());
    }

    public static boolean isGroupProject(String _objectType) {
        return _objectType != null && _objectType.matches(NAME_PROJECT_GROUP);
    }

    public static boolean isGroupRole(Group _group) {
        return _group != null && GroupFilter.isGroupRole(_group.getObjectType()) && !StringUtils.isEmptyString(_group.getObjectSubType()) && !_group.getObjectSubType().matches("(board)|(privacy)");
    }

    public static boolean isGroupRole(String _objectType) {
        return _objectType != null && _objectType.matches(NAME_ROLE_GROUP);
    }

    public static boolean isGroupStudents(Group _group) {
        return _group != null && GroupFilter.isGroupStudents(_group.getObjectType());
    }

    public static boolean isGroupStudents(String _cn) {
        return _cn != null && _cn.matches(NAME_STUDENTS_GROUP);
    }

    public static boolean isGroupSysadmins(Group _group) {
        return _group != null && GroupFilter.isGroupSysadmins(_group.getObjectType());
    }

    public static boolean isGroupSysadmins(String _cn) {
        return _cn != null && _cn.matches(NAME_SYSADMINS_GROUP);
    }

    public static boolean isGroupSystem(Group _group) {
        return _group != null && GroupFilter.isGroupSystem(_group.getObjectType());
    }

    public static boolean isGroupSystem(String _objectType) {
        return _objectType != null && _objectType.matches(NAME_SYSTEM_GROUP);
    }

    public static boolean isGroupTeachers(Group _group) {
        return _group != null && GroupFilter.isGroupTeachers(_group.getObjectType());
    }

    public static boolean isGroupTeachers(String _cn) {
        return _cn != null && _cn.matches(NAME_TEACHERS_GROUP);
    }

}

