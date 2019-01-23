/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.comparator;

import de.sbe.ldc.domain.Exam;
import java.util.Comparator;

public abstract class ExamComparator
implements Comparator<Exam> {
    public static final ExamComparator ID = new ExamComparator(){

        @Override
        public int compare(Exam _e1, Exam _e2) {
            int compareResult = 0;
            if (_e1.getId() != null && _e2.getId() != null) {
                compareResult = _e1.getId().compareTo(_e2.getId());
            }
            return compareResult;
        }
    };

    ExamComparator() {
    }

}

