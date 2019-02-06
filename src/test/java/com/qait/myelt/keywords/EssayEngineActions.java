package com.qait.myelt.keywords;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.PropFileHandler;
import com.qait.automation.utils.YamlReader;
import com.qait.myelt.keywords.Assignment;

public class EssayEngineActions extends GetPage {

	public EssayEngineActions(WebDriver driver) {
		super(driver, "EssayEngine");
		// TODO Auto-generated constructor stub
	}

	Assignment assign = new Assignment(driver);
	int textAreaCount;
	int rowsInGradebook;

	/*
	 * This function is the base function for Essay class and calls all the
	 * common functions and Essay Functions in itself
	 */
	public void testEssayActivity() {
		assign.set_activity_details();
		logMessage("---------------Essay Logs Starts here-----------------");
		assign.verifyActivityIsSingleScreenActivityOrMultiScreen();
		essay_activity_text_stems_count_is_less_than_3();
		send_text_to_firstTextArea();
		Assert.assertTrue(assign.submit_button_is_enabled());
		logMessage("#Test: Submit button is Enabled on Typing Text in Text Area");
		clear_text_from_Text_Area();
		Assert.assertFalse(assign.submit_button_is_enabled());
		logMessage("#Test: Submit button is disabled on removing Text from Text Area");
		send_text_to_multipleTextArea();
		assign.verify_soft_save();
		assign.Submit_activity();
		Assert.assertTrue(verify_TextArea_is_disable());
		logMessage("#Test: Text Area is disabled after submit");
		Assert.assertFalse(assign.submit_button_is_enabled());
		logMessage("#Test: Submit button is disabled after submiting answer");
		assign.verify_PDF_button_is_enabled();
		assign.navigate_to_Grades();
		assign.verify_grades_are_displayed_for_submitted_activity();
		verify_max_possible_score_equals_number_Of_TextArea();
		verify_number_of_rows_Gradebook_equals_numberOfTextArea();
		verify_user_typed_text_appears_under_StudentAnswer();
		verify_user_score_1_for_each_filled_TypeIn_text();
		logMessage("Engine Completed: Essay Engine tests completed");
		logMessage("------------Essay Logs Ends here-----------------");

	}

	/* This function counts the number of text area in Essay activity */
	public void essay_activity_text_stems_count_is_less_than_3() {
		PropFileHandler.writeToFile("TextAreaCountInEssay", String.valueOf(elements("EssayTextArea").size()));
		Assert.assertTrue(Integer.parseInt(PropFileHandler.readProperty("TextAreaCountInEssay")) <= 3);
		logMessage("#Test: Text Area count for current essay activity is "
				+ PropFileHandler.readProperty("TextAreaCountInEssay") + " which is less than 3");
	}

	/* This function send keys in first text area */
	public void send_text_to_firstTextArea() {
		element("EssayTextAreaMultiple", String.valueOf(1)).click();
		element("EssayTextAreaMultiple", String.valueOf(1)).sendKeys(YamlReader.getData("Text"));
	}

	/* This function send keys to all text area */
	public void send_text_to_multipleTextArea() {
		for (int elementIndex = 1; elementIndex <= Integer
				.parseInt(PropFileHandler.readProperty("TextAreaCountInEssay")); elementIndex++) {
		//	driver.switchTo().frame(driver.findElement(By.id("activity_container")));
			hardWait(5);
			System.out.println(element("TitleText").getText());
			isElementDisplayed("EssayTextArea");
			JavascriptExecutor js=(JavascriptExecutor)driver;
			js.executeScript("document.querySelector('.single-question textarea').click()");
			element("EssayTextArea").sendKeys(YamlReader.getData("Text"));
			hardWait(10);
		}
	}

	/* Clears text from text area */
	public void clear_text_from_Text_Area() {
		element("EssayTextAreaMultiple", String.valueOf(1)).clear();
		element("EssayTextAreaMultiple", String.valueOf(1)).sendKeys(Keys.BACK_SPACE);
	}

	/* Verify textarea is disabled or nor */
	public boolean verify_TextArea_is_disable() {
		return element("EssayTextArea").getAttribute("readonly").equals("true");
	}

	/* This function verify Max possible score equals number of text area */
	public void verify_max_possible_score_equals_number_Of_TextArea() {
		Assert.assertEquals(Integer.parseInt(PropFileHandler.readProperty("TextAreaCountInEssay")),
				(int) assign.max_possible_Score_of_activity());
		logMessage("#Test: Maximum possible score equals number Of TextArea");
	}

	/* This verifies number of rows in gradebook equals number of text area */
	public void verify_number_of_rows_Gradebook_equals_numberOfTextArea() {
		Assert.assertEquals(assign.number_of_rows_in_the_Gradebook(),
				Integer.parseInt(PropFileHandler.readProperty("TextAreaCountInEssay")));
		logMessage("#Test: Number of rows in gradebook is equal to number of type InBox");
	}

	public void verify_soft_save() {
		try {
			assign.close_activity();
			hardWait(3);
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			isElementDisplayed("ResumeStateBtn");
			isElementDisplayed("AssignmentActivitName");
			String AssignmentActivityName = element("AssignmentActivitName").getText().toString().trim();
			PropFileHandler.writeToFile("AssignmentActivitName", AssignmentActivityName);
			element("ResumeStateBtn").click();
			hardWait(3);
		} catch (Exception e) {
			executeJavascript(
					"document.querySelector(\"[class*='html5-activity']\").click()");
			hardWait(3);
		}
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(TestSessionInitiator._getTimeoutsFromConfig()),
				TimeUnit.SECONDS);
	}

	/*
	 * This verify that user typed text is displayed under student answer in the
	 * grade book
	 */
	public void verify_user_typed_text_appears_under_StudentAnswer() {
		Assert.assertEquals(element("StudentAnswer").getText(), "Sample Text");
		logMessage("#Test: User typed text is displayed in Student answer");
	}

	/* Verify correct scoring is done for the answers */
	public void verify_user_score_1_for_each_filled_TypeIn_text() {
		for (int rowCount = 1; rowCount <= assign.number_of_rows_in_the_Gradebook(); rowCount++) {
			if (element("Student_Answer_Review", String.valueOf(rowCount)).getText().length() == 0) {
				Assert.assertEquals(element("score_Review", String.valueOf(rowCount)).getText(), "0.0");
				logMessage("#Test: Student answer is blank. Thus, score is 0");
			} else {
				Assert.assertEquals(element("score_Review", String.valueOf(rowCount)).getText(), "1.0");
				logMessage("#Test: Student answer contains text. Thus score is 1");
			}
		}
	}
}
