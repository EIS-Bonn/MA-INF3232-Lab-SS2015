<?php
require_once 'DBconnect.php';

$link=dbconnect();

$handle = @fopen("lexicon", "r");
if ($handle) {
    while (($buffer = fgets($handle, 4096)) !== false) {
        
		$txt=explode(" ",$buffer);
		$query="select count(*) as count from adjectives where name like '".$txt[0]."'"; 
		$result=mysql_query($query);
		$row = mysql_fetch_assoc($result);
		if( $row['count']==0)
		{
			echo $query="INSERT INTO `adjectives` (`name`) VALUES  ('".$txt[0]."') ;";
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