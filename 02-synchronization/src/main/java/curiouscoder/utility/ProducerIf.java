package curiouscoder.utility;

import java.util.Random;

public class ProducerIf implements Runnable {

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