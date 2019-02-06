package com.qait.myelt.tests;

import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValues;

import java.util.ArrayList;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.ResourceLoader;

public class AccessCodeVerificationTest {

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
	public void Step02_Create_Student() {
		test.acvt.VerifyBookAccess();
	}

	@Test(dependsOnMethods = "Step01_Launch_Application", enabled = true)
	public void Step03_Create_Student_And_Provide_Access_OF_All_Deployed_HTML_Books() {
		ArrayList<String> allHTMLBooksList = ResourceLoader.getAllDeployedHTMLBooks();
		if (allHTMLBooksList.isEmpty()) {
			System.out.println("There are no html access code present in file. "
					+ "So, enter the access code in file 'htmlContent_codeDeployedBooks.txt' "
					+ "and then re-run the test.");
		} else {
			Boolean result = test.acvt.verifyAllHTMLBookAccess(allHTMLBooksList.get(0));
			for (String htmlBook : allHTMLBooksList) {
				if (!result) {
					result = test.acvt.re_EnterHMTLBooksAccessCode(htmlBook);
				} else {
					String login_id = test.acvt.fillDetailsWithUsernameContainingEmail();
					System.out.println(login_id + " with password: " + getData("passwd") + "created successfully.");
					test.acvt.navigateToDeployedHMTLBook();
					test.acvt.verifyDeployedHMTLBookAddedToStudent(htmlBook);
					test.loginPage.VerifyUserIsOnSignOutPage();
				}
			}
			ResourceLoader.cleanHTMLDeployedBooksFile();
		}
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
