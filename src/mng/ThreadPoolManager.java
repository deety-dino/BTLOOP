package mng;

import java.util.concurrent.*;

/**
 * Centralized thread pool manager for background game work and scheduling.
 */
public class ThreadPoolManager {
    private static volatile ThreadPoolManager instance;
    private final ExecutorService executorService;
    private final ScheduledExecutorService scheduledExecutor;

    private ThreadPoolManager() {
        // Reserve 1 core for JavaFX; use the rest for background work
        int processors = Math.max(1, Runtime.getRuntime().availableProcessors() - 1);
        this.executorService = Executors.newFixedThreadPool(processors);
        this.scheduledExecutor = Executors.newScheduledThreadPool(2);
    }

    public static ThreadPoolManager getInstance() {
        if (instance == null) {
            synchronized (ThreadPoolManager.class) {
                if (instance == null) instance = new ThreadPoolManager();
            }
        }
        return instance;
    }

    // Schedule a recurring task at fixed rate (this is the method Play.java expects)
    public void scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        scheduledExecutor.scheduleAtFixedRate(command, initialDelay, period, unit);
    }

    // Shutdown executors gracefully
    public void shutdown() {
        executorService.shutdown();
        scheduledExecutor.shutdown();
        try {
            if (!executorService.awaitTermination(3, TimeUnit.SECONDS)) executorService.shutdownNow();
            if (!scheduledExecutor.awaitTermination(3, TimeUnit.SECONDS)) scheduledExecutor.shutdownNow();
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            scheduledExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}