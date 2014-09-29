</section> <!-- /.main view -->
<footer> <!-- page footer -->
        <div>
	<?php if(isset($page_additional_footer)) echo $page_additional_footer; ?>
        </div>
        <p><!-- Open Knowledge Link -->
            <a href="http://opendefinition.org/">
            <img style="vertical-align: middle; margin-top:-2px; margin-right:2px; margin-left:2px;" alt='<? echo _("This material is Open Knowledge"); ?>' border="0"
            src="http://assets.okfn.org/images/ok_buttons/ok_80x15_blue.png" /></a> 
            <!-- /Open Knowledge Link -->
        <? echo _('SlideWiki is developed by <a href="http://aksw.org"> AKSW research group/Uni Leipzig</a>, <a href="http://eis.iai.uni-bonn.de/">EIS/Uni Bonn</a> and <a href="http://www.iais.fraunhofer.de/">Fraunhofer IAIS</a>'); ?> | 
        <a href="imprint" target="_blank"><? echo _("Imprint"); ?></a> | 
        <a href="termsOfUse" target="_blank"><? echo _("Terms of Use"); ?></a> | 
        <a href="https://groups.google.com/d/forum/slidewiki"><? echo _("Mailing list"); ?></a> |
        <a href="documentation/"><? echo _("Documentation"); ?></a></p>
        
</footer>

<!-- registration and login forms -->

<?php if (!$user['is_authorized']): ?>
<section id="login-register-modal" class="modal hide fade">
    <header class="modal-header">
        <a href="#" title="close window" class="close">×</a>
        <h3><? echo _("Login or Register"); ?> <a onclick="facebook_login()" style="cursor:pointer"><img src="static/img/facebook.jpg" width="20" alt="<? echo _("Login via Facebook"); ?>"></a></h3>
    </header>
	
    <section class="modal-body">
		<header>
			<nav>
				<ul class="tabs" data-tabs="tabs">
					<li class="active"><a href="#loginform"><? echo _("Login"); ?></a>
                                            
                                            
					<li><a href="#registerform"><? echo _("Register"); ?></a>
				</ul>
			</nav>
		</header>
		
		<section class="tab-content">
			<form id="loginform" name = "loginform" action="javascript:loginSubmit()" class="tab-pane active">
				<fieldset>
					<legend><? echo _("Login"); ?></legend>
					<div id = "email" class="clearfix">
						<label for="email_login"><? echo _("Email"); ?></label>
						<div class="input">
							<input type="text" name="login" id="email_login" class="xlarge" />
						</div>
					</div>
					<div id="password" class="clearfix">
						<label for="password_login"><? echo _("Password"); ?></label>
						<div class="input">
							<input type="password" name="password" id="password_login" class="xlarge" />
                                                        <span class="help-block"><a style="cursor:pointer" onclick="pass_recovery_email()"><? echo _("Can't access my account"); ?></a></span>
						</div>
                                                
					</div>
					<div class="actions">
						<button class="btn primary" type="submit"><? echo _("Login"); ?></button>
					</div>
				</fieldset>
			</form>
			
			<form id="registerform" name="registerform" class="tab-pane"  action="javascript:registerSubmit()">
				<fieldset>
					<legend><? echo _("Register"); ?></legend>
					<div id="username" class="clearfix">
						<label for="username_register"><? echo _("Choose Username"); ?></label>
						<div class="input" id="reg_username">
							<input type="text" name="username" id="username_register" class="xlarge" />
                                                        <span class="help-block" id="reg_username_span"></span>
                                                </div>
					</div>
					<div id="reg_email" class="clearfix">
						<label for="login_register"><? echo _("Your Email"); ?></label>
						<div class="input" id="reg_addresse">
							<input type="text" name="login" id="login_register" class="xlarge" />
                                                        <span class="help-block" id="reg_addresse_span"></span>
                                                </div>
					</div>
					<div id="confirm_reg_email" class="clearfix">
						<label for="confirm_login_register"><? echo _("Confirm Your Email"); ?></label>
						<div class="input" id="reg_confirm">
							<input type="text" name="confirm_login" id="confirm_login_register" class="xlarge" />
                                                        <span class="help-block" id="reg_confirm_span"></span>
						</div>
					</div>
					<div id="reg_password_div" class="clearfix">
						<label for="password_register"><? echo _("Choose Password"); ?></label>
						<div class="input">
							<input type="password" name="password" id="password_register" class="xlarge" />
                                                        <span class="help-block" id="reg_password_span"></span>
						</div>
					</div>
					<div id="confirm_password_div" class="clearfix">
						<label for="verifypassword_register"><? echo _("Verify Password"); ?></label>
						<div class="input">
							<input type="password" name="verifypassword" id="verifypassword_register" class="xlarge" />
                                                        <span class="help-block" id="reg_verifypassword_span"></span>
						</div>
					</div>
					<div id="captcha" class="clearfix">
						<label for="captcha_register"><? echo _("Insert Captcha from picture below"); ?><br/></label>
						<div class="input">
							<input type="text" name="captcha" id="captcha_register" class="xlarge" /><div id="captcha_img"><img id="captchaImage" src="./?url=ajax/captcha" alt=""/></div>
                                                        <span class="help-block" id="reg_captcha_span"></span>
                                                </div>
					</div>
					<div class="clearfix">
						<label for="license_notification"></label>
						<div class="input">
							<input type="checkbox" checked disabled="disabled" id="license_notification" /><? echo _('I accept that all my contributions will be under <a href="http://creativecommons.org/licenses/by-sa/2.0/" target="_blank">CC BY SA</a> license.'); ?>
						</div>
					</div>					           
					<div class="actions">
						<button class="btn primary" type="submit"><? echo _("Register now!"); ?></button>
					</div>
				</fieldset>
			</form>
		</section>
	</section>
</section>
<?php endif; ?>



<div id="pass_recovery_email" class="modal fade">
    <header class="modal-header">
        <a href="#" title="close window" class="close">×</a>
        <h3><? echo _("Password recovery"); ?></h3>
    </header>
    <div class="modal-body">
        <fieldset>
                <div id="recovery_form" class="clearfix">
                    <label for="email_recovery"><? echo _("E-mail"); ?></label>
                    <div class="input">
                        <input type="text" name="email_recovery" id="email_recovery" class="xlarge" />
                        <span class="help-block"><? echo _("Enter the registration e-mail"); ?></span>
                    </div>
                </div>
                <div class="actions">
                    <button class="btn primary" onclick="send_password('email')"><? echo _("Send"); ?></button>
                    <button class="btn" onclick="pass_recovery_username()"><? echo _("Recover by username"); ?></button>
                </div>
       </fieldset>            
    </div>
</div>
<div id="choose_user" class="modal fade">
    <header class="modal-header">
        <a href="#" title="close window" class="close">×</a>
        <h3><? echo _("Choose a SlideWiki user:"); ?></h3>
    </header>
    <div class="modal-body" id="user_list" style="text-align: center;">
       
    </div>
</div>
<script id="choose_user_script" type="text/x-jquery-tmpl">
    <button class="btn btn-info" onclick="authNoPassword(${fb_id},${id})">
    ${username} 
    </button><br>
</script>
<div id="pass_recovery_username" class="modal fade">
    <header class="modal-header">
        <a href="#" title="close window" class="close">×</a>
        <h3><? echo _("Password recovery"); ?></h3>
    </header>
    <div class="modal-body">
        <fieldset>
            <div id="recovery_form_username" class="clearfix">
                <label for="username_recovery"><? echo _("Username"); ?></label>
                <div class="input">
                    <input type="text" name="username_recovery" id="username_recovery" class="xlarge" />
                    <span class="help-block"><? echo _("Enter the username"); ?></span>
                </div>
            </div>
            <div class="actions">
                <button class="btn primary" id="submit" onclick="send_password('username')"><? echo _("Send"); ?></button>                   
            </div>
        </fieldset>
     </div>
</div>


<!-- image manager -->
<a id="img_manager_dummy" style="display:none" href="#"></a>
<script src="static/js/google_analytics.js"></script>
</body>
</html>
