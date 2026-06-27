import java.util.Scanner;


public class Task08_CharacterFrequency {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        boolean[] alreadyPrinted = new boolean[input.length()];

        System.out.println("Character frequencies:");

        for (int i = 0; i < input.length(); i++) {
            if (alreadyPrinted[i]) {
                continue;
            }

            char currentChar = input.charAt(i);
            int frequency = 0;

            for (int j = 0; j < input.length(); j++) {
                if (input.charAt(j) == currentChar) {
                    frequency++;
                    alreadyPrinted[j] = true;
                }
            }

            if (currentChar == ' ') {
                System.out.println("'(space)' -> " + frequency + " time(s)");
            } else {
                System.out.println("'" + currentChar + "' -> " + frequency + " time(s)");
            }
        }

        scanner.close();
    }
}
