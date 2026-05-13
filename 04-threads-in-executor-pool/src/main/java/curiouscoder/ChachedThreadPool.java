package curiouscoder;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/*
 * WorkerTask:
 * - Implements Runnable, so it represents a task.
 * - Used by CachedThreadPool for execution.
 */
class ChachedTask implements Callable<String> {

    // Unique task identifier
    private final Integer id;

    public ChachedTask(Integer id) {
        this.id = id;
    }

    @Override
    public String call() {
        return "Executing task: " + id + ", Thread: " + Thread.currentThread().getName();
    }
}

public class ChachedThreadPool {

    public static void main(String[] args) throws InterruptedException {

        /*
         * Creates a Cached Thread Pool.
         *
         * Behavior:
         * - Creates new threads as needed.
         * - Reuses previously created idle threads.
         * - No fixed thread limit.
         * - Best for many short-lived asynchronous tasks.
         *
         * Warning:
         * - Can create too many threads if tasks increase heavily.
         */
        java.util.concurrent.ExecutorService executorService
                = java.util.concurrent.Executors.newCachedThreadPool();

        /*
         * Submits 20 tasks.
         *
         * Since cached thread pool has no fixed size,
         * it may create many threads dynamically.
         */
        List<java.util.concurrent.Future<String>> futures = new java.util.ArrayList<>();
        for (int i = 0; i < 200; i++) {
            futures.add(executorService.submit(new ChachedTask(i + 1)));
        }

        System.out.println("All tasks submitted.");

        for (java.util.concurrent.Future<String> future : futures) {
            try {
                System.out.println(future.get());
            } catch (ExecutionException ex) {
                System.getLogger(ChachedThreadPool.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }

        /*
         * Stops accepting new tasks.
         * Already submitted tasks continue execution.
         */
        executorService.shutdown();
    }
}
