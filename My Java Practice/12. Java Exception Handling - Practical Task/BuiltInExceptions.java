import java.util.InputMismatchException;
import java.util.Scanner;


public class BuiltInExceptions {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {
            // Taking numerator input from user
            System.out.print("Enter numerator (integer): ");
            int numerator = scanner.nextInt();

            // Taking denominator input from user
            System.out.print("Enter denominator (integer): ");
            int denominator = scanner.nextInt();

            // Performing division (may throw ArithmeticException)
            int result = numerator / denominator;

            System.out.println("Result: " + numerator + " / " + denominator + " = " + result);

        } 
        // Handles invalid input (e.g., letters instead of numbers)
        catch (InputMismatchException ime) {
            System.out.println("Input error: please enter valid integers.");

        } 
        // Handles division by zero error
        catch (ArithmeticException ae) {
            System.out.println("Math error: division by zero is not allowed.");

        } 
        // Always executes (used for cleanup)
        finally {
            scanner.close();
        }
    }
}