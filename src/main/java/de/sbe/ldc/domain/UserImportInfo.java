/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.AbstractBean;

public final class UserImportInfo
extends AbstractBean {
    public static final String PROPERTYNAME_ADDED_GROUPS = "addedGroups";
    public static final String PROPERTYNAME_ADDED_USERS = "addedUsers";
    public static final String PROPERTYNAME_DELETED_GROUPS = "deletedGroups";
    public static final String PROPERTYNAME_DELETED_USERS = "deletedUsers";
    public static final String PROPERTYNAME_ERRORS = "errors";
    public static final String PROPERTYNAME_MODIFIED_GROUPS = "modifiedGroups";
    public static final String PROPERTYNAME_MODIFIED_USERS = "modifiedUsers";
    public static final String PROPERTYNAME_TIME = "time";
    public static final String PROPERTYNAME_WARNINGS = "warnings";
    private static final long serialVersionUID = -5775874586563297409L;
    private int addedGroups;
    private int addedUsers;
    private int deletedGroups;
    private int deletedUsers;
    private int errors;
    private int modifiedGroups;
    private int modifiedUsers;
    private long time;
    private int warnings;

    public void clear() {
        this.setAddedGroups(0);
        this.setDeletedGroups(0);
        this.setModifiedGroups(0);
        this.setAddedUsers(0);
        this.setDeletedUsers(0);
        this.setModifiedUsers(0);
        this.setErrors(0);
        this.setWarnings(0);
        this.setTime(0L);
    }

    public int getAddedGroups() {
        return this.addedGroups;
    }

    public int getAddedUsers() {
        return this.addedUsers;
    }

    public int getDeletedGroups() {
        return this.deletedGroups;
    }

    public int getDeletedUsers() {
        return this.deletedUsers;
    }

    public int getErrors() {
        return this.errors;
    }

    public int getModifiedGroups() {
        return this.modifiedGroups;
    }

    public int getModifiedUsers() {
        return this.modifiedUsers;
    }

    public long getTime() {
        return this.time;
    }

    public int getWarnings() {
        return this.warnings;
    }

    public void incAddedGroups() {
        this.setAddedGroups(this.getAddedGroups() + 1);
    }

    public void incAddedUsers() {
        this.setAddedUsers(this.getAddedUsers() + 1);
    }

    public void incDeletedGroups() {
        this.setDeletedGroups(this.getDeletedGroups() + 1);
    }

    public void incDeletedUsers() {
        this.setDeletedUsers(this.getDeletedUsers() + 1);
    }

    public void incErrors() {
        this.setErrors(this.getErrors() + 1);
    }

    public void incModifiedGroups() {
        this.setModifiedGroups(this.getModifiedGroups() + 1);
    }

    public void incModifiedUsers() {
        this.setModifiedUsers(this.getModifiedUsers() + 1);
    }

    public void incWarnings() {
        this.setWarnings(this.getWarnings() + 1);
    }

    public void setAddedGroups(int _addedGroups) {
        int old = this.addedGroups;
        this.addedGroups = _addedGroups;
        this.firePropertyChange(PROPERTYNAME_ADDED_GROUPS, old, this.addedGroups);
    }

    public void setAddedUsers(int _addedUsers) {
        int old = this.addedUsers;
        this.addedUsers = _addedUsers;
        this.firePropertyChange(PROPERTYNAME_ADDED_USERS, old, this.addedUsers);
    }

    public void setDeletedGroups(int _deletedGroups) {
        int old = this.deletedGroups;
        this.deletedGroups = _deletedGroups;
        this.firePropertyChange(PROPERTYNAME_DELETED_GROUPS, old, this.deletedGroups);
    }

    public void setDeletedUsers(int _deletedUsers) {
        int old = this.deletedUsers;
        this.deletedUsers = _deletedUsers;
        this.firePropertyChange(PROPERTYNAME_DELETED_USERS, old, this.deletedUsers);
    }

    public void setErrors(int _errors) {
        int old = this.errors;
        this.errors = _errors;
        this.firePropertyChange(PROPERTYNAME_ERRORS, old, this.errors);
    }

    public void setModifiedGroups(int _modifiedGroups) {
        int old = this.modifiedGroups;
        this.modifiedGroups = _modifiedGroups;
        this.firePropertyChange(PROPERTYNAME_MODIFIED_GROUPS, old, this.modifiedGroups);
    }

    public void setModifiedUsers(int _modifiedUsers) {
        int old = this.modifiedUsers;
        this.modifiedUsers = _modifiedUsers;
        this.firePropertyChange(PROPERTYNAME_MODIFIED_USERS, old, this.modifiedUsers);
    }

    public void setTime(long _time) {
        long old = this.time;
        this.time = _time;
        this.firePropertyChange(PROPERTYNAME_TIME, old, this.time);
    }

    public void setWarnings(int _warnings) {
        int old = this.warnings;
        this.warnings = _warnings;
        this.firePropertyChange(PROPERTYNAME_WARNINGS, old, this.warnings);
    }
}

