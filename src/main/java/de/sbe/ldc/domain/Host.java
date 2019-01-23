/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.AbstractLdapBean;
import de.sbe.ldc.domain.HostInfo;
import de.sbe.ldc.domain.Room;
import java.util.Collections;
import java.util.List;

public interface Host
extends AbstractLdapBean {
    public static final List<Host> EMPTY_HOST_LIST = Collections.emptyList();
    public static final String PROPERTYNAME_CN = "cn";
    public static final String PROPERTYNAME_ROOMS = "rooms";

    public void addRoom(Room var1);

    public String getCn();

    public HostInfo getHostInfo();

    public HostType getObjectSubType();

    public List<Room> getRooms();

    public void removeRoom(Room var1);

    public void setCn(String var1);

    public void setObjectSubType(HostType var1);

    public void setRooms(List<Room> var1);

    public static enum HostType {
        ACCESSPOINT,
        ACCESSPOINT_NOSHOW,
        BEAMER,
        BEAMER_NOSHOW,
        BOARD,
        BOARD_NOSHOW,
        COMPUTER,
        COMPUTER_NOSHOW,
        COPIER,
        COPIER_NOSHOW,
        COURSE,
        COURSE_NOSHOW,
        DIB,
        DIB_NOSHOW,
        MAC,
        MAC_NOSHOW,
        MISC,
        MISC_NOSHOW,
        MODEM,
        MODEM_NOSHOW,
        NAS,
        NAS_NOSHOW,
        NETBOOK,
        NETBOOK_NOSHOW,
        NOSHOW,
        NOSHOW_NOSHOW,
        NOTEBOOK,
        NOTEBOOK_NOSHOW,
        PRINTER,
        PRINTER_NOSHOW,
        PRIVACY,
        PRIVACY_NOSHOW,
        ROUTER,
        ROUTER_NOSHOW,
        SCANNER,
        SCANNER_NOSHOW,
        SERVER,
        SERVER_NOSHOW,
        SMARTBOARD,
        SMARTBOARD_NOSHOW,
        SMARTPHONE,
        SMARTPHONE_NOSHOW,
        STUDENT,
        STUDENT_NOSHOW,
        SWITCH,
        SWITCH_NOSHOW,
        TABLET,
        TABLET_NOSHOW,
        TEACHER,
        TEACHER_NOSHOW,
        TUX,
        TUX_NOSHOW,
        VIRTUALMACHINE,
        VIRTUALMACHINE_NOSHOW,
        WHITEBOARD,
        WHITEBOARD_NOSHOW;
        
    }

}

