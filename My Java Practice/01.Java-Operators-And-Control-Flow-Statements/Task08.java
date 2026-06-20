import java.util.Scanner;

public class Task08 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter student marks (0 - 100): ");
        int marks = input.nextInt();

        if (marks >= 0 && marks <= 100) {
            if (marks >= 90) {
                System.out.println("Grade: A+ (Excellent)");
            } else if (marks >= 80) {
                System.out.println("Grade: A  (Very Good)");
            } else if (marks >= 70) {
                System.out.println("Grade: B  (Good)");
            } else if (marks >= 60) {
                System.out.println("Grade: C  (Average)");
            } else if (marks >= 50) {
                System.out.println("Grade: D  (Pass)");
            } else {
                System.out.println("Grade: F  (Fail)");
            }
        } else {
            System.out.println("Invalid marks. Please enter a value between 0 and 100.");
        }

        input.close();
    }
}
