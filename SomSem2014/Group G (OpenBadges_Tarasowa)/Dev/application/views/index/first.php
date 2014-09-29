<!DOCTYPE html>
<html lang="en">
    <head>
       <meta charset="utf-8">
        <title>SlideWiki Introduction</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css" rel="stylesheet">
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
        <script src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script> 
        <script src="static/js/userControl.js"></script>
        <link rel="stylesheet" href="static/css/first.css">
        
        <script>
            function loginSubmit() {
                var login = document.loginform.login.value;
                var pass = document.loginform.password.value;	
                $.ajax({
                    url : './?url=ajax/login&login='+login+'&pass='+pass,
                    success : function(msg) {            
                            if (msg==-1) {
                                $('#email').addClass('error');
                                $('#password').addClass('error');
                            } else {                    
                                window.location = './';
                            }
                    }
                })
            }
            
            $(document).ready(function() {
                $('#myCarousel').carousel({interval: 2000});
            });
        </script>       
    </head>    
    
    
    
    <body>
        
        <div class="row">
            <div class="white header">
                <div class="span4 image_space"><img src="static/img/slidewiki_logo.png" alt="SlideWiki"/></div>
                
                    <form id="loginform" name = "loginform" action="javascript:loginSubmit()" class="form-inline">
                        <input type="text" name="login" id="email_login" class="input-middle" placeholder="Email">
                        <input type="password" name="password" id="password_login" class="input-middle" placeholder="Password">
                        <label class="checkbox">
                            <input type="checkbox"> <?echo _("Remember me"); ?>
                        </label>
                        <button type="submit" class="btn"> <? echo _("Sign in"); ?></button>
                    </form>
                
                <div style="float:right; margin-top: -30px; margin-right: 20px;"><h3><?php echo _("OpenCourseWare means the content that can be reused..."); ?></h3></div>
            </div>
            <div class="blue">
                <div id="myCarousel" class="carousel slide">
                  <div class="carousel-inner">
                    <div class="item active">
                      <img src="static/img/index/enterprise.jpg" alt="">
                      <div class="container">
                        <div class="carousel-caption">
                          <h2><?php echo _("...by corporation in United States"); ?></h2>                

                        </div>
                      </div>
                    </div>
                    <div class="item">
                      <img src="static/img/index/lecture.JPG" alt="">
                      <div class="container">
                        <div class="carousel-caption">
                          <h2><?php echo _("... by lecturer in France"); ?></h2>

                          
                        </div>
                      </div>
                    </div>
                    <div class="item">
                      <img src="static/img/index/uganda.jpg" alt="">
                      <div class="container">
                        <div class="carousel-caption">
                          <h2><?php echo _("... by students in Uganda"); ?></h2>

                          
                        </div>
                      </div>
                    </div>
                      <div class="item">
                      <img src="static/img/index/teacher.jpg" alt="">
                      <div class="container">
                        <div class="carousel-caption">
                          <h2><?php echo _("...by school teacher in Laos"); ?></h2>

                          
                        </div>
                      </div>
                    </div>
                      <div class="item">
                      <img src="static/img/index/mexica.jpg" alt="">
                      <div class="container">
                        <div class="carousel-caption">
                          <h2><?php echo _("...by enrollees in Mexica"); ?></h2>

                          
                        </div>
                      </div>
                    </div>
                      <div class="item">
                      <img src="static/img/index/iraq.JPG" alt="">
                      <div class="container">
                        <div class="carousel-caption">
                          <h2><?php echo _("...for life-long learning in Iraq"); ?></h2>

                          
                        </div>
                      </div>
                    </div>
                      <div class="item">
                      <img src="static/img/index/children.jpg" alt="">

                    </div>
                  </div>
                  <a class="left carousel-control" href="#myCarousel" data-slide="prev">‹</a>
                  <a class="right carousel-control" href="#myCarousel" data-slide="next">›</a>
                </div><!-- /.carousel -->
               </div>
            </div>
          

    </body>
</html>

