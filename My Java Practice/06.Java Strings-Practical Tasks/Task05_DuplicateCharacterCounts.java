import java.util.Scanner;

public class Task05_DuplicateCharacterCounts {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        boolean[] alreadyReported = new boolean[input.length()];
        boolean foundAnyDuplicate = false;

        System.out.println("Duplicate characters and their counts:");

        for (int i = 0; i < input.length(); i++) {
            if (alreadyReported[i]) {
                continue; 
            }

            char currentChar = input.charAt(i);
            int occurrenceCount = 0;

            for (int j = 0; j < input.length(); j++) {
                if (input.charAt(j) == currentChar) {
                    occurrenceCount++;
                }
            }

            if (occurrenceCount > 1) {
                System.out.println("'" + currentChar + "' -> " + occurrenceCount + " times");
                foundAnyDuplicate = true;

                
                for (int j = 0; j < input.length(); j++) {
                    if (input.charAt(j) == currentChar) {
                        alreadyReported[j] = true;
                    }
                }
            }
        }

        if (!foundAnyDuplicate) {
            System.out.println("No duplicate characters found.");
        }

        scanner.close();
    }
}
