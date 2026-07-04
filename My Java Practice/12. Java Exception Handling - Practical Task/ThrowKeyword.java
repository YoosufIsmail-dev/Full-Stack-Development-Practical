import java.util.InputMismatchException;
import java.util.Scanner;


public class ThrowKeyword {

    public static void validateAge(int age) {

        // Business rule validation
        if (age < 18) {
            throw new IllegalArgumentException(
                    "Student must be at least 18 years old to proceed."
            );
        }

        System.out.println("Age verified successfully. Student is eligible.");
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {
            // User input for age
            System.out.print("Enter the student's age: ");
            int studentAge = scanner.nextInt();

            // Method call that may throw exception
            validateAge(studentAge);

        } 
        // Handles custom exception from validateAge()
        catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());

        } 
        // Handles invalid input type (non-integer values)
        catch (InputMismatchException e) {
            System.out.println("Error: Invalid input. Please enter a valid numeric age.");

        } 
        // Always executes for cleanup
        finally {
            scanner.close();
        }
    }
}