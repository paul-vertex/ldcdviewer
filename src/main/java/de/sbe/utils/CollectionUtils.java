/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.utils;

import java.util.Collection;

public abstract class CollectionUtils {
    public static boolean isEmptyCollection(Collection<?> _collection) {
        return _collection == null || _collection.isEmpty();
    }
}

