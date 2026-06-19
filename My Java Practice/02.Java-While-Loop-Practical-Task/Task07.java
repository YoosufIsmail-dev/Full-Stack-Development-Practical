import java.util.Scanner;

public class Task07 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter a number to find its factorial: ");
        int number = input.nextInt();

        if (number < 0) {
            System.out.println("Factorial is not defined for negative numbers.");
        } else {
            long factorial = 1;
            int i = 1;


            while (i <= number) {
                factorial *= i;
                i++;
            }

            System.out.println("Factorial of " + number + " = " + factorial);
        }

        input.close();
    }
}
