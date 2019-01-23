/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.AbstractBean;
import de.sbe.ldc.domain.Host;
import de.sbe.ldc.domain.HostInfo;
import de.sbe.ldc.domain.Role;
import de.sbe.ldc.domain.Session;
import de.sbe.ldc.domain.Switch;
import de.sbe.ldc.domain.User;
import de.sbe.ldc.domain.filter.RoleFilter;
import de.sbe.utils.Settings;
import java.util.List;

public class HostChooser
extends AbstractBean {
    public static final String PROPERTYNAME_INCLUDE_ADMIN_HOST = "includeAdminHost";
    public static final String PROPERTYNAME_INCLUDE_TEACHER_HOST = "includeTeacherHost";
    public static final String PROPERTYNAME_INCLUDE_THIS_HOST = "includeThisHost";
    private static final long serialVersionUID = 5694426367042169505L;
    private transient Switch defaultIncludeAdminHost;
    private transient Switch defaultIncludeTeacherHost;
    private transient Switch defaultIncludeThisHost;
    private final List<Host> hosts;
    private transient boolean includeAdminHost = Settings.getBoolean("host.include_admin_host");
    private transient boolean includeTeacherHost = Settings.getBoolean("host.include_teacher_host");
    private transient boolean includeThisHost = Settings.getBoolean("host.include_my_host");

    public HostChooser(List<Host> _hosts) {
        this.hosts = _hosts;
    }

    public boolean containsAdminHost() {
        if (this.hosts == null) {
            return false;
        }
        for (Host host : this.hosts) {
            List<User> users = host.getHostInfo().getUsers();
            if (users == null) continue;
            for (User user : users) {
                if (!RoleFilter.isAdmin(user.getRole())) continue;
                return true;
            }
        }
        return false;
    }

    public boolean containsTeacherHost() {
        if (this.hosts == null) {
            return false;
        }
        for (Host host : this.hosts) {
            List<User> users = host.getHostInfo().getUsers();
            if (users == null) continue;
            for (User user : users) {
                if (!RoleFilter.isTeacher(user.getRole())) continue;
                return true;
            }
        }
        return false;
    }

    public boolean containsThisHost() {
        Host workstation = Session.getInstance().getThisHost();
        if (this.hosts == null || workstation == null) {
            return false;
        }
        for (Host host : this.hosts) {
            if (!workstation.equals(host)) continue;
            return true;
        }
        return false;
    }

    public Switch getDefaultIncludeAdminHost() {
        return this.defaultIncludeAdminHost;
    }

    public Switch getDefaultIncludeTeacherHost() {
        return this.defaultIncludeTeacherHost;
    }

    public Switch getDefaultIncludeThisHost() {
        return this.defaultIncludeThisHost;
    }

    public List<Host> getHosts() {
        return this.hosts;
    }

    public boolean isIncludeAdminHost() {
        return this.includeAdminHost;
    }

    public boolean isIncludeTeacherHost() {
        return this.includeTeacherHost;
    }

    public boolean isIncludeThisHost() {
        return this.includeThisHost;
    }

    public void setDefaultIncludeAdminHost(Switch _defaultExcludeAdminHost) {
        this.defaultIncludeAdminHost = _defaultExcludeAdminHost;
    }

    public void setDefaultIncludeTeacherHost(Switch _defaultExcludeTeacherHost) {
        this.defaultIncludeTeacherHost = _defaultExcludeTeacherHost;
    }

    public void setDefaultIncludeThisHost(Switch _defaultExcludeThisHost) {
        this.defaultIncludeThisHost = _defaultExcludeThisHost;
    }

    public void setIncludeAdminHost(boolean _includeAdminHost) {
        boolean old = this.includeAdminHost;
        this.includeAdminHost = _includeAdminHost;
        this.firePropertyChange(PROPERTYNAME_INCLUDE_ADMIN_HOST, old, this.includeAdminHost);
        Settings.set("host.include_admin_host", _includeAdminHost);
    }

    public void setIncludeTeacherHost(boolean _includeTeacherHost) {
        boolean old = this.includeTeacherHost;
        this.includeTeacherHost = _includeTeacherHost;
        this.firePropertyChange(PROPERTYNAME_INCLUDE_TEACHER_HOST, old, this.includeTeacherHost);
        Settings.set("host.include_teacher_host", _includeTeacherHost);
    }

    public void setIncludeThisHost(boolean _includeThisHost) {
        boolean old = this.includeThisHost;
        this.includeThisHost = _includeThisHost;
        this.firePropertyChange(PROPERTYNAME_INCLUDE_THIS_HOST, old, this.includeThisHost);
        Settings.set("host.include_my_host", _includeThisHost);
    }
}

