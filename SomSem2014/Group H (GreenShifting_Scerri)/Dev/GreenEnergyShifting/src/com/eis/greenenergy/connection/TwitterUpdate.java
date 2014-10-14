package com.eis.greenenergy.connection;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Class which defines an AsyncTask for connecting to the Twitter Api.<br />
 * By connecting to the Twitter Api, sending tweets about the user's score is enabled and covered in<br /> this AsyncTask.
 *
 * @author	Veronika Henk, Sarvenaz Golchin, Mahnaz Hajibaba
 * @version	1.3
 * @since	2014-05-30
 */
public class TwitterUpdate extends AsyncTask<String, String, String> {
	
	/**
	 * Consumer key for accessing Twitter API.
	 */
    private static String TWITTER_CONSUMER_KEY;
    
    /**
	 * Consumer secret for accessing Twitter API.
	 */	
    private static String TWITTER_CONSUMER_SECRET;
    	
    /**
	 * Preference key for accessing Twitter API.
	 */	
    private static String PREF_KEY_OAUTH_TOKEN;
    	
    /**
	 * Verification secret for accessing Twitter API.
	 */	
    private static String PREF_KEY_OAUTH_SECRET;
    	
    /**
	 * Object to access and modify the user's preference data.
	 */	
    private static SharedPreferences mSharedPreferences;
    	
    /**
	 * Context of the activity. In other words global information about the activity environment are stored in this object.
	 */	
    private Context context;
    	
    /**
	 * Dialog indicating to the user to wait until the data processing is done.
	 */	
    private ProgressDialog pDialog;
    	
    /**
     * The context of the calling activity is assigned to the class variable <code>context</code>.<br />
     * The class variables <code>TWITTER_CONSUMER_KEY</code>, <code>TWITTER_CONSUMER_SECRET</code>, 
     * <code>PREF_KEY_OAUTH_TOKEN</code>, and <code>PREF_KEY_OAUTH_SECRET</code> 
     * are initialized with constant values from the class <code>UserManager</code>. 
     * Moreover the constructor assigns a <code>SharedPreferences</code> instance of the activity.<br />
     * 
     * @param context			Context of the activity calling the Async-Task <code>TwitterUpdate</code>.
     * @param consumerKey		Constant variable which is part of the Twitter Api key and necessary to send a request.
   	 * @param consumerSecret	Constant variable which is part of the Twitter Api key and necessary to send a request.
   	 * @param oauthToken		Constant variable which is part of the Twitter Api key and necessary to send a request.
   	 * @param oauthSecret		Constant variable which is part of the Twitter Api key and necessary to send a request.
   	 */
    public TwitterUpdate(Context context, String consumerKey, String consumerSecret, String oauthToken, String oauthSecret) {
    	this.context = context;
    	mSharedPreferences = context.getSharedPreferences("MyPref", 0);
    	TWITTER_CONSUMER_KEY = consumerKey;
    	TWITTER_CONSUMER_SECRET = consumerSecret;
    	PREF_KEY_OAUTH_TOKEN = oauthToken;
    	PREF_KEY_OAUTH_SECRET = oauthSecret;
    }
 
    /**
     * Defines what needs to be done before the network connection is established.<br />
     * A progress dialog is created and displayed as an indication to the user to wait until the network request is completed.
     */
    @Override
    protected void onPreExecute() {
    	super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Updating to twitter...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
	}
 
    /**
     * A connection to the Twitter Api is established in order to tweet the user's score.<br />
     * Moreover the constants containing the Twitter Api key are used to build a connection and 
     * the <code>SharedPreferences</code> instance is used to access the user's Twitter account.
     * The new status is posted using the Twitter Api.
     * 
     * 	@param	args[0]	Text of the tweet which should be posted on Twitter as status update<br />
     *  @return			<code>null</code> if the status update was successful;<br />
     *  				<code>-1</code> otherwise.
     */
    protected String doInBackground(String... args) {
    	String status = args[0];
        try {
        	ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
            builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);

            // Accessing the user's Twitter account
            String access_token = mSharedPreferences.getString(PREF_KEY_OAUTH_TOKEN, "");
            String access_token_secret = mSharedPreferences.getString(PREF_KEY_OAUTH_SECRET, "");
                 
            // Establishing connection
            AccessToken accessToken = new AccessToken(access_token, access_token_secret);
            Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);
                 
            // Update status
            twitter4j.Status response = twitter.updateStatus(status);
            Log.d("Status", "> " + response.getText());
        } catch (TwitterException e) {
            // Error in updating status
            Log.d("Twitter Update Error", e.getMessage());
            	return "-1";
        }
        return null;
    }
 
    /**
     * Defines what needs to be done after the network connection is closed.<br />
     * The progress dialog, which was created earlier, is dismissed.
     * 
     * @param	file_url	Result of the operation conducted in the <code>doInBackground()</code> method.
     */
    protected void onPostExecute(String file_url) {
    	// dismiss the dialog after getting all predictions
        pDialog.dismiss();
    }
}