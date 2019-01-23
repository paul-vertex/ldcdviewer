/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.repository;

import de.sbe.ldc.domain.Group;
import de.sbe.ldc.domain.GroupImpl;
import de.sbe.ldc.domain.JavaBean;
import de.sbe.ldc.domain.User;
import de.sbe.ldc.domain.repository.AbstractBeanRepository;
import de.sbe.ldc.domain.repository.RemovePolicy;
import de.sbe.ldc.domain.repository.RemovePolicyAdapter;
import de.sbe.ldc.domain.repository.RepositoryContext;
import java.util.Collections;
import java.util.List;

public final class GroupRepository
extends AbstractBeanRepository<Group, String> {
    private static final long serialVersionUID = 3513238688308814640L;
    private final RemovePolicy<Group, String> removePolicy = new GroupRemovePolicy(this);

    GroupRepository(RepositoryContext _context) {
        super(_context, GroupImpl.class, String.class);
    }

    @Override
    protected String getId(Group _instance) {
        return _instance.getCn();
    }

    @Override
    public String getIdName() {
        return "cn";
    }

    @Override
    public RemovePolicy<Group, String> getRemovePolicy() {
        return this.removePolicy;
    }

    @Override
    protected void setId(Group _instance, String _id) {
        _instance.setCn(_id);
    }

    private static class GroupRemovePolicy
    extends RemovePolicyAdapter<Group, String> {
        public static final List<User> EMPTY_USER_LIST = Collections.emptyList();

        GroupRemovePolicy(AbstractBeanRepository<Group, String> _repo) {
            super(_repo);
        }

        @Override
        public void remove(Group _group) {
            _group.setMember(EMPTY_USER_LIST);
            super.remove(_group);
        }
    }

}

