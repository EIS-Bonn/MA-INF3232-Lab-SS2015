<?php
include 'html2text.php';
include 'DBconnect.php';
require_once 'phpQuery.php';

$count_adj=0;

if(isset($_GET['action']))
{
	switch ($_GET['action']){
		case 'pos' :
		    addpos();
			break;
		case 'neu' :
		    addneu();
			break;
		case 'neg' :
		    addneg();
			break;
		case 'Sdelete' :
		    Sdelete();
			break;
		case 'adj_delete' :
		    adj_delete();
			break;	
		case 'word_delete' :
		    word_delete();
			break;
		
		case 'insert_opinion_adj' :
		    insert_opinion_adj();
			break;
		case 'sentiment_text' :
		    sentiment_text();
			break;
		case 'find-word' :
		    find_word();
			break;	
	}
}

function find_word()
{
$word=sanitize($_GET['word']);
$link=dbconnect();
$query="SELECT * FROM  `adjectives` WHERE name like '".$word."%';";
	$result=mysql_query($query);
	$pos='positive';
	$neg='negative';
	$neu='neutral';
	while ($row = mysql_fetch_assoc($result)) {
		$pos=adjopinion($row['id'],'positive');
		$neg=adjopinion($row['id'],'negative');
		$neu=adjopinion($row['id'],'neutral');
		findopinion($pos,$neg,$neu,$row['id']);
		
		echo "<div class='col-lg-2' id='sent".$row['id']."'>";
		if ($row['sense']=='positive')
				echo "<div class='alert alert-dismissable alert-success'>";
		else if($row['sense']=='negative')
				echo "<div class='alert alert-dismissable alert-danger'>";
		else echo "<div class='alert alert-dismissable alert-info'>";
				echo "<h4 align='right'>".$row['name']."</h4>
					<input type='hidden' id='IDs' value='".$row['id']."'/>
					<p align='right'>					
						<button type='button' class='btn btn-success btn-sm' onclick='adj_opinion(".$row['id'].",1);'>".$pos."</button> 
						<button type='button' class='btn btn-primary btn-sm' onclick='adj_opinion(".$row['id'].",2);'>".$neu."</button>
						<button type='button' class='btn btn-danger btn-sm' onclick='adj_opinion(".$row['id'].",3);' >".$neg."</button>
					</p>
					<!-- <p align='left'><button type='button' class='btn btn-danger btn-sm' onclick='adjdelete(".$row['id'].");'>Delete</button> </p> -->
				</div>
			  </div>
			  ";
	}
	
	mysql_close();

}

function sanitize($data){
          //$result = trim($data);
          //$result = htmlspecialchars($data);
          $result = mysql_real_escape_string($data);
          return $result;
      }

function sentiment_text()
{
	$text=sanitize($_GET['text']);
	$arr=explode(" ",$text);
	//print_r ($arr);
	$link=dbconnect();
	$sent_text="";
	$pos=0;
	$neg=0;
	for ($i=0;$i<count($arr);$i++)
	{	
		if ($arr[$i]=="\n")
			{
				$sent_text=$sent_text."<br>";
			}
		$query="select count(*) as count from adjectives where name like '".$arr[$i]."' ";
		$result=mysql_query($query);
		$row = mysql_fetch_assoc($result);
		if ($row['count']==0)
		{
			$sent_text=$sent_text."".$arr[$i]." ";
		}else 
		{
			$query="select * from adjectives where name like '".$arr[$i]."' ";
			$result=mysql_query($query);
			$row = mysql_fetch_assoc($result);
			if ($row['sense']=='positive')
			{
				$sent_text=$sent_text."<span class='btn btn-success disabled'>".$arr[$i]."</span> ";
				$pos++;
			}else if ($row['sense']=='negative')
			{
				$sent_text=$sent_text."<span class='btn btn-danger disabled'>".$arr[$i]."</span> ";
				$neg++;
			} else 
			{
				$sent_text=$sent_text."".$arr[$i]." ";
			}
		}
	}
	echo $sent_text;
	echo "<br>Positive: ".$pos." negative: ".$neg;
}

function insert_opinion_adj()
{
	$ida=$_GET['id'];
	$sent=$_GET['sent'];
	switch ($sent){
		case 1 :
		    $sent='positive';
			break;
		case 2 :
		    $sent='neutral';
			break;
		case 3 :
		    $sent='negative';
			break;
	}
	$link=dbconnect();
     $query="INSERT INTO `adj_opi` (`ida`,`adjopinion`) VALUES ( '".$ida."','".$sent."'); ";
	$result=mysql_query($query);
	if ($result)
	{
		echo "<div class='alert alert-dismissable alert-success'>Your opinion add successfully</div>";
	}else{
		echo "<div class='alert alert-dismissable alert-danger'>There is some error please try again</div>";
	}
}

function addneg()
{
	$ids=$_GET['IDs'];
	$link=dbconnect();
    $query="INSERT INTO `opinion` (`IDs`,`sentiment`) VALUES ( '".$ids."','negative'); ";
	$result=mysql_query($query);
	if ($result)
	{
		echo "<div class='alert alert-dismissable alert-success'>Your opinion add successfully</div>";
	}else{
		echo "<div class='alert alert-dismissable alert-danger'>There is some error please try again</div>";
	}
}


function addneu()
{
	$ids=$_GET['IDs'];
	$link=dbconnect();
    $query="INSERT INTO `opinion` (`IDs`,`sentiment`) VALUES ( '".$ids."','neutral'); ";
	$result=mysql_query($query);
	if ($result)
	{
		echo "<div class='alert alert-dismissable alert-success'>Your opinion add successfully</div>";
	}else{
		echo "<div class='alert alert-dismissable alert-danger'>There is some error please try again</div>";
	}
}


function addpos()
{
	$ids=$_GET['IDs'];
	$link=dbconnect();
    $query="INSERT INTO `opinion` (`IDs`,`sentiment`) VALUES ( '".$ids."','positive'); ";
	$result=mysql_query($query);
	if ($result)
	{
		echo "<div class='alert alert-dismissable alert-success'>Your opinion add successfully</div>";
	}else{
		echo "<div class='alert alert-dismissable alert-danger'>There is some error please try again</div>";
	}
}

function word_delete()
{
	$ids=$_GET['id'];
	$link=dbconnect();
    $query="delete from words where ID=$ids";
	$result=mysql_query($query);
	if ($result)
	{
		echo "<div class='alert alert-dismissable alert-success'>Delete successfully</div>";
	}else{
		echo "<div class='alert alert-dismissable alert-danger'>There is some error please try again</div>";
	}
}

function adj_delete()
{
	$ids=$_GET['id'];
	$link=dbconnect();
    $query="delete from adjectives where id=$ids";
	$result=mysql_query($query);
	if ($result)
	{
		echo "<div class='alert alert-dismissable alert-success'>Delete successfully</div>";
	}else{
		echo "<div class='alert alert-dismissable alert-danger'>There is some error please try again</div>";
	}
}

function Sdelete()
{
	$ids=$_GET['id'];
	$link=dbconnect();
    $query="delete from sentences where ID=$ids";
	$result=mysql_query($query);
	if ($result)
	{
		echo "<div class='alert alert-dismissable alert-success'>Delete successfully</div>";
	}else{
		echo "<div class='alert alert-dismissable alert-danger'>There is some error please try again</div>";
	}
}

function positive($q)
{
	$link=dbconnect();
	if ($q=='sent')
		$query="select count(*) as count from opinion where sentiment like '%positive%'";
	else
		$query="select count(distinct ida) as count from adj_opi where adjopinion like '%positive%'" ;
	$result=mysql_query($query);
	$row = mysql_fetch_assoc($result);
	echo $row['count'];
}

function All($q)
{
	global $count_adj;
	$link=dbconnect();
	if ($q=='sent')
		$query="select count(*) as count from sentences ";
	else
		$query="select count(*) as count from adjectives WHERE id NOT IN (SELECT ida FROM adj_opi)";
	$result=mysql_query($query);
	$row = mysql_fetch_assoc($result);
	$count_adj =$row['count'];
	echo $row['count'];
	mysql_close($link);
}

function neutral($q)
{
	$link=dbconnect();
	if ($q=='sent')
		$query="select count(*) as count from opinion where sentiment like '%neutral%'";
	else
		$query="select count(distinct ida ) as count from adj_opi where adjopinion like '%neutral%'";
	$result=mysql_query($query);
	$row = mysql_fetch_assoc($result);
	echo $row['count'];
	mysql_close($link);
}

function allvote($q)
{
	$link=dbconnect();
	if ($q=='sent')
		$query="select count(*) as count from opinion ";
	else
		$query="SELECT COUNT( distinct ida ) AS count FROM adj_opi ";
	$result=mysql_query($query);
	$row = mysql_fetch_assoc($result);
	echo $row['count'];
	mysql_close($link);
}

function negative($q)
{
	$link=dbconnect();
	if ($q=='sent')
		$query="select count(*) as count from opinion where sentiment like '%negative%' ";
	else
		$query="select count(distinct ida) as count from adj_opi where adjopinion like '%negative%'";
	$result=mysql_query($query);
	$row = mysql_fetch_assoc($result);
	echo $row['count'];
	mysql_close($link);
}

function sentences()
{
	$link=dbconnect();
	
	$query="SELECT count(*) as count FROM sentences WHERE ID NOT IN (SELECT IDs FROM opinion)  ";
	$result=mysql_query($query);
	$row = mysql_fetch_assoc($result);
	$rand=rand ( 0 ,$row['count']  );
	if ($rand<10)
	{
		$rand=10;
		}
	else if	($rand>$row['count']-10)
			{
				$rand=$row['count']-10;
			}
	$co=$rand-10;
     $query="SELECT * FROM sentences WHERE ID NOT IN (SELECT IDs FROM opinion) LIMIT ".$rand ." , 3 ;";
	$result=mysql_query($query);
	while ($row = mysql_fetch_assoc($result)) {
		echo "<div class='bs-component' id='sent".$row['ID']."'>
				<div class='alert alert-dismissable alert-info'>
					<h4 align='right'>".$row['sentence']."</h4>
					<input type='hidden' id='IDs' value='".$row['ID']."'/>
					<p align='right'><button type='button' class='btn btn-success btn-sm' onclick='sent_positive(".$row['ID'].");'>مثبت</button> 
						<button type='button' class='btn btn-primary btn-sm' onclick='sent_neutral(".$row['ID'].");'>خنثی</button>
						<button type='button' class='btn btn-danger btn-sm' onclick='sent_negative(".$row['ID'].");'>منفی</button>
					</p>
					<!-- <p align='left'><button type='button' class='btn btn-danger btn-sm' onclick='SENdelete(".$row['ID'].");'>Delete</button> </p> -->
				</div>
			  </div>";
	}
	echo "<p align='left'><button type='button' class='btn btn-danger btn-sm' onclick='Next_sentences();'>Next Sentences</button> </p>";
	mysql_close();
}

function adjectives()
{
global $count_adj;
	$link=dbconnect();
	
	/*$query="SELECT count(*) as count FROM  `adjectives`  WHERE id NOT IN (SELECT ida FROM adj_opi)";
	$result=mysql_query($query);
	$row = mysql_fetch_assoc($result);*/
	//echo $count_adj;
	 $rand=0;//mt_rand ( 0 ,$count_adj  );
	if ($rand<10)
	{
		$rand=0;
		}
	else if	($rand>$count_adj-10)
			{
				$rand=$count_adj-10;
			}
	//echo $rand;
     $query="SELECT * FROM  `adjectives` WHERE id NOT IN (SELECT ida FROM adj_opi)  order by id ASC limit ".$rand .",10 ;";
	$result=mysql_query($query);
	$pos='positive';
	$neg='negative';
	$neu='neutral';
	while ($row = mysql_fetch_assoc($result)) {
		echo "<div class='col-lg-4' id='sent".$row['id']."'>
				<div class='alert alert-dismissable alert-info'>
					<h4 align='right'>".$row['name']."</h4>
					<input type='hidden' id='IDs' value='".$row['id']."'/>
					<p align='right'><button type='button' class='btn btn-success btn-sm' onclick='adj_opinion(".$row['id'].",1);'>مثبت</button> 
						<button type='button' class='btn btn-primary btn-sm' onclick='adj_opinion(".$row['id'].",2);'>خنثی</button>
						<button type='button' class='btn btn-danger btn-sm' onclick='adj_opinion(".$row['id'].",3);'>منفی</button>
					</p>
					<!-- <p align='left'><button type='button' class='btn btn-danger btn-sm' onclick='adjdelete(".$row['id'].");'>Delete</button> </p> -->
				</div>
			  </div>";
	}
	echo "<p align='left'><button type='button' class='btn btn-danger btn-sm' onclick='Next_sentences();'>Next Adjectives</button> </p>";
	mysql_close();
}

function op_adj()
{
	$link=dbconnect();
	$page=$_GET['page'];
    $query="SELECT * FROM  `adjectives`  order by sense ,name DESC limit $page,10 ";
	$page+=10;
	$result=mysql_query($query);
	$pos='positive';
	$neg='negative';
	$neu='neutral';
	while ($row = mysql_fetch_assoc($result)) {
		$pos=adjopinion($row['id'],'positive');
		$neg=adjopinion($row['id'],'negative');
		$neu=adjopinion($row['id'],'neutral');
		findopinion($pos,$neg,$neu,$row['id']);
		
		echo "<div class='col-lg-2' id='sent".$row['id']."'>";
		if ($row['sense']=='positive')
				echo "<div class='alert alert-dismissable alert-success'>";
		else if($row['sense']=='negative')
				echo "<div class='alert alert-dismissable alert-danger'>";
		else echo "<div class='alert alert-dismissable alert-info'>";
				echo "<h4 align='right'>".$row['name']."</h4>
					<input type='hidden' id='IDs' value='".$row['id']."'/>
					<p align='right'>					
						<button type='button' class='btn btn-success btn-sm' onclick='adj_opinion(".$row['id'].",1);'>".$pos."</button> 
						<button type='button' class='btn btn-primary btn-sm' onclick='adj_opinion(".$row['id'].",2);'>".$neu."</button>
						<button type='button' class='btn btn-danger btn-sm' onclick='adj_opinion(".$row['id'].",3);' >".$neg."</button>
					</p>
					<!-- <p align='left'><button type='button' class='btn btn-danger btn-sm' onclick='adjdelete(".$row['id'].");'>Delete</button> </p> -->
				</div>
			  </div>
			  ";
	}
	echo "<p align='left'><a type='button' class='btn btn-danger btn-sm' href='http://www.computerssl.com/opinion.php?p=ad&page=".$page."'>Next </a> </p>";
	mysql_close();
}

function findopinion($pos,$neg,$neu,$id)
{
		if ($pos>$neg)
		{
			$query="UPDATE  `adjectives` SET  `sense` =  'positive' WHERE  `adjectives`.`id` =".$id.";";
			$result=mysql_query($query);
		}else if($neg>$neu)
		{
			$query="UPDATE  `adjectives` SET  `sense` =  'negative' WHERE  `adjectives`.`id` =".$id.";";
			$result=mysql_query($query);
		}
		
}

function adjopinion($id,$sen)
{
	$link=dbconnect();
	$query="select count( ida) as count from adj_opi where adjopinion like '%".$sen."%' and ida= ".$id.";";
	$result=mysql_query($query);
	$row = mysql_fetch_assoc($result);
	return $row['count'];
	
}


?>