public class PrinterService {

   
    static class Printer {

        public synchronized void printDocument(String employeeName, String documentName) {

            // Thread enters critical section (only one at a time)
            System.out.println(employeeName + " is printing: " + documentName);

            try {
                // Simulate printing time (helps show thread behavior clearly)
                Thread.sleep(400);
            } catch (InterruptedException e) {

                // Restore interrupt status and exit safely
                Thread.currentThread().interrupt();
                System.out.println("Printing interrupted for: " + documentName);
                return;
            }

            System.out.println(employeeName + " finished printing: " + documentName);
            System.out.println("------------------------------------");
        }
    }

   
    static class EmployeeThread extends Thread {

        private final Printer printer;
        private final String documentName;

        EmployeeThread(String employeeName, String documentName, Printer printer) {
            super(employeeName); // sets thread name
            this.documentName = documentName;
            this.printer = printer;
        }

        @Override
        public void run() {
            // Each thread executes this concurrently
            printer.printDocument(getName(), documentName);
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Printer printer = new Printer();

        // Creating multiple employee threads (concurrent execution)
        EmployeeThread emp1 = new EmployeeThread("Employee-A", "Invoice.pdf", printer);
        EmployeeThread emp2 = new EmployeeThread("Employee-B", "Report.docx", printer);
        EmployeeThread emp3 = new EmployeeThread("Employee-C", "Presentation.pptx", printer);

        // Thread priority (NOTE: not guaranteed by JVM scheduling)
        emp1.setPriority(Thread.NORM_PRIORITY);
        emp2.setPriority(Thread.MAX_PRIORITY);
        emp3.setPriority(Thread.MIN_PRIORITY);

        System.out.println("Sending print jobs to the printer...\n");

        // start() → begins concurrent execution
        emp1.start();
        emp2.start();
        emp3.start();

        // join() → main thread waits for all printing to complete
        emp1.join();
        emp2.join();
        emp3.join();

        System.out.println("\nAll print jobs have been completed.");
    }
}