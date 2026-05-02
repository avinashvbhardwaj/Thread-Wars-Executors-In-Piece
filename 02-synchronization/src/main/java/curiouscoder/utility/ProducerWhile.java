package curiouscoder.utility;

import java.util.Random;

public class ProducerWhile implements Runnable {

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