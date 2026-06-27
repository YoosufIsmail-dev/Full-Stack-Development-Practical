public class VehicleManagementSystem {

    public static void main(String[] args) {

        System.out.println("    Vehicle Management System \n");

        Car car = new Car("BMW I7", 450.0, 30.0);
        System.out.println(" Car Details ");
        car.start();
        car.stop();
        car.calculateMileage();

        System.out.println();

        Bike bike = new Bike("Unicon 160", 300.0, 6.0);
        System.out.println(" Bike Datails ");
        bike.start();
        bike.stop();
        bike.calculateMileage();

        System.out.println("\n End of Program");
    }
}


abstract class Vehicle {

    protected String vehicleName;

    public Vehicle(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public abstract void start();
    public abstract void stop();
}


interface FuelEfficiency {
    void calculateMileage();
}



class Car extends Vehicle implements FuelEfficiency {

    private double distanceTravelledKm;
    private double fuelUsedLitres;

    public Car(String vehicleName, double distanceTravelledKm, double fuelUsedLitres) {
        super(vehicleName);
        this.distanceTravelledKm = distanceTravelledKm;
        this.fuelUsedLitres = fuelUsedLitres;
    }

    public void start() {
        System.out.println(vehicleName + " (Car): Engine started with key/start button.");
    }

    public void stop() {
        System.out.println(vehicleName + " (Car): Engine stopped. Handbrake engaged.");
    }

    public void calculateMileage() {
        double mileage = distanceTravelledKm / fuelUsedLitres;
        System.out.printf("%s (Car): Mileage = %.2f km/l%n", vehicleName, mileage);
    }
}

class Bike extends Vehicle implements FuelEfficiency {

    private double distanceTravelledKm;
    private double fuelUsedLitres;

    public Bike(String vehicleName, double distanceTravelledKm, double fuelUsedLitres) {
        super(vehicleName);
        this.distanceTravelledKm = distanceTravelledKm;
        this.fuelUsedLitres = fuelUsedLitres;
    }

    public void start() {
        System.out.println(vehicleName + " (Bike): Engine started with kick/self-start.");
    }

    public void stop() {
        System.out.println(vehicleName + " (Bike): Engine stopped. Side stand down.");
    }

    public void calculateMileage() {
        double mileage = distanceTravelledKm / fuelUsedLitres;
        System.out.printf("%s (Bike): Mileage = %.2f km/l%n", vehicleName, mileage);
    }
}