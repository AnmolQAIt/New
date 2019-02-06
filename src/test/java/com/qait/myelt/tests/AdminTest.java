package com.qait.myelt.tests;

import static com.qait.automation.utils.ResourceLoader.getBooks;
import static com.qait.automation.utils.ResourceLoader.readProperties;
import static com.qait.automation.utils.YamlReader.getData;
import static com.qait.automation.utils.YamlReader.getYamlValues;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.DataReadWrite;

public class AdminTest {

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
		test.loginPage.LoginToTheApplication(getData("users.Admin.username"), getData("users.Admin.passwd"));
	}

	@Test(dependsOnMethods = "Step02_LoginApplication")
	public void Step03_Create_Student() {
		test.adminAction.VerifyAdminPage();
		test.adminAction.VerifyStudentCreation("admin");
		test.loginPage.VerifyUserIsOnSignOutPage();
	}

	@Test(dependsOnMethods = "Step03_Create_Student")
	public void Step04_StudentAccountVerification() {
		test.loginPage.LoginToTheApplication(readProperties("admin_student_account_id"), getData("passwd"));
		test.loginPage.VerifyUserIsOnSignOutPage();
	}

	@Test(dependsOnMethods = "Step04_StudentAccountVerification")
	public void Step05_Create_Instructor() {
		test.loginPage.LoginToTheApplication(getData("users.Admin.username"), getData("users.Admin.passwd"));
		test.adminAction.VerifyAdminPage();
		test.adminAction.VerifyInstructorCreation();
		test.loginPage.VerifyUserIsOnSignOutPage();
	}

	@Test(dependsOnMethods = "Step05_Create_Instructor")
	public void Step06_InstructorAccountVerification() {
		test.loginPage.LoginToTheApplication(readProperties("inst_account_id"), getData("passwd"));
		test.loginPage.VerifyUserIsOnSignOutPage();
	}

	@Test(dependsOnMethods = "Step06_InstructorAccountVerification")
	public void Step07_Add_Student() {
		test.loginPage.LoginToTheApplication(getData("users.Admin.username"), getData("users.Admin.passwd"));
		test.adminAction.VerifyAdminTools();
		test.adminAction.VerifyAddEditDeleteUserClicked();
		test.adminAction.VerifyAddUserOptions("Student");
		test.loginPage.VerifyUserIsOnSignOutPage();
	}

	@Test(dependsOnMethods = "Step07_Add_Student")
	public void Step08_Add_Instructor() {
		test.loginPage.LoginToTheApplication(getData("users.Admin.username"), getData("users.Admin.passwd"));
		test.adminAction.VerifyAdminTools();
		test.adminAction.VerifyAddEditDeleteUserClicked();
		test.adminAction.VerifyAddUserOptions("Instructor");
		test.loginPage.VerifyUserIsOnSignOutPage();
	}

	@Test(dependsOnMethods = "Step08_Add_Instructor")
	public void Step09_Book_Permission_Instructor() {
		test.loginPage.LoginToTheApplication(getData("users.Admin.username"), getData("users.Admin.passwd"));
		test.adminAction.VerifyAdminTools();
		test.adminAction.VerifyAddEditDeleteUserClicked();
		test.adminAction.VerifyUserSearch(readProperties("inst_account_id"));
		test.adminAction.VerifyBookPermission(getBooks(), "Instructor");
		test.loginPage.VerifyUserIsOnSignOutPage();
	}

	@Test(dependsOnMethods = "Step09_Book_Permission_Instructor")
	public void Step10_Book_Permission_Student() {
		test.loginPage.LoginToTheApplication(getData("users.Admin.username"), getData("users.Admin.passwd"));
		test.adminAction.VerifyAdminTools();
		test.adminAction.VerifyAddEditDeleteUserClicked();
		test.adminAction.VerifyUserSearch(readProperties("admin_student_account_id"));
		Map<String, String> books = new HashMap<String, String>();
		books.put(readProperties("inst_book_id"), readProperties("inst_assign_book_name"));
		test.adminAction.VerifyBookPermission(books, "Student");
		test.loginPage.VerifyUserIsOnSignOutPage();
	}

	ArrayList<String> bookNames = new ArrayList<>();

	@Test(dependsOnMethods = "Step07_Add_Student")
	public void Step12_Permission_of_All_DeployedBooks_Student() {
		test.launchApplication(getData("baseurl"));
		test.loginPage.LoginToTheApplication(getData("users.Admin.username"), getData("users.Admin.passwd"));
		test.adminAction.VerifyAdminTools();
		test.adminAction.VerifyAddEditDeleteUserClicked();
		test.adminAction.VerifyUserSearch(readProperties("admin_student_account_id"));
		Map<String, String> books = new HashMap<String, String>();
		books = DataReadWrite.readBooksFromCSVFile("books.csv");
		test.adminAction.switchToBookPermissionFrames();
		for (Map.Entry<String, String> book : books.entrySet()) {
			bookNames.add(test.adminAction.VerifyAllDeployedBookPermission(book, "Student"));
		}
		test.adminAction.switchToDefaultFrame();
		test.loginPage.VerifyUserIsOnSignOutPage();
	}

	@Test(dependsOnMethods = "Step12_Permission_of_All_DeployedBooks_Student")
	public void Step11_Permission_Validation() {
		test.loginPage.LoginToTheApplication(readProperties("admin_student_account_id"), getData("passwd"));
		for (String bookName : bookNames) {
			test.adminAction.VerifyPermissonOFBookToStudent(bookName);
		}
		test.loginPage.VerifyUserIsOnSignOutPage();

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
