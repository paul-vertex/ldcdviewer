/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.google.gson.FieldNamingStrategy
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 */
package de.sbe.ldc.persistence.protocol;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.sbe.ldc.persistence.morpher.deserializer.EnumDeserializer;
import de.sbe.ldc.persistence.morpher.deserializer.JsonObjectDeserializer;
import de.sbe.ldc.persistence.protocol.ActionStatusProcessor;
import de.sbe.ldc.persistence.protocol.JsonProcessorAdapter;
import de.sbe.ldc.persistence.protocol.ResponseAction;
import de.sbe.ldc.persistence.protocol.ResponseStatus;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ActionStatusProcessorAdapter
extends JsonProcessorAdapter
implements ActionStatusProcessor {
    public static final String PROPERTYNAME_ACTIONS = "actions";
    private LinkedList<ResponseAction> actions;
    private Gson gson;
    private ResponseAction last;
    private List<ResponseStatus> status;
    private PropertyChangeSupport support;

    private void addAction(ResponseAction _action) {
        this.actions.add(_action);
        this.support.fireIndexedPropertyChange(PROPERTYNAME_ACTIONS, this.actions.size(), null, _action);
    }

    public void addPropertyChangeListener(PropertyChangeListener _listener) {
        this.support.addPropertyChangeListener(_listener);
    }

    public void addPropertyChangeListener(String _propertyName, PropertyChangeListener _listener) {
        this.support.addPropertyChangeListener(_propertyName, _listener);
    }

    protected void doProcessAction(ResponseAction _action) {
    }

    protected void doProcessStatus(ResponseStatus _status) {
    }

    protected void doSetUp() {
    }

    protected void doTearDown() {
    }

    private ResponseAction findParent(ResponseAction _maybeParent, ResponseAction _action) {
        if (_maybeParent.getAction().equals(_action.getAction())) {
            return _maybeParent;
        }
        if (_maybeParent.getChildren() != null && !_maybeParent.getChildren().isEmpty()) {
            return this.findParent(_maybeParent.getChildren().getLast(), _action);
        }
        return null;
    }

    public final List<ResponseStatus> getCumulativeStates() {
        ArrayList<ResponseStatus> list = new ArrayList<ResponseStatus>();
        if (this.status != null) {
            list.addAll(this.status);
        }
        if (this.actions != null) {
            for (ResponseAction action : this.actions) {
                list.addAll(action.getCumulativeStatus());
            }
        }
        return list;
    }

    public ResponseStatus getStatus() {
        ResponseStatus result = null;
        for (ResponseStatus state : this.status) {
            if (result == null) {
                result = state;
            } else if (result.getType().getSeverity() < state.getType().getSeverity()) {
                result = state;
            }
            if (!ResponseStatus.isFatal(result)) continue;
            break;
        }
        if (!ResponseStatus.isFatal(result)) {
            for (ResponseAction action : this.actions) {
                if (result == null) {
                    result = action.getStatus();
                } else if (result.getType().getSeverity() < action.getStatus().getType().getSeverity()) {
                    result = action.getStatus();
                }
                if (!ResponseStatus.isFatal(result)) continue;
                break;
            }
        }
        return result;
    }

    public boolean hasErrors() {
        ResponseStatus result = this.getStatus();
        return ResponseStatus.isError(result) || ResponseStatus.isFatal(result);
    }

    @Override
    public final void processAction(ResponseAction _action) {
        if (_action.getSubAction() == null) {
            this.addAction(_action);
        } else if (this.actions.isEmpty()) {
            this.addAction(_action);
        } else {
            ResponseAction parent = this.findParent(this.actions.getLast(), _action);
            if (parent == null) {
                this.addAction(_action);
            } else {
                parent.addSub(_action);
            }
        }
        if (this.last != null && this.last.getStatus() == null) {
            this.last.setStatus(ResponseStatus.SUCCESS);
        }
        this.last = _action;
        this.doProcessAction(_action);
    }

    @Override
    public final void processJson(JsonArray _array) {
    }

    @Override
    public final void processJson(JsonObject _object) {
        if (_object.has("action")) {
            ResponseAction action = (ResponseAction)this.gson.fromJson((JsonElement)_object, ResponseAction.class);
            action.setOrig(_object);
            this.processAction(action);
        } else if (_object.has("status")) {
            this.processStatus((ResponseStatus)this.gson.fromJson((JsonElement)_object, ResponseStatus.class));
        }
    }

    @Override
    public final void processStatus(ResponseStatus _status) {
        if (this.last == null) {
            this.status.add(_status);
        } else {
            this.last.setStatus(_status);
        }
        this.doProcessStatus(_status);
    }

    public void removePropertyChangeListener(PropertyChangeListener _listener) {
        this.support.removePropertyChangeListener(_listener);
    }

    public void removePropertyChangeListener(String _propertyName, PropertyChangeListener _listener) {
        this.support.removePropertyChangeListener(_propertyName, _listener);
    }

    @Override
    public final void setUp() {
        this.actions = new LinkedList();
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Enum.class, (Object)new EnumDeserializer());
        builder.registerTypeAdapter(JsonObject.class, (Object)new JsonObjectDeserializer());
        builder.setFieldNamingStrategy(new FieldNamingStrategy(){

            public String translateName(Field _f) {
                return _f.getName().equals("subAction") ? "subaction" : (_f.getName().equals("type") ? "status" : _f.getName());
            }
        });
        this.gson = builder.create();
        this.status = new ArrayList<ResponseStatus>();
        this.support = new PropertyChangeSupport(this);
        this.doSetUp();
    }

    @Override
    public final void tearDown() {
        if (this.last != null && this.last.getStatus() == null) {
            this.last.setStatus(ResponseStatus.SUCCESS);
        }
        this.doTearDown();
    }

}

