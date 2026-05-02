package curiouscoder;

public class InconsistencyProblem {

    // incrementing an integer is not an atomic process
    private static int counter;

    private static void increment(){
        counter++;
    }

    static void main() throws InterruptedException {
        Thread t1 = new Thread(()->{
            for(int i = 0;i <1000;i++){
                increment();
            }
        });

        Thread t2 = new Thread(()->{
            for(int i = 0;i <1000;i++){
                increment();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("final counter value :" + counter);
    }
}
