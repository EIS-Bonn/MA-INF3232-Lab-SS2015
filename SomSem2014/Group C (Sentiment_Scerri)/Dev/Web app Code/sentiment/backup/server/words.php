<?php 
require_once 'DBconnect.php';
?>
<!DOCTYPE html>
<html >
  <head>
	<meta http-equiv="Content-Language" content="en, fa">
   
    <title>لغات  برای ارزیابی</title>
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
                <li class="active"><a href="#search" data-toggle="tab">Words </a></li>                
              </ul>
              <div id="myTabContent" class="tab-content">
                <div class="tab-pane fade active in" id="search">
					<div id="Tresults">
						<table class="table table-striped table-hover ">
							<thead>
							  <tr>
								<th>word </th>
								<th>neutral </th>
								<th>negative </th>
								<th>positive </th>
								<th>total</th>
								<th>delete</th>
							  </tr>
							</thead>
						<?php Allword(); ?></div>
						</table>
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
<?php
function Allword()
{
	$link=dbconnect();
    $query="SELECT * FROM words order by total DESC  ";
	$result=mysql_query($query);
	while ($row = mysql_fetch_assoc($result)) 
	{
		echo "<tr>
				<td>".$row['word']."</td>
				<td>".$row['neutral']."</td>
				<td>".$row['negative']."</td>
				<td>".$row['positive']."</td>
				<td>".$row['total']."</td>
				<td><button type='button' class='btn btn-danger btn-sm' onclick='word_delete(".$row['ID'].");'>Delete ".$row['ID']."</button> </td>
			  </tr>";
	}
}
?>