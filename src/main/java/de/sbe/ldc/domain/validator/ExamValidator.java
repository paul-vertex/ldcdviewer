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
import de.sbe.ldc.domain.Exam;
import de.sbe.ldc.utils.ValidationUtils;
import de.sbe.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class ExamValidator
implements Validator<PresentationModel<Exam>> {
    public ValidationResult validate(PresentationModel<Exam> _model) {
        String name;
        ArrayList<ValidationMessage> messages = new ArrayList<ValidationMessage>();
        Exam.Before before = (Exam.Before)((Object)_model.getBufferedValue("before"));
        if (Exam.Before.LOGOFF.equals((Object)before)) {
            messages.add((ValidationMessage)ValidationUtils.createWarningMessage("validation.exam.before.logoff"));
        } else if (Exam.Before.REBOOT.equals((Object)before)) {
            messages.add((ValidationMessage)ValidationUtils.createWarningMessage("validation.exam.before.reboot"));
        }
        List hosts = (List)_model.getBufferedValue("hosts");
        List rooms = (List)_model.getBufferedValue("rooms");
        if ((hosts == null || hosts.isEmpty()) && (rooms == null || rooms.isEmpty())) {
            messages.add((ValidationMessage)ValidationUtils.createErrorMessage("hosts", "validation.exam.hosts.empty"));
        }
        if (StringUtils.isEmptyString(name = (String)_model.getBufferedValue("name"))) {
            messages.add((ValidationMessage)ValidationUtils.createErrorMessage("exam.id", "validation.exam.id.empty"));
        }
        return ValidationUtils.createSortedUnmodifiableValidationResult(messages);
    }
}

