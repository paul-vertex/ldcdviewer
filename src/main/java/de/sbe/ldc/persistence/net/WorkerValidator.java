/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.persistence.net;

import de.sbe.ldc.persistence.net.CommunicationManager;
import de.sbe.ldc.persistence.net.Worker;
import de.sbe.ldc.persistence.protocol.Request;
import de.sbe.ldc.persistence.protocol.Response;
import de.sbe.ldc.resources.I18N;
import de.sbe.utils.ConcurrentUtils;
import de.sbe.utils.logging.LogUtils;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkerValidator {
    public static boolean isInvalid = false;
    private final Logger logger = LogUtils.getLogger(this.getClass());

    public boolean validate(Worker _worker) {
        Response response = null;
        try {
            response = (Response)_worker.get();
            if (response.isFailed()) {
                if (Request.RequestStatus.AUTH.equals((Object)response.getRequest().getStatus())) {
                    LogUtils.getLogger(response.getClass()).severe(I18N.getLocalizedString("logging.auth.insufficient_user_privileges"));
                    isInvalid = true;
                    ConcurrentUtils.submit(() -> CommunicationManager.getInstance().refresh());
                } else {
                    LogUtils.getLogger(response.getClass()).severe(response.getRequest().getInfo());
                }
            }
        }
        catch (InterruptedException _ie) {
            this.logger.log(Level.WARNING, "", _ie);
        }
        catch (ExecutionException _ee) {
            this.logger.log(Level.WARNING, "", _ee);
        }
        return response == null ? false : !response.isFailed();
    }
}

