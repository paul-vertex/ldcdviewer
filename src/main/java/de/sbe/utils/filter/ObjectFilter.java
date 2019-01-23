/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.utils.filter;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ObjectFilter<F>
implements Filter<F> {
    private final Set<F> objects;

    public ObjectFilter(Collection<F> _fs) {
        this.objects = new HashSet<F>(_fs);
    }

    public /* varargs */ ObjectFilter(F ... _fs) {
        this((Collection<F>)Arrays.asList(_fs));
    }

    public void add(Collection<F> _fs) {
        this.objects.addAll(_fs);
    }

    public /* varargs */ void add(F ... _fs) {
        this.add((Collection<F>)Arrays.asList(_fs));
    }

    @Override
    public boolean include(F _obj) {
        return this.objects.contains(_obj);
    }

    public void remove(Collection<F> _fs) {
        this.objects.removeAll(_fs);
    }

    public /* varargs */ void remove(F ... _fs) {
        this.remove((Collection<F>)Arrays.asList(_fs));
    }
}

