package com.eis.greenenergy.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import twitter4j.Twitter;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.eis.greenenergy.help.*;

/**
 * Class to display and control the detail view of the application.<br />
 * This view includes a ListView with the next 10 hourly energy data predictions, as well as the display of the user's data. 
 * If the user already granted the authority to access his Twitter account, the ImageButton for tweeting is also displayed.
 *
 * @author	Veronika Henk, Sarvenaz Golchin, Mahnaz Hajibaba
 * @version	2.1
 * @since	2014-06-14
 */
public class DetailActivity extends Activity {

	/**
	 * Upper green energy limit. Influences the amount of scores a user gets for tweeting and setting notifications.
	 */
	private int UPPERLIMIT = 60;
	
	/**
	 * Lower green energy limit. Influences the amount of scores a user gets for tweeting and setting notifications.
	 */
	private int LOWERLIMIT = 27;
	
	/**
	 * Current amount of green energy in relation to conventional energies.
	 */
	private double percentage;
	
	/**
	 * Score of the user who is logged in.
	 */
	private String score;
	
	/**
	 * Name of the user who is logged in.
	 */
	private String username;
	
	/**
	 * Description field to display the user's score.
	 */
	private TextView scoreview;
	
	/**
	 * Description field to display the username.
	 */
	private TextView userview;
	
	/**
	 * List with 10 entries containing green energy predictions for the next 10 hours.
	 */
	private ListView predictionList;
	
	/**
	 * Instance of <code>CustomAdapter</code>, necessary to create a custom <code>ListView</code>.
	 */
	CustomAdapter dataAdapter = null;
	
	/**
	 * Button displayed as Twitter-icon, which enables sending tweets.
	 */
	private ImageButton twitterButton;
	
	/**
	 * Representation of the user who is currently logged in.
	 */
	private UserManager user;
	
	/**
	 * Array containing the background color for every entry in the <code>ListView</code>.
	 */
	String[] colors = new String[10];
	
	/**
	 * Twitter object for establishing a connection with the Twitter API and realizing a status update.
	 */
    private static Twitter twitter;
     
    /**
	 * Object to access and modify the user's preference data.
	 */
    private static SharedPreferences mSharedPreferences;
     
    /**
	 * Instance of <code>ConnectionDetector</code>, which creates a <code>ConnectivityManager</code> object to check if the internet connection is available.
	 */
    private ConnectionDetector cd;
     
    /**
	 * <code>AlertDialog</code> to display a message if no internet connection is available.
	 */
    AlertDialogManager alert = new AlertDialogManager();
    
    /**
	 * Notification to be displayed to the user.
	 */
    Notification notification;
    
	/**
	  * Initiates the activity layout, checks for a network connection, and initiates the <code>UserManager</code> object.<br />
	  * In detail, the corresponding layout file is assigned in this method. 
	  * Moreover, this method checks if the device has access to a network connection 
	  * by creating an instance of the auxiliary class <code>AlertDialogManager</code>.<br />
	  * By creating an instance of the class <code>UserManager</code> this method tries to access the user's data. 
	  * If the user's data is retrieved successfully, the method <code>initGUI</code> which builds the actual layout is called; 
	  * otherwise this activity itself is finished and <code>MainActivity</code> is restored and subsequently redirects to <code>LoginActivity</code>.
	  *
	  * @param	savedInstanceState	Saved information about each <code>View</code> object in this activity's layout, 
	  * 		to enable restoring this state if the activity instance is destroyed and recreated.
	  */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Assign the activity layout
		setContentView(R.layout.activity_detail);
		
		// Enable connection to Twitter's authorization screen, because Twitter API sucks with Android 4.
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
				
		// Check for network connection using ConnectionDetector
		cd = new ConnectionDetector(getApplicationContext());
		if (!cd.isConnectingToInternet()) {
			alert.showAlertDialog(DetailActivity.this, "Internet Connection Error", "Please connect to working Internet connection", false);
			return;
		}
		
		// Call isloggedin() method to access username and score
		user = new UserManager(this);
		if(!user.getLoggedIn()) {
			// If the user is not logged in, this activity is finished.
    	    finish();
    	}
    	else {
    		// If the user is logged in, username and score are assigned, and the activity layout is displayed.
    		score = user.getScore();
    		username = user.getUsername();
    		initGUI();
    	}
	}

	/**
	 * Calls the method to initiate the activity layout if the user is logged in, or finishes this activity otherwise.<br />
	 * Moreover, this method creates a new instance of <code>UserManager</code> when the activity is restored and checks 
	 * if the user is logged in. If the user is logged in, the user data is displayed along with the activity's layout.
	 */
	@Override
	protected void onResume(){
		super.onResume();
		
		// Create a new instance of UserManager
		user = new UserManager(this);
		
		if(user.getLoggedIn()) {
			// If the user is logged in, the user data is assigned and initGUI() is called.
			score = user.getScore();
			username = user.getUsername();
			initGUI(); 		
		}	
		else {
			// If the user is not logged in, this activity is finished.
			finish();
		}
	}
	
	/** 
	 * Sets the actual layout for this activity.<br />
	 * In detail, the individual layout elements are initialized and text is assigned to the text views.
	 * Moreover an instance of <code>SharedPreferences</code> is initialized, as well.
	 * The current point in time is obtained in order to retrieve the current amount of green energy calling the method <code>accessdata()</code>.<br />
	 * Using the method <code>isTwitterLoggedInAlready()</code>, the twitter button becomes invisible if the user is not logged into Twitter already;
	 * otherwise the behavior of the twitter button is implemented by setting up the <code>onClick()</code> method.<br />
	 * Furthermore the current point of time is requested and predictions for the next 10 hours are retrieved, 
	 * calling the method <code>getPredictions()</code> from the instance of <code>UserManager</code>.
	 * The method <code>displayListView()</code> is called in order to construct the list view with its entries and display it on the screen.
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
		
     	// Get current amount of green energy
     	String[] pdata = user.accessdata(time);
		percentage = Double.parseDouble(pdata[0]);
		
        // Initialize layout elements
		scoreview = (TextView) findViewById(R.id.score);
		userview = (TextView) findViewById(R.id.usernameview);
		twitterButton = (ImageButton) findViewById(R.id.twitterImageButton);	
		scoreview.setText(score);
		userview.setText(username);
		
		// If the user is not logged into Twitter, the according button is not visible
		if (!user.isTwitterLoggedInAlready()) {
			twitterButton.setVisibility(View.INVISIBLE);
		}
		// If the user is logged into Twitter, the method onClick() is declared
		else {
			twitterButton.setOnClickListener(new View.OnClickListener() {
				/**
				 * Initiates the posting of new tweet and the increment of the user's score.<br />
				 * The update of the user's Twitter status is triggered by calling the method <code>loginToTwitter()</code> 
				 * from the <code>UserManager</code> instance.
				 * Furthermore if the user's Twitter status was updated successfully his score is increased depending on the current amount of green energy. 
				 * The user's new score is displayed on the screen and also stored in the external database by 
				 * calling the method <code>writeScore()<code>, which is part of the <code>UserManager</code> class. 
				 * Moreover a <code>Toast</code> message is shown to the user.
				 * 
				 * @param	v	View which triggers the actions of this method by being clicked. In this case it is the Twitter button.	
				 */
				@Override
				public void onClick(View v) {
			        // Call the method to publish user's score on Twitter!
					twitter = user.loginToTwitter();
			            	
			        if(user.scoreincreased() && twitter == null) {
			        	// If tweeting was successful, the user gets scores for sharing on Twitter:
			        	int addscore = -1;
	            		if(percentage > UPPERLIMIT)	addscore = 5;
	            		else if(percentage > LOWERLIMIT)	addscore = 3;
	            		else	addscore = 1;
			        	
	            		// Write the user's new score into the database:
			            int scoreint = Integer.parseInt(score);
						scoreint = scoreint + addscore;
						score = "" + scoreint;
						scoreview.setText(score);
						boolean storescore = user.writeScore(scoreint);
						if(storescore)	
							Toast.makeText(getApplicationContext(), "Your tweet was posted and you increased your score!", Toast.LENGTH_LONG).show();
			        }
			    }
			});
		}
		
		// Call the function to display the list view with its prediction entries:
		displayListView();
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
		int id = item.getItemId();
		// Find the called menu entry by comparing IDs
		if (id == R.id.action_signout) {
			logout();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Constructs the list view to show predictions for the next 10 hours to the user.<br />
	 * In this method the current point in time is retrieved in order to get predictions for the next 10 hours calling the method <code>getPredictions()</code>.
	 * The formatted amount of green energy for every prediction is stored in the array <code>predictions</code>, 
	 * the corresponding color for each entry in the list view is stored in the array <code>colors</code>.
	 * Furthermore the unformatted amount of green energy is stored in the array <code>amounts</code>; 
	 * it is necessary to calculate the score which the user will get if he activates a notification.<br />
	 * Moreover the list <code>arraylist</code> of the type <code>PredictionEntry</code> is created and gets filled 
	 * with the data which should be displayed in the <code>ListView</code>.
	 * The <code>ListView</code> <code>predictionList</code> gets connected to the data contained in <code>arraylist</code> using an 
	 * instance of the inner class <code>CustomAdapter</code> in order to display <code>predictionList</code> to the user. 
	 */
	private void displayListView() {
		// The current point in time is retrieved
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(new Date());
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int min = calendar.get(Calendar.MINUTE);
		String time = hour + ":" + min;
				
		// Predictions and colors for the next 10 hours are requested and displayed in a list
		String[][] tmp = user.getPredictions(time);
		String[] predictions = new String[10];
		String[] amounts = new String[10];
		for(int i = 0; i < 10; i++) {
			predictions[i] = tmp[i][0];
			colors[i] = tmp[i][1];
			amounts[i] = tmp[i][2];
		}

		// A Predictionentry list is created, which enables to maintain the selection state of the list
		ArrayList<PredictionEntry> arraylist = new ArrayList<PredictionEntry>();
		for(int i = 0; i < 10; i++) {
			// When the list is created no entry is selected, therefore the second parameter is always false
			int id = i+1;
			arraylist.add(new PredictionEntry(predictions[i], false, id, Double.parseDouble(amounts[i])));
		}
		
		// connect data to the ListView using an instance of CustomAdapter
		dataAdapter = new CustomAdapter(this, R.layout.custom_list_layout, arraylist);
		predictionList = (ListView) findViewById(R.id.predictionList);
		predictionList.setAdapter(dataAdapter);
	}
	
	/**
	 * Conducts the user's logout from the application as well as from Twitter.<br />
	 * Moreover to log the user out from the application, the login-data is deleted from his device.
	 * To log out from Twitter the instance of <code>SharedPreferences</code> is edited and the Twitter Api keys are removed from it.
	 * As the user is not supposed to see this view, when he is not logged in, this activity is finished.
	 * As this activity is always called from <code>MainActivity</code>, this is restored and then automatically forwards to <code>LoginActivity</code>.
	 */
	private void logout(){
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
		
		// Terminate the instance of this activity
		finish();
	}
	
	/** 
	 * Creates a notification and activates an instance of AlarmManager in order to push the notification at a particular time.<br />
	 * This method creates an instance of <code>PendingIntent</code> in order to enable pushing the notification, 
	 * as well as an instance of <code>Intent</code> in order to call the class <code>MainActivity</code> when the user tabs on the notification.
	 * The notification is constructed by using an instance of <code>NotificationCompat.Builder</code>. 
	 * By using this class the compatibility of this method with all Android versions is ascertained.<br />
	 * Moreover, this method creates another intent in order to call the auxiliary class <code>NotificationPublisher</code> using the 
	 * above mentioned instance of <code>AlarmManager</code>.
	 * When activating the instance of <code>AlarmManager</code> the delay until the notification shows up is defined in milliseconds.
	 * 
	 * @param	amount	Predicted amount of green energy, necessary to calculate the increment of the user's score.
	 * @param	delay	Amount of milliseconds until the notification is pushed.
	 * @return			Notification which was created in this method because it is necessary in case the alarm gets canceled.
	 */
	public Notification createNotification(int delay, double amount) {
		// Define the score to be added
		int tmpscore;
		if(amount > UPPERLIMIT)	tmpscore = 5;
		else if(amount > LOWERLIMIT)	tmpscore = 3;
		else tmpscore = 0;
		String futurescore = tmpscore+"";
		
		// Creation of intent to call MainActivity and pending intent to push the notification
		Intent myIntent = new Intent(this, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this,  0,  myIntent,  Intent.FLAG_ACTIVITY_NEW_TASK);
		
		// Creation of notification itself
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		builder.setContentIntent(pendingIntent);
		builder.setContentTitle("Green Shifting Notification");
		builder.setContentText("for using Energy now!");
		if(tmpscore > 0) {
			builder.setTicker("Your score was increased ;)");
			builder.setSubText("Your score was increased ;)");
		}
		builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
		builder.setLights(Color.BLUE, 1000, 500);
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setAutoCancel(true);
		Notification notification = builder.build();
		
		// Creation of intent to class the class NotificationPublisher
		Intent notificationIntent = new Intent(this, NotificationPublisher.class);
		notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
		notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
		notificationIntent.putExtra(NotificationPublisher.NOTIFICATIONSCORE, futurescore);
		PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, 0);
		
		// Calculate the amount of milliseconds until the notification is pushed
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(new Date());
		Long wakeup = calendar.getTimeInMillis()+delay;
		
		// Creation of an AlarmManager instance and activation of the alarm
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, wakeup, pIntent);
		
		return notification;
	}
	
	/**
	 * Cancels the notification passed as parameter.<br />
	 * Moreover this method creates another intent which equals the intent (has the same extras) which was used to set the alarm. 
	 * By doing so the initially created alarm gets overwritten, then the newly created alarm is canceled. 
	 * This proceeding is necessary as we cannot access the alarm which was created in another method.
	 * 
	 * @param notification	Instance of <code>Notification</code> which is supposed to be canceled.
	 */
	public void cancelNotification(Notification notification) {
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
	    Intent notificationIntent = new Intent(this, NotificationPublisher.class);
	    notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
	    notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
	    notificationIntent.putExtra(NotificationPublisher.NOTIFICATIONSCORE, "-1");
	    PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, 0);
	    // Cancel alarm
	    try {
	        alarmManager.cancel(pendingIntent);
	        pendingIntent.cancel();
	    } catch (Exception e) {
	        Log.e("DetailActivity", "AlarmManager update was not canceled. " + e.toString());
	    }
	}
	
	/**
	 * Inner class to maintain a <code>ListView</code> with single choice mode.<br />
	 * This class gets data from an array list and converts each item into a view that's placed into the list.
	 * The array list has the data type <code>PredictionEntry</code> and contains each entry's label and its state (selected or not selected).<br />
	 * Moreover, this class represents a custom adapter for <code>ListView</code> instances and 
	 * is necessary to assign different background colors to the list's entries.
	 *
	 * @author	Veronika Henk, Sarvenaz Golchin, Mahnaz Hajibaba
	 * @version	1.3
	 * @since	2014-08-24
	 */
	private class CustomAdapter extends ArrayAdapter<PredictionEntry> {
		
		/**
		 * List with data for entries in the <code>ListView</code>
		 */
		private ArrayList<PredictionEntry> preList;
		
		/**
		 * Initiates an instance of CustomAdapter.<br />
		 * First, this constructor calls the constructor of <code>ArrayAdapter</code> and then assigns data to the <code>ArrayList</code> preList.
		 * 
		 * @param	context				Context of <code>DetailActivity</code>, which created an instance of <code>CustomAdapter</code>.
		 * @param	textViewResourceId	Id of the xml-resource which defines the custom layout of a single list item.
		 * @param	preList				Array list which was initialized and filled with data in <code>DetailActivity</code>.
		 */
		public CustomAdapter(Context context, int textViewResourceId, ArrayList<PredictionEntry> preList) {
			super(context, textViewResourceId, preList);
			this.preList = new ArrayList<PredictionEntry>();
			this.preList.addAll(preList);
		}

		/**
		 * Represents the data structure of a single list item.<br />
		 * An item consists of its linear layout, a text view, and a check box. 
		 * We need to access the linear layout of each entry to set its background color.
		 * The text view contains the entry's label and the check box consists of an alarm icon. 
		 * The appearance of the alarm icon changes depending on whether the check box is checked or not.
		 */
		private class ViewHolder {
			LinearLayout layout;
			TextView label;
			CheckBox alarm;
		}
		
		/**
		 * Gets the view of a list item and sets the appearance of this item in the list view.<br />
		 * In other words, this method maintains the appearance and the behavior of the list's entries. 
		 * It is called for every list item and returns the view itself.<br />
		 * In this method a custom layout is inflated in order to specify a root view and to prevent attachment to the root.<br />
		 * An instance of the private class <code>ViewHolder</code> is created to access the components of this list item.
		 * Furthermore, the actions which should be done when the check box changes its state, are defined.
		 * The background color of this list item is set and the entry's state (selected or not selected) is put into effect.
		 * 
		 * @param	position	Position of the requested entry within the adapter's data set.
		 * @param	convertView	The old view to reuse, if possible.
		 * @param	parent		The parent that this view will eventually be attached to.
		 * @return				The view corresponding to the data at the specified position.
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			
			// The view is initiated if it was not before
			if(convertView == null) {
				LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = vi.inflate(com.eis.greenenergy.main.R.layout.custom_list_layout, null);
				// Get access to the item's components
				holder = new ViewHolder();
				holder.layout = (LinearLayout) convertView.findViewById(com.eis.greenenergy.main.R.id.layoutentry);
				holder.label = (TextView) convertView.findViewById(com.eis.greenenergy.main.R.id.entrylabel);
				holder.alarm = (CheckBox) convertView.findViewById(com.eis.greenenergy.main.R.id.entrycheck);
				convertView.setTag(holder);
				
				holder.alarm.setOnClickListener(new View.OnClickListener() {
					/**
					 * Defines the behavior of the instance of <code>CheckBox</code>.<br />
					 * If the state of the item is now <code>selected</code> the method <code>createNotification()</code> is called in order to 
					 * initiate the notification and a <code>Toast</code> message is displayed to the user as confirmation.
					 * If the state of the item is now <code>not selected</code>, the alarm which was set before to initiate the notification,
					 * is now canceled and in this case also a <code>Toast</code> message is displayed to the user as confirmation.<br />
					 * Independent from the item's state the method <code>update()</code> is called to update the list view.
					 * 
					 * @param	v	View which triggers the actions of this method by being clicked. In this case it is the item's check box.	
					 */
					public void onClick(View v) {
						for(int i = 0; i < preList.size(); i++) {
							preList.get(i).setSelected(false);
						}
						
						CheckBox cb = (CheckBox) v;
						PredictionEntry entry = (PredictionEntry) cb.getTag();
						entry.setSelected(cb.isChecked());
						
						if(cb.isChecked()) {
							int hour = entry.getId();
							//For testing: int wait = hour * 5 * 1000;
							int wait = hour * 60 * 60 * 1000;
							notification = createNotification(wait, entry.getAmount());
							
							String msg;
							if(hour == 1)	msg = "You will be notified in 1 hour!";
							else	msg = "You will be notified in " + hour + " hours!";
							Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
						}
							
						else {
							// cancel all alarms:
							cancelNotification(notification);
							Toast.makeText(getApplicationContext(), "The notification was canceled!", Toast.LENGTH_SHORT).show();
						}
						
						// Update the list view
						update();
					}
				});
			}
			else {
				// If the view was already initiated, it is reused
				holder = (ViewHolder) convertView.getTag();
			}
			
			// Set the item's background color:
			for(int j = 0; j < 10; j++) {
				if(j == position) {
					String colorstring = colors[j];
					holder.layout.setBackgroundColor(Color.parseColor(colorstring));
				}
			}
			
			// The item's state is put into effect:
			PredictionEntry pe = preList.get(position);
			holder.label.setText(pe.getName());
			holder.alarm.setChecked(pe.isSelected());
			holder.alarm.setTag(pe);
			
			return convertView;
		}
		
		/**
		 * Calls the method <code>notifyDataSetChanged()</code> to initiate an update of the list view.
		 */
		public void update() {
			this.notifyDataSetChanged();
		}
	} // End of inner class
	
}