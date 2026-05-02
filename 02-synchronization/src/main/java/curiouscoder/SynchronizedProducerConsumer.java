package curiouscoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class SharedBuffer {

    private final List<Integer> buffer = new ArrayList<>();

    private int capacity;

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void produce() throws InterruptedException {
        System.out.println("Lock  acquired by Producer capacity:" + capacity);
        if (capacity == buffer.size()) {
            System.out.println("Buffer is full, Producer is waiting...");
            wait();
        }
        for (int i = 0; i < capacity; i++) {
            buffer.add(i);
            System.out.println("Added Value:" + i);
            Thread.sleep(300);
        }
        notify();
    }

    public synchronized void consume() throws InterruptedException {
        System.out.println("Lock  acquired by Consumer capacity:" + capacity);
        if (capacity > buffer.size()) {
            System.out.println("Buffer is not full yet, Consumer is waiting...");
            wait();
        }

        while (!buffer.isEmpty()) {
            Integer remove = buffer.removeFirst();
            System.out.println("Removed Value:" + remove);
            Thread.sleep(300);
        }
        notify();
    }
}

class Producer implements Runnable {

    private final SharedBuffer sharedBuffer;

    public Producer(SharedBuffer sharedBuffer) {
        this.sharedBuffer = sharedBuffer;

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1500);
                sharedBuffer.setCapacity(new Random().nextInt(0, 15));
                sharedBuffer.produce();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Consumer implements Runnable {

    private final SharedBuffer sharedBuffer;

    public Consumer(SharedBuffer sharedBuffer) {
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

public class SynchronizedProducerConsumer {

    static void main() {

        SharedBuffer sharedBuffer = new SharedBuffer();


        var t1 = new Thread(new Producer(sharedBuffer));
        var t2 = new Thread(new Consumer(sharedBuffer));


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
