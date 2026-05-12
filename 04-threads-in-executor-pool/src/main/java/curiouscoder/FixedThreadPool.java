package curiouscoder;

import java.util.concurrent.TimeUnit;

/*
 * Work class:
 * - Implements Runnable, so it represents a task to be executed by a thread.
 * - Runnable does NOT return any result.
 */
class Work implements Runnable {

    // Unique id for each task
    private final Integer id;

    public Work(Integer id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {

            // Simulated task execution time
            long duration = 7L;

            // Random duration example
            // long duration = (long) (Math.random() * 5);
            // Prints task and thread details
            System.out.println(
                    "Executing task: " + id
                    + ", Duration: " + duration + " seconds"
                    + ", Thread: " + Thread.currentThread().getName()
            );

            // Thread sleeps to simulate actual work
            TimeUnit.SECONDS.sleep(duration);

        } catch (InterruptedException ex) {

            // Handles interruption while sleeping
            System.getLogger(Work.class.getName())
                    .log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}

public class FixedThreadPool {

    public static void main(String[] args) {

        /*
         * Creates a fixed thread pool with 3 threads.
         *
         * Behavior:
         * - Maximum 3 tasks execute concurrently.
         * - Remaining tasks wait in queue.
         * - Threads are reused for multiple tasks.
         */
        java.util.concurrent.ExecutorService executorService
                = java.util.concurrent.Executors.newFixedThreadPool(3);

        /*
         * Submits 15 tasks to thread pool.
         *
         * execute():
         * - Used for Runnable tasks
         * - Does not return any result
         */
        for (int i = 0; i < 15; i++) {
            executorService.execute(new Work(i));
        }

        /*
         * Stops accepting new tasks.
         * Already submitted tasks will still execute.
         */
        executorService.shutdown();

        // Main thread continues immediately
        System.out.println("All tasks submitted.");
    }
}
