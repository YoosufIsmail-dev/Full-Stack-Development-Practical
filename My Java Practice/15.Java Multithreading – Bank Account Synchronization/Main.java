public class Main {

   
    public static void main(String[] args) throws InterruptedException {

        // Shared resource (all threads operate on this single account)
        BankAccount sharedAccount = new BankAccount("Y.M Ismail", 2000.00);

        System.out.println("Initial Balance: " + sharedAccount.getBalance());
        System.out.println("----------------------------------------------");

        /**
         * Creating deposit threads
         * Each thread performs multiple deposit operations
         */
        Thread depositThread1 =
                new Thread(new DepositTask(sharedAccount, 100.00, 5), "Deposit-Thread-1");

        Thread depositThread2 =
                new Thread(new DepositTask(sharedAccount, 50.00, 5), "Deposit-Thread-2");

        /**
         * Creating withdrawal threads
         * Each thread performs multiple withdrawal operations
         */
        Thread withdrawThread1 =
                new Thread(new WithdrawTask(sharedAccount, 200.00, 5), "Withdraw-Thread-1");

        Thread withdrawThread2 =
                new Thread(new WithdrawTask(sharedAccount, 300.00, 5), "Withdraw-Thread-2");

        // Start all threads → enables concurrent execution
        depositThread1.start();
        depositThread2.start();
        withdrawThread1.start();
        withdrawThread2.start();

        // join() ensures main thread waits for all operations to complete
        depositThread1.join();
        depositThread2.join();
        withdrawThread1.join();
        withdrawThread2.join();

        System.out.println("----------------------------------------------");
        System.out.println("Final Balance: " + sharedAccount.getBalance());

        // Validation check after multithreaded execution
        if (sharedAccount.getBalance() >= 0) {
            System.out.println("Success: Balance remained safe.");
        } else {
            System.out.println("Error: Balance became negative.");
        }
    }
}