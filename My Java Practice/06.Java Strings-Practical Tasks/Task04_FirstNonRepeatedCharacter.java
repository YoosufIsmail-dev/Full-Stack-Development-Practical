import java.util.Scanner;


public class Task04_FirstNonRepeatedCharacter {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        char firstNonRepeated = '\0';
        boolean found = false;

        for (int i = 0; i < input.length() && !found; i++) {
            char currentChar = input.charAt(i);
            int occurrenceCount = 0;

            for (int j = 0; j < input.length(); j++) {
                if (input.charAt(j) == currentChar) {
                    occurrenceCount++;
                }
            }

            if (occurrenceCount == 1) {
                firstNonRepeated = currentChar;
                found = true;
            }
        }

        if (found) {
            System.out.println("The first non-repeated character is: " + firstNonRepeated);
        } else {
            System.out.println("There is no non-repeated character in the string.");
        }

        scanner.close();
    }
}
