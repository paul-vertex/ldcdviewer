/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.AbstractBean;
import de.sbe.ldc.domain.Group;
import de.sbe.ldc.domain.Host;
import de.sbe.ldc.domain.Room;
import de.sbe.ldc.domain.User;
import de.sbe.ldc.domain.repository.RepositoryContext;
import de.sbe.ldc.security.Auth;
import de.sbe.utils.NetUtils;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.List;

public class Session
extends AbstractBean {
    private static Session instance;
    public static final String PROPERTYNAME_SELECTED_GROUPS = "selectedGroups";
    public static final String PROPERTYNAME_SELECTED_HOST = "selectedHost";
    public static final String PROPERTYNAME_SELECTED_ROOMS = "selectedRooms";
    public static final String PROPERTYNAME_SELECTED_USERS = "selectedUsers";
    public static final String PROPERTYNAME_THIS_HOST = "thisHost";
    public static final String PROPERTYNAME_THIS_USER = "thisUser";
    private static final long serialVersionUID = 2084983597497815883L;
    private List<Group> selectedGroups;
    private Host selectedHost;
    private List<Room> selectedRooms;
    private List<User> selectedUsers;
    private boolean serverProfilesAllowed = true;
    private Host thisHost;
    private User thisUser;

    public static synchronized Session getInstance() {
        if (instance == null) {
            instance = new Session();
            instance.bind();
        }
        return instance;
    }

    private Session() {
    }

    private void bind() {
        Auth.getInstance().addPropertyChangeListener(PROPERTYNAME_THIS_USER, new PropertyChangeListener(){

            @Override
            public void propertyChange(PropertyChangeEvent _evt) {
                if (RepositoryContext.getDefaultUsers().isLoaded()) {
                    Session.this.setThisUser((User)RepositoryContext.getDefaultUsers().getById(Auth.getInstance().getUser()));
                }
            }
        });
        RepositoryContext.getDefaultHosts().addPropertyChangeListener("loaded", new PropertyChangeListener(){

            @Override
            public void propertyChange(PropertyChangeEvent _evt) {
                String hostname = NetUtils.getHostName();
                if (hostname == null) {
                    Session.this.setSelectedHost(null);
                    Session.this.setSelectedRooms(null);
                    Session.this.setThisHost(null);
                    return;
                }
                Host host = (Host)RepositoryContext.getDefaultHosts().getById(hostname.toLowerCase());
                Session.this.setThisHost(host);
                Session.this.setSelectedHost(host);
                if (host == null) {
                    Session.this.setSelectedRooms(null);
                    return;
                }
                if (RepositoryContext.getDefaultRooms().isLoaded()) {
                    for (Room room : RepositoryContext.getDefaultRooms().getBeans()) {
                        List<Host> member = room.getMember();
                        if (member == null || member.isEmpty() || !member.contains(host)) continue;
                        Session.this.setSelectedRooms(Collections.singletonList(room));
                        break;
                    }
                }
            }
        });
        RepositoryContext.getDefaultRooms().addPropertyChangeListener("loaded", new PropertyChangeListener(){

            @Override
            public void propertyChange(PropertyChangeEvent _evt) {
                Host host = Session.this.getThisHost();
                if (host == null) {
                    Session.this.setSelectedRooms(null);
                    return;
                }
                for (Room room : RepositoryContext.getDefaultRooms().getBeans()) {
                    List<Host> member = room.getMember();
                    if (member == null || member.isEmpty() || !member.contains(host)) continue;
                    Session.this.setSelectedRooms(Collections.singletonList(room));
                    break;
                }
            }
        });
        RepositoryContext.getDefaultUsers().addPropertyChangeListener("loaded", new PropertyChangeListener(){

            @Override
            public void propertyChange(PropertyChangeEvent _evt) {
                Session.this.setThisUser((User)RepositoryContext.getDefaultUsers().getById(Auth.getInstance().getUser()));
            }
        });
    }

    public List<Group> getSelectedGroups() {
        return this.selectedGroups;
    }

    public Host getSelectedHost() {
        return this.selectedHost;
    }

    public List<Room> getSelectedRooms() {
        return this.selectedRooms;
    }

    public List<User> getSelectedUsers() {
        return this.selectedUsers;
    }

    public Host getThisHost() {
        return this.thisHost;
    }

    public User getThisUser() {
        return this.thisUser;
    }

    public boolean isServerProfilesAllowed() {
        return this.serverProfilesAllowed;
    }

    public void setSelectedGroups(List<Group> _selectedGroups) {
        List<Group> old = this.selectedGroups;
        this.selectedGroups = _selectedGroups;
        this.firePropertyChange(PROPERTYNAME_SELECTED_GROUPS, old, this.selectedGroups);
    }

    public void setSelectedHost(Host _selectedHost) {
        Host old = this.selectedHost;
        this.selectedHost = _selectedHost == null ? this.thisHost : _selectedHost;
        this.firePropertyChange(PROPERTYNAME_SELECTED_HOST, (Object)old, (Object)this.selectedHost);
    }

    public void setSelectedRooms(List<Room> _selectedRooms) {
        List<Room> old = this.selectedRooms;
        this.selectedRooms = _selectedRooms;
        this.firePropertyChange(PROPERTYNAME_SELECTED_ROOMS, old, this.selectedRooms);
    }

    public void setSelectedUsers(List<User> _selectedUsers) {
        List<User> old = this.selectedUsers;
        this.selectedUsers = _selectedUsers;
        this.firePropertyChange(PROPERTYNAME_SELECTED_USERS, old, this.selectedUsers);
    }

    public void setServerProfilesAllowed(boolean _serverProfilesAllowed) {
        this.serverProfilesAllowed = _serverProfilesAllowed;
    }

    public void setThisHost(Host _thisHost) {
        Host old = this.thisHost;
        this.thisHost = _thisHost;
        this.firePropertyChange(PROPERTYNAME_THIS_HOST, (Object)old, (Object)this.thisHost);
    }

    public void setThisUser(User _thisUser) {
        User old = this.thisUser;
        this.thisUser = _thisUser;
        this.firePropertyChange(PROPERTYNAME_THIS_USER, (Object)old, (Object)this.thisUser);
    }

}

