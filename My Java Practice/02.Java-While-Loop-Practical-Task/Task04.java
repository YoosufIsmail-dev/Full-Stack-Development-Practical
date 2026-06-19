import java.util.Scanner;

public class Task04 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter a number to print its multiplication table: ");
        int num = input.nextInt();

        System.out.println("\nMultiplication Table of " + num + ":");

        int multiplier = 1;

        
        while (multiplier <= 10) {
            System.out.println(num + " x " + multiplier + " = " + (num * multiplier));
            multiplier++;
        }

        input.close();
    }
}
