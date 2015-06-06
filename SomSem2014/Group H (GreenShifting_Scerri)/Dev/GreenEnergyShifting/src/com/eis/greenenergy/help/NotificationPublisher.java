package com.eis.greenenergy.help;
 
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Auxiliary class to push the notification once it is alerted by the <code>AlarmManager</code>.<br />
 * As every instance of this class is a receiver of broadcasts, it was registered as such in <code>AndroidManifest.xml</code>.
 *
 * @author	Veronika Henk, Sarvenaz Golchin, Mahnaz Hajibaba
 * @version	1.3
 * @since	2014-08-25
 */
public class NotificationPublisher extends BroadcastReceiver {
 
	/**
	 * Id of the notification to be displayed to the user.
	 */
	public static String NOTIFICATION_ID = "notification-id";
	
	/**
	 * Notification to be displayed to the user.
	 */
	public static String NOTIFICATION = "notification";
	
	/**
	 * Amount which is added to the user's score when the notification is displayed.
	 */
	public static String NOTIFICATIONSCORE = "notification-score";
	
	/**
	 * <code>Builder</code> which is necessary to push the notification.
	 */
	public static String NOTIFICATIONBUILDER = "notification-builder";

	/**
	 * Is called when an instance of this class receives an intent sent by <code>sendBroadcast()</code>.<br />
	 * The notification created earlier is now accessed and pushed. In other words, this notification is now displayed to the user.<br />
	 * Also, if the predicted green energy amount is above 27%, the user's score is increased.
	 * 
	 * @param	context	Context of the application to enable the initiation of an instance of <code>NotificationManager</code>.
	 * @param	intent	Intent, which contains the notification itself and was sent from the method <code>createNotification</code>.
	 */
	public void onReceive(Context context, Intent intent) {
		// Get the notification from the intent
		NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = intent.getParcelableExtra(NOTIFICATION);
		int id = intent.getIntExtra(NOTIFICATION_ID, 0);
		
		// Write the user's increased score into the database
		String newscore = intent.getStringExtra(NOTIFICATIONSCORE);
		UserManager user = new UserManager(context);
		if(user.getLoggedIn()) {
			String score = user.getScore();
			int scoreint = Integer.parseInt(score);
			int tmp = Integer.parseInt(newscore);
			int futurescore = scoreint + tmp;
			user.writeScore(futurescore);
		}
		
		// Push the notification
		notificationManager.notify(id, notification);
    }
}