package skehel.tim.cameraremote;

import java.util.ArrayList;
import java.util.Locale;

import skehel.tim.cameraremote.model.Camera;
import skehel.tim.cameraremote.model.Canon;
import skehel.tim.cameraremote.model.Pentax;
import skehel.tim.cameraremote.model.Samsung;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends Activity implements ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	private static Context _context;
	private boolean _timeLapseRunning;
	private TimeLapseWorker _timeLapseWorker;
	private Camera _camera;
	private SharedPreferences _preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		_context = this;
		//set up and load preferences
		_preferences = this.getPreferences(MODE_PRIVATE);
		int savedCamera = _preferences.getInt("camera", R.id.radioCanon);
		setCamera(savedCamera);
		
		
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			switch (position) {
			case 0:
				return FragShutter.newInstance(position + 1);
			case 1:
				return FragTimeLapse.newInstance(position + 1);
			case 2:
				return FragOptions.newInstance(position + 1);
			default:
				return FragShutter.newInstance(position + 1);
			}

			/*
			 * if (position == 0) { return FragShutter.newInstance(position +
			 * 1); } else { return FragTimeLapse.newInstance(position + 1); } //
			 * choose which it returns here
			 */
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class FragShutter extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static FragShutter newInstance(int sectionNumber) {
			FragShutter fragment = new FragShutter();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public FragShutter() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_shutter,
					container, false);
			return rootView;
		}
	}

	public static class FragTimeLapse extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static FragTimeLapse newInstance(int sectionNumber) {
			FragTimeLapse fragment = new FragTimeLapse();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public FragTimeLapse() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_timelapse,
					container, false);
			return rootView;
		}
	}

	public static class FragOptions extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static FragOptions newInstance(int sectionNumber) {
			FragOptions fragment = new FragOptions();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public FragOptions() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_options,
					container, false);
			return rootView;
		}
	}


	public static Context getContext() {
		return _context;
	}

	public void plusOrMinus(View view) {
		//get the button pushed ID
		int id = view.getId();
		
		switch (id) { //do the appropriate action for whichever button
		case R.id.plusSeconds:
			TextView seconds = (TextView) findViewById(R.id.txtSeconds);
			plus(seconds);
			break;
		case R.id.plusMinutes:
			TextView minutes = (TextView) findViewById(R.id.txtMinutes);
			plus(minutes);
			break;
		case R.id.plusHours:
			TextView hours = (TextView) findViewById(R.id.txtHours);
			plus(hours);
			break;
		case R.id.takeSeconds:
			TextView secondsT = (TextView) findViewById(R.id.txtSeconds);
			take(secondsT);
			break;
		case R.id.takeMinutes:
			TextView minutesT = (TextView) findViewById(R.id.txtMinutes);
			take(minutesT);
			break;
		case R.id.takeHours:
			TextView hoursT = (TextView) findViewById(R.id.txtHours);
			take(hoursT);
			break;
		}
	}

	private void plus(TextView text) {
		//get the current value
		String time = text.getText().toString();
		int i = Integer.parseInt(time);
		i++;
		if (i < 60) { //if its less than 60 
			if (i < 10) { //check if its less than 10
				time = "0" + Integer.toString(i); //if so prefix a 0
			} else {
				time = Integer.toString(i); 
			}
		} else {
			time = "00"; //if its more than 60 reset to 00
		}
		text.setText(time); //return the new value to screen
	}

	private void take(TextView text) {
		String time = text.getText().toString();
		int i = Integer.parseInt(time);
		i--;
		if (i > 0) { 
			if (i < 10) {
				time = "0" + Integer.toString(i);
			} else {
				time = Integer.toString(i);
			}
		} else { //if its less than 00 just return 00
			time = "00";
		}
		text.setText(time); //return value to screen
	}

	public void fireShutter(View view) {
		// get Camera
		Switch delay = (Switch) findViewById(R.id.delay);
		if (delay.isChecked() == true) { //check if delay is on
			_camera.fireDelay(); //if so fire delay
		} else {
			_camera.fireShutter(); //if not fire shutter
		}
	}

	public void fireTimeLapse(View view) {
		if (_timeLapseRunning == true) { //if the time lapse is running
			_timeLapseWorker.stop(); //stop the time lapse
			_timeLapseRunning = false;
		} else { 
			// set running as true
			_timeLapseRunning = true;
			// set button image as running
			Drawable newButton = _context.getResources().getDrawable(
					R.drawable.lapse_selector);
			ImageButton button = (ImageButton) findViewById(R.id.fireTimeLapse);
			button.setImageDrawable(newButton);
			// get time
			TextView txtSeconds = (TextView) findViewById(R.id.txtSeconds);
			int seconds = Integer.parseInt(txtSeconds.getText().toString());
			TextView txtMinutes = (TextView) findViewById(R.id.txtMinutes);
			int minutes = Integer.parseInt(txtMinutes.getText().toString());
			TextView txtHours = (TextView) findViewById(R.id.txtHours);
			int hours = Integer.parseInt(txtHours.getText().toString());
			int time = (seconds + (minutes * 60) + (hours * 3600)) * 1000;
			//create new timelapse work to execute code in separate thread
			_timeLapseWorker = new TimeLapseWorker(button, _context, _camera, time);
			_timeLapseWorker.execute(); //execute running in background
		}
	}

	public void setCamera(int cameraCode) {
		
		switch (cameraCode) {
		case R.id.radioCanon: _camera = new Canon();
			break;
		case R.id.radioSamsung: _camera = new Samsung();
			break;
		case R.id.radioPentax: _camera = new Pentax();
		default: _camera = new Canon();
			break;		
		}
		
	}
	
	public void selectCamera(View view) {
		int cameraCode = view.getId();
		setCamera(cameraCode);
		//save the camera selected
		SharedPreferences.Editor editor = _preferences.edit();
		editor.putInt("camera", cameraCode);
		editor.commit();
	}
}
