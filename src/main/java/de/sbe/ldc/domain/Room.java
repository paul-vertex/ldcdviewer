/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.AbstractLdapBean;
import de.sbe.ldc.domain.Host;
import de.sbe.ldc.domain.HostInfo;
import de.sbe.ldc.domain.RoomInfo;
import de.sbe.ldc.domain.RoomLayout;
import java.util.Collections;
import java.util.List;

public interface Room
extends AbstractLdapBean {
    public static final List<Room> EMPTY_ROOM_LIST = Collections.emptyList();
    public static final String PROPERTYNAME_CN = "cn";
    public static final String PROPERTYNAME_MEMBER = "member";

    public void addMember(Host var1);

    public boolean containsMember(Host var1);

    public String getCn();

    public RoomLayout getCsettings();

    public List<HostInfo> getHostInfos();

    public List<Host> getMember();

    public RoomInfo getRoomInfo();

    public void removeMember(Host var1);

    public void setCn(String var1);

    public void setCsettings(RoomLayout var1);

    public void setMember(List<Host> var1);
}

