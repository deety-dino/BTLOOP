package mng;

import java.util.concurrent.*;

/**
 * Centralized thread pool manager for the game.
 * Manages all background tasks and parallel processing.
 */
public class ThreadPoolManager {
    private static ThreadPoolManager instance;
    private final ExecutorService executorService;
    private final ScheduledExecutorService scheduledExecutor;

    private ThreadPoolManager() {
        // Create thread pool with available processors
        int processors = Runtime.getRuntime().availableProcessors();
        this.executorService = Executors.newFixedThreadPool(processors);
        this.scheduledExecutor = Executors.newScheduledThreadPool(2);
    }

    public static synchronized ThreadPoolManager getInstance() {
        if (instance == null) {
            instance = new ThreadPoolManager();
        }
        return instance;
    }

    /**
     * Submit a task for execution in the thread pool
     */
    public Future<?> submit(Runnable task) {
        return executorService.submit(task);
    }

    /**
     * Submit a callable task that returns a result
     */
    public <T> Future<T> submit(Callable<T> task) {
        return executorService.submit(task);
    }

    /**
     * Schedule a task to run after a delay
     */
    public ScheduledFuture<?> schedule(Runnable task, long delay, TimeUnit unit) {
        return scheduledExecutor.schedule(task, delay, unit);
    }

    /**
     * Execute tasks in parallel and wait for all to complete
     */
    public void executeInParallel(Runnable... tasks) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(tasks.length);
        for (Runnable task : tasks) {
            executorService.submit(() -> {
                try {
                    task.run();
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
    }

    /**
     * Shutdown all thread pools gracefully
     */
    public void shutdown() {
        executorService.shutdown();
        scheduledExecutor.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
            if (!scheduledExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduledExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            scheduledExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}