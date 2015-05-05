package com.eis.greenenergy.test;

import com.eis.greenenergy.main.MainActivity;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

public class MoreDetailButtonUnitTesting extends android.test.ActivityUnitTestCase<MainActivity> {
	private int moreDetailButton;
	private int currentGreenEnergy;
	private int inOneHour;
	private int inThreeHour;
	private int inFiveHour;
	
	private MainActivity activity;
	  
	  public MoreDetailButtonUnitTesting() {
		    super(MainActivity.class);
		  }
	  @Override
	  protected void setUp() throws Exception {
	    super.setUp();
	    Intent intent = new Intent(getInstrumentation().getTargetContext(),
	        MainActivity.class);
	    startActivity(intent, null, null);
	    activity = getActivity();
	  }
	  
	  
	  public void testLayout() {
		// Check that the layout of the MainActivity contains a button with the R.id.moreDetailButton ID
		    moreDetailButton = com.eis.greenenergy.main.R.id.moreDetailButton;
		    assertNotNull(activity.findViewById(moreDetailButton));
		    Button view1 = (Button) activity.findViewById(moreDetailButton);
		    
		  //Ensure that the text on the button is "More Details"
		    assertEquals("Incorrect label of the button", "More Details", view1.getText());
		  
		    // Check that the layout of the MainActivity contains a textView with the R.id.currentGreenEnergy ID 
		    currentGreenEnergy = com.eis.greenenergy.main.R.id.currentGreenEnergy;
		    assertNotNull(activity.findViewById(currentGreenEnergy));
		    TextView view2 = (TextView) activity.findViewById(currentGreenEnergy);
		    
		  //Ensure that the text on the textView is "Current Green Energy"
		    assertEquals("Incorrect label of the button", "Current Green Energy :", view2.getText());
		    
		 // Check that the layout of the MainActivity contains a textView with the R.id.inOneHour ID 
		    inOneHour = com.eis.greenenergy.main.R.id.inOneHour;
		    assertNotNull(activity.findViewById(inOneHour));
		    TextView view3 = (TextView) activity.findViewById(inOneHour);
		    
		  //Ensure that the text on the textView is "in an hour"
		    assertEquals("Incorrect label of the button", "in an hour :", view3.getText());
		  
		 // Check that the layout of the MainActivity contains a textView with the R.id.inThreeHour ID 
		    inThreeHour = com.eis.greenenergy.main.R.id.inThreeHour;
		    assertNotNull(activity.findViewById(inThreeHour));
		    TextView view4 = (TextView) activity.findViewById(inThreeHour);
		    
		  //Ensure that the text on the textView is "in 3 hours"
		    assertEquals("Incorrect label of the button", "in 3 hours :", view4.getText());
		  
		 // Check that the layout of the MainActivity contains a textView with the R.id.inFiveHour ID 
		    inFiveHour = com.eis.greenenergy.main.R.id.inFiveHour;
		    assertNotNull(activity.findViewById(inFiveHour));
		    TextView view5 = (TextView) activity.findViewById(inFiveHour);
		    
		  //Ensure that the text on the textView is "in 5 hours"
		    assertEquals("Incorrect label of the button", "in 5 hours :", view5.getText());
		  
	  }
	  
	//Ensure that if the getActivity.onClick() method is called, 
	//that the correct intent is triggered via the getStartedActivityIntent() method
	
	  public void testIntentTriggerViaOnClick() {
		    moreDetailButton = com.eis.greenenergy.main.R.id.moreDetailButton;
		    Button view = (Button) activity.findViewById(moreDetailButton);
		    assertNotNull("Button not allowed to be null", view);

		//Ensure that the text on the button is "More Details"
		    assertEquals("Incorrect label of the button", "More Details", view.getText());
		    
		    view.performClick();
		    
		    // TouchUtils cannot be used, only allowed in 
		    // InstrumentationTestCase or ActivityInstrumentationTestCase2 
		  
		    // Check the intent which was started
		    Intent triggeredIntent = getStartedActivityIntent();
		    assertNotNull("Intent was null", triggeredIntent);
		    String data = triggeredIntent.getExtras().getString("detail");

		    assertEquals("Incorrect data passed via the intent",
		        "from main to more detail", data);
		
		    
	  }
}