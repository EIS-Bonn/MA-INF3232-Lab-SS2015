

	

 <script type="text/javascript">
 
//window.open("http://www.google.com",'name','height=430,width=800');
var str = document.cookie;
var count = str.match(/salmansiddiqui:/g);
alert(count);
</script>
 
<?php 

/*echo $_COOKIE[q_Occur];
echo "<script src='http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js'></script>";
echo "<script>";
echo 'var url = "http://localhost/slidewiki/deck/'.$test_ID;
echo "</script>";
$a = "http://localhost/slidewiki/deck/"."$test_ID";
echo $_COOKIE[testLink];
?>
<?php $main = "http://localhost/slidewiki/deck/";
                              $test_id = $test_ID;
                              $link_title = "Link Title";
echo '<a href="'.$main.''.$test_id.'">'.$link_tit.'</a>'; ?>


<script src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
<script>
                             
$.getJSON('1-.json', function (json) {
	
    var a = json.toSource();
   // var b = a.contains("http://localhost");
    var count = a.match(/localhost/g);

    //var b = a.slice(114,146);
    alert(count.length);
});



</script>
<!--  <script>
var a="salman";
a = a+ '<?php echo $test_ID ;?>';
alert(a);
</script>
 <!--  var a = data.toSource();
  var pos = a.slice(318,357);
  alert(pos); -->*/
 


/*$con=mysqli_connect('localhost','root','','slidewiki');
// Check connection
if (mysqli_connect_errno()) {
	echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
$sql = mysqli_query($con,"SELECT count(*) FROM `deck_revision` where user_id=360 and not translated_from_revision = 'NULL';");
$row = mysqli_fetch_array($sql);
if ($row['count(*)'] == 50 ){
echo "You have reached Basic Translation Badge criteria claim your badge";	
}
if ($row['count(*)'] >50 && $row['count(*)']<=100 ){
	echo "You have reached Intermediate Translation Badge criteria claim your badge";	
}
	
if ($row['count(*)'] >100 ){
	echo "You have reached Advanced Translation Badge criteria claim your badge";
}

*/

?>




