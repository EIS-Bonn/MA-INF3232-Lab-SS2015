package com.eis.greenenergy.main;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.appwidget.*;
import android.widget.*;

import com.eis.greenenergy.help.UserManager;

/**
 * Class to display and manage the behavior of the application's widget.
 *
 * @author	Veronika Henk, Sarvenaz Golchin, Mahnaz Hajibaba
 * @version	2.2
 * @since	2014-08-11
 */
public class WidgetProvider extends AppWidgetProvider {
	/**
	 * Representation of the user who is currently logged in.
	 */
	UserManager user;
	
	/**
	 * Upper green energy limit. Influences the amount of scores a user gets for tweeting and setting notifications.
	 */
	private double UPPERLIMIT = 60;
	
	/**
	 * Constant which represents the widget refreshing action.
	 */
	public static String ACTION_WIDGET_REFRESH = "ActionReceiverRefresh";

	/**
	 * Initiates the widget layout and functionality.<br />
	 * In this method the current point of time is accessed in order to display it to the user as the last instance when the widget was updated.<br />
	 * Moreover, the current amount of green energy is retrieved to display the corresponding background color and bar chart on the widget.
	 * Two intents are created within this method. 
	 * One of them is added to the widget layout itself and causes the application to launch when the user tabs on the widget.
	 * The other intent is added to the widget's refresh button and implicitly causes this method to be called again.
	 * 
	 * @param	context				Context of this instance of the widget.
	 * @param	appWidgetManager	Object to manage and update the widget's state.
	 * @param	appWidgetIds		The appWidgetIds for which an update is needed.
	 */
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        user = new UserManager(context);

        // Perform this loop procedure for each Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];
            
            // Get current point of time
            Calendar calendar = GregorianCalendar.getInstance();
    		calendar.setTime(new Date());
    		int hour = calendar.get(Calendar.HOUR_OF_DAY);
    		int min = calendar.get(Calendar.MINUTE);
    		
    		// Format time
    		String smin, shour;
    		if(hour <= 9)	shour = "0"+hour;
    		else	shour = hour+"";
    		if(min <= 9)	smin = "0"+min;
    		else smin = min+"";
    		String time = shour + ":" + smin;
    		
    		// Retrieve current amount of green energy
    		String ptmp = user.accessdata(time)[0];
            double percentage = Double.parseDouble(ptmp);
    		
            // Create an intent to launch the application
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // Create an intent to refresh the widget
            Intent active = new Intent(context, WidgetProvider.class);
            active.setAction(ACTION_WIDGET_REFRESH);
            PendingIntent actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);
            
            // Get the widget-layout, connect refresh button to the intent active and connect the widget layout to the intent pendingIntent
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            views.setOnClickPendingIntent(R.id.refresh, actionPendingIntent);
            views.setOnClickPendingIntent(R.id.widget, pendingIntent);
            views.setTextViewText(R.id.text3, "Updated:"+time);
            
            // Set correct background
            if(percentage < UPPERLIMIT) 
            	views.setInt(R.id.widget, "setBackgroundResource", R.drawable.roundborder_yellow);
         	else 
         		views.setInt(R.id.widget, "setBackgroundResource", R.drawable.roundborder);
         
            // Set correct chart
            if(percentage >= 76)	views.setInt(R.id.barchart, "setImageResource", R.drawable.barchart1);
            else if(percentage >= 60)	views.setInt(R.id.barchart, "setImageResource", R.drawable.barchart2);
            else if(percentage >= 41)	views.setInt(R.id.barchart, "setImageResource", R.drawable.barchart3);
            else if(percentage >= 25)	views.setInt(R.id.barchart, "setImageResource", R.drawable.barchart4);
            else	views.setInt(R.id.barchart, "setImageResource", R.drawable.barchart5);
            
            // Cause the AppWidgetManager to perform an update on the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
    
    /**
     * Calls the method <code>onUpdate(context, appWidgetManager, appWidgetIds)</code>.<br />
     * This method represents a possibility to call the method which is necessary to refresh the widget's settings from within the <code>WidgetProvider</code> class.
     * 
     * @param context	Context of this instance of the widget.
     */
    private void onUpdate(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisAppWidgetComponentName = new ComponentName(context.getPackageName(),getClass().getName());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidgetComponentName);
        onUpdate(context, appWidgetManager, appWidgetIds);
    }

    /**
     * Initiates refreshing the widget.<br />
     * This method is called when the refresh button is clicked. It then calls the method <code>onUpdate(context)</code> and the settings are reprocessed.
     * 
     * @param	context	Context of this instance of the widget.
     * @param	intent	Intent which triggered the call of this method.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_WIDGET_REFRESH)) {
           onUpdate(context);
        } 
        else {
            super.onReceive(context, intent);
        }
    }
}