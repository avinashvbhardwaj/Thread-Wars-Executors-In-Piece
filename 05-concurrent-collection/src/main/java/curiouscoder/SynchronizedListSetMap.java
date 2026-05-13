package curiouscoder;

public class SynchronizedListSetMap {

    public static void main(String[] args) {
        synchronizedList();
        synchronizedSet();
        synchronizedMap();
    }

    private static void synchronizedList() {
        System.out.println("Using synchronized list:");

        // add and remove method are synchronized
        // intrinsic lock is used to synchronize the add and remove method 
        // thread have to wait for each other to release the lock to perform the operation
        // Use concurrent collection (e.g., a concurrent list)
        java.util.List<Integer> myList = java.util.Collections.synchronizedList(new java.util.ArrayList<>());
        java.util.List<Thread> threads = new java.util.ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    for (int j = 0; j < 5000; j++) {
                        myList.add(j);
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

        System.out.println("List size: " + myList.size());

    }

    private static void synchronizedSet() {
        System.out.println("Using synchronized set:");
        java.util.Set<Integer> mySet = java.util.Collections.synchronizedSet(new java.util.HashSet<>());
        java.util.List<Thread> threads = new java.util.ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    for (int j = 0; j < 5000; j++) {
                        mySet.add(j);
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

        System.out.println("Set size: " + mySet.size());

    }

    private static void synchronizedMap() {
        System.out.println("Using synchronized map:");
        java.util.Map<Integer, String> myMap = java.util.Collections.synchronizedMap(new java.util.HashMap<>());
        java.util.List<Thread> threads = new java.util.ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    for (int j = 0; j < 5000; j++) {
                        myMap.put(j, "Value-" + j);
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

        System.out.println("Map size: " + myMap.size());
    }

}
