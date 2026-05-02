package curiouscoder;

public class SynchronizedClassLevelLocking {

    private static int counter;

    //You’re locking on SynchronizedClassLevelLocking.class
    //That means only one thread across the entire JVM (for this class) can enter this block at a time
    //Even if there are multiple instances of the class → all threads still compete for the same lock
    //
    //Since counter is likely static, it is shared across all instances, so:
    //Using this would be ❌ wrong (different objects → different locks)
    //Using .class ensures correct synchronization at class level
    //
    //Synchronizing on .class ensures a single lock across all instances, making it ideal for protecting static shared resources.
    //
    // private static synchronized void increment() {
    //    counter++;
    //}
    //Both use the class-level lock
    private static void increment() {
        synchronized (SynchronizedClassLevelLocking.class) {
            counter++;
        }
    }

    static void main() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                increment();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("final counter value :" + counter);
    }

}
