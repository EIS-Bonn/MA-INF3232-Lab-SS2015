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

/**
 * Class which defines an AsyncTask to access the user-login-data stored in an external database. <br /> 
 * The purpose of this task is to verify user data, which was retrieved within the application, by <br />
 * comparing it to user data which is stored in the database. In short, this task represents the login of <br />
 * the user on data level. The accomplishment of this task includes connecting to a PHP script which<br /> 
 * runs on the same server as the external database.
 *
 * @author	Veronika Henk, Sarvenaz Golchin, Mahnaz Hajibaba
 * @version	1.2
 * @since	2014-05-30
 */
public class Login extends AsyncTask<String,Void,String> {
	
	/**
	 * Name of the user who wants to log in.
	 */
	String username;
	
	/**
	 * Encrypted password the user inserted in order to log in.
	 */
	String password;
	
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
	 * @param	context	Context of the activity calling the Async-Task <code>Login</code>.
	 */
	public Login(Context context) {
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
	 * Sends a request with user data to a web service in order to verify the data.<br />
	 * The web services compares the sent data with the user data stored in the external database.
	 * 
	 * @param	arg0[0] Username, which was retrieved as the user's input.
	 * @param	arg0[1] Password, which was retrieved as the user's input.
	 * @return 			Result of the network request, which is <code>0</code> if the verification was successful;<br />
	 * 					<code>1</code> if no connection to the web service could be established;<br />
	 * 					<code>2</code> if the username does not exist in the database;<br />
	 * 					<code>3</code> if the password does not match the password stored in the database.
	 */
	@Override
	protected String doInBackground(String... arg0) {
		try {
			// Assigning the input data to class variables
			username = (String)arg0[0];
			password = (String)arg0[1];
			
			// Defining server address and parameters
			String link="http://greenenergy.veronika-henk.de/login.php";
			String data  = URLEncoder.encode("username", "UTF-8")  + "=" + URLEncoder.encode(username, "UTF-8");
	        data += "&" + URLEncoder.encode("password", "UTF-8")  + "=" + URLEncoder.encode(password, "UTF-8");
	        URL url = new URL(link);
	        
	        // Establishment of network connection and sending of request
	        URLConnection conn = url.openConnection(); 
	        conn.setDoOutput(true); 
	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
	        wr.write( data ); 
	        wr.flush(); 
	        
	        // Accessing the result using a <code>BufferedReader</code>
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
			// If no connection to the web service could be established, <code>1</code> is returned.
			return "1";
		}
	}
   
	/**
	 * Defines what needs to be done after the network connection is closed.<br />
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