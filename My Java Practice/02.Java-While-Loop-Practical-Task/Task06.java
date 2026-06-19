import java.util.Scanner;

public class Task06 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter a number: ");
        long number = input.nextLong();

        long temp = Math.abs(number); 

        
        if (temp == 0) {
            digitCount = 1;
        }


        while (temp != 0) {
            temp = temp / 10; 
            digitCount++;
        }

        System.out.println("Number: " + number);
        System.out.println("Number of digits: " + digitCount);

        input.close();
    }
}
