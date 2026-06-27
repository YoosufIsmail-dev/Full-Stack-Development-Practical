

public class Task01_LargestElement {

    public static void main(String[] args) {
        int[] arr = {10, 40, 7, 99, 4};
        int largest = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > largest) largest = arr[i];
        }
        System.out.println("Largest Element: " + largest);
    }
    
}
