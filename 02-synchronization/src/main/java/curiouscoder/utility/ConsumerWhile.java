package curiouscoder.utility;

public class ConsumerWhile implements Runnable {

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