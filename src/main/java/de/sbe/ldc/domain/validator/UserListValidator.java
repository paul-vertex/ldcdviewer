/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.jgoodies.binding.PresentationModel
 *  com.jgoodies.validation.ValidationMessage
 *  com.jgoodies.validation.ValidationResult
 *  com.jgoodies.validation.Validator
 */
package de.sbe.ldc.domain.validator;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.validation.ValidationMessage;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import de.sbe.ldc.domain.Role;
import de.sbe.ldc.domain.UserList;
import de.sbe.ldc.domain.repository.UserListRepository;
import de.sbe.ldc.utils.ValidationUtils;
import de.sbe.utils.StringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserListValidator
implements Validator<PresentationModel<UserList>> {
    public static final Pattern PATTERN_ID = Pattern.compile("^[a-z][a-z0-9.-]{0,30}$");
    private final UserListRepository repository;

    public UserListValidator(UserListRepository _repository) {
        this.repository = _repository;
    }

    public ValidationResult validate(PresentationModel<UserList> _adapter) {
        String list;
        String id;
        UserList.FileType fileType;
        String delimiter;
        ArrayList<ValidationMessage> messages = new ArrayList<ValidationMessage>();
        String codePage = (String)_adapter.getBufferedValue("originalCodePage");
        if (StringUtils.isEmptyString(codePage)) {
            messages.add((ValidationMessage)ValidationUtils.createErrorMessage("userList.codePage", "validation.userList.codePage.invalid"));
        }
        if (StringUtils.isEmptyString(delimiter = (String)_adapter.getBufferedValue("delimiter"))) {
            messages.add((ValidationMessage)ValidationUtils.createErrorMessage("userList.delimiter", "validation.userList.delimiter.invalid"));
        }
        if ((fileType = (UserList.FileType)((Object)_adapter.getBufferedValue("fileType"))) == null) {
            messages.add((ValidationMessage)ValidationUtils.createErrorMessage("userList.fileType", "validation.userList.fileType.invalid"));
        }
        if (StringUtils.isEmptyString(id = (String)_adapter.getBufferedValue("id")) || !PATTERN_ID.matcher(id).matches()) {
            messages.add((ValidationMessage)ValidationUtils.createErrorMessage("userList.id", "validation.userList.id.invalid"));
        } else {
            UserList other = (UserList)this.repository.getById(id);
            if (other != null && !((UserList)_adapter.getBean()).equals(other)) {
                messages.add((ValidationMessage)ValidationUtils.createErrorMessage("userList.id", "validation.userList.id.duplicate"));
            }
        }
        String initialPassword = (String)_adapter.getBufferedValue("initialPassword");
        if (StringUtils.isEmptyString(initialPassword)) {
            messages.add((ValidationMessage)ValidationUtils.createWarningMessage("userList.initialPassword", "validation.userList.initialPassword.empty"));
        }
        if (StringUtils.isEmptyString(list = (String)_adapter.getBufferedValue("list"))) {
            messages.add((ValidationMessage)ValidationUtils.createWarningMessage("userList.list", "validation.userList.list.empty"));
        } else {
            List<UserList.Column> columns = (List)_adapter.getBufferedValue("column");
            if (columns != null) {
                EnumMap<UserList.Column.ColumnType, Integer> counter = new EnumMap<UserList.Column.ColumnType, Integer>(UserList.Column.ColumnType.class);
                for (UserList.Column column : columns) {
                    if (UserList.Column.ColumnType.SKIP_COLUMN.equals((Object)column.getColumnType())) continue;
                    if (!counter.containsKey((Object)column.getColumnType())) {
                        counter.put(column.getColumnType(), 0);
                    }
                    counter.put(column.getColumnType(), (Integer)counter.get((Object)column.getColumnType()) + 1);
                }
                for (Object i : counter.values()) {
                    if ((Integer)i <= 1) continue;
                    messages.add((ValidationMessage)ValidationUtils.createErrorMessage("userList.list", "validation.userList.column.duplicate"));
                    break;
                }
                if (!UserList.FileType.COURSES.equals((Object)fileType)) {
                    Object i;
                    UserList.Column column;
                    boolean pkeyExists = false;
                    i = columns.iterator();
                    while (((Iterator) i).hasNext() && !(pkeyExists = (column = (UserList.Column)((Iterator) i).next()).isPrimaryKey())) {
                    }
                    if (!pkeyExists) {
                        messages.add((ValidationMessage)ValidationUtils.createErrorMessage("userList.list", "validation.userList.column.pkey_not_set"));
                    }
                }
            }
        }
        Role role = (Role)_adapter.getBufferedValue("role");
        if (role == null) {
            messages.add((ValidationMessage)ValidationUtils.createErrorMessage("userList.role", "validation.userList.role.invalid"));
        }
        return ValidationUtils.createSortedUnmodifiableValidationResult(messages);
    }
}

