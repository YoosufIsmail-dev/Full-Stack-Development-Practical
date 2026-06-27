public class StudentManagementSystem {

    public static void main(String[] args) {


        Student student1 = new Student();


        Student student2 = new Student(101, "Mohamed Akram", "Data Science");


        Student student3 = new Student(student2);

        System.out.println(" Student 1: Default Constructor ");
        student1.displayDetails();

        System.out.println(" Student 2: Parameterized Constructor ");
        student2.displayDetails();

        System.out.println(" Student 3: Copy Constructor ");
        student3.displayDetails();
    }
}

class Student {

    private int studentId;
    private String studentName;
    private String course;

   
    public Student() {
        studentId = 0;
        studentName = "Unknown";
        course = "Not Assigned";
    }

  
    public Student(int studentId, String studentName, String course) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.course = course;
    }

   
    public Student(Student otherStudent) {
        this.studentId = otherStudent.studentId;
        this.studentName = otherStudent.studentName;
        this.course = otherStudent.course;
    }


    public void displayDetails() {
        System.out.println("Student ID   : " + studentId);
        System.out.println("Student Name : " + studentName);
        System.out.println("Course       : " + course);
        System.out.println();
    }
}