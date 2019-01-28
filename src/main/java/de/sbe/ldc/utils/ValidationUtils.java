/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.jgoodies.binding.PresentationModel
 *  com.jgoodies.validation.Severity
 *  com.jgoodies.validation.ValidationMessage
 *  com.jgoodies.validation.ValidationResult
 *  com.jgoodies.validation.ValidationResultModel
 *  com.jgoodies.validation.message.SimpleValidationMessage
 */
package de.sbe.ldc.utils;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.validation.Severity;
import com.jgoodies.validation.ValidationMessage;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.ValidationResultModel;
import com.jgoodies.validation.message.SimpleValidationMessage;
import de.sbe.ldc.domain.comparator.ValidationMessageComparator;
import java.awt.Container;
import java.util.Collections;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JViewport;

public abstract class ValidationUtils {

    public static SimpleValidationMessage createErrorMessage(String _key) {
        return ValidationUtils.createMessage(_key, Severity.ERROR);
    }

    public static SimpleValidationMessage createErrorMessage(String _key, String _messageKey) {
        return ValidationUtils.createMessage(_key, _messageKey, Severity.ERROR);
    }

    public static SimpleValidationMessage createMessage(String _key, Severity _severity) {
        String message = _key;
        if (!message.endsWith(".")) {
            message.concat(".");
        }
        return new SimpleValidationMessage(message, _severity, (Object)_key);
    }

    public static SimpleValidationMessage createMessage(String _key, String _messageKey, Severity _severity) {
        return new SimpleValidationMessage(_key + ", " + _messageKey + ", " + _severity.toString());
    }

    public static SimpleValidationMessage createOkMessage(String _key) {
        return ValidationUtils.createMessage(_key, Severity.OK);
    }

    public static SimpleValidationMessage createOkMessage(String _key, String _messageKey) {
        return ValidationUtils.createMessage(_key, _messageKey, Severity.OK);
    }

    public static ValidationResult createSortedUnmodifiableValidationResult(List<ValidationMessage> _messages) {
        Collections.sort(_messages, ValidationMessageComparator.SEVERITY);
        ValidationResult result = new ValidationResult();
        result.addAll(_messages);
        return ValidationResult.unmodifiableResult((ValidationResult)result);
    }

    public static SimpleValidationMessage createWarningMessage(String _key) {
        return ValidationUtils.createMessage(_key, Severity.WARNING);
    }

    public static SimpleValidationMessage createWarningMessage(String _key, String _messageKey) {
        return ValidationUtils.createMessage(_key, _messageKey, Severity.WARNING);
    }

    public static JComponent getValidComponent(JComponent _component) {
        JComponent result = _component;
        Container parent = _component.getParent();
        if (parent instanceof JViewport && (parent = parent.getParent()) instanceof JScrollPane) {
            result = (JScrollPane)parent;
        }
        return result;
    }

    public static boolean isBuffering(PresentationModel<?> _adapter, String _property) {
        Object bufferedValue = _adapter.getBufferedValue(_property);
        Object value = _adapter.getValue(_property);
        return bufferedValue == null && value != null || bufferedValue != null && value == null || bufferedValue != null && value != null && !bufferedValue.equals(value);
    }
}

