package com.eis.greenenergy.test;

import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.ViewAsserts;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.TextView;

import com.eis.greenenergy.main.R;
import com.eis.greenenergy.main.MainActivity;
import com.eis.greenenergy.main.DetailActivity;

public class MainToMoreDeatilsIntegrationTesting extends ActivityInstrumentationTestCase2 <MainActivity> {

	private MainActivity activity;

	  public MainToMoreDeatilsIntegrationTesting() {
	    super(MainActivity.class);
	  }

	  @Override
	  protected void setUp() throws Exception {
	    super.setUp();
	    setActivityInitialTouchMode(false);
	    activity = getActivity();
	  }
	  
	  public void testStartSecondActivity() throws Exception {
		    // add monitor to check for the second activity
		    ActivityMonitor monitor = getInstrumentation().addMonitor(DetailActivity.class.getName(), null, false);

		    // find button and click it
		    Button view = (Button) activity.findViewById(R.id.moreDetailButton);
		    
		    // TouchUtils handles the sync with the main thread internally
		    TouchUtils.clickView(this, view);

		    // wait 2 seconds for the start of the activity
		    DetailActivity startedActivity = (DetailActivity) monitor.waitForActivityWithTimeout(2000);
		    assertNotNull(startedActivity);

		    // search for the textView
		    TextView textView = (TextView) startedActivity.findViewById(R.id.titledetail);
		    
		    // check that the TextView is on the screen
		    ViewAsserts.assertOnScreen(startedActivity.getWindow().getDecorView(),
		        textView);
		    // validate the text on the TextView
		    assertEquals("Text incorrect", "Hourly Green Energy Prediction", textView.getText().toString());
		    
		    // press back and click again
		    this.sendKeys(KeyEvent.KEYCODE_BACK);
		    
		    TouchUtils.clickView(this, view);
		  }
	
}
