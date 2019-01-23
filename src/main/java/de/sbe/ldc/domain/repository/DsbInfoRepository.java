/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.repository;

import de.sbe.ldc.domain.DsbInfo;
import de.sbe.ldc.domain.JavaBean;
import de.sbe.ldc.domain.repository.AbstractBeanRepository;
import de.sbe.ldc.domain.repository.RepositoryContext;

public class DsbInfoRepository
extends AbstractBeanRepository<DsbInfo, String> {
    private static final long serialVersionUID = 6174418420352419005L;

    protected DsbInfoRepository(RepositoryContext context) {
        super(context, DsbInfo.class, String.class);
    }

    @Override
    protected String getId(DsbInfo _instance) {
        return _instance.getId();
    }

    @Override
    public String getIdName() {
        return "id";
    }

    @Override
    protected void setId(DsbInfo _instance, String _id) {
        _instance.setId(_id);
    }
}

