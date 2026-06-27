
public class LibraryManagementSystem {

    public static void main(String[] args) {

        System.out.println("   LIBRARY MANAGEMENT SYSTEM \n");

       
        Book book1 = new Book();
        book1.setBookId("B001");
        book1.setTitle("Clean Code");
        book1.setAuthor("Rasin Mohamed");
        book1.setPrice(4500.0);

        Book book2 = new Book("B002", "Effective Java", "Mohamed Ramees", 3200.0);

        System.out.println(" Encapsulation: Book Details (Via getters) ");
        System.out.println("Book 1 -> ID: " + book1.getBookId()
                + ", Title: " + book1.getTitle()
                + ", Author: " + book1.getAuthor()
                + ", Price: Rs. " + book1.getPrice());
        System.out.println("Book 2 -> ID: " + book2.getBookId()
                + ", Title: " + book2.getTitle()
                + ", Author: " + book2.getAuthor()
                + ", Price: Rs. " + book2.getPrice());


        Student student = new Student("Inthifa Hassan", "P100", "ST2001", "Computer Science");
        Librarian librarian = new Librarian("Mohamed Rilwan", "P200", "LB3001");

        System.out.println("\n Inheritance: Person -> Student / Librarian ");
        student.displayPersonInfo();  
        System.out.println("Department    : " + student.getDepartment());

        librarian.displayPersonInfo(); 
        System.out.println("Staff ID      : " + librarian.getStaffId());

       
        System.out.println("\n Abstraction & Polymorphism: LibraryItem -> Book ");
        LibraryItem item = new Book("B003", "The Pragmatic Programmer", "Krish", 2800.0);
        item.displayInfo(); 

        System.out.println("\n Interface: Borrowable ");
        System.out.println("Student borrowing a book:");
        student.borrowItem();

        System.out.println("\nBook being borrowed (book's own perspective):");
        book2.borrowItem();

        System.out.println("\nReturning items:");
        student.returnItem();
        book2.returnItem();
    }
}

abstract class LibraryItem {

 
    public abstract void displayInfo();
}


interface Borrowable {
    void borrowItem();

    void returnItem();
}


class Book extends LibraryItem implements Borrowable {

   
    private String bookId;
    private String title;
    private String author;
    private double price;

   
    public Book() {
        this.bookId = "Not Assigned";
        this.title = "Not Assigned";
        this.author = "Not Assigned";
        this.price = 0.0;
    }

   
    public Book(String bookId, String title, String author, double price) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
        } else {
            System.out.println("Invalid price. Price cannot be negative.");
        }
    }

    
    @Override
    public void displayInfo() {
        System.out.println("Book Details:");
        System.out.println("  Book ID : " + bookId);
        System.out.println("  Title   : " + title);
        System.out.println("  Author  : " + author);
        System.out.println("  Price   : Rs. " + price);
    }

    
    public void borrowItem() {
        System.out.println("The book \"" + title + "\" has been marked as borrowed.");
    }

    
    public void returnItem() {
        System.out.println("The book \"" + title + "\" has been returned to the library.");
    }
}


class Person {

    protected String name;
    protected String personId;

    public Person(String name, String personId) {
        this.name = name;
        this.personId = personId;
    }

    public void displayPersonInfo() {
        System.out.println("Name          : " + name);
        System.out.println("Person ID     : " + personId);
    }
}


class Student extends Person implements Borrowable {

    private String studentId;
    private String department;

    public Student(String name, String personId, String studentId, String department) {
        super(name, personId); 
        this.studentId = studentId;
        this.department = department;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getDepartment() {
        return department;
    }

    
    public void borrowItem() {
        System.out.println(name + " (Student ID: " + studentId + ") has borrowed a library item.");
    }

    
    public void returnItem() {
        System.out.println(name + " (Student ID: " + studentId + ") has returned the library item.");
    }
}


class Librarian extends Person {

    private String staffId;

    public Librarian(String name, String personId, String staffId) {
        super(name, personId); 
        this.staffId = staffId;
    }

    public String getStaffId() {
        return staffId;
    }
}
