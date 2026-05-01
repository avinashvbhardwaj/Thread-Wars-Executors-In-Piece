package curiouscoder;

import java.util.stream.IntStream;

public class RunnableAnonymous {

    static void main() {
        System.out.println("starting main thread.....");

        new Thread(()->{
            IntStream.range(1, 10).boxed().forEach((i) ->System.out.println("Runner2 : " + i));
        }).start();
        new Thread(()->{
            IntStream.range(1, 10).boxed().forEach((i) ->System.out.println("Runner1 : " + i));
        }).start();

        System.out.println("ending main thread.....");

    }

}
