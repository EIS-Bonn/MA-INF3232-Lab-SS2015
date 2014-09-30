<?php 
include('../DBconnect.php');

	$link=dbconnect();
	  $query="SELECT count(*) as count FROM news_sense where id  in (select idn from news_opi) ";
	  $result=mysql_query($query);
	  $row = mysql_fetch_assoc($result);
		echo "total: ".$row['count']."<br>";
		
	  $query="SELECT count(*) as count FROM news_sense where id  in (select idn from news_opi) and sentiment2 like 'positive' ";
	  $result=mysql_query($query);
	  $row = mysql_fetch_assoc($result);
		echo "positive news from voters: ".$row['count']."<br>";
		
		$query="SELECT count(*) as count FROM news_sense where id  in (select idn from news_opi) and sentiment2 like 'positive' and sentiment like 'positive'";
	  $result=mysql_query($query);
	  $row = mysql_fetch_assoc($result);
		echo "compare amount of positive news: ".$row['count']." percent: ".($row['count']/234)*100 ."<br>";
		
		$query="SELECT count(*) as count FROM news_sense where id  in (select idn from news_opi) and sentiment2 like 'neutral' ";
	  $result=mysql_query($query);
	  $row = mysql_fetch_assoc($result);
		echo "neutral news from voters: ".$row['count']."<br>";
		
	  $query="SELECT count(*) as count FROM news_sense where id  in (select idn from news_opi) and sentiment2 like 'neutral' and sentiment like 'neutral'";
	  $result=mysql_query($query);
	  $row = mysql_fetch_assoc($result);
		echo "compare amount of neutral news: ".$row['count']." percent: ".($row['count']/298)*100 ."<br>";
		
		$query="SELECT count(*) as count FROM news_sense where id  in (select idn from news_opi) and sentiment2 like 'negative' ";
	  $result=mysql_query($query);
	  $row = mysql_fetch_assoc($result);
		echo "negative news from voters: ".$row['count']."<br>";
		
	  $query="SELECT count(*) as count FROM news_sense where id  in (select idn from news_opi) and sentiment2 like 'negative' and sentiment like 'negative'  ";
	  $result=mysql_query($query);
	  $row = mysql_fetch_assoc($result);
		echo "compare amount of negative news: ".$row['count']." percent: ".($row['count']/267)*100 ."<br>";
		
		$query="SELECT count(*) as count FROM news_sense where id  in (select idn from news_opi) and sentiment2 like sentiment  ";
	  $result=mysql_query($query);
	  $row = mysql_fetch_assoc($result);
		echo "compare equal sentiment of  news: ".$row['count']."<br>".($row['count']/799)*100;
	  
?>