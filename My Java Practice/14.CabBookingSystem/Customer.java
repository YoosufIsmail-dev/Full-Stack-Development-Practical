/*
  Customer.java
  Represents a customer thread attempting to book a shared cab.
 */
public class Customer extends Thread {

    private final Cab cab;
    private final String customerName;

  
    public Customer(Cab cab, String customerName) {
        this.cab = cab;
        this.customerName = customerName;
    }

    /*
      Thread execution.
      Each customer attempts to book the shared cab.
     */
    @Override
    public void run() {

        // Simulate a small delay to increase concurrent execution
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Attempt to book the cab
        cab.bookCab(customerName);
    }
}