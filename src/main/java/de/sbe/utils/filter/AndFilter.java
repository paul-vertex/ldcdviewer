/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.utils.filter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AndFilter<F>
implements Filter<F> {
    private final List<Filter<F>> filters;

    @SafeVarargs
    public /* varargs */ AndFilter(Filter<F> ... _filters) {
        this.filters = Collections.unmodifiableList(Arrays.asList(_filters));
    }

    @Override
    public boolean include(F _obj) {
        boolean include;
        include = true;
        for (Filter<F> filter : this.filters) {
            if (!(include &= filter.include(_obj))) break;
        }
        return include;
    }
}

