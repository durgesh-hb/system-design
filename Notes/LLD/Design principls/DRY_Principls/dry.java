public class DRY {
	
	
//	double discount1 = price1 * 0.10;
//	double discount2 = price2 * 0.10;
//	double discount3 = price3 * 0.10;
	
	// instead of using this above code again and again we created one reusable method 'discount'
	
	// DRY -> Do not repeat yourself
	
	static class discount{
		
		public double calculate(double amount) {
			return amount * 0.15;
		}
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		discount discount = new discount();
		double ans = discount.calculate(1000);
		System.out.println(ans);
		
	}

}
