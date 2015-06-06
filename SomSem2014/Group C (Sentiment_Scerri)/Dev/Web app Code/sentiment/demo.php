<?php
require_once "server/function.php";
?>
<!DOCTYPE html>
<html >
  <head>
	<meta http-equiv="Content-Language" content="en, fa">
   
    <title>آنالیز حسی</title>
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
    <div class="container" dir="rtl">
     
      <!-- Navs
      ================================================== -->
      <div class="bs-docs-section">
        <div class="row">
         
          
        </div>
      </div>

	 <div class="bs-docs-section">
		 <div class="row">
          <div class="col-lg-12">
			<p id="message"></p>
          </div>
        </div>
		<div class="row">
		 
          <div class="col-lg-12">
			<h3>متن:</h3>
			<div class="form-group">
                    <div class="col-lg-12">
                      <textarea class="form-control" rows="10" id="textArea"></textarea>
                    </div>
            </div>
			<div class="form-group">
                    <div class="col-lg-12" style="margin-top:10px;">
                      <button type='button' style="width: 100px;" class='btn btn-info btn-sm' onclick='sentiment_text();'>اجرا</button>
                    </div>
            </div>
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
