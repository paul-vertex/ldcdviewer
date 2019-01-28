/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.persistence.net;

import de.sbe.ldc.persistence.protocol.Command;
import de.sbe.ldc.persistence.protocol.Request;
import de.sbe.ldc.persistence.sync.TickerProcessor;
import de.sbe.utils.ConcurrentUtils;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import sh.vertex.ldcdviewer.LDCDViewer;
import sh.vertex.ldcdviewer.ui.LDCDUI;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

public class TickerConnection extends AbstractConnection {

    private final ExecutorService service = ConcurrentUtils.newSingleDaemonThreadExecutor();

    public static TickerConnection instance;

    public TickerConnection() {
        super(Integer.MAX_VALUE);
    }

    @Override
    protected void doShutdown() {
    }

    public void tick() throws IOException {
        instance = this;

        Request ticker = new Request(Command.STATE_GET);
        this.send(ticker);
        this.service.execute(new Ticker());
    }

    private final class Ticker implements Runnable {
        private final TickerProcessor processor = new TickerProcessor();

        Ticker() {
        }

        @Override
        public void run() {
            while (isValid()) {
                getLock().lock();
                try {
                    String line = TickerConnection.this.getReader().readLine();
                    if (line == null)
                        break;
                    if (line.isEmpty()) {
                        System.out.println("???");
                        continue;
                    }

                    this.processor.processLine(line);
                } catch (Exception anyException) {
                    anyException.printStackTrace();
                    CommunicationManager.getInstance().refresh();
                } finally {
                    getLock().unlock();
                }
            }
        }
    }

}

