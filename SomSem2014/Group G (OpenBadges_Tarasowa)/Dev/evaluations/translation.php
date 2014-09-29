<?php

define('DS', DIRECTORY_SEPARATOR);
define('ROOT', dirname(dirname(__FILE__)));

// include bootstrap
require_once (ROOT . DS . 'application' . DS . 'config' . DS . 'config.php');
require_once (ROOT . DS . 'libraries' . DS . 'backend' . DS . 'phpQuery' . DS .'phpQuery.php');

include("excelwriter.inc.php");

Class Evaluator extends Model {
    function __construct() {
        $this->initConnection();
//        $excel=new ExcelWriter("translationDeck.xls");
//        if($excel==false)
//        echo $excel->error;
//        $head_array=array("Id","Original","Translated");
//        $excel->writeLine($head_array);
//        $decklist = new DeckList();
//        $originalDecks = $decklist->getAllOriginal();
//        $translationsDecks = $decklist->getAllTranslated();
//        //$slidelist->slides = $slidelist->getAllSlides();
//        foreach ($originalDecks as $deck) {
//                $excel->writeRow();
//                $excel->writeCol($deck->id);
//                $excel->writeCol('1');
//                $excel->writeCol('0');
//        }
//        foreach ($translationsDecks as $deck) {
//                $excel->writeRow();
//                $excel->writeCol($deck->id);
//                $excel->writeCol('0');
//                $excel->writeCol('1');
//        }
//        $excel->close();
//        
//        $excel=new ExcelWriter("translationSlide.xls");
//        if($excel==false)
//        echo $excel->error;
//        $head_array=array("Id","Original","Google translation","Revised");
//        $excel->writeLine($head_array);
//        $slidelist = new SlideList();        
//        $original = $slidelist->getAllOriginal();
//        $translations = $slidelist->getAllTranslated();
//        $revisions = $slidelist->getAllRevised();
//        //$slidelist->slides = $slidelist->getAllSlides();
//        foreach ($original as $slide) {
//                $excel->writeRow();
//                $excel->writeCol($slide->id);
//                $excel->writeCol('1');
//                $excel->writeCol('0');
//                $excel->writeCol('0');
//        }
//        foreach ($translations as $slide) {
//                $excel->writeRow();
//                $excel->writeCol($slide->id);
//                $excel->writeCol('0');
//                $excel->writeCol('1');
//                $excel->writeCol('0');
//        }
//        foreach ($revisions as $slide) {
//                $excel->writeRow();
//                $excel->writeCol($slide->id);
//                $excel->writeCol('0');
//                $excel->writeCol('0');
//                $excel->writeCol('1');
//        }
//        $excel->close();
        
//        $excel=new ExcelWriter("decksStatistics.xls");
//        if($excel==false)
//        echo $excel->error;
//        $head_array=array("Id","Total","Original","Revised","Google");
//        $excel->writeLine($head_array);
//        $deck_array = array(750,9860,2147,1241,1240,1239,1238,1237,1236,1235,9588,9456);
//
//        //$slidelist->slides = $slidelist->getAllSlides(); 9588, 9456, 9860,
//        foreach ($deck_array as $deck_id) {
//            $deck = new Deck();
//            $deck->id = $deck_id;
//            $slides = array();
//            $slides = $deck->getSlidesLite();
//            $total = count($slides);
//            $revised = 0;
//            $original = 0;
//            $google = 0;
//            foreach($slides as $slide){
//                if ($slide->translation_status == 'original') $original++;
//                if ($slide->translation_status == 'revised') $revised++;
//                if ($slide->translation_status == 'google') $google++;
//                if (!($slide->translation_status == 'original' || $slide->translation_status == 'revised' || $slide->translation_status == 'google')) echo $slide->deck->id.' ';
//            }
//                $excel->writeRow();
//                $excel->writeCol($deck_id);
//                $excel->writeCol($total);
//                $excel->writeCol($original);
//                $excel->writeCol($revised);
//                $excel->writeCol($google);
//        }
        
//        $excel=new ExcelWriter("re_translations.xls");
//        if($excel==false)
//        echo $excel->error;
//        $head_array=array("Id","Retranslations");
//        $excel->writeLine($head_array);
//        $list = new DeckList();
//        $list->decks = $list->getAllTranslated();
//
//        //$slidelist->slides = $slidelist->getAllSlides(); 9588, 9456, 9860,
//        foreach ($list->slides as $slide) {
//                $retrans = 0;
//                $revisions = $list->dbQuery('SELECT * FROM deck_revisions WHERE deck_id:=deck_id',array('deck_id' => $deck->id));
//                $excel->writeRow();
//                $excel->writeCol($deck->id);
//                $excel->writeCol($retrans);
//        }
//        
//        $excel->close();
        
        $excel=new ExcelWriter("languages.xls");
        if($excel==false)
        echo $excel->error;
        $head_array=array("Language","Decks","Slides");
        $excel->writeLine($head_array);
        $deck = new Deck();
        $languages = $deck->dbQuery('SELECT DISTINCT language FROM DECK WHERE 1');
        foreach($languages as $lang_string){
            $lang_arr = explode('-', $lang_string['language']);
            $language = $lang_arr[0];
            $excel->writeRow();
            $excel->writeCol($language);
            $lang_full = $lang_arr[0].'-'.$lang_arr[1];
            $number_decks = $deck->dbQuery('SELECT COUNT(*) AS count1 FROM DECK WHERE language=:lang_string',array('lang_string' => $lang_full));
            $excel->writeCol($number_decks[0]['count1']);
            $number_slides = $deck->dbQuery('SELECT COUNT(*) AS count1 FROM SLIDE WHERE language=:lang_string',array('lang_string' => $lang_string['language']));
            $excel->writeCol($number_slides[0]['count1']);
        }
        $excel->close();
        echo "Success.";       
    }
    
    private function initConnection() {
        // connect to db
        if ($this->connect ( DB_DSN, DB_USER, DB_PASSWORD ) == 0)
                die ( "Could not connect to db" );
    }
    
}

function __autoload($className) {
    if (file_exists(ROOT . DS . 'application' . DS . 'library' . DS . $className . '.class.php')) {
            require_once(ROOT . DS . 'application' . DS . 'library' . DS . $className . '.class.php');
    } else if (file_exists(ROOT . DS . 'application' . DS . 'controllers' . DS . $className . '.php')) {
            require_once(ROOT . DS . 'application' . DS . 'controllers' . DS . $className . '.php');
    } else if (file_exists(ROOT . DS . 'application' . DS . 'models' . DS . $className . '.php')) {
            require_once(ROOT . DS . 'application' . DS . 'models' . DS . $className . '.php');
    } else {
            if ($className != strtolower($className))
            {
                    __autoload(strtolower($className));
            }
            else
            {
                    /* Error Generation Code Here */
            }
    }
}
new Evaluator();

?>