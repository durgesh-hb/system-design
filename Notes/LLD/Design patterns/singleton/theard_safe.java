package design_patterns;

public class singletondemo {

	
	private singletondemo() {	
	}
	
	private static volatile singletondemo instance = null;	
	
	public static singletondemo getinstance() {
		
		if(instance == null) {
			
			synchronized (singletondemo.class) {
				if(instance == null) {
					instance = new singletondemo();
				}
			}
		}
		return instance;
	}
	
}
