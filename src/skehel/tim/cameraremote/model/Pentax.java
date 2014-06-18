package skehel.tim.cameraremote.model;

import skehel.tim.cameraremote.IRcomm;

public class Pentax implements Camera {

	@Override
	public void fireShutter() {
		IRcomm irc = IRcomm.getInstance();
		String irCode = "37683,494,114,38,38,38,38,38,38,38,38,38,38,38,38,38,38,";
		irc.sendIRCode(irCode);
	}

	@Override
	public void fireDelay() {
		IRcomm irc = IRcomm.getInstance();
		String irCode = "37683,494,114,38,38,38,38,38,38,38,38,38,38,38,114,38,38,";
		irc.sendIRCode(irCode);
	}

	@Override
	public void timeLapse(int time) throws InterruptedException {
		fireShutter();
		Thread.sleep(time);
	}
}
