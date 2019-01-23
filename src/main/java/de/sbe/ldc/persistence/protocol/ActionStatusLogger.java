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
        this.logger.info(_action.getText());
    }

    @Override
    protected void doProcessStatus(ResponseStatus _status) {
        if (ResponseStatus.isError(_status) || ResponseStatus.isFatal(_status)) {
            this.logger.severe(_status.getText());
        } else if (ResponseStatus.isInfo(_status)) {
            this.logger.info(_status.getText());
        } else if (ResponseStatus.isWarning(_status)) {
            this.logger.warning(_status.getText());
        }
    }
}

