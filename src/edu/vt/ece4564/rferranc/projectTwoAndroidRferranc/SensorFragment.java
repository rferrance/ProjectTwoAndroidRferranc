package edu.vt.ece4564.rferranc.projectTwoAndroidRferranc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;
import android.app.Fragment;

/*
 * Fragment for viewing sensor data and the data from analysis
 */
public class SensorFragment extends Fragment {
	private TextView tempView;
	private TextView humiView;
	private TextView humiDisp;
	private TextView tempDisp;
	private TextView infoDisp;
	private TextView lightDisp;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        View rootView = inflater.inflate(R.layout.sensorfragment, container, false);
        
		tempView = (TextView) rootView.findViewById(R.id.textView3);
		tempDisp = (TextView) rootView.findViewById(R.id.textView1);
		humiView = (TextView) rootView.findViewById(R.id.textView4);
		humiDisp = (TextView) rootView.findViewById(R.id.textView2);
		infoDisp = (TextView) rootView.findViewById(R.id.textView5);
		lightDisp = (TextView) rootView.findViewById(R.id.textView7);
		
        return rootView;
    }
    
    /*
     * Update current values of sensors
     */
    public void updateSensorData(SensorData data) {
    	if(tempDisp != null) {
    		tempDisp.setText(Integer.toString(data.getTemp()) + "°C");
    		humiDisp.setText(Integer.toString(data.getHumidity()) + "%");
    		lightDisp.setText(Integer.toString(data.getLight()) + "Lx");
    		analizeData(data);
    	}
    }
    
    /*
     * Analize the data from the sensors and output advice based on conditions.
     */
    public void analizeData(SensorData data) {
    	String resString = "";
    	if(data.getHumidity() > 75) {
			resString = "Humidity is too high for wine and cigars.\n";
		} else if(data.getHumidity() > 65) {
			resString = "Humidity is good for wine and cigars.\n";
		} else {
			resString = "Humidity is too low for wine and cigars.\n";
		}
		if(data.getTemp() > 23) {
			resString += "Temperature is too high for wine and cigars.\n";
		} else if(data.getTemp() > 18) {
			resString += "Temperature is good cigars, but too high for " +
					"wine storage.\n";
		} else if((data.getTemp() >= 10)&&((data.getTemp() <= 16))) {
			resString += "Temperature is too low for cigars, but is good " +
					"for red wine storage.\n";
		} else {
			resString += "Temperature is too low for cigars, and " +
					"for red wine storage.\n";
		}
		if(data.getLight() > 250) {
			resString += "Light intensity will likely damage wine and cigars.\n";
		} else if(data.getLight() > 100) {
			resString += "Light intensity may damage wine and cigars.\n";
		} else {
			resString += "Light intensity shoule be safe for wine and cigars.\n";
		}
		infoDisp.setText(resString);
    }
}
