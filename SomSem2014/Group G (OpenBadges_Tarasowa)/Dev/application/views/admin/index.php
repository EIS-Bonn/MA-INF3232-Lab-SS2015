<?php 
if($alert):
?>
<header class="page-header">
	<h1>
		SlideWiki Admin
	</h1>
</header>
<article>
<div class="content row">
<div class="span6">
<h2>Delete deck # </h2><input typer="text" id="deck_id" class="span2">
<a class="btn" onclick="showConfirmation('deck','delete');"> Delete deck after confirmation</a>
</div>
<div class="span6">
<h2>Delete user # </h2><input typer="text" id="user_id" class="span2">
<a class="btn" onclick="showConfirmation('user','delete');"> Delete user after confirmation</a>
</div>
<div id="delete_confirmation">

</div>
</div>
</article>
<?php else: ?>
<div class="alert alert-block  alert-warning fade in" data-alert="alert">
    <h2>Permission denied!</h2>
</div>
<?php endif;?>
<script>
function showConfirmation(item_type,action){
	if(item_type=='deck' && action=='delete'){
		alert('deck');
		return;
	}
	if(item_type=='user' && action=='delete'){
		alert('user');
		return;
	}
}
</script>
