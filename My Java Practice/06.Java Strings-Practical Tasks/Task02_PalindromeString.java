import java.util.Scanner;


public class Task02_PalindromeString {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        String normalizedInput = input.toLowerCase();

        int start = 0;
        int end = normalizedInput.length() - 1;
        boolean isPalindrome = true;

        while (start < end) {
            if (normalizedInput.charAt(start) != normalizedInput.charAt(end)) {
                isPalindrome = false;
                break;
            }
            start++;
            end--;
        }

        if (isPalindrome) {
            System.out.println("\"" + input + "\" is a Palindrome string.");
        } else {
            System.out.println("\"" + input + "\" is NOT a Palindrome string.");
        }

        scanner.close();
    }
}
