<?php
require_once "server/function.php";
?>
<!DOCTYPE html>
<html >
  <head>
	<meta http-equiv="Content-Language" content="en, fa">
   
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.css" media="screen">
    <link rel="stylesheet" href="css/bootswatch.min.css">
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="bower_components/html5shiv/dist/html5shiv.js"></script>
      <script src="bower_components/respond/dest/respond.min.js"></script>
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
                <li class="active"><a href="#">All <span class="badge"><?php All($q); ?></span></a></li>
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
			
              <ul class="nav nav-tabs" style="margin-bottom: 15px;">
                <li class="active"><a href="#search" data-toggle="tab">Word </a></li>                
              </ul>
              <div id="myTabContent" class="tab-content">
                <div class="tab-pane fade active in" id="search">
					<form id='teacher'  class='bs-example form-horizontal'  >
						<fieldset>
						<div class='form-group'>
							<label for='Tuser' class='col-lg-2 control-label'>Word: </label>
							<div class='col-lg-2'>
									<input type='text' class='form-control'  id='word'  >
							</div>
							
								
							<button type="button" class="btn btn-primary" onclick="findword();">Find word</button>
							
						</div>
						</fieldset>
					</form>
					<div id="wresults"></div>
                </div>
				
                
              </div>
           	
          </div>
        </div>
		
	 
	 </div>
      
       
    </div>
    
  </body>
</html>