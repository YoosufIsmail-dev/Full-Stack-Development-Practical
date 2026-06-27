public class Task03_EvenOddCount {
    public static void main(String[] args) {
        int[] numbers = {4, 9, 2, 15, 7, 8};
        int evenCount = 0;
        int oddCount = 0;

        for (int num : numbers) {
            if (num % 2 == 0) {
                evenCount++;
            } else {
                oddCount++;
            }
        }

        System.out.println("Even numbers: " + evenCount);
        System.out.println("Odd numbers: " + oddCount);
    }
}

