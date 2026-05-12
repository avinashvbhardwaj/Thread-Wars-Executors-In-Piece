package curiouscoder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/*
 * Task class:
 * - Implements Runnable, so it represents a task.
 * - Runnable tasks do not return any result.
 */
class Task implements Runnable {

    // Unique id for each task
    private final Integer id;

    public Task(Integer id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {

            // Random execution time between 0 to 4 seconds
            long duration = (long) (Math.random() * 5);

            // Prints task execution details
            System.out.println(
                    "Executing task: " + id
                    + ", Duration: " + duration + " seconds"
                    + ", Thread: " + Thread.currentThread().getName()
            );

            // Simulates task processing time
            TimeUnit.SECONDS.sleep(duration);

        } catch (InterruptedException ex) {

            // Handles interruption during sleep
            System.getLogger(Task.class.getName())
                    .log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}

public class SingleThreadExecutor {

    public static void main(String[] args) {

        /*
         * Creates a Single Thread Executor.
         *
         * Behavior:
         * - Only ONE thread executes tasks.
         * - Tasks execute sequentially (one after another).
         * - Tasks are executed in submission order.
         * - Useful when order is important.
         */
        ExecutorService executorService
                = java.util.concurrent.Executors.newSingleThreadExecutor();

        /*
         * Submits 10 tasks to executor.
         *
         * execute():
         * - Used for Runnable tasks
         * - Does not return result
         */
        for (int i = 0; i < 10; i++) {
            executorService.execute(new Task(i));
        }

        /*
         * Stops accepting new tasks.
         * Already submitted tasks will continue execution.
         */
        executorService.shutdown();

        // Main thread continues immediately
        System.out.println("All tasks submitted.");
    }
}
