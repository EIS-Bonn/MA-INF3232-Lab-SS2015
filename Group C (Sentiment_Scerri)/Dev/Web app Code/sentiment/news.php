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
	<link rel="stylesheet" href="css/custom.css" >
    <link rel="stylesheet" href="css/bootswatch.min.css">
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="../bower_components/html5shiv/dist/html5shiv.js"></script>
      <script src="../bower_components/respond/dest/respond.min.js"></script>
    <![endif]-->
	<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/bootswatch.js"></script>
	<script src="js/ajaxall.js"></script>
    <script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-41906386-1', 'computerssl.com');
  ga('send', 'pageview');

</script>
  </head>
  <body dir="rtl" >
    <div class="container">
		<div class="bs-docs-section">
        <div class="row">
         
          <div class="col-lg-12">
            <div class="bs-component">
              <ul class="nav nav-pills" style="    float: right;">
			  <li class="active"><a href="#">  تعداد آرا بدون تکرار<span class="badge"> <?php allvote('news'); ?></span></a></li> 
				<li class="active"><a href="#"> تعداد کل آرا<span class="badge"> <?php allvote('news1'); ?></span></a></li> 
                <li class="active"><a href="#"> تمام خبرها<span class="badge"> 1170</span></a></li>
				 
              </ul>
            </div>
          </div>
        </div>
      </div>
      <!-- Navs
      ================================================== -->

	 <div class="bs-docs-section">
		 <div class="row">
          <div class="col-lg-12">
			<h3 >
دوستان گرامی با تشکر از همکاری شما در انجام این پروژه ما برای تست عملکرد سیستم آنالیز حس متن فارسی نیاز داریم نظر یا حس شما  را در مورد  اخبار قرار داده شده در سایت جویا شویم تا سیستم ما بتواند عملکرد دقیق تر و نزدیک تری به نظر کاربران فارسی داشته باشد. برای مشاهده متن کامل هر خبر کافی است بر روی تیتر خبر کلیک کرده و بعد از مطالعه خبر حس خود را نسبت به آن به صورت مثبت (positive) , منفی (negative), خنثی (neutral) انتخاب کنید. 
</h3>
			<p id="message"></p>
			<div id="news" >
				<?php news_sense(); ?>
			</div>
			
          </div>
        </div>
		
	 
	 </div>
      
       
    </div>
    
	
  </body>
</html>
