<?php
error_reporting(E_ALL);
require_once (ROOT . DS . 'application' . DS . 'library' . DS . 'CacheAPC.php');
//testtest
class TestController extends Controller {
	function start() {
		$deck_id=41;
		$deck=new Deck();
		$deck->createFromID($deck_id);
		$tree=$deck->getTree();
		$rev=new Revision($tree);
		$changeset=array('user_id'=>'5','items'=>array(array('item_id'=>'tree-2-slide-2-1'),array('item_id'=>'tree-41-slide-1-1')));
		$result=$rev->handleChange($changeset);
		$root_change=$result['root_changed'];
		$this->set ( 'root_change', $root_change );
		$this->set ( 'rev', $rev );
	}

	function test() {
		$deck_id=69;
		$user_id=2;
		$deck = new Deck ();
		$deck->createFromID ( $deck_id );
		if(!$user_id)
			$out= -1;// user is not logged in
		if($user_id!=$deck->owner->id){
			//user is not the owner of the deck
			$out=  1;
		}else{
			//user is the owner of the deck
			if (count ( $deck->getUsageExceptUser($user_id))) {
				//deck is used somewhere else
				$out=  1;
			}
			else{
				//deck is not used somewhere else
				$out=  0;
			}
		}	
		$this->set ( 'usage', $out );
	}
	function alignPositions(){
		$this->_template->disableHeader();
		$this->_template->disableFooter();
		$this->_noRender = true;
		$deckList=new DeckList();
		$deckList->alignAllWrongPositions();
	}
	function getUsageCounts(){
		$this->_template->disableHeader();
		$this->_template->disableFooter();
		$this->_noRender = true;
		$dl=new DeckList();
		$list=$dl->getAllDecks(100000);
		foreach ($list as $dc){
			$usage=$dc->getUsage();
			echo $dc->title.'(id='.$dc->id.') -> '.count($usage).'<br/>';
		}
	}	
	function createUsagePath(){
		$this->_template->disableHeader();
		$this->_template->disableFooter();
		$this->_noRender = true;
		$deck_id=$_GET['deck'];
		$deck=new Deck();
		$deck->createFromID($deck_id);	
		$this->prepareUsagePath($deck,'');
	}	
	private function prepareUsagePath($deck,$prev){
		$usage=$deck->getUsage();
		//echo count($usage).'<br>';
		if (count($usage)){
			foreach ($usage as $dc){
				$this->prepareUsagePath($dc,'<a href="./?url=main/deck&deck='.$deck->id.'">'.$deck->title.'</a><b> > </b>'.$prev);
			}				
		}else{
			echo '<a href="./?url=main/deck&deck='.$deck->id.'">'.$deck->title.'</a> <b> > </b>'.$prev.'<br>';
		}
	}
	function importOldSlideWiki(){
		$link = mysql_connect(DB_HOST, DB_USER, DB_PASSWORD);
		$db_selected = mysql_select_db('slidewiki_old', $link);
		$result = mysql_query("SELECT * FROM deck");
		while ($row = mysql_fetch_assoc($result)) {
		    echo 'timestamp: '.$row['timestamp'].'<br>';
		    echo 'user id: '.$row['user_id'].'<br>';
		    echo '<hr>';
		    $deck=new Deck();
			$user = new User ();
			$user->createFromID ( $row ['user_id'] );
			$deck->user=$user;
			//$deck->create();
			$result2 = mysql_query("SELECT * FROM deck_revision WHERE deck_id=".$row['id']);
			while ($row2 = mysql_fetch_assoc($result2)) {
				echo $row2['title'].'<br>';
				echo 'deck id: '.$row2['id'].'<br>';
				$deck->title=$row2['title'];
				$user2 = new User ();
				$user2->createFromID ( $row2 ['user_id'] );
				$deck->user=$user2;	
				//$deck->commit();
				$result3 = mysql_query("SELECT * FROM deck_content WHERE deck_revision_id=".$row2['id']);
				while ($row3 = mysql_fetch_assoc($result3)) {
					
				}
			}

		}
	}
	function sendMsg(){
		$m=new Msg();
		$m->sender_id=1;
		$m->receiver_id=2;
		$m->title="a test msg";
		$m->content="dsad sa gdsg dsfg dsg sd gdsfds gdsg sdg sdg.d sgsdgdsg<b>asad</b>ad sagf sagasg";
		$m->save();
		$m->send();

	}
	function checkAPC(){
	    $oCache = new CacheAPC();
		if ($oCache->bEnabled) { // if APC enabled
		    echo 'Now we saved all in memory, click <a href="ndex2.php">here</a> to check what we have in memory';
		} else {
		    echo 'Seems APC not installed, please install it to perform tests';
		}
	}
	function checkStats(){
		echo "stats:";
		$stat=new Statistics();
		$stat->calculateAll();
		var_dump($stat);
	}
	function checkShortUrl(){
			$a=@$_GET['a'];
			$u=new Url();
			$short=$u->addget_short_url($a);
			echo "Short URL: http://slidewiki.org/sp/".$short;
			echo "<br/>Long URL: ".$u->get_short_url($short);
	}	
	function clearAPCache(){            
		$type=@$_GET['type'];
		$id=@$_GET['id'];
		if(!isset($type)){
			echo "please specify a type: all, home, etc.";
		}elseif($type=="all"){
			apc_clear_cache();	
		}elseif($type=="home"){
				apc_delete('statistics');
				apc_delete('new_decks');
				apc_delete('featured_decks');
				apc_delete('home_activities');
		}
		elseif($type=="deck"){
				apc_delete('deck_tree_'.$id);
				apc_delete('deck_question_'.$id);
				apc_delete('deck_short_activities_'.$id);
				apc_delete('home_activities');
		}	
		elseif($type=="avatar"){
				apc_delete('user_avatar_'.$id);
		}			
	}
	function testCarousel(){
		
	}
}
