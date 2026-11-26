package LLD;

public class Solid_D2 {
	
	interface paymentservice{
		void pay(double amount);
	}
	
	static class rozarpay implements paymentservice{
		
		@Override
		public void pay(double mount) {
			System.out.println("Paying through Rozar pay");
		}
	}
	
	static class paypal implements paymentservice{
		
		@Override
		public void pay(double amount) {
			System.out.println("Paying through paypal");
		}
	}
	
	static class stripeservice implements paymentservice {
		
	    @Override
	    public void pay(double amount) {
	        System.out.println("Payment of " + amount
	                + " processed through Stripe");
	    }
	}
	
	static class payment{
		
		private paymentservice payservice;
		
		public payment(paymentservice payservice) {
			this.payservice = payservice;
		}
		
		public void makepayment(double amount) {
			payservice.pay(amount);
		}
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		paymentservice rozarpay = new rozarpay();
		payment payment = new payment(rozarpay);
		payment.makepayment(5000);
		
		
		paymentservice stripe = new stripeservice();
		payment payment2 = new payment(stripe);
		payment2.makepayment(5000);
	}

}
