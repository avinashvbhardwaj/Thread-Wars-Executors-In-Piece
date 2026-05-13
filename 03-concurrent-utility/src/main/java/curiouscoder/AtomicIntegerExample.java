package curiouscoder;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * How It Works Internally
 *
 * AtomicInteger uses: CAS (Compare And Swap) CPU-level atomic instructions
 * lock-free algorithms instead of traditional locking.
 *
 * Synchronization works but:
 *
 * 1. involves locking 2. thread blocking 3. possible heavier
 *
 * AtomicInteger:
 *
 * 1. lock-free 2. faster for simple counters 3. highly scalable
 *
 * Why AtomicInteger is Better Than synchronized:
 *
 * Here AtomicInteger provides better performance and scalability than
 * synchronized blocks because it avoids the overhead of acquiring and releasing
 * locks, allowing multiple threads to update the counter concurrently without
 * blocking each other.
 *
 * In summary, AtomicInteger is a more efficient and scalable choice for simple
 * counters in concurrent programming compared to synchronized blocks.
 *
 */
public class AtomicIntegerExample {

    public static void main(String[] args) {

        AtomicInteger atomicInteger = new AtomicInteger(0);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("Thread 1: " + atomicInteger.incrementAndGet());
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("Thread 2: " + atomicInteger.incrementAndGet());
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

        System.out.println("Counter :" + atomicInteger.get());

    }
}
