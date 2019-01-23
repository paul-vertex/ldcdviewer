/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.AbstractBean;
import de.sbe.ldc.domain.Group;
import de.sbe.ldc.domain.User;
import java.util.List;

public class DsbInfo
extends AbstractBean {
    public static final String PROPERTYNAME_FILTER = "filter";
    public static final String PROPERTYNAME_GROUPS = "groups";
    public static final String PROPERTYNAME_ID = "id";
    public static final String PROPERTYNAME_TEXT = "text";
    public static final String PROPERTYNAME_USERS = "users";
    private static final long serialVersionUID = 8154375208077003829L;
    private String filter;
    private List<Group> groups;
    private String id;
    private String text;
    private List<User> users;

    public String getFilter() {
        return this.filter;
    }

    public List<Group> getGroups() {
        return this.groups;
    }

    public String getId() {
        return this.id;
    }

    public String getText() {
        return this.text;
    }

    public List<User> getUsers() {
        return this.users;
    }

    public void setFilter(String _filter) {
        this.filter = _filter;
        this.firePropertyChange(PROPERTYNAME_FILTER, (Object)this.filter, (Object)this.filter);
    }

    public void setGroups(List<Group> groups) {
        List<Group> old = this.getGroups();
        this.groups = groups;
        this.firePropertyChange(PROPERTYNAME_GROUPS, old, this.getGroups());
    }

    public void setId(String id) {
        String old = this.getId();
        this.id = id;
        this.firePropertyChange(PROPERTYNAME_ID, (Object)old, (Object)this.getId());
    }

    public void setText(String text) {
        String old = this.getText();
        this.text = text;
        this.firePropertyChange(PROPERTYNAME_TEXT, (Object)old, (Object)this.getText());
    }

    public void setUsers(List<User> users) {
        List<User> old = this.getUsers();
        this.users = users;
        this.firePropertyChange(PROPERTYNAME_USERS, old, this.getUsers());
    }
}

