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
import de.sbe.ldc.domain.Group;
import de.sbe.ldc.domain.repository.GroupRepository;
import de.sbe.ldc.utils.ValidationUtils;
import de.sbe.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GroupValidator
implements Validator<PresentationModel<Group>> {
    public static final Pattern PATTERN_CN = Pattern.compile("[a-z0-9_][a-z0-9_-]{0,30}$");
    private final boolean created;
    private final GroupRepository repository;

    public GroupValidator(GroupRepository _repository, boolean _created) {
        this.repository = _repository;
        this.created = _created;
    }

    public ValidationResult validate(PresentationModel<Group> _adapter) {
        List member;
        String objectType;
        ArrayList<ValidationMessage> messages = new ArrayList<ValidationMessage>();
        if (this.created) {
            String cn = (String)_adapter.getBufferedValue("cn");
            if (StringUtils.isEmptyString(cn) || !PATTERN_CN.matcher(cn).matches()) {
                messages.add((ValidationMessage)ValidationUtils.createErrorMessage("group.cn", "validation.group.cn.invalid"));
            } else {
                Group group = (Group)this.repository.getById(cn);
                if (group != null && !((Group)_adapter.getBean()).equals(group)) {
                    messages.add((ValidationMessage)ValidationUtils.createErrorMessage("group.cn", "validation.group.cn.duplicate"));
                }
            }
        }
        if ((member = (List)_adapter.getBufferedValue("member")) == null || member.isEmpty()) {
            messages.add((ValidationMessage)ValidationUtils.createWarningMessage("group.member", "validation.group.member.empty"));
        }
        if (this.created && StringUtils.isEmptyString(objectType = (String)_adapter.getBufferedValue("objectType"))) {
            messages.add((ValidationMessage)ValidationUtils.createErrorMessage("group.type", "validation.group.type.invalid"));
        }
        return ValidationUtils.createSortedUnmodifiableValidationResult(messages);
    }
}

