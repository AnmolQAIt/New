package com.qait.myelt.tests;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValues;
import static com.qait.automation.utils.ResourceLoader.*;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.PropFileHandler;

public class StudentTest {
	TestSessionInitiator test;

	@BeforeClass
	public void start_test_Session() {
		test = new TestSessionInitiator();
	}

	@Test
	public void Step01_Launch_Application() {
		test.launchApplication(getData("baseurl"));
		
	}

	@Test(dependsOnMethods = "Step01_Launch_Application", enabled = false)
	public void Step02_EnrolCourseKey() {
		test.studActions.VerifyCourseKeyEnrolment();
		test.loginPage.VerifyUserIsOnSignOutPage();
	}

	@Test(dependsOnMethods = "Step01_Launch_Application", enabled = false)
	public void Step03_EnrolContentCode() {
		test.studActions.VerifyContentKeyEnrolment();
		test.loginPage.VerifyUserIsOnSignOutPage();
	}

	@Test(dependsOnMethods = "Step01_Launch_Application", enabled = false)
	public void Step04_EnrolResourceTab() {
		test.launchApplication(getData("baseurl"));
		test.loginPage.LoginToTheApplication(readProperties("inst_student_account_id"), getData("passwd"));
		test.studActions.VerifyEditUserProfile();
		test.cmnActions.VerifyAnnouncements();
		test.studActions.VerifyResourceTabEnrolment1();
		test.studActions.VerifyResourceTabEnrolment2();
		test.studActions.VerifyResourceTabEnrolment3();
		test.cmnActions.VerifyGridAandListView();
		test.loginPage.VerifyUserIsOnSignOutPage();
	}

	@Test(dependsOnMethods = "Step04_EnrolResourceTab", enabled = false)
	public void Step05_ChangePasswordAndNavigateHelpPage() {
		test.loginPage.LoginToTheApplication(readProperties("inst_student_account_id"), getData("passwd"));
		test.cmnActions.VerifyAllCoursesType();
		test.cmnActions.VerifySendEmailToInstructor();
		test.cmnActions.VerifyChangeLanguage();
		test.studActions.VerifyChangePassword(readProperties("inst_student_account_id"));
		test.loginPage.VerifyUserIsOnSignOutPage();
		test.studActions.VerifyHelpPage(readProperties("inst_student_account_id"));
	}

	private String grade = null;

	// @Test(dependsOnMethods = "Step05_ChangePasswordAndNavigateHelpPage")
	@Test
	public void Step06_CompleteInstructorGrading() {
		test.launchApplication(getData("baseurl"));
		test.loginPage.LoginToTheApplication(readProperties("admin_student_account_id"), getData("passwd"));
		test.studActions.VerifyTakeAssignment();
		test.studActions.VerifyStudentAttemptAssignment();
		grade = test.studActions.VerifyAssignmentGrade();
		test.loginPage.VerifyUserIsOnSignOutPage();
		test.loginPage.LoginToTheApplication(readProperties("inst_account_id"), getData("passwd"));
		test.instActions.VerifyInstructorGradeBook(grade);
		test.instActions.VerifyAssignmentStatisticsReports();
		test.loginPage.VerifyUserIsOnSignOutPage();

		if (getData("super_product").equals("false")) {
			verification_of_Grades_onOldUIbooks();
		}
		if (getData("super_product").equals("true")) {
			verification_of_Grades_SuperProducts();
		}
		test.loginPage.LoginToTheApplication(readProperties("inst_account_id"), getData("passwd"));
		test.instActions.verifySelfActivityGradeOnInstructorEnd(grade);
		String activityName = PropFileHandler.readProperty("AssignmentActivitName");
		if (activityName.contains("(")) {
			activityName = activityName.substring(activityName.indexOf("(") + 1, activityName.lastIndexOf(")"));
		}
		System.out.println(activityName);
		test.instActions.verifyScoreOfCorrectAndInCorrectAnswersOnInstructorEnd(readProperties("inst_book_id"),
				readProperties("admin_student_account_id"), activityName);
		test.instActions.VerifScoresByAssignmentReports(grade, readProperties("admin_student_account_id"));
		test.loginPage.VerifyUserIsOnSignOutPage();
		System.out.println("******************Grade is: " + grade);
	}

	/*
	 * This methods verify the Grades of books on olD UI
	 */
	public void verification_of_Grades_onOldUIbooks() {
		test.loginPage.LoginToTheApplication(readProperties("admin_student_account_id"), getData("passwd"));
		test.studActions.VerifyTakeSelfStudyActivity(readProperties("inst_book_id"));
		test.studActions.launchActivity();
		test.studActions.VerifyStudentAttemptSelfActivityAssignment();
		grade = test.studActions.VerifySelfActivityAssignmentGrade();
		System.out.println("*********************GRADE IS: " + grade + "*******************************");
		test.studActions.verifyReviewActivityAndReviewScoresTab();
		test.loginPage.VerifyUserIsOnSignOutPage();

		test.loginPage.LoginToTheApplication(readProperties("inst_account_id"), getData("passwd"));
		test.instActions.verifySelfActivityGradeOnInstructorEnd(grade);
		String activityName = PropFileHandler.readProperty("CurrentActivityChildTitleText");
		if (activityName.contains("(")) {
			activityName = activityName.substring(activityName.indexOf("(") + 1, activityName.lastIndexOf(")"));
		}
		test.instActions.verifyScoreOfCorrectAndInCorrectAnswersOnInstructorEnd(readProperties("inst_book_id"),
				readProperties("admin_student_account_id"), activityName);
		test.instActions.VerifScoresByAssignmentReports(grade, readProperties("admin_student_account_id"));
		test.loginPage.VerifyUserIsOnSignOutPage();
	}

	/*
	 * This methods verify the Grades of Super Products on New UI
	 */
	public void verification_of_Grades_SuperProducts() {
		test.launchApplication(getData("baseurl"));
		test.loginPage.LoginToTheApplication(readProperties("admin_student_account_id"), getData("passwd"));
		test.studActions.VerifyTakeSelfStudyActivity(readProperties("inst_book_id"));
		test.studActions.naviagteThroughSuperProductActivity();
		test.studActions.VerifyStudentAttemptAssignment();
		grade = test.studActions.VerifyAssignmentGrade();
		test.studActions
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
}
