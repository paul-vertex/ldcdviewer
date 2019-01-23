/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.jgoodies.binding.PresentationModel
 *  com.jgoodies.validation.ValidationMessage
 *  com.jgoodies.validation.ValidationResult
 *  com.jgoodies.validation.Validator
 *  com.jgoodies.validation.message.SimpleValidationMessage
 */
package de.sbe.ldc.domain.validator;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.validation.ValidationMessage;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.message.SimpleValidationMessage;
import de.sbe.ldc.domain.GenderType;
import de.sbe.ldc.domain.Role;
import de.sbe.ldc.domain.User;
import de.sbe.ldc.domain.filter.RoleFilter;
import de.sbe.ldc.domain.repository.UserRepository;
import de.sbe.ldc.domain.validator.PasswordValidator;
import de.sbe.ldc.utils.ValidationUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator
implements Validator<PresentationModel<User>> {
    public static final Pattern PATTERN_GIVENNAME = Pattern.compile("^[^\\s].*$");
    public static final Pattern PATTERN_SN = Pattern.compile("^[^\\s].*$");
    public static final Pattern PATTERN_UID = Pattern.compile("^[a-z][a-z0-9.-]{0,30}$");
    private final boolean created;
    private boolean isFromUserDialog = false;
    private final UserRepository repository;

    public UserValidator(UserRepository _repository, boolean _created) {
        this.repository = _repository;
        this.created = _created;
        this.isFromUserDialog = false;
    }

    public UserValidator(UserRepository _repository, boolean _created, boolean _isFromUserDialog) {
        this.repository = _repository;
        this.created = _created;
        this.isFromUserDialog = _isFromUserDialog;
    }

    public ValidationResult validate(PresentationModel<User> _adapter) {
        SimpleValidationMessage messagePassword;
        String givenname;
        GenderType gender;
        String sn;
        String password;
        String passwordReenter;
        ArrayList<ValidationMessage> messages = new ArrayList<ValidationMessage>();
        boolean accountAutoLocked = (Boolean)(_adapter.getBufferedValue("accountAutoLocked") != null ? _adapter.getBufferedValue("accountAutoLocked") : null);
        if (accountAutoLocked) {
            messages.add((ValidationMessage)ValidationUtils.createWarningMessage("user.locked"));
        }
        List classes = (List)(_adapter.getBufferedValue("classes") != null ? _adapter.getBufferedValue("classes") : null);
        if (_adapter.getBufferedValue("role") != null) {
            if (classes == null || classes.size() == 0) {
                if (RoleFilter.isStudent((Role)_adapter.getBufferedValue("role"))) {
                    messages.add((ValidationMessage)ValidationUtils.createErrorMessage("user.classes", "validation.user.classes.empty"));
                } else if (RoleFilter.isTeacher((Role)_adapter.getBufferedValue("role"))) {
                    messages.add((ValidationMessage)ValidationUtils.createWarningMessage("user.classes", "validation.user.classes.empty"));
                }
            } else if (classes.size() > 1 && RoleFilter.isStudent((Role)_adapter.getBufferedValue("role"))) {
                messages.add((ValidationMessage)ValidationUtils.createErrorMessage("user.classes", "validation.user.classes.empty"));
            }
        }
        if ((gender = (GenderType)((Object)(_adapter.getBufferedValue("gender") != null ? _adapter.getBufferedValue("gender") : null))) == null) {
            messages.add((ValidationMessage)ValidationUtils.createWarningMessage("user.gender", "validation.user.gender.empty"));
        }
        String string = givenname = _adapter.getBufferedValue("givenname") != null ? _adapter.getBufferedValue("givenname").toString() : null;
        if (givenname == null || !PATTERN_GIVENNAME.matcher(givenname).matches()) {
            messages.add((ValidationMessage)ValidationUtils.createErrorMessage("user.givenname", "validation.user.givenname.invalid"));
        }
        if ((messagePassword = PasswordValidator.validatePassword(password = _adapter.getBufferedValue("password") != null ? _adapter.getBufferedValue("password").toString() : null, passwordReenter = _adapter.getBufferedValue("passwordConfirm") != null ? _adapter.getBufferedValue("passwordConfirm").toString() : null, this.isFromUserDialog)) != null) {
            messages.add((ValidationMessage)messagePassword);
        }
        if (_adapter.getBufferedValue("role") == null) {
            messages.add((ValidationMessage)ValidationUtils.createErrorMessage("user.role", "validation.user.role.empty"));
        }
        String string2 = sn = _adapter.getBufferedValue("sn") != null ? _adapter.getBufferedValue("sn").toString() : null;
        if (sn == null || !PATTERN_SN.matcher(sn).matches()) {
            messages.add((ValidationMessage)ValidationUtils.createErrorMessage("user.sn", "validation.user.sn.invalid"));
        }
        if (this.created) {
            String uid;
            String string3 = uid = _adapter.getBufferedValue("uid") != null ? _adapter.getBufferedValue("uid").toString() : null;
            if (uid == null || !PATTERN_UID.matcher(uid).matches()) {
                messages.add((ValidationMessage)ValidationUtils.createErrorMessage("user.uid", "validation.user.uid.invalid"));
            } else {
                for (User user : this.repository.getBeans()) {
                    if (user.equals(_adapter.getBean()) || user.getUid() == null || !user.getUid().equalsIgnoreCase(uid)) continue;
                    messages.add((ValidationMessage)ValidationUtils.createErrorMessage("user.uid", "validation.user.uid.duplicate"));
                    break;
                }
            }
        }
        return ValidationUtils.createSortedUnmodifiableValidationResult(messages);
    }
}

