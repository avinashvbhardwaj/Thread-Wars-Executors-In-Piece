package curiouscoder;

import java.util.stream.IntStream;

public class ThreadJoin {
    static void main() {
        System.out.println("starting main thread.....");

        Runnable run1 = () -> {
            IntStream.range(1, 10).boxed().forEach((i) -> {
                System.out.println("Runner1 : " + i);
                try {
                    //when the application crashes/fails abruptly at the time if thread was sleeping
                    // then it throws Interrupted exception
                    Thread.sleep(560);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            });
        };
        Runnable run2 = () -> {
            {
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
        };

        var t1 = new Thread(run1);
        t1.setName("MyThread-1");
        var t2 = new Thread(run2);
        t2.setName("MyThread-2");

        t1.start();
        t2.start();

        try {
            // All threads run independently.
            // The main thread creates two child threads: thread#1 and thread#2.
            // The main thread does not wait for these threads to complete and may finish execution earlier.
            // If the main thread has tasks that depend on the result of thread#1,
            // it must wait; otherwise, execution order issues may occur.
            // To ensure proper sequencing, call join() on thread#1.
            // This makes the main thread wait until thread#1 completes before proceeding.

            t1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Iterate over all active threads in the JVM
        // Thread.getAllStackTraces() returns a map of Thread → StackTrace
        // keySet() gives all the Thread objects currently running
        // Useful for debugging, monitoring, or analyzing thread activity
        for(Thread t : Thread.getAllStackTraces().keySet()){
            System.out.println("Thread Name: " + t.getName() + ", State: " + t.getState());
        }

        System.out.println("ending main thread.....");

    }


}
