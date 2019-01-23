/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.utils.filter;

import de.sbe.utils.filter.Filter;
import de.sbe.utils.logging.Execution;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NotFilter<F>
implements Filter<F> {
    private final List<Filter<F>> filters;

    public /* varargs */ NotFilter(Filter<F> ... _filters) {
        this.filters = Collections.unmodifiableList(Arrays.asList(_filters));
    }

    @Execution
    @Override
    public boolean include(F _obj) {
        boolean include;
        include = true;
        for (Filter<F> filter : this.filters) {
            if (!(include &= !filter.include(_obj))) break;
        }
        return include;
    }
}

