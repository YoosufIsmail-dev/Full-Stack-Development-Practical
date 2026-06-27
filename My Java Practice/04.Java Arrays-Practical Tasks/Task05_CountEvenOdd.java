public class Task05_EvenOddCount {
    public static void main(String[] args) {
        int[] arr = {12, 45, 7, 89, 34, 20, 15, 8};
        int evenCount = 0;
        int oddCount = 0;
        
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] % 2 == 0) {
                evenCount++;
            } else {
                oddCount++;
            }
        }
        
        System.out.println("Even numbers: " + evenCount);
        System.out.println("Odd numbers: " + oddCount);
    }
}