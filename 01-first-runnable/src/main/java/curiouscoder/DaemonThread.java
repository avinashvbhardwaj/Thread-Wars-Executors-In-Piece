package curiouscoder;

class WorkerRunner implements Runnable {

    @Override
    public void run() {
        System.out.println("Executing the worker thread");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Executed the worker thread");
    }
}

class DaemonRunner implements Runnable {

    @Override
    public void run() {
        while (true) {
            System.out.println("Executing the daemon thread");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}

public class DaemonThread {

    static void main() {
        // Java does NOT kill user (non-daemon / worker) threads automatically
        // Even if the main thread finishes, the application keeps running
        // until all user threads complete execution
        new Thread(new WorkerRunner()).start();
        // Daemon Thread:
        // - Runs in background (low priority service thread)
        // - Supports user threads (e.g., GC, housekeeping tasks)
        // - JVM automatically terminates daemon threads when ALL user threads finish
        // - JVM does NOT wait for daemon threads to complete

        // Key Difference:
        // User Thread  -> Keeps JVM alive
        // Daemon Thread -> Does NOT keep JVM alive

        // Example:
        // t.setDaemon(true); // must be called before start()

        // Note:
        // If only daemon threads are left, JVM exits immediately

        Thread thread = new Thread(new DaemonRunner());
        thread.setDaemon(true);
        thread.start();

    }


}
