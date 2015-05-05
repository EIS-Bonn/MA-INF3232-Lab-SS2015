<?php
	/*
	 *  A data set representing a new user can be inserted by the use of this script as a web service 
	 * 
	 * 	(Error-)codes which can be assigned to the variable $result:
	 *  0	No error occurred
	 *  1	Connection could not be established
	 *  2	The username given as input does already exist in the database
	 *  -1	An unexpected error occurred
	 */
	
	// Importing file with credentials to access the database
	require_once('connectvars.php');
	// Connecting to the MySQL database
	$con = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
	if (mysqli_connect_errno($con)) {
    	// Establishing the connection to the database was not successful
    	$result = "1";
	}
	
	// The input (username and password) is fetched via accessing the $_POST-variable
	$username = $_POST['username'];
	$password = $_POST['password'];
	
	// Querying database and using the username, given as input, as condition
	$resulttmp = mysqli_query($con, "SELECT password FROM ge_login WHERE username = '$username'");
	$row = mysqli_fetch_array($resulttmp);
	$data = $row[0];
	// Testing if we received data
	if($data) {
		// We received data, hence the username does already exist and we assign the according errorcode
		$result = "2";
	}
	else {
		// Username does not exist yet, hence we can create a new user data set by storing the log in data
		// The password is stored encrypted
		$insertion = "INSERT INTO ge_login (username, password, score) VALUES('$username', SHA('$password'), 0)";
		$resultinsert = mysqli_query($con, $insertion) or die(mysqli_error($con));
		// If inserting the new user data was successful we assign "0"
		if($resultinsert)
			$result = "0";
		// If inserting the new user data was unexpectedly not successful, we assign the errorcode "-1"
		else
			$result = "-1";
	}
	// Echoing the (error-)code which represents the status as the output of this web service
	echo $result;
	// Close MySQL-connection
	mysqli_close($con);
?>