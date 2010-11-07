package com.psegina.vctrl;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/** Main screen for the Voltage Control app
 * @package com.psegina.vctrl
 * @author Petar Šegina <psegina@ymail.com>
 * @www http://psegina.vacau.com
 *
 * This is the main screen launched when the app is run 
 * 
 */
public class VoltageControl extends Activity {
	private VoltagePair[] vPairs;
	private VoltageTable vTab = new VoltageTable();
	private String s,ov,fq,toastText; 
	private TextView originalValues,currentValues,frequency,what,with;
	private Button increase, decrease, reset, applyChange;
	
	/**
	 * Warns the user about possible consequences of using this software
	 */
	private void legal(){
		new AlertDialog.Builder(this).setTitle(R.string.legalTitle).setMessage(R.string.legalBody).show();
	}
		
	// We set up the OnClickListener for the UI elements:
	private View.OnClickListener l = new View.OnClickListener(){
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.increaseButton:
				VoltageKnob.increase(5);
				toastText = "Voltage increased";
				break;
			case R.id.decreaseButton:
				VoltageKnob.decrease(5);
				toastText = "Voltage decreased";
				break;
			case R.id.resetButton:
				VoltageKnob.reset();
				toastText = "Voltage reset";
				break;
			case R.id.applyButton:
				VoltageKnob.replace(Integer.parseInt(""+what.getText()), Integer.parseInt(""+with.getText()));
				toastText = "Replaced values";
				what.setText("");
				with.setText("");
				break;
			default:
				toastText = "Unknown command";
				break;
			}
			Toast.makeText(getApplicationContext(), toastText, 250).show();
			drawTable();
		}
		
	};
	
	private void drawTable(){
		vTab.read();
        vPairs=vTab.getPairs();
        s = "";
        ov = "";
        fq = "";
        for(int i=0;i<vPairs.length;i++){
        	s+=vPairs[i].originalVoltage+"\n";
        	ov+=vPairs[i].currentVoltage+"\n";
        	fq+=vPairs[i].targetSpeed+"\n";
        }
        s=s.substring(0,s.length()-1);
        ov=ov.substring(0,ov.length()-1);
        fq=fq.substring(0,fq.length()-1);
        originalValues.setText(s);
        currentValues.setText(ov);
        frequency.setText(fq);
        vPairs=null;
        s=null;
        ov=null;
        fq=null;
	}
	
	public void onCreateOptionsMenu(){
		
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Prevent autofocus on EditText:
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
        
        // License and warnings
        legal();
        // Load the user interface
        setContentView(R.layout.main);
                
        // Load the TextViews:
        originalValues = (TextView) findViewById(R.id.mainOriginal);
        currentValues = (TextView) findViewById(R.id.mainCurrent);
        frequency = (TextView) findViewById(R.id.mainFrequency);
        what = (TextView) findViewById(R.id.replaceWhat);
        with = (TextView) findViewById(R.id.replaceWith);
        // Load the buttons:
        increase = (Button) findViewById(R.id.increaseButton);
        decrease = (Button) findViewById(R.id.decreaseButton);
        reset = (Button) findViewById(R.id.resetButton);
        applyChange = (Button) findViewById(R.id.applyButton);
        // Set the onClick listeners
        increase.setOnClickListener(l);
        decrease.setOnClickListener(l);
        reset.setOnClickListener(l);
        applyChange.setOnClickListener(l);
        
        // We want to make sure the UV module is loaded:
        if( Helper.checkForModule() )
        	originalValues.setText("Loaded");
        	else originalValues.setText("nOt");
        
        // Finally, we load the current table:
       drawTable();
       
    }
    
    // Closes the Activity if the user exits.
    public void onPause(){
    	finish();
    }
    
    // If the user comes back from an another application, let's refresh the view:
    @Override
    public void onResume(){
    	super.onResume();
    	drawTable();
    }
    
}