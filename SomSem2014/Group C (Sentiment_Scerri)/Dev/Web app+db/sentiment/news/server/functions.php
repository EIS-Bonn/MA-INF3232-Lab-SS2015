<?php
  include('DBconnect.php');
  include 'simple_html_dom.php';
  
  if(isset($_GET['action']))
  {
	switch ($_GET['action']){
		case 'political' :
		    political();
			break;
		case 'sport' :
		    sport();
			break;
		case 'social' :
		    social();
			break;
		case 'economic' :
		    economic();
			break;
		case 'show_news' :
			$cat=$_GET['cat'];
		    show_news($cat);
			break;
	}
  }
  function political()
  {
	parse('http://www.tabnak.ir/fa/rss/11','tabnak','political');
	echo "<br>";
	parse('http://www.yjc.ir/fa/rss/3','yjc','political');
	echo "<br>";
	parse('http://farsnews.com/rss.php?srv=1','farsnews','political');
	
  }
  function sport()
  {
	parse('http://www.tabnak.ir/fa/rss/2','tabnak','sport');
	echo "<br>";
	parse('http://www.varzesh3.com/rss','varzesh3','sport');
	echo "<br>";
	parse('http://yjc.ir/fa/rss/8','yjc','sport');
	echo "<br>";
	parse('http://farsnews.com/rss.php?srv=4','farsnews','sport');	
  }
	function social()
	{
		parse('http://www.tabnak.ir/fa/rss/3','tabnak','social');
		echo "<br>";
		parse('http://yjc.ir/fa/rss/5','yjc','social');
		echo "<br>";
		parse('http://farsnews.com/rss.php?srv=3','farsnews','social');
		echo "<br>";
		culture();
		
	}
	function economic()
	{
		parse('http://www.tabnak.ir/fa/rss/6','tabnak','economic');
		echo "<br>";
		parse('http://www.yjc.ir/fa/rss/6','yjc','economic');
		echo "<br>";
		parse('http://farsnews.com/rss.php?srv=2','farsnews','economic');
		echo "<br>";
		internatinal();
		
	}
	function internatinal()
	{
		parse('http://farsnews.com/rss.php?srv=6','farsnews','international');
		parse('http://www.yjc.ir/fa/rss/9','yjc','international');
	}
	
	function culture()
	{
		parse('http://farsnews.com/rss.php?srv=7','farsnews','culture');
		parse('http://www.yjc.ir/fa/rss/4','yjc','culture');
		
	}
  
  
  
  function parse($url,$site,$category)   
  {
    $rss = simplexml_load_file($url);
	
	if ($rss->channel->item)
	{
		$i=0;
		foreach ($rss->channel->item as $item) {	
			$i++;
			$title = (string) $item->title; // Title
		  
			  $link   = (string) $item->link; // Url Link
			   $pubDate1   =  $item->pubDate;
			   if ($site!='farsnews')
			   {
					$pubDate=pubdatetotime($pubDate1);
				}else
				{
					 $pubDate=jalali_to_gregorian($pubDate1,$mod='-');					
				}
			  
			  echo $count=last_news($link,$pubDate,$site,$category);
			  
			if ($count==0)
			{
				$txt=export($link,$site);
				$link1=dbconnect();		  
			   $query="INSERT INTO `news` (`source`,`title`,`url`,`txt`,`publish_date`,`category`) VALUES 
				( '".$site."','".$title."','".$link."','".$txt."','".$pubDate."','".$category."');";
				//echo "</br>";
				$result=mysql_query($query);   
				mysql_close();
			}
			if ($i==10)
			{
				echo 'break';
				break;
			}
		}
	}
  }
  
  function export($url,$site) 
  {	  	
	$txt='';
	switch ($site)
	{
		case 'tabnak':
			$homepage = file_get_html($url);	
			foreach($homepage->find('div') as $element) 
				{				
					 $z=$element->class;
					if ($z=='body' )
					{
						$txt=$element->plaintext ;								
					}
				}
			break;
		case 'yjc':
			$homepage = file_get_html($url);	
			foreach($homepage->find('div') as $element) 
				{				
					 $z=$element->class;
					if ($z=='body' )
					{
						$txt=$element->plaintext ;								
					}
				}
			break;
		case 'varzesh3':
			 $homepage = file_get_html($url);	
			 
			if ($homepage)
			{
				foreach($homepage->find('table') as $element) 
					{				
						 $z=$element->id;
						if ($z=='CenterTable' )
						{
							$txt=$element->plaintext ;								
						}
					}
			}
			break;
		case 'farsnews':
			 $homepage = file_get_html($url);	
			 
			if ($homepage)
			{
				foreach($homepage->find('span') as $element) 
					{				
						 $z=$element->id;
						if ($z=='ctl00_bodyHolder_newstextDetail_nwstxtBodyPane' )
						{
							$txt=$element->plaintext ;								
						}
					}
			}
			break;
	}
		return $txt;
  }
  
  function last_news($link,$date,$site,$cat)
  {
	  $link1=dbconnect();
	  $date=rtrim($date);
	  $query="SELECT count(*) as count FROM `news` where (url like '".$link."' or publish_date like '".$date."') and source like '".$site."' and category like '".$cat."'";
	  $result=mysql_query($query);
	  $row = mysql_fetch_assoc($result);
	  mysql_close();
	  return $row['count'];	  
  }
  
  function CAT_news($category)
  {
	  $link1=dbconnect();
	  $query="SELECT count(*) as count FROM `news` where  category like '".$category."' ";
	  $result=mysql_query($query);
	  $row = mysql_fetch_assoc($result);
	  mysql_close();
	  echo $row['count'];	  
  }
  
  function show_news($cat){
	$link1=dbconnect();
	  $query="SELECT title,url,source,UNIX_TIMESTAMP(publish_date) as publish_date1,publish_date FROM `news` where  category like '".$cat."' order by publish_date1 DESC limit 0,25 ";
	  $result=mysql_query($query);
	  $cat=Name_category($cat);
	  echo "<h2>$cat</h2>				
				<div class='well'  >";
	  while ($row = mysql_fetch_assoc($result)) 
	  {
		 
		$ago=ago(new DateTime($row['publish_date']));
		$name=Name_news_website($row['source']);		
		echo " <span><h5><a href='".$row['url']."' target='_blank'>".$row['title']."</a>  ".$ago."  ".$name." </h5></span><hr> ";
	  
	  }
	  mysql_close();
	  echo "	
		</div>";
  }
  
  function pubdatetotime($pubDate) {

		$months = array('Jan' => '01', 'Feb' => '02', 'Mar' => '03', 
		'Apr' => '04', 'May' => '05', 'Jun' => '06', 
		'Jul' => '07', 'Aug' => '08', 'Sep' => '09', 
		'Oct' => '10', 'Nov' => '11', 'Dec' => '12');

		$date = substr($pubDate, 0,12);
		$year = substr($date, 7,4); 
		$month = substr($date, 3,3);
		$d =  substr($date, 0,2);

		$time = substr($pubDate, 12,9);

		return $year."-".$months[$month]."-".$d." ".$time;  
}
//thats for compare to time and now 
function ago( $datetime )
{
	date_default_timezone_set("Asia/Tehran"); 
	$now=new DateTime();
    $interval = $now->diff( $datetime );
    $suffix = ( $interval->invert ? ' قبل' : '' );
    if ( $v = $interval->y >= 1 ) return pluralize( $interval->y, 'سال' ) . $suffix;
    if ( $v = $interval->m >= 1 ) return pluralize( $interval->m, 'ماه' ) . $suffix;
    if ( $v = $interval->d >= 1 ) return pluralize( $interval->d, 'روز' ) . $suffix;
    if ( $v = $interval->h >= 1 ) return pluralize( $interval->h, 'ساعت' ) . $suffix;
    if ( $v = $interval->i >= 1 ) return pluralize( $interval->i, 'دقیقه' ) . $suffix;
    return pluralize( $interval->s, 'ثانیه' ) . $suffix;
}

function pluralize( $count, $text ) 
{ 
    return $count . ( ( $count == 1 ) ? ( " $text" ) : ( " ${text}" ) );
}
// change english name to persian for showing in frontend
function Name_news_website($name){
	switch ($name){
		case 'tabnak':
			$text='تابناک';
			break;
		case 'yjc':
			$text='باشگاه خبرنگاران جوان';
			break;
		case 'varzesh3':
			$text='ورزش3';
			break;
		case 'farsnews':
			$text= 'فارس نیوز';
			break;
	}
	return $text;
}

function Name_category($name){
	switch ($name){
		case 'economic':
			$text='اقتصادی';
			break;
		case 'social':
			$text='اجتماعی';
			break;
		case 'sport':
			$text='ورزشی';
			break;
		case 'political':
			$text= 'سیاسی';
			break;
		case 'culture':
			$text= 'فرهنگی';
			break;
		case 'international':
			$text= 'بین الملل';
			break;
			
	}
	return $text;
}

// change jalali date to miladi for fars news 
function jalali_to_gregorian($pubDate1,$mod=''){
	$date = substr($pubDate1, 0,10);
	$j_y = substr($date, 0,4); 
	$j_m = substr($date, 5,2);
	$j_d =  substr($date, 8,2);
	$time = substr($pubDate1, 13,8);
	 $d_4=($j_y+1)%4;
	 $doy_j=($j_m<7)?(($j_m-1)*31)+$j_d:(($j_m-7)*30)+$j_d+186;
	 $d_33=(int)((($j_y-55)%132)*.0305);
	 $a=($d_33!=3 and $d_4<=$d_33)?287:286;
	 $b=(($d_33==1 or $d_33==2) and ($d_33==$d_4 or $d_4==1))?78:(($d_33==3 and $d_4==0)?80:79);
	 if((int)(($j_y-19)/63)==20){$a--;$b++;}
	 if($doy_j<=$a){
	  $gy=$j_y+621; $gd=$doy_j+$b;
	 }else{
	  $gy=$j_y+622; $gd=$doy_j-$a;
	 }
	 foreach(array(0,31,($gy%4==0)?29:28,31,30,31,30,31,31,30,31,30,31) as $gm=>$v){
	  if($gd<=$v)break;
	  $gd-=$v;
	 }
	 return($mod=='')?array($gy,$gm,$gd):$gy.$mod.$gm.$mod.$gd." ".$time;
}
  
?> 