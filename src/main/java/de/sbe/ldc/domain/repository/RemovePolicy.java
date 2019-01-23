/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.repository;

import de.sbe.ldc.domain.JavaBean;

interface RemovePolicy<B extends JavaBean, I> {
    public void remove(B var1);
}

