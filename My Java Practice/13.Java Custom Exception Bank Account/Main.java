import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Creating BankAccount object
        BankAccount account = new BankAccount("AC12345", "Kasun Perera", 5000.0);

        System.out.println("=== Bank Account Withdrawal System ===");

        System.out.println("Account Number: " + account.getAccountNumber());
        System.out.println("Account Holder: " + account.getAccountHolderName());
        System.out.println("Current Balance: Rs. " + account.getBalance());

        System.out.print("Enter withdrawal amount: ");
        double withdrawalAmount = scanner.nextDouble();

        // Exception handling for withdrawal
        try {
            account.withdraw(withdrawalAmount);
        } catch (InsufficientBalanceException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error occurred.");
        }

        System.out.println("Final Balance: Rs. " + account.getBalance());

        scanner.close();
    }
}
