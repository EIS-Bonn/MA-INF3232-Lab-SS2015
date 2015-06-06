/**
 * The activities in this package represent the controller part of the application; 
 * they manage and connect the application's view (the layout files) with the 
 * application's data model , which is represented by Async-tasks in the package 
 * <code>com.eis.greenenergy.connection</code>.<br />
 * The activities <code>SignupActivity</code> and <code>LoginActivity</code> are quiet similar; 
 * they display the sign up-/log in-view to the user and handle the user's input. Their main 
 * purpose is to trigger the sign up-/log in-process itself.<br />
 * The activity <code>MainActivity</code> constitutes the application's main view and is also 
 * the launcher activity of the application. In other words, this activity is the connection 
 * between all the other activities. Altogether green energy information is shown in this view. 
 * The user can only interact with this view by clicking the Twitter- or the 
 * "More Details"-button.<br />
 * The activity <code>DetailActivity</code> embodies the view which is assigned for 
 * detailed green energy information. Moreover, it includes a list with predictions of 
 * the green energy amount for the next ten hours. The list entries are checkable, 
 * if one entry is selected it triggers a notification to be pushed at the time corresponding 
 * to this prediction. If the user is logged into Twitter, the Twitter-button is also visible 
 * in this view. Hence this view does also handle the user's interaction with the application.<br />
 * The class <code>WidgetProvider</code> is not an activity, but does also belong in this package 
 * as it is also a part of the application's controller and because it does also represent a view. 
 * The purpose of this class is to manage the application's widget and the instances of this 
 * widget that are created by the user. Equally to the other controller classes 
 * <code>WidgetProvider</code> has to handle the user's interaction. Interacting with widgets 
 * corresponding to this provider is limited to clicking the refresh button or to tabbing on 
 * the widget itself in order to launch the application.
 *
 * @author	Veronika Henk, Sarvenaz Golchin, Mahnaz Hajibaba
 * @version	1.2
 * @since	2014-07-25
 */
package com.eis.greenenergy.main;