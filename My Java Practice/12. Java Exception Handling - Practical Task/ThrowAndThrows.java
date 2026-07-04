import java.util.InputMismatchException;
import java.util.Scanner;

public class ThrowAndThrows {

    /**
     * Method checks if number is positive
     *
     * throws Exception → indicates caller must handle this exception
     */
    public static void checkPositive(int number) throws Exception {

        // Manually throw exception if number is negative
        if (number < 0) {
            throw new Exception("Negative number entered. Number must be positive.");
        }

        System.out.println(number + " is a positive number.");
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {
            // Input from user
            System.out.print("Enter a number to check if it is positive: ");
            int inputNumber = scanner.nextInt();

            // Method call that may throw exception
            checkPositive(inputNumber);

        } 
        // Handles invalid input (non-integer values)
        catch (InputMismatchException e) {
            System.out.println("Error: Invalid input. Please enter a valid integer.");

        } 
        // Handles custom exception thrown by checkPositive()
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());

        } 
        // Always executes (cleanup + final message)
        finally {
            System.out.println("Program continues gracefully after exception handling.");
            scanner.close();
        }
    }
}