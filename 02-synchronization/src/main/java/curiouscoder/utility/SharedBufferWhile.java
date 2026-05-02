package curiouscoder.utility;

import java.util.ArrayList;
import java.util.List;

public class SharedBufferWhile {

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
        notify();
    }

    public synchronized void consume() throws InterruptedException {
        System.out.println("Lock  acquired by Consumer Name:" + Thread.currentThread().getName());
        while (capacity > buffer.size()) {
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
