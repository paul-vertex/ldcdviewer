/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.comparator;

import de.sbe.ldc.domain.Contact;
import java.util.Comparator;

public abstract class ContactComparator<T>
implements Comparator<T> {
    public static final ContactComparator<Contact> DISPLAY_NAME = new ContactComparator<Contact>(){

        @Override
        public int compare(Contact _c1, Contact _c2) {
            String d2;
            String d1 = _c1 == null ? null : _c1.getDisplayName();
            String string = d2 = _c2 == null ? null : _c2.getDisplayName();
            if (d1 == null) {
                if (d2 == null) {
                    return 0;
                }
                return -1;
            }
            if (d2 == null) {
                return 1;
            }
            return d1.compareTo(d2);
        }
    };

    private ContactComparator() {
    }

}

