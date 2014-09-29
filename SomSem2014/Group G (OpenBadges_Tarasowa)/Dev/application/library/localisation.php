<?php
$locale = Locale::acceptFromHttp($_SERVER['HTTP_ACCEPT_LANGUAGE']);
ini_set('intl.default_locale', 'en-GB');

if ($locale) {
    $supported = array("en_GB.UTF-8","ru_RU.UTF-8","nl_NL.UTF-8","de_DE.UTF-8");
    if (!in_array($locale . '.UTF-8', $supported)){
        $locale = substr($locale, 0, 2);
        $i=0;
        
        while ($locale != substr($supported[$i], 0,2) && $i < count($supported)){
            $i++;
        }
        if ($i != count($supported)){
            $locale = $supported[$i];
        }else{
            $locale = locale_get_default() . ".UTF-8";
        }
    } else{
        $locale .= '.UTF-8';
    }
    // echo "<font color='#fff'>" . $locale ."</font>";

   
    $dir = $_SERVER['DOCUMENT_ROOT']. '/locale';

    putenv("LANG=$locale");
    putenv("LANGUAGE=$locale");
    putenv("LC_ALL=$locale");
    putenv("LC_MESSAGES=$locale");
    setlocale(LC_ALL, $locale);
    //setlocale(LC_MESSAGES, $locale);
    bindtextdomain("slidewiki", $dir);
    textdomain("slidewiki");
    
}  
?>


