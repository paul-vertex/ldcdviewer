/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.persistence.net;

import de.sbe.ldc.persistence.net.AbstractConnection;
import de.sbe.ldc.persistence.net.CommunicationManager;
import de.sbe.ldc.persistence.protocol.Command;
import de.sbe.ldc.persistence.protocol.Request;
import de.sbe.ldc.persistence.protocol.Response;
import de.sbe.ldc.persistence.sync.TickerProcessor;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

class TickerConnection
        extends AbstractConnection {
    private static final long serialVersionUID = -2049850476391786759L;

    TickerConnection() {
        super(Integer.MAX_VALUE);
    }

    @Override
    protected void doShutdown() {
    }

    void tick() throws IOException {
        Request ticker = new Request(Command.TICKER);
        this.send(ticker);
        new Thread(new Ticker()).start();

    }

    private final class Ticker
            implements Runnable {
        private final TickerProcessor processor = new TickerProcessor();

        Ticker() {
        }

        @Override
        public void run() {
            while (TickerConnection.this.isValid()) {
                TickerConnection.this.getLock().lock();
                try {
                    String line = TickerConnection.this.getReader().readLine();
                    if (line.isEmpty()) {
                        System.out.println("???");
                        continue;
                    }
                    TickerConnection.this.logger.fine(line);
                    this.processor.processLine(line);
                } catch (Exception _e) {
                    TickerConnection.this.logger.log(Level.SEVERE, "", _e);
                    CommunicationManager.getInstance().refresh();
                } finally {
                    TickerConnection.this.getLock().unlock();
                }
            }
        }
    }

}

