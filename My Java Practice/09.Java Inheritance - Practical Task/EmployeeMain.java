public class EmployeeMain {
    public static void main(String[] args) {

        System.out.println(" EMPLOYEE MANAGEMENT SYSTEM \n");

        System.out.println("1. Creating Full-Time Employee:");
        FullTimeEmployee fullTime = new FullTimeEmployee("Mohamed Kiyas", 1001, 5000.00);
        fullTime.displaySalaryInfo();
        System.out.println();

        System.out.println("2. Creating Part-Time Employee:");
        PartTimeEmployee partTime = new PartTimeEmployee("Puvan Krish", 1002, 25.5, 20.50);
        partTime.displaySalaryInfo();
        System.out.println();

      

        System.out.println("3. Calculating Salaries:");
        System.out.println("Full-Time Employee:");
        System.out.println("Name: " + fullTime.getName());
        System.out.println("Monthly Salary: $" + String.format("%.2f", fullTime.calculateSalary()));
        System.out.println("Annual Salary: $" + String.format("%.2f", fullTime.calculateSalary() * 12));
        System.out.println();

        System.out.println("Part-Time Employee:");
        System.out.println("Name: " + partTime.getName());
        System.out.println("Hours Worked: " + partTime.getHoursWorked());
        System.out.println("Hourly Rate: $" + String.format("%.2f", partTime.getHourlyRate()));
        System.out.println("Total Salary: $" + String.format("%.2f", partTime.calculateSalary()));
        System.out.println();

        System.out.println("4. Employee Summary:");
       

        Employee[] employees = {
            new FullTimeEmployee("Mohamed Kiyas", 1004, 5500.00),
            new PartTimeEmployee("Puvan Krish", 1005, 30.0, 18.75)
        };

        for (Employee emp : employees) {
            if (emp instanceof FullTimeEmployee) {
                FullTimeEmployee fte = (FullTimeEmployee) emp;
                System.out.println("• " + fte.getName() + " (Full-Time)");
                System.out.println("  Salary: $" + String.format("%.2f", fte.calculateSalary()) + "/month");
            } else if (emp instanceof PartTimeEmployee) {
                PartTimeEmployee pte = (PartTimeEmployee) emp;
                System.out.println("• " + pte.getName() + " (Part-Time)");
                System.out.println("  Salary: $" + String.format("%.2f", pte.calculateSalary()));
            }
            System.out.println();
        }

        System.out.println(" INHERITANCE DEMONSTRATION COMPLETE ");
    }
}


class Employee {
    private String name;
    private int id;

    public Employee(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void displayInfo() {
        System.out.println("Employee ID: " + id);
        System.out.println("Name: " + name);
    }

    public double calculateSalary() {
        return 0;
    }
}


class FullTimeEmployee extends Employee {

    private double monthlySalary;

    public FullTimeEmployee(String name, int id, double monthlySalary) {
        super(name, id);
        this.monthlySalary = monthlySalary;
    }

   
    public double calculateSalary() {
        return monthlySalary;
    }

    public void displaySalaryInfo() {
        displayInfo();
        System.out.println("Type: Full-Time");
        System.out.println("Monthly Salary: $" + monthlySalary);
    }
}


class PartTimeEmployee extends Employee {

    private double hoursWorked;
    private double hourlyRate;

    public PartTimeEmployee(String name, int id, double hoursWorked, double hourlyRate) {
        super(name, id);
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }


    public double calculateSalary() {
        return hoursWorked * hourlyRate;
    }

    public void displaySalaryInfo() {
        displayInfo();
        System.out.println("Type: Part-Time");
        System.out.println("Hours Worked: " + hoursWorked);
        System.out.println("Hourly Rate: $" + hourlyRate);
    }
}