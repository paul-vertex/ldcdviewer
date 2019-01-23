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
import de.sbe.ldc.domain.Password;
import de.sbe.ldc.utils.ValidationUtils;
import de.sbe.utils.StringUtils;

public class PasswordValidator
implements Validator<PresentationModel<Password>> {
    public static SimpleValidationMessage validatePassword(String _password, String _passwordConfirm, boolean _isFromUserDialog) {
        SimpleValidationMessage message = null;
        String password = _password;
        String passwordConfirm = _passwordConfirm;
        if (StringUtils.isEmptyString(password)) {
            password = null;
            if (!_isFromUserDialog) {
                message = ValidationUtils.createErrorMessage("user.password", "validation.user.passwords.are.empty");
            }
        }
        if (StringUtils.isEmptyString(passwordConfirm)) {
            passwordConfirm = null;
        }
        if (password != null && passwordConfirm == null || password == null && passwordConfirm != null || password != null && passwordConfirm != null && !password.equals(passwordConfirm)) {
            message = ValidationUtils.createErrorMessage("user.password", "validation.user.passwords.not.equals");
        }
        return message;
    }

    public ValidationResult validate(PresentationModel<Password> _adapter) {
        String passwordConfirm;
        ValidationResult result = new ValidationResult();
        String password = _adapter.getBufferedValue("password") != null ? _adapter.getBufferedValue("password").toString() : null;
        SimpleValidationMessage message = PasswordValidator.validatePassword(password, passwordConfirm = _adapter.getBufferedValue("passwordConfirm") != null ? _adapter.getBufferedValue("passwordConfirm").toString() : null, false);
        if (message != null) {
            result.add((ValidationMessage)message);
        }
        return result;
    }
}

