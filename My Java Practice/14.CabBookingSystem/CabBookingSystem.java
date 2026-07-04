/*

  Main class that demonstrates multithreading and synchronization.
 */
public class CabBookingSystem {

    public static void main(String[] args) {

        System.out.println("===== Cab Booking System =====");
        System.out.println("Multiple customers are trying to book the same cab...\n");

        // Shared Cab object
        Cab cab = new Cab();

        // Create customer threads
        Customer customer1 = new Customer(cab, "Customer 1");
        Customer customer2 = new Customer(cab, "Customer 2");
        Customer customer3 = new Customer(cab, "Customer 3");
        Customer customer4 = new Customer(cab, "Customer 4");
        Customer customer5 = new Customer(cab, "Customer 5");

        // Start all customer threads
        customer1.start();
        customer2.start();
        customer3.start();
        customer4.start();
        customer5.start();

        // Wait for all threads to complete
        try {
            customer1.join();
            customer2.join();
            customer3.join();
            customer4.join();
            customer5.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Main thread interrupted.");
        }

        // Display final result
        System.out.println("\n===== Final Result =====");

        if (!cab.isAvailable()) {
            System.out.println("Cab booking completed successfully.");
            System.out.println("Only one customer was able to book the cab.");
        } else {
            System.out.println("No customer booked the cab.");
        }
    }
}