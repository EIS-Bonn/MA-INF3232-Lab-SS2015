package com.eis.greenenergy.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileOutputStream;

import com.eis.greenenergy.connection.*;

/**
 * Class to display and control the view in which the user can sign up in order to use the application.<br />
 * This view enables the user to chose user name and password and manages the sign up-process. 
 * In order to do so, this activity needs to access the Async-Task <code>Signup</code>.
 * Moreover, this class uses the same layout file as <code>LoginActivity</code> because the same element types are needed. 
 * The layout element's labels and attributes are adapted programmatically.
 *
 * @author	Veronika Henk, Sarvenaz Golchin, Mahnaz Hajibaba
 * @version	1.2
 * @since	2014-05-30
 */
public class SignupActivity extends Activity {
	/**
	 * Text field for the username to sign up.
	 */
	private EditText usersignup;
	
	/**
	 * Text field for the password to sign up.
	 */
	private EditText passwordsignup;
	
	/**
	 * Description field to display an error message to the user.
	 */
	private TextView errormsg;
	
	/**
	 * Description field to display the "Sign Up" label.
	 */
	private TextView label;
	
	/**
	 * Description field to ask the user if he already signed up. 
	 * This field is only in this activity because it shares the same layout file with <code>LoginActivity</code>. 
	 * In this activity the <code>questnew</code>-field is set invisible.
	 */
	private TextView questnew;
	
	/**
	 * Button "Sign Up", to launch the sign up-process.
	 */
	private Button signup;
	
	/**
	 * Button "Sign Up", to forward the user to the <code>SignupActivity</code>. 
	 * This button is only in this class because this activity and <code>LoginActivity</code> 
	 * share the same layout file and this button needs to be set invisible in this activity.
	 */
	private Button gotologin;

	/**
	  * Initiates the activity layout.<br />
	  * Moreover, the different layout views are assigned and the behavior for the button is implemented.
	  * This activity's layout has one button to trigger the sign up-process itself.
	  * 
	  * @param	savedInstanceState	Saved information about each <code>View</code> object in this activity's layout,
	  * 		to enable restoring this state if the activity instance is destroyed and recreated.
	  */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Setting up the layout:
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		// Setting up the sign up-button:
		signup = (Button)findViewById(R.id.loginbutton);
		signup.setText(getString(R.string.signup));
		signup.setOnClickListener(new View.OnClickListener() {
			/**
			 * Initiates the sign up-process of ther user by calling the method <code>signup()</code>.<br />
			 * 
			 * @param	v	View which triggers the action of this method by being clicked. In this case it is the Sign up button.
			 */
			@Override
			public void onClick(View v) {
				signup();
			}
		});
		
		// Setting up the layout's elements:
		usersignup = (EditText)findViewById(R.id.username);
		passwordsignup = (EditText)findViewById(R.id.password);
		label = (TextView) findViewById(R.id.loginlabel);
		label.setText(getString(R.string.signup));
		questnew = (TextView) findViewById(R.id.questnew);
		questnew.setVisibility(View.INVISIBLE);
		errormsg = (TextView) findViewById(R.id.errormsg);
		errormsg.setVisibility(View.INVISIBLE);
		gotologin = (Button) findViewById(R.id.gosignup);
		gotologin.setVisibility(View.INVISIBLE);
	}

	/**
	 * Method to conduct the process to sign up the user. This method is called when the <code>Sign Up</code> button is clicked.<br />
	 * First, in this method the <code>TextView</code> for error messages is set invisible and the user's input (user name and password) is retrieved.
	 * If the inserted user name is empty or if the password has less than 5 characters, the corresponding error message is displayed to the user. 
	 * Otherwise the sign up of the user is initiated creating and executing an instance of the Async-Task <code>Signup</code>.<br />
	 * Furthermore, the result of Async-Task is accessed. If an exception is thrown, the error code "-1" is assigned. 
	 * Otherwise if the retrieved result equals "0", conducting the sign up using the Async-Task <code>Signup</code> was successful.<br />
	 * If the Async-Task was assessed as successful, user name and password are stored on the user's device. Moreover, the class 
	 * <code>MainActivity</code> is called and the instance of <code>SignupActivity</code> is finished, since it should not be visible 
	 * to the user after he signed up.<br />
	 * If the Async-Task could not be assessed as successful, the corresponding error message is displayed. 
	 * This message is retrieved by converting the error code returned by the Async-Task:<br />
	 * <code>1</code> means: no connection to the web service could be established;<br />
	 * <code>2</code> means: the username does already exist in the database;<br />
	 * <code>-1</code> means: an unexpected error occurred.
	 */
	public void signup(){
		// Getting the username and password inserted by the user:
		String username = usersignup.getText().toString();
		String password = passwordsignup.getText().toString();
		// The TextView reserved for error messages is set invisible:
		errormsg.setVisibility(View.INVISIBLE);
		
		// Testing whether the user's input is valid: checking if the inserted user name is empty:
		if(username.trim().equals("")) {
			// Output the error message
			errormsg.setVisibility(View.VISIBLE);
			errormsg.setText("Please insert an username!");
		}
		// Checking if the password has at least 5 characters:
		else if(password.trim().length() < 5) {
			// Output the error message
			errormsg.setVisibility(View.VISIBLE);
			errormsg.setText("Password must be at least 5 characters!");
		}
		else {
			// Try to sign up the user using the Async-Task Signup:
			Signup signup = new Signup(this);
			signup.execute(username, password);
			String result;
			try {
				// Retrieve the Async-Task's result:
				result = signup.get();
			} catch(Exception e) {
				// If the task did not retrieve a valid result, -1 is assigned:
				result = "-1";
			}
			
			// Checking if the sign up was successful (if the result equals 0):
			if(result.equals("0")) {
				try {
					// Store user name and password on the user's device:
					FileOutputStream fos = openFileOutput("username", Context.MODE_PRIVATE);
					fos.write(username.getBytes());
					fos.close();
					FileOutputStream fos2 = openFileOutput("password", Context.MODE_PRIVATE);
					fos2.write(password.getBytes());
					fos2.close();
				} catch(Exception e) { }	
				
				// If the sign up was successful, forward to MainActivity.class:
				Intent gotomain = new Intent(SignupActivity.this, MainActivity.class);
		        startActivity(gotomain);
		        // Finish this instance of the activity:
		        finish();
			}
			
			else {
				// If the sign up was not successful, an error message is displayed:
				String errortext;
				// Converting the error codes into an error message which is displayed to the user:
				if(result.equals("1"))	errortext = "Connection could not be established!";
				else if(result.equals("2"))	errortext = "Username already exist!";
				else	errortext = "An unexpected error occured!";
				
				// Displaying the error message:
				errormsg.setVisibility(View.VISIBLE);
				errormsg.setText(errortext);
			}
		}
	}
}