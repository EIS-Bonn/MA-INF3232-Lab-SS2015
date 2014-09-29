<?php
	define('DS', DIRECTORY_SEPARATOR);
	define('ROOT', dirname(dirname(__FILE__)));
	
	// include bootstrap
	require_once (ROOT . DS . 'application' . DS . 'config' . DS . 'config.php');
	require_once (ROOT . DS . 'cronjobs' . DS . 'calculate_deck_popularity.php');
	require_once (ROOT . DS . 'cronjobs' . DS . 'calculate_slide_popularity.php');

	class CalculatePopularity {
		function __construct() {
			new CalculateDeckPopularity;
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
	
	new CalculatePopularity;
?>