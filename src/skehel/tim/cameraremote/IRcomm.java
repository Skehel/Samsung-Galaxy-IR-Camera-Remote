package skehel.tim.cameraremote;

import java.lang.reflect.Method;


public class IRcomm {

		private Object _irService;
		private Class _irClass;
		private Method _sendIR;
		private static IRcomm _instance = null;
		
		protected IRcomm() {
			try{
				//get the method from the ir class
				_irService = MainActivity.getContext().getSystemService("irda");
		    	_irService.getClass();
		    	_irClass = _irService.getClass();
				_sendIR = _irClass.getMethod("write_irsend", new Class[]{String.class});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public static IRcomm getInstance() {
			if (_instance == null){ //check if the class has been initiated 
				_instance = new IRcomm(); //if it hasnt initiate it
			}
			return _instance; //return the instance of the class
		}
		
		public void sendIRCode(String irCode) {
			try {
				_sendIR.invoke(_irService, irCode); //send IR signal
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
