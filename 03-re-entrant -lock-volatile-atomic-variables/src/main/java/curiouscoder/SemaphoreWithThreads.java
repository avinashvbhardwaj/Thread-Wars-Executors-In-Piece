package curiouscoder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

enum DownloadStatus {
    INSTANCE;

    private final Semaphore semaphore = new Semaphore(3, true);

    public void downloadFile() {
        try {
            semaphore.acquire();
            System.out.println("Downloading...: ");
            Thread.sleep(2000);
            System.out.println("Finished downloading...: ");
        } catch (InterruptedException e) {
            System.getLogger(DownloadStatus.class.getName()).log(System.Logger.Level.ERROR, (String) null, e);
        } finally {
            semaphore.release();
        }
    }

}

public class SemaphoreWithThreads {

    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            threads.add(new Thread(DownloadStatus.INSTANCE::downloadFile));
        }

        threads.forEach(Thread::start);
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
