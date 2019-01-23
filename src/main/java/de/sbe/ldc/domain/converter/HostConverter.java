/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.domain.converter;

import de.sbe.ldc.domain.Host;
import de.sbe.utils.StringConverter;

public abstract class HostConverter
implements StringConverter<Host> {
    public static final HostConverter CN = new HostConverter(){

        @Override
        public String convert(Host _obj) {
            return _obj.getCn();
        }
    };

    HostConverter() {
    }

}

