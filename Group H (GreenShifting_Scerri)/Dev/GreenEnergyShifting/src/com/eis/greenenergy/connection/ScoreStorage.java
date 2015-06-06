package com.eis.greenenergy.connection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import android.os.AsyncTask;

/**
 * Class which defines an AsyncTask to update the score value of a user in an external database. <br />
 * The accomplishment of this task includes connecting to a PHP script which runs on the same server<br />
 * as the external database.
 *
 * @author	Veronika Henk, Sarvenaz Golchin, Mahnaz Hajibaba
 * @version	1.2
 * @since	2014-05-30
 */
public class ScoreStorage extends AsyncTask<String,Void,String> {
	
	/**
	 * Updated amount of scores the user how now.
	 */
	String newscore;
	
	/**
	 * Name of the user who is logged in.
	 */
	String username;
	
	/**
	 * Defines what needs to be done before the network connection is established.
	 * For this Async-Task, we do not have tasks to be done beforehand, therefore the method is empty.
	 */
	protected void onPreExecute(){ }
	   
	/**
	 * Stores the updated score of the user in the external database.<br />
	 * In detail the data set corresponding to this user is selected using the parameter <code>username</code>. 
	 * This data set is then updated by editing the user's score.
	 * 
	 * @param	arg0[0]	The current score of the user, which was just increased.
	 * @param	arg0[1]	The username of the calling <code>UserManager</code> instance.
	 * @return			<code>0</code> if updating the score was successful;<br />
	 * 					<code>-1</code> if no connection to the web service or to the external database could be established.
	 */
	@Override
	protected String doInBackground(String... arg0) {
		try {
			// Assigning the input data to class variables.
			newscore = (String)arg0[0];
			username = (String)arg0[1];
			
			// Defining server address and parameters
			String link="http://greenenergy.veronika-henk.de/setscore.php";
			String data  = URLEncoder.encode("newscore", "UTF-8")  + "=" + URLEncoder.encode(newscore, "UTF-8");
			data += "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
	        URL url = new URL(link);
	        
	        // Establishment of network connection and sending of request
	        URLConnection conn = url.openConnection(); 
	        conn.setDoOutput(true); 
	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
	        wr.write( data ); 
	        wr.flush(); 
	        
	        // Access the result using a <code>BufferedReader</code>
	        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	        while((line = reader.readLine()) != null) {
	           sb.append(line);
	           break;
	        }
	        
	        // Retrieving the result of the web service
	        return sb.toString();
		}catch(Exception e){
			// If no connection could be established, the errorcode "-1" is returned.
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