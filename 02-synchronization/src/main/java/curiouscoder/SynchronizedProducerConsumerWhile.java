package curiouscoder;

import java.util.ArrayList;
import java.util.List;

//🔹 Why while is critical (core improvement)
//
//Using while ensures:
//
//Condition is rechecked after wakeup
//Prevents:
//stale state execution
//incorrect assumptions
//
//👉 This makes the code thread-safe logically

//Even though this code is “correct”, it can still suffer from starvation.
//⚠️ Problem 1: Lock unfairness
//
//synchronized has NO fairness guarantee
//
//👉 Example:
//
//Producer-1 releases lock
//Both Producer-2 and Consumer-1 are waiting
//JVM may always pick Producer-2
//
//➡️ Consumer-1 may never get chance
//➡️ 👉 Starvation of consumer

//⚠️ Problem 2: notifyAll() + competition
//
//When notifyAll() is called:
//
//ALL waiting threads wake up
//They race for the lock
//
//👉 Possible outcome:
//
//Same type of thread keeps winning
//
//Example:
//
//Producers wake up → grab lock → fill buffer again
//Consumers keep waiting
//
//➡️ Consumers starve

//🔥 Starvation Example Flow
//Producer-1 fills buffer
//Calls notifyAll()
//Both consumers wake up
//Consumer-1 gets lock, consumes everything
//Calls notifyAll()
//Producers wake up
//Producer-1 again gets lock (unfair scheduling)
//Repeats...
//
//➡️ Consumer-2 may never run
//➡️ 👉 Starvation
class SharedBufferWhile {

    private final List<String> buffer = new ArrayList<>();

    private final int capacity;

    public SharedBufferWhile(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void produce() throws InterruptedException {
        System.out.println("Lock  acquired by Producer Name:" + Thread.currentThread().getName());
        while (capacity == buffer.size()) {
            System.out.println("Buffer is full, Producer is waiting...");
            wait();
        }
        for (int i = buffer.size(); i < capacity; i++) {
            buffer.add(Thread.currentThread().getName() + ", =" + i);
            System.out.println("Added Value:" + Thread.currentThread().getName() + ", =" + i);
            Thread.sleep(300);
        }
        notifyAll();
    }

    public synchronized void consume() throws InterruptedException {
        System.out.println("Lock  acquired by Consumer Name:" + Thread.currentThread().getName());
        while (capacity > buffer.size()) {
            System.out.println("Buffer is not full yet, Consumer is waiting...");
            wait();
        }

        while (!buffer.isEmpty()) {
            String remove = buffer.removeFirst();
            System.out.println(Thread.currentThread().getName() + " Removed Value:" + remove);
            Thread.sleep(300);
        }
        notifyAll();
    }
}

class ProducerWhile implements Runnable {

    private final SharedBufferWhile sharedBuffer;

    public ProducerWhile(SharedBufferWhile sharedBuffer) {
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

class ConsumerWhile implements Runnable {

    private final SharedBufferWhile sharedBuffer;

    public ConsumerWhile(SharedBufferWhile sharedBuffer) {
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

public class SynchronizedProducerConsumerWhile {

    static void main() {

        SharedBufferWhile sharedBuffer = new SharedBufferWhile(10);

        var t1 = new Thread(new ProducerWhile(sharedBuffer), "Producer-1");
        var t2 = new Thread(new ConsumerWhile(sharedBuffer), "Consumer-1");
        var t3 = new Thread(new ProducerWhile(sharedBuffer), "Producer-2");
        var t4 = new Thread(new ConsumerWhile(sharedBuffer), "Consumer-2");

        t1.start();
        t2.start();
        t3.start();
        t4.start();



    }
}
