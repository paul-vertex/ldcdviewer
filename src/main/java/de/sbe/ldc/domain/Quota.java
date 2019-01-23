/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import de.sbe.ldc.domain.AbstractBean;
import de.sbe.ldc.domain.Copyable;
import java.util.Arrays;
import java.util.Comparator;

public class Quota
extends AbstractBean
implements Copyable {
    public static final String PROPERTYNAME_SIZE = "size";
    public static final String PROPERTYNAME_UNIT = "unit";
    public static final String PROPERTYNAME_UNLIMITED = "unlimited";
    private static final long serialVersionUID = 118285379482626652L;
    private long size;
    private UNIT unit;
    private boolean unlimited = true;

    @Override
    public Quota clone() {
        return this.copy();
    }

    @Override
    public Quota copy() {
        Quota copy = new Quota();
        copy.setSize(this.getSize());
        copy.setUnit(this.getUnit());
        copy.setUnlimited(this.isUnlimited());
        return copy;
    }

    public long getSize() {
        return this.size;
    }

    public UNIT getUnit() {
        return this.unit != null ? this.unit : UNIT.DEFAULT;
    }

    public boolean isUnlimited() {
        return this.unlimited;
    }

    public void setSize(long _size) {
        long old = this.size;
        this.size = _size;
        this.firePropertyChange(PROPERTYNAME_SIZE, old, this.getSize());
    }

    public void setUnit(UNIT _unit) {
        UNIT old = this.unit;
        this.unit = _unit;
        this.firePropertyChange(PROPERTYNAME_UNIT, (Object)old, (Object)this.getUnit());
    }

    public void setUnlimited(boolean _unlimited) {
        boolean old = this.unlimited;
        this.unlimited = _unlimited;
        this.firePropertyChange(PROPERTYNAME_UNLIMITED, old, this.isUnlimited());
    }

    public static enum UNIT {
        GB(1024),
        MB(1),
        TB(1048576);
        
        public static final UNIT DEFAULT;
        private final int multiplier;

        public static UNIT[] sortedValues() {
            UNIT[] units = UNIT.values();
            Arrays.sort(units, new Comparator<UNIT>(){

                @Override
                public int compare(UNIT _u1, UNIT _u2) {
                    return Integer.valueOf(_u1.getMultiplier()).compareTo(_u2.getMultiplier());
                }
            });
            return units;
        }

        private UNIT(int _multiplier) {
            this.multiplier = _multiplier;
        }

        public int getMultiplier() {
            return this.multiplier;
        }

        static {
            DEFAULT = MB;
        }

    }

}

