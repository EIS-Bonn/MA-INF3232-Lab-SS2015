package com.eis.greenenergy.connection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import android.os.AsyncTask;

/**
 * Class which defines an AsyncTask to access the score value of a user stored in an external database. <br />
 * The score data is accessed in order to synchronize it with the data stored locally on the user's device. <br />
 * The accomplishment of this task includes connecting to a PHP script which runs on the same server<br />
 * as the external database.
 *
 * @author	Veronika Henk, Sarvenaz Golchin, Mahnaz Hajibaba
 * @version	1.1
 * @since	2014-05-30
 */
public class SyncScore extends AsyncTask<String,Void,String> {
	/**
	 * Name of the user who is logged in.
	 */
	String username;
	
	/**
	 * Encrypted password of the user who is currently logged in.
	 */
	String password;

	/**
	 * Defines what needs to be done before the network connection is established.
	 * For this Async-Task, we do not have tasks to be done beforehand, therefore the method is empty.
	 */
	protected void onPreExecute(){}
	
	/**
	 * Sends a request with user data to a web service in order to verify the data.<br />
	 * The web services compares the sent data with the user data stored in the external database
	 * and retrieves the latest score which corresponds to the given <code>username</code>.<br />
	 * 
	 * @param	arg0[0] Username to select corresponding data set in the database
	 * @param	arg0[1]	Password to verify the userdata
	 * @return			Result of the network request which is either the score from the database, or -1.
	 */
	@Override
	protected String doInBackground(String... arg0) {
		try {
			username = (String)arg0[0];
			password = (String)arg0[1];
			
			// Defining server address and parameters
	        String link="http://greenenergy.veronika-henk.de/getscore.php";
	        String data  = URLEncoder.encode("username", "UTF-8")  + "=" + URLEncoder.encode(username, "UTF-8");
	        data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
	        URL url = new URL(link);
	        
	        // Establishment of network connection and sending of request
	        URLConnection conn = url.openConnection(); 
	        conn.setDoOutput(true); 
	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
	        wr.write( data ); 
	        wr.flush(); 
	        
	        // Access the result by using a BufferedReader
	        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	        while((line = reader.readLine()) != null) {
	           sb.append(line);
	           break;
	        }
	        // Returning the result of the web service
	        return sb.toString();
		}catch(Exception e){
			// If no connection could be established "-1" is returned
			return "-1";
		}
	}

	/**
	 * Defines what needs to be done after the network connection is closed.
	 * For this Async-Task, we do not have tasks to be done afterwards, therefore the method is empty.
	 */
   @Override
   protected void onPostExecute(String result){}
}

