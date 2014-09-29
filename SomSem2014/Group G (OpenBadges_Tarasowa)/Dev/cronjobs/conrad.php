<?php
define('DS', DIRECTORY_SEPARATOR);
define('ROOT', dirname(dirname(__FILE__)));

// include bootstrap
require_once (ROOT . DS . 'application' . DS . 'config' . DS . 'config.php');
require_once (ROOT . DS . 'libraries' . DS . 'backend' . DS . 'phpQuery' . DS .'phpQuery.php');

$german = new Deck();
$german->createFromID(791);
$german->content = $german->fetchDeckContent();
$english = new Deck();
$english->createFromID(788);
$english->content = $english->fetchDeckContent();
for ($i=0, $j=0; $i < count($german->content), $j < count($english->content); $i++, $j++){
    if (get_class ( $english->content[$j] ) == 'Deck'){
        $j++;
    }else{
        echo $german->content[$i]->id;
        echo '<br>';
        $german->content[$i]->translator_id = 105;        
        $german->content[$i]->translated_from = $english->content[$j]->slide_id;
        $german->content[$i]->translated_from_revision = $english->content[$j]->id;
        $german->content[$i]->dbQuery('UPDATE slide SET translated_from=:translated_from,translated_from_revision=:translated_from_revision WHERE id=:id',array('translated_from' => $german->content[$i]->translated_from, 'translated_from_revision'=>$german->content[$i]->translated_from_revision));
        $german->content[$i]->removeGoogle();
        
    }
}

        
?>
