<?php
class AdminController extends Controller {
    function index(){
    	if($this->_user ['id']==2 || $this->_user ['id']==1 || $this->_user ['id']==3){
			$this->set('alert', '1');
		}else{
			$this->set('alert', '0');
		}      
    }
}
