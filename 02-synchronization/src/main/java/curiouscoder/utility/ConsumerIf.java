package curiouscoder.utility;

public class ConsumerIf implements Runnable {

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