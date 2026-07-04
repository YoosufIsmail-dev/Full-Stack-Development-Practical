public class DepositTask implements Runnable {

    // Shared BankAccount object (critical shared resource in multithreading)
    private BankAccount account;

    private double depositAmount;
    private int numberOfTransactions;

    /**
     * Constructor initializes shared account and deposit settings
     */
    public DepositTask(BankAccount account, double depositAmount, int numberOfTransactions) {
        this.account = account;
        this.depositAmount = depositAmount;
        this.numberOfTransactions = numberOfTransactions;
    }

  
    @Override
    public void run() {

        for (int i = 0; i < numberOfTransactions; i++) {

            // Each iteration represents one deposit transaction
            account.deposit(depositAmount);
        }
    }
}