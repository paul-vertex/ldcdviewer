/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.persistence.protocol;

import de.sbe.ldc.persistence.protocol.JsonProcessor;
import de.sbe.ldc.persistence.protocol.ResponseAction;
import de.sbe.ldc.persistence.protocol.ResponseStatus;

public interface ActionStatusProcessor
extends JsonProcessor {
    public void processAction(ResponseAction var1);

    public void processStatus(ResponseStatus var1);
}

