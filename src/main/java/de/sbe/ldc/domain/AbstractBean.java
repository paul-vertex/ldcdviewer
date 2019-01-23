/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.jgoodies.binding.beans.Model
 */
package de.sbe.ldc.domain;

import com.jgoodies.binding.beans.Model;
import de.sbe.ldc.domain.JavaBean;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractBean
extends Model
implements JavaBean {
    private static final long serialVersionUID = 7099220465160186446L;
    private transient ReentrantLock lock;

    protected AbstractBean() {
    }

    public ReentrantLock getLock() {
        if (this.lock == null) {
            this.lock = new ReentrantLock();
        }
        return this.lock;
    }
}

