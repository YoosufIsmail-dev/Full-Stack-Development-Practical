public class NumberPrinter {

    static class Printer {

        private static final int LIMIT = 10;
        private boolean isOddTurn = true; // controls execution order

       
        public synchronized void printOdd(int number) {

            while (!isOddTurn) {
                try {
                    wait(); // releases lock and waits
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            System.out.println("Odd  -> " + number);

            // Switch turn to even thread
            isOddTurn = false;

            notifyAll(); // wake up waiting threads
        }

     
        public synchronized void printEven(int number) {

            while (isOddTurn) {
                try {
                    wait(); // releases lock and waits
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            System.out.println("Even -> " + number);

            // Switch turn back to odd thread
            isOddTurn = true;

            notifyAll(); // wake up waiting threads
        }
    }

  
    static class OddNumberThread extends Thread {

        private final Printer printer;

        OddNumberThread(Printer printer) {
            this.printer = printer;
        }

        @Override
        public void run() {
            for (int i = 1; i <= Printer.LIMIT; i += 2) {
                printer.printOdd(i);
            }
        }
    }

    /**
     * EvenNumberThread
     * Responsible for printing even numbers only
     */
    static class EvenNumberThread extends Thread {

        private final Printer printer;

        EvenNumberThread(Printer printer) {
            this.printer = printer;
        }

        @Override
        public void run() {
            for (int i = 2; i <= Printer.LIMIT; i += 2) {
                printer.printEven(i);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Printer printer = new Printer();

        // Creating two threads (odd and even)
        Thread t1 = new OddNumberThread(printer);
        Thread t2 = new EvenNumberThread(printer);

        System.out.println("Starting number printing using two threads...\n");

        // start() → begins concurrent execution
        t1.start();
        t2.start();

        // join() → ensures main thread waits for both threads
        t1.join();
        t2.join();

        System.out.println("\n✓ Completed printing 1 to " + Printer.LIMIT);
    }
}