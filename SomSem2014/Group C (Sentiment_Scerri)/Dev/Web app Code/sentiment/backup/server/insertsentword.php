<?php
require_once 'DBconnect.php';
 $link=dbconnect();
    $query="select * from opinion join sentences on opinion.ids=sentences.id where sentences.addsent=0 ";
	$result=mysql_query($query);
	while ($row = mysql_fetch_assoc($result)) {
		$text = explode(' ',$row['sentence']);
		for ($i=0;$i<count($text);$i++)
		{
			$count=0;
			$total=0;
			$query="select * from words where word like '".$text[$i]."' ";
			$result1=mysql_query($query);
			$row1 = mysql_fetch_assoc($result1);
			switch ($row['sentiment']){				
				case 'positive':
					$count=$row1['positive']+1;
					$total=$row1['total']+1;
					$query="UPDATE  `sentiment`.`words` SET  `positive` =  '".$count."',`total`='".$total."' WHERE  `words`.`ID` =".$row1['ID'].";";
					$result2=mysql_query($query);
					break;
				case 'negative':
					$count=$row1['negative']+1;
					$total=$row1['total']+1;
					$query="UPDATE  `sentiment`.`words` SET  `negative` =  '".$count."',`total`='".$total."' WHERE  `words`.`ID` =".$row1['ID'].";";
					$result2=mysql_query($query);
					break;
				case 'neutral':
					$count=$row1['neutral']+1;
					$total=$row1['total']+1;
					$query="UPDATE  `sentiment`.`words` SET  `neutral` =  '".$count."',`total`='".$total."' WHERE  `words`.`ID` =".$row1['ID'].";";
					$result2=mysql_query($query);
					break;
			}
		}
		echo $query="update sentences set `addsent`=1 where ID=".$row['IDs'].";";
		$result3=mysql_query($query);
		
	}
?>