<?php
require_once '../DBconnect.php';

$link=dbconnect();
$query="SELECT * FROM adjectives where sense like 'neutral' order by name DESC ";
$result=mysql_query($query);
$file = fopen("neutral.txt","w");

 while ($row = mysql_fetch_assoc($result)) 
{
	$txt=explode(" ",$row['name']);
	if(count ($txt)==1)
	{
		echo $row['name']." ".$row['sense']."<br>";
		fwrite($file,$row['name']."\n");
	}
 }

fclose($file); 
?>
