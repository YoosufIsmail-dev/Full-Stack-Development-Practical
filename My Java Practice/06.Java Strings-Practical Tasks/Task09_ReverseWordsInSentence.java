import java.util.Scanner;

public class Task09_ReverseWordsInSentence {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a sentence: ");
        String sentence = scanner.nextLine();

    
        String[] words = new String[countWords(sentence)];
        int wordIndex = 0;
        String currentWord = "";

        for (int i = 0; i < sentence.length(); i++) {
            char currentChar = sentence.charAt(i);

            if (currentChar != ' ') {
                currentWord = currentWord + currentChar;
            } else if (currentWord.length() > 0) {
                words[wordIndex] = currentWord;
                wordIndex++;
                currentWord = "";
            }
        }

       
        if (currentWord.length() > 0) {
            words[wordIndex] = currentWord;
            wordIndex++;
        }

       
        String reversedSentence = "";
        for (int i = words.length - 1; i >= 0; i--) {
            reversedSentence = reversedSentence + words[i];
            if (i != 0) {
                reversedSentence = reversedSentence + " ";
            }
        }

        System.out.println("Sentence with reversed word order: " + reversedSentence);

        scanner.close();
    }

    private static int countWords(String sentence) {
        int wordCount = 0;
        boolean insideWord = false;

        for (int i = 0; i < sentence.length(); i++) {
            char currentChar = sentence.charAt(i);

            if (currentChar != ' ' && !insideWord) {
                insideWord = true;
                wordCount++;
            } else if (currentChar == ' ') {
                insideWord = false;
            }
        }

        return wordCount;
    }
}
