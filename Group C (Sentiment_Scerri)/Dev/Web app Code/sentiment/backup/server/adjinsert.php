<?php
include 'DBconnect.php';
$link=dbconnect();
echo $query="INSERT INTO `sentiment`.`adjectives` ( `name`, `sense`) VALUES ( 'آرامشی', 'negative')";
$result=mysql_query($query);
?>