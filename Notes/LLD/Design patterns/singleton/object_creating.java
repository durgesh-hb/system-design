package design_patterns;

public class loggerSingleton {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		singletondemo singletonobj = singletondemo.getinstance();
		
		System.out.println(singletonobj);
		
		singletondemo singletonobj2 = singletondemo.getinstance();
		
		System.out.println(singletonobj2);
		
		System.out.println(singletonobj == singletonobj2);
		
	}

}
