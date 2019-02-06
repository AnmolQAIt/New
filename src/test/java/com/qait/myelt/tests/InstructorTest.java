package com.qait.myelt.tests;

import static com.qait.automation.utils.ResourceLoader.logOutputFile;
import static com.qait.automation.utils.ResourceLoader.readProperties;
import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValues;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.ResourceLoader;
import com.sun.jna.platform.win32.WinUser.HARDWAREINPUT;

public class InstructorTest {
	TestSessionInitiator test;

	@BeforeClass
	public void start_test_Session() {
		test = new TestSessionInitiator();
	}

	@Test
	public void Step01_Launch_Application() {
		test.launchApplication(getData("baseurl"));
	}

	@Test(dependsOnMethods = "Step01_Launch_Application")
	public void Step02_LoginApplication() {
		test.loginPage.LoginToTheApplication(readProperties("inst_account_id"), getData("passwd"));
	}

	@Test(dependsOnMethods = "Step02_LoginApplication")
	public void Step03_Instructor_Course_Creation() {
		test.instActions.VerifyCourseCreation();
		test.instActions.VerifyCourseCreation1();
		test.instActions.VerifyCourseCreation2();
		test.instActions.VerifyEditCourse();
	}

	@Test(dependsOnMethods = "Step03_Instructor_Course_Creation")
	public void Step04_Instructor_Student_Creation() {
		test.instActions.VerifyStudentCreation(test.adminAction);
	}

@Test(dependsOnMethods = "Step04_Instructor_Student_Creation")
	public void Step05_Instructor_EnrolStudent() {
		test.instActions.VerifyStudentEnrollInCourse(readProperties("admin_student_account_id"));
		test.instActions.VerifyStudentEnrollInCourse(getData("old_student_enroll"));
		Reporter.log("verified old student is successfully enrolled in the course.");
		test.instActions.sendCourseKeyThroughEmail();
		 test.instActions.VerifyStudentEnrollInCourse(readProperties("inst_student_account_id"));
	}

	@Test(dependsOnMethods = "Step05_Instructor_EnrolStudent")
	public void Step06_Instructor_Assingment_Creation() {
		test.instActions.VerfiyCreateAssessment();
		//test.instActions.VerifyEditAssignment();
		test.loginPage.VerifyUserIsOnSignOutPage();
		test.loginPage.LoginToTheApplication(readProperties("admin_student_account_id"), getData("passwd"));
		test.studActions.VerifyAssignmentForStudent();
		test.loginPage.VerifyUserIsOnSignOutPage();
	}

	@Test(dependsOnMethods = "Step06_Instructor_Assingment_Creation")
	public void Step07_Instructor_ForgetPassword() {
		test.launchApplication(getData("baseurl"));
		test.loginPage.LoginToTheApplication(readProperties("inst_account_id"), "");
		test.instActions.VerifyForgetPassword(readProperties("inst_account_id"));
		test.loginPage.LoginToTheApplication(readProperties("inst_account_id"), getData("passwd"));
		test.instActions.VerifyEmailCourseKeyToMemebers(readProperties("inst_crs_name"), getData("Email_Subject"),
				readProperties("course_key"));
		test.loginPage.VerifyUserIsOnSignOutPage();
	}

	@Test(dependsOnMethods = "Step07_Instructor_ForgetPassword")
	public void Step08_Create_Instructor_Through_CreateAnAccountOption() {
		test.instActions.clickOnCreateAnAccountLink();
		test.instActions.selectInstructorTypeAccountAndClickNextButton();
		test.instActions.fillUserInformationWithValidEmailAddress();
		test.instActions.selectSeriesAndItsAssociatedLevelsAndClickNextButton();
		test.instActions.reviewAccountInformationAndClickSubmitButton();
		test.instActions.verifySuccessfulEmailConfirmationMessageOfInstructorAccount();
}

	@Test(dependsOnMethods = "Step08_Create_Instructor_Through_CreateAnAccountOption")
	public void Step09_Verify_Registration_And_Request_Approval_Process_Through_SuperAdmin() {
		test.loginPage.LoginToTheApplication(getData("users.Super_Admin.username"),
				getData("users.Super_Admin.passwd"));
		test.adminAction.clickOnInstructorRequestsLink();
		test.adminAction.selectViewAllFromStatusDropDown();
		test.adminAction.clickOnSelectLink();
		test.adminAction.sendAccountApprovalEmailToInstructor();
		test.adminAction.clickOnApproveButtonAndVerifyApproveMessage();
		test.loginPage.VerifyUserIsOnSignOutPage();
		test.loginPage.LoginToTheApplication(getData("email_account"),
				"natgeo");
		test.loginPage.VerifyUserIsOnSignOutPage();
		Reporter.log(logOutputFile(
				"Verified that Admin " + getData("email_account") + " is able to login Successfully."));
		
		
	}

	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.customFunctions.takeScreenShotOnException(getYamlValues("screenshot"), this.getClass().getSimpleName(),
				result);
	}

	//@AfterClass
	public void stop_test_session() {
		test.closeBrowserSession();
	}
}
