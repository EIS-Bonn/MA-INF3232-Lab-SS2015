package com.eis.greenenergy.test;

import android.content.Intent;
import android.widget.Button;

import com.eis.greenenergy.main.LoginActivity;

public class LoginButtonUIUnitTesting extends android.test.ActivityUnitTestCase<LoginActivity> {
	
	private int loginbutton;
	  private LoginActivity activity;
	  
	  public LoginButtonUIUnitTesting() {
		    super(LoginActivity.class);
		  }
	  @Override
	  protected void setUp() throws Exception {
	    super.setUp();
	    Intent intent = new Intent(getInstrumentation().getTargetContext(),
	        LoginActivity.class);
	    startActivity(intent, null, null);
	    activity = getActivity();
	  }
	  
	  // Check that the layout of the MainActivity contains a button with the R.id.loginbutton ID
	  public void testLayout() {
		    loginbutton = com.eis.greenenergy.main.R.id.loginbutton;
		    assertNotNull(activity.findViewById(loginbutton));
		    Button view = (Button) activity.findViewById(loginbutton);
		    
		  //Ensure that the text on the button is "Login"
		    assertEquals("Incorrect label of the button", "Log In", view.getText());
		  }
	  
	//Ensure that if the getActivity.onClick() method is called, 
	//that the correct intent is triggered via the getStartedActivityIntent() method
	
	  public void testIntentTriggerViaOnClick() {
		    loginbutton = com.eis.greenenergy.main.R.id.loginbutton;
		    Button view = (Button) activity.findViewById(loginbutton);
		    assertNotNull("Button not allowed to be null", view);

//		    view.performClick();
//		    
//		    // TouchUtils cannot be used, only allowed in 
//		    // InstrumentationTestCase or ActivityInstrumentationTestCase2 
//		  
//		    // Check the intent which was started
//		    Intent triggeredIntent = getStartedActivityIntent();
//		    assertNotNull("Intent was null", triggeredIntent);
//		    String data = triggeredIntent.getExtras().getString("logid");
//
//		    assertEquals("Incorrect data passed via the intent",
//		        "from login to main", data);
//		
//		  
		    }
  
}

