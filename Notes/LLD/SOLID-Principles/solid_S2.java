package LLD;

public class single {
	// single responsibility here all the class have the one responsibility
	private String riderid;
	private String customername;
	private double distance;
	private double fare;
	
	public static class calculatefare {
		
		public static double calculateprice(double distance) {
		double fare = distance * 10;
		System.out.println("Your fare is " + fare);
		return fare;
		}
	}
		
	public static class assignrider{
		
		public static void rider(String riderid) {
			System.out.println("Assigning rider for you");
			System.out.println("Your assinged rider is " + riderid);
		}
	}
	
	public static class processpayment{
		
		public static void payment(double fare) {
			System.out.println("Complete the payments amount of " + fare);
		}
	}
	
	public static class ridereport{
		
		public static void generatereport(String customer, String riderid, double fare, double distance) {
			System.out.println("===REPORT===");
			System.out.println("Customer " + customer);
			System.out.println("Distance " + distance);
			System.out.println("Assigned rider " + riderid);
			System.out.println("Total fare " + fare);
		}
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String customername = "eren";
		String ridername = "Mikasa";
		double distance = 8;
		
		checkout.performcheckout(customername, ridername, distance);
		
	}

}
