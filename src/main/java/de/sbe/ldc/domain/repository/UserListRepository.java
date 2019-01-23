/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.repository;

import de.sbe.ldc.domain.JavaBean;
import de.sbe.ldc.domain.UserList;
import de.sbe.ldc.domain.repository.AbstractBeanRepository;
import de.sbe.ldc.domain.repository.RepositoryContext;

public class UserListRepository
extends AbstractBeanRepository<UserList, String> {
    private static final long serialVersionUID = 2449366218679439676L;

    UserListRepository(RepositoryContext _context) {
        super(_context, UserList.class, String.class);
    }

    @Override
    protected String getId(UserList _instance) {
        return _instance.getId();
    }

    @Override
    public String getIdName() {
        return "id";
    }

    @Override
    protected void setId(UserList _instance, String _id) {
        _instance.setId(_id);
    }
}

