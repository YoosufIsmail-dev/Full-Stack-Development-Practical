public class Task08_SecondLargest {
    public static void main(String[] args) {
        int[] numbers = {12, 45, 78, 23, 56, 89, 34, 67};
        
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
        
        System.out.println("Array elements: ");
        for (int num : numbers) {
            System.out.print(num + " ");
        }
        System.out.println("\nLargest element: " + largest);
        System.out.println("Second largest element: " + secondLargest);
    }
}