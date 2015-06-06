
<!DOCTYPE html
PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	
    <title>Display feeds</title>
	<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
	<script src="js/ajaxall.js"></script>
    <script type="text/javascript">    
		setInterval(function() {
					  politics();
					}, 350000); 
		setInterval(function() {
					  sports();
					}, 400000); 
		setInterval(function() {
					  social();
					}, 500000); 
		setInterval(function() {
					  economic();
					}, 1000000); 
	</script>
  <body >

  <div  id='wresults'>
	
    </div>
	<hr>
<div  id='wresults1'>
  
    </div>   
	<hr>
<div  id='wresults2'>
  
    </div>
<hr>
<div  id='wresults3'>
  
    </div>   	
	
  </body>
</html>