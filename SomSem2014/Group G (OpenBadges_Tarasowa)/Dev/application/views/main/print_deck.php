<!DOCTYPE html>
<html>
<head>
<base href="<?php echo BASE_PATH?>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="libraries/frontend/deck.js/core/deck.core.css" type="text/css" media="all" />
<link rel="stylesheet" href="static/css/main.css" type="text/css" media="all" />
<!-- MathJax -->
<script type="text/javascript" src="libraries/frontend/MathJax/MathJax.js?config=default"></script>
	<!-- jquery -->
<script type="text/javascript" src="libraries/frontend/jquery.min.js"></script>

<!-- Code Mirror -->
<link rel="stylesheet" href="libraries/frontend/codemirror/lib/codemirror.css">
<script src="libraries/frontend/codemirror/lib/codemirror.js"></script>
<script src="libraries/frontend/codemirror/mode/javascript/javascript.js"></script>
<script src="libraries/frontend/codemirror/mode/xml/xml.js"></script>
<script src="libraries/frontend/codemirror/mode/htmlmixed/htmlmixed.js"></script>
<script src="libraries/frontend/codemirror/mode/css/css.js"></script>
<script src="libraries/frontend/codemirror/mode/php/php.js"></script>
<script src="libraries/frontend/codemirror/mode/ntriples/ntriples.js"></script>
<script src="libraries/frontend/codemirror/mode/sparql/sparql.js"></script>
<script src="libraries/frontend/codemirror/lib/util/formatting.js"></script>

<script src="static/js/view-spec/print_deck.js"></script>
<title><?php echo $deckObject->title; ?></title>
<style>
@media print{@page {size: landscape}}

</style>
</head>
<body class="deck-container" id="slide-area">
<?php
foreach ($deckObject->slides as $slide)
{
 echo'<div class="slide'.($slide->position==1?($slide->deck==$deckObject->id?' first-slide':' first-sub-slide'):'').'" id="tree-'.$slide->deck.'-slide-'.$slide->id.'-'.$slide->position.'-view">
	<div class="slide-content">
		<div class="slide-header">
		<h2>
			<div class="slide-title">'.$slide->title.'
			</div>
		</h2>
		</div>
		<div class="slide-body" style="font-size:24px !important;">'.$slide->body.'
		</div>
		<div class="slide-footer">'.$deckObject->footer_text.'
		</div>
		<div class="slide-metadata">
		</div>
	</div>
</div>'.PHP_EOL;	
}
?>	
<script>
    $(".code").each(function(i,v) {
		$(v).attr('id','code'+i);	
    });
	 $(".code").each(function(i,v) {
		var mode = $(v).attr('mode');
		var ce = document.getElementById($(v).attr('id'));
		// Probably needs more kludges to be portable... you get the idea
		var text = ce.textContent || ce.innerText;
		var editor = CodeMirror(function(node){ce.parentNode.replaceChild(node, ce);}, {
		  value: text,
		  mode: mode,
		  lineNumbers: true,
		  lineWrapping: true,
		readOnly: true,
		});
    });
</script>
</body>
</html>
