

public class Task02_SmallestElement {


    public static void main(String[] args) {
        int[] arr = {10, 45, 6, 90, 64, 8, 9,};
        int smallest = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < smallest) smallest = arr[i];
        }
        System.out.println("Smallest Element: " + smallest);
    }
}

