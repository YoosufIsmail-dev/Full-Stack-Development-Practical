import java.util.Scanner;

public class Task01 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Numbers from 1 to 100:");

        int number = 1;

    
        while (number <= 100) {
            System.out.print(number + " ");
            number++;
        }

        System.out.println(); 
        input.close();
    }
}
