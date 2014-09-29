<?php

	if(empty($message)) {
                echo '<div id="success">';
                echo _("The file");
                echo basename( $_FILES['uploaded']['name']);
                echo _("has been uploaded successfully.");
                echo "</div>";
		echo '<div id="proceed"><a href="deck/'. $deck_num .'">';
                echo _('Click link to proceed to the presentation');
                echo '</a></div>';
	}else{
            echo $message;
        }	

?>