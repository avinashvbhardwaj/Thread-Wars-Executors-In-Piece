package curiouscoder;

public class SynchronizedBlock {

    private int counter1;
    private int counter2;

    //Independent locks for each synchronized block
    //Decoupling is important after understanding the problem
    //Yet no thread is updating the same variable
    //but Main object belongs to SynchronizedBlock
    //Introduced two new Object for Thread lock purpose
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    //It is not recommended to use synchronized keyword on method
    //because method has other part  of the code that does not need synchronization
    private void increment1() {
        //execute before operations
        synchronized (lock1) {
            counter1++;
        }
        //execute after operations
    }

    private void increment2() {
        //execute before operations
        synchronized (lock2) {
            counter2++;
        }
        //execute after operations
    }

    private void execute() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                increment1();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                increment2();
            }
        });
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("final counter1 value :" + counter1);
        System.out.println("final counter2 value :" + counter2);
    }

    static void main() throws InterruptedException {

        var example = new SynchronizedBlock();


        example.execute();


    }


}
