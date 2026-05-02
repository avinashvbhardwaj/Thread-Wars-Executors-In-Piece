package curiouscoder;

import curiouscoder.utility.ConsumerIf;
import curiouscoder.utility.ProducerIf;
import curiouscoder.utility.SharedBufferIf;

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
