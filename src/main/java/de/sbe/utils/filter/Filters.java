/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.utils.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public abstract class Filters {
    public static <F> void filter(Collection<F> _collection, Filter<F> _filter) {
        Iterator<F> it = _collection.iterator();
        while (it.hasNext()) {
            if (_filter.include(it.next())) continue;
            it.remove();
        }
    }

    public static <F> List<F> filterAsList(Collection<F> _collection, Filter<F> _filter) {
        ArrayList<F> newList = new ArrayList<F>(_collection);
        Filters.filter(newList, _filter);
        return newList;
    }

    public static <F> Set<F> filterAsSet(Collection<F> _collection, Filter<F> _filter) {
        HashSet<F> newSet = new HashSet<F>(_collection);
        Filters.filter(newSet, _filter);
        return newSet;
    }

    private Filters() {
    }
}

