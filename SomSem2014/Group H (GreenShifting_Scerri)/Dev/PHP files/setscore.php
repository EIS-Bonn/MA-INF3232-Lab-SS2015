<?php
	/* 
	 *  The score of a user can be set by accessing the external database using this script
	 */
	
	// File with credentials to access the database
	require_once('connectvars.php');
	// Connecting to the MySQL database
	$con = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
	if (mysqli_connect_errno($con)) {
    	// "-1" is assigned if establishing the connection to the database was not successful
    	$data = "-1";
	}
	// The input (username and the new score of the user) is fetched via acessing the $_POST-variable
	$username = $_POST['username'];
	$newscore = $_POST['newscore'];

	// Updating database setting the new score
	$resulttmp = mysqli_query($con,"UPDATE ge_login SET score = $newscore WHERE username='$username'");
	if($resulttmp == '1')	$data = '0';
	else $data = '-1';
	
	// Returning the score as output of this script
	echo $data;
	
	// Close database connection
	mysqli_close($con);
?>