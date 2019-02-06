package com.qait.myelt.keywords;

import static com.qait.automation.utils.ResourceLoader.getKey;
import static com.qait.automation.utils.ResourceLoader.getValue;
import static com.qait.automation.utils.ResourceLoader.randomInt;
import static com.qait.automation.utils.ResourceLoader.readProperties;
import static com.qait.automation.utils.ResourceLoader.subStringAfterBrackets;
import static com.qait.automation.utils.ResourceLoader.subStringBeforeBrackets;
import static com.qait.automation.utils.ResourceLoader.writeProperties;
import static com.qait.automation.utils.YamlReader.getData;

import java.net.URL;
import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.qait.automation.getpageobjects.GetPage;

import junit.framework.Assert;

public class AdminPageActions extends GetPage {

	WebDriver driver = null;

	public AdminPageActions(WebDriver driver) {
		super(driver, "AdminPage");
		this.driver = driver;
	}

	public void VerifyAdminPage() {
		hardWait(2);
		isElementDisplayed("student_tab");
		element("student_tab").click();
		logMessage("Verified that the user is on the MyELT Admin Page");
	}

	/**
	 * Method which verifies student creation.
	 * 
	 */
	public void VerifyStudentCreation(String std_type) {
		/* check create student button is displayed */
		isElementDisplayed("crt_student_btn");
		element("crt_student_btn").click();

		/* switch to student dialog box */

		/* check select box user type is student is displayed */
		if (std_type == "admin") {
			isElementDisplayed("usr_type");
			element("usr_type").click();
			Select usr_type = new Select(element("usr_type"));
			usr_type.selectByValue("1");
		}

		/* created user account & stored in userdata property file */
		isElementDisplayed("usr_account");
		String student_account_id = "qai" + System.currentTimeMillis();
		writeProperties(std_type + "_student_account_id", student_account_id);
		element("usr_account").sendKeys(student_account_id);

		/* verify first, middle, last name of student */
		isElementDisplayed("usr_f_name");
		element("usr_f_name").sendKeys(student_account_id);
		isElementDisplayed("usr_m_name");
		element("usr_m_name").sendKeys(getData("Student.MiddleName"));
		isElementDisplayed("usr_l_name");
		element("usr_l_name").sendKeys(getData("Student.LastName"));

		/* verify student password & confirm again */
		isElementDisplayed("usr_passwd");
		element("usr_passwd").sendKeys(getData("passwd"));
		isElementDisplayed("usr_cfn_passwd");
		element("usr_cfn_passwd").sendKeys(getData("passwd"));

		/* verify student verification question & answer */
		// isElementDisplayed("usr_vf_ques");
		// Select verf_question = new Select(element("usr_vf_ques"));
		// verf_question.selectByIndex(1);
		// isElementDisplayed("usr_vf_ans");
		// element("usr_vf_ans").sendKeys(getData("Student.school"));

		/* verify student email & confirm again */
		isElementDisplayed("usr_email");
		element("usr_email").sendKeys(student_account_id + "@qai.com");
		isElementDisplayed("usr_re_email");
		element("usr_re_email").sendKeys(student_account_id + "@qai.com");
		isElementDisplayed("usr_add_btn");
		element("usr_add_btn").click();

		/* verify success message after click on add user button */
		isElementDisplayed("usr_cfn_msg");
		Assert.assertTrue(element("usr_cfn_msg").getText()
				.contains("User \"" + student_account_id + "\" has been created successfully."));
		logMessage("Verified that the Student Account is Created By Admin");

		/* close the dialog box */

		try {
			isElementDisplayed("cls_dialog");
			element("cls_dialog").click();
		} catch (Exception exp1)

		{

			try {
				driver.navigate().to(new URL(getData("baseurl") + "ilrn/enrollment/enrollment.do#"));
			} catch (Exception sub) {

			}

		}

	}

	public void VerifyInstructorCreation() {

		/* check create student button is displayed */
		isElementDisplayed("crt_student_btn");
		element("crt_student_btn").click();

		/* switch to student dialog box */

		/* check select box user type is student is displayed */
		isElementDisplayed("usr_type");
		// element("usr_type").click();
		Select usr_type = new Select(element("usr_type"));
		usr_type.selectByValue("2");

		/* created user account & stored in userdata property file */
		isElementDisplayed("usr_account");
		String inst_account_id = "qai" + System.currentTimeMillis();
		writeProperties("inst_account_id", inst_account_id);
		element("usr_account").sendKeys(inst_account_id);

		/* verify first, middle, last name of student */
		isElementDisplayed("usr_f_name");
		element("usr_f_name").sendKeys(inst_account_id);
		hardWait(1);
		isElementDisplayed("usr_m_name");
		element("usr_m_name").sendKeys(getData("Instructor.MiddleName"));
		isElementDisplayed("usr_l_name");
		element("usr_l_name").sendKeys(getData("Instructor.LastName"));

		/* verify student password & confirm again */
		isElementDisplayed("usr_passwd");
		element("usr_passwd").sendKeys(getData("passwd"));
		isElementDisplayed("usr_cfn_passwd");
		element("usr_cfn_passwd").sendKeys(getData("passwd"));

		/* verify student verification question & answer */
		// isElementDisplayed("usr_vf_ques");
		// Select verf_question = new Select(element("usr_vf_ques"));
		// verf_question.selectByIndex(2);
		// isElementDisplayed("usr_vf_ans");
		// element("usr_vf_ans").sendKeys(getData("Instructor.school"));

		/* verify student email & confirm again */
		isElementDisplayed("usr_email");
		// element("usr_email").sendKeys(inst_account_id + "@qai.com");
		element("usr_email").sendKeys("myeltautomation@gmail.com");
		isElementDisplayed("usr_re_email");
		// element("usr_re_email").sendKeys(inst_account_id + "@qai.com");
		element("usr_re_email").sendKeys("myeltautomation@gmail.com");
		isElementDisplayed("usr_add_btn");
		element("usr_add_btn").click();

		/* verify success message after click on add user button */
		isElementDisplayed("usr_cfn_msg");
		Assert.assertTrue(element("usr_cfn_msg").getText()
				.contains("User \"" + inst_account_id + "\" has been created successfully."));
		logMessage("Verified that the Instructor Account is Created By Admin");

		/* close the dialog box */
		isElementDisplayed("cls_dialog");
		element("cls_dialog").click();
	}

	public void VerifyAdminTools() {
		isElementDisplayed("lnk_admn_tools");
		element("lnk_admn_tools").click();
        
		Assert.assertTrue(driver.findElement(By.xpath(".//*[@id='navbar-menu']/ul[2]/li[2]/ul/li[1]/a")).isDisplayed());
		logMessage("Verified 'Legacy Admin Tools' link appears on click of Admin tools drop down.");

		clickUsingJS(driver.findElement(By.xpath(".//*[@id='navbar-menu']/ul[2]/li[2]/ul/li[1]/a")));
		logMessage("Verified that the Admin is on AdminTool Page");
	}

	public void clickOnInstructorRequestsLink() {
		isElementDisplayed("lnk_admn_tools");
		element("lnk_admn_tools").click();

		Assert.assertTrue(driver.findElement(By.cssSelector("[class='dropdown open'] a[href*='newAdministration']"))
				.isDisplayed());
		logMessage("Verified 'Instructor Requests' link appears on click of Admin tools drop down.");

		clickUsingJS(driver.findElement(By.cssSelector("[class='dropdown open'] a[href*='newAdministration']")));
		logMessage("Verified that the Admin is on Instructor Requests Page");
	}

	public void selectViewAllFromStatusDropDown() {
		isElementDisplayed("drpDown_statusSelect");
		selectProvidedTextFromDropDown(element("drpDown_statusSelect"), "View All");
		logMessage("Verified that 'View All' option appears in 'Status' drop down on Instructor Requests Page");
	}

	public void clickOnSelectLink() {
		isElementDisplayed("lbl_status", getData("email_account"));
		logMessage("verified the Instructor Request status for account approval is: "
				+ element("lbl_status", getData("email_account")).getText().trim());

		isElementDisplayed("lnk_select", getData("email_account"));
		element("lnk_select", getData("email_account")).click();
	}

	public void sendAccountApprovalEmailToInstructor() {
		isElementDisplayed("dlg_lbl_role", getData("email_account"));
		isElementDisplayed("dlg_lbl_institution", getData("school_or_institution_name"));
		logMessage("Verified that email address: '" + getData("email_account") + "' & institution: '"
				+ getData("school_or_institution_name") + "' appears in 'Status' dialog box.");

		element("btn_approval_email").click();
		element("btn_template").click();
		element("lnk_accnt_apprval").click();
		hardWait(3);
		element("btn_sendEmail").click();

		Assert.assertEquals(element("lbl_submissionSuccess").getText().trim(), "Success: Email Sent Successfully!");
	}

	public void clickOnApproveButtonAndVerifyApproveMessage() {
		isElementDisplayed("btn_details_approve");
		element("btn_details_approve").click();
		Assert.assertEquals(element("lbl_submissionSuccess").getText().trim(), "Account Created!!");
		element("btn_close").click();
	}

	public void VerifyAddEditDeleteUserClicked() {
		hardWait(3);
		switchToFrame(element("frm_admn"));
		hardWait(2);
		isElementDisplayed("lnk_add_edit_del_usr");
		element("lnk_add_edit_del_usr").click();
		hardWait(1);
		// hardWait(13);
		logMessage("Verified that the Admin is on Add, Edit & Delete User Page");
	}

	public void VerifyUserSearch(String user) {
		switchToFrame(element("frm_usr_srch"));
		isElementDisplayed("btn_clr");
		isElementDisplayed("btn_aply_fltr");
		isElementDisplayed("txt_usr_name");

		// element("btn_clr").click();
		element("txt_usr_name").clear();
		element("txt_usr_name").sendKeys(user);
		element("btn_aply_fltr").click();
		logMessage("Verified that the Admin is able to Search User(Student/Instructor) on Search Page");
	}

	public void VerifyBookPermission(Map <String, String> books, String type) {
		// driver.switchTo().parentFrame();
		hardWait(1);
		driver.switchTo().defaultContent();
		WebElement iframe51 = driver.findElement(By.xpath(
				"//div[@class='wrap jPanelMenu-panel']//div[@id='content']//div[@class='container']//span[@id='marker_top']//span[@id='marker_top']//div//div//iframe[@class='external-content-frame']"));
		driver.switchTo().frame(iframe51);
		hardWait(2);
		WebElement iframe52 = driver.findElement(By.name("bottom_right_frame"));
		driver.switchTo().frame(iframe52);
		hardWait(2);
		isElementDisplayed("tbl_usr_data");
		isElementDisplayed("lnk_perm");
		driver.findElement(By.xpath("html/body/form[@id='main_form']/table[2]/tbody/tr[3]/td/small/a[2]")).click();

		// switchToFrame(element("frm_usr_vw"));
		// element("lnk_perm").click();

		hardWait(1);
		int rnd = randomInt(books.size());
		element("lnk_book_name", subStringBeforeBrackets(getValue(books, rnd)),
				subStringAfterBrackets(getValue(books, rnd))).click();
		element("rdo_book_id", getKey(books, rnd)).click();

		element("btn_aply").click();
		driver.switchTo().defaultContent();
		System.out.println(type + " Books detail---->" + books);
		if (type == "Instructor") {
			writeProperties("inst_book_id", getKey(books, rnd));
			writeProperties("inst_assign_book_name", getValue(books, rnd));
		} else {
			writeProperties("stud_book_id", getKey(books, rnd));
			writeProperties("inst_assign_book_name", subStringBeforeBrackets(readProperties("inst_assign_book_name")));
		}
		logMessage("Verified that the Admin is able to give permission to Student/Instructor");
	}

	public void switchToBookPermissionFrames() {
		// driver.switchTo().parentFrame();
		hardWait(1);
		driver.switchTo().defaultContent();
		WebElement iframe51 = driver.findElement(By.xpath(
				"//div[@class='wrap jPanelMenu-panel']//div[@id='content']//div[@class='container']//span[@id='marker_top']//span[@id='marker_top']//div//div//iframe[@class='external-content-frame']"));
		driver.switchTo().frame(iframe51);
		hardWait(2);
		WebElement iframe52 = driver.findElement(By.name("bottom_right_frame"));
		driver.switchTo().frame(iframe52);
		hardWait(2);
		isElementDisplayed("tbl_usr_data");
		isElementDisplayed("lnk_perm");
		driver.findElement(By.xpath("html/body/form[@id='main_form']/table[2]/tbody/tr[3]/td/small/a[2]")).click();

		// switchToFrame(element("frm_usr_vw"));

		// element("lnk_perm").click();
	}

	public void switchToDefaultFrame() {
		driver.switchTo().defaultContent();
	}

	public String VerifyAllDeployedBookPermission(Map.Entry<String, String> book, String type) {
		hardWait(1);
		clickUsingJS(driver.findElement(By
				.xpath("//table[@id='feature_table']/descendant::tr[@class='user_header_row']/td/descendant::small[1][contains(text(),'"
						+ subStringBeforeBrackets(book.getValue()) + "')]/following::span/small[contains(text(),'("
						+ subStringAfterBrackets(book.getValue()) + ")')]")));
		element("rdo_book_id", book.getKey()).click();

		String bookName = element("label_book_name", book.getKey()).getText().trim();
		bookName = bookName.substring("Access to".length(), bookName.length()).trim();

		element("btn_aply").click();
		System.out.println(type + " Books detail---->" + book);
		logMessage("Verified that the Admin is able to give permission to Student/Instructor");

		return bookName;
	}

	// public void VerifyBookPermission(String type) {
	// driver.switchTo().parentFrame();
	// switchToFrame(element("frm_usr_vw"));
	// isElementDisplayed("tbl_usr_data");
	// isElementDisplayed("lnk_perm");
	// element("lnk_perm").click();
	//
	// int size = elements("lnk_allbooks").size();
	// System.out.println("Size of Books Table::--> " + size);
	// Random rndmBook = new Random();
	// int val = rndmBook.nextInt(size);
	// System.out.println("Random Value::--> " + val);
	// String book_name = null;
	// if (val > 0) {
	// book_name = driver
	// .findElement(
	// By.xpath("(//table[@id='feature_table']/descendant::tr[@class='user_header_row'])["
	// + val + "]/td/descendant::small[1]"))
	// .getText();
	// System.out.println("Clicked On " + book_name + " book header.");
	//
	// driver.findElement(
	// By.xpath("(//table[@id='feature_table']/descendant::tr[@class='user_header_row'])["
	// + val + "]/td/descendant::small[1]")).click();
	// } else {
	// val += 1;
	// book_name = driver
	// .findElement(
	// By.xpath("(//table[@id='feature_table']/descendant::tr[@class='user_header_row'])["
	// + val + "]/td/descendant::small[1]"))
	// .getText();
	// System.out.println("Clicked On " + book_name + " book header.");
	//
	// driver.findElement(
	// By.xpath("(//table[@id='feature_table']/descendant::tr[@class='user_header_row'])["
	// + val + "]/td/descendant::small[1]")).click();
	// }
	// String attr = driver
	// .findElement(
	// By.xpath("(//table[@id='feature_table']/descendant::tr[@class='user_header_row'])["
	// + val + "]/td")).getAttribute("onclick")
	// .toString();
	// int attr_val = Integer.parseInt(attr.substring(attr.indexOf("(") + 1,
	// attr.indexOf(")")));
	// System.out.println("Book Attribute Value:: " + attr_val);
	// int sec_len = driver
	// .findElements(
	// By.xpath("(//table[@id='feature_table']/descendant::tr[starts-with(@id,'tr_"
	// + attr_val + "_')])")).size();
	// System.out.println("Sections within a book::--> " + sec_len);
	// int rndSect = rndmBook.nextInt(sec_len);
	// System.out.println("Random Section Clicked::--> " + rndSect);
	// System.out
	// .println(driver
	// .findElement(
	// By.xpath("//table[@id='feature_table']/descendant::tr[starts-with(@id,'tr_"
	// + attr_val
	// + "_"
	// + rndSect
	// + "')]/td[@class='decription']"))
	// .getText()
	// + " book permisson is given.");
	// String book_id = driver
	// .findElement(
	// By.xpath("//table[@id='feature_table']/descendant::tr[starts-with(@id,'tr_"
	// + attr_val
	// + "_"
	// + rndSect
	// + "')]/td[@class='radio'][2]/input"))
	// .getAttribute("name").split("view-")[1];
	// driver.findElement(
	// By.xpath("//table[@id='feature_table']/descendant::tr[starts-with(@id,'tr_"
	// + attr_val
	// + "_"
	// + rndSect
	// + "')]/td[@class='radio'][2]/input")).click();
	//
	// element("btn_aply").click();
	// driver.switchTo().defaultContent();
	// if (type == "Instructor") {
	// writeProperties("inst_book_id", book_id);
	// writeProperties("inst_assign_book_name", book_name);
	// } else {
	// writeProperties("stud_book_id", book_id);
	// }
	// logMessage("Verified that the Admin is able to give permission to
	// Student/Instructor");
	// }

	public void VerifyPermissonOFBookToStudent(String book_id) {
		try {
			Assert.assertTrue(driver.findElement(By.xpath(".//a[contains(text(),'" + book_id + "')]")).isDisplayed());
			logMessage("Verified user got permission for book '" + book_id + "'.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickUsingJS(WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}

	public void VerifyAddUserOptions(String user_type) {

		hardWait(5);
		try {

			driver.switchTo().defaultContent();
			WebElement iframe49 = driver.findElement(By.xpath(
					"//div[@class='wrap jPanelMenu-panel']//div[@id='content']//div[@class='container']//span[@id='marker_top']//span[@id='marker_top']//div//div//iframe[@class='external-content-frame']"));
			driver.switchTo().frame(iframe49);
			hardWait(3);
			WebElement iframe50 = driver.findElement(By.name("bottom_right_frame"));
			driver.switchTo().frame(iframe50);

			// switchToFrame(element("frm_usr_vw"));
			isElementDisplayed("lnk_add_stud");
			element("lnk_add_stud").click();

			hardWait(3);

			if (user_type == "Student") {
				isElementDisplayed("slct_usr_type");
				hardWait(1);
				Select usrType = new Select(element("slct_usr_type"));
				usrType.selectByValue("2");
			} else {
				isElementDisplayed("slct_usr_type");
				Select usrType = new Select(element("slct_usr_type"));
				usrType.selectByValue("2");
			}

			isElementDisplayed("txt_usr_name");
			String dmUsr = "qai" + System.currentTimeMillis();
			element("txt_usr_name").sendKeys(dmUsr);

			isElementDisplayed("txt_usr_pwd");
			element("txt_usr_pwd").sendKeys("password");

			isElementDisplayed("txt_usr_pwd_cfn");
			element("txt_usr_pwd_cfn").sendKeys("password");

			isElementDisplayed("txt_lst_name");
			element("txt_lst_name").sendKeys("demoTest");

			isElementDisplayed("txt_fst_name");
			element("txt_fst_name").sendKeys("demoTest");

			// isElementDisplayed("slct_ver_ques");
			// Select quesType = new Select(element("slct_ver_ques"));
			// quesType.selectByValue("ELEMENTARY_SCHOOL");

			// isElementDisplayed("txt_ver_ans");
			// element("txt_ver_ans").sendKeys("QAI");

			isElementDisplayed("txt_email");
			// element("txt_email").sendKeys(dmUsr + "@qai.com");
			element("txt_email").sendKeys("myeltautomation@gmail.com");
			isElementDisplayed("txt_cfn_email");
			// element("txt_cfn_email").sendKeys(dmUsr + "@qai.com");
			element("txt_cfn_email").sendKeys("myeltautomation@gmail.com");
			hardWait(1);
			isElementDisplayed("btn_add_usr");
			element("btn_add_usr").click();
			hardWait(2);
			Assert.assertTrue(element("p_add_usr_msg").getText().trim().equals("Added new user."));
			driver.switchTo().defaultContent();
			logMessage(
					"Verified that the Admin is able to Add Student/Instructor using Add new User link Under Admin Tools Tab");

		} catch (Exception ex) {
			driver.switchTo().defaultContent();
			WebElement iframe43 = driver.findElement(By.xpath(
					"//div[@class='wrap jPanelMenu-panel']//div[@id='content']//div[@class='container']//span[@id='marker_top']//span[@id='marker_top']//div//div//iframe[@class='external-content-frame']"));
			driver.switchTo().frame(iframe43);
			hardWait(5);
			WebElement iframe44 = driver.findElement(By.name("bottom_right_frame"));
			driver.switchTo().frame(iframe44);

			isElementDisplayed("lnk_add_stud");
			element("lnk_add_stud").click();

			if (user_type == "Student") {
				isElementDisplayed("slct_usr_type");
				Select usrType = new Select(element("slct_usr_type"));
				usrType.selectByValue("1");
			} else {
				isElementDisplayed("slct_usr_type");
				Select usrType = new Select(element("slct_usr_type"));
				usrType.selectByValue("2");
			}

			isElementDisplayed("txt_usr_name");
			String dmUsr = "qai" + System.currentTimeMillis();
			element("txt_usr_name").sendKeys(dmUsr);

			isElementDisplayed("txt_usr_pwd");
			element("txt_usr_pwd").sendKeys("password");

			isElementDisplayed("txt_usr_pwd_cfn");
			element("txt_usr_pwd_cfn").sendKeys("password");

			isElementDisplayed("txt_lst_name");
			element("txt_lst_name").sendKeys("demoTest");

			isElementDisplayed("txt_fst_name");
			element("txt_fst_name").sendKeys("demoTest");

			// isElementDisplayed("slct_ver_ques");
			// Select quesType = new Select(element("slct_ver_ques"));
			// quesType.selectByValue("ELEMENTARY_SCHOOL");
			//
			// isElementDisplayed("txt_ver_ans");
			// element("txt_ver_ans").sendKeys("QAI");

			isElementDisplayed("txt_email");
			// element("txt_email").sendKeys(dmUsr + "@qai.com");
			element("txt_email").sendKeys("myeltautomation@gmail.com");
			isElementDisplayed("txt_cfn_email");
			// element("txt_cfn_email").sendKeys(dmUsr + "@qai.com");
			element("txt_cfn_email").sendKeys("myeltautomation@gmail.com");
			hardWait(1);
			isElementDisplayed("btn_add_usr");
			element("btn_add_usr").click();
			hardWait(2);
			Assert.assertTrue(element("p_add_usr_msg").getText().trim().equals("Added new user."));
			driver.switchTo().defaultContent();
			logMessage(
					"Verified that the Admin is able to Add Student/Instructor using Add new User link Under Admin Tools Tab");
		}
	}
}
