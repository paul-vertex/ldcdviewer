/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.repository;

import de.sbe.ldc.domain.PropertyChangeMediator;
import de.sbe.ldc.domain.repository.ContactRepository;
import de.sbe.ldc.domain.repository.DsbInfoRepository;
import de.sbe.ldc.domain.repository.ExamRepository;
import de.sbe.ldc.domain.repository.GroupRepository;
import de.sbe.ldc.domain.repository.HostRepository;
import de.sbe.ldc.domain.repository.RoleRepository;
import de.sbe.ldc.domain.repository.RoomRepository;
import de.sbe.ldc.domain.repository.UserListRepository;
import de.sbe.ldc.domain.repository.UserRepository;

public final class RepositoryContext {
    private static RepositoryContext instance;
    private ContactRepository contacts;
    private DsbInfoRepository dsbInfos;
    private ExamRepository exams;
    private GroupRepository groups;
    private HostRepository hosts;
    private PropertyChangeMediator propertyChangeMediator;
    private RoleRepository roles;
    private RoomRepository rooms;
    private UserListRepository userLists;
    private UserRepository users;

    public static ContactRepository getDefaultContacts() {
        return RepositoryContext.getInstance().getContacts();
    }

    public static DsbInfoRepository getDefaultDsbInfos() {
        return RepositoryContext.getInstance().getDsbInfos();
    }

    public static ExamRepository getDefaultExams() {
        return RepositoryContext.getInstance().getExams();
    }

    public static GroupRepository getDefaultGroups() {
        return RepositoryContext.getInstance().getGroups();
    }

    public static HostRepository getDefaultHosts() {
        return RepositoryContext.getInstance().getHosts();
    }

    public static RoleRepository getDefaultRoles() {
        return RepositoryContext.getInstance().getRoles();
    }

    public static RoomRepository getDefaultRooms() {
        return RepositoryContext.getInstance().getRooms();
    }

    public static UserListRepository getDefaultUserLists() {
        return RepositoryContext.getInstance().getUserLists();
    }

    public static UserRepository getDefaultUsers() {
        return RepositoryContext.getInstance().getUsers();
    }

    public static synchronized RepositoryContext getInstance() {
        if (instance == null) {
            instance = new RepositoryContext();
        }
        return instance;
    }

    public synchronized ContactRepository getContacts() {
        if (this.contacts == null) {
            this.contacts = new ContactRepository(this);
        }
        return this.contacts;
    }

    public synchronized DsbInfoRepository getDsbInfos() {
        if (this.dsbInfos == null) {
            this.dsbInfos = new DsbInfoRepository(this);
        }
        return this.dsbInfos;
    }

    public synchronized ExamRepository getExams() {
        if (this.exams == null) {
            this.exams = new ExamRepository(this);
        }
        return this.exams;
    }

    public synchronized GroupRepository getGroups() {
        if (this.groups == null) {
            this.groups = new GroupRepository(this);
        }
        return this.groups;
    }

    public synchronized HostRepository getHosts() {
        if (this.hosts == null) {
            this.hosts = new HostRepository(this);
        }
        return this.hosts;
    }

    public synchronized PropertyChangeMediator getPropertyChangeMediator() {
        if (this.propertyChangeMediator == null) {
            this.propertyChangeMediator = new PropertyChangeMediator();
        }
        return this.propertyChangeMediator;
    }

    public synchronized RoleRepository getRoles() {
        if (this.roles == null) {
            this.roles = new RoleRepository(this);
        }
        return this.roles;
    }

    public synchronized RoomRepository getRooms() {
        if (this.rooms == null) {
            this.rooms = new RoomRepository(this);
        }
        return this.rooms;
    }

    public synchronized UserListRepository getUserLists() {
        if (this.userLists == null) {
            this.userLists = new UserListRepository(this);
        }
        return this.userLists;
    }

    public synchronized UserRepository getUsers() {
        if (this.users == null) {
            this.users = new UserRepository(this);
        }
        return this.users;
    }
}

