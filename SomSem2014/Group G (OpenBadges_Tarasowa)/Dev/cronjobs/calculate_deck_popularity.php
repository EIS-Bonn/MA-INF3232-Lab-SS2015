<?php
	class CalculateDeckPopularity extends Deck {
	
		public $popularity = array();
		
		function __construct() {
			// get all deck revision ids and based_on parameters
			// based_on will be used to calculate diameter of the based_on graph relantionships
                        
			$deck_revisions = $this->getAllDeckRevisions();
			$deck_usage = $this->getAllDeckUsage();
			$deck_subscriptions = $this->getAllDeckSubscriptions();
			
			$slide_popularity = new CalculateSlidePopularity;
			
			$this->setPopularity($deck_revisions, $deck_usage, $deck_subscriptions, $slide_popularity->popularity);
                        
                        
			
		}
		
		function initConnection() {
                    
		}
		
		function setPopularity($deck_revisions, $deck_usage, $deck_subscriptions, $slide_popularity) {
			
			foreach($deck_revisions as $deck) {
				$this->popularity[$deck['id']]['popularity'] = 0;
			}
			
			foreach($deck_usage as $deck_id) {
				if(array_key_exists($deck_id['item_id'], $this->popularity)) {
					$this->popularity[$deck_id['item_id']]['popularity'] += 1;
				}
			}
			
			foreach($deck_subscriptions as $deck_id) {
				if(array_key_exists($deck_id['item_id'], $this->popularity)) {
					$this->popularity[$deck_id['item_id']]['popularity'] += 1;
				}
			}
			
			foreach($slide_popularity as $slide_id => $slide_param) {
				if(!empty($slide_param['deck_revision_id']) && array_key_exists($slide_param['deck_revision_id'], $this->popularity)) {
					$this->popularity[$slide_param['deck_revision_id']]['popularity'] += $slide_param['popularity'];
				}
			}
			
			foreach($this->popularity as $deck_id => $deck_param) {
				$this->updatePopularityOf($deck_id, $deck_param['popularity']);				
			}
			
		}
	}
?>