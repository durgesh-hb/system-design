package design_patterns_2;

public class troopsfactory {
	
	public static troops crttroopsfact(String type) {
		
		if(type.equalsIgnoreCase("barbarian")){
			return new barbarian();
		}else if(type.equalsIgnoreCase("wizard")) {
			return new wizard();
		}else if(type.equalsIgnoreCase("archer")) {
			return new archer();
		}else {
			throw new IllegalArgumentException("Unknow type troop");
		}
	}

}
