import java.util.Scanner;

public class Task09 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter day number (1-7): ");
        int dayNumber = input.nextInt();

        switch (dayNumber) {
            case 1:
                System.out.println("Day " + dayNumber + ": Monday");
                break;
            case 2:
                System.out.println("Day " + dayNumber + ": Tuesday");
                break;
            case 3:
                System.out.println("Day " + dayNumber + ": Wednesday");
                break;
            case 4:
                System.out.println("Day " + dayNumber + ": Thursday");
                break;
            case 5:
                System.out.println("Day " + dayNumber + ": Friday");
                break;
            case 6:
                System.out.println("Day " + dayNumber + ": Saturday");
                break;
            case 7:
                System.out.println("Day " + dayNumber + ": Sunday");
                break;
            default:
                System.out.println("Invalid day number. Please enter a value between 1 and 7.");
        }

        input.close();
    }
}
