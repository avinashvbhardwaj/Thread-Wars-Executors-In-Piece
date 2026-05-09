package curiouscoder;

import java.util.concurrent.locks.Lock;

public class DeadLockExample {

    Lock lock1 = new java.util.concurrent.locks.ReentrantLock(true);
    Lock lock2 = new java.util.concurrent.locks.ReentrantLock(true);

    public void worker1() {
        lock1.lock();
        System.out.println("worker1 acquired the lock1...");
        try {
            Thread.sleep(300);
            lock2.lock();
            System.out.println("worker1 acquired the lock2...");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock1.unlock();
            lock2.unlock();
        }
    }

    public void worker2() {
        lock2.lock();
        System.out.println("worker2 acquired the lock2...");
        try {
            Thread.sleep(300);
            lock1.lock();
            System.out.println("worker2 acquired the lock1...");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock2.unlock();
            lock1.unlock();
        }

    }

    static void main() {

        DeadLockExample example = new DeadLockExample();

        new Thread(example::worker1).start();
        new Thread(example::worker2).start();

    }
}
