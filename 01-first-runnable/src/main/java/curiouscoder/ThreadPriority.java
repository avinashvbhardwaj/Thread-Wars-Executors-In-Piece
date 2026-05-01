package curiouscoder;

class Task implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {

            System.out.println(Thread.currentThread().getName() + " - COUNT " + i);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}

public class ThreadPriority {

    static void main() {

        var low = new Thread(new Task(), "Low Priority Thread");
        var normal = new Thread(new Task(), "Normal Priority Thread");
        var high = new Thread(new Task(), "High Priority Thread");

        // Notes on Priority:
        // - Priority is just a hint to the thread scheduler, NOT a guarantee
        // - Higher priority threads may get more CPU time, but behavior is OS-dependent
        // - Do NOT rely on priority for correctness or execution order

        // Starvation:
        // - Occurs when a low-priority thread rarely or never gets CPU time
        // - High-priority threads can dominate execution, causing others to "starve"
        // - Can also happen with unfair locks or resource contention

        // Best Practice:
        // - Avoid depending on priority for fairness
        // - Use proper synchronization tools (locks, queues, executors)
        // - Prefer fair locks or thread pools to reduce starvation risk

        low.setPriority(Thread.MIN_PRIORITY);
        normal.setPriority(Thread.NORM_PRIORITY);
        high.setPriority(Thread.MAX_PRIORITY);

        low.start();
        normal.start();
        high.start();
    }
}
