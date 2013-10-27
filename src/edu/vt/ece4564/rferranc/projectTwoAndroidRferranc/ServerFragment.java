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
 * Fragment for setting the server address and port
 */
public class ServerFragment extends Fragment {
	private Button confirmAddress;
	private EditText newAddress;
	private EditText newPort;
	private TextView resultView;
	public ServerFragmentInterface sInterface;
	
	@Override
	public void onAttach(Activity activity) {
	    super.onAttach(activity);

	    // This makes sure that the container activity has implemented
	    // the callback interface. If not, it throws an exception
	    try {
	        sInterface = (ServerFragmentInterface) activity;
	    } catch (ClassCastException e) {
	        throw new ClassCastException(activity.toString()
	                + " must implement ServerFragmentInterface.");
	    }
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        View rootView = inflater.inflate(R.layout.serverfragment, container, false);
        newAddress = (EditText)rootView.findViewById(R.id.editText1);
        newPort = (EditText)rootView.findViewById(R.id.editText2);
		confirmAddress = (Button) rootView.findViewById(R.id.button1);
		resultView = (TextView) rootView.findViewById(R.id.textView2);
		
		confirmAddress.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if((!newAddress.getText().toString().equals(""))&&
				(!newPort.getText().toString().equals(""))) {
					sInterface.addressChanged(newAddress.getText().toString().trim()
							+ ":" + newPort.getText().toString().trim());
					resultView.setText("Server address changed.");
				} else if (!newPort.getText().toString().equals("")) {
					resultView.setText("Please enter an address.");
				} else {
					resultView.setText("Please enter a port.");
				}
			}
		});
        return rootView;
    }

}
