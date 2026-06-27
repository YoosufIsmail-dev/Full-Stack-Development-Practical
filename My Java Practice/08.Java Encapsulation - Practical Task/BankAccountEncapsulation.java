
public class BankAccountEncapsulation {

    public static void main(String[] args) {

       
        BankAccount account = new BankAccount();

        
        account.setAccountNumber("ACC10018905435678");
        account.setAccountHolderName("Fathima Risana");
        account.setBalance(0.0);

        System.out.println(" Initial Account Details ");
        account.displayAccountDetails();

       
        System.out.println("\n Depositing Rs. 5000...");
        account.deposit(5000);

        
        System.out.println("Withdrawing Rs. 2000...");
        account.withdraw(2000);

       
        System.out.println("\n Updated Account Details ");
        account.displayAccountDetails();
    }
}


class BankAccount {

   
    private String accountNumber;
    private String accountHolderName;
    private double balance;

   
    public BankAccount() {
        this.accountNumber = "Not Assigned";
        this.accountHolderName = "Not Assigned";
        this.balance = 0.0;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
       
        if (balance >= 0) {
            this.balance = balance;
        } else {
            System.out.println("Invalid balance value. Balance cannot be negative.");
        }
    }

  
       
    public void deposit(double amount) {
        if (amount > 0) {
            balance = balance + amount;
            System.out.println("Deposit successful. Amount deposited: Rs. " + amount);
        } else {
            System.out.println("Deposit failed. Deposit amount must be greater than zero.");
        }
    }

  
    
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal failed. Withdrawal amount must be greater than zero.");
        } else if (amount > balance) {
            System.out.println("Withdrawal failed. Insufficient balance.");
        } else {
            balance = balance - amount;
            System.out.println("Withdrawal successful. Amount withdrawn: Rs. " + amount);
        }
    }

    
    public void displayAccountDetails() {
        System.out.println("Account Number      : " + accountNumber);
        System.out.println("Account Holder Name : " + accountHolderName);
        System.out.println("Balance             : Rs. " + balance);
    }
}
