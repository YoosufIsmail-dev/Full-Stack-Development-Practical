import java.util.Scanner;

public class Task02 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Odd numbers between 1 and 50:");

        int number = 1;

     
        while (number <= 50) {
            if (number % 2 != 0) {
                System.out.print(number + " ");
            }
            number++;
        }

        System.out.println();
        input.close();
    }
}
