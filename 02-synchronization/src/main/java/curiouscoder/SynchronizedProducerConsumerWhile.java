package curiouscoder;

import curiouscoder.utility.*;

public class SynchronizedProducerConsumerWhile {

    static void main() {

        SharedBufferWhile sharedBuffer = new SharedBufferWhile(10);

        var t1 = new Thread(new ProducerWhile(sharedBuffer), "Producer-1");
        var t2 = new Thread(new ConsumerWhile(sharedBuffer), "Consumer-1");
        var t3 = new Thread(new ProducerWhile(sharedBuffer), "Producer-2");

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
