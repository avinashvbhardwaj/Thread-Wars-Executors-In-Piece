package curiouscoder;

import java.util.stream.IntStream;

class Runnable1 implements Runnable {

    @Override
    public void run() {
        IntStream.range(1, 10).boxed().forEach((i) ->System.out.println("Runner1 : " + i));
    }
}


class Runnable2 implements Runnable {

    @Override
    public void run() {
        IntStream.range(1, 10).boxed().forEach((i) ->System.out.println("Runner2 : " + i));
    }
}

public class RunnableInterface {

    static void main() {
        System.out.println("starting main thread.....");

        new Thread(new Runnable1()).start();
        new Thread(new Runnable2()).start();

        System.out.println("ending main thread.....");

    }

}
