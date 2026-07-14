import java.util.Scanner;

public class Solution {
    static int A;
    
    static boolean checkPattern(int a, int b) throws Exception {
        if (a <= 0 || b <= 0) {
            throw new Exception("Breadth and height must be positive");
        }
        return true;
    }
    
    static {
        try {
            Scanner scan = new Scanner(System.in);
            int A1 = scan.nextInt();
            int H1 = scan.nextInt();
            
            if (checkPattern(A1, H1)) {
                A = A1 * H1;
            }
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
    }
    
    public static void main(String[] args) {
        System.out.println(A);
    }
}
