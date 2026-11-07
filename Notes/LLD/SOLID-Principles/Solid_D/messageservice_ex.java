package LLD;

public class Solid_D {
	
	// any service that wan to send message must implements this interface
	interface messageservice{
		void sendmessage();
	}
	
	static class emailservice implements messageservice{
		
		@Override
		public void sendmessage(){
			System.out.println("Sending Email");
		}
	}
	
	static class smsservice implements messageservice{
		
		@Override
		public void sendmessage() {
			System.out.println("Sending SMS");
		}
	}
	
	static class notification{
		
		private messageservice service;
		// constructor
		public notification(messageservice service) {
			this.service = service;
		}
		
		public void sendnotification() {
			service.sendmessage();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		messageservice email = new emailservice();
		notification notification = new notification(email);
		notification.sendnotification();
		
		messageservice smsservice = new smsservice();
		notification notification2 = new notification(smsservice);
		notification2.sendnotification();
		
		
	}

}
