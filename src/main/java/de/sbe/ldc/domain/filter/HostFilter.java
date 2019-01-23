/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.filter;

import de.sbe.ldc.domain.Host;
import de.sbe.ldc.domain.HostInfo;
import de.sbe.ldc.domain.Role;
import de.sbe.ldc.domain.User;
import de.sbe.ldc.domain.filter.RoleFilter;
import de.sbe.utils.filter.Filter;
import java.util.List;

public abstract class HostFilter
implements Filter<Host> {
    public static final HostFilter COMPUTER = new HostFilter(){

        @Override
        public boolean include(Host _obj) {
            return Host.HostType.COMPUTER.equals((Object)_obj.getObjectSubType());
        }
    };
    public static final HostFilter COMPUTER_NOSHOW;
    public static final String NAME_COMPUTER_TYPE = "computer";
    public static final String NAME_NETBOOK_TYPE = "netbook";
    public static final String NAME_NOTEBOOK_TYPE = "notebook";
    public static final HostFilter NETBOOK;
    public static final HostFilter NETBOOK_NOSHOW;
    public static final HostFilter NOTEBOOK;
    public static final HostFilter NOTEBOOK_NOSHOW;

    HostFilter() {
    }

    static {
        NOTEBOOK = new HostFilter(){

            @Override
            public boolean include(Host _obj) {
                return Host.HostType.NOTEBOOK.equals((Object)_obj.getObjectSubType());
            }
        };
        NETBOOK = new HostFilter(){

            @Override
            public boolean include(Host _obj) {
                return Host.HostType.NETBOOK.equals((Object)_obj.getObjectSubType());
            }
        };
        COMPUTER_NOSHOW = new HostFilter(){

            @Override
            public boolean include(Host _obj) {
                return Host.HostType.COMPUTER_NOSHOW.equals((Object)_obj.getObjectSubType());
            }
        };
        NOTEBOOK_NOSHOW = new HostFilter(){

            @Override
            public boolean include(Host _obj) {
                return Host.HostType.NOTEBOOK_NOSHOW.equals((Object)_obj.getObjectSubType());
            }
        };
        NETBOOK_NOSHOW = new HostFilter(){

            @Override
            public boolean include(Host _obj) {
                return Host.HostType.NETBOOK_NOSHOW.equals((Object)_obj.getObjectSubType());
            }
        };
    }

    public static final class ByRole
    extends HostFilter {
        private final boolean defaultIfHostIsEmpty;
        private final RoleFilter filter;

        public ByRole(boolean _defaultIfHostIsEmpty, RoleFilter _filter) {
            this.defaultIfHostIsEmpty = _defaultIfHostIsEmpty;
            this.filter = _filter;
        }

        @Override
        public boolean include(Host _obj) {
            if (this.filter == null) {
                return false;
            }
            List<User> users = _obj.getHostInfo().getUsers();
            if (users == null || users.isEmpty()) {
                return this.defaultIfHostIsEmpty;
            }
            for (User user : users) {
                if (!this.filter.include(user.getRole())) continue;
                return true;
            }
            return false;
        }
    }

}

