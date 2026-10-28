
//here we practiced abstraction and interface
public class test {
	
	// 1. FULL ABSTRACTION
	interface securityathenticater{
		// only the contract method
		boolean login(int id, int pin);
		
	}
	
	public static class athenticater implements securityathenticater{

		@Override // implemented here of that contract method
		public boolean login(int id, int pin) {
			System.out.println("Security checking for user = " + id);
			
			if(pin == 1234) {
				System.out.println("Athentication successfull");
				return true;
			}else {	
				System.out.println("Entered pin is incorrect");
			}
			
			return false;
		}
		
	}
	
	// 2.  PARTIAL ABSTRACTION (Abstract Class)
	
	public static abstract class smartatm{
		
		String id;
		int balance;
		
		public smartatm(String id, int amount) {
			this.id = id;
			this.balance = amount;
		}
		
		public void startmachine() { // Concrete
			System.out.println("Booting up the hardware for the atm = " + id);
		}
		
		public abstract void dispense(String id, int amount); // contract
		
	}
	
	 //  3. CONCRETE CLASS EXTENDING THE TEMPLATE
    public static class premiumatm extends smartatm {

		public premiumatm(String id, int amount) {
			super(id, amount);
		}

		@Override
		public void dispense(String id, int amount) {
			// Unique behavior for this specific machine type
            if (amount <= balance) {
                balance -= amount;
                System.out.println("[Dispenser] Premium Machine sliding mechanical tray. Out flow: $" + amount);
            } else {
                System.out.println("[Dispenser] Machine vault out of cash!");
            }
		}
    	
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		securityathenticater athu = new athenticater();
		athu.login(102, 12340);
		athu.login(101, 1234);
	}

}
