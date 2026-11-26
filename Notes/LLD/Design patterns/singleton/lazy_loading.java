package design_patterns;

public class singletondemo {

	
	private singletondemo() {	
	}
	
	// and this is Lazy loading
	private static singletondemo instance = null;
	
	public static singletondemo getinstance() {
		
		if(instance == null) {
			instance = new singletondemo();
		}
		return instance;
	}
	
}
