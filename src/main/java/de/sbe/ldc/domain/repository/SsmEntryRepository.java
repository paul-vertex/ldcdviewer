/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.repository;

import de.sbe.ldc.domain.JavaBean;
import de.sbe.ldc.domain.repository.AbstractBeanRepository;
import de.sbe.ldc.domain.repository.RepositoryContext;
import de.sbe.ldc.domain.ssm.SsmEntry;

public class SsmEntryRepository
extends AbstractBeanRepository<SsmEntry, Long> {
    private static final long serialVersionUID = 1L;

    public SsmEntryRepository() {
        super(RepositoryContext.getInstance(), SsmEntry.class, Long.class);
    }

    @Override
    protected Long getId(SsmEntry _instance) {
        return _instance.getId();
    }

    @Override
    public String getIdName() {
        return "id";
    }

    @Override
    protected void setId(SsmEntry _instance, Long _id) {
        _instance.setId(_id);
    }
}

