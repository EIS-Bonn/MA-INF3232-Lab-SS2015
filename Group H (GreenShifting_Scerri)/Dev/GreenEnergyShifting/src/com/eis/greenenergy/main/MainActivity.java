package com.eis.greenenergy.main;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import com.eis.greenenergy.help.*;

/**
 * Class to display and control the main view of the application.<br />
 * This activity displays the user's data along with the ImageButton for sending Tweets. 
 * It also shows the current amount of green energy, a related pie chart, and a thumb icon as recommendation. 
 * Moreover this view includes predictions concerning energy data in 1, 3, and 5 hours. 
 * A button enables the forwarding to the detail view by sending an intent to <code>DetailActivity</code>. <br />
 * Furthermore the activity redirects the user to <code>LoginActivity</code>, if the user's login-data is not stored on the internal device storage.
 *
 * @author	Veronika Henk, Sarvenaz Golchin, Mahnaz Hajibaba
 * @version	2.1
 * @since	2014-05-30
 */
public class MainActivity extends Activity {

	/**
     * Upper green energy limit. Influences the amount of scores a user gets for tweeting and setting notifications.
     */
	private int UPPERLIMIT = 60;
	
	/**
     * Lower green energy limit. Influences the amount of scores a user gets for tweeting and setting notifications.
     */
	private int LOWERLIMIT = 27;
	
	/**
     * Description field to display the user's score.
     */
	private TextView scoreview;
	
	/**
     * Description field to display the username.
     */
	private TextView userview;
	
	/**
     * Description field to display the current percentage of green energy in relation to conventional energies.
     */
	private TextView percentageOfGreenEnergyView;
	
	/**
     * Description field to display the green energy prediction for in 1 hour to the user.
     */
	private TextView percentageOneHourView;
	
	/**
     * Description field to display the green energy prediction for in 3 hours to the user.
     */
	private TextView percentageThreeHourView;
	
	/**
     * Description field to display the green energy prediction for in 5 hours to the user.
     */
	private TextView percentageFiveHourView;
	
	/**
     * Current amount of green energy in relation to conventional energies.
     */
	private double percentage;
	
	/**
     * Button "More Details", to forward the user to <code>DetailActivity</code>.
     */
	private Button moreDetailButton;
	
	/**
     * Button displayed as Twitter-icon, which enables sending tweets.
     */
	private ImageButton twitterButton;
	
	/**
     * Layout element to view the bar chart related to the current green energy amount.
     */
	private ImageView chartImageView;
	
	/**
     * Score of the user who is logged in.
     */
	private String score;
	
	/**
     * Name of the user who is logged in.
     */
	private String username;
	
	/**
     * Representation of the user who is currently logged in.
     */
	UserManager user;
 
	/**
     * Twitter object for establishing a connection with the Twitter API and realizing a status update.
     */
	private static Twitter twitter;
    
	/**
     * Request object to parse the Twitter API URL and retrieve the authentication URL.
     */
    private static RequestToken requestToken;
     
    /**
     * Object to access and modify the user's preference data.
     */
    private static SharedPreferences mSharedPreferences;
     
    /**
     * Instance of <code>ConnectionDetector</code>, which creates a <code>ConnectivityManager</code> object to check if the 
     * internet connection is available.
     */
    private ConnectionDetector cd;
     
    /**
     * <code>AlertDialog</code> to display a message if no internet connection is available.
     */
    AlertDialogManager alert = new AlertDialogManager();
	
    /**
	  * Initiates the activity layout, checks for a network connection, and initiates <code>UserManager</code> object.<br />
	  * In detail, the corresponding layout file is assigned in this method. 
	  * Moreover this method checks if the device has access to a network connection 
	  * by creating an instance of the auxiliary class <code>AlertDialogManager</code>.<br />
	  * By creating an instance of the class <code>UserManager</code> this method tries to access the user's data. 
	  * If this is completed successfully, the method <code>initGUI</code> which builds the actual layout is called; 
	  * otherwise it redirects to <code>LoginActivity</code> and this activity itself is finished.
	  *
	  * @param	savedInstanceState	Saved information about each <code>View</code> object in this activity's layout, 
	  * 		to enable restoring this state if the activity instance is destroyed and recreated.
	  */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Assign the activity layout
		setContentView(R.layout.activity_main);
		
		// Enable connection to Twitter's authorization screen, because Twitter API sucks with Android 4.
		if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
		
		// Check for network connection using ConnectionDetector
		cd = new ConnectionDetector(getApplicationContext());
        if (!cd.isConnectingToInternet()) {
            alert.showAlertDialog(MainActivity.this, "Internet Connection Error", "Please connect to working Internet connection", false);
            return;
        }
        
        /*
    	 *  Call isloggedin() method to access username and score
    	 */
		user = new UserManager(this);
		// If the user is not logged in, LoginActivity is called and this activity is finished.
    	if(!user.getLoggedIn()) {
    		Intent gotologin = new Intent(MainActivity.this, LoginActivity.class);
    		gotologin.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
    	       startActivity(gotologin);
    	       finish();
    	}
    	// If the user is logged in, username and score are assigned, and the activity layout is displayed.
    	else {
    		score = user.getScore();
    		username = user.getUsername();
    		initGUI(); 	
    	}
	}	
	
	/**
	 * Calls the method to initiate the activity layout if the user is logged in, or finishes this activity otherwise.<br />
	 * Moreover, this method creates a new instance of <code>UserManager</code> when the activity is restored and checks 
	 * if the user is logged in. If the user is logged in, the user data is displayed along with the activity's layout.
	 * Otherwise <code>LoginActivity</code> is called and this instance of the <code>MainActivity</code> is terminated.
	 */
	@Override
	protected void onResume(){
		super.onResume();
		
		// Create a new instance of UserManager
		user = new UserManager(this);
		
		// If the user is not logged in LoginActivity is called and this activity is finished.
    	if(!user.getLoggedIn()) {
    		Intent gotologin = new Intent(MainActivity.this, LoginActivity.class);
    		gotologin.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
    	       startActivity(gotologin);
    	       finish();
    	}
    	// If the user is logged in, the user data is assigned and initGUI() is called.
    	else {
    		score = user.getScore();
    		username = user.getUsername();
    		// If the user is already logged in, we display username and score
    		initGUI(); 	
    	}
	}
	
	/**
	 * Sets the actual layout for this activity.<br />
	 * First, an instance of <code>SharedPreferences</code> is initialized.
	 * Next, the current point in time is requested and the individual layout elements are initialized and text is assigned to the textviews.
	 * Therefore, the current amount of green energy and predictions for in 1, 3, and 5 hours are retrieved, 
	 * calling the method <code>accessdata()</code> for the instance of <code>UserManager</code> with the current point in time as parameter.
	 * The predictions are assigned to the according textviews in order to display them on the screen.<br />
	 * Based on the current percentage of green energy, the corresponding chart is selected and displayed on the center of the screen.<br />
	 * Also, the more detail and the Twitter button are implemented by setting up their <code>onClick()</code> methods.
	 */
	private void initGUI() {
		// Initialize instance of SharedPreferences
        mSharedPreferences = getApplicationContext().getSharedPreferences("MyPref", 0);
        
		// Retrieve current point in time
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(new Date());
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int min = calendar.get(Calendar.MINUTE);
		String time = hour + ":" + min;
		
        // Initialize layout elements
		percentageOfGreenEnergyView = (TextView) findViewById(R.id.percentageOfGreenEnergy);
		chartImageView = (ImageView) findViewById(R.id.chartImageView);
		percentageOneHourView  = (TextView) findViewById(R.id.percentageOneHour);
		percentageThreeHourView  = (TextView) findViewById(R.id.percentageThreeHour);
		percentageFiveHourView  = (TextView) findViewById(R.id.percentageFiveHour);
		moreDetailButton = (Button) findViewById(R.id.moreDetailButton);
		twitterButton = (ImageButton) findViewById(R.id.twitterImageButton);
		scoreview = (TextView) findViewById(R.id.score);
		userview = (TextView) findViewById(R.id.usernameview);
		scoreview.setText(score);
		userview.setText(username);
		
		// Get predictions:
		String[] pdata = user.accessdata(time);
		// Format predictions and assign them to the corresponding textviews:
		if(pdata != null) {
			if(Double.parseDouble(pdata[1]) >= 10) {
				if(pdata[1].length() == 4)	percentageOneHourView.setText(pdata[1] + "0 %");
				else	percentageOneHourView.setText(pdata[1] + " %");
			}
			else {
				if(pdata[1].length() == 3)	percentageOneHourView.setText("  " + pdata[1] + "0 %");
				else	percentageOneHourView.setText("  " + pdata[1] + " %");
			}
			
			if(Double.parseDouble(pdata[2]) >= 10) {
				if(pdata[2].length() == 4)	percentageThreeHourView.setText(pdata[2] + "0 %");
				else	percentageThreeHourView.setText(pdata[2] + " %");
			}
			else {
				if(pdata[2].length() == 3)	percentageThreeHourView.setText("  " + pdata[2] + "0 %");
				else	percentageThreeHourView.setText("  " + pdata[2] + " %");
			}
			
			if(Double.parseDouble(pdata[3]) >= 10) {
				if(pdata[3].length() == 4)	percentageFiveHourView.setText(pdata[3] + "0 %");
				else	percentageFiveHourView.setText(pdata[3] + " %");
			}
			else {
				if(pdata[3].length() == 3)	percentageFiveHourView.setText("  " + pdata[3] + "0 %");
				else	percentageFiveHourView.setText("  " + pdata[3] + " %");
			}
			
			if(Double.parseDouble(pdata[0]) >= 10) {
				if(pdata[0].length() == 4)	percentageOfGreenEnergyView.setText(pdata[0] + "0 %");
				else	percentageOfGreenEnergyView.setText(pdata[0] + " %");
			}
			else {
				if(pdata[0].length() == 3)	percentageOfGreenEnergyView.setText(pdata[0] + "0 %");
				else	percentageOfGreenEnergyView.setText(pdata[0] + " %");
			}
		
			// Display corresponding chart:
			percentage = Double.parseDouble(pdata[0]);
			if(percentage < 25)	chartImageView.setImageResource(R.drawable.fake_1_24);
			else if(percentage < 41) chartImageView.setImageResource(R.drawable.fake_25_40);
			else if(percentage < 60) chartImageView.setImageResource(R.drawable.fake_41_59);
			else if(percentage < 76) chartImageView.setImageResource(R.drawable.fake_60_75);
			else chartImageView.setImageResource(R.drawable.fake_76_100);
		}
		
		// OnClickListener of Twitter button:
		twitterButton.setOnClickListener(new View.OnClickListener() {
			/**
			 * Published the user's score on Twitter by calling the method <code>loginToTwitter()</code>.<br />
			 * If the user was already logged into Twitter, his status was updated successfully and his score was increased. 
			 * The user's new score is written into the database.<br />
			 * If the user was not already logged into Twitter or if the status update was not successful, 
			 * the user is forwarded to the page provided by Twitter API to authorize this application.
			 * 
			 * @param v	View which triggers the action of this method by being clicked. In this case it is the Twitter-<code>ImageButton</code>.
			 */
			@Override
			public void onClick(View v) {
				// Try to publish the user's score on Twitter:
				twitter = user.loginToTwitter();
	            if(twitter != null) {
	             	try {
	             		requestToken = twitter.getOAuthRequestToken(user.getCallbackURL());
	                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(requestToken.getAuthenticationURL())));
	                } catch (TwitterException e) {
	                	e.printStackTrace();
	                }
	            }
	            	
	            if(user.scoreincreased()) {
	            	// Tweeting was successful, user gets more score for sharing on Twitter:
	            	int addscore = -1;
	            	if(percentage > UPPERLIMIT)	addscore = 5;
	            	else if(percentage > LOWERLIMIT)	addscore = 3;
	            	else	addscore = 1;
	            		
	            	int scoreint = Integer.parseInt(score);
					scoreint = scoreint + addscore;
					score = "" + scoreint;
					scoreview.setText(score);
					boolean storescore = user.writeScore(scoreint);
					if(storescore)	Toast.makeText(getApplicationContext(), "Your tweet was posted and you increased your score!", Toast.LENGTH_LONG).show();
	            }
	        }
	    });

		// OnClickListener of more detail button:
		moreDetailButton.setOnClickListener(new View.OnClickListener() {
			/**
			 * Redirects the user to the More Details activity.<br />
			 * In detail, this method starts the activity <code>DetailActivity</code> by creating
			 * an intent and calling the method <code>startActivity()</code>.<br />
			 * The More Details activity displays the predictions of the green energy amount for the next ten hours.
			 * 
			 * @param	v	View which triggers the action of this method by being clicked. In this case it is the More Details button.
			 */
            @Override
			public void onClick(View v) {
            	Intent goToDetailPage = new Intent(MainActivity.this, DetailActivity.class);
            	// Adding info text for testing:
            	goToDetailPage.putExtra("detail", "from main to more detail");
		        startActivity(goToDetailPage);
            }
           });		

		/*
		 * If condition which is only important when the user was redirected from the Twitter authentification page.
		 * Here the URI is parsed to get the oAuth Verifier.
		 * Also if the user is logged in and if his tweet was posted successfully, his score is increased.
		 */
        if (!user.isTwitterLoggedInAlready()) {
        	// First call of Activity since the user was supposed to log in to twitter
            Uri uri = getIntent().getData();
            if (uri != null && uri.toString().startsWith(user.getCallbackURL())) {
            	// User did log in: we parse the URI to get the oAuth verifier
                String verifier = uri.getQueryParameter(user.getTwitterOauthVerfifier());
 
                try {
                    // Get the access token
                    AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
 
                    // Shared Preferences
                    Editor e = mSharedPreferences.edit();
 
                    // Store access token, access token secret, and login status (true) in the user's application preferences: 
                    e.putString(user.getPrefKeyOauthToken(), accessToken.getToken());
                    e.putString(user.getPrefKeyOauthSecret(), accessToken.getTokenSecret());
                    e.putBoolean(user.getPrefKeyTwitterLogin(), true);
                    e.commit();
                    Log.i("Twitter OAuth Token", "> " + accessToken.getToken());
                    
                    user.loginToTwitter();
                    if(user.scoreincreased()) {
	            		// Tweeting was successful, user gets more score for sharing on Twitter depending on the current amount of green energy:
                    	int addscore = -1;
	            		if(percentage > UPPERLIMIT)	addscore = 5;
	            		else if(percentage > LOWERLIMIT)	addscore = 3;
	            		else	addscore = 1;
                    	
	            		int scoreint = Integer.parseInt(score);
						scoreint = scoreint + addscore;
						score = "" + scoreint;
						scoreview.setText(score);
						boolean storescore = user.writeScore(scoreint);
						if(storescore)	Toast.makeText(getApplicationContext(), "Your tweet was posted and you increased your score!", Toast.LENGTH_LONG).show();
	            	}
               
                } catch (Exception e) {
                    // Check log for login errors
                    Log.i("Twitter Login Error", "> " + e.getMessage());
                }
            }
        }
	}

	/**
	 * Initiates the menu initialization when an instance of the activity is created.<br />
	 * Moreover the menu is inflated when this method is called and the items defined in this method are added.
	 * So far we only have one item, which is the logout function.
	 * 
	 * @param	menu	The menu of this activity.
	 * @return			<code>true</code> if the menu was inflated successfully;<br />
	 * 					<code>false</code> otherwise.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Defines the actions to be done when an item in the menu is selected.<br />
	 * So far we only have one menu entry, which has the purpose to trigger the user's logout.
	 * Therefore if this item is selected, the method <code>logout()</code> which is also defined in this activity, is called.
	 * 
	 * @param	item	Menu entry which was selected.
	 * @return			<code>true</code> if the selected item was identified successfully and according actions could be initiated;<br />
	 * 					<code>false</code> otherwise.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Find the called menu entry by comparing IDs
		int id = item.getItemId();
		if (id == R.id.action_signout) {
			logout();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Conducts the user's logout from the application as well as from Twitter.<br />
	 * Moreover to log the user out from the application, the login-data is deleted from his device.
	 * To log out from Twitter the instance of <code>SharedPreferences</code> is edited and the Twitter Api keys are removed from it.
	 * This activity automatically forwards to <code>LoginActivity</code>. 
	 * As the user is not supposed to see this view, when he is not logged in, this activity is then finished.
	 */
	public void logout(){
		// Delete login-data
		File dir = getFilesDir();
		new File(dir, "username").delete();
		new File(dir, "password").delete();
		new File(dir, "score").delete();
		
		if(user.isTwitterLoggedInAlready()) {
			// Clear shared preferences
	        Editor e = mSharedPreferences.edit();
	        e.remove(user.getPrefKeyOauthToken());
	        e.remove(user.getPrefKeyOauthSecret());
	        e.remove(user.getPrefKeyTwitterLogin());
	        e.commit();
		}
		
		// Forward to LoginActivity and terminate this instance of MainActivity
		Intent gotologin = new Intent(MainActivity.this, LoginActivity.class);
		gotologin.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(gotologin);
        finish();
	}

}