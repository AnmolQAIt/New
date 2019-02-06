package com.qait.myelt.keywords;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.PropFileHandler;
import com.qait.automation.utils.YamlReader;

public class FIBMSEngineActions extends GetPage {

	public FIBMSEngineActions(WebDriver driver) {
		super(driver, "FIBMSEngine");
		// TODO Auto-generated constructor stub
	}

	Assignment assign = new Assignment(driver);
	String activityType;
	/*
	 * This is the Base function of the FIBMS engine and includes the common
	 * functions to be tested on TypeIn and Drop down activities.This calls the
	 * test functions of respective activities after executing the common
	 * function
	 */
	public void testFIBMSActivity() {
		
		logMessage("--------------FIBMS logs starts here---------------");
		
		hardWait(1);
    	assign.verify_activity_title_is_displayed_and_match_to_Activity_BCT();
		assign.verifyActivityIsSingleScreenActivityOrMultiScreen();
		try {
			isElementDisplayed("TypeIn");
			activityType="TypeIn";
		} catch (Exception e) {
			activityType="Dropdown";
		}
		if(activityType.equals("TypeIn"))
			testFIBMS_TypeIn();
		else if (activityType.equals("Dropdown"))
			testFIBMS_DragDrop();
		logMessage("Engine Completed: FIBMS Engine tests completed");
		logMessage("-------------FIBMS logs ends here-------------");
	}

	/*
	 * This is the test function for the drag drop activity. It contains all the
	 * unique functions of drag drop activity
	 */
	public void testFIBMS_DragDrop() {
		verify_number_of_distractor_words_is_less_or_equals_4();
		verify_double_click_on_lozenge_return_it_to_word_bank();
		//Assert.assertFalse(assign.submit_button_is_enabled());
		logMessage("#Test: Submit button is disabled after removing Drag and drop element");
		attempt_all_answers_FIBMS_DragDrop();
		Assert.assertTrue(assign.submit_button_is_enabled());
		logMessage("#Test: Submit button is enabled after Drag and drop element");
		assign.verify_soft_save();
		assign.Submit_activity();
		assign.verify_PDF_button_is_enabled();
	//	verify_Incorrect_answer_text_turns_red_in_dragdrop_FIBMS();
		assign.click_show_answer();
	//	verify_correct_answer_text_turns_green_in_dragdrop_FIBMS();
		assign.navigate_to_Grades();
		assign.verify_grades_are_displayed_for_submitted_activity();
	}

	/*
	 * This is the test function for TypeIn activity. This function contains all
	 * the unique functions to TypeIn activity
	 */
	public void testFIBMS_TypeIn() {
		 
		assign.set_activity_details();		
		verify_submit_button_disables_on_deleting_from_TypeIn_activity();
		attempt_all_answers_FIBMS_TypeIn();
		Assert.assertTrue(assign.submit_button_is_enabled());
		assign.verify_soft_save();
		assign.Submit_activity();
		assign.verify_PDF_button_is_enabled();
		verify_Incorrect_answer_text_turns_red_in_typeIn_FIBMS();
		assign.click_show_answer();
		verify_correct_answer_text_turns_green_in_typeIn_FIBMS();
		assign.navigate_to_Grades();
		assign.verify_grades_are_displayed_for_submitted_activity();
	}

	
	/* Verify that submit button disables on deleting text from type in activity */
	public void verify_submit_button_disables_on_deleting_from_TypeIn_activity() {
		
		
		element("TypeIn").sendKeys(YamlReader.getData("Text"));
		element("TypeIn").clear();
		click(element("ContentClass"));
		hardWait(2);
		Assert.assertFalse(assign.submit_button_is_enabled());
		logMessage("#Test: Submit button disables on deleting from the TypeIn-FIBMS activity");
		
		
		
	}

	/*
	 * Verify that correct answer element is displayed after submitting answer
	 * in TypeIn
	 */
	public void verify_correct_answer_text_turns_green_in_typeIn_FIBMS() {
		Assert.assertTrue(isElementDisplayed("CorrectAnswerTypeIn"));
		logMessage("#Test: Correct answer text is displayed in green");
	}

	/*
	 * Verify that Incorrect answer element is displayed after submitting answer
	 * in TypeIn
	 */
	public void verify_Incorrect_answer_text_turns_red_in_typeIn_FIBMS() {
		Assert.assertTrue(isElementDisplayed("IncorrectAnswerTypeIn"));
		logMessage("#Test: InCorrect answer text is displayed in red");
	}

	/*
	 * Verify that correct answer element is displayed after submitting answer
	 * in Drag drop
	 */
	public void verify_correct_answer_text_turns_green_in_dragdrop_FIBMS() {
		try{
			Assert.assertTrue(isElementDisplayed("CorrectAnswerDragDropMultiPage"));
		}catch(Exception e){
			Assert.assertTrue(isElementDisplayed("CorrectAnswerDragDropSinglePage"));
		}
		logMessage("#Test: Correct answer text is displayed in green");
	}

	/*
	 * Verify that Incorrect answer element is displayed after submitting answer
	 * in DragDrop
	 */
	public void verify_Incorrect_answer_text_turns_red_in_dragdrop_FIBMS() {
		try{
			Assert.assertTrue(isElementDisplayed("InCorrectAnswerDragDropMultiPage"));
		}catch(Exception e){
			try{
				Assert.assertTrue(isElementDisplayed("IncorrectAnswerDragDropSinglePage"));
			}catch(Exception e1)
			{
				Assert.assertTrue(isElementDisplayed("CorrectAnswerDragDropMultiPage"));
			}
			
		}
			logMessage("#Test: InCorrect answer text is displayed in red");	
	}

	/*
	 * Verify that number of distractor word 'i.e, Number of drag drop element
	 * remaining after attempting all answer' is less than 4
	 */
	public void verify_number_of_distractor_words_is_less_or_equals_4() {
		try{
		isElementDisplayed("NavigationPanel");
		Assert.assertTrue(elements("DragElementOnCurrentPage").size()- elements("DropAreaOnCurrentPage", String.valueOf(1)).size() <= 4);
		}catch(Exception e){
			Assert.assertTrue(elements("DragElement").size()- elements("DropArea").size() <= 4);
		}
		logMessage("#Test: Number of distractor words is less than or equals 4 in current activity");
	}

	/*
	 * Verify double click on the lozenge 'Drag element' in content area,
	 * returns it to word bank
	 */
	public void verify_double_click_on_lozenge_return_it_to_word_bank() {
		int size ;
		WebElement dragElement,dropArea;
		try{
			isElementDisplayed("NavigationPanel");
			size= elements("DragElementOnCurrentPage").size();
		 dragElement = element("DragElementMultiPage",String.valueOf(1));
		 dropArea = element("DropAreaMultiplePage", String.valueOf(1));
		 hardWait(1);
		 dragandDrop(dragElement, dropArea);
		 hardWait(1);
		 click(element("DragInsideDrop", String.valueOf(1)));
		 hardWait(1);
		 Assert.assertTrue(elements("DragElementOnCurrentPage").size() == size);
		}catch(Exception e){
			size= elements("DragElementOnCurrentPage").size();
			 dragElement = element("DragElementSinglePage",String.valueOf(1));
			 dropArea = element("DropAreaSinglePage", String.valueOf(1));
			 dragandDrop(dragElement, dropArea);
			 click(element("DragInsideDrop", String.valueOf(1)));
			 Assert.assertTrue(elements("DragElement").size() == size);
		}
		assign.refresh_current_activity();
		logMessage("#Test: Drag element returns to word bank on double click lozenge ");
	}
	
	/* Attempts all the answer in FIBMS TypeIn */
	public void attempt_all_answers_FIBMS_TypeIn() {
		try {
			isElementDisplayed("NavigationPanel");
			for (int pageIndex = 1; pageIndex <= Integer.parseInt(element("LastPageOnNavPanel").getText()); pageIndex++) {
				click(element("NextNavButton", String.valueOf(pageIndex)));
				for (int elementIndex = 1; elementIndex <= elements("MultipleTypeInOnCurrentPage").size(); elementIndex++) {
					element("MultipleTypeInMultiPage", String.valueOf(elementIndex)).sendKeys(YamlReader.getData("Text"));
				}
			}
		} catch (Exception e) {
			for (int elementIndex = 1; elementIndex <= elements("TypeIn").size(); elementIndex++) {
				element("MultipleTypeInSinglePage", String.valueOf(elementIndex)).sendKeys(YamlReader.getData("Text"));
			}
		}
	}

	/* Attempts all drag drop answers */
	public void attempt_all_answers_FIBMS_DragDrop() {
		WebElement dragElement, dropArea;
		try {
			isElementDisplayed("NavigationPanel");
			for (int pageIndex = 1; pageIndex <= Integer.parseInt(element("LastPageOnNavPanel").getText()); pageIndex++) {
				click(element("NextNavButton", String.valueOf(pageIndex)));
				hardWait(1);
				for (int elementIndex = 1; elementIndex <= elements("DropAreaOnCurrentPage").size(); elementIndex++) {
					dragElement = element("DragElementMultiPage",String.valueOf(1));
					dropArea = element("DropAreaMultiplePage", String.valueOf(elementIndex));
						dragandDrop(dragElement, dropArea);
				}
				
			}
		} catch (Exception e) {
			for (int elementIndex = 1; elementIndex <= elements("DropArea").size(); elementIndex++) {
				dragElement = element("DragElementSinglePage", String.valueOf(1));
				dropArea = element("DropAreaSinglePage", String.valueOf(elementIndex));
				dragandDrop(dragElement, dropArea);
				
			}
		}

	}

}
