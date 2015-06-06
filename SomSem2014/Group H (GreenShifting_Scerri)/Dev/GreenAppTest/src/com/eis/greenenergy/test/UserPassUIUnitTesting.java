package com.eis.greenenergy.test;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.EditText;

import com.eis.greenenergy.main.LoginActivity;

public class UserPassUIUnitTesting extends ActivityUnitTestCase<LoginActivity> {
	
	private int username;
	private int password;
	  private LoginActivity activity;
	  
	  public UserPassUIUnitTesting() {
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
	  
	// Check that the layout of the MainActivity contains a textView with the R.id.username ID
	  public void testLayout() {
		 username = com.eis.greenenergy.main.R.id.username;
		 assertNotNull(activity.findViewById(username));
		 EditText view = (EditText) activity.findViewById(username);
			    
		  //Ensure that the text on the EditText is "Username"
		  assertEquals("Incorrect label of the EditText", "Username", view.getHint());
			  
		  
		  
		// Check that the layout of the MainActivity contains a textView with the R.id.password ID
		  
		 password = com.eis.greenenergy.main.R.id.password;
		 assertNotNull(activity.findViewById(password));
		 EditText view2 = (EditText) activity.findViewById(password);
			    
		//Ensure that the text on the EditText is "Password"
		  assertEquals("Incorrect label of the EditText", "Password", view2.getHint());
			  }

}
