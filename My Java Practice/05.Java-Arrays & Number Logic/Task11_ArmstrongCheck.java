import java.util.Scanner;


public class Task11_ArmstrongCheck {

   public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int number = scanner.nextInt();

        int originalNumber = number;
        int sum = 0;

        int temp = number;
        int digitCount = 0;

        while (temp != 0) {
            temp /= 10;
            digitCount++;
        }

        temp = number;

        while (temp != 0) {
            int digit = temp % 10;
            int power = 1;

            for (int i = 1; i <= digitCount; i++) {
                power *= digit;
            }

            sum += power;
            temp /= 10;
        }

        if (sum == originalNumber) {
            System.out.println(originalNumber + " is an Armstrong number.");
        } else {
            System.out.println(originalNumber + " is not an Armstrong number.");
        }

        scanner.close();
    }
}
