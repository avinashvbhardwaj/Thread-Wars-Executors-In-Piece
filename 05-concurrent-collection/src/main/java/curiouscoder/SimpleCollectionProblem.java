package curiouscoder;

public class SimpleCollectionProblem {

    public static void main(String[] args) {
        // Create a simple collection (e.g., an ArrayList)
        java.util.List<Integer> myCollection = new java.util.ArrayList<>();
        java.util.List<Thread> threads = new java.util.ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    for (int j = 0; j < 5000; j++) {
                        myCollection.add(j);
                    }
                }
            };
            threads.add(t);
        }
        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
            }
        }

        // Print the collection        
        System.out.println("Collection size: " + myCollection.size());

    }
}
