import java.util.Scanner;

public class Task06 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter student marks (out of 100): ");
        int marks = input.nextInt();

        if (marks >= 0 && marks <= 100) {
            if (marks >= 50) {
                System.out.println("Result: PASS  (Marks: " + marks + ")");
            } else {
                System.out.println("Result: FAIL  (Marks: " + marks + ")");
            }
        } else {
            System.out.println("Invalid marks entered. Please enter a value between 0 and 100.");
        }

        input.close();
    }
}
