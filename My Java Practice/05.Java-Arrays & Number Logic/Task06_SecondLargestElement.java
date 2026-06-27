
public class Task06_SecondLargestElement {
public static void main(String[] args) {
        int[] numbers = {4, 9, 2, 15, 7, 45,67,1,77};
        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;

        for (int num : numbers) {
            if (num > largest) {
                secondLargest = largest;
                largest = num;
            } else if (num > secondLargest && num != largest) {
                secondLargest = num;
            }
        }
    
        System.out.println("Second largest element: " + secondLargest);
    }
   
}
