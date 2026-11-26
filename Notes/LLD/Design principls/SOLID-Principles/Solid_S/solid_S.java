public class Solid_S {
	
		// A class should have only one responsibility and one reason to change.
	
	public static class reciptprinter{
		
		public void printrecipt(String customername, double amount) {
			System.out.println("====RECIPT====");
			System.out.println("customer - " + customername);
			System.out.println("total amount - " + amount);
		}
	}
	
	public static class sendnotification{
		
		public void sendmsg(String customername, double amount) {
			System.out.println("Sending message for customer " + customername + " with bill amount " + amount);
		}
	}
	
	public static class checkoutmanager{
		
		reciptprinter recipt = new reciptprinter();
		sendnotification msg = new sendnotification();
		
		public void processcheckout(String customername, double discount, double billamount) {
			
			double topay = billamount - discount;
			
			recipt.printrecipt(customername, topay);
			System.out.println("processing payment for " + customername);
			msg.sendmsg(customername, topay);
		}	
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		checkoutmanager checkout = new checkoutmanager();
		checkout.processcheckout("Eren", 50, 1000);
		
	}

}
