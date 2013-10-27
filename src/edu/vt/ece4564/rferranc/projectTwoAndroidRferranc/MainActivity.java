package edu.vt.ece4564.rferranc.projectTwoAndroidRferranc;


import java.util.Calendar;


import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.webkit.WebViewFragment;

public class MainActivity extends Activity 
	implements SensorEventListener, ServerFragmentInterface {

	private SensorManager mSensorManager;
	private Sensor mHumi;
	private Sensor mTemp;
	private Sensor mLight;
	private double lastTemp = -100;
	private double lastHumi = -1;
	private int lastLight = -1;
	String measuredIn;
	String serverAddress;
	AsyncHttpPost postData;
	// Declare Tab Variable
	ActionBar.Tab Tab1, Tab2, Tab3;
	ServerFragment fragmentTab1 = new ServerFragment();
	SensorFragment fragmentTab2 = new SensorFragment();
	MyWebViewFragment fragmentTab3 = new MyWebViewFragment();
	
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ActionBar actionBar = getActionBar();

		// Hide Actionbar Icon
		actionBar.setDisplayShowHomeEnabled(false);

		// Hide Actionbar Title
		actionBar.setDisplayShowTitleEnabled(false);

		// Create Actionbar Tabs
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Set Tab Icon and Titles
		Tab1 = actionBar.newTab().setText("Server");
		Tab2 = actionBar.newTab().setText("Sensors");
		Tab3 = actionBar.newTab().setText("Graph");

		// Set Tab Listeners
		Tab1.setTabListener(new TabListener(fragmentTab1));
		Tab2.setTabListener(new TabListener(fragmentTab2));
		Tab3.setTabListener(new TabListener(fragmentTab3));

		// Add tabs to actionbar
		actionBar.addTab(Tab1);
		actionBar.addTab(Tab2);
		actionBar.addTab(Tab3);
		
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	    //mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
	    mTemp = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
	    mHumi = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
	    mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
	    measuredIn = "area";
	    serverAddress = "http://172.31.175.84:8080";
	    fragmentTab3.setURL(serverAddress);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * Executes when sensor data changes, gets new data and sends to server
	 * (non-Javadoc)
	 * @see android.hardware.SensorEventListener#onSensorChanged(android.hardware.SensorEvent)
	 */
	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		Calendar c = Calendar.getInstance(); 
		int seconds = c.get(Calendar.SECOND);
		Sensor sensor = event.sensor;
		SensorData data = new SensorData(measuredIn);
		data.setTime(c.getTimeInMillis());
        if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            lastTemp = event.values[0];
            data.setTemp((int)event.values[0]);
            data.setHumidity((int)lastHumi);
            data.setLight(lastLight);
        } else if (sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY) {
            lastHumi = event.values[0];
            data.setHumidity((int)event.values[0]);
            data.setTemp((int)lastTemp);
            data.setLight(lastLight);
        } else if (sensor.getType() == Sensor.TYPE_LIGHT) {
            lastLight = (int) event.values[0];
            data.setLight((int)event.values[0]);
            data.setHumidity((int)lastHumi);
            data.setTemp((int)lastTemp);
        }
        
		if((data.getHumidity()!=-1)&&(data.getTemp()!=-100) &&
				(data.getLight()!=-1)) {
			// Send data to the server and update the UI
	        postData = new AsyncHttpPost(data);
	        postData.execute(serverAddress.trim() + "/data");
			fragmentTab2.updateSensorData(data);
		}
	}
	
	/*
	 * Resumes sensors when the app unpauses
	 * @see android.app.Activity#onResume()
	 */
	protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mHumi, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mTemp, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
    }
	
	/*
	 * Disables sensors when the app pauses
	 * @see android.app.Activity#onPause()
	 */
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
    

	@Override
	public void addressChanged(String newAddress) {
		if(serverAddress.contains("http://")) {
			serverAddress = newAddress;	
		} else {
			serverAddress = "http://" + newAddress;	
		}
		fragmentTab3.setURL(serverAddress);
	}

}
