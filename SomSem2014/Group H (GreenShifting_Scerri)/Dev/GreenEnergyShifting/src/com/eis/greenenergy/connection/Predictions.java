package com.eis.greenenergy.connection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Class which defines an AsyncTask to access energy prediction data from an external database. <br />
 * Predictions about the green energy amount for the next 10 hours can be retrieved using this Async-task. 
 * The accomplishment of these tasks includes connecting to a PHP script which runs on the same server as the external database.
 *
 * @author 	Veronika Henk, Sarvenaz Golchin, Mahnaz Hajibaba
 * @version 1.1
 * @since 	2014-05-30
 */
public class Predictions extends AsyncTask<String,Void,String> {
	/**
	 * Current point of time.
	 */
	private String time;
	
	/**
	 * Dialog indicating to the user to wait until the data processing is done.
	 */
	private ProgressDialog pDialog;
	
	/**
	 * Context of the activity. In other words global information about the activity environment are stored in this object.
	 */
	private Context context;
	
	/**
	 * The context of the calling activity is assigned to the class variable <code>context</code>.
	 * 
	 * @param	context	context of the activity calling the Async-Task <code>Predictions</code>
	 */
	public Predictions(Context context) {
		this.context = context;
	}
	
	/**
	 * Defines what needs to be done before the network connection is established.<br />
	 * A progress dialog is created and displayed as an indication to the user to wait until the network request is completed.
	 */
	protected void onPreExecute(){ 
		super.onPreExecute();
	    pDialog = new ProgressDialog(context);
	    pDialog.setMessage("Loading...");
	    pDialog.setIndeterminate(false);
	    pDialog.setCancelable(false);
	    pDialog.show();
	}
	   
	/**
	 * Sends a request to a web service in order to receive energy data.<br />
	 * The predicted amount of green energy for the next 10 hours following the point of time given as input are retrieved.
	 * 
	 * @param	arg0[0] The current point of time in 24h-format.
	 * @return			String variable containing a <code>JSON</code>-array with the green energy data if accessing the external database was successful;<br />
	 * 					<code>-1</code> if no connection to the web service or to the database could be established.
	 */
	@Override
	protected String doInBackground(String... arg0) {
		try {
			// Assigning the input data to the class variables.
			time = (String)arg0[0];
			
			// Defining server address and parameters.
			String link="http://greenenergy.veronika-henk.de/getepredictions.php";
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
	 * The progress dialog, which was created earlier, is dismissed.
	 * 
	 * @param	result	Result of the operation conducted in the <code>doInBackground()</code> method.
	 */
   @Override
   protected void onPostExecute(String result){
	   // dismiss the dialog
       pDialog.dismiss();
   }
}