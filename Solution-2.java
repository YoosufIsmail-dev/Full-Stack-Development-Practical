import java.util.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int testCases = Integer.parseInt(scan.nextLine());

        while (testCases-- > 0) {
            String inputLine = scan.nextLine();

            String pattern = "\\b(\\w+)(\\s+\\1\\b)+";                    //Write your regex here
            Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE); //Compile the pattern

            Matcher m = p.matcher(inputLine);                              //Find any match in inputLine

            String result = m.replaceAll("$1");                           //Use the replaceAll method

            System.out.println(result);
        }
    }
}
