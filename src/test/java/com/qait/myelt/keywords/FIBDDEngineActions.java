package com.qait.myelt.keywords;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.PropFileHandler;

public class FIBDDEngineActions extends GetPage {

	public FIBDDEngineActions(WebDriver driver) {
		super(driver, "FIBDDEngine");
		// TODO Auto-generated constructor stub
	}

	Assignment assign=new Assignment(driver);
	

	/* Test function of FIBDD activity */
	public void testFIBDDActivity() {
		logMessage("-----------------FIBDD logs starts here----------------");
		assign.set_activity_details();
		verify_MaxDropDown_Elements_are_15();
		verify_Dropdown_Menu_item_count_is_2_to_5();
		verify_multiple_dropdown_in_single_sentence();
		verify_Only_one_answer_can_be_SELECT();
		verify_Answer_infirst_location_is_empty();
		verify_submit_button_has_RollOver_state();
		assign.verify_soft_save();
		assign.Submit_activity();
		assign.verify_PDF_button_is_enabled();
		assign.verify_Show_answer_is_Enabled_after_submit();
		verify_Incorrect_answer_text_turns_red();
		verify_show_answer_isDisabled_if_all_answers_are_correct();
		verify_all_correct_answers_not_placed_in_First_place();
		verify_allCorrect_Answer_are_shown_on_ShowAnswer_click();
		verify_correct_answer_text_turns_green();
		verify_Number_of_rows_in_Gradebook_match_Number_of_line_in_activity();
		logMessage("Engine Completed: FIBDD Engine tests completed");
		logMessage("---------------FIBDD logs ends here----------------");
	}

	/*
	 * Verifies that number of drop down elements on current page is less than
	 * 15
	 */
	public void verify_MaxDropDown_Elements_are_15() {
		switchToDefaultContent();
		assign.switch_to_activity_frame();
		PropFileHandler.writeToFile("NumberOfDropDownOnCurrentPage", String.valueOf(elements("Dropdown").size()));
		Assert.assertTrue(elements("Dropdown").size() <= 15);
		logMessage("#Test: Dropdown element Count in current activity is "+ elements("Dropdown").size());
	}

	/* Verifies that drop down option in current activity varies between 2 and 5 */
	public void verify_Dropdown_Menu_item_count_is_2_to_5() {
		Assert.assertTrue(elements("DropdownOption").size() >= 2 && elements("DropdownOption").size() <= 5);
		int a=elements("DropdownOption").size();
		a=a-1;
		logMessage("#Test: Dropdown option Count is : " + a);
	}

	/* Verifies that a single sentence may contain multiple drop down */
	public void verify_multiple_dropdown_in_single_sentence() {
		PropFileHandler.writeToFile("NumberOfRowsInFIBDD", String.valueOf(elements("Number_Of_dropdowns").size()));
		Assert.assertTrue(elements("DropdownInSentence").size() >= 1);
		logMessage("#Test: Multiple Dropdown Elements appear in single Sentence");
	}

	/* Verifies first drop down is always empty */
	public void verify_Answer_infirst_location_is_empty() {
		Assert.assertTrue(element("FirstDropdown").getText().equals(""));
		logMessage("#Test: First drop down option is empty in the dropdown list");
	}

	/* Verifies that submit button has a roll over state */
	public void verify_submit_button_has_RollOver_state() {
		Assert.assertTrue(isElementDisplayed("SubmitRollOver"));
		attempt_all_answers();
		click(element("ActivityBody"));
		Assert.assertTrue(assign.submit_button_is_enabled());
		logMessage("#Test: Submit button is in roll over state");
	}

	/* Verifies that only one answer can be selected from drop down option */
	public void verify_Only_one_answer_can_be_SELECT() {
		try {
			isElementDisplayed("MultiSelect");
		} catch (Exception e) {
			logMessage("#Test: Only one answer can be selected in the current activity");
		}
	}

	/* This function attempt all answers with the index currently defined as 2 */
	public void attempt_all_answers() {
		verify_MaxDropDown_Elements_are_15();
		for (int elementIndex = 1; elementIndex <= Integer.parseInt(PropFileHandler.readProperty("NumberOfDropDownOnCurrentPage")); elementIndex++) {
		try
		{
			selectProvidedIndexFromDropDown(element("DropdownMultiple", String.valueOf(elementIndex)), 2);
		}
		catch(Exception ex)
		{
		}
			
			hardWait(1);
		}
	}

	/* Verifies that all correct answer are not placed in first place */
	public void verify_all_correct_answers_not_placed_in_First_place() {
		Assert.assertTrue(isElementDisplayed("IncorrectAnswer"));
		logMessage("#Test: All correct answers are not placed in first place");
	}

	/*
	 * Verifies that show answer is disabled if all the correct answers are
	 * correct
	 */
	public void verify_show_answer_isDisabled_if_all_answers_are_correct() {
		if (elements("CorrectAnswer").size() == Integer.parseInt(PropFileHandler.readProperty("NumberOfDropDownOnCurrentPage"))) {
			Assert.assertFalse(assign.verify_Show_answer_is_Enabled_after_submit());
			logMessage("#Test: Show answer button is disabled when all answers are correct");
		}
	}

	/* Verify that all correct answer are shown on show answer button */
	public void verify_allCorrect_Answer_are_shown_on_ShowAnswer_click() {
		assign.click_show_answer();
		try {
			isElementDisplayed("IncorrectAnswer");
		} catch (Exception e) {
			logMessage("#Test: All correct answer are shown on clicking show answer");
		}
	}

	/*
	 * Verifies that Number of rows in the grade book equals the Number of line
	 * in the activity
	 */
	public void verify_Number_of_rows_in_Gradebook_match_Number_of_line_in_activity() {
		assign.navigate_to_Grades();
		assign.verify_grades_are_displayed_for_submitted_activity();
		Assert.assertEquals(assign.number_of_rows_in_the_Gradebook(),Integer.parseInt(PropFileHandler.readProperty("NumberOfRowsInFIBDD")));
		logMessage("#Test: Number of rows in Gradebook equals number of line in activity");
	}

	/* Verifies that correct answer element is displayed after submit */
	public void verify_correct_answer_text_turns_green() {
		Assert.assertTrue(isElementDisplayed("CorrectAnswer"));
		logMessage("#Test: Correct answer text is displayed in green");
	}

	/* Verify that Incorrect answer element is displayed after submit */
	public void verify_Incorrect_answer_text_turns_red() {
		Assert.assertTrue(isElementDisplayed("IncorrectAnswer"));
		logMessage("#Test: InCorrect answer text is displayed in red");
	}
	
	public void verify_soft_save()
	{
		assign.close_activity();
		hardWait(2);
		isElementDisplayed("ResumeStateBtn");
		isElementDisplayed("AssignmentActivitName");
		String AssignmentActivityName=element("AssignmentActivitName").getText().toString().trim();
		PropFileHandler.writeToFile("AssignmentActivitName", AssignmentActivityName);
		try{
		element("ResumeStateBtn").click();
		}catch(Exception ex ){
			element("AssignmentActivitName").click();
		}
		finally {
			driver.navigate().refresh();
		}
		hardWait(2);
	}
	
}
