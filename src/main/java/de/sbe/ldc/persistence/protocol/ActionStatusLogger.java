/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.persistence.protocol;

import de.sbe.ldc.persistence.protocol.ActionStatusProcessorAdapter;
import de.sbe.ldc.persistence.protocol.ResponseAction;
import de.sbe.ldc.persistence.protocol.ResponseStatus;
import java.util.logging.Logger;

public class ActionStatusLogger
extends ActionStatusProcessorAdapter {
    @Override
    protected void doProcessAction(ResponseAction _action) {
        System.out.println(_action.getText());
    }

    @Override
    protected void doProcessStatus(ResponseStatus _status) {
        if (ResponseStatus.isError(_status) || ResponseStatus.isFatal(_status)) {
            System.out.println(_status.getText());
        } else if (ResponseStatus.isInfo(_status)) {
            System.out.println(_status.getText());
        } else if (ResponseStatus.isWarning(_status)) {
            System.out.println(_status.getText());
        }
    }
}

