/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import java.beans.PropertyChangeListener;
import java.beans.VetoableChangeListener;
import javax.management.MXBean;

@MXBean
public interface JavaBean {
    public void addPropertyChangeListener(PropertyChangeListener var1);

    public void addPropertyChangeListener(String var1, PropertyChangeListener var2);

    public void addVetoableChangeListener(String var1, VetoableChangeListener var2);

    public void addVetoableChangeListener(VetoableChangeListener var1);

    public void removePropertyChangeListener(PropertyChangeListener var1);

    public void removePropertyChangeListener(String var1, PropertyChangeListener var2);

    public void removeVetoableChangeListener(String var1, VetoableChangeListener var2);

    public void removeVetoableChangeListener(VetoableChangeListener var1);
}

