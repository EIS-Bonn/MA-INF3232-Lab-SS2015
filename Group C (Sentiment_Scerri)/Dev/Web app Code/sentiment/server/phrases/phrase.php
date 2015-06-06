<?php
require_once '../DBconnect.php';

$link=dbconnect();

$handle = @fopen("phrase.txt", "r");
$i=0;
if ($handle) {
    while (($buffer = fgets($handle)) !== false) {
        
			$query="select count(*) as count from phrase where txt like '".$buffer."'"; 
		$result=mysql_query($query);
		$row = mysql_fetch_assoc($result);
		if( $row['count']==0)
		{
		
			echo $i++."  ";
			
			echo $query="INSERT INTO `phrase` (`txt`) VALUES  ('".$buffer."') ;";
			$result=mysql_query($query);
			echo "<br>";
		}
    }
    if (!feof($handle)) {
        echo "Error: unexpected fgets() fail\n";
    }
    fclose($handle);
} 
?>