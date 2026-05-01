package curiouscoder;

import java.util.stream.IntStream;

public class RunnableAnonymous {

    static void main() {

        new Thread(() -> {
            IntStream.range(1, 10).boxed().forEach((i) -> System.out.println("Runner2 : " + i));
        }).start();
        new Thread(() -> {
            IntStream.range(1, 10).boxed().forEach((i) -> System.out.println("Runner1 : " + i));
        }).start();

    }

}
