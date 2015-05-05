<?php
	/*
	 * The amount of green energy can be accessed by the use of this script as a web service
	 */
	 
	// Importing file with credentials to access the database
	require_once('connectvars.php');
	// Connecting to MySQL database
	$con = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
	if (mysqli_connect_errno($con)) {
    	// Establishing the connection to the database was not successful
    	$result = "-1";
	}

	// The input (current point in time) is fetched via accessing the $_POST-variable
	$time = $_POST['time'];
	
	// Format the point in times to use it in the condition of the database query
	$tmp = explode(':', $time);
	$etime = $tmp[0] . ':00:00';
	$queryenergy = "SELECT percentage FROM energydata WHERE timehour = '$etime'";
	$resultqenergy = mysqli_query($con, $queryenergy) or die(mysqli_error($con));
	$row = mysqli_fetch_array($resultqenergy);
	$currentdata = $row[0];
	
	$hours[0] = 1;	$hours[1] = 3;	$hours[2] = 5;
	for($i = 0; $i < 3; $i++) {
		$tmp = explode(':', $time);
		$h = $tmp[0];
		$min = $tmp[1];
		$newh = $h + $hours[$i]; 
		if($newh > 23)	$newh = $newh - 24;
		if($min >= 45) {
			if($newh < 10)	$stringh = '0' . $newh;
			else $stringh = $newh . '';
			if($i == 0)	$ptime1 = $stringh . ':45:00';
			if($i == 1)	$ptime3 = $stringh . ':45:00';
			else 	$ptime5 = $stringh . ':45:00';
			//$ptime = $stringh . ':45:00';
		}
		else {
			$phour = $newh - 1;
			if($phour < 0)	$phour = $phour + 24;
			if($phour < 10)	$stringh = '0' . $phour;
			else $stringh = $phour . '';
			if($i == 0)	$ptime1 = $stringh . ':45:00';
			if($i == 1)	$ptime3 = $stringh . ':45:00';
			else	$ptime5 = $stringh . ':45:00';
			//$ptime = $stringh . ':45:00';
		}
	}
	
	// Get percentage for the current and prediction data from the database
	$queryprediction = "SELECT percentage FROM predictiondata WHERE timehour = '$ptime1'";
	$resultqprediction = mysqli_query($con, $queryprediction) or die(mysqli_error($con));
	while($row = mysqli_fetch_array($resultqprediction)) {
		$predata[0] = $row['percentage'];
	}
	
	$queryprediction = "SELECT percentage FROM predictiondata WHERE timehour = '$ptime3'";
	$resultqprediction = mysqli_query($con, $queryprediction) or die(mysqli_error($con));
	while($row = mysqli_fetch_array($resultqprediction)) {
		$predata[1] = $row['percentage'];
	}
	
	$queryprediction = "SELECT percentage FROM predictiondata WHERE timehour = '$ptime5'";
	$resultqprediction = mysqli_query($con, $queryprediction) or die(mysqli_error($con));
	while($row = mysqli_fetch_array($resultqprediction)) {
		$predata[2] = $row['percentage'];
	}
	
	// Close MySQL-connection
	mysqli_close($con);
	
	// Inner class for energy amount entries:
	class Edata {
       public $name = "";
       public $percentage  = "";
    }
	
	$energy[4];
	
	// Create Edata objects for very energy amount entry and storing them in the array $energy
	$energy[0] = new Edata();
	$energy[0]->name = "current";
	$energy[0]->percentage  = $currentdata;
	
	$energy[1] = new Edata();
	$energy[1]->name="p1";
	$energy[1]->percentage = $predata[0];
	
	$energy[2] = new Edata();
	$energy[2]->name="p2";
	$energy[2]->percentage = $predata[1];
	
	$energy[3] = new Edata();
	$energy[3]->name="p3";
	$energy[3]->percentage = $predata[2];

	// Convert the array $energy into the JSON-array EData
    $output = json_encode(array('EData' => $energy));
	// Return the JSON-array
    echo $output;
?>