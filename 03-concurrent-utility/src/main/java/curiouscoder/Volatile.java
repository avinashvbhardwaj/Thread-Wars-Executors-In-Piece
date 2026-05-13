package curiouscoder;

// What Happens Internally
// Two threads exist:
//      Main Thread
//      Worker Thread (t1)
// Worker thread continuously runs:
// Without volatile, JVM optimization or CPU caching may happen.
// The worker thread may cache:terminated=false and never see the update from the main thread, resulting in an infinite loop.
// volatile Solves Visibility Problem by ensuring that changes to the variable are immediately visible to all threads, 
// preventing caching issues and allowing the worker thread to see the updated value of terminated when it is set to true by the main thread.
// forces:
// reads to come from main memory
// writes to be flushed to main memory
class VolatileWorker implements Runnable {

    // private boolean terminated = false;
    private volatile boolean terminated = false;

    public void setTerminated(boolean terminated) {
        this.terminated = terminated;
    }

    public boolean isTerminated() {
        return terminated;
    }

    @Override
    public void run() {
        while (!isTerminated()) {
            System.out.println("Waiting for termination signal...");
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                System.getLogger(VolatileWorker.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
        System.out.println("Termination signal received, exiting...");
    }
}

public class Volatile {

    public static void main(String[] args) {
        VolatileWorker worker = new VolatileWorker();

        Thread t1 = new Thread(worker);
        t1.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.getLogger(Volatile.class.getName()).log(System.Logger.Level.ERROR, (String) null, e);
        }

        worker.setTerminated(true);
    }

}
