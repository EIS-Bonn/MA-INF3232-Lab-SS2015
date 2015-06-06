<meta http-equiv="Content-Language" content="en, fa">

<?php 
include('server/DBconnect.php');

	$link=dbconnect();
	  $query="SELECT * FROM `news_sense` where set_sent=3 limit 0,100  ";
	  $result=mysql_query($query);
	  while($row = mysql_fetch_assoc($result))
	  {
		echo strlen($row['txt']).'  '. $row['id'].'<br><br>';
		if (strlen($row['txt'])>0 && strlen($row['txt'])<31000  )
		{
			 echo date('h:i:s') . "\n";
			  $sent=get_sentiment ($row['txt']);
			  print_r($sent);
			  echo date('h:i:s') . "\n";
			  echo "<br>";
			  if (!isset($sent))
			  {
				   $sent='neutral';
			  }else
			  {
				  echo $query="UPDATE `news_sense` SET `sentiment` = '".$sent['sentiment']."', `set_sent` = '1' WHERE `id` = ".$row['id'].";";
				   echo "<br>";				   
				  $result1=mysql_query($query);
			  }
		}else 
		{
			echo $query="UPDATE `news_sense` SET `sentiment` = 'neutral', `set_sent` = '1' WHERE `id` = ".$row['id'].";";
			 echo "<br>";		
			$result1=mysql_query($query);
		}
	  }
	mysql_close();
function get_sentiment($txt)
{
        $soapClient = new SoapClient("http://localhost:8080/PersianSentiment/services/Sentiment?wsdl"); 
    
        // Prepare SoapHeader parameters 
        $sh_param = array( 
                    'Username'    =>    '', 
                    'Password'    =>    ''); 
        $headers = new SoapHeader('http://localhost:8080/PersianSentiment/services/Sentiment', 'UserCredentials', $sh_param); 
    
        // Prepare Soap Client 
        $soapClient->__setSoapHeaders(array($headers)); 
    
        // Setup the RemoteFunction parameters 
		
        $ap_param = array( 
                    'text'     =>    $txt); 
                    
        // Call RemoteFunction () 
        $error = 0; 
        try { 
            $info = $soapClient->__call("persian_sentiment", array($ap_param)); 
        } catch (SoapFault $fault) { 
            $error = 1; 
            print(" 
            alert('Sorry, blah returned the following ERROR: ".$fault->faultcode."-".$fault->faultstring.". We will now take you back to our home page.'); "); 
        } 
        
        if ($error == 0) {        
             $auth_num = $info->persian_sentimentReturn; 
            unset($soapClient); 
			return $auth_num;
           }
		
  }  

    
?>