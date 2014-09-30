<?php
require_once "classes/SlideWiki.php";

// initialize a session 
session_start(); 

if(!isset($_SESSION['uid']) ){
	header( 'Location: ../index.php' ) ;
};
// $_SESSION['uid']=1;

$sw = new SlideWiki();

$swl=$sw->createDeck('Semantic Web lecture','Newly created');
$sw->insertNewSlideToDeck('<h2>Motivation</h2>The Semantic Web is important because<ul><li>Data and information needs to be integrated</li></ul>', $swl);

$rdf=$sw->createDeck('RDF');
$sw->insertNewSlideToDeck('<h2>Overview</h2>This lecture adresses<ul><li>RDF data model and serialization</li><li>Semantics</li></ul>', $rdf);
$sw->insertDeckToDeck($rdf,$swl);

$rdfs=$sw->createDeck('RDF-Schema');
$sw->insertNewSlideToDeck('<h2>Overview</h2>This lecture adresses<ul><li>Syntax and intuition</li><li>Semantics</li></ul>', $rdfs,NULL,'New slide created');
$sw->insertDeckToDeck($rdfs,$swl);

$owl=$sw->createDeck('OWL');
$sw->insertNewSlideToDeck('<h2>Overview</h2>This lecture adresses<ul><li>Syntax and intuition</li><li>Semantics</li></ul>', $owl);
$sw->insertNewSlideToDeck('<h2>The Cauchy-Schwarz Inequality</h2><div class="math-header">The Cauchy-Schwarz Inequality</div>

\\[ \\left( \\sum_{k=1}^n a_k b_k \\right)^2 \\leq \\left( \\sum_{k=1}^n a_k^2 \\right) \\left( \\sum_{k=1}^n b_k^2 \\right) \\]', $owl);

$sw->insertDeckToDeck($owl,$swl);



$sparql=$sw->createDeck('SPARQL lecture');
$sw->insertNewSlideToDeck('<h2>Overview</h2>This lecture adresses<ul><li>Syntax and intuition</li><li>Semantics</li></ul>', $sparql);
$sw->insertDeckToDeck($sparql,$swl);

$sw->insertNewSlideToDeck('<h2>Conclusions</h2>The Semantic Web has a bright future because<ul><li>there is RDF and SPARQL</li></ul>', $swl);

$sw->dbQuery('INSERT INTO users VALUES (NULL,\'soeren.auer@gmail.com\',\'soeren\',\'\',NULL)');

$sw->addStyle('neon',file_get_contents('..\js\deck.js\themes\style\neon.css'));
$sw->addStyle('swiss',file_get_contents('..\js\deck.js\themes\style\swiss.css'));
$sw->addStyle('web-2.0',file_get_contents('..\js\deck.js\themes\style\web-2.0.css'));

print_r($sw->getDeckContent($swl));

$object = $_REQUEST['object'];

switch($object->action) {
	case "createdeck":
		$sw->createDeck();
}
?>
