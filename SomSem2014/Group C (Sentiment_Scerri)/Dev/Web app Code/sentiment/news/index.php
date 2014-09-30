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
  </head>
  <body >
	<div class="container">
     
      <!-- Navs
      ================================================== -->
     <div class="col-lg-12">
            <h2>خبرها</h2>
            <div class="bs-component">
              <ul class="nav nav-pills" style="    float: right;">
                
                <li class="active"><a href="#">سیاسی <span class="badge"><?php CAT_news('political'); ?></span></a></li>
                <li class="active"><a href="#">ورزشی <span class="badge"><?php CAT_news('sport'); ?></span></a></li>
				<li class="active"><a href="#">اجتماعی <span class="badge"><?php CAT_news('social'); ?></span></a></li>
				 <li class="active"><a href="#">اقتصادی <span class="badge"><?php CAT_news('economic'); ?></span></a></li> 
              </ul>
            </div>
     </div>
	
	<div class="row" >
			
          <div class="col-lg-3">
			<h3>اجتماعی</h3>
            <div class="bs-component">
              <div class='well'>
                <?php Show_news('social'); ?>
              </div>
            </div>
          </div>
          <div class="col-lg-3">
			<h3>ورزشی</h3>
            <div class="bs-component">
				<div class='well'>
					<?php Show_news('sport'); ?>
				</div>
            </div>
          </div>
          <div class="col-lg-3">
			<h3>سیاسی</h3>
            <div class="bs-component">
				<div class='well'>
					<?php Show_news('political'); ?>
				</div>	
            </div>
          </div>
		  <div class="col-lg-3">
			<h3>اقتصادی</h3>
            <div class="bs-component">
				<div class='well'>
					<?php Show_news('economic'); ?>
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
