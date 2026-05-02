package curiouscoder;

import java.util.ArrayList;
import java.util.List;

//🔥 Why if is dangerous?
//
//Because of:
//
//👉 Spurious wakeups
//
//A thread can wake up without notify()
//
//👉 Race condition after wakeup
//
//Example:
//
//Producer waits (buffer full)
//Consumer removes items and calls notify()
//Producer wakes up
//BUT before producer runs, another producer fills buffer again
//Now buffer is full again ❗
//
//➡️ With if, producer continues incorrectly
//➡️ With while, it rechecks condition and waits again
//checkout class SynchronizedProducerConsumerWhile
class SharedBufferIf {

    private final List<String> buffer = new ArrayList<>();
    //buffer is shared between threads → needs synchronization
    private final int capacity;
    //capacity limits how much producer can add

    public SharedBufferIf(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void produce() throws InterruptedException {
        System.out.println("Lock  acquired by Producer Name:" + Thread.currentThread().getName());
        if (capacity == buffer.size()) {
            System.out.println("Buffer is full, Producer is waiting...");
            wait();
        }
        for (int i = 0; i < capacity; i++) {
            buffer.add(Thread.currentThread().getName() + ", =" + i);
            System.out.println("Added Value:" + Thread.currentThread().getName() + ", =" + i);
            Thread.sleep(300);
        }
        notify();
    }

    public synchronized void consume() throws InterruptedException {
        System.out.println("Lock  acquired by Consumer Name:" + Thread.currentThread().getName());
        if (capacity > buffer.size()) {
            System.out.println("Buffer is not full yet, Consumer is waiting...");
            wait();
        }

        while (!buffer.isEmpty()) {
            String remove = buffer.removeFirst();
            System.out.println("Removed Value:" + remove);
            Thread.sleep(300);
        }
        notify();
    }

}

class ProducerIf implements Runnable {

    private final SharedBufferIf sharedBuffer;

    public ProducerIf(SharedBufferIf sharedBuffer) {
        this.sharedBuffer = sharedBuffer;

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1500);
                sharedBuffer.produce();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class ConsumerIf implements Runnable {

    private final SharedBufferIf sharedBuffer;

    public ConsumerIf(SharedBufferIf sharedBuffer) {
        this.sharedBuffer = sharedBuffer;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1500);
                sharedBuffer.consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}

public class SynchronizedProducerConsumerIf {

    static void main() {

        SharedBufferIf sharedBuffer = new SharedBufferIf(10);

        var t1 = new Thread(new ProducerIf(sharedBuffer), "Producer-1");
        var t2 = new Thread(new ConsumerIf(sharedBuffer), "Consumer-1");

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
