/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain;

import java.util.Arrays;
import java.util.Comparator;

public enum KopanoEntity {
    NON_ACTIVE_USER(1),
    NONE(2),
    USER(0);
    
    private final Integer idx;

    public static KopanoEntity forString(String _text) {
        return _text == null ? NONE : KopanoEntity.valueOf(_text.toUpperCase().replaceAll("-", "_"));
    }

    public static KopanoEntity[] sortedValues() {
        KopanoEntity[] values = KopanoEntity.values();
        Arrays.sort(values, new KopanoEntityComparator());
        return values;
    }

    public static KopanoEntity[] sortedValuesWithoutShared() {
        KopanoEntity[] values = new KopanoEntity[]{NONE, USER};
        Arrays.sort(values, new KopanoEntityComparator());
        return values;
    }

    private KopanoEntity(Integer _idx) {
        this.idx = _idx;
    }

    public Integer getIdx() {
        return this.idx;
    }

    public String toString() {
        return this.name().toLowerCase().replaceAll("_", "-");
    }

    public static final class KopanoEntityComparator
    implements Comparator<KopanoEntity> {
        @Override
        public int compare(KopanoEntity _ze1, KopanoEntity _ze2) {
            return _ze1.getIdx().compareTo(_ze2.getIdx());
        }
    }

}

