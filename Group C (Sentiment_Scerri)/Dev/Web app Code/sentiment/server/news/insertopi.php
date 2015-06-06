<?php 
include('../DBconnect.php');

	$link=dbconnect();
	  $query="SELECT * FROM news_sense where id  in (select idn from news_opi) ";
	  $result=mysql_query($query);
	  while($row = mysql_fetch_assoc($result))
	  {
		 $query="SELECT * FROM `news_opi` where idn=".$row['id'];
		$result1=mysql_query($query);
		$neu=0;
		$neg=0;
		$pos=0;
		while($row1 = mysql_fetch_assoc($result1))
		{
			switch ($row1['opinion']){
				case 'positive':
					$pos++;
					break;
				case 'negative':
					$neg++;
					break;
				case 'neutral':
					$neu++;
					break;
			}
		}
			$opi='neutral';
			echo '<br>'.$pos.' '.$neg.' '.$neu.'<br>';
			if ($pos>$neg && $pos>$neu)
			{
				$opi='positive';
			}else if ($neg>$neu && $neg>$pos)
				{
					$opi='negative';
				}
			echo '<br>';
			echo $query="UPDATE `news_sense` SET `sentiment2` = '".$opi."' WHERE `id` =".$row['id'];
			$result2=mysql_query($query);
	  }
	  
?>