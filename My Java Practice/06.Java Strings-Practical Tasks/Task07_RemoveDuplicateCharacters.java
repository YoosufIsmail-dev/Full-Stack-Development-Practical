import java.util.Scanner;


public class Task07_RemoveDuplicateCharacters {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        String result = "";

        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            boolean alreadyAdded = false;

           
            for (int j = 0; j < result.length(); j++) {
                if (result.charAt(j) == currentChar) {
                    alreadyAdded = true;
                    break;
                }
            }

            if (!alreadyAdded) {
                result = result + currentChar;
            }
        }

        System.out.println("String after removing duplicate characters: " + result);

        scanner.close();
    }
}
