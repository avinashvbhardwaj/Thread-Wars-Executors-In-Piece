package curiouscoder;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {

    private static int counter = 0;

    private static final Lock lock = new ReentrantLock(true);

    public static void increment() {
        try {
            lock.lock();
            counter++;
        } finally {
            lock.unlock();
        }
    }

    static void main() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                increment();
            }
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                increment();
            }
        });
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Counter :" + counter);
    }
}
