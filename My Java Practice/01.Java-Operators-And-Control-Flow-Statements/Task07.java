import java.util.Scanner;

public class Task07 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter first number: ");
        double num1 = input.nextDouble();

        System.out.print("Enter second number: ");
        double num2 = input.nextDouble();

        if (num1 > num2) {
            System.out.println("The bigger number is: " + num1);
        } else if (num2 > num1) {
            System.out.println("The bigger number is: " + num2);
        } else {
            System.out.println("Both numbers are equal: " + num1);
        }

        input.close();
    }
}
