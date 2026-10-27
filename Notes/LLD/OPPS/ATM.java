package LLD;

public class ATM {
	
	private double balance;
	private int pin;
	
	public ATM(double initialbalance, int pin) {
		this.balance = initialbalance;
		this.pin = pin;
	}
	
	public void getbalance(int pin) {
		if(this.pin == pin) {
			System.out.println("balance is - "+balance);
		}else {
			System.out.println("Enter the correct pin to get balance");
		}
	}
	
	public void withdraw(int amount, int enteredpin) {
		if(this.pin == enteredpin) {
			if(amount <= balance) {
				balance -= amount;
			}else {
				System.out.println("There is not enough balance for your entered amonut to process");
			}
		}else {
			System.out.println("Enter the correct pin");
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ATM myatm = new ATM(5000, 1234);
		myatm.getbalance(1234); // correct pin
		myatm.getbalance(1233); // in-correct pin
		myatm.withdraw(5000,1233);// correct amount but invalid pin
		myatm.withdraw(10000, 1234); // more then balance
		myatm.withdraw(1000,1234); // correct
		myatm.getbalance(1234); // check balance after withdraw
	}

}
