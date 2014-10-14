<?php
	/* 
	 *  The correctness of a set of log in-data can be verified and the user's score can be accessed by using this script as a web service
	 */
	
	// Importing file with credentials to access the database
	require_once('connectvars.php');
	// Connecting to the MySQL database
	$con = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
	if (mysqli_connect_errno($con)) {
    	// Establishing the connection to the database was not successful
    	$result = "-1";
	}
	// The input (username and password) is fetched via acessing the $_POST-variable
	$username = $_POST['username'];
	$password = $_POST['password'];

	// Querying database in order to retrieve the user's encrypted password
	$querytmp = "SELECT password, score FROM ge_login WHERE username='$username' LIMIT 1";
	$resulttmp = mysqli_query($con,$querytmp);
	while($row = mysqli_fetch_array($resulttmp)) {
		$data = $row['password'];
		$score = $row['score'];
	}
	
	// Checking if we received a data set as result of the query
	if(!$data) {
		// We did not receive data, hence the username does not exist and the corresponging errorcode is assigned
		$result = "-1";
	}
	// Using the sha1-function in order to encrypt the password given as input
	else if(sha1($password) != $data) {
		// The password given as input does not match the password stored in the database and the corresponding errorcode is assigned
		$result = "-1";
	}
	else {
		// Accessing user's score
		$result = $score;
	}
	
	// Echoing the (error-)code which represents the status as the output of this web service or the score if no error occured
	echo $result;
	// Close MySQL-connection
	mysqli_close($con);
?>