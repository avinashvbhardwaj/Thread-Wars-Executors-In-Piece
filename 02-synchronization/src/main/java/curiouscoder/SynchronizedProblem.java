package curiouscoder;

public class SynchronizedProblem {

//    When dealing  with multiple  synchronized instance methods in a class,
//    all of them compete for same single lock - the object's monitor

    private static int counter1;
    private static int counter2;

//    if  Thread#1 holds the that ock executing method1(),
//    Thread#2 trying to enter method2() on the same object - must wait
//    even if method1(), method2(0 are logically independent

    private synchronized static void increment1() {
        counter1++;
    }
    private synchronized static void increment2() {
        counter2++;
    }

    static void main() throws InterruptedException {
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


}
