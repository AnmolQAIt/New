package com.qait.myelt.keywords;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.SwitchToParentFrame;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.PropFileHandler;
import com.qait.automation.utils.YamlReader;

public class Assignment extends GetPage {

	/* Constructor of the current class */
	public Assignment(WebDriver driver) {
		super(driver, "Assignment");
		this.driver = driver;
	}

	EssayEngineActions EssayEngine;
	FIBMSEngineActions FIBMSEngine;
	FIBDDEngineActions FIBDDEngine;
	List<String> list = new ArrayList<String>();

	/* This function launches the course written in the DataFile.properties" */
	public void launchCourse(String crs_name) {
		try {
			click(element("Course", crs_name));
			logMessage("#Test: Course: " + "" + crs_name + " is Launch Successfully");
		} catch (Exception ex) {

		}
	}

	/* This function launch the first Activity in the specified course */
	public void launchActivity() {
		try {
			isElementDisplayed("FirstUnitTitle");
			click(element("FirstUnitTitle"));
		} catch (Exception e) {
			changeTheURL(getCurrentURL() + "/section/0");
			hardWait(1);
			try {
				click(element("FirstUnitTitle"));
			} catch (Exception ex) {
				click(element("FirstUnitTitle2"));
			}

		}
		hardWait(2);
		element("FirstActivityParentTitle").click();
		hardWait(2);
		try {
			click(element("FirstActivityChildTitle"));
			logMessage("#Test: First Activity of the course is launched successfully");
		} catch (Exception ex) {
			element("FirstActivityChildTitle2").click();
			logMessage("#Test: First Activity of the course is launched successfully");
		}
	}

	/*
	 * This function detects the Engine on the current page and return the value
	 * of current Engine type
	 */
	public String detectEngine() {
		String engineType = "";
		String engineDescription;
		switchToDefaultContent();
		hardWait(2);
		switch_to_activity_frame();
		try {
			isElementDisplayed("EngineBody");
		} catch (Exception e) {
			click_StartAgain();
		}
		engineDescription = element("EngineDescription").getAttribute("class");
		if (engineDescription.contains("essay"))
			engineType = "essay";
		else if (engineDescription.contains("fibdd"))
			engineType = "fibdd";
		else if (engineDescription.contains("sort"))
			engineType = "sort";
		else if (engineDescription.contains("snsc"))
			engineType = "snsc";
		else if (engineDescription.contains("mcss"))
			engineType = "mcss";
		else if (engineDescription.contains("seq"))
			engineType = "seq";
		else if (engineDescription.contains("pfrd"))
			engineType = "pfrd";
		else if (engineDescription.contains("mcim"))
			engineType = "mcim";
		else if (engineDescription.contains("mcms"))
			engineType = "mcms";
		else if (engineDescription.contains("hglt"))
			engineType = "hglt";
		else if (engineDescription.contains("fibms")) {
			try {
				isElementDisplayed("TypeIn");
				engineType = "fibms TypeIn";
			} catch (org.openqa.selenium.NoSuchElementException e) {
				engineType = "fibms Dragdrop";
			}
		} else if (engineDescription.contains("spk"))
			engineType = "spk";
		else if (engineDescription.contains("diag"))
			engineType = "diag";
		else if (engineDescription.contains("fibau"))
			engineType = "fibau";
		else if (engineDescription.contains("quiz"))
			engineType = "quiz";
		else if (engineDescription.contains("stimul"))
			engineType = "stimul";
		else if (engineDescription.contains("hangman"))
			engineType = "hangman";
		else if (engineDescription.contains("srils"))
			engineType = "srils";
		else {
			navigate_to_Next_Activity();
			detectEngine();
		}
		return engineType;
	}

	/*
	 * This function Get the detected Engine name and call the detected engine
	 * Keyword class
	 */
	public void testActivity() {
		String engineName;
		try {
			while (element("NextActivity").isEnabled()) {
				if (list.size() == 4) {
					break;
				}
				engineName = detectEngine();
				if (list.contains(engineName)) {
					navigate_to_Next_Activity();
					logMessage(engineName + " is already Tested ");
				}

				else if (engineName.equals("fibms Dragdrop")) {
					navigate_to_Next_Activity();
					logMessage(engineName + " is already Tested ");
				}

				else {
					switch (engineName) {

					case "essay":
						logMessage("#Test: Current engine to be tested is Essay type");
						EssayEngine = new EssayEngineActions(driver);
						EssayEngine.testEssayActivity();
						list.add("essay");
						navigate_back_to_Activity_from_Grades();
						break;

					case "fibdd":
						logMessage("#Test: Current engine to be tested is FIBDD");
						FIBDDEngine = new FIBDDEngineActions(driver);
						FIBDDEngine.testFIBDDActivity();
						list.add("fibdd");
						navigate_back_to_Activity_from_Grades();
						break;

					case "fibms TypeIn":
						logMessage("#Test: Current engine to be tested is FIBMS TypeIn");
						FIBMSEngine = new FIBMSEngineActions(driver);
						FIBMSEngine.testFIBMSActivity();
						list.add("fibms TypeIn");
						navigate_back_to_Activity_from_Grades();
						break;

					case "fibms Dragdrop":
						logMessage("#Test: Current engine to be tested is FIBMS Dragdrop");
						FIBMSEngine = new FIBMSEngineActions(driver);
						FIBMSEngine.testFIBMSActivity();
						list.add("fibms Dragdrop");
						navigate_back_to_Activity_from_Grades();
						break;

					default:
						logMessage("#Test: Current Engine is " + engineName + ". It is not implemented");
					}
				}
				navigate_to_Next_Activity();
			}
		} catch (StaleElementReferenceException e) {
			testActivity();
		}

	}

	/* ------------Common functions for the Engines------------------ */

	/*
	 * This function writes the current activity title and unit title in the
	 * property file
	 */
	public void set_activity_details() {
		String[] temp;

		try {
			if (element("CurrentUnitTitle").getText().trim().contains("/")) {
				temp = element("CurrentUnitTitle").getText().trim().split("/");
				PropFileHandler.writeToFile("CurrentUnitTitleText", temp[0].substring(0, temp[0].length() - 1));
				PropFileHandler.writeToFile("CurrentActivityParentTitleText", temp[1].substring(1));
				PropFileHandler.writeToFile("CurrentActivityChildTitleText",
						element("CurrentActivityTitle").getText().trim());
			} else if (element("CurrentUnitTitle").getText().trim().contains(":")) {
				PropFileHandler.writeToFile("CurrentUnitTitleText",
						element("CurrentUnitTitle").getText().trim().replace(":", ""));
				temp = element("CurrentActivityTitle").getText().trim().split(":");
				PropFileHandler.writeToFile("CurrentActivityParentTitleText", temp[0]);

				try {
					PropFileHandler.writeToFile("CurrentActivityChildTitleText", temp[1].substring(1));
				} catch (ArrayIndexOutOfBoundsException ex) {
					PropFileHandler.writeToFile("CurrentActivityChildTitleText", "");
				}
			}
			try {
				logMessage("Current Activity Title is: " + PropFileHandler.readProperty("CurrentUnitTitleText") + " "
						+ PropFileHandler.readProperty("CurrentActivityParentTitleText") + " "
						+ PropFileHandler.readProperty("CurrentActivityChildTitleText"));

			} catch (ArrayIndexOutOfBoundsException ex) {

			}
		} catch (Exception ex1) {

		}
	}

	/*
	 * This function get the activity title and navigate to TOC and match the
	 * activity title on TOC then it navigate back to activity page
	 */
	public void verify_activity_title_is_displayed_and_match_to_Activity_BCT() {
		Assert.assertTrue(isElementDisplayed("CurrentUnitTitle"));
		Assert.assertTrue(isElementDisplayed("CurrentActivityTitle"));
		String temp, preTemp;
		temp = element("ChildTitleText").getText().toString().trim();
		preTemp = temp.replace(":", "");
		PropFileHandler.writeToFile("CurrentActivityChildTitleText", preTemp);
		close_activity();
		switchToDefaultContent();
		driver.navigate().refresh();
		// changes
		Assert.assertTrue(PropFileHandler.readProperty("CurrentUnitTitleText")
				.contains(element("ActivityBCT_Unit").getText().trim()));
		hardWait(1);

		// Assert.assertTrue(PropFileHandler.readProperty("CurrentActivityParentTitleText").contains(element("ActivityBCT_Activity").getText().trim()));
		// Assert.assertTrue((element("ActivityBCT_Activity").getText().trim()).contains((PropFileHandler.readProperty("CurrentActivityParentTitleText"))));

		navigate_back_to_activity_from_TOC();
		switchToDefaultContent();
		switch_to_activity_frame();

		try {
			click_StartAgain();
		} catch (Exception e) {
		}
		logMessage("#Test: Activity title is displayed and match to the Activity BCT");
		hardWait(1);
	}

	/* This function is for navigating back from TOC to current activity */
	public void navigate_back_to_activity_from_TOC() {
		try {
			try {
				if (elements("CurrentActivityLink", PropFileHandler.readProperty("CurrentActivityChildTitleText"))
						.size() == 1) {

					click(element("CurrentActivityLink",
							PropFileHandler.readProperty("CurrentActivityChildTitleText")));
				} else {
					click(element("CurrentActivityLink", PropFileHandler.readProperty("CurrentActivityParentTitleText")
							+ ": " + PropFileHandler.readProperty("CurrentActivityChildTitleText")));
				}
			} catch (Exception e) {
				try {

					if (elements("CurrentActivityLink",
							PropFileHandler.readProperty("CurrentActivityChildTitleText").toLowerCase()).size() == 1) {
						click(element("CurrentActivityLink",
								PropFileHandler.readProperty("CurrentActivityChildTitleText").toLowerCase()));
					} else {
						click(element("CurrentActivityLink",
								PropFileHandler.readProperty("CurrentActivityParentTitleText") + ": "
										+ PropFileHandler.readProperty("CurrentActivityChildTitleText").toLowerCase()));
					}
				} catch (Exception e1) {

					// Newly added in the code
					// changes
					for (WebElement element : elements("ActivityLinksCountOnTOC")) {
						String temp1 = element.getText();
						if (PropFileHandler.readProperty("CurrentActivityChildTitleText").contains(temp1))
							element.click();
					}
				}

				click(element("CurrentActivityLink", PropFileHandler.readProperty("CurrentActivityParentTitleText")
						+ ": "
						+ Character.toString(PropFileHandler.readProperty("CurrentActivityChildTitleText").charAt(0))
								.toUpperCase()
						+ PropFileHandler.readProperty("CurrentActivityChildTitleText").substring(1)));
			}

		} catch (NoSuchElementException e1) {

			// Newly added in the code

			for (WebElement element : elements("ActivityLinksCountOnTOC")) {
				String temp2 = element.getText().trim();
				if (PropFileHandler.readProperty("CurrentActivityChildTitleText").contains(temp2))
					element.click();
			}

			for (WebElement element : elements("ActivityLinksCountOnTOC")) {
				// changes
				String temp, preTemp, completeActivityTitle;
				completeActivityTitle = PropFileHandler.readProperty("CurrentActivityParentTitleText") + ": "
						+ PropFileHandler.readProperty("CurrentActivityChildTitleText");
				temp = element.getText().trim();
				preTemp = temp.replace(":", "");
				try {
					if (preTemp.equals(PropFileHandler.readProperty("CurrentActivityChildTitleText"))
							|| preTemp.equals(completeActivityTitle)) {
						PropFileHandler.writeToFile("CurrentActivityChildTitleText", temp);
						element.click();
					}

					else if (element("IncompleteActivity").isDisplayed()) {
						element("IncompleteActivity").click();
					}
				} catch (Exception ex) {
					try {
						element("IncompleteActivity2").click();
					} catch (Exception exp) {
						element("CompletedActivity").click();
					}
				}

			}

		}
	}

	/*
	 * This function verify the Incomplete icon in the TOC for the Current
	 * activity
	 */

	public void verify_currect_activity_is_Incomplete() {
		try {
			try {
				if (elements("CurrentActivityLink", PropFileHandler.readProperty("CurrentActivityChildTitleText"))
						.size() == 1) {
					isElementDisplayed("IncompleteActivity",
							PropFileHandler.readProperty("CurrentActivityChildTitleText"));
				} else {
					isElementDisplayed("IncompleteActivity",
							PropFileHandler.readProperty("CurrentActivityParentTitleText") + ": "
									+ PropFileHandler.readProperty("CurrentActivityChildTitleText"));
				}
			} catch (Exception e) {
				try {
					if (elements("CurrentActivityLink",
							PropFileHandler.readProperty("CurrentActivityChildTitleText").toLowerCase()).size() == 1) {
						isElementDisplayed("IncompleteActivity",
								PropFileHandler.readProperty("CurrentActivityChildTitleText").toLowerCase());
					} else {
						isElementDisplayed("IncompleteActivity",
								PropFileHandler.readProperty("CurrentActivityParentTitleText") + ": "
										+ PropFileHandler.readProperty("CurrentActivityChildTitleText").toLowerCase());
					}

				} catch (Exception e1) {
					isElementDisplayed("IncompleteActivity",
							PropFileHandler.readProperty("CurrentActivityParentTitleText") + ": "
									+ Character.toString(
											PropFileHandler.readProperty("CurrentActivityChildTitleText").charAt(0))
											.toUpperCase()
									+ PropFileHandler.readProperty("CurrentActivityChildTitleText").substring(1));
				}
			}
			// changes
		} catch (NoSuchElementException e) {
			for (WebElement element : elements("ActivityLinksCountOnTOC"))
				if (element.getText().trim().equals(PropFileHandler.readProperty("CurrentActivityChildTitleText"))) {
					isElementDisplayed("IncompleteActivity2",
							PropFileHandler.readProperty("CurrentActivityChildTitleText"));

				}

		}
		logMessage("#Test: Current Activity icon displays the status as incomplete for the current activity");

	}

	/* This function switch focus to activity frame of the launched activity */
	public void switch_to_activity_frame() {
		try {
			driver.findElement(By.cssSelector(".center-block.btn.btn-arrow.ng-scope")).isDisplayed();
			switchToDefaultContent();
			driver.findElement(By.cssSelector(".center-block.btn.btn-arrow.ng-scope")).click();
			switchToFrame(element("ActivityFrame"));
		} catch (Exception exception) {
			switchToFrame(element("ActivityFrame"));
		}
	}

	/* This function is for navigating to next activity */
	public void navigate_to_Next_Activity() {
		switchToDefaultContent();
		if (YamlReader.getData("super_product").equals("true")) {
			try {
				element("NextActivityforNewUI").click();
			} catch (Exception ex1) {
				click(element("NextActivity"));
			}
		} else {
			click(element("NextActivity"));
		}
	}

	/*
	 * This function detects the current activity to be Single screen or
	 * Multi-screen
	 */
	public void verifyActivityIsSingleScreenActivityOrMultiScreen() {
		try {
			Assert.assertTrue(isElementDisplayed("NavigationPanel"));
			String lastPageNumber = (element("LastPageOnNavPanel").getText());
			Assert.assertTrue(Integer.parseInt(lastPageNumber) <= 15);
			logMessage(
					"#Test: Current activity is multi screen Activity. Maximum pages on navigation panel is less than 15");
		} catch (Exception e) {
			logMessage("#Test: Current Activity is Single Screen Activity");
		}
	}

	/* This function verify if submit button is enabled on not */
	public boolean submit_button_is_enabled() {
		hardWait(1);
		return element("SubmitButton").isEnabled();
	}

	/* Show Answers and Hide Answers for current activity */
	public void verifyShowAndHideAnswers() {
		int correct_answer = elements("lbl_crt_ans").size();
		int incorrect_answer = elements("lbl_incrt_ans").size();

		logMessage("verified there is '" + correct_answer + "' correct and '" + incorrect_answer
				+ "' in-correct answers respectively in student grade book before Show Answers button submit.");
		PropFileHandler.writeToFile("IncorrectAnswersCount", String.valueOf(correct_answer));

		element("btn_shw_ans").click();
		correct_answer = elements("lbl_crt_ans").size();
		logMessage("verified there is '" + correct_answer + "' correct and '"
				+ String.valueOf(((correct_answer + incorrect_answer) > correct_answer) ? 0 : incorrect_answer)
				+ "' in-correct answers respectively in student grade book after Show Answers button submit.");
		PropFileHandler.writeToFile("TotalAnswersCount", String.valueOf(correct_answer));

		isElementDisplayed("btn_shw_ans");
	}

	/* Submitting the current activity */
	public void Submit_activity() {

		try {
			hardWait(2);
			click(element("SubmitButton"));
			logMessage("******Activity is now Submitted******");
			verifyShowAndHideAnswers();
		} catch (Exception ex) {
			switchToDefaultContent();
			switch_to_activity_frame();
			Assert.assertTrue(isElementDisplayed("ContinueButton"));
			click_continue();
			hardWait(1);
			click(element("SubmitButton"));
			logMessage("******Activity is now Submitted******");
			hardWait(2);
			verifyShowAndHideAnswers();
			if (YamlReader.getData("super_product").equals("true")) {
				try {
					isElementDisplayed("NextActivityforNewUI");
				} catch (Exception ex2) {
					switchToDefaultContent();
					click(element("CloseButton"));
					hardWait(3);
					try {
						driver.findElement(By.cssSelector("#asg-btn-show-all")).click();
						String AssignName = PropFileHandler.readProperty("AssignmentActivitName").toString().trim();
						hardWait(2);
						String AssignScore = driver
								.findElement(By.xpath("//*[contains(text(),'" + AssignName + "')]/ancestor::tr//td[3]"))
								.getText().toString();
						System.out.println("Assignement Score:" + AssignScore);
						PropFileHandler.writeToFile("AssignmentScore", AssignScore);

					} catch (Exception e) {
						driver.findElement(By.xpath(".//li[@id='header_grades_assigned']/a")).click();
						hardWait(2);
						String AssignName = PropFileHandler.readProperty("AssignmentActivitName").toString().trim();
						String AssignScore = driver
								.findElement(By.xpath("//a[contains(text(),'" + AssignName + "')]/ancestor::tr//td[3]"))
								.getText().toString();
						System.out.println("Assignement Score:" + AssignScore);
						PropFileHandler.writeToFile("AssignmentScore", AssignScore);
					}
				}
				}
			
			}
		
		}
	

	public void Submit_activity_essay() {

		try {
			hardWait(2);
			click(element("SubmitButton"));
			logMessage("******Activity is now Submitted******");

		} catch (Exception ex) {
			switchToDefaultContent();
			switch_to_activity_frame();
			Assert.assertTrue(isElementDisplayed("ContinueButton"));
			click_continue();
			hardWait(1);
			click(element("SubmitButton"));
			logMessage("******Activity is now Submitted******");
			hardWait(1);

			switchToDefaultContent();
			click(element("CloseButton"));
			hardWait(3);
			try {
				driver.findElement(By.cssSelector("#asg-btn-show-all")).click();
				String AssignName = PropFileHandler.readProperty("AssignmentActivitName").toString().trim();
				String AssignScore = driver
						.findElement(By.xpath("//*[contains(text(),'" + AssignName + "')]/ancestor::tr//td[3]"))
						.getText().toString();
				PropFileHandler.writeToFile("AssignmentScore", AssignScore);

			} catch (Exception e) {
				driver.findElement(By.cssSelector("[data-href='#/grades/book/']")).click();
				String AssignName = PropFileHandler.readProperty("CurrentActivityChildTitleText").toString().trim();
				String AssignScore = driver.findElement(By.xpath(
						".//span[contains(text(),'" + AssignName + "')]/parent::a/parent::td/following-sibling::td[2]"))
						.getText().toString();
				PropFileHandler.writeToFile("AssignmentScore", AssignScore);
			}

		}

	}

	/* Closing the activity */
	public void close_activity() {
		try {
			switchToDefaultContent();
			hardWait(1);
			Assert.assertTrue(isElementDisplayed("CloseButton"));
			
			//driver.findElement(By.id("closePopUp")).click();
			//Assert.assertFalse(isElementDisplayed("closePopUp"));
			//System.out.println("Activity is Closed");
			element("closePopUp").click();
			
			//click(element("CloseButton"));
			//executeJavascript("document.querySelector('#closePopUp').click()");
		} catch (Exception ex) {
			System.out.println("This is super Product Activity");
		}
	}

	/*
	 * This button Continues the current incomplete activity when launched from
	 * TOC
	 */
	public void click_continue() {
		click(element("ContinueButton"));
	}

	/* Starting again an incomplete activity */
	public void click_StartAgain() {
		click(element("StartAgainButton"));
	}

	/* Refresh current activity and Continue */
	public void refresh_current_activity() {
		driver.navigate().refresh();
		hardWait(1);
		switchToDefaultContent();
		switch_to_activity_frame();
		click_continue();

	}

	/*
	 * This function verify the Soft save functionality. Application waits for
	 * 30 seconds.
	 */
	public void verify_soft_save() {
		hardWait(30);
		close_activity();
		driver.navigate().refresh();
		verify_currect_activity_is_Incomplete();
		navigate_back_to_activity_from_TOC();
		switchToDefaultContent();
		switch_to_activity_frame();
		Assert.assertTrue(isElementDisplayed("ContinueButton"));
		click_continue();
		hardWait(1);
		logMessage("#Test: Soft Save is working correctly");
	}

	/* This function verifies the status of show answer button */
	public boolean verify_Show_answer_is_Enabled_after_submit() {
		return element("ShowAnswer").isEnabled();
	}

	/* Clicking show answer button after submit */
	public void click_show_answer() {
		click(element("ShowAnswer"));
	}

	/* This navigates back to the current activity from grades */
	public void navigate_back_to_Activity_from_Grades() {
		click(element("SelfStudyActivity"));
		driver.navigate().refresh();
		hardWait(2);
		navigate_back_to_activity_from_TOC();
	}

	/* This navigates to grades from the current activity */
	public void navigate_to_Grades() {
		switchToDefaultContent();
		click(element("CloseButton"));
		click(element("GradesButton"));
		String Gradebookactivityname = element("td_gradebookActivityName").getText().toString().trim();
		PropFileHandler.writeToFile("GradeBookActivityName", Gradebookactivityname);
		logMessage("Verified that ActivityName on Grades Page :-  " + Gradebookactivityname + " is displayed.");
	}

	/* This function verifies that grades for current activity is displayed */
	public void verify_grades_are_displayed_for_submitted_activity() {
		String temp;
		try {
			try {
				temp = PropFileHandler.readProperty("CurrentActivityParentTitleText") + ": "
						+ PropFileHandler.readProperty("CurrentActivityChildTitleText");
				Assert.assertTrue(isElementDisplayed("ActivityTitleinGrades", temp));
				Assert.assertTrue(isElementDisplayed("Grades", temp));
				PropFileHandler.writeToFile("GradesForCurrentActivity", element("Grades", temp).getText());
				click(element("Grades", temp));
			} catch (Exception e) {
				try {
					temp = PropFileHandler.readProperty("CurrentActivityParentTitleText") + ": "
							+ PropFileHandler.readProperty("CurrentActivityChildTitleText").toLowerCase();
					Assert.assertTrue(isElementDisplayed("ActivityTitleinGrades", temp));
					Assert.assertTrue(isElementDisplayed("Grades", temp));
					PropFileHandler.writeToFile("GradesForCurrentActivity", element("Grades", temp).getText());
					click(element("Grades", temp));
				} catch (Exception e1) {
					temp = PropFileHandler.readProperty("CurrentActivityParentTitleText") + ": "
							+ Character
									.toString(PropFileHandler.readProperty("CurrentActivityChildTitleText").charAt(0))
									.toUpperCase()
							+ PropFileHandler.readProperty("CurrentActivityChildTitleText").substring(1);
					Assert.assertTrue(isElementDisplayed("ActivityTitleinGrades", temp));
					Assert.assertTrue(isElementDisplayed("Grades", temp));
					PropFileHandler.writeToFile("GradesForCurrentActivity", element("Grades", temp).getText());
					click(element("Grades", temp));
				}
			}
		} catch (Exception e) {

			try {
				Assert.assertTrue(isElementDisplayed("ActivityTitleinGrades",
						PropFileHandler.readProperty("CurrentUnitTitleText") + " / "
								+ PropFileHandler.readProperty("CurrentActivityParentTitleText") + " / "
								+ PropFileHandler.readProperty("CurrentActivityChildTitleText")));
			} catch (Exception ex) {
				try {
					Assert.assertTrue(isElementDisplayed("ActivityTitleinGrades",
							PropFileHandler.readProperty("GradeBookActivityName")));
				} catch (Exception ex2) {

				}

			}
			try {
				Assert.assertTrue(isElementDisplayed("Grades",
						PropFileHandler.readProperty("CurrentUnitTitleText") + " / "
								+ PropFileHandler.readProperty("CurrentActivityParentTitleText") + " / "
								+ PropFileHandler.readProperty("CurrentActivityChildTitleText")));
			} catch (Exception ex1) {
				try {
					Assert.assertTrue(
							isElementDisplayed("Grades", PropFileHandler.readProperty("GradeBookActivityName")));
				} catch (Exception ex) {

				}

			}
			try {
				PropFileHandler.writeToFile("GradesForCurrentActivity",
						element("Grades",
								PropFileHandler.readProperty("CurrentUnitTitleText") + " / "
										+ PropFileHandler.readProperty("CurrentActivityParentTitleText") + " / "
										+ PropFileHandler.readProperty("CurrentActivityChildTitleText")).getText());

				click(element("Grades",
						PropFileHandler.readProperty("CurrentUnitTitleText") + " / "
								+ PropFileHandler.readProperty("CurrentActivityParentTitleText") + " / "
								+ PropFileHandler.readProperty("CurrentActivityChildTitleText")));

			} catch (Exception ex2) {
				PropFileHandler.writeToFile("GradesForCurrentActivity",
						element("Grades", PropFileHandler.readProperty("GradeBookActivityName")).getText());
				click(element("Grades", PropFileHandler.readProperty("GradeBookActivityName")));
				Assert.assertTrue(isElementDisplayed("ReviewActivityScoresTab"));

			}

			// click(element("Grades",PropFileHandler.readProperty("CurrentUnitTitleText")+
			// " / "+
			// PropFileHandler.readProperty("CurrentActivityParentTitleText")+ "
			// / "+
			// PropFileHandler.readProperty("CurrentActivityChildTitleText")));
		}
		logMessage("#Test: Grades are displayed for current submitted activity in the Gradebook");
	}

	/* This function returns the max score for current activity */
	public float max_possible_Score_of_activity() {
		String[] score = PropFileHandler.readProperty("GradesForCurrentActivity").split("/");
		PropFileHandler.writeToFile("Max_Score", score[1]);
		return Float.valueOf(score[1]);
	}

	/* This function returns the number of rows in the Gradebook */
	public int number_of_rows_in_the_Gradebook() {
		click(element("ReviewScoresTab"));
		return elements("StudentAnswer").size();
	}

	/*
	 * This function verifies that if any PDF is available for current activity
	 * and also it is enabled
	 */
	public void verify_PDF_button_is_enabled() {
		try {
			Assert.assertTrue(element("PDFIcon").isEnabled());
			logMessage("#Test: Pdf icon is enabled after submit ");
		} catch (Exception e) {
			logMessage("#Test: No Pdf icon is present in the current activity");
			hardWait(2);
		}
	}

	public void set_activity_title() {
		switchToDefaultContent();
		System.out.println("essaysysywritten");
		PropFileHandler.writeToFile("AssignmentActivityName",
				driver.findElement(By.cssSelector(".hand.no-white-space.ng-binding.ng-scope")).getText().trim());
		
	}

}
