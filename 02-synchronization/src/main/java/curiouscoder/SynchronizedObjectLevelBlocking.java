package curiouscoder;

public class SynchronizedObjectLevelBlocking {

    private int counter;

    //Lock is acquired on current object (this)
    //Only one thread per object instance can execute the synchronized block at a time
    //Other threads trying to use the same object will wait
    //
    //You’re not synchronizing the entire method—only the critical section:
    //
    //✔ Non-critical work runs without locking (better performance)
    //✔ Lock is held for shorter duration
    //✔ Reduces unnecessary contention
    //This approach ensures thread safety per object instance, while keeping performance optimized by synchronizing only the critical section.
    //
    // Better alternative in such cases:
    //
    //private final Object lock = new Object();
    //
    //synchronized (lock) {
    //    counter++;
    //}
    private void increment() {
        //execute before operations
        synchronized (this) {
            counter++;
        }
        //execute after operations
    }

    static void main() throws InterruptedException {

        var example = new SynchronizedObjectLevelBlocking();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                example.increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                example.increment();
            }
        });
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("final counter1 value :" + example.counter);
    }


}
