/*package com.qait.myelt.tests;

import static com.qait.automation.utils.ResourceLoader.getKey;
import static com.qait.automation.utils.ResourceLoader.getValue;
import static com.qait.automation.utils.ResourceLoader.randomInt;
import static com.qait.automation.utils.ResourceLoader.readProperties;
import static com.qait.automation.utils.ResourceLoader.subStringAfterBrackets;
import static com.qait.automation.utils.ResourceLoader.subStringBeforeBrackets;
import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValues;

import java.util.Map;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.PropFileHandler;
import com.qait.automation.utils.ResourceLoader;
import com.qait.automation.utils.YamlReader;

public class TagVerification {

	TestSessionInitiator test;
	private String grade = null;

	@BeforeClass
	public void Start_Test_Session() {
		test = new TestSessionInitiator();
		test.launchApplication(getData("baseurl"));
	}
	
	@Test
	public void Step01_Login_For_TagVerification() {
		test.loginPage.VerifyUserIsOnLoginPage();
		test.loginPage.LoginToTheApplication(
				ResourceLoader.readProperties("TagVerification_User_id"),
				YamlReader.getYamlValue("passwd"));
	}
	
	@Test(dependsOnMethods = "Step01_Login_For_TagVerification")
	public void verification_of_Tag() {
			test.Tag.VerifyTag(Tag, type);
			test.Tag.launchActivity();
			test.Tag.VerifyStudentAttemptSelfActivityAssignment();
			grade = test.Tag.VerifySelfActivityAssignmentGrade();
			System.out.println("*********************GRADE IS: " + grade + "*******************************");
			test.Tag.verifyReviewActivityAndReviewScoresTab();
			test.loginPage.VerifyUserIsOnSignOutPage();
			String activityName = PropFileHandler.readProperty("CurrentActivityChildTitleText");
			if (activityName.contains("(")) {
				activityName = activityName.substring(activityName.indexOf("(") + 1, activityName.lastIndexOf(")"));
			}
			test.loginPage.VerifyUserIsOnSignOutPage();
		}

		
		 * This methods verify the Grades of Super Products on New UI
		 
		public void verification_of_Grades_SuperProducts() {
			test.launchApplication(getData("baseurl"));
			test.loginPage.LoginToTheApplication(readProperties("admin_student_account_id"), getData("passwd"));
			test.Tag.VerifyTag(readProperties("inst_book_id"));
			test.Tag.naviagteThroughSuperProductActivity();
			test.Tag.VerifyStudentAttemptAssignment();
			grade = test.Tag.VerifyAssignmentGrade();
			test.Tag
					.verifyReviewActivityAndReviewScoresTab(PropFileHandler.readProperty("AssignmentActivitName"));
			test.loginPage.VerifyUserIsOnSignOutPage();
			System.out.println("******************Grade is: " + grade);
		}

		@AfterMethod
		public void take_screenshot_on_failure(ITestResult result) {
			test.customFunctions.takeScreenShotOnException(getYamlValues("screenshot"), this.getClass().getSimpleName(),
					result);
		}

		@AfterClass
		public void stop_test_session() {
			// test.closeBrowserSession();
		}
}*/


