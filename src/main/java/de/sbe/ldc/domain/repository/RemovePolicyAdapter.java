/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.repository;

import de.sbe.ldc.domain.JavaBean;
import de.sbe.ldc.domain.PropertyChangeMediator;
import de.sbe.ldc.domain.repository.AbstractBeanRepository;
import de.sbe.ldc.domain.repository.RemovePolicy;
import de.sbe.ldc.domain.repository.RepositoryContext;
import de.sbe.utils.logging.LogUtils;
import java.beans.PropertyChangeListener;
import java.util.logging.Logger;

class RemovePolicyAdapter<B extends JavaBean, I>
implements RemovePolicy<B, I> {
    protected final Logger logger = LogUtils.getLogger(this.getClass());
    private final AbstractBeanRepository<B, I> repo;

    RemovePolicyAdapter(AbstractBeanRepository<B, I> _repo) {
        this.repo = _repo;
    }

    public AbstractBeanRepository<B, I> getRepo() {
        return this.repo;
    }

    @Override
    public void remove(B _bean) {
        _bean.removePropertyChangeListener(this.repo.getContext().getPropertyChangeMediator());
    }
}

