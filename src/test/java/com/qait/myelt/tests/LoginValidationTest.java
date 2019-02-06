package com.qait.myelt.tests;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValues;
import static com.qait.automation.utils.ResourceLoader.*;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.qait.automation.TestSessionInitiator;

public class LoginValidationTest {

	TestSessionInitiator test;

	@BeforeClass
	public void start_test_Session() {
		test = new TestSessionInitiator();
		Reporter.log(InitialEmptyLogFile());
	}

	@Test
	public void Step01_Launch_Application() {
		test.launchApplication(getData("baseurl"));
		test.loginPage.VerifyUserIsOnLoginPage();
		Reporter.log(logOutputFile(
				"Verified that Web Url " + getData("baseurl") + " is Successfully launched on Web Browser."));
	}

	@Test(dependsOnMethods = "Step01_Launch_Application")
	public void Step02_Login_With_Admin() {
		test.loginPage.LoginToTheApplication(getData("users.Admin.username"), getData("users.Admin.passwd"));
		test.loginPage.VerifyUserIsOnSignOutPage();
		Reporter.log(logOutputFile(
				"Verified that Admin " + getData("users.Admin.username") + " is able to login Successfully."));
	}

	@Test(dependsOnMethods = "Step01_Launch_Application")
	public void Step03_Login_With_Instructor() {
		test.loginPage.LoginToTheApplication(getData("users.instructor.username"), getData("users.instructor.passwd"));
		test.loginPage.VerifyUserIsOnSignOutPage();
		Reporter.log(logOutputFile("Verified that Instructor " + getData("users.instructor.username")
				+ " is able to login Successfully."));
	}

	@Test(dependsOnMethods = "Step01_Launch_Application")
	public void Step04_Login_With_Student() {
		test.loginPage.LoginToTheApplication(getData("users.Student.username"), getData("users.Student.passwd"));
		test.loginPage.VerifyUserIsOnSignOutPage();
		Reporter.log(logOutputFile(
				"Verified that Student " + getData("users.Student.username") + " is able to login Successfully."));
	}

	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.customFunctions.takeScreenShotOnException(getYamlValues("screenshot"), this.getClass().getSimpleName(),
				result);
	}

	@AfterClass
	public void stop_test_session() {
		test.closeBrowserSession();
	}

}
