/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.ssm;

import de.sbe.ldc.domain.Host;
import de.sbe.ldc.domain.repository.SsmActionRepository;
import de.sbe.ldc.domain.rpc.Impersonator;
import de.sbe.ldc.domain.rpc.RpcObject;
import de.sbe.ldc.domain.ssm.SsmAction;
import de.sbe.ldc.persistence.morpher.RpcScope;
import de.sbe.ldc.persistence.morpher.SerializableProperty;
import java.util.Arrays;
import java.util.List;

public class SsmEntry
extends RpcObject {
    public static final String PROPERTYNAME_ACTIONS = "actions";
    public static final String PROPERTYNAME_CATEGORY = "category";
    public static final String PROPERTYNAME_DESCRIPTION = "description";
    public static final String PROPERTYNAME_OWNER = "owner";
    public static final String PROPERTYNAME_PERSON_IN_CHARGE_NAME = "personInChargeName";
    public static final String PROPERTYNAME_PRIORITY = "priority";
    public static final String PROPERTYNAME_STATE = "state";
    public static final String PROPERTYNAME_SUBCATEGORY = "subcategory";
    public static final String PROPERTYNAME_SUBJECT = "subject";
    public static final String SCOPE_DEFAULT = "default";
    private static final long serialVersionUID = 1L;
    private SsmActionRepository actions;
    @SerializableProperty
    @RpcScope(scope="default")
    private Category category;
    @SerializableProperty
    @RpcScope(scope="default")
    private String description;
    @SerializableProperty
    @RpcScope(scope="default")
    private Host owner;
    @SerializableProperty
    @RpcScope(scope="default")
    private Impersonator personInChargeName;
    @SerializableProperty
    @RpcScope(scope="default")
    private Priority priority;
    @SerializableProperty
    @RpcScope(scope="default")
    private State state;
    @SerializableProperty
    @RpcScope(scope="default")
    private Subcategory subcategory;
    @SerializableProperty
    @RpcScope(scope="default")
    private String subject;

    public void addNewAction(SsmAction _action) {
        this.getActions().add(_action);
        _action.setParentId(this.getId());
        this.firePropertyChange(PROPERTYNAME_ACTIONS, null, (Object)this.getActions());
    }

    public SsmEntry clone() {
        SsmEntry clone = new SsmEntry();
        clone.setCreatorId(this.getCreatorId());
        clone.setCreatorName(this.getCreatorName());
        clone.setDescription(this.getDescription());
        clone.setName(this.getName());
        clone.setPersonInChargeName(this.getPersonInChargeName());
        clone.setPriority(this.getPriority());
        clone.setState(this.getState());
        clone.setSubject(this.getSubject());
        clone.setSubcategory(this.getSubcategory());
        clone.setCategory(this.getCategory());
        return clone;
    }

    public SsmActionRepository getActions() {
        if (this.actions == null) {
            this.actions = new SsmActionRepository();
        }
        return this.actions;
    }

    public Category getCategory() {
        return this.category;
    }

    public String getDescription() {
        return this.description;
    }

    public Host getOwner() {
        return this.owner;
    }

    public Impersonator getPersonInChargeName() {
        return this.personInChargeName;
    }

    public Priority getPriority() {
        return this.priority;
    }

    public State getState() {
        return this.state;
    }

    public Subcategory getSubcategory() {
        return this.subcategory;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setActions(SsmActionRepository _actions) {
        this.actions = _actions;
        this.firePropertyChange(PROPERTYNAME_ACTIONS, (Object)this.actions, (Object)this.actions);
    }

    public void setCategory(Category _category) {
        this.category = _category;
        this.firePropertyChange(PROPERTYNAME_CATEGORY, (Object)this.category, (Object)this.category);
    }

    public void setDescription(String _description) {
        this.description = _description;
        this.firePropertyChange(PROPERTYNAME_DESCRIPTION, (Object)this.description, (Object)this.description);
    }

    @Override
    public void setId(Long _id) {
        super.setId(_id);
        for (SsmAction action : this.getActions().getBeans()) {
            action.setParentId(_id);
        }
    }

    public void setOwner(Host _owner) {
        this.owner = _owner;
        this.firePropertyChange(PROPERTYNAME_OWNER, (Object)this.owner, (Object)this.owner);
    }

    public void setPersonInChargeName(Impersonator _personInChargeName) {
        this.personInChargeName = _personInChargeName;
        this.firePropertyChange(PROPERTYNAME_PERSON_IN_CHARGE_NAME, (Object)this.personInChargeName, (Object)this.personInChargeName);
    }

    public void setPriority(Priority _priority) {
        this.priority = _priority;
        this.firePropertyChange(PROPERTYNAME_PRIORITY, (Object)this.priority, (Object)this.priority);
    }

    public void setState(State _state) {
        this.state = _state;
        this.firePropertyChange(PROPERTYNAME_STATE, (Object)this.state, (Object)this.state);
    }

    public void setSubcategory(Subcategory _subcategory) {
        this.subcategory = _subcategory;
        this.firePropertyChange(PROPERTYNAME_SUBCATEGORY, (Object)this.subcategory, (Object)this.subcategory);
    }

    public void setSubject(String _subject) {
        this.subject = _subject;
        this.firePropertyChange(PROPERTYNAME_SUBJECT, (Object)this.subject, (Object)this.subject);
    }

    public static enum Subcategory {
        ACCESSPOINT,
        BEAMER,
        HOST,
        LINUX,
        MISC,
        MYSHN,
        NOTEBOOK,
        PRINTER,
        SCANNER,
        SWITCH,
        WHITEBOARD,
        WINDOWS;
        

        public static List<Subcategory> sortedValues() {
            return Arrays.asList(Subcategory.values());
        }
    }

    public static enum State {
        CLOSED,
        IN_PROGRESS,
        OPENED;
        

        public static List<State> sortedValues() {
            return Arrays.asList(new State[]{OPENED, IN_PROGRESS, CLOSED});
        }
    }

    public static enum Severity {
        ENHANCEMENT,
        MAJOR,
        MINOR,
        NORMAL;
        

        public static List<Severity> sortedValues() {
            return Arrays.asList(new Severity[]{MAJOR, NORMAL, MINOR, ENHANCEMENT});
        }
    }

    public static enum Priority {
        P1,
        P2,
        P3;
        
    }

    public static enum Category {
        HW,
        SW,
        UNKNOWN;
        

        public static List<Category> sortedValues() {
            return Arrays.asList(new Category[]{HW, SW, UNKNOWN});
        }
    }

}

