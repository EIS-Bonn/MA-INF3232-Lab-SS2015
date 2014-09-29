<?php
define('DS', DIRECTORY_SEPARATOR);
define('ROOT', dirname(__FILE__));
include("excelwriter.inc.php");
$excel=new ExcelWriter("translation1.xls");

if($excel==false)
echo $excel->error;
$head_array=array("Id","Translated");
$excel->writeLine($head_array);
$k=0;
$decklist = new DeckList();
$slidelist = new SlideList();
$originalDecks = $decklist->getAllOriginal();
$translationsDecks = $decklist->getAllTranslated();
//$slidelist->slides = $slidelist->getAllSlides();


foreach ($originalDecks as $deck) {
        $excel->writeCol($deck->id);
        $excel->writeCol('original');
}

foreach ($translationsDecks as $deck) {
        $excel->writeCol($deck->id);
        $excel->writeCol('translated');
}
$excel->close();
echo "Success.";
?>