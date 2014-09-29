<header class="page-header">
	<h1>Compile SCSS to CSS</h1>
</header>

<article>
<form method="post" action="">
<div class="clearfix">
	<label for="source">SCSS:</label>
	<div class="input">
		<textarea  id ="source" name="source" class="span6" /><?php echo $input;?></textarea>
	</div>
</div>
<div class="actions">
<input type="submit" name="submit" class="btn primary"  value="Compile" />
</div>
</form>
<div id="output">
<?php echo $css;?>
</div>
</article>

<hr>


<footer>

</footer>