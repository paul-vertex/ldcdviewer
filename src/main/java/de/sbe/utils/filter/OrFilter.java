/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.utils.filter;

import de.sbe.utils.filter.Filter;
import de.sbe.utils.logging.Execution;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OrFilter<F>
implements Filter<F> {
    private final List<Filter<F>> filters;

    public /* varargs */ OrFilter(Filter<F> ... _filters) {
        this.filters = Collections.unmodifiableList(Arrays.asList(_filters));
    }

    @Execution
    @Override
    public boolean include(F _obj) {
        boolean include;
        include = false;
        for (Filter<F> f : this.filters) {
            if (include |= f.include(_obj)) break;
        }
        return include;
    }
}

