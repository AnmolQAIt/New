package com.qait.myelt.tests;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValues;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.ResourceLoader;
import com.qait.automation.utils.YamlReader;

public class EngineTest {

	TestSessionInitiator test;

	@BeforeClass
	public void Start_Test_Session() {
		test = new TestSessionInitiator();
		test.launchApplication(getData("baseurl"));
	}

	@Test
	public void Step01_Login_For_Engine_Test() {
		test.loginPage.VerifyUserIsOnLoginPage();
		test.loginPage.LoginToTheApplication(
				ResourceLoader.readProperties("admin_student_account_id"),
				YamlReader.getYamlValue("passwd"));
	}

	@Test(dependsOnMethods = "Step01_Login_For_Engine_Test")
	public void Step02_Launch_Course() {
		String self_activity = ResourceLoader.readProperties("inst_assign_book_name");
		try {
			self_activity = self_activity.substring(0,self_activity.lastIndexOf("HTML")).trim();
		} catch (Exception e) {
			self_activity = ResourceLoader.readProperties("inst_assign_book_name");
		}
		test.assignment.launchCourse(self_activity);
	}

	@Test(dependsOnMethods = "Step02_Launch_Course")
	public void Step03_Launch_Activity() {
		test.assignment.launchActivity();
	}

	@Test(dependsOnMethods = "Step03_Launch_Activity")
	public void Step04_Test_Activity() {
		test.assignment.testActivity();
	}

	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.customFunctions.takeScreenShotOnException(
				getYamlValues("screenshot"), this.getClass().getSimpleName(),
				result);
	}

//	@AfterClass
//	public void Stop_Test_Session() {
//		test.closeBrowserSession();
//	}

}
