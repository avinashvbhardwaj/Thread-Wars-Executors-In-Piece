package curiouscoder;

/*
 * CallableTask:
 * - Implements Callable<String>, so it can return a value.
 * - Similar to Runnable, but Callable can:
 *      1. Return a result
 *      2. Throw checked exceptions
 */
class CallableTask implements java.util.concurrent.Callable<String> {

    // Task identifier
    private final Integer id;

    public CallableTask(Integer id) {
        this.id = id;
    }

    @Override
    public String call() throws Exception {

        // Simulates task processing time
        java.util.concurrent.TimeUnit.SECONDS.sleep(2);

        // Prints task execution details
        System.out.println(
                "Executing task: " + id
                + ", Duration: 2 seconds"
                + ", Thread: " + Thread.currentThread().getName()
        );

        // Result returned by Callable task
        return "Task " + id + " completed.";
    }
}

class CallableExample {

    public static void main(String[] args) {

        /*
         * Creates a thread pool with 3 worker threads.
         * At most 3 tasks run in parallel.
         */
        java.util.concurrent.ExecutorService executorService
                = java.util.concurrent.Executors.newFixedThreadPool(3);

        // Stores Future objects representing task results
        java.util.List<java.util.concurrent.Future<String>> futures
                = new java.util.ArrayList<>();

        /*
         * Submits 10 Callable tasks to executor service.
         * submit() returns Future<String> immediately.
         */
        for (int i = 0; i < 10; i++) {
            futures.add(executorService.submit(new CallableTask(i)));
        }

        /*
         * future.get():
         * - Waits until task completes
         * - Returns the result from call()
         */
        for (java.util.concurrent.Future<String> future : futures) {
            try {
                System.out.println(future.get());

            } catch (java.util.concurrent.ExecutionException
                    | InterruptedException e) {

                // Handles task execution exceptions
                System.getLogger(CallableExample.class.getName())
                        .log(System.Logger.Level.ERROR, (String) null, e);
            }
        }

        // Gracefully shuts down executor service
        executorService.shutdown();
    }
}
