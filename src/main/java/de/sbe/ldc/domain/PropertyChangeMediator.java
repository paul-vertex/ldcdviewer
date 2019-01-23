/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.jgoodies.binding.beans.Model
 */
package de.sbe.ldc.domain;

import com.jgoodies.binding.beans.Model;
import de.sbe.ldc.domain.JavaBean;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PropertyChangeMediator
extends Model
implements JavaBean,
PropertyChangeListener {
    private static final long serialVersionUID = 8975092862148253747L;

    @Override
    public void propertyChange(PropertyChangeEvent _evt) {
        this.firePropertyChange(_evt);
    }
}

