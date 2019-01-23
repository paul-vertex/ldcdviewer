/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.repository;

import de.sbe.ldc.domain.Host;
import de.sbe.ldc.domain.HostImpl;
import de.sbe.ldc.domain.HostInfo;
import de.sbe.ldc.domain.JavaBean;
import de.sbe.ldc.domain.PropertyChangeMediator;
import de.sbe.ldc.domain.repository.AbstractBeanRepository;
import de.sbe.ldc.domain.repository.AddPolicy;
import de.sbe.ldc.domain.repository.AddPolicyAdapter;
import de.sbe.ldc.domain.repository.RemovePolicy;
import de.sbe.ldc.domain.repository.RemovePolicyAdapter;
import de.sbe.ldc.domain.repository.RepositoryContext;
import java.beans.PropertyChangeListener;

public class HostRepository
extends AbstractBeanRepository<Host, String> {
    private static final long serialVersionUID = 7272034883011553421L;
    private AddPolicy<Host, String> addPolicy = new HostAddPolicy(this);
    private RemovePolicy<Host, String> removePolicy = new HostRemovePolicy(this);

    HostRepository(RepositoryContext _context) {
        super(_context, HostImpl.class, String.class);
    }

    @Override
    protected AddPolicy<Host, String> getAddPolicy() {
        return this.addPolicy;
    }

    @Override
    protected String getId(Host _instance) {
        return _instance.getCn();
    }

    @Override
    public String getIdName() {
        return "cn";
    }

    @Override
    protected RemovePolicy<Host, String> getRemovePolicy() {
        return this.removePolicy;
    }

    @Override
    protected void setId(Host _instance, String _id) {
        _instance.setCn(_id);
    }

    private static class HostRemovePolicy
    extends RemovePolicyAdapter<Host, String> {
        HostRemovePolicy(AbstractBeanRepository<Host, String> _repo) {
            super(_repo);
        }

        @Override
        public void remove(Host _bean) {
            _bean.getHostInfo().removePropertyChangeListener((PropertyChangeListener)this.getRepo().getContext().getPropertyChangeMediator());
            super.remove(_bean);
        }
    }

    private static final class HostAddPolicy
    extends AddPolicyAdapter<Host, String> {
        HostAddPolicy(AbstractBeanRepository<Host, String> _repo) {
            super(_repo);
        }

        @Override
        public void add(Host _bean) {
            _bean.getHostInfo().addPropertyChangeListener((PropertyChangeListener)this.getRepo().getContext().getPropertyChangeMediator());
            super.add(_bean);
        }
    }

}

