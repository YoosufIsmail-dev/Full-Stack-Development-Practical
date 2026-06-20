import java.util.Scanner;

public class Task14 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter month number (1-12): ");
        int monthNumber = input.nextInt();

        switch (monthNumber) {
            case 1:
                System.out.println("Month " + monthNumber + ": January");
                break;
            case 2:
                System.out.println("Month " + monthNumber + ": February");
                break;
            case 3:
                System.out.println("Month " + monthNumber + ": March");
                break;
            case 4:
                System.out.println("Month " + monthNumber + ": April");
                break;
            case 5:
                System.out.println("Month " + monthNumber + ": May");
                break;
            case 6:
                System.out.println("Month " + monthNumber + ": June");
                break;
            case 7:
                System.out.println("Month " + monthNumber + ": July");
                break;
            case 8:
                System.out.println("Month " + monthNumber + ": August");
                break;
            case 9:
                System.out.println("Month " + monthNumber + ": September");
                break;
            case 10:
                System.out.println("Month " + monthNumber + ": October");
                break;
            case 11:
                System.out.println("Month " + monthNumber + ": November");
                break;
            case 12:
                System.out.println("Month " + monthNumber + ": December");
                break;
            default:
                System.out.println("Invalid month number. Please enter a value between 1 and 12.");
        }

        input.close();
    }
}
