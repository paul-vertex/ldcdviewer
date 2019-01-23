/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import java.util.Arrays;
import java.util.Comparator;

public enum ZarafaEntity {
    NON_ACTIVE_USER(1),
    NONE(2),
    USER(0);
    
    private final Integer idx;

    public static ZarafaEntity forString(String _text) {
        return _text == null ? NONE : ZarafaEntity.valueOf(_text.toUpperCase().replaceAll("-", "_"));
    }

    public static ZarafaEntity[] sortedValues() {
        ZarafaEntity[] values = ZarafaEntity.values();
        Arrays.sort(values, new ZarafaEntityComparator());
        return values;
    }

    public static ZarafaEntity[] sortedValuesWithoutShared() {
        ZarafaEntity[] values = new ZarafaEntity[]{NONE, USER};
        Arrays.sort(values, new ZarafaEntityComparator());
        return values;
    }

    private ZarafaEntity(Integer _idx) {
        this.idx = _idx;
    }

    public Integer getIdx() {
        return this.idx;
    }

    public String toString() {
        return this.name().toLowerCase().replaceAll("_", "-");
    }

    public static final class ZarafaEntityComparator
    implements Comparator<ZarafaEntity> {
        @Override
        public int compare(ZarafaEntity _ze1, ZarafaEntity _ze2) {
            return _ze1.getIdx().compareTo(_ze2.getIdx());
        }
    }

}

