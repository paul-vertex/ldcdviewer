/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.repository;

import de.sbe.ldc.domain.Group;
import de.sbe.ldc.domain.JavaBean;
import de.sbe.ldc.domain.User;
import de.sbe.ldc.domain.UserImpl;
import de.sbe.ldc.domain.repository.AbstractBeanRepository;
import de.sbe.ldc.domain.repository.RemovePolicy;
import de.sbe.ldc.domain.repository.RemovePolicyAdapter;
import de.sbe.ldc.domain.repository.RepositoryContext;
import java.util.Collections;
import java.util.List;

public final class UserRepository
extends AbstractBeanRepository<User, String> {
    private static final long serialVersionUID = 6769639293618668562L;
    private final transient RemovePolicy<User, String> removePolicy = new UserRemovePolicy(this);

    UserRepository(RepositoryContext _context) {
        super(_context, UserImpl.class, String.class);
    }

    @Override
    protected String getId(User _instance) {
        return _instance.getUid();
    }

    @Override
    public String getIdName() {
        return "uid";
    }

    @Override
    public RemovePolicy<User, String> getRemovePolicy() {
        return this.removePolicy;
    }

    @Override
    protected void setId(User _instance, String _id) {
        _instance.setUid(_id);
    }

    private static final class UserRemovePolicy
    extends RemovePolicyAdapter<User, String> {
        public static final List<Group> EMPTY_GROUP_LIST = Collections.emptyList();

        UserRemovePolicy(AbstractBeanRepository<User, String> _repo) {
            super(_repo);
        }

        @Override
        public void remove(User _user) {
            _user.setGroups(EMPTY_GROUP_LIST);
            super.remove(_user);
        }
    }

}

