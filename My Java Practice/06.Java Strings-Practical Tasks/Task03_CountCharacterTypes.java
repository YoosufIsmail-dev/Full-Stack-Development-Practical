import java.util.Scanner;

 
public class Task03_CountCharacterTypes {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        int vowelCount = 0;
        int consonantCount = 0;
        int digitCount = 0;
        int specialCharacterCount = 0;

        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            char lowerChar = Character.toLowerCase(currentChar);

            if (currentChar >= 'a' && currentChar <= 'z' || currentChar >= 'A' && currentChar <= 'Z') {
                if (lowerChar == 'a' || lowerChar == 'e' || lowerChar == 'i'
                        || lowerChar == 'o' || lowerChar == 'u') {
                    vowelCount++;
                } else {
                    consonantCount++;
                }
            } else if (currentChar >= '0' && currentChar <= '9') {
                digitCount++;
            } else if (currentChar != ' ') {
               
                specialCharacterCount++;
            }
        }

        System.out.println("Vowels: " + vowelCount);
        System.out.println("Consonants: " + consonantCount);
        System.out.println("Digits: " + digitCount);
        System.out.println("Special characters: " + specialCharacterCount);

        scanner.close();
    }
}
