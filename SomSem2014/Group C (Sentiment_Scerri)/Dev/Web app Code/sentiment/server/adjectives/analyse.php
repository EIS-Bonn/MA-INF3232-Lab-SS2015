<?php
$file = fopen("panalyse.txt", "r");
$i=0;
$j=0;
$k=0;

while(!feof($file)){
     $line = fgets($file);
	
    if (strcmp($line,"Sentiment: p")==9)
			$i++;
	if (strcmp($line,"Sentiment: neg")==7)
			$j++;
	if (strcmp($line,"Sentiment: neu")==6)
			$k++;
}
fclose($file);
$count1=$i+$j+$k;
echo 'Positive amount from '.$count1 .': '.$i .'and '.($i/$count1)*100 .' Percent<br>';
echo 'Negative '. $j.' Neutral '.$k.'<br>';

echo 'amount '.$count1.'<br>';

$pos=$i;
$file = fopen("neganalyse.txt", "r");
$i=0;
$j=0;
$k=0;
while(!feof($file)){
     $line = fgets($file);
	 //echo strcmp($line,"Sentiment: neg").'<br>';
    if (strcmp($line,"Sentiment: p")==9)
			$j++;
	if (strcmp($line,"Sentiment: neg")==7)
			$i++;
	if (strcmp($line,"Sentiment: neu")==6)
			$k++;
}
$count2=$i+$j+$k;
fclose($file);
echo 'Negative amount from '.$count2 .' : '.$i .'and '.($i/$count2)*100 .' Percent<br>';
echo 'Positive '. $j.' Neutral '.$k.'<br>';

echo 'amount '.$count2.'<br>';
$neg=$i;
$file = fopen("neuanalyse.txt", "r");
$i=0;
$j=0;
$k=0;
while(!feof($file)){
      $line = fgets($file);
	// echo strcmp($line,"Sentiment: neu").'<br>';
    if (strcmp($line,"Sentiment: p")==9)
			$k++;
	if (strcmp($line,"Sentiment: neg")==7)
			$j++;
	if (strcmp($line,"Sentiment: neu")==6)
			$i++;
}
fclose($file);
$count3=$i+$j+$k;
echo 'Neutral amount from '.$count3 .' : '.$i .'and '.($i/$count3)*100 .' Percent<br>';
echo 'Negative '. $j.' Positive '.$k.'<br>';

echo 'amount '.$count3.'<br>';
$co=$count3+$count2+$count1;
$z=$i+$neg+$pos;
echo "Accuracy ".($z/$co)*100 .'<br>';
echo 'amount '.$co.'<br>';
?>