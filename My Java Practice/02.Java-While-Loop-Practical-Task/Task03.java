import java.util.Scanner;

public class Task03 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number (n): ");
        int n = sc.nextInt();
        int i = 2;
        int sum = 0;

        while (i <= n) {
            sum += i;
            i += 2;
        }

        System.out.println("Sum of even numbers from 1 to " + n + " is: " + sum);
        sc.close();
    }
}