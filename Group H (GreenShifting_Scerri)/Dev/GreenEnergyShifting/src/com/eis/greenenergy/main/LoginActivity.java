package com.eis.greenenergy.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileOutputStream;

import com.eis.greenenergy.connection.*;

/**
 * Class to display and control the view on which the user can log in.<br />
 * This view is displayed when the application is started and the user is not logged in already. 
 * For users who use the application for the first time, the view displays a link to the <code>Signup</code> view.<br />
 * The log in view enables the user to insert his user name and password and manages the log in-process. 
 * Moreover, this class needs to access the AsyncTask <code>Login</code> to conduct the user's log in.
 *
 * @author	Veronika Henk, Sarvenaz Golchin, Mahnaz Hajibaba
 * @version	1.2
 * @since	2014-05-30
 */
public class LoginActivity extends Activity {
	
	/**
	 * Text field for the username to log in.
	 */
	private EditText userlogin;
	
	/**
	 * Text field for the password to log in.
	 */
	private EditText passwordlogin;
	
	/**
	 * Description field to display an error message to the user.
	 */
	private TextView errormsg;
	
	/**
	 * Description field to display the "Log In" label.
	 */
	private TextView label;
	
	/**
	 * Description field to ask the user if he already signed up. 
	 */
	private TextView questnew;

	/**
	 * Button "Log In", to launch the log in-process.
	 */
	private Button login;

	/**
	 * Button "Sign Up", to forward the user to the <code>SignupActivity</code>.
	 */
	private Button gosignup;

	/**
	 * Initiates the activity layout.<br />
	 * Moreover, the different layout views are assigned and the behavior for the buttons is implemented.
	 * This activity's layout has two buttons, one to trigger the log in-process itself and one to redirect to the <code>Signup</code> activity.
	 * 
	 * @param	savedInstanceState	Saved information about each <code>View</code> object in this activity's layout,
	 * 			to enable restoring this state if the activity instance is destroyed and recreated.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Setting up the layout:
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		// Setting up the layout's elements:
		userlogin = (EditText)findViewById(R.id.username);
		passwordlogin = (EditText)findViewById(R.id.password);
		label = (TextView)findViewById(R.id.loginlabel);
		label.setText(getString(R.string.login));
		questnew = (TextView) findViewById(R.id.questnew);
		questnew.setText(getString(R.string.questnew));
		errormsg = (TextView) findViewById(R.id.errormsg);
		errormsg.setVisibility(View.INVISIBLE);
		
		// Setting up the log in-button:
		login = (Button)findViewById(R.id.loginbutton);
		login.setText(getString(R.string.login));
		login.setOnClickListener(new View.OnClickListener() {
			/**
			 * Initiates the log in-process of the user by calling the method <code>login()</code>.<br />
			 * 
			 * @param	v	View which triggers the action of this method by being clicked. In this case it is the Log in button.
			 */
            @Override
			public void onClick(View v) {
                login();
            }
        });
		
		// Setting up the button to redirect to the Signup activity:
		gosignup = (Button) findViewById(R.id.gosignup);
		// Display the button's text as underlined:
		SpannableString linksignup = new SpannableString(getString(R.string.signup));
		linksignup.setSpan(new UnderlineSpan(), 0, linksignup.length(), 0);
		gosignup.setText(linksignup);
		gosignup.setOnClickListener(new View.OnClickListener() {
			/**
			 * Redirects the user to the <code>Signup</code> activity.<br />
			 * In detail, this method starts the activity <code>SignupActivity</code> by creating 
			 * an intent and calling the method <code>startActivity()</code>.
			 * Then the method <code>finish()</code> is called to cause the instance of this activity itself to finish when it is restored.
			 * 
			 * @param	v	View which triggers the action of this method by being clicked. In this case it is the Sign up button.
			 */
			@Override
			public void onClick(View v) {
				Intent gotologin = new Intent(LoginActivity.this, SignupActivity.class);
				gotologin.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		        startActivity(gotologin);
		        finish();
			}
		});
	}

	/**
	 * Method to conduct the process to log in the user. This method is called when the <code>Log In</code> button is clicked.<br />
	 * First, in this method the <code>TextView</code> for error messages is set invisible and the user's input (user name and password) is retrieved.
	 * If the inserted user name is empty, the corresponding error message is displayed to the user. Otherwise the log in of the user is initiated creating 
	 * and executing an instance of the Async-Task <code>Login</code>.<br />
	 * Furthermore, the result of Async-Task is accessed. If an exception is thrown, the error code "-1" is assigned. 
	 * Otherwise if the retrieved result equals "0", 
	 * conducting the log in using the Async-Task <code>Login</code> was successful.<br />
	 * If the Async-Task was assessed as successful, user name and password are stored on the user's device. 
	 * Moreover, the class <code>MainActivity</code> is called and the instance of <code>LoginActivity</code> is finished 
	 * since it should not be visible to the user when he is logged in.<br />
	 * If the Async-Task could not be assessed as successful, the corresponding error message is displayed. 
	 * This message is retrieved by converting the error code returned by the Async-Task:<br />
	 * <code>1</code> means: no connection to the web service could be established;<br />
	 * <code>2</code> means: the username does not exist in the database;<br />
	 * <code>3</code> means: the password is not correct.
	 */
	public void login(){
		// Getting user name and password inserted by the user:
		String username = userlogin.getText().toString();
		String password = passwordlogin.getText().toString();
		// The TextView reserved for error messages is set invisible:
		errormsg.setVisibility(View.INVISIBLE);

		// Testing whether the user's input is valid: checking if the inserted user name is empty:
		if(username.trim().equals("")) {
			// Output the error message
			errormsg.setVisibility(View.VISIBLE);
			errormsg.setText("Please insert an username!");
		}
		else {
			// Try to log the user using the Async-Task Login:
			Login login = new Login(this);
			login.execute(username, password);
			String result;
			try {
				// Retrieve the Async-Task's result:
				result = login.get();
			} catch(Exception e) {
				// If the task did not retrieve a valid result, -1 is assigned:
				result = "-1";
			}
			
			//  Checking if the log in was successful (if the result equals 0):
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
				
				// If the log in was successful, forward to MainActivity.class:
				Intent gotomain = new Intent(LoginActivity.this, MainActivity.class);
		        startActivity(gotomain);
		        // Finish this instance of the activity:
		        finish();
			}

			else {
				// If the log in was not successful, an error message is displayed:
				String errortext;
				// Convert the error code into an error message which is displayed to the user:
				if(result.equals("1"))	errortext = "Connection could not be established!";
				else if(result.equals("2"))	errortext = "Username does not exist!";
				else if(result.equals("3"))	errortext = "The inserted password is not correct!";
				else	errortext = "An unexpected error occured!";
				
				// Displaying the error message:
				errormsg.setVisibility(View.VISIBLE);
				errormsg.setText(errortext);
			}
		}
	}
}