package LLD;

// Parent class
public class Solid_L {

    protected double balance;

    public Solid_L(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {

        if (amount > 0) {
            balance += amount;
            System.out.println("Amount deposited = " + amount);
            System.out.println("Current balance = " + balance);
        }
    }
}

// Interface for accounts that support withdrawal
interface Withdrawable {

    void withdraw(double amount);
}

// Savings Account
class SavingsAccount extends Solid_L implements Withdrawable {

    public SavingsAccount(double balance) {
        super(balance);
    }

    @Override
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

// Fixed Deposit Account
class FixedDepositAccount extends Solid_L {

    public FixedDepositAccount(double balance) {
        super(balance);
    }
}

class Main {

    public static void main(String[] args) {

    	Solid_L savings = new SavingsAccount(10000);
    	Solid_L fixedDeposit = new FixedDepositAccount(20000);

        System.out.println("=== Savings Account ===");
        savings.deposit(2000);

        Withdrawable withdrawableAccount = (SavingsAccount) savings;
        withdrawableAccount.withdraw(1000);

        System.out.println("\n=== Fixed Deposit Account ===");
        fixedDeposit.deposit(3000);

    }
}