/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.persistence.net;

import de.sbe.ldc.persistence.protocol.Command;
import de.sbe.ldc.persistence.protocol.Request;
import de.sbe.ldc.persistence.protocol.Response;
import de.sbe.utils.ConcurrentUtils;
import de.sbe.utils.Settings;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DataConnection extends AbstractConnection {

    public static DataConnection instance;

    private static final long serialVersionUID = 2399403360305135516L;
    private final transient ScheduledExecutorService service = ConcurrentUtils.newSingleDaemonThreadScheduledExecutor();

    DataConnection() {
        super(Settings.getInt("net.connection_timeout"));
        instance = this;
        this.service.scheduleAtFixedRate(new PingRunnable(this), Settings.getLong("net.ping_period"), Settings.getLong("net.ping_period"), TimeUnit.MILLISECONDS);
    }

    @Override
    protected void doShutdown() {
        this.service.shutdownNow();
        try {
            this.send(new Request(Command.QUIT));
        } catch (Exception _ioe) {
            _ioe.printStackTrace();
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
                    System.out.println(response.getRequest().getInfo());
                }
            } catch (Exception _e) {
                _e.printStackTrace();
                CommunicationManager.getInstance().refresh();
            }
        }
    }

}

