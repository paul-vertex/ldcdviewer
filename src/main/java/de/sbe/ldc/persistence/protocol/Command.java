/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.persistence.protocol;

public enum Command {
    AUTH_AUTO,
    AUTH_DSB,
    AUTH_LOGIN,
    AUTH_SESSION,
    COLLECT_FILES,
    DETECT_CHARSET,
    DISTRIBUTE_ACTIVATE,
    DISTRIBUTE_CLEANUP,
    DISTRIBUTE_FILES,
    DISTRIBUTE_INFO,
    DISTRIBUTE_UPLOAD,
    DSB_AUTH,
    DSB_INFO,
    EXAM_LIST,
    EXAM_OFF,
    EXAM_ON,
    GROUP_ADD,
    GROUP_ADDMEMBER,
    GROUP_DELETE,
    GROUP_DELMEMBER,
    GROUP_LIST,
    GROUP_MODIFY,
    GROUP_MOVEMEMBER,
    GROUP_RENAME,
    HELO,
    HOST_ADD,
    HOST_BEAM,
    HOST_DELETE,
    HOST_LIST,
    HOST_LOGOFF,
    HOST_MODIFY,
    HOST_MONITOR,
    HOST_REBOOT,
    HOST_RENAME,
    HOST_RESET,
    HOST_SHUTDOWN,
    HOST_WAKE,
    INPUT_OFF,
    INPUT_ON,
    INTERNET_OFF,
    INTERNET_ON,
    INTRANET_OFF,
    INTRANET_ON,
    MYSHN_CMD,
    OBJECT_DEVICE_ADD,
    OBJECT_DEVICE_DELETE,
    OBJECT_DEVICE_GET_INVENTORY,
    OBJECT_DEVICE_GET_INVENTORY_INFO,
    OBJECT_DEVICE_MODIFY,
    OBJECT_DEVICE_MODIFY_INVENTORY,
    PING,
    PRINTER_OFF,
    PRINTER_ON,
    PWGEN,
    QUIT,
    ROLE_LIST,
    ROOM_ADD,
    ROOM_DELETE,
    ROOM_LIST,
    ROOM_MODIFY,
    ROOM_REBOOT,
    ROOM_RENAME,
    ROOM_SHUTDOWN,
    ROOM_WAKE,
    RPC,
    SCREEN_OFF,
    SCREEN_ON,
    SESSIONKEY,
    STARTTLS,
    STATE_DEFAULTS,
    STATE_GET,
    STATE_SET,
    TICKER,
    USB_OFF,
    USB_ON,
    USER_ADD,
    USER_DELETE,
    USER_IMPORT,
    USER_LIST,
    USER_MODIFY,
    USER_RENAME,
    USERLIST_ADD,
    USERLIST_DELETE,
    USERLIST_LIST,
    USERLIST_MODIFY,
    WEBAPI_REQUEST,
    WEBFILTER_LOGS_LIST,
    WEBFILTER_LOGS_UPDATE,
    WEBFILTER_OFF,
    WEBFILTER_ON,
    WEBFILTER_PHRASEFILTER_DISABLE_CATEGORIES,
    WEBFILTER_PHRASEFILTER_ENABLE_CATEGORIES,
    WEBFILTER_PHRASEFILTER_GET_CATEGORIES,
    WEBFILTER_PHRASEFILTER_GET_ENTRIES,
    WEBFILTER_URLFILTER_ADD_URL,
    WEBFILTER_URLFILTER_DELETE_URL,
    WEBFILTER_URLFILTER_DISABLE_CATEGORIES,
    WEBFILTER_URLFILTER_ENABLE_CATEGORIES,
    WEBFILTER_URLFILTER_GET_CATEGORIES,
    WEBFILTER_URLFILTER_GET_ENTRIES,
    WEBFILTER_URLLIST_ADD_URL;
    
    public static final String KEY_ACTION = "action";
    public static final String KEY_ATTRS = "attrs";
    public static final String KEY_BEAMER = "beamer";
    public static final String KEY_CMD = "cmd";
    public static final String KEY_CN = "cn";
    public static final String KEY_DATA = "data";
    public static final String KEY_DELETE = "delete";
    public static final String KEY_DN = "dn";
    public static final String KEY_EXPIRES = "expires";
    public static final String KEY_HOST = "host";
    public static final String KEY_HOSTS = "hosts";
    public static final String KEY_ID = "id";
    public static final String KEY_LANG = "lang";
    public static final String KEY_MEMBER = "member";
    public static final String KEY_MEMBER_OF = "member_of";
    public static final String KEY_NEW_GROUP = "new_group";
    public static final String KEY_OBJECT_SUB_TYPE = "objectsubtype";
    public static final String KEY_OLD_GROUP = "old_group";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PORT = "port";
    public static final String KEY_ROOM = "room";
    public static final String KEY_ROOMS = "rooms";
    public static final String KEY_RPC_REQUEST = "rpc_request";
    public static final String KEY_RPC_VERSION = "rpc_version";
    public static final String KEY_STATE = "state";
    public static final String KEY_STATEID = "stateid";
    public static final String KEY_TEXT = "text";
    public static final String KEY_TYPE = "type";
    public static final String KEY_UID = "uid";
    public static final String KEY_USER = "user";
    public static final String KEY_USERS = "users";
    public static final String KEY_VERSION = "version";
    public static final String OPTION_A = "-a";
    public static final String OPTION_B = "-b";
    public static final String OPTION_DRY_RUN = "--dryrun";
    public static final String RPC_VERSION = "1.0";
    private final String template = this.name().replaceAll("_", " ");

    public String getTemplate() {
        return this.template;
    }
}

