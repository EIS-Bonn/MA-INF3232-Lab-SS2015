<html xmlns="http://www.w3.org/1999/xhtml" lang="fa" xml:lang="fa" dir="rtl">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
</head>
<?php
/*after run this script in browser right click in web page and see the inspect element (code s of this page)

this information is rdf/xml and you should use some library like Jena for export your needs 

best regrds,
Mohammad

*/
include 'simple_html_dom.php';
include '../html2text.php';
require_once '../DBconnect.php';
$j=0;
$link=dbconnect();
$po=1;
$ne=1;
$nu=1;
for ($j=1; $j<33; $j++)
{
echo $url='http://www.iran-booking.com/reviews/page/'.$j;
echo '<br>';
$homepage = file_get_html($url);
	  $i=0;
	
					foreach($homepage->find('div') as $element) 
					{
						$txt='';
						$z=$element->class;
						if ($z!='' || $z!=null )
							if ($z=='date' && $i<=10)
							{
								$star= substr( $element,-18,1) . '\n';
							}
							if ($z=='body' && $i<=10)
							{
								$txt=$element->plaintext ;
								if ((int) $star <3){
									$file = fopen("comments/neg/CM".$ne.".txt","w");
									$ne++;
								}
								if ((int) $star >3)
									$file = fopen("comments/pos/CM".$po++.".txt","w");
								if ((int) $star==3)
									$file = fopen("comments/neu/CM".$nu++.".txt","w");
								echo (int)$star;
							    fwrite($file,$txt.PHP_EOL.(int)$star);
								fclose($file);
								
							}
							
					}
					
				
					//$query="select count(*)as count from adjectives where name like '".$z."' ;";
					//$result=mysql_query($query);
					//$row = mysql_fetch_assoc($result);
					/*echo $row['count'].'<br>';
					if ($row['count']==0)
					{
						 echo $query="INSERT INTO `sentiment`.`adjectives` (`Name`) VALUES ( '".convert_html_to_text($z)."');";
						 $result=mysql_query($query);
						
					}
				}
				else{
					 ++$j;
				}
			if ($j==4)
				break;		*/
		}
		

mysql_close();
function convert_html_to_text($html)
{
    //ignores links
    $html2text = new Html2Text($html, false, array('do_links' => 'none', 'width' => 0));
    return $html2text->get_text();
}

 ?>
 </html>