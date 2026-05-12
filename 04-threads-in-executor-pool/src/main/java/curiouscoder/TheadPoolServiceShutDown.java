package curiouscoder;

import java.util.concurrent.TimeUnit;

/*
 * Worker class:
 * - Implements Runnable, so it represents a task.
 * - Each task simulates some work using sleep().
 */
class Worker implements Runnable {

    // Unique task identifier
    private final Integer id;

    public Worker(Integer id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {

            // Simulated task execution time
            long duration = 2L;

            // Random duration example
            // long duration = (long) (Math.random() * 5);
            // Prints task execution details
            System.out.println(
                    "Executing task: " + id
                    + ", Duration: " + duration + " seconds"
                    + ", Thread: " + Thread.currentThread().getName()
            );

            // Simulates actual processing work
            TimeUnit.SECONDS.sleep(duration);

        } catch (InterruptedException ex) {

            /*
             * Restores interrupted status.
             * Best practice after catching InterruptedException.
             */
            Thread.currentThread().interrupt();
        }
    }
}

public class TheadPoolServiceShutDown {

    public static void main(String[] args) {

        /*
         * Creates a fixed thread pool with 10 threads.
         *
         * Behavior:
         * - Maximum 10 tasks execute concurrently.
         * - Remaining tasks wait in queue.
         */
        java.util.concurrent.ExecutorService executorService
                = java.util.concurrent.Executors.newFixedThreadPool(10);

        /*
         * Submits 100 tasks to executor service.
         */
        for (int i = 0; i < 100; i++) {
            executorService.execute(new Worker(i));
        }

        System.out.println("All tasks submitted.");

        /*
         * shutdown():
         * - Stops accepting new tasks.
         * - Already submitted tasks continue execution.
         */
        executorService.shutdown();

        try {

            /*
             * awaitTermination():
             * - Waits for all tasks to complete.
             * - Wait time here = 15 seconds.
             *
             * Returns:
             * - true  -> all tasks completed
             * - false -> timeout occurred
             */
            if (!executorService.awaitTermination(
                    15000,
                    TimeUnit.MILLISECONDS
            )) {

                System.out.println(
                        "Executor service did not terminate in the specified time."
                );

                /*
                 * shutdownNow():
                 * - Attempts to stop running tasks immediately.
                 * - Interrupts active threads.
                 * - Removes waiting tasks from queue.
                 */
                executorService.shutdownNow();
            }

        } catch (InterruptedException e) {

            /*
             * If current thread gets interrupted while waiting,
             * force shutdown immediately.
             */
            executorService.shutdownNow();

            // Restore interrupted status
            Thread.currentThread().interrupt();
        }
    }
}
