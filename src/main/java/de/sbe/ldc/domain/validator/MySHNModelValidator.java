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
import de.sbe.ldc.domain.AbstractMySHNModel;
import de.sbe.ldc.utils.ValidationUtils;
import java.util.ArrayList;

public class MySHNModelValidator<T extends AbstractMySHNModel>
implements Validator<PresentationModel<T>> {
    public ValidationResult validate(PresentationModel<T> _adapter) {
        ArrayList<ValidationMessage> messages = new ArrayList<ValidationMessage>();
        boolean executeImmediately = (Boolean)(_adapter.getBufferedValue("executeImmediately") != null ? _adapter.getBufferedValue("executeImmediately") : null);
        if (executeImmediately) {
            messages.add((ValidationMessage)ValidationUtils.createWarningMessage("validation.hostFormat.execute_immediately"));
        }
        return ValidationUtils.createSortedUnmodifiableValidationResult(messages);
    }
}

