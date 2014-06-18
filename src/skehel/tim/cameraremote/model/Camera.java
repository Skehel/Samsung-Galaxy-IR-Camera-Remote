package skehel.tim.cameraremote.model;

public interface Camera {
	public void fireShutter();
	public void fireDelay();	
	public void timeLapse(int time) throws InterruptedException;
}
