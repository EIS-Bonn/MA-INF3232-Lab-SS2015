package com.eis.greenenergy.help;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Auxiliary class to simplify the creation of Alert Dialogs.
 *
 * @author	Veronika Henk, Sarvenaz Golchin, Mahnaz Hajibaba
 * @version	1.0
 * @see		AlertDialog
 * @since 	2014-05-30
 */
public class AlertDialogManager {
    /**
     * Displays a simple Alert Dialog.
     * 
     * @param	context	Context of the activity, which creates an instance of <code>AlertDialogManager</code>.
     * @param	title	Title of the Alert Dialog.
     * @param	message	Message displayed in the Alert Dialog.
     * @param	status	Represents success or failure and is used to set an icon (or no icon if the value is null).
     * */
    public void showAlertDialog(Context context, String title, String message, Boolean status) {
    	
    	// Creating a builder to set up the attributes of the dialog
    	AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
 
        // Setting icon of the dialog
        if(status != null)
            builder.setIcon((status) ? com.eis.greenenergy.main.R.drawable.success : com.eis.greenenergy.main.R.drawable.fail);
 
        // Setting OK button of the dialog
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	
            }
        });
 
        // create and display the dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
