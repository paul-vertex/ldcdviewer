/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.persistence.net;

import de.sbe.ldc.domain.Server;
import de.sbe.ldc.persistence.net.AbstractConnection;
import de.sbe.ldc.persistence.net.CommunicationManager;
import de.sbe.ldc.persistence.protocol.Command;
import de.sbe.ldc.persistence.protocol.Request;
import de.sbe.ldc.persistence.protocol.Response;
import de.sbe.utils.ConcurrentUtils;
import de.sbe.utils.Settings;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

class DataConnection
extends AbstractConnection {
    private static final long serialVersionUID = 2399403360305135516L;
    private final transient ScheduledExecutorService service = ConcurrentUtils.newSingleDaemonThreadScheduledExecutor();

    DataConnection() {
        super(Settings.getInt("net.connection_timeout"));
        this.service.scheduleAtFixedRate(new PingRunnable(this), Settings.getLong("net.ping_period"), Settings.getLong("net.ping_period"), TimeUnit.MILLISECONDS);
    }

    @Override
    protected void doShutdown() {
        this.service.shutdownNow();
        try {
            this.send(new Request(Command.QUIT));
        }
        catch (Exception _ioe) {
            this.logger.log(Level.WARNING, "", _ioe);
        }
    }

    private class PingRunnable
    implements Runnable {
        private final AbstractConnection connection;

        PingRunnable(AbstractConnection _owner) {
            this.connection = _owner;
        }

        @Override
        public void run() {
            if (!DataConnection.this.isValid()) {
                return;
            }
            try {
                Request ping = new Request(Command.PING);
                ping.putEncodedData("host", DataConnection.this.getServer().getIp());
                Response response = this.connection.send(ping);
                if (response.isFailed()) {
                    DataConnection.this.logger.warning(response.getRequest().getInfo());
                }
            }
            catch (Exception _e) {
                DataConnection.this.logger.log(Level.SEVERE, "", _e);
                CommunicationManager.getInstance().refresh();
            }
        }
    }

}

