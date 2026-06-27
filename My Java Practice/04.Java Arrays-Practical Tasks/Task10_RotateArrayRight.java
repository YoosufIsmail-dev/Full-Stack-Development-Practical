public class Task10_RotateArray {
    public static void main(String[] args) {
        int[] numbers = {12, 45, 78, 23, 56, 89, 34, 67};
        
        System.out.println("Original array: ");
        for (int num : numbers) {
            System.out.print(num + " ");
        }
        
        int lastElement = numbers[numbers.length - 1];
        
       
        for (int i = numbers.length - 1; i > 0; i--) {
            numbers[i] = numbers[i - 1];
        }
        
  
        numbers[0] = lastElement;
        
        System.out.println("\nArray after right rotation by one position: ");
        for (int num : numbers) {
            System.out.print(num + " ");
        }
    }
}