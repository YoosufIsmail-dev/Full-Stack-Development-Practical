public class Task01_LargestElement {
    public static void main(String[] args) {
        int[] numbers = {4, 9, 2, 15, 7};
        int largest = numbers[0];

        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] > largest) {
                largest = numbers[i];
            }
        }

        System.out.println("Array Elements:");
        for (int num : numbers) {
            System.out.print(num + " ");
        }
        System.out.println("\nLargest element: " + largest);
    }
}
