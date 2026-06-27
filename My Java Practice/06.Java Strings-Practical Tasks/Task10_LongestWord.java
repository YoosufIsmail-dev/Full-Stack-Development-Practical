import java.util.Scanner;


public class Task10_LongestWord {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a sentence: ");
        String sentence = scanner.nextLine();

        String currentWord = "";
        String longestWord = "";

        for (int i = 0; i < sentence.length(); i++) {
            char currentChar = sentence.charAt(i);

            if (currentChar != ' ') {
                currentWord = currentWord + currentChar;
            } else {
                if (currentWord.length() > longestWord.length()) {
                    longestWord = currentWord;
                }
                currentWord = ""; 
            }
        }

       
        if (currentWord.length() > longestWord.length()) {
            longestWord = currentWord;
        }

        if (longestWord.length() == 0) {
            System.out.println("No words found in the sentence.");
        } else {
            System.out.println("The longest word is: \"" + longestWord + "\" (" + longestWord.length() + " characters)");
        }

        scanner.close();
    }
}
