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
//for ($i=1; $i<35; $i++)
//{
$homepage = file_get_html('http://fa.wiktionary.org/w/index.php?title=%D8%B1%D8%AF%D9%87:%D8%B5%D9%81%D8%AA%E2%80%8C%D9%87%D8%A7%DB%8C_%D9%81%D8%A7%D8%B1%D8%B3%DB%8C&pagefrom=%D9%87%D8%B1%D9%81%D8%AA#mw-pages');
	  
	foreach($homepage->find('a') as $element) 
		{
		    $z=$element->title;
		    if ($z!='' || $z!=null )
				if ($z!='رده:صفت‌های فارسی')
				{
					echo $z . '  ';
					$query="select count(*)as count from adjectives where name like '".$z."' ;";
					$result=mysql_query($query);
					$row = mysql_fetch_assoc($result);
					echo $row['count'].'<br>';
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
				break;
				//{break;}
			//$query="INSERT INTO `sentiment`.`adjectives` (`Name`) VALUES ( '".convert_html_to_text($z[1])."');";
			//$result=mysql_query($query);			
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