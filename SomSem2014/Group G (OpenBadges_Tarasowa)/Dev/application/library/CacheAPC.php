<?php

class CacheAPC {

    public $iTtl = 600; // Time To Live
    public $bEnabled = false; // APC enabled?

    // constructor
    function __construct() {
        $this->bEnabled = true;

    }
    // get data from memory
    function getData($sKey) {
        $cache_file = 'cache/'.$sKey.'.cache';
        if(file_exists($cache_file)){
            if(time()-$this->iTtl > filemtime($cache_file)){
                $this->delData($sKey);
                return false;
            }else{
                $vData=file_get_contents($cache_file);
                return $vData;
            }
        }else{
            return false;
        }

    }

    // save data to memory
    function setData($sKey, $vData) {
        $cache_file = 'cache/'.$sKey.'.cache';
        file_put_contents($cache_file, $vData);
    }

    // delete data from memory
    function delData($sKey) {
        $cache_file = 'cache/'.$sKey.'.cache';
        @unlink($cache_file);
    }
}

?>
