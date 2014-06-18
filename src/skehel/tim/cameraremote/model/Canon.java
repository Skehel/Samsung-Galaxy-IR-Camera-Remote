package skehel.tim.cameraremote.model;

import skehel.tim.cameraremote.IRcomm;

public class Canon implements Camera {
		
	@Override
	public void fireShutter() {
		IRcomm irc = IRcomm.getInstance();
		String irCode = "32897,16,241,16,241,";
		irc.sendIRCode(irCode);
	}

	@Override
	public void fireDelay() {
		IRcomm irc = IRcomm.getInstance();
		String irCode = "32897,16,177,16,177,";
		irc.sendIRCode(irCode);
	}

	@Override
	public void timeLapse(int time) throws InterruptedException {
		fireShutter();
		Thread.sleep(time);
	}

}
