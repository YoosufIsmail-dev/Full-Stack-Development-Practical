/*
  Cab.java
  Demonstrates thread synchronization using the synchronized keyword.
 */
public class Cab {

    // True if the cab is available; false if already booked
    private boolean isAvailable = true;

    
    public synchronized boolean bookCab(String customerName) {

        if (isAvailable) {
            isAvailable = false;
            System.out.println(customerName + " successfully booked the cab.");
            return true;
        } else {
            System.out.println(customerName
                    + " failed to book the cab. Cab is already booked.");
            return false;
        }
    }

    /*
     Returns the current availability status of the cab.
     */
    public synchronized boolean isAvailable() {
        return isAvailable;
    }
}