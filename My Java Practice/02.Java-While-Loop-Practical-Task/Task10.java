import java.util.Scanner;

public class Task10 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int choice = 0;

        System.out.println("===== Menu-Driven Calculator =====");

        
        while (choice != 5) {

            // Display menu
            System.out.println("\nSelect an operation:");
            System.out.println("1. Addition");
            System.out.println("2. Subtraction");
            System.out.println("3. Multiplication");
            System.out.println("4. Division");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");

            choice = input.nextInt();

            if (choice == 5) {
                System.out.println("Exiting calculator. Goodbye!");
                break;
            }

            if (choice < 1 || choice > 5) {
                System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                continue;
            }

            System.out.print("Enter first number: ");
            double num1 = input.nextDouble();

            System.out.print("Enter second number: ");
            double num2 = input.nextDouble();

            switch (choice) {
                case 1:
                    System.out.println("Result: " + num1 + " + " + num2 + " = " + (num1 + num2));
                    break;
                case 2:
                    System.out.println("Result: " + num1 + " - " + num2 + " = " + (num1 - num2));
                    break;
                case 3:
                    System.out.println("Result: " + num1 + " * " + num2 + " = " + (num1 * num2));
                    break;
                case 4:
                    if (num2 != 0) {
                        System.out.println("Result: " + num1 + " / " + num2 + " = " + (num1 / num2));
                    } else {
                        System.out.println("Error: Cannot divide by zero.");
                    }
                    break;
            }
        }

        input.close();
    }
}
