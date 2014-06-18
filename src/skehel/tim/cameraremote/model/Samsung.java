package skehel.tim.cameraremote.model;

import skehel.tim.cameraremote.IRcomm;

public class Samsung implements Camera {
	@Override
	public void fireShutter() {
		IRcomm irc = IRcomm.getInstance();
		String irCode = "38028,169,168,21,63,21,63,21,63,21,21,21,21,21,21,21,21,21,21,21,63,21,63,21,63,21,21,21,21,21,21,21,21,21,21,21,21,21,63,21,21,21,21,21,21,21,21,21,21,21,21,21,64,21,21,21,63,21,63,21,63,21,63,21,63,21,63,21,1794,169,168,21,21,21,3694,";
		irc.sendIRCode(irCode);
	}

	@Override
	public void fireDelay() {
		IRcomm irc = IRcomm.getInstance();
		String irCode = "38028,169,168,21,63,21,63,21,63,21,21,21,21,21,21,21,21,21,21,21,63,21,63,21,63,21,21,21,21,21,21,21,21,21,21,21,21,21,63,21,21,21,21,21,63,21,21,21,21,21,21,21,63,21,21,21,63,21,63,21,21,21,64,21,63,21,63,21,1794,169,168,21,21,21,3694,";
		irc.sendIRCode(irCode);
	}

	@Override
	public void timeLapse(int time) throws InterruptedException {
		fireShutter();
		Thread.sleep(time);
	}
}
