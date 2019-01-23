/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.ssm;

import de.sbe.ldc.domain.Contact;
import de.sbe.ldc.domain.Group;
import de.sbe.ldc.domain.Session;
import de.sbe.ldc.domain.User;
import de.sbe.ldc.domain.comparator.ContactComparator;
import de.sbe.ldc.domain.comparator.UserComparator;
import de.sbe.ldc.domain.repository.RepositoryContext;
import de.sbe.ldc.domain.repository.SsmActionRepository;
import de.sbe.ldc.domain.rpc.Impersonator;
import de.sbe.ldc.domain.ssm.SsmAction;
import de.sbe.ldc.domain.ssm.SsmCloseAction;
import de.sbe.ldc.domain.ssm.SsmEntry;
import de.sbe.ldc.domain.ssm.SsmForwardAction;
import de.sbe.ldc.resources.I18N;
import de.sbe.ldc.ui.shared.converter.ImpersonatorConverter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class SsmFactory {
    public static void addNewAction(SsmEntry _entry, SsmAction _action) {
        _entry.addNewAction(_action);
        _action.setEntry(_entry);
        if (_action instanceof SsmForwardAction) {
            _entry.setPersonInChargeName(((SsmForwardAction)_action).getReceiver());
        } else {
            _entry.setPersonInChargeName(_action.getCreatorName());
        }
        if (_action instanceof SsmCloseAction) {
            _entry.setState(SsmEntry.State.CLOSED);
        } else {
            _entry.setState(SsmEntry.State.IN_PROGRESS);
        }
        _entry.setTsUpdated(_action.getTsCreated());
    }

    public static SsmAction createAction() {
        return SsmFactory.setDefaults(new SsmAction());
    }

    public static SsmForwardAction createAssumeAction() {
        SsmForwardAction action = (SsmForwardAction)SsmFactory.setDefaults(new SsmForwardAction());
        action.setReceiver(Session.getInstance().getThisUser());
        return action;
    }

    public static SsmCloseAction createCloseAction() {
        return (SsmCloseAction)SsmFactory.setDefaults(new SsmCloseAction());
    }

    public static SsmEntry createEntry() {
        return SsmFactory.setDefaults(new SsmEntry());
    }

    public static SsmEntry createEntry(SsmEntry _template) {
        return SsmFactory.setDefaults(_template == null ? new SsmEntry() : _template.clone());
    }

    public static SsmForwardAction createForwardAction() {
        return (SsmForwardAction)SsmFactory.setDefaults(new SsmForwardAction());
    }

    public static Impersonator[] createReceivers(SsmEntry _entry) {
        Object member;
        ArrayList<Impersonator> list = new ArrayList<Impersonator>();
        TreeSet<Contact> contacts = new TreeSet<Contact>(ContactComparator.DISPLAY_NAME);
        TreeSet<User> users = new TreeSet<User>(UserComparator.DISPLAYNAME);
        Group group = (Group)RepositoryContext.getDefaultGroups().getById("support");
        if (group != null && (member = group.getMember()) != null) {
            users.addAll((Collection<User>)member);
        }
        if (_entry != null) {
            for (SsmAction action : _entry.getActions().getBeans()) {
                users.add((User)action.getCreatorName());
            }
        }
        if (Session.getInstance().getThisUser().getGroups().contains(group)) {
            contacts.addAll(RepositoryContext.getDefaultContacts().getBeans());
        }
        users.remove(Session.getInstance().getThisUser());
        list.addAll(users);
        list.addAll(contacts);
        return list.toArray(new Impersonator[list.size()]);
    }

    private static SsmAction setDefaults(SsmAction _action) {
        _action.setCreatorName(Session.getInstance().getThisUser());
        return _action;
    }

    private static SsmEntry setDefaults(SsmEntry _entry) {
        _entry.setCreatorName(Session.getInstance().getThisUser());
        _entry.setPersonInChargeName(_entry.getCreatorName());
        _entry.setState(SsmEntry.State.OPENED);
        SsmAction action = SsmFactory.createAction();
        action.setCreatorName(_entry.getCreatorName());
        action.setEntry(_entry);
        action.setSubject(I18N.getLocalizedString("SsmEntryDialog.new_entry", ImpersonatorConverter.convertFromImpersonator(_entry.getCreatorName())));
        action.setTsCreated(_entry.getTsCreated());
        action.setTsUpdated(_entry.getTsUpdated());
        _entry.addNewAction(action);
        return _entry;
    }
}

