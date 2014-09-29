
<?php require_once (ROOT . DS . 'application' . DS . 'views' . DS . 'activity_templates.php'); ?>
<? session_start();
$test_ID = $_COOKIE[testID];?>
<script src="libraries/frontend/deck.js/modernizr.custom.js"></script>
<script src="libraries/frontend/deck.js/core/deck.core.js"></script>
<script src="libraries/frontend/deck.js/extensions/menu/deck.menu.js"></script>
<script src="libraries/frontend/deck.js/extensions/goto/deck.goto.js"></script>
<script src="libraries/frontend/deck.js/extensions/status/deck.status.js"></script>
<script src="libraries/frontend/deck.js/extensions/navigation/deck.navigation.js"></script>
<script src="libraries/frontend/deck.js/extensions/hash/deck.hash.js"></script>       
<script src="static/js/view-spec/playq.js"></script>
<script src="libraries/frontend/deck.js/extensions/scale/deck.scale.questions.js"></script>
<script type="text/javascript" src="libraries/frontend/jquery-tmpl/jquery.tmpl.min.js"></script>
<script src="static/js/questions.js"></script>
<script src="static/js/view-spec/playq.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>    



<script type="text/javascript">
getProfile(<?php echo $_GET['id']; ?>);
filterFollowers(<?php echo $_GET['id']; ?>,'');
getUserStream(<?php echo $_GET['id']; ?>,'','');

</script>
<?php $_SESSION['swiki_id'] = $_GET['id'];?>

 <header class="page-header row">
    <div class="span14">
        <h2><?php echo "Displaying Badges For"?><br>
        
            <img src="./?url=ajax/getAvatarSrc&id=<?php echo trim($profile->id);?>" height="50" width="50" class="avatar" />
                <?php echo $_GET['id'];?>
                <span class="r_entity r_profilepage" itemscope itemtype="http://schema.org/ProfilePage">
                	<meta itemprop="url" content="./user/<?php echo trim($profile->id);?>" />
	            	<span class="r_prop r_name" itemprop="name"><?php echo trim($profile->username);?></span></h2>
		        
		        
		            </span>
          	  </span>    
            	
              
    </div>

</header>

<!-- ---------------------------------------------------- -->	

<!--  $test_ID = $_COOKIE[testID]; -->
<!-- Display Badges --> 
<div id="badges">. . .</div>

<?php $sw_id=$profile->id;
$con=mysqli_connect('localhost','root','','slidewiki');
// Check connection
if (mysqli_connect_errno()) {
	echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
$sql = mysqli_query($con,"SELECT count(*) FROM badge WHERE slidewiki_id=$sw_id;");
$row = mysqli_fetch_array($sql);
//Check if badges have already been imported or not, no record found! imform user to import badges
if ($row['count(*)'] == 0 ){
	echo "<h3>You do not have any badges stored in the system.<br>To import badges use import feature.</h3>";
	
}else 
	 //badges record found! retrieve information from database and display
    $sql = mysqli_query($con,"SELECT mb_user_id,group_id FROM badge where slidewiki_id=$sw_id");
    $row = mysqli_fetch_array($sql);
	$mozilla_id= $row['mb_user_id'];
	$mozilla_grp=$row['group_id'];
	echo "<script src='http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js'></script>";
	echo "<script>";
	echo 'var url = "http://beta.openbadges.org/displayer/' . $mozilla_id . '/group/' . $mozilla_grp . '.json";';
	echo 'var widgetcode = "<table>"';
	?>

$.getJSON(url,
    function(data) {
   
        var i=0;
        var widgetcode = "<table>";
        while (i < data.badges.length < 4) {
            widgetcode = widgetcode + "<td align='center'>";
            badgeName = data.badges[i].assertion.badge.name;
            imgUrl = data.badges[i].assertion.badge.image;
            critUrl = data.badges[i].assertion.badge.criteria;
            critUrl = critUrl +"?deck="+ '<?php echo $test_ID ;?>';
           <!--   assertUrl = data.badges[i].hostedUrl;-->
            assertUrl ="./?url=user/badgeDescription";
           <!--widgetcode = widgetcode + "<a href='" + assertUrl + "'><img src='"+ imgUrl +"' width='180' height='150' border='0'/></a><br /></a><br>Criteria:<a href='" + critUrl + "'>" + badgeName +"</a>";-->          
            widgetcode = widgetcode + "<a href='" + assertUrl + "'><img src='"+ imgUrl +"' width='180' height='150' border='0'/></a><br /></a><br>Badge: " + badgeName +"</a>";
            widgetcode = widgetcode + "</td>";
            i = i+1;
                if (i === data.badges.length) {
                widgetcode = widgetcode + "</table>";
                document.getElementById("badges").innerHTML=widgetcode;
                return;
                
                
                }
        }
    }
); 
</script>
