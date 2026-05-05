package curiouscoder;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Worker {

    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public void produce() throws InterruptedException {
        try {
            Thread.sleep(1000);
            lock.lock();
            System.out.println("Producer method...");
            condition.await();
            Thread.sleep(3000);
            System.out.println("Again After Wait Producer method...");
        } finally {
            lock.unlock();
        }
    }

    public void consumer() throws InterruptedException {
        try {
            Thread.sleep(2000);
            lock.lock();
            System.out.println("Consumer method...");
            Thread.sleep(3000);
            condition.signal();
            System.out.println("Again After Signal Consumer method...");
        } finally {
            lock.unlock();
        }
    }

}

public class ReentrantLockProducerConsumer {

    static void main() {

        Worker worker = new Worker();

        Thread t1 = new Thread(() -> {
            try {
                worker.produce();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            try {
                worker.consumer();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);

            }
        });
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
