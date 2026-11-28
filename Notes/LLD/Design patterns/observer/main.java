package design_patterns_3;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		channel channel = new channel();
		
		observer user1 = new subs("Eren");
		observer user2 = new subs("mikasa");
		
		channel.add(user1);
		channel.add(user2);
		
		channel.uplodevideo("system design video");
		
		channel.remove(user2);
		
		channel.uplodevideo("Sprng boot");
		
		
	}

}
