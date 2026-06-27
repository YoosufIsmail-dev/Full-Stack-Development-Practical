import java.util.Scanner;

public class Task06_SearchElement {
    public static void main(String[] args) {
        int[] numbers = {12, 45, 78, 23, 56, 89, 34, 67};
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter element to search: ");
        int searchElement = scanner.nextInt();
        
        boolean found = false;
        int position = -1;
        
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == searchElement) {
                found = true;
                position = i;
                break;
            }
        }
        
        System.out.println("Array elements: ");
        for (int num : numbers) {
            System.out.print(num + " ");
        }
        
        if (found) {
            System.out.println("\nElement " + searchElement + " found at position " + position);
        } else {
            System.out.println("\nElement " + searchElement + " not found in the array");
        }
        
        scanner.close();
    }
}