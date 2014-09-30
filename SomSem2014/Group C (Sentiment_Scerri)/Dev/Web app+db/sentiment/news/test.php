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
include 'server/simple_html_dom.php';
include 'server/html2text.php';
require_once 'server/DBconnect.php';

$link=dbconnect();


 $url='http://www.tabnak.ir/fa/news/421912/%DA%A9%D8%B4%D8%AA%D8%A7%D8%B1-%D9%85%D8%B1%D8%AF%D9%85-%D8%BA%D8%B2%D9%87-%D8%AD%D8%A7%D8%B5%D9%84-%D8%B9%D9%84%D9%85%E2%80%8C%D9%81%D8%B1%D9%88%D8%B4%DB%8C-%D8%AF%D8%A7%D9%86%D8%B4%D9%85%D9%86%D8%AF%D8%A7%D9%86';
 '<br>';
 $homepage = file_get_html($url);
	  $i=0;
	
					foreach($homepage->find('div') as $element) 
					{
						$txt='';
						$z=$element->class;
						if ($z!='' || $z!=null )
							
							if ($z=='inner_content_news' && $i<=10)
							{
								echo $txt=$element->plaintext ;								
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
		
		

mysql_close();
function convert_html_to_text($html)
{
    //ignores links
    $html2text = new Html2Text($html, false, array('do_links' => 'none', 'width' => 0));
    return $html2text->get_text();
}

 ?>
 </html>