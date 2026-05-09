package curiouscoder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class LiveLockExample {

    Lock lock1 = new java.util.concurrent.locks.ReentrantLock(true);
    Lock lock2 = new java.util.concurrent.locks.ReentrantLock(true);

    public void worker1() {
        while (true) {
            try {
                if (lock1.tryLock(500, TimeUnit.MILLISECONDS)) {
                    System.out.println("worker1 acquired the lock1...");
                    System.out.println("worker1 trying to acquire the lock2...");
                    if (lock2.tryLock(500, TimeUnit.MILLISECONDS)) {
                        System.out.println("worker1 acquired the lock2...");
                        lock2.unlock();
                        lock1.unlock();
                    } else {
                        System.out.println("worker1 can not acquire the lock2...");
                        lock1.unlock();
                    }
                } else {
                    System.out.println("worker1 can not acquire the lock1...");
                }
            } catch (InterruptedException ex) {
                System.getLogger(LiveLockExample.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
    }

    public void worker2() {
        while (true) {
            try {
                if (lock2.tryLock(500, TimeUnit.MILLISECONDS)) {
                    System.out.println("worker2 acquired the lock2...");
                    System.out.println("worker2 trying to acquire the lock1...");
                    if (lock1.tryLock(500, TimeUnit.MILLISECONDS)) {
                        System.out.println("worker2 acquired the lock1...");
                        lock1.unlock();
                        lock2.unlock();
                    } else {
                        System.out.println("worker2 can not acquire the lock1...");
                        lock2.unlock();
                    }
                } else {
                    System.out.println("worker2 can not acquire the lock2...");
                }
            } catch (InterruptedException ex) {
                System.getLogger(LiveLockExample.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
    }

    static void main(String[] args) {

        LiveLockExample example = new LiveLockExample();

        new Thread(example::worker1).start();
        new Thread(example::worker2).start();

    }
}
