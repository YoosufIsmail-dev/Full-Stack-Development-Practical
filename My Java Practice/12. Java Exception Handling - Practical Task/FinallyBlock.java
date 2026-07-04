import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * FinallyBlock
finally block is used for cleanup operations such as closing Scanner.
 */
public class FinallyBlock {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {
            // Input: first number
            System.out.print("Enter the first number: ");
            double firstNumber = scanner.nextDouble();

            // Input: second number
            System.out.print("Enter the second number: ");
            double secondNumber = scanner.nextDouble();

            // Perform division operation
            double result = firstNumber / secondNumber;

            // Check for division by zero (infinite result case)
            if (Double.isInfinite(result)) {
                throw new ArithmeticException("Division by zero is not allowed.");
            }

            System.out.println("Result of division: " + result);

        } 
        // Handles arithmetic errors (e.g., division by zero)
        catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());

        } 
        // Handles invalid input (non-numeric values)
        catch (InputMismatchException e) {
            System.out.println("Error: Invalid input. Please enter numeric values only.");

        } 
        // Always executes regardless of success or error
        finally {
            System.out.println("Program execution completed");
            scanner.close(); // resource cleanup
        }
    }
}