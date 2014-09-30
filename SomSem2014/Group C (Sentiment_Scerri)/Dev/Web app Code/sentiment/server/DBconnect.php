<?php
$host="localhost";
$dbuser="root";
$pass="";
$db="sentiment";
function DBconnect ()
{
	global $host;
	global $dbuser;
	global $pass;
	global $db;
	
	 $link=@mysql_connect($host,$dbuser,$pass);
	if(!$link)
	{
		fail("DB Not Connect ");
	}
	if(!@mysql_select_db($db))
	{
		fail(" DB not found ");
		die ('Can\'t use foo : ' . mysql_error());
	}

	return $link;
	

}
function fail($txtmsg)
{	
	echo($txtmsg);
}	

?>