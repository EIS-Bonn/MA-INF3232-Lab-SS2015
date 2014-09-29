<?php
	class CalculateSlidePopularity extends Slide {
		
		public $popularity = array ();
		
		function __construct() {
			// get all slide revision ids and based_on parameter
			$slide_revisions = $this->getAllSlideRevisions();
			// get all slides that in the decks
			$slides_in_decks = $this->getAllSlidesInDecks();
			
			$this->setPopularity($slide_revisions, $slides_in_decks);
		}
		
		function setPopularity($slide_revisions, $slides_in_decks) {
			// populate the popularity array
			foreach($slide_revisions as $slide) {
				$this->popularity[$slide['id']]['popularity'] = 0;
			}
		
			foreach($slides_in_decks as $slide) {
				if(array_key_exists($slide['item_id'], $this->popularity)) {
					$this->popularity[$slide['item_id']]['popularity'] += 1;
					$this->popularity[$slide['item_id']]['deck_revision_id'] = $slide['deck_revision_id'];
				}
			}
			
			foreach($this->popularity as $slide_id => $slide_param) {
				$this->updatePopularityOf($slide_id, $slide_param['popularity']);
			}
		}
		
	}
?>