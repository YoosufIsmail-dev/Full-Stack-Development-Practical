import java.util.Scanner;

public class Task08 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int number = input.nextInt();

        int originalNumber = number;
        int reversedNumber = 0;

        
        while (number != 0) {
            int lastDigit = number % 10;
            reversedNumber = reversedNumber * 10 + lastDigit;
            number = number / 10;
        }

        System.out.println("Original Number: " + originalNumber);
        System.out.println("Reversed Number: " + reversedNumber);


        if (originalNumber == reversedNumber) {
            System.out.println(originalNumber + " is a Palindrome number.");
        } else {
            System.out.println(originalNumber + " is NOT a Palindrome number.");
        }

        input.close();
    }
}
