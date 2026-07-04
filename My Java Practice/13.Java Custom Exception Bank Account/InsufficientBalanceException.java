// Custom exception class for handling insufficient balance cases
public class InsufficientBalanceException extends Exception {

    public InsufficientBalanceException(String message) {
        super(message); // Pass error message to parent Exception class
    }
}