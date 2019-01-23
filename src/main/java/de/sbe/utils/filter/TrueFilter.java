/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.utils.filter;

import de.sbe.utils.filter.Filter;

public class TrueFilter<F>
implements Filter<F> {
    @Override
    public boolean include(F _obj) {
        return true;
    }
}

