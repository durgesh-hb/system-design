package design_patterns_3;

public class subs implements observer{
	
	String name;
	
	subs(String name){
		this.name = name;
	}
	
	@Override
	public void update(String vidoename) {
		System.out.println(name + " recived  notofication " + vidoename);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
