import java.util.Scanner;


public class Task10_PalindromeCheck {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int number = scanner.nextInt();

        int originalNumber = number;
        int reversedNumber = 0;

        int tempNumber = number;

        if (tempNumber < 0) {
            tempNumber = -tempNumber;
        }

        while (tempNumber != 0) {
            int lastDigit = tempNumber % 10;
            reversedNumber = (reversedNumber * 10) + lastDigit;
            tempNumber = tempNumber / 10;
        }

        if (originalNumber >= 0 && originalNumber == reversedNumber) {
            System.out.println(originalNumber + " is a Palindrome number.");
        } else {
            System.out.println(originalNumber + " is NOT a Palindrome number.");
        }

        scanner.close();
    }
}
