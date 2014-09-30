<?php
require_once 'DBconnect.php';
include 'html2text.php';
require_once 'phpQuery.php';
// getStoreTweets('mehrodad',20);
if(isset($_GET['action']) && $_GET['action']=='insert')
{
		    $user=$_GET['Tuser'];
			$amount=$_GET['Tamount'];
		     getStoreTweets($user,$amount);
			count_word($user);
		
}

function getStoreTweets($username, $count)
{
	
    $output = array();
    $aggregated = array();
    $aggregate = '';
    $counter = 0;
     $dump = get_web_page('https://twitter.com/i/profiles/show/' . $username . '/timeline/with_replies?include_available_features=1&include_entities=1');
    $obj = json_decode($dump ['content']);
     $has_more = $obj->has_more_items;
     $doc = phpQuery::newDocument($obj->items_html);
	 $link=dbconnect();
    foreach (pq('li') as $item) {		
        if (count($output) >= $count) {
            if ($aggregate) {
                $aggregated [] = $aggregate;
                
				$query="INSERT INTO `sentiment`.`sentences` (`sentence`,`Name`) VALUES ( '".convert_html_to_text($aggregate)."','".$username."');";
				$result=mysql_query($query);
				
                $counter = 0;
                $aggregate = '';
            }
            //return $aggregated;
        } else {
			if ($id = pq($item)->attr('data-item-id')) {
                $ids [] = $id;
            } else {
                continue;
            }
           
			$tweet_url = 'http://twitter.com/'.$username.'/statuses/'.pq($item)->attr('data-item-id');
            $timestamp = pq($item)->find('.time')->find('._timestamp')->attr('data-time');
             $tweet_text = pq($item)->find('.tweet-text')->html();
            $output [] = $tweet_text . '<meta name="url" content="' . $tweet_url . '"><meta name="timestamp" content="' . $timestamp . '">';
            $aggregate =  $tweet_text . '<meta name="url" content="' . $tweet_url . '"><meta name="timestamp" content="' . $timestamp . '">';
            $counter++;
			
			 $query="INSERT INTO `sentiment`.`sentences` (`sentence`,`Name`) VALUES ( '".convert_html_to_text($aggregate)."','".$username."');";
				//echo $query.'<br>';
				$result=mysql_query($query);
				
                $aggregated [] = $aggregate;
                $counter = 0;
                $aggregate = '';
            
        }
    }
    $last_id = $ids [count($ids) - 1];
	
    while ((count($output) < $count) && $has_more) {
	
       $dump = get_web_page('https://twitter.com/i/profiles/show/' . $username . '/timeline/with_replies?include_available_features=1&include_entities=1&max_id=' . $last_id . '&oldest_unread_id=0');
        $obj = json_decode($dump ['content']);
        $has_more = $obj->has_more_items;
         $doc = phpQuery::newDocument($obj->items_html);
        $ids = array();
		
        foreach (pq('li') as $item) {
            if (count($output) >= $count) {
                if ($aggregate) {
                   
				$query="INSERT INTO `sentiment`.`sentences` (`sentence`,`Name`) VALUES ( '".convert_html_to_text($aggregate)."','".$username."');";
				//echo $query.' <br>';
				$result=mysql_query($query);
				
                    $aggregated [] = $aggregate;
                    $counter = 0;
                    $aggregate = '';
                }
                //return $aggregated;
            } else {
                if ($id = pq($item)->attr('data-item-id')) {
                    $ids [] = $id;
                } else {
                    continue;
                }
				$tweet_url = 'http://twitter.com/'.$username.'/statuses/'.pq($item)->attr('data-item-id');
                $timestamp = pq($item)->find('.time')->find('._timestamp')->attr('data-time');
                $tweet_text = pq($item)->find('.tweet-text')->html();
				$output [] = $tweet_text . '<meta name="url" content="' . $tweet_url . '"><meta name="timestamp" content="' . $timestamp . '">';
                $aggregate =  $tweet_text . '<meta name="url" content="' . $tweet_url . '"><meta name="timestamp" content="' . $timestamp . '">';
                $counter++;
                
                    
					$query="INSERT INTO `sentiment`.`sentences` (`sentence`,`Name`) VALUES ( '".convert_html_to_text($aggregate)."','".$username."');";
					//echo $query.' <br>';
					$result=mysql_query($query);
					
                    $aggregated [] = $aggregate;
                    $counter = 0;
                    $aggregate = '';
                
            }
        }
        if (count($ids)) {
            $last_id = $ids [count($ids) - 1];
        } else {
            $last_id = 0;
        }
    }
	
    $query="SELECT * FROM sentences WHERE Name Like '".$username."' ";
	$result=mysql_query($query);
	while ($row = mysql_fetch_assoc($result)) {
		echo "<div class='bs-component' id='sent".$row['ID']."'>
				<div class='alert alert-dismissable alert-info'>
					<h4 align='right'>".$row['sentence']."</h4>
					<input type='hidden' id='IDs' value='".$row['ID']."'/>
					<p align='right'><button type='button' class='btn btn-danger btn-sm' onclick='Sdelete(".$row['ID'].");'>Delete</button> 
						
					</p>
				</div>
			  </div>";
	}
	mysql_close();
   
}

function convert_html_to_text($html)
{
    //ignores links
    $html2text = new Html2Text($html, false, array('do_links' => 'none', 'width' => 0));
    return $html2text->get_text();
}

function get_web_page($url)
{
    $options = array(CURLOPT_RETURNTRANSFER => true, // return web page
        CURLOPT_HEADER => false, // don't return headers
        CURLOPT_FOLLOWLOCATION => true, // follow redirects
        CURLOPT_ENCODING => "", // handle all encodings
        CURLOPT_USERAGENT => "spider", // who am i
        CURLOPT_AUTOREFERER => true, // set referer on redirect
        CURLOPT_CONNECTTIMEOUT => 120, // timeout on connect
        CURLOPT_TIMEOUT => 120, // timeout on response
        CURLOPT_MAXREDIRS => 10, // stop after 10 redirects
        CURLOPT_SSL_VERIFYPEER => false); // Disabled SSL Cert checks


    $ch = curl_init($url);
    curl_setopt_array($ch, $options);
    $content = curl_exec($ch);
    $err = curl_errno($ch);
    $errmsg = curl_error($ch);
    $header = curl_getinfo($ch);
    curl_close($ch);

    $header ['errno'] = $err;
    $header ['errmsg'] = $errmsg;
    $header ['content'] = $content;
    return $header;
}


function count_word($user)
{
	$count=0;
	$link=dbconnect();
    $query="SELECT * FROM sentences where Name like '".$user."'  ";
	$result=mysql_query($query);
	while ($row = mysql_fetch_assoc($result)) 
	{
		$text = explode(' ',$row['sentence']);
		for ($i=0;$i<count($text);$i++)
		{
			 $query="INSERT INTO words (word)
						SELECT * FROM (SELECT '".$text[$i]."') AS tmp
						WHERE NOT EXISTS (
							SELECT word FROM words WHERE word = '".$text[$i]."'
						) LIMIT 1";
			$result1=mysql_query($query);
		}
		$count=$count+count($text);
	}
	//echo $count;
}

?>