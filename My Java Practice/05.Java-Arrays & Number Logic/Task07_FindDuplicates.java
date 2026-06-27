
public class Task07_FindDuplicates {

    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 2, 4, 5, 3, 4, 5, 4};

        System.out.println("Duplicate elements:");
        boolean found = false;

        for (int i = 0; i < numbers.length; i++) {
            boolean alreadyChecked = false;

            for (int j = 0; j < i; j++) {
                if (numbers[i] == numbers[j]) {
                    alreadyChecked = true;
                    break;
                }
            }

            if (!alreadyChecked) {
                for (int j = i + 1; j < numbers.length; j++) {
                    if (numbers[i] == numbers[j]) {
                        System.out.println(numbers[i]);
                        found = true;
                        break;
                    }
                }
            }
        }

        if (!found) {
            System.out.println("No duplicates found.");
        }
    }
}
