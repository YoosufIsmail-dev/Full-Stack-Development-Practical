import java.util.Scanner;

public class Task11 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter a character: ");
        char ch = input.next().charAt(0);

       
        char lowerCh = Character.toLowerCase(ch);

        if (Character.isLetter(ch)) {
            switch (lowerCh) {
                case 'a':
                case 'e':
                case 'i':
                case 'o':
                case 'u':
                    System.out.println("'" + ch + "' is a Vowel.");
                    break;
                default:
                    System.out.println("'" + ch + "' is a Consonant.");
            }
        } else {
            System.out.println("'" + ch + "' is not a letter.");
        }

        input.close();
    }
}
