
<?php include('inc/header.php');
session_start();
?>
<div class="container-narrow">
<div class="masthead">
	
	<h1 class="muted">Importing</h1>
	<h3>This page will close after importing from Mozilla Backpack is complete ..!!</h3>
</div>
<script type="text/javascript">
setTimeout(function(){
window.open("http://localhost/slidewiki/?url=user/badges");
},2000);

setTimeout(function(){
	window.close();
},2000);


</script>
<?php

$mozilla_id=$_SESSION['mid'];
$mozilla_grp=$_SESSION['mgrp'];
$sw_id=$_SESSION['uid'];
$con=mysqli_connect('localhost','root','','slidewiki');
// Check connection
if (mysqli_connect_errno()) {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
$sql = mysqli_query($con,"SELECT count(*) FROM badge WHERE slidewiki_id=$sw_id;");
$row = mysqli_fetch_array($sql);

if ($row['count(*)'] ==0 ){
	mysqli_query($con,"INSERT INTO badge (slidewiki_id,mb_user_id,group_id) VALUES ('$sw_id', '$mozilla_id','$mozilla_grp')");

}?>

<div id="preview">. . .</div>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript">
