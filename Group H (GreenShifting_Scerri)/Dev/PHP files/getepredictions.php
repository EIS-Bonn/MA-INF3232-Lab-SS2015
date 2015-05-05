<?php
	/*
	 * The predicted amount of green energy can be accessed by the use of this script as a web service
	 */
	 
	// Importing file with credentials to access the database
	require_once('connectvars.php');
	
	// Define the limits for the recommendation to use energy
	$LOWERLIMIT = 27;
	$UPPERLIMIT = 60;
	
	// Connecting to MySQL database
	$con = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
	if (mysqli_connect_errno($con)) {
    	// Establishing the connection to the database was not successful
    	$result = "-1";
	}

	// The input (current point in time) is fetched via acessing the $_POST-variable
	$time = $_POST['time'];
	
	// Format the point in times of the predictions to use it in the condition of the database query
	for($i = 0; $i < 10; $i++) {
		$tmp = explode(':', $time);
		$h = $tmp[0];
		$min = $tmp[1];
		$hours = $i+1;
		$newh = $h + $hours; 
		if($newh > 23)	$newh = $newh - 24;
		if($min >= 45) {
			if($newh < 10)	$stringh = '0' . $newh;
			else $stringh = $newh . '';
			if($i == 0)	$ptime1 = $stringh . ':45:00';
			else if($i == 1)	$ptime2 = $stringh . ':45:00';
			else if($i == 2)	$ptime3 = $stringh . ':45:00';
			else if($i == 3)	$ptime4 = $stringh . ':45:00';
			else if($i == 4)	$ptime5 = $stringh . ':45:00';
			else if($i == 5)	$ptime6 = $stringh . ':45:00';
			else if($i == 6)	$ptime7 = $stringh . ':45:00';
			else if($i == 7)	$ptime8 = $stringh . ':45:00';
			else if($i == 8)	$ptime9 = $stringh . ':45:00';
			else 	$ptime10 = $stringh . ':45:00';
			//$ptime = $stringh . ':45:00';
		}
		else {
			$phour = $newh - 1;
			if($phour < 0)	$phour = $phour + 24;
			if($phour < 10)	$stringh = '0' . $phour;
			else $stringh = $phour . '';
			if($i == 0)	$ptime1 = $stringh . ':45:00';
			else if($i == 1)	$ptime2 = $stringh . ':45:00';
			else if($i == 2)	$ptime3 = $stringh . ':45:00';
			else if($i == 3)	$ptime4 = $stringh . ':45:00';
			else if($i == 4)	$ptime5 = $stringh . ':45:00';
			else if($i == 5)	$ptime6 = $stringh . ':45:00';
			else if($i == 6)	$ptime7 = $stringh . ':45:00';
			else if($i == 7)	$ptime8 = $stringh . ':45:00';
			else if($i == 8)	$ptime9 = $stringh . ':45:00';
			else 	$ptime10 = $stringh . ':45:00';
			//$ptime = $stringh . ':45:00';
		}
	}
	
	// Get ID of first dataset for correct order
	$queryid = "SELECT ID FROM predictiondata WHERE (timehour = '$ptime1')";
	$resultid = mysqli_query($con, $queryid) or die(mysqli_error($con));
	while($row = mysqli_fetch_array($resultid)) {
		$startid = $row['ID'];
	}	
	
	// Get percentage for the prediction data from the database
	$queryprediction = "SELECT ID, percentage FROM predictiondata WHERE (timehour = '$ptime1' OR timehour = '$ptime2' OR timehour = '$ptime3' OR timehour = '$ptime4' OR " .
						"timehour = '$ptime5' OR timehour = '$ptime6' OR timehour = '$ptime7' OR timehour = '$ptime8' OR timehour = '$ptime9' OR timehour = '$ptime10')";
	$resultqprediction = mysqli_query($con, $queryprediction) or die(mysqli_error($con));
	$k1 = 0;	$k2 = 0;
	while($row = mysqli_fetch_array($resultqprediction)) {
		if($row['ID'] >= $startid) {
			$testdata[$k1] = $row['percentage'];
			$k1++;
		}		
		else {
			$testdata2[$k2] = $row['percentage'];
			$k2++;
		}
	}
	
	for($j1 = 0; $j1 < $k1; $j1++) {
		$predata[$j1] = $testdata[$j1];
	}
	for($j2 = 0; $j2 < $k2; $j2++) {
		$tmp = $k1+$j2;
		$predata[$tmp] = $testdata2[$j2];
	}
	
	// Close MySQL-connection
	mysqli_close($con);
	
	// Inner class for prediction entries
	class Edata {
       public $name = "";
       public $percentage  = "";
	   public $color = "";
    }
	
	$energy[10];
	
	for($i = 0; $i < 10; $i++) {
		$j = $i+1;
		$name = "p"+ $j;
		
		// Create Edata object for every prediction entry and store it in the array $energy
		$energy[$i] = new Edata();
		$energy[$i]->name = $name;
		$energy[$i]->percentage  = $predata[$i];
		
		// Assign a color to every entry (to be displayed as background in a ListView) depending on the amount of green energy
		if($predata[$i] > $UPPERLIMIT)	$color = "#40FF00";
		else if($predata[$i] < $LOWERLIMIT)	$color = "#FE9A2E";
		else $color = "#D8D8D8";
		$energy[$i]->color = $color;
	}

	// Convert the array $energy into the JSON-array PData
    $output = json_encode(array('PData' => $energy));
	// Return the JSON-array
    echo $output;
?>