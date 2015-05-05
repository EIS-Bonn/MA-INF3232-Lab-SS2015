package com.eis.greenenergy.help;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Auxiliary class to check if the user's internet connection is available.<br /> 
 * This class simplifies to conduct this check in several different views.
 *
 * @author	Veronika Henk, Sarvenaz Golchin, Mahnaz Hajibaba
 * @version	1.0
 * @since	2014-05-30
 */
public class ConnectionDetector {
  
	/**
	 * Context of the activity. In other words global information about the activity environment are stored in this object.
	 */
    private Context _context;
    
    /**
     * The context of the calling activity is assigned to the class variable <code>_context</code>.
     * 
     * @param	context	Context of the activity creating an instance of <code>ConnectionDetector</code>.
     */
    public ConnectionDetector(Context context){
        this._context = context;
    }

    /**
     * Returns a boolean value indicating of the device is connected to the internet.<br />
     * To do so this function checks for all possible internet providers.
     * 
     * @return	<code>true</code> if the connection to a network was detected;<br />
     * 			<code>false</code> otherwise.
     */
    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
        	NetworkInfo[] info = connectivity.getAllNetworkInfo();
        	if (info != null) {
        		for (int i = 0; i < info.length; i++) {
        			if (info[i].getState() == NetworkInfo.State.CONNECTED)
        				return true;
        		}
        	}
        }
        return false;
    }
}