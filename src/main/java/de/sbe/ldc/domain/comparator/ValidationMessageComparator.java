/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.jgoodies.validation.Severity
 *  com.jgoodies.validation.ValidationMessage
 */
package de.sbe.ldc.domain.comparator;

import com.jgoodies.validation.Severity;
import com.jgoodies.validation.ValidationMessage;
import java.util.Comparator;

public abstract class ValidationMessageComparator
implements Comparator<ValidationMessage> {
    public static final ValidationMessageComparator SEVERITY = new ValidationMessageComparator(){

        @Override
        public int compare(ValidationMessage _vm1, ValidationMessage _vm2) {
            return _vm1.severity().compareTo((Severity) _vm2.severity());
        }
    };

}

