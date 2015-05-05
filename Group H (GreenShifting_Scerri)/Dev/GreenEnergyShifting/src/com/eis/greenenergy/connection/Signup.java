package com.eis.greenenergy.connection;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Class which define an AsyncTask to insert new user data into an external database. <br />
 * Moreover the purpose of this task is to check if the data given by the user does not violate any<br />
 * constraints before inserting it. The accomplishment of this task includes connecting to a PHP script<br />
 * which runs on the same server as the external database.
 *
 * @author	Veronika Henk, Sarvenaz Golchin, Mahnaz Hajibaba
 * @version	1.1
 * @since	2014-05-30
 */
public class Signup  extends AsyncTask<String,Void,String> {
	
	/**
	 * Username inserted by the user who wants to sign up.
	 */
	String username;
	
	/**
	 * Encrypted password the user inserted in the <code>Signup</code>-view.
	 */
	String password;
	
	/**
	 * Context of the activity. In other words global information about the activity environment are stored in this object.
	 */
	private Context context;
	
	/**
	 * Dialog indicating to the user to wait until the data processing is done.
	 */
	private ProgressDialog pDialog;
   
   /**
    * The context of the calling activity is assigned to the class variable <code>context</code>.
    * 
    * @param	context	context of the activity calling the Async-Task <code>Signup</code>
    */
   public Signup(Context context) {
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
    * Stores the login-data of a new user in the external database.<br />
    * Moreover the connection to a web service is established in order to insert the username and the encrypted password in the database.
    * By using the web service it is also checked if the login-data violates the constraints it underlies.
    * 
    * @param	arg0[0]	Username, which was inserted by the user on the signup-view and retrieved through an instance of <code>UserManager</code>.
    * @param	arg0[1]	Password, which was inserted by the user on the signup-view and retrieved through an instance of <code>UserManager</code>.
    * @return			<code>0</code> if storing the new data was successful;<br />
    * 					<code>1</code> if no connection to the web service could be established;<br />
    * 					<code>2</code> if the username does already exist in the database;<br />
    * 					<code>-1</code> if an unexpected error occurred.
    */
   @Override
   protected String doInBackground(String... arg0) {
	   try {
		   // accessing the data inserted by the user
		   username = (String)arg0[0];
           password = (String)arg0[1];
           
           // defining server address and parameters
           String link="http://greenenergy.veronika-henk.de/signup.php";
           String data  = URLEncoder.encode("username", "UTF-8")  + "=" + URLEncoder.encode(username, "UTF-8");
           data += "&" + URLEncoder.encode("password", "UTF-8")  + "=" + URLEncoder.encode(password, "UTF-8");
           URL url = new URL(link);
           
           // establishment of network connection and sending request
           URLConnection conn = url.openConnection(); 
           conn.setDoOutput(true); 
           OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
           wr.write( data ); 
           wr.flush(); 
           
           // using a buffered reader to access the result
           BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
           StringBuilder sb = new StringBuilder();
           String line = null;
           // read Server response
           while((line = reader.readLine()) != null) {
              sb.append(line);
              break;
           }
           // returning (error-)code
           return sb.toString();
	   }catch(Exception e){
		   // if an error occurred while establishing the connection the responding errorcode "1" is returned
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
