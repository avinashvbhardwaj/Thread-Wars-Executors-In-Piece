package curiouscoder;

public class SyncronizedMethod {

    // incrementing an integer is not an atomic process
    private static int counter;

    //Each thread may cache variables locally (CPU cache), so:
    //One thread updates value
    //Another thread may still see old value

    //synchronized ensures:
    //Mutual exclusion → only one thread at a time
    //Memory visibility → changes made by one thread are visible to others
    //Every object in Java has an intrinsic lock (monitor).
    //When a thread enters a synchronized block:
    //It acquires the lock
    //Executes the code
    //Releases the lock
    //Other threads must wait until the lock is released.

    private synchronized static void increment() {
        counter++;
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
