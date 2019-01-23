/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.persistence.protocol;

import de.sbe.ldc.persistence.net.AbstractConnection;

public interface Processor {
    public AbstractConnection getConnection();

    public String getDescription();

    public long getId();

    public ProcessorLevel getLevel();

    public void processLine(String var1);

    public void setConnection(AbstractConnection var1);

    public void setId(long var1);

    public void setUp();

    public void tearDown();

    public static enum ProcessorLevel {
        BACKGROUND_THREAD,
        EVENT_DISPATCH_THREAD;
        
    }

}

