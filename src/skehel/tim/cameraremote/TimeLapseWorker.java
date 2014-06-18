package skehel.tim.cameraremote;

import java.util.ArrayList;

import skehel.tim.cameraremote.R;
import skehel.tim.cameraremote.model.Camera;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageButton;


public class TimeLapseWorker extends AsyncTask<Void, Void, Void> {
	
	private ImageButton _button;
	private Context _context;
	private Camera _camera;
	private int _time;
	private Thread _background;
	
	public TimeLapseWorker(ImageButton button, Context context, Camera camera, int time) {
		_button = button;
		_context = context;
		_camera = camera;
		_time = time;
	}
	
	@Override
	protected Void doInBackground(Void... args0) { //executes in background
		//get current thread
		_background = Thread.currentThread();
		
		boolean stop = false;
		while (stop==false) { //while user wants it to run
			try {
				_camera.timeLapse(_time); //run
			} catch (InterruptedException e) {
				stop = true;
			}
		}
		
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) { //executes when doInBackground is finished
		//reset the button to normal state 
		Drawable selector = _context.getResources().getDrawable(R.drawable.button_selector);
        _button.setImageDrawable(selector);
    }
	
	public void stop() {
		_background.interrupt();
	}
}
