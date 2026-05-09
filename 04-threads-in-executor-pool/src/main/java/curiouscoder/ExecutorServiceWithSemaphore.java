package curiouscoder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;

enum DownloadStatus {
    INSTANCE;

    private final Semaphore semaphore = new Semaphore(3, true);

    public void downloadFile(String threadName) {
        try {
            semaphore.acquire();
            System.out.println("Downloading...: " + threadName);
            Thread.sleep(Math.round(Math.random() * 2000) + 3000);
            System.out.println("Finished downloading...: " + threadName);
        } catch (InterruptedException e) {
            System.getLogger(DownloadStatus.class.getName()).log(System.Logger.Level.ERROR, (String) null, e);
        } finally {
            semaphore.release();
        }
    }

}

public class ExecutorServiceWithSemaphore {

    public static void main(String[] args) {
        // create a thread pool of 5 threads and call downloadFile() method 10 times
        ExecutorService executorService = java.util.concurrent.Executors.newCachedThreadPool();
        for (int i = 1; i < 13; i++) {
            final int index = i;
            executorService.execute(() -> DownloadStatus.INSTANCE.downloadFile(Thread.currentThread().getName() + "-" + index));
        }
        executorService.shutdown();
    }
}
