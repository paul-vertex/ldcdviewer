/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.repository;

import de.sbe.ldc.domain.JavaBean;
import de.sbe.ldc.domain.PropertyChangeMediator;
import de.sbe.ldc.domain.repository.AbstractBeanRepository;
import de.sbe.ldc.domain.repository.AddPolicy;
import de.sbe.ldc.domain.repository.RepositoryContext;
import de.sbe.utils.logging.LogUtils;
import java.beans.PropertyChangeListener;
import java.util.logging.Logger;

class AddPolicyAdapter<B extends JavaBean, I>
implements AddPolicy<B, I> {
    protected final Logger logger = LogUtils.getLogger(this.getClass());
    private final AbstractBeanRepository<B, I> repo;

    AddPolicyAdapter(AbstractBeanRepository<B, I> _repo) {
        this.repo = _repo;
    }

    @Override
    public void add(B _bean) {
        _bean.addPropertyChangeListener(this.repo.getContext().getPropertyChangeMediator());
    }

    public AbstractBeanRepository<B, I> getRepo() {
        return this.repo;
    }
}

