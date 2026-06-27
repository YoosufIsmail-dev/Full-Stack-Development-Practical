
public class Task04_ReverseArray {

  
    public static void main(String[] args) {
        int[] numbers = {12, 45, 67, 89, 23, 56, 78, 34};
        
        System.out.print("Original array: ");
        for (int num : numbers) {
            System.out.print(num + " ");
        }

        int left = 0;
        int right = numbers.length - 1;
        
        while (left < right) {
         
            int temp = numbers[left];
            numbers[left] = numbers[right];
            numbers[right] = temp;
            left++;
            right--;
        }
        
        System.out.print("\nReversed array: ");
        for (int num : numbers) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}

