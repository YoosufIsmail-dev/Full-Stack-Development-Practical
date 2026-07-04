import java.util.ArrayList;
import java.util.List;


public class StudentResultGenerator {

  
    static class ResultReport {

        private final StringBuilder report = new StringBuilder();

        // Critical section: only one thread can execute at a time
        public synchronized void addResult(String studentName, int marks) {
            String grade = calculateGrade(marks);

            report.append(String.format(
                    "%-10s | Marks: %3d | Grade: %s%n",
                    studentName, marks, grade
            ));
        }

        // Grade calculation logic (no threading needed here)
        private String calculateGrade(int marks) {
            if (marks >= 90) return "A+";
            if (marks >= 75) return "A";
            if (marks >= 60) return "B";
            if (marks >= 50) return "C";
            return "F";
        }

        public void printReport() {
            System.out.println("\n========== FINAL STUDENT REPORT ==========");
            System.out.print(report);
            System.out.println("===========================================");
        }
    }

    /**
     * StudentThread represents each student running in parallel
     */
    static class StudentThread extends Thread {

        private final ResultReport report;
        private final String studentName;

        StudentThread(String studentName, ResultReport report) {
            super(studentName); // set thread name
            this.studentName = studentName;
            this.report = report;
        }

        @Override
        public void run() {

            // Step 1: simulate mark calculation (concurrent work)
            int marks = calculateMarks();

            try {
                // Simulate processing delay (thread switching visible)
                Thread.sleep(150);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            // Step 2: safely write result to shared report
            report.addResult(studentName, marks);

            System.out.println(studentName + " calculated marks: " + marks);
        }

        // Simple deterministic mark generation based on name
        private int calculateMarks() {
            return 50 + (getName().length() * 7) % 51;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        ResultReport report = new ResultReport();

        List<StudentThread> students = new ArrayList<>();

        // Creating multiple threads (each student runs independently)
        students.add(new StudentThread("Ahmed", report));
        students.add(new StudentThread("Fatima", report));
        students.add(new StudentThread("Yoosuf", report));
        students.add(new StudentThread("Zara", report));

        System.out.println("Calculating student results...\n");

        // Start all threads → concurrent execution begins
        for (StudentThread student : students) {
            student.start();
        }

        // Check thread status using isAlive() before and after join()
        for (StudentThread student : students) {

            System.out.println(student.getName() +
                    " is alive? " + student.isAlive());

            // join() → main thread waits for this student thread to finish
            student.join();

            System.out.println(student.getName() +
                    " is alive after join? " + student.isAlive());
        }

        // Print final combined report (after all threads complete)
        report.printReport();
    }
}