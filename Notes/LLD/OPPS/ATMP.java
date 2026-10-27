
//here we are learning the polymorphsim runtime
class ATM {
	
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
				System.out.println("withdrwa succesfull");
			}else {
				System.out.println("There is not enough balance for your entered amonut to process");
			}
		}else {
			System.out.println("Enter the correct pin");
		}
	}
}

	// here use the inheritance + method overriding + code reuse
	class InternationalATM extends ATM {
	
	    public InternationalATM(double initialbalance, int pin) {
	        super(initialbalance, pin);
	    }
	    
	    @Override
	    public void withdraw(int amount, int enteredpin) {
	        System.out.println("[International Context] Applying extra security layer...");
	        super.withdraw(amount, enteredpin); // Calling parent logic
	    }
	
	}
	
	
	public class ATMP {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		InternationalATM myatm = new InternationalATM(5000, 1234);
		myatm.getbalance(1234);
		myatm.withdraw(1000, 1234);
		myatm.getbalance(1234);


	}

}
