package curiouscoder;

/*
 * StockMarketUpdater:
 * - Implements Runnable, so it represents a task.
 * - This task will run periodically using ScheduledExecutorService.
 */
class StockMarketUpdater implements Runnable {

    @Override
    public void run() {

        // Task logic executed repeatedly
        System.out.println(
                "Updating and downloading stock related data from web... "
                + "Thread: " + Thread.currentThread().getName()
        );
    }
}

public class ScheduledThreadPool {

    public static void main(String[] args) {

        /*
         * Creates a Scheduled Thread Pool with 1 thread.
         *
         * Purpose:
         * - Execute tasks after delay
         * - Execute tasks periodically
         */
        java.util.concurrent.ScheduledExecutorService scheduledExecutorService
                = java.util.concurrent.Executors.newScheduledThreadPool(1);

        /*
         * scheduleAtFixedRate(task, initialDelay, period, unit)
         *
         * Parameters:
         * 1. Task to execute
         * 2. Initial delay before first execution
         * 3. Time interval between executions
         * 4. Time unit
         *
         * Here:
         * - First execution happens after 1 second
         * - Then task repeats every 2 seconds
         */
        scheduledExecutorService.scheduleAtFixedRate(
                new StockMarketUpdater(),
                1000,
                2000,
                java.util.concurrent.TimeUnit.MILLISECONDS
        );

        // Main thread continues immediately
        System.out.println("All tasks scheduled.");

        /*
         * Note:
         * Program keeps running because scheduler thread is active.
         * To stop execution:
         * scheduledExecutorService.shutdown();
         */
    }
}
