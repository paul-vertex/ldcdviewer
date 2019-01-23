/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.persistence.net;

import de.sbe.ldc.persistence.net.AbstractConnection;
import de.sbe.ldc.persistence.protocol.Processor;
import de.sbe.ldc.persistence.protocol.Request;
import de.sbe.ldc.persistence.protocol.Response;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

public class Worker
extends SwingWorker<Response, String>
implements Processor {
    private AbstractConnection connection;
    private long id;
    private int processedLines;
    private final Processor processor;
    private final Request request;

    public Worker(Request _request) {
        this(_request, null);
    }

    public Worker(Request _request, Processor _processor) {
        this.request = _request;
        this.processor = _processor;
    }

    @Override
    protected Response doInBackground() throws Exception {
        return this.connection.send(this.request, this);
    }

    @Override
    public AbstractConnection getConnection() {
        return this.connection;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public Processor.ProcessorLevel getLevel() {
        return Processor.ProcessorLevel.BACKGROUND_THREAD;
    }

    public Processor getProcessor() {
        return this.processor;
    }

    private void incProcessedLines() {
        ++this.processedLines;
        if (-1 < this.request.getDataNumber()) {
            int newProgress = this.processedLines * 100 / this.request.getDataNumber();
            if (newProgress > 100) {
                newProgress = 100;
            }
            this.setProgress(newProgress);
        }
    }

    @Override
    protected void process(List<String> _chunks) {
        Processor lproc = this.getProcessor();
        if (lproc == null) {
            return;
        }
        for (String line : _chunks) {
            lproc.processLine(line);
            this.incProcessedLines();
        }
    }

    @Override
    public void processLine(String _line) {
        Processor lproc = this.getProcessor();
        if (lproc == null) {
            return;
        }
        if (Processor.ProcessorLevel.BACKGROUND_THREAD.equals((Object)lproc.getLevel())) {
            lproc.processLine(_line);
            this.incProcessedLines();
        } else if (Processor.ProcessorLevel.EVENT_DISPATCH_THREAD.equals((Object)lproc.getLevel())) {
            this.publish(_line);
        }
    }

    @Override
    public void setConnection(AbstractConnection _connection) {
        this.connection = _connection;
        Processor lproc = this.getProcessor();
        if (lproc == null) {
            return;
        }
        lproc.setConnection(_connection);
    }

    @Override
    public void setId(long _id) {
        this.id = _id;
        Processor lproc = this.getProcessor();
        if (lproc == null) {
            return;
        }
        lproc.setId(_id);
    }

    @Override
    public void setUp() {
        Processor lproc = this.getProcessor();
        if (lproc == null) {
            return;
        }
        if (Processor.ProcessorLevel.BACKGROUND_THREAD.equals((Object)lproc.getLevel())) {
            lproc.setUp();
        }
    }

    @Override
    public void tearDown() {
        Processor lproc = this.getProcessor();
        if (lproc == null) {
            return;
        }
        if (Processor.ProcessorLevel.BACKGROUND_THREAD.equals((Object)lproc.getLevel())) {
            lproc.tearDown();
        }
    }

}

