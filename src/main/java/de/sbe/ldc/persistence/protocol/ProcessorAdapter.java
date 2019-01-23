/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.persistence.protocol;

import de.sbe.ldc.persistence.net.AbstractConnection;

public class ProcessorAdapter
implements Processor {
    private AbstractConnection connection;
    private long id;

    @Override
    public final AbstractConnection getConnection() {
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

    @Override
    public void processLine(String _line) {
    }

    @Override
    public final void setConnection(AbstractConnection _connection) {
        this.connection = _connection;
    }

    @Override
    public void setId(long _id) {
        this.id = _id;
    }

    @Override
    public void setUp() {
    }

    @Override
    public void tearDown() {
    }
}

