import java.util.Scanner;

public class Task09 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the number of terms (n): ");
        int n = input.nextInt();

        if (n <= 0) {
            System.out.println("Please enter a positive number.");
        } else {
            System.out.println("Fibonacci Series up to " + n + " terms:");

            int first = 0;  
            int second = 1; 
            int count = 1;

  
            while (count <= n) {
                System.out.print(first + " ");

                int nextTerm = first + second; 
                first = second;             
                second = nextTerm;

                count++;
            }

            System.out.println();
        }

        input.close();
    }
}
