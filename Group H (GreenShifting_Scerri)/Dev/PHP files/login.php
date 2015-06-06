<?php
	/* 
	 *  The correctness of a set of log in-data can be verified by the use of this script as a web service
	 * 
	 *  (Error-)codes which can be assigned to the variable $result:
	 *  0	No error occurred
	 *  1	Connection could not be established
	 *  2	The username given as input does not exist in the database
	 *  3	The password given as input does not match the password which is stored in the database
	 */
	
	// Importing file with credentials to access the database
	require_once('connectvars.php');
	// Connecting to the MySQL database
	$con = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
	if (mysqli_connect_errno($con)) {
    	// establishing the connection to the database was not successful
    	$result = "1";
	}
	// The input (username and password) is fetched via acessing the $_POST-variable
	$username = $_POST['username'];
	$password = $_POST['password'];

	// Querying database in order to retrieve the user's encrypted password
	$resulttmp = mysqli_query($con,"SELECT password FROM ge_login where username='$username'");
	$row = mysqli_fetch_array($resulttmp);
	$data = $row[0];
	// Checking if we received a data set as result of the query
	if(!$data) {
		// We did not receive data, hence the username does not exist
		// The corresponging errorcode is assigned
		$result = "2";
	}
	// Using the sha1-function in order to encrypt the password given as input
	else if(sha1($password) != $data) {
		// The password given as input does not match the password stored in the database
		// The corresponding errorcode is assigned
		$result = "3";
	}
	else {
		// No error occurred, hence the code "0" is assigned
		$result = "0";
	}
	
	// Echoing the (error-)code which represents the status as the output of this web service
	echo $result;
	// Close MySQL-connection
	mysqli_close($con);
?>