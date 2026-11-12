public class KISS {
    static boolean age(int age) {
		return age>=18;
	}
	
	static boolean agecheck(int age) {
		if(age >= 18) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		KISS age = new KISS();
		boolean ans = age.agecheck(3);
		System.out.println(ans);
		
	}

}
