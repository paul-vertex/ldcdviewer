/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.persistence.net;

import de.sbe.ldc.persistence.net.AbstractConnection;
import de.sbe.ldc.persistence.net.CommunicationManager;
import de.sbe.ldc.persistence.net.DataConnection;
import de.sbe.ldc.persistence.net.Worker;
import de.sbe.utils.ConcurrentUtils;
import de.sbe.utils.logging.LogUtils;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;

class WorkerExecutor
implements Runnable {
    private final DataConnection connection;

    WorkerExecutor(DataConnection _connection) {
        this.connection = _connection;
    }

    @Override
    public void run() {
        try {
            while (this.connection.isValid()) {
                Worker worker;
                CommunicationManager.getInstance().getLock().lock();
                try {
                    while (CommunicationManager.getInstance().getWorkers().isEmpty()) {
                        CommunicationManager.getInstance().getCondition().await();
                    }
                    worker = CommunicationManager.getInstance().getWorkers().poll();
                }
                finally {
                    CommunicationManager.getInstance().getLock().unlock();
                }
                worker.setConnection(this.connection);
                ConcurrentUtils.newSingleDaemonThreadExecutor().execute(worker);
                worker.get();
            }
        }
        catch (InterruptedException _ie) {
            LogUtils.getLogger(WorkerExecutor.class).log(Level.WARNING, "", _ie);
        }
        catch (ExecutionException _ee) {
            LogUtils.getLogger(WorkerExecutor.class).log(Level.WARNING, "", _ee);
            CommunicationManager.getInstance().refresh();
        }
        finally {
            this.connection.shutdown();
        }
    }
}

