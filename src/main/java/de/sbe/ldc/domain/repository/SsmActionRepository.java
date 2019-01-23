/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.repository;

import de.sbe.ldc.domain.JavaBean;
import de.sbe.ldc.domain.repository.AbstractBeanRepository;
import de.sbe.ldc.domain.repository.RepositoryContext;
import de.sbe.ldc.domain.ssm.SsmAction;

public class SsmActionRepository
extends AbstractBeanRepository<SsmAction, Long> {
    private static final long serialVersionUID = 1L;

    public SsmActionRepository() {
        super(RepositoryContext.getInstance(), SsmAction.class, Long.class);
    }

    @Override
    protected Long getId(SsmAction _instance) {
        return _instance.getId();
    }

    @Override
    public String getIdName() {
        return "id";
    }

    @Override
    protected void setId(SsmAction _instance, Long _id) {
        _instance.setId(_id);
    }
}

