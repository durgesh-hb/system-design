package system-design.Notes.LLD.SOLID_Principles.Solid_I;

public class Solid_I {
	
	interface smarthome{
		
		void turnon();
		void turnoff();
		void captureimage();
		void playmusic();
		
	}
	
	static class smartlight implements smarthome{
		
		public void turnon() {
			System.out.println("Trun on the lights");
		}
		
		public void turnoff() {
			System.out.println("Trun off the lights");
		}
		
		public void playmusic() {
			throw new UnsupportedOperationException();
			// not needed for smart light but forced to implement
		}
		
		public void captureimage() {
			throw new UnsupportedOperationException();
			// not needed
		}
	}
	
	static class speaker implements smarthome {
		
		public void turnon() {
			System.out.println("Trun on the speaker");
		}
		
		public void turnoff() {
			System.out.println("Trun off the speaker");
		}
		
		public void playmusic() {
			System.out.println("Playing music");
		}
		
		public void captureimage() {
			throw new UnsupportedOperationException();
			// not needed
		}
	}
	
	static class camera implements smarthome{
		
		public void turnon() {
			System.out.println("Trun on the camera");
		}
		
		public void turnoff() {
			System.out.println("Trun off the camera");
		}
		
		public void playmusic() {
			throw new UnsupportedOperationException();
			// not needed
		}
		
		public void captureimage() {
			System.out.println("Capturing image");
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		smartlight light = new smartlight();
		light.turnon();
		
	}

}
