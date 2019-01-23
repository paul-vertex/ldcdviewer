/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 */
package de.sbe.ldc.persistence.protocol;

import com.google.gson.JsonObject;
import de.sbe.ldc.domain.AbstractBean;
import de.sbe.ldc.persistence.protocol.ResponseStatus;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ResponseAction
extends AbstractBean {
    public static final String ACTION_ADD_GROUP = "add_group";
    public static final String ACTION_ADD_USER = "add_user";
    public static final String ACTION_CREATE_LDAP_ENTRY = "create_ldap_entry";
    public static final String ACTION_DELETE_GROUP = "delete_group";
    public static final String ACTION_DELETE_USER = "delete_user";
    public static final String ACTION_FILE_EXISTS = "file_exists";
    public static final String ACTION_MODIFY_GROUP = "modify_group";
    public static final String ACTION_MODIFY_USER = "modify_user";
    public static final String ACTION_QUERY = "query";
    public static final String PROPERTYNAME_ACTION = "action";
    public static final String PROPERTYNAME_CHILDREN = "children";
    public static final String PROPERTYNAME_DATA = "data";
    public static final String PROPERTYNAME_ORIG = "orig";
    public static final String PROPERTYNAME_PARENT = "parent";
    public static final String PROPERTYNAME_STATUS = "status";
    public static final String PROPERTYNAME_SUB_ACTION = "subAction";
    public static final String PROPERTYNAME_TEXT = "text";
    private static final long serialVersionUID = -8830099616128761051L;
    private String action;
    private LinkedList<ResponseAction> children;
    private JsonObject orig;
    private ResponseAction parent;
    private ResponseStatus status;
    private String subAction;
    private String text;
    private final long time = System.currentTimeMillis();

    public static boolean isAddGroupAction(String _text) {
        return _text != null && ACTION_ADD_GROUP.equals(_text);
    }

    public static boolean isAddUserAction(String _text) {
        return _text != null && ACTION_ADD_USER.equals(_text);
    }

    public static boolean isDeleteGroupAction(String _text) {
        return _text != null && ACTION_DELETE_GROUP.equals(_text);
    }

    public static boolean isDeleteUserAction(String _text) {
        return _text != null && ACTION_DELETE_USER.equals(_text);
    }

    public static boolean isGroupAction(String _text) {
        return ResponseAction.isAddGroupAction(_text) || ResponseAction.isDeleteGroupAction(_text);
    }

    public static boolean isModifyGroupAction(String _text) {
        return _text != null && ACTION_MODIFY_GROUP.equals(_text);
    }

    public static boolean isModifyUserAction(String _text) {
        return _text != null && ACTION_MODIFY_USER.equals(_text);
    }

    public static boolean isQueryAction(String _text) {
        return _text != null && ACTION_QUERY.equals(_text);
    }

    public static boolean isUserAction(String _text) {
        return ResponseAction.isAddUserAction(_text) || ResponseAction.isDeleteUserAction(_text) || ResponseAction.isModifyUserAction(_text);
    }

    public void addSub(ResponseAction _sub) {
        _sub.setParent(this);
        if (this.children == null) {
            this.children = new LinkedList();
        }
        this.children.add(_sub);
        this.fireIndexedPropertyChange(PROPERTYNAME_CHILDREN, this.children.size(), null, (Object)_sub);
    }

    public String getAction() {
        return this.action;
    }

    public LinkedList<ResponseAction> getChildren() {
        return this.children;
    }

    public List<ResponseStatus> getCumulativeStatus() {
        ArrayList<ResponseStatus> list = new ArrayList<ResponseStatus>();
        if (this.status != null) {
            list.add(this.status);
        }
        if (this.children != null) {
            for (ResponseAction kid : this.children) {
                List<ResponseStatus> kidList = kid.getCumulativeStatus();
                if (kidList.isEmpty()) continue;
                list.addAll(kidList);
            }
        }
        return list;
    }

    public JsonObject getOrig() {
        return this.orig;
    }

    public ResponseAction getParent() {
        return this.parent;
    }

    public ResponseStatus getStatus() {
        ResponseStatus result = this.status;
        if (this.children != null && !this.children.isEmpty()) {
            for (ResponseAction kid : this.children) {
                if (result == null) {
                    result = kid.getStatus();
                } else if (kid.getStatus() != null && result.getType().getSeverity() < kid.getStatus().getType().getSeverity()) {
                    result = kid.getStatus();
                }
                if (!ResponseStatus.isFatal(result)) continue;
                break;
            }
        }
        return result;
    }

    public String getSubAction() {
        return this.subAction;
    }

    public String getText() {
        return this.text;
    }

    public long getTime() {
        return this.time;
    }

    public void setAction(String _action) {
        String old = this.action;
        this.action = _action;
        this.firePropertyChange(PROPERTYNAME_ACTION, (Object)old, (Object)this.action);
    }

    public void setOrig(JsonObject _orig) {
        JsonObject old = this.orig;
        this.orig = _orig;
        this.firePropertyChange(PROPERTYNAME_ORIG, (Object)old, (Object)this.orig);
    }

    public void setParent(ResponseAction _parent) {
        ResponseAction old = this.parent;
        this.parent = _parent;
        this.firePropertyChange(PROPERTYNAME_PARENT, (Object)old, (Object)this.parent);
    }

    public void setStatus(ResponseStatus _status) {
        ResponseStatus old = this.status;
        this.status = _status;
        this.firePropertyChange(PROPERTYNAME_STATUS, (Object)old, (Object)this.status);
    }

    public void setSubAction(String _subAction) {
        String old = this.subAction;
        this.subAction = _subAction;
        this.firePropertyChange(PROPERTYNAME_SUB_ACTION, (Object)old, (Object)this.subAction);
    }

    public void setText(String _text) {
        String old = this.text;
        this.text = _text;
        this.firePropertyChange(PROPERTYNAME_TEXT, (Object)old, (Object)this.text);
    }
}

