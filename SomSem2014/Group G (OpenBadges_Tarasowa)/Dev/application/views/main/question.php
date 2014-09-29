<div class="close" onclick="closeQuestionOverlay('question_main_overlay')"></div>
<div class="form-stacked" id="questions-new-question">
	<div class="row">
		<div class="span6">
			<div class="clearfix">
				<label for="quest_text"><? echo _("Question"); ?></label>
				<div class="input"><textarea rows="1" style="min-height:17px;" type="text" name="quest_text" id="quest_text"/></div>
			</div>
		</div>
		<div class="span4">
			<div class="clearfix">
				<label for="difficulty"><? echo _("Difficulty"); ?></label>
				<div class="input">
					<select class="span4" name="difficulty" id="difficulty">
						<option value="0,2"><? echo _("Very easy"); ?></option>
						<option value="0,4"><? echo _("Easy"); ?></option>
						<option value="0,6"><? echo _("Middle"); ?></option>
						<option value="0,8"><? echo _("Difficult"); ?></option>
						<option value="1"><? echo _("Very difficult"); ?></option>
					</select>
				</div>
			</div>
		</div>
	</div>
	<div class="actions" id="save_button">
		<button class="btn primary" onclick="saveQuestion('<?php echo $id;?>')">Save</button>
	</div>
	<div id="answer_row" style="visibility:hidden">
	<div class="row">
		<div class="span10">
			<div class="clearfix">
				<label for="answers"><? echo _("Answers"); ?>
					<button class="btn small answerRow" quest_id="<?php echo $quest_id;?>" id="addAnswer" onclick="addMoreAnswer()">+</button>
				</label>
				<table id="answers" class="condensed-table">
				<thead class="blue">
                                    <tr>
                                        <th><? echo _("Answer"); ?></th>
                                        <th><? echo _("Correct?"); ?></th>
                                        <th><? echo _("Explanation"); ?></th>
                                        <th><? echo _("Delete"); ?></th>
                                    </tr>
                                </thead>
                                <tbody></tbody>
				</table>				
			</div>
		</div>		
	</div>
	<div class="actions" id="save_all_answers_button">
		<button class="btn primary" id="save_all_answers" name="save_all_answers" 
                        quest_id="<?php echo $quest_id;?>" onclick="saveAllAnswers('<?php echo $quest_id;?>')"><? echo _("Save answers"); ?></button>
	</div>	
	</div>
</div>
	
