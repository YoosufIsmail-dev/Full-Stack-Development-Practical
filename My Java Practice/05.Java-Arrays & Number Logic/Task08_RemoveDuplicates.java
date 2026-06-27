
public class Task08_RemoveDuplicates {

  public static void main(String[] args) {
        int[] numbers = {12, 45, 67, 89, 23, 45, 78, 89, 12};
        
        System.out.print("Original array: ");
        for (int num : numbers) {
            System.out.print(num + " ");
        }
        
     
        int uniqueCount = 0;
        for (int i = 0; i < numbers.length; i++) {
            boolean isDuplicate = false;
            for (int j = 0; j < i; j++) {
                if (numbers[i] == numbers[j]) {
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                uniqueCount++;
            }
        }
        
  
        int[] uniqueArray = new int[uniqueCount];
        int index = 0;
        for (int i = 0; i < numbers.length; i++) {
            boolean isDuplicate = false;
            for (int j = 0; j < i; j++) {
                if (numbers[i] == numbers[j]) {
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                uniqueArray[index] = numbers[i];
                index++;
            }
        }
        
        System.out.print("\nArray after removing duplicates: ");
        for (int num : uniqueArray) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
  
}
