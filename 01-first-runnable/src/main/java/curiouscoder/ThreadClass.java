package curiouscoder;

import java.util.stream.IntStream;

class Runner1 extends Thread {

    @Override
    public void run() {

        IntStream.range(1, 10).boxed().forEach((i) -> {
            System.out.println("Runner1 : " + i);
            try {
                //when the application crashes/fails abruptly at the time if thread was sleeping
                // then it throws Interrupted exception
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });
    }
}


class Runner2 extends Thread {

    @Override
    public void run() {
        IntStream.range(1, 10).boxed().forEach((i) -> {
            System.out.println("Runner2 : " + i);
            try {
                //when the application crashes/fails abruptly at the time if thread was sleeping
                // then it throws Interrupted exception
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });
    }
}

public class ThreadClass {

    static void main() {
        System.out.println("starting main thread.....");

        var thread1 =  new Runner1();
        var thread2 =  new Runner2();

        thread1.start();
        thread2.start();

        System.out.println("ending main thread.....");

    }
}
