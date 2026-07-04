import java.util.List;

public class ThreadExecutionManager {


    static class DetailsPrinter {

        public synchronized void printThreadDetails(String threadName, long id) {

            // This block is protected so only one thread executes at a time
            System.out.println("---------------------------------------");
            System.out.println("Thread Name : " + threadName);
            System.out.println("Thread ID   : " + id);

            // Shows current thread priority (managed by JVM/OS)
            System.out.println("Priority    : " + Thread.currentThread().getPriority());

            // Checks if the current thread is still running
            System.out.println("Is Alive    : " + Thread.currentThread().isAlive());

            try {
                // Simulate processing time so thread switching becomes visible
                Thread.sleep(300);
            } catch (InterruptedException e) {

                // Restore interrupt status and exit method safely
                Thread.currentThread().interrupt();
                System.out.println(threadName + " interrupted.");
                return;
            }

            System.out.println(threadName + " finished processing.");
            System.out.println("---------------------------------------\n");
        }
    }

    /**
     * Worker thread class
     * Each thread calls shared printer resource
     */
    static class Worker extends Thread {

        private final DetailsPrinter printer;

        Worker(String name, DetailsPrinter printer) {
            super(name); // set thread name
            this.printer = printer;
        }

        @Override
        public void run() {
            // Each thread executes this method concurrently
            printer.printThreadDetails(getName(), getId());
        }
    }

    public static void main(String[] args) throws InterruptedException {

        DetailsPrinter printer = new DetailsPrinter();

        // Creating 3 threads (requirement)
        Worker w1 = new Worker("Worker-1", printer);
        Worker w2 = new Worker("Worker-2", printer);
        Worker w3 = new Worker("Worker-3", printer);

        System.out.println("Starting all threads...\n");

        // start() → begins concurrent execution
        w1.start();
        w2.start();
        w3.start();

        // isAlive() → checks whether thread is currently running
        System.out.println("Thread status after start:");
        System.out.println(w1.getName() + " alive? " + w1.isAlive());
        System.out.println(w2.getName() + " alive? " + w2.isAlive());
        System.out.println(w3.getName() + " alive? " + w3.isAlive());

        System.out.println("\nWaiting for threads to finish...\n");

        // join() → main thread waits for all worker threads to finish
        w1.join();
        w2.join();
        w3.join();

        // After join(), all threads must be finished
        System.out.println("\nThread status after join:");
        System.out.println(w1.getName() + " alive? " + w1.isAlive());
        System.out.println(w2.getName() + " alive? " + w2.isAlive());
        System.out.println(w3.getName() + " alive? " + w3.isAlive());

        System.out.println("\nAll threads have completed execution.");
    }
}