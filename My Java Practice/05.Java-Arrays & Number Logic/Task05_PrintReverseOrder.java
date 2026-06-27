
public class Task05_PrintReverseOrder {

    public static void main(String[] args) {
        int[] numbers = {12, 13, 14, 15, 16, 17, 18, 19};
        
        System.out.print("Original array: ");
        for (int num : numbers) {
            System.out.print(num + " ");
        }
        
        System.out.print("\nArray in reverse order: ");
        for (int i = numbers.length - 1; i >= 0; i--) {
            System.out.print(numbers[i] + " ");
        }
        System.out.println();
    }
}
  

