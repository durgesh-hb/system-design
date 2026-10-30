public class Solid_O {
	
	public interface DiscountStrategy{
		
		double getdiscount(double amount);
		
	}
	
	public static class regulardiscount implements DiscountStrategy{
		
		@Override
		public double getdiscount(double amount) {
			double discount = amount * 0.05;
			System.out.println("You are regular customer so discount is 10%");
			return discount;
		}
		
	}
	
	public static class vipdiscount implements DiscountStrategy{
		
		@Override
		public double getdiscount(double amount) {
			double discount = amount * 0.1;
			System.out.println("your are vip customer so your discount is ");
			return discount;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		double billamount = 1000;
		
		// testing regular customer
		DiscountStrategy regular = new regulardiscount();
		double finalamount = billamount - regular.getdiscount(billamount);
		System.out.println("Final amount to be payed after discount = " + finalamount);
		
		// testing vip customer
		DiscountStrategy vip = new vipdiscount();
		double finalamountforvip = billamount - vip.getdiscount(billamount);
		System.out.println("Final amount to be payed after discount = " + finalamountforvip);
		

	}

}
