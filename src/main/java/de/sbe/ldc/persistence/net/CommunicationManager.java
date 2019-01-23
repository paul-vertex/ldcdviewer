/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.jgoodies.uif.application.Application
 *  com.jgoodies.uif.application.ApplicationContext
 *  com.jgoodies.uif.application.ExitListener
 */
package de.sbe.ldc.persistence.net;

import com.jgoodies.uif.application.Application;
import com.jgoodies.uif.application.ApplicationContext;
import com.jgoodies.uif.application.ExitListener;
import de.sbe.ldc.domain.AbstractBean;
import de.sbe.ldc.domain.Server;
import de.sbe.ldc.persistence.net.AbstractConnection;
import de.sbe.ldc.persistence.net.Authenticator;
import de.sbe.ldc.persistence.net.DataConnection;
import de.sbe.ldc.persistence.net.Reconnector;
import de.sbe.ldc.persistence.net.TickerConnection;
import de.sbe.ldc.persistence.net.Worker;
import de.sbe.ldc.persistence.net.WorkerValidator;
import de.sbe.ldc.persistence.protocol.Processor;
import de.sbe.ldc.persistence.protocol.Request;
import de.sbe.ldc.persistence.protocol.Response;
import de.sbe.ldc.persistence.sync.DataLoader;
import de.sbe.utils.Settings;
import de.sbe.utils.logging.LogUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommunicationManager
extends AbstractBean
implements ExitListener {
    private static final CommunicationManager instance;
    public static final Server PREFERRED_SERVER;
    public static final String PROPERTYNAME_CONNECTED = "connected";
    private static final long serialVersionUID = -4131061359322439959L;
    private final Condition condition = this.getLock().newCondition();
    private boolean connected;
    private List<DataConnection> datas = new ArrayList<DataConnection>();
    private ExecutorService dataService;
    private final Logger logger = LogUtils.getLogger(this.getClass());
    private boolean refreshInProgress = false;
    private TickerConnection ticker;
    private final Queue<Worker> workers = new LinkedList<Worker>();

    public static CommunicationManager getInstance() {
        return instance;
    }

    private CommunicationManager() {
    }

    public boolean applicationExitAllowed(EventObject _event) {
        boolean allowed;
        allowed = false;
        this.getLock().lock();
        try {
            allowed = this.workers.isEmpty();
        }
        finally {
            this.getLock().unlock();
        }
        return allowed;
    }

    public void applicationExiting() {
    }

    Condition getCondition() {
        return this.condition;
    }

    List<DataConnection> getDatas() {
        return this.datas;
    }

    ExecutorService getDataService() {
        return this.dataService;
    }

    TickerConnection getTicker() {
        return this.ticker;
    }

    Queue<Worker> getWorkers() {
        return this.workers;
    }

    public synchronized void reconnect() {
        do {
            try {
                new Reconnector().reconnect();
            }
            catch (IOException _ioe) {
                this.logger.log(Level.SEVERE, "", _ioe);
                try {
                    Thread.sleep(Settings.getLong("net.reconnect_delay", 1000L));
                }
                catch (InterruptedException _ie) {
                    this.logger.log(Level.WARNING, "", _ie);
                }
                continue;
            }
            break;
        } while (true);
    }

    public void refresh() {
        this.getLock().lock();
        try {
            if (this.refreshInProgress) {
                return;
            }
            this.refreshInProgress = true;
        }
        finally {
            this.getLock().unlock();
        }
        this.reconnect();
        DataLoader.getInstance().loadNeededContext();
        while (WorkerValidator.isInvalid) {
            this.reconnect();
            DataLoader.getInstance().loadNeededContext();
        }
        this.getLock().lock();
        try {
            this.refreshInProgress = false;
        }
        finally {
            this.getLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void refreshSession() {
        this.getLock().lock();
        try {
            Authenticator auth = new Authenticator();
            for (DataConnection connection : this.getDatas()) {
                try {
                    //auth.authenticateSession(connection, Auth.getInstance().getSession());
                }
                catch (Exception _ioe) {
                    LogUtils.getLogger(this.getClass()).log(Level.SEVERE, "", _ioe);
                    break;
                }
            }
        }
        finally {
            this.getLock().unlock();
        }
    }

    public final Worker request(Request _request) {
        return this.request(_request, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final Worker request(Request _request, Processor _processor) {
        Worker worker = new Worker(_request, _processor);
        this.getLock().lock();
        try {
            this.workers.offer(worker);
            this.condition.signal();
        }
        finally {
            this.getLock().unlock();
        }
        return worker;
    }

    void setConnected(boolean _connected) {
        boolean old = this.connected;
        this.connected = _connected;
        this.firePropertyChange(PROPERTYNAME_CONNECTED, old, this.connected);
    }

    void setDataService(ExecutorService _dataService) {
        this.dataService = _dataService;
    }

    void setTicker(TickerConnection _ticker) {
        this.ticker = _ticker;
    }

    static {
        PREFERRED_SERVER = new Server(null, Settings.getString("net.server_host"));
        instance = new CommunicationManager();
        ApplicationContext.getInstance().getApplication().addExitListener((ExitListener)instance);
    }
}

