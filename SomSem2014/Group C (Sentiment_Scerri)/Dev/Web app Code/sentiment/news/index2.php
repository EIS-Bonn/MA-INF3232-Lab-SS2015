<?php
require_once "server/functions.php";
?>
<!DOCTYPE html>
<html dir="rtl">
  <head>
	<meta http-equiv="Content-Language" content="en, fa">
   
    <title>خبر فارسی</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.css" media="screen">
    <link rel="stylesheet" href="css/bootswatch.min.css">
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="../bower_components/html5shiv/dist/html5shiv.js"></script>
      <script src="../bower_components/respond/dest/respond.min.js"></script>
    <![endif]-->
        <script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-41906386-1', 'computerssl.com');
  ga('send', 'pageview');

</script>
<script src="http://d3js.org/d3.v3.min.js" charset="utf-8"></script>
  </head>
  <body >
	<div class="navbar navbar-default navbar-fixed-top">
      <div class="container">
        
        <div class="navbar-collapse collapse" id="navbar-main">
          

          <ul class="nav navbar-nav navbar-right">
			<li><a href="#political" >سیاسی</a></li>
            <li><a href="#social" >اجتماعی</a></li>
			<li><a href="#economic" >اقتصادی</a></li>
            <li><a href="#sport" >ورزشی</a></li>
			  <div class="navbar-header">
				  <a href="/news/index2.php" class="navbar-brand">خبر فارسی</a>
				  <button class="navbar-toggle" type="button" data-toggle="collapse" data-target="#navbar-main">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				  </button>
				</div>
            
          </ul>

        </div>
      </div>
    </div>
	<div class="container">
     
      <!-- Navs
      ================================================== -->
     <div class="col-lg-12" style="    margin-top: 50px;">
		 <div class="col-lg-10" >
					<!--<h2>خبرها</h2>
					<div class="bs-component">
					  <ul class="nav nav-pills" style="    float: right;">
						
						<li class="active"><a href="#">سیاسی <span class="badge"><?php CAT_news('political'); ?></span></a></li>
						<li class="active"><a href="#">ورزشی <span class="badge"><?php CAT_news('sport'); ?></span></a></li>
						<li class="active"><a href="#">اجتماعی <span class="badge"><?php CAT_news('social'); ?></span></a></li>
						 <li class="active"><a href="#">اقتصادی <span class="badge"><?php CAT_news('economic'); ?></span></a></li> 
					  </ul>
					</div>-->
			</div>
		 <div class="col-lg-2" >
				
			</div>
			
     </div>
	<div class="col-lg-12" >
		<div class="col-lg-10" >
					<div class="bs-component">
					  <ul class="nav nav-pills" style="    float: right;">
						
						<li class="active"><a href="#">سیاسی <span class="badge"><?php CAT_news('political'); ?></span></a></li>
						<li class="active"><a href="#">ورزشی <span class="badge"><?php CAT_news('sport'); ?></span></a></li>
						<li class="active"><a href="#">اجتماعی <span class="badge"><?php CAT_news('social'); ?></span></a></li>
						 <li class="active"><a href="#">اقتصادی <span class="badge"><?php CAT_news('economic'); ?></span></a></li>
						<li class="active"><a href="#">بین الملل <span class="badge"><?php CAT_news('international'); ?></span></a></li>						 
						<li class="active"><a href="#">فرهنگی <span class="badge"><?php CAT_news('culture'); ?></span></a></li>
					  </ul>
					</div>
		
		</div>
		<div class="col-lg-2" >
			<h4>
			<?php date_default_timezone_set("Asia/Tehran"); 
					  echo "ساعت ".Date('H:i:s'); 
					?>
			</h4>
		</div>
		
	</div>
	<div class="col-lg-12" id='sentimentChart'></div>
	<div class="col-lg-12" >
		<hr>
		<div class="col-lg-2">
			
		</div>
		<div class="col-lg-8">
			<div class="col-lg-12"  id='results'>					
						<?php Show_news('political'); ?>					
				</div>
			</div>
					  
        
		<div class="col-lg-2">
			<h3>خبرها</h3>
			<div class="bs-component">
					  <ul  >
						
						<li ><h5><a href="#" onclick="show_news('political')">سیاسی </a></h5></li>
						<li ><h5><a href="#" onclick="show_news('sport')">ورزشی </a></h5></li>
						<li ><h5><a href="#" onclick="show_news('social')">اجتماعی </a></h5></li>
						 <li ><h5><a href="#" onclick="show_news('economic')">اقتصادی </a></h5></li>
						<li ><h5><a href="#" onclick="show_news('international')">بین الملل </a></h5></li>						 
						<li ><h5><a href="#" onclick="show_news('culture')">فرهنگی </a></h5></li>
					  </ul>
			</div>
		</div>
	</div>
		
	</div>
        
    <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/bootswatch.js"></script>
	<script src="js/ajaxall.js"></script>
  </body>
</html>
