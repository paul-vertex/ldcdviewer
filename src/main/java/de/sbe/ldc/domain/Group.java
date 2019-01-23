/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.AbstractLdapBean;
import de.sbe.ldc.domain.Clopen;
import de.sbe.ldc.domain.DiskQuota;
import de.sbe.ldc.domain.User;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public interface Group
extends AbstractLdapBean,
DiskQuota {
    public static final List<Group> EMPTY_GROUP_LIST = Collections.emptyList();
    public static final String PROPERTYNAME_CN = "cn";
    public static final String PROPERTYNAME_MEMBER = "member";
    public static final String PROPERTYNAME_OWNER = "owner";
    public static final String PROPERTYNAME_STATE = "state";

    public void addMember(User var1);

    public String getCn();

    public List<User> getMember();

    public List<User> getMember(Comparator<User> var1);

    public String getObjectSubType();

    public User getOwner();

    public Clopen getState();

    public void removeMember(User var1);

    public void setCn(String var1);

    public void setMember(List<User> var1);

    public void setObjectSubType(String var1);

    public void setOwner(User var1);

    public void setState(Clopen var1);
}

