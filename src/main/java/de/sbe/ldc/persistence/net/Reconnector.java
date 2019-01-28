/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.persistence.net;

import de.sbe.ldc.domain.Server;
import de.sbe.ldc.persistence.net.AbstractConnection;
import de.sbe.ldc.persistence.net.Authenticator;
import de.sbe.ldc.persistence.net.CommunicationManager;
import de.sbe.ldc.persistence.net.DataConnection;
import de.sbe.ldc.persistence.net.TickerConnection;
import de.sbe.ldc.persistence.net.WorkerExecutor;
import de.sbe.utils.ConcurrentUtils;
import de.sbe.utils.Settings;
import de.sbe.utils.logging.LogUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

public class Reconnector {
    private final Logger logger = LogUtils.getLogger(this.getClass());

    public Reconnector() {
    }

    private void auth(List<AbstractConnection> _connections) throws IOException {
        new Authenticator().authenticate(_connections);
    }

    private List<AbstractConnection> connect() throws IOException {
        ArrayList<AbstractConnection> connections = new ArrayList<AbstractConnection>();
        int i = Settings.getInt("net.connection_number", 1);
        while (--i >= 0) {
            DataConnection data = new DataConnection();
            try {
                data.reconnect(CommunicationManager.PREFERRED_SERVER);
            }
            catch (IOException _ioe) {
                data.shutdown();
                throw _ioe;
            }
            connections.add(data);
        }
        TickerConnection ticker = new TickerConnection();
        try {
            ticker.reconnect(CommunicationManager.PREFERRED_SERVER);
        }
        catch (IOException _ioe) {
            ticker.shutdown();
            throw _ioe;
        }
        connections.add(ticker);
        return connections;
    }

    private void control() throws IOException {
        this.shutdown(null);
        List<AbstractConnection> connections = null;
        try {
            connections = this.connect();
            this.auth(connections);
            this.execute(connections);
        }
        catch (IOException _ioe) {
            this.shutdown(connections);
            throw _ioe;
        }
    }

    private void execute(List<AbstractConnection> _connections) throws IOException {
        ExecutorService datas = ConcurrentUtils.newFixedDaemonThreadPool(_connections.size() - 1);
        for (AbstractConnection connection : _connections) {
            if (connection instanceof DataConnection) {
                datas.execute(new WorkerExecutor((DataConnection)connection));
                CommunicationManager.getInstance().getDatas().add((DataConnection)connection);
                continue;
            }
            if (!(connection instanceof TickerConnection)) continue;
            ((TickerConnection)connection).tick();
            CommunicationManager.getInstance().setTicker((TickerConnection)connection);
        }
        CommunicationManager.getInstance().setDataService(datas);
    }

    public final void reconnect() throws IOException {
        this.logger.info("logging.persistence.net.reconnect =" +  CommunicationManager.PREFERRED_SERVER);
        CommunicationManager.getInstance().setConnected(false);
        this.control();
        CommunicationManager.getInstance().setConnected(true);
    }

    public void shutdown(List<AbstractConnection> _connections) {
        TickerConnection ticker;
        ExecutorService datas;
        if (_connections != null) {
            for (AbstractConnection connection : _connections) {
                connection.shutdown();
            }
        }
        if ((datas = CommunicationManager.getInstance().getDataService()) != null) {
            datas.shutdownNow();
            CommunicationManager.getInstance().setDataService(null);
            CommunicationManager.getInstance().getDatas().clear();
        }
        if ((ticker = CommunicationManager.getInstance().getTicker()) != null) {
            ticker.shutdown();
            CommunicationManager.getInstance().setTicker(null);
        }
    }
}

