package design_patterns;

public class singletondemo {

	// 1. private constructor
	private singletondemo() {	
	}
	// 2. store static object
	// and this is Eager initialization
	private static singletondemo instance = new singletondemo();
	
	// 3. public access
	public static singletondemo getinstance() {
		return instance;
	}
	
}
