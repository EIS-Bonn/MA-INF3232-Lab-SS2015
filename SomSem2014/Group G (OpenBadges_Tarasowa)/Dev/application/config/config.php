<?php

/** Configuration Variables **/
// enable error reporting
define ('DEVELOPMENT_ENVIRONMENT',true);

// db stuff
define('DB_DSN', 'mysql:dbname=slidewiki;host=localhost;charset=utf8');
define('DB_NAME', 'slidewiki');
define('DB_USER', 'root');
define('DB_PASSWORD', '');
//base path for mode rewrite
define('BASE_PATH', 'http://localhost/slidewiki/');

//playing by KeyNode.JS
define('KEYNODE_ENABLED',"true");
define('KEYNODE_SERVER', 'http://localhost:4444');
define('KEYNODE_PRESENTER', 'http://localhost:4444/presenter/');
define('KEYNODE_WATCHER', 'http://localhost:4444/watcher/');



