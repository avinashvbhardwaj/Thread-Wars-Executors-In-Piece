package curiouscoder;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class WorkerThread extends Thread {

    private final int id;
    private final CountDownLatch latch;

    public WorkerThread(int id, CountDownLatch latch) {
        this.id = id;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            // Simulate work
            System.out.println("WorkerThread " + id + " started work.");
            Thread.sleep(3000);
            latch.countDown();
        } catch (InterruptedException e) {
        }
    }
}

public class CountDownLatchExample {

    private static final CountDownLatch latch = new CountDownLatch(5);

    public static void main(String[] args) {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 10; i++) {
            executor.execute(new WorkerThread(i, latch));
        }

        try {
            System.out.println("WAll task submitted. Main thread waiting for worker threads to finish.");
            latch.await();
            System.out.println("All worker threads have finished. Main thread proceeding.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            executor.shutdown();
        }

        System.out.println("All tasks completed. Main thread exiting.");

    }

}
