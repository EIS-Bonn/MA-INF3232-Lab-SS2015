/**
 * This package represents the data model of the application and solely contains Async-tasks; 
 * every class in this package extends the Android-class <code>android.os.AsyncTask</code>. 
 * The purpose of these Async-tasks is to connect to the external Twitter API or to connect to an external 
 * web-server in order to update or retrieve data.<br />
 * The Async-tasks <code>Login</code> and <code>Signup</code> are necessary to conduct the log in and sign up process.<br />
 * The task <code>SyncScore</code> enables the access of the latest score of an user stored in an external database.
 * Similar to this task is the Async-task <code>ScoreStorage</code> which is used to update the score of an user in the external database.<br />
 * The tasks <code>EnergyNowAccess</code> and <code>Predictions</code> allow to retrieve data about the current amount of green energy 
 * as well as predictions for certain hours in the future.<br />
 * The Async-task <code>TwitterUpdate</code> enables to access the Twitter API and to send Tweets from the user's Twitter account.
 *
 * @author	Veronika Henk, Sarvenaz Golchin, Mahnaz Hajibaba
 * @version	1.3
 * @since	2014-07-25
 */
package com.eis.greenenergy.connection;