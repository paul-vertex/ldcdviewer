/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.comparator;

import de.sbe.ldc.domain.Host;
import java.util.Comparator;

public abstract class HostComparator
implements Comparator<Host> {
    public static final HostComparator CN = new HostComparator(){

        @Override
        public int compare(Host _h1, Host _h2) {
            String c2;
            int compareResult = 0;
            String c1 = _h1 == null ? null : _h1.getCn();
            String string = c2 = _h2 == null ? null : _h2.getCn();
            compareResult = c1 == null ? (c2 == null ? 0 : -1) : (c2 == null ? 1 : c1.compareTo(c2));
            return compareResult;
        }
    };

    HostComparator() {
    }

}

