public class bankaccount {

    protected double balance;

    public bankaccount(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Amount deposited = " + amount);
        System.out.println("Current balance = " + balance);
    }

    public void withdraw(double amount) {

        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Amount withdrawn = " + amount);
            System.out.println("Remaining balance = " + balance);
        } else {
            System.out.println("Insufficient balance or invalid amount");
        }
    }
}

	class SavingsAccount extends bankaccount {
	
	    public SavingsAccount(double balance) {
	        super(balance);
	    }
	}

	class FixedDepositAccount extends bankaccount {
	
	    public FixedDepositAccount(double balance) {
	        super(balance);
	    }

    @Override
    public void withdraw(double amount) {
        throw new UnsupportedOperationException(
            "Withdrawal not allowed before maturity"
        );
    }
    
    public static void main(String[] args) {

        bankaccount savings = new SavingsAccount(10000);
        bankaccount fixedDeposit = new FixedDepositAccount(20000);

        System.out.println("=== Savings Account ===");
        savings.withdraw(1000);

        System.out.println("=== Fixed Deposit Account ===");
        fixedDeposit.withdraw(1000);
    }
}