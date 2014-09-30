<?php
require_once "server/function.php";
$q=$_GET['p'];
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
  <body >
    <div class="container">
     
      <!-- Navs
      ================================================== -->
      <div class="bs-docs-section">
        <div class="row">
         
          <div class="col-lg-12">
            <h2>Sentiment</h2>
            <div class="bs-component">
              <ul class="nav nav-pills">
                <li class="active"><a href="#">All <span class="badge"><?php //All($q); ?></span></a></li>
                <li><a href="#">Positive <span class="badge"><?php positive($q); ?></span></a></li>
                <li><a href="#">Neutral <span class="badge"><?php neutral($q); ?></span></a></li>
				<li><a href="#">Negative <span class="badge"><?php negative($q); ?></span></a></li>
				 <li><a href="#">Votes <span class="badge"><?php allvote($q); ?></span></a></li> 
              </ul>
            </div>
          </div>
        </div>
      </div>

	 <div class="bs-docs-section">
		 <div class="row">
          <div class="col-lg-12">
			<p id="message"></p>
			
			<?php if ($q=='adj') 
					{
						echo "<h2 align='right'>صفتها</h2>
								<h3 align='right'> 
آنالیز حسی متن یکی از موضوعات جدید تحقیقاتی در
دنیای فناوری اطلاعات می باشد. ما برای ساخت آنالیز حسی فارسی در یک
پروژه دانشگاهی نیاز به جمع آوری یک سری داده اولیه در مورد حس افراد
برای  صفات مختلف در زبان فارسی را داریم. پیشاپیش از اینکه ما را در
انجام این پروژه همراهی می کنید سپاسگذاریم.



شما می توانید حس خود را در مورد هر صفت با انتخاب گزینه های مثبت منفی
خنثی تعیین نمایید و هر زمان که از ادامه کار منصرف شدید صفحه موجود را
ترک نمایید. نظرات شما به صورت لحظه ای ثبت می گردد.

برخی از صفات موجود در این لیست از واژگان روزمره بسیار فاصله دارد پس در
صورت اینکه صفتی برای شما نامعلوم یا ناآشنا می باشد آن را بدون پاسخ رها
کنید 
یا از این 
<a href='http://fa.wiktionary.org/w/index.php?title=%D8%B1%D8%AF%D9%87:%D8%B5%D9%81%D8%AA%E2%80%8C%D9%87%D8%A7%DB%8C_%D9%81%D8%A7%D8%B1%D8%B3%DB%8C'>آدرس</a> 
معنای آن را دریابید.

سپاس

</h3>
";
						adjectives();	
					}else if ($q=='sent'){
							echo "<h2 align='right'>جمله ها</h2>";
							sentences();
						}else {
								echo "<h2 align='right'>Adjectives</h2>";
								op_adj();
							}
							?>
			
          </div>
        </div>
		
	 
	 </div>
      
       
    </div>
    
	
  </body>
</html>
