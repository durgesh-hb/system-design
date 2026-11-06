package system-design.Notes.LLD.SOLID_Principles.Solid_I;


public class Solid_I2 {
	
	interface powercontrol{
		
		void trunon();
		void turnoff();
	}
	
	interface musicplayer{
		void playmusic();
		
	}
	
	interface imagecapture{
		void capture();
	}
	
	static class light implements powercontrol{
		
		public void trunon() {
			System.out.println("Truning on the light's");
		}
		
		public void turnoff() {
			System.out.println("Turning off the litgh's");
		}
	}
	
	static class camera implements imagecapture, powercontrol{
		
		public void trunon() {
			System.out.println("Truning on the camera");
		}
		
		public void turnoff() {
			System.out.println("Turning off the camera");
		}
		
		public void capture() {
			System.out.println("Capturing images");
		}
	}
	
	static class music implements musicplayer, powercontrol{
		
		public void trunon() {
			System.out.println("Truning on the speaker");
		}
		
		public void turnoff() {
			System.out.println("Turning off the speaker");
		}
		
		public void playmusic() {
			System.out.println("Playing music");
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		light lgt =  new light();
		lgt.trunon();
		
		camera cam = new camera();
		cam.capture();
	}

}
