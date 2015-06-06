package com.eis.greenenergy.help;

import java.io.*;

import org.json.JSONArray;
import org.json.JSONObject;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.content.Context;
import android.content.SharedPreferences;

import com.eis.greenenergy.connection.*;

/**
 * Very important auxiliary class, which manages the user's data, as well as the energy data which is displayed to the user.<br />
 * Moreover this class manages nearly the whole data structure of the application and controls the access to most Async-tasks. 
 *
 * @author	Veronika Henk, Sarvenaz Golchin, Mahnaz Hajibaba
 * @version	1.0
 * @since	2014-05-30
 */
public class UserManager {
	/**
	 * Context of the activity. In other words global information about the activity environment are stored in this object.
	 */
	private Context context;
	
	/**
	 * Name of the user who is logged in.
	 */
	private String username;
	
	/**
	 * Score of the user who is logged in.
	 */
	private String score;
	
	/**
	 * Indicates if the user is logged in, or not.
	 */
	private boolean loggedIn;
	
	/**
	 * Indicates if the user's score was increased recently. 
	 */
	private boolean increased;
	
	/**
	 * Object to access and modify the user's preference data.
	 */
	private static SharedPreferences mSharedPreferences;
	
	/**
	 * Message to be tweeted as status update if the user's score is still 0.
	 */
	private String FIRSTTWEET;
	
	/**
	 * Message to be tweeted as status update containing the user's score.
	 */
	private String TWEET;
	
	/**
	 * Consumer key for accessing Twitter API.
	 */
	private static String TWITTER_CONSUMER_KEY = "RhHrpBzuvGWK5Sg4za5JsTSwQ";
	
	/**
	 * Consumer secret for accessing Twitter API.
	 */
    private static String TWITTER_CONSUMER_SECRET = "K51dsve9JyorbGe0TFhaeLxS4p2miRCw7aJXzQ8xKJr2VP6DuX";
    
    /**
	 * Preference key for accessing Twitter API.
	 */
    private static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
    
    /**
	 * Verification secret for accessing Twitter API.
	 */
    private static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
    
    /**
	 * Preference key for logging into Twitter.
	 */
    private static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn";
    
    /**
	 * Callback URL for accessing Twitter API.
	 */
    private static final String TWITTER_CALLBACK_URL = "oauth://t4jsample";
    
    /**
	 * URL verifier for accessing Twitter API.
	 */
    private static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
	
    /**
     * In this constructor the context of the calling activity is assigned to the class variable <code>context</code>.<br />
     * The method <code>isloggedin()</code> is called and its result is assigned to the boolean variable <code>loggedIn</code>. 
     * Moreover the constructor assigns a <code>SharedPreferences</code> instance of the activity.<br />
     * The boolean class variable <code>increased</code> represents whether the user increased his score; 
     * as the score has not increased when the constructor is called, the value <code>false</code> is assigned to it.<br />
     * Furthermore, messages are assigned to the class variables <code>TWEET</code> and <code>FIRSTTWEET</code>, 
     * which represent the tweets which can be published on Twitter using this application.
     * 
     * @param context	Context of the activity creating an instance of <code>UserManager</code>.
     */
	public UserManager(Context context) {
		this.context = context;
		loggedIn = isloggedin();
		mSharedPreferences = context.getSharedPreferences("MyPref", 0);
		increased = false;
		
		// Status update messages
		TWEET = "I increased my #GreenEnergy Shifting App score! My new score is: " + score + " ;)";
		FIRSTTWEET = "I just started using #GreenEnergy Shifting App to support #RenewableEnergies!";
	}
	
	/**
	 * Returns a boolean value to express whether the user is already logged in.<br />
	 * Moreover, this function checks whether the user's data is already stored on the device. 
	 * Using the Async-Task <code>SyncScore</code> the user data stored on the device is 
	 * verified by comparing it to the user data stored on the external database.
	 * Furthermore the Async-Task <code>SyncScore</code> returns the current score of the 
	 * user if it can be accessed successfully.
	 * Within this method this score is assigned to the class variable <code>score</code>.
	 * 
	 * @return	<code>true</code> if the user's data was verified successfully and his score was accessed;<br />
	 * 			<code>false</code> otherwise.
	 */
	private boolean isloggedin() {
		String password;
		try {
			/*
			 * The files "username" and "password" are accessed.
			 * If those files are stored on the device, their content is stored in 
			 * the class variable "username" and in the class variable "password".
			 */
			FileInputStream fis = context.openFileInput("username");
			InputStreamReader in = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(in);
			username = br.readLine();
			fis.close();
			
			FileInputStream fis2 = context.openFileInput("password");
			InputStreamReader in2 = new InputStreamReader(fis2);
			BufferedReader br2 = new BufferedReader(in2);
			password = br2.readLine();
			fis2.close();
			
			/*
			 * An instance of the SyncScore Async-Task is created to verify the login-data stored on the user's device and to access the score.
			 * The Async-Task is executed with the parameters username and password.
			 */
			SyncScore syncscore = new SyncScore();
			syncscore.execute(username, password);
			try {
				score = syncscore.get();
			} catch(Exception e) {
				/*
				 *  If something went wrong, the error code "-1" is assigned to represent that 
				 *  the user's data was not verified successfully.
				 */
				score = "-1"; 
			}
			/*
			 * Value of variable result is converted to a boolean value: true if the login was successful; false otherwise.
			 */
			if(!score.equals("-1"))	return true;
			else	return false;
		} catch(Exception e) {
			// If something else went wrong false is returned, as the user is then not logged in.
			return false;
		}
	}
	
	/**
	 * Returns a boolean value to indicate whether the user is already logged into Twitter or not.
	 * 
	 * @return	<code>true</code> if the twitter login status in SharedPreferences is <code>true</code>;<br />
	 * 			<code>false</code> otherwise.
	 */
	public boolean isTwitterLoggedInAlready() {
		// return twitter login status from Shared Preferences
        return mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);
	}
	
	/**
	 * Logs the user into Twitter if he is not logged in already.
	 * If the user is already logged in and if his score is not <code>0</code>, his score is published on Twitter.
	 * Otherwise if the the user is already logged in and he does not have any score, a message is published on Twitter, which is not mentioning the score.
	 * 
	 * @return	<code>Twitter</code> variable if the user was not logged in before;<br />
	 * 			<code>null</code> if the user was logged in already.
	 */
    public Twitter loginToTwitter() {
        /*
         * If the user was not already logged into Twitter, a new Twitter instance is created by using 
         * consumer key and consumer secret and connecting with Twitter Api.
         */
        if (!isTwitterLoggedInAlready()) {
            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
            builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
            Configuration configuration = builder.build();
            TwitterFactory factory = new TwitterFactory(configuration);
            Twitter twitter = factory.getInstance();
            return twitter;
        } 
        /*
         * If the user was already logged into Twitter, the user's Twitter status is updated.
         * If the user's score is not "0" his score is tweeted; otherwise the status update says that the user just started using the app.
         * The status update is accomplished by using the Async-Task TwitterUpdate.
         */
        else {
            TwitterUpdate tu = new TwitterUpdate(context, TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET, PREF_KEY_OAUTH_TOKEN, PREF_KEY_OAUTH_SECRET);
            // Choose correct status
            String status = "";
            if(score.equals("0"))	status = FIRSTTWEET;
            else	status = TWEET;
            tu.execute(status);
            try {
            	tu.get();
            	// If the user's Twitter status was updated successfully, his score is increased and true is assigned to the variable <code>increased</code>.
            	increased = true;
            } catch(Exception e) {}
        	return null;
        }
    }
    
    /**
     * Updates the user's score in the external database, using the AsyncTask ScoreStorage.
     * 
     * @param	newscore	Updated number of the user's score.
     * @return				<code>true</code> if the score value was updated successfully;<br />
     * 						<code>false</code> otherwise.
     */
	public boolean writeScore(int newscore) {
		/*
		 * An instance of the ScoreStorage Async-Task is created to upload the new score value.
		 * The Async-Task is executed with the parameters inputscore, which is newscore converted to a string, and username.
		 */
		String inputscore = ""+newscore;
		ScoreStorage storage = new ScoreStorage();
		storage.execute(inputscore, username);
		
		String tmpdata = "";
		try {
			tmpdata = storage.get();
			/*
			 * If the result of the Async-Task is not "-1", updating the data set was successful.
			 */
			if(!tmpdata.equals("-1")) {
				try {
					// The new score is stored in the file "score" on the user's device.
					FileOutputStream fos = context.openFileOutput("score", Context.MODE_PRIVATE);
					fos.write(tmpdata.getBytes());
					fos.close();
					this.score = newscore+"";
					}	catch(Exception e) {
						return false;
					}	
			}
		} catch(Exception e) {
			return false;
		}
		// The result of the Async-Task is transformed into a boolean value.
		if(tmpdata.equals("0"))	return true;
		else	return false;
	}
	
	/**
	 * Indicates whether the user's score has increased in order to display a <code>Toast</code> message.
	 * 
	 * @return	Boolean variable <code>increased</code>, which is <code>true</code> if the user's score has increased.
	 */
	public boolean scoreincreased() {
		return increased;
	}

	/**
	 * Retrieves energy data necessary for the application's main view using the Async-Task <code>EnergyNowAccess</code>.
	 * 
	 * @param	time	Current point of time.
	 * @return			<code>null</code> if the data could not be accessed successfully;<br />
	 * 					otherwise the string array <code>energydata[]</code>, which contains the accessed energy data itself.<br />
	 * 					In detail, <code>energydata[0]</code> = current energy amount,<br />
	 * 					<code>energydata[1]</code> = prediction for energy amount in 1 hour,<br />
	 * 					<code>energydata[2]</code> = prediction for energy amount in 3 hours,<br />
	 * 					<code>energydata[3]</code> = prediction for energy amount in 4 hours.
	 */
	public String[] accessdata(String time) {
		/*
		 * An instance of the EnergyNowAccess Async-Task is created to access energy data from the external database.
		 * The Async-Task is executed with the parameter time.
		 */
		EnergyNowAccess ea = new EnergyNowAccess();
		ea.execute(time);
		String jsonstring="";
		String[] energydata = new String[4];
		try {
			jsonstring = ea.get();
			if (jsonstring != null) {
                try {
                	// The result of the Async-Task is a JSON-object
                    JSONObject jsonObj = new JSONObject(jsonstring);
                     
                    // The object contains the energy data in form of an array
                    JSONArray energy = jsonObj.getJSONArray("EData");
                   
                    // Assigning the energy data to the array energydata[]
                    for (int i = 0; i < energy.length(); i++) {
                       JSONObject edata = energy.getJSONObject(i);
                       energydata[i] = edata.getString("percentage");
                    }
                } catch(Exception e) {
                	// If something goes wrong, null is returned
                	return null;
                }
			}
		} catch(Exception e) {
			// If something goes wrong, null is returned
			return null;
		}
		// If the access was successful, the array is returned
		return energydata;
	}
	
	/**
	 * Gets the energy data predictions for the next 10 hours following <code>time</code>.<br />
	 * This method accesses predictions for the amount of green energy using the Async-Task <code>Predictions</code>.
	 * Depending on the amount of green energy for each hour, a color is assigned. 
	 * Color, percentage, and formatted percentage are stored in the 2-dimensional array <code>predictionentry</code>.<br />
	 * In other words, this method generates entries for the ListView, which is displayed on the detail view of this application.
	 * 
	 * @param	time	Current point of time.
	 * @return			<code>null</code> if something went wrong accessing the data;<br />
	 * 					otherwise 2-dimensional string array with 10 entries,<br />
	 * 					with <code>predictionentry[i][0]</code> = formatted energy prediction data for the next 10 hours,<br />
	 * 					<code>predictionentry[i][1]</code> = background color for this entry depending on the green energy amount,<br />
	 * 					<code>predictionentry[i][2]</code> = energy prediction data without formatting, so that it can be parsed into a <code>double</code>-variable.
	 */
	public String[][] getPredictions(String time) {
		Predictions pd = new Predictions(context);
		pd.execute(time);
		String jsonstring="";
		// predictionentry[i][0] = percentage, predictionentry[i][1] = color, predictionentry[i][2] = unformatted percentage
		String[][] predictionentry = new String[10][3];
		try {
			jsonstring = pd.get();
			if (jsonstring != null) {
                try {
                	// The result of the Async-Task is a JSON-object
                    JSONObject jsonObj = new JSONObject(jsonstring);
                     
                    // The object contains the energy data in form of an array
                    JSONArray energy = jsonObj.getJSONArray("PData");
                   
                    for (int i = 0; i < energy.length(); i++) {
                       	JSONObject edata = energy.getJSONObject(i);
                       
                       	int htmp = i+1;
                       	// Assigning color to the array predictionentry[i][1] and percentage to the array predictionentry[i][2]
           				String predtmp = edata.getString("percentage");
           				String pcolor = edata.getString("color");
           				predictionentry[i][1] = pcolor;
           				predictionentry[i][2] = predtmp;
           				
           				if(predtmp != null) {
           					// Formatting the percentage data
           					double tmp = Double.parseDouble(predtmp);
           					if(tmp < 10) {
           						predtmp = "  " + predtmp;
           						if(predtmp.length() == 5)	predtmp = predtmp + "0";
           						predtmp = predtmp + " %";
           					}
           					else	{
           						if(predtmp.length() == 4)	predtmp = predtmp + "0";
           						predtmp = predtmp + " %";
           					}
           					
           					// Formatting the description
           					String label;
           					if(i == 0)	label = "In 1 hour     :     ";
           					else if(i < 9)	label = "In " + htmp + " hours   :     ";
           					else	label = "In " + htmp + " hours :     ";

           					// Assigning the prediction data to the array predictionentry[i][0]
           					predictionentry[i][0] = label + predtmp;
           				}
           				else {
           					// If something went wrong while accessing the prediction data, an empty entry is stored in the array
           					predictionentry[i][0] = "";
           				}
                   }
                } catch(Exception e) {
                	// If an exception is thrown, null is returned
                	return null;
                }
			}
		} catch(Exception e) {
			return null;
		}
		// The array is returned in order to display it in a ListView on DetailActivity
		return predictionentry;
	}
	
	/**
	 * Indicates if the user is logged in, or not.<br />
	 * Delivers the result of the method <code>isloggedin()</code>, which was called from the constructor of this class.
	 * 
	 * @return	Class variable <code>loggedIn</code>.
	 */
	public boolean getLoggedIn() {
		return loggedIn;
	}
	
	/**
	 * Delivers the username, which was retrieved in the method <code>isloggedin()</code>.
	 * 
	 * @return	Class variable <code>username</code>.
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Delivers the score, which was retrieved in the method <code>isloggedin()</code>.
	 * 
	 * @return Class variable <code>score</code>.
	 */
	public String getScore() {
		return score;
	}
	
	/**
	 * Enables to access the callback URL from the class <code>MainActivity</code>.
	 * 
	 * @return	Constant variable <code>TWITTER_CALLBACK_URL</code>.
	 */
	public String getCallbackURL() {
		return TWITTER_CALLBACK_URL;
	}
	
	/**
	 * Enables to access the Twitter login preference from the class <code>MainActivity</code>.
	 * 
	 * @return	Constant variable <code>PREF_KEY_TWITTER_LOGIN</code>.
	 */
	public String getPrefKeyTwitterLogin() {
		return PREF_KEY_TWITTER_LOGIN;
	}
	
	/**
	 * Enables to access the Twitter authentication token from the class <code>MainActivity</code>.
	 * 
	 * @return	Constant variable <code>PREF_KEY_OAUTH_TOKEN</code>.
	 */
	public String getPrefKeyOauthToken() {
		return PREF_KEY_OAUTH_TOKEN;
	}
	
	/**
	 * Enables to access the Twitter authentication secret from the class <code>MainActivity</code>.
	 * 
	 * @return	Constant variable <code>PREF_KEY_OAUTH_SECRET</code>.
	 */
	public String getPrefKeyOauthSecret() {
		return PREF_KEY_OAUTH_SECRET;
	}
	
	/**
	 * Enables to access the Twitter authentication verifier from the class <code>MainActivity</code>.
	 * 
	 * @return	Constant variable <code>URL_TWITTER_OAUTH_VERIFIER</code>.
	 */
	public String getTwitterOauthVerfifier() {
		return URL_TWITTER_OAUTH_VERIFIER;
	}
}
