/**
 * The classes in this package are basically auxiliary classes; they do also belong to the controller part of the application as they do 
 * support the classes and functionalities implemented in the package <code>com.eis.greenenergy.main</code>. 
 * This package constitutes a very important part of the application, though.<br />
 * The purpose of the class <code>UserManager</code> is to manage the user's data, as well as the application's whole data structure and 
 * the access to most Async-tasks.<br />
 * The class <code>ConnectionDetector</code> facilitates to secure that the user's device is connected to the internet. 
 * If the user is not connected to the internet, a dialog with a message is displayed to the user creating an instance of the class 
 * <code>AlertDialogManager</code>.<br />
 * The class <code>PredictionEntry</code> helps to display the list on the detail view of the application properly as an instance of 
 * this class represents an entry in the energy prediction list.<br />
 * Finally, the class <code>NotificationPublishes</code> pushes the notifications once it receives them by getting an alarm from the 
 * <code>AlarmManager</code>.<br />
 *
 * @author	Veronika Henk, Sarvenaz Golchin, Mahnaz Hajibaba
 * @version	1.2
 * @since	2014-07-25
 */
package com.eis.greenenergy.help;