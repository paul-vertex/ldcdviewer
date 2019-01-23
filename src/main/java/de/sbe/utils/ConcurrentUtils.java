/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.utils;

import de.sbe.utils.logging.Execution;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class ConcurrentUtils {
    private static ExecutorService cachedDaemonThreadPool;
    private static ThreadFactory defaultThreadFactory;

    public static synchronized ExecutorService getCachedDaemonThreadPool() {
        if (cachedDaemonThreadPool == null) {
            cachedDaemonThreadPool = Executors.newCachedThreadPool(ConcurrentUtils.getDefaultThreadFactory());
        }
        return cachedDaemonThreadPool;
    }

    public static synchronized ThreadFactory getDefaultThreadFactory() {
        if (defaultThreadFactory == null) {
            defaultThreadFactory = new DaemonThreadFactory();
        }
        return defaultThreadFactory;
    }

    public static ExecutorService newFixedDaemonThreadPool(int _nThreads) {
        return Executors.newFixedThreadPool(_nThreads, ConcurrentUtils.getDefaultThreadFactory());
    }

    public static ExecutorService newSingleDaemonThreadExecutor() {
        return Executors.newSingleThreadExecutor(ConcurrentUtils.getDefaultThreadFactory());
    }

    public static ScheduledExecutorService newSingleDaemonThreadScheduledExecutor() {
        return Executors.newSingleThreadScheduledExecutor(ConcurrentUtils.getDefaultThreadFactory());
    }

    @Execution
    public static void submit(Runnable _runnable) {
        ConcurrentUtils.getCachedDaemonThreadPool().submit(_runnable);
    }

    private static class DaemonThreadFactory
    implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final String namePrefix;
        private final AtomicInteger threadNumber;

        DaemonThreadFactory() {
            SecurityManager securityManager = System.getSecurityManager();
            this.group = securityManager == null ? Thread.currentThread().getThreadGroup() : securityManager.getThreadGroup();
            this.namePrefix = "pool-" + poolNumber.getAndIncrement() + "-daemon_thread-";
            this.threadNumber = new AtomicInteger(1);
        }

        @Override
        public Thread newThread(Runnable _runnable) {
            Thread thread = new Thread(this.group, _runnable, this.namePrefix + this.threadNumber.getAndIncrement(), 0L);
            thread.setDaemon(true);
            if (thread.getPriority() != 5) {
                thread.setPriority(5);
            }
            return thread;
        }
    }

}

