
public class Task04_AverageOfElements {

   public static void main(String[] args) {

        int[] arr = {8, 60, 40, 90, 20,25};
        int sum = 0;
        for (int i = 0; i < arr.length; i++) sum += arr[i];
        double average = (double) sum / arr.length;
        System.out.println("Average = " + average);
        
    }

}
