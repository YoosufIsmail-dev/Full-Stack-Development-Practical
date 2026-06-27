import java.util.Scanner;


public class Task06_AnagramCheck {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the first string: ");
        String firstInput = scanner.nextLine();

        System.out.print("Enter the second string: ");
        String secondInput = scanner.nextLine();

        String firstCleaned = removeSpacesAndLowercase(firstInput);
        String secondCleaned = removeSpacesAndLowercase(secondInput);

        boolean isAnagram = true;

        if (firstCleaned.length() != secondCleaned.length()) {
            isAnagram = false;
        } else {
            int[] characterCounts = new int[256];

            for (int i = 0; i < firstCleaned.length(); i++) {
                characterCounts[firstCleaned.charAt(i)]++;
            }

            for (int i = 0; i < secondCleaned.length(); i++) {
                characterCounts[secondCleaned.charAt(i)]--;
            }

            for (int i = 0; i < characterCounts.length; i++) {
                if (characterCounts[i] != 0) {
                    isAnagram = false;
                    break;
                }
            }
        }

        if (isAnagram) {
            System.out.println("\"" + firstInput + "\" and \"" + secondInput + "\" ARE anagrams.");
        } else {
            System.out.println("\"" + firstInput + "\" and \"" + secondInput + "\" are NOT anagrams.");
        }

        scanner.close();
    }

    private static String removeSpacesAndLowercase(String text) {
        String result = "";
        String lowerText = text.toLowerCase();

        for (int i = 0; i < lowerText.length(); i++) {
            char currentChar = lowerText.charAt(i);
            if (currentChar != ' ') {
                result = result + currentChar;
            }
        }

        return result;
    }
}
