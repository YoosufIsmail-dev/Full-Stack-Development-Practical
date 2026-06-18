public class Task01 {
    public static void main(String[] args) {

        
        for (int row = 1; row <= 5; row++) {

            for (int col = 1; col <= row; col++) {
                System.out.print("*");
            }

            System.out.println(); // Move to next line after each row
        }
    }
}
