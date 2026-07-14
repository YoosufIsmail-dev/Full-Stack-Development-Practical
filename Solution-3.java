import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int numSentences = Integer.parseInt(in.nextLine());

        while (numSentences-- > 0) {
            String input = in.nextLine();

            String regex = "\\b(\\w+)(\\s+\\1\\b)+";

            Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

            Matcher m = p.matcher(input);

            while (m.find()) {
                input = input.replaceAll(m.group(), m.group(1));
                m = p.matcher(input);
            }

            System.out.println(input);
        }

        in.close();
    }
}
