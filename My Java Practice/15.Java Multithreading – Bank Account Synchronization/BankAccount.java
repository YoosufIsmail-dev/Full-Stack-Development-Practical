public class BankAccount {

    // Account details (shared resource in multithreading context)
    private String accountHolder;
    private double balance;

    public BankAccount(String accountHolder, double initialBalance) {
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
    }

  
    public synchronized void deposit(double amount) {

        // Input validation to avoid invalid transactions
        if (amount <= 0 || Double.isNaN(amount) || Double.isInfinite(amount)) {
            throw new IllegalArgumentException("Invalid deposit amount");
        }

        // Update shared balance safely
        balance += amount;

        System.out.println(Thread.currentThread().getName()
                + " deposited: " + amount
                + " | Balance: " + balance);
    }

   
    public synchronized void withdraw(double withdrawalAmount) {

        // Validate withdrawal amount
        if (withdrawalAmount <= 0 || Double.isNaN(withdrawalAmount) || Double.isInfinite(withdrawalAmount)) {
            throw new IllegalArgumentException("Invalid withdrawal amount");
        }

        // Check sufficient balance
        if (withdrawalAmount > balance) {
            System.out.println(Thread.currentThread().getName()
                    + " attempted withdraw: " + withdrawalAmount
                    + " | FAILED (Balance: " + balance + ")");
        } else {
            balance -= withdrawalAmount;

            System.out.println(Thread.currentThread().getName()
                    + " withdrew: " + withdrawalAmount
                    + " | Balance: " + balance);
        }
    }

   
    public synchronized double getBalance() {
        return balance;
    }
}