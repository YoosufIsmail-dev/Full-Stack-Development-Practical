import java.util.Scanner;


public class Task09_RemoveDuplicates {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of elements: ");
        int size = scanner.nextInt();

        int[] numbers = new int[size];

        System.out.println("Enter " + size + " elements:");
        for (int i = 0; i < size; i++) {
            numbers[i] = scanner.nextInt();
        }

       
        int[] uniqueNumbers = new int[size];
        int uniqueCount = 0;

        for (int i = 0; i < numbers.length; i++) {
            boolean isDuplicate = false;

          
            for (int j = 0; j < uniqueCount; j++) {
                if (numbers[i] == uniqueNumbers[j]) {
                    isDuplicate = true;
                    break;
                }
            }

            if (!isDuplicate) {
                uniqueNumbers[uniqueCount] = numbers[i];
                uniqueCount++;
            }
        }

        System.out.print("Array after removing duplicates: ");
        for (int i = 0; i < uniqueCount; i++) {
            System.out.print(uniqueNumbers[i]);
            if (i < uniqueCount - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();

        scanner.close();
    }
}