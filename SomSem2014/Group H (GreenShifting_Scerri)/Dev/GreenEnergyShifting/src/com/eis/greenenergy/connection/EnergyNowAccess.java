package com.eis.greenenergy.connection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import android.os.AsyncTask;

/**
 * Class which defines an AsyncTask to access energy data from an external database. <br />
 * By using this AsyncTask the current amount of green energy as well as predictions for the green energy amount in 1, 3, and 5 hours is retrieved.
 * The accomplishment of this task includes connecting to a PHP script which runs on the same server as the external database.
 *
 * @author 	Veronika Henk, Sarvenaz Golchin, Mahnaz Hajibaba
 * @version 1.2
 * @since 	2014-05-30
 */
public class EnergyNowAccess extends AsyncTask<String,Void,String> {
	/**
	 * Current point of time.
	 */
	String time;

	/**
	 * Defines what needs to be done before the network connection is established.
	 * For this Async-Task, we do not have tasks to be done beforehand, therefore the method is empty.
	 */
	protected void onPreExecute(){}
	   
	/**
	 * Sends a request to a web service in order to receive energy data.<br />
	 * Moreover, information about the current amount of green energy and the predicted amount for in 1, 3, and 5 hours is retrieved.
	 * 
	 * @param	arg0[0] The current point of time in 24h-format.
	 * @return			String variable containing a <code>JSON</code>-Array with the energy information if accessing the external database was successful;<br />
	 * 					<code>-1</code> if no connection to the web service or to the database could be established.
	 */
	@Override
	protected String doInBackground(String... arg0) {
		try {
			// Assigning the input data to the class variables.
			time = (String)arg0[0];
			
			// Defining server address and parameters.
			String link="http://greenenergy.veronika-henk.de/getenergynow.php";
			String data  = URLEncoder.encode("time", "UTF-8")  + "=" + URLEncoder.encode(time, "UTF-8");
	        URL url = new URL(link);
	        
	        // Establishment of network connection and sending request.
	        URLConnection conn = url.openConnection(); 
	        conn.setDoOutput(true); 
	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
	        wr.write(data); 
	        wr.flush(); 
	        
	        // Accessing the result by using a <code>BufferedReader</code>.
	        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	        while((line = reader.readLine()) != null) {
	           sb.append(line);
	           break;
	        }
	        // Returning the retrieved data.
	        return sb.toString();
		}catch(Exception e){
			// If an error occurred "-1" is returned.
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