public class WithdrawTask implements Runnable {

    // Shared BankAccount object (critical shared resource)
    private BankAccount account;

    private double withdrawalAmount;
    private int numberOfTransactions;

    /**
     * Constructor initializes shared account and withdrawal settings
     */
    public WithdrawTask(BankAccount account, double withdrawalAmount, int numberOfTransactions) {
        this.account = account;
        this.withdrawalAmount = withdrawalAmount;
        this.numberOfTransactions = numberOfTransactions;
    }

    
    @Override
    public void run() {

        for (int i = 0; i < numberOfTransactions; i++) {

            // Each loop iteration represents one withdrawal transaction
            account.withdraw(withdrawalAmount);
        }
    }
}