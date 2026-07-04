// BankAccount class represents a simple bank account system
public class BankAccount {

    private String accountNumber;
    private String accountHolderName;
    private double balance;

    // Constructor to initialize account details
    public BankAccount(String accountNumber, String accountHolderName, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
    }

    // Method to withdraw money from account
    // Throws custom exception if balance is insufficient
    public void withdraw(double withdrawalAmount) throws InsufficientBalanceException {

        if (withdrawalAmount > balance) {
            throw new InsufficientBalanceException(
                "Insufficient balance! Available balance is Rs. " + balance
            );
        }

        balance -= withdrawalAmount;

        System.out.println("Withdrawal successful.");
        System.out.println("Remaining balance: Rs. " + balance);
    }

    // Getter methods (Encapsulation)
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }
}