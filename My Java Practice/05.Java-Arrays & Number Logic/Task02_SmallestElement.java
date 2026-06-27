public class Task02_SmallestElement {
    public static void main(String[] args) {
        int[] numbers = {4, 9, 2, 15, 7};
        int smallest = numbers[0];

        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] < smallest) {
                smallest = numbers[i];
            }
        }

        System.out.println("Smallest element: " + smallest);
    }
}
