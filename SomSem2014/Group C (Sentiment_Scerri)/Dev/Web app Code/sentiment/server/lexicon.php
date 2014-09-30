<?php
require_once 'DBconnect.php';

$link=dbconnect();
$query="SELECT * FROM adjectives order by name DESC ";
$result=mysql_query($query);
$file = fopen("lexicon.txt","w");

 while ($row = mysql_fetch_assoc($result)) 
{
	$txt=explode(" ",$row['name']);
	if(count ($txt)==1)
		fwrite($file,$row['name']." ".$row['sense']."\n");
 }

fclose($file); ?>
