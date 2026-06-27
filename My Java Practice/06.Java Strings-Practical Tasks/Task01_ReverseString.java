import java.util.Scanner;


public class Task01_ReverseString {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        char[] characters = input.toCharArray();

        int start = 0;
        int end = characters.length - 1;

        while (start < end) {
           
            char temp = characters[start];
            characters[start] = characters[end];
            characters[end] = temp;

            start++;
            end--;
        }

      
        String reversedString = "";
        for (int i = 0; i < characters.length; i++) {
            reversedString = reversedString + characters[i];
        }

        System.out.println("Reversed string: " + reversedString);

        scanner.close();
    }
}
