/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.repository;

import de.sbe.ldc.domain.JavaBean;
import de.sbe.ldc.domain.Role;
import de.sbe.ldc.domain.RoleImpl;
import de.sbe.ldc.domain.repository.AbstractBeanRepository;
import de.sbe.ldc.domain.repository.RepositoryContext;

public final class RoleRepository
extends AbstractBeanRepository<Role, String> {
    private static final long serialVersionUID = 4509743250724578987L;

    RoleRepository(RepositoryContext _context) {
        super(_context, RoleImpl.class, String.class);
    }

    @Override
    protected String getId(Role _instance) {
        return _instance.getId();
    }

    @Override
    public String getIdName() {
        return "id";
    }

    @Override
    protected void setId(Role _instance, String _id) {
        _instance.setId(_id);
    }
}

