package curiouscoder;

class ProduceConsume {

    //Thread enters produce() and acquires the lock on this.
    //
    //Prints:Running the produce method...

    //Calls wait():
    //Releases the lock on this
    //Goes into waiting state
    //Now it will pause execution until another thread calls notify() or notifyAll() on the same object
    //
    //After being notified:
    //It does NOT immediately run
    //It first needs to re-acquire the lock
    //Once lock is acquired again → continues execution
    //
    //Prints: Again after wait in the produce method...
    public void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("Running the produce method...");
            wait();
            System.out.println("Again after wait in the produce method...");
        }
    }

    //Thread.sleep(1000):
    //Just delays execution for 1 second
    //Ensures produce() likely runs first and goes into wait()
    //Enters synchronized block → acquires same lock (this)
    //
    //Prints: Running the consume method...
    //Calls notify():
    //Wakes up one thread waiting on this
    //In this case → the produce() thread
    //
    //⚠️ Important:
    //
    //notify() does NOT release the lock immediately
    //The awakened thread (produce) must wait until consume() exits synchronized block
    //
    //Prints: Again after notify in the consume method...
    //Exits synchronized block → releases lock
    public void consume() throws InterruptedException {

        Thread.sleep(1000);

        synchronized (this) {
            System.out.println("Running the consume method...");
            notify();
            //OTHER OPERATIONS ARE EXECUTED FIRST
            System.out.println("Again after notify in the consume method...");
        }
    }


}

public class SynchronizedWaitAndNotify {

    static void main() {

        var process = new ProduceConsume();
        var t1 = new Thread(() -> {
            try {
                process.produce();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        var t2 = new Thread(() -> {
            try {
                process.consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
