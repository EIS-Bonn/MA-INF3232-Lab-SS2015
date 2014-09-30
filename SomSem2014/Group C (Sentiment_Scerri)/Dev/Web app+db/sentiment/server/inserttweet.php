
<!DOCTYPE html>
<html >
  <head>
	<meta http-equiv="Content-Language" content="en, fa">
   
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="../css/bootstrap.css" media="screen">
    <link rel="stylesheet" href="../css/bootswatch.min.css">
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="../bower_components/html5shiv/dist/html5shiv.js"></script>
      <script src="../bower_components/respond/dest/respond.min.js"></script>
    <![endif]-->
    
  </head>
  <body >
    <div class="container">
     
      <!-- Navs
      ================================================== -->

	 <div class="bs-docs-section">
		
		 <div class="row">
          <div class="col-lg-12">
			<p id="message"></p>
			
              <ul class="nav nav-tabs" style="margin-bottom: 15px;">
                <li class="active"><a href="#search" data-toggle="tab">Twitter </a></li>                
              </ul>
              <div id="myTabContent" class="tab-content">
                <div class="tab-pane fade active in" id="search">
					<form id='teacher'  class='bs-example form-horizontal'  >
						<fieldset>
						<div class='form-group'>
							<label for='Tuser' class='col-lg-2 control-label'>Twitter User: </label>
							<div class='col-lg-2'>
									<input type='text' class='form-control'  id='Tuser'  >
							</div>
							
								<label for='Tamount' class='col-lg-2 control-label'>Tweet Amount:</label>
							<div class='col-lg-2'>
									<input type='text' class='form-control'  id='Tamount'  value='500'>
							</div>
							<button type="button" class="btn btn-primary" onclick="findtweets();">Find Tweets</button>
							
						</div>
						</fieldset>
					</form>
					<div id="Tresults"></div>
                </div>
				
                
              </div>
           	
          </div>
        </div>
		
	 
	 </div>
      
       
    </div>
    <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
    <script src="../js/bootswatch.js"></script>
	<script src="../js/ajaxall.js"></script>
  </body>
</html>