<?php
require_once '../DBconnect.php';

$link=dbconnect();
$query="SELECT * FROM adjectives where sense like 'positive' order by name DESC ";
$result=mysql_query($query);
$file = fopen("pos3.lst","w");
$i=1;
 while ($row = mysql_fetch_assoc($result)) 
{
	
		echo $i++." ". $row['name']." ".$row['sense']."<br>";
		fwrite($file,$row['name']."\n");
	
 }
 
 $query="SELECT * FROM adjectives where sense like 'negative' order by name DESC ";
$result=mysql_query($query);
$file = fopen("neg3.lst","w");

$i=1;
 while ($row = mysql_fetch_assoc($result)) 
{
	
		echo $i++." ".$row['name']." ".$row['sense']."<br>";
		fwrite($file,$row['name']."\n");
	
 }

fclose($file); 
?>
