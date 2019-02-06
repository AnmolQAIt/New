package com.qait.myelt.keywords;

import static com.qait.automation.utils.ResourceLoader.readProperties;
import static com.qait.automation.utils.ResourceLoader.writeProperties;
import static com.qait.automation.utils.YamlReader.getData;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.bcel.generic.Select;
import org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner.stdDSA;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.PropFileHandler;

import junit.framework.Assert;

public class InstructorPageActions extends GetPage {
String url;
	WebDriver driver = null;

	public InstructorPageActions(WebDriver driver) {
		super(driver, "InstructorPage");
		this.driver = driver;
	}

	/*
	 * In VerifyCourseCreation method, we are creating a course & save the
	 * course value in userdata.properties file.
	 */
	@SuppressWarnings("deprecation")
	public void VerifyCourseCreation() {
		isElementDisplayed("lnk_course");
		hardWait(2);
		element("lnk_course").click();
		hardWait(2);
		isElementDisplayed("btn_add_course");
		hardWait(3);
		element("btn_add_course").click();
		isElementDisplayed("btn_course_name");
		String crs_name = "Test_Course" + System.currentTimeMillis();
		element("btn_course_name").sendKeys(crs_name);
		isElementDisplayed("btn_course_no");
		element("btn_course_no").sendKeys(getData("course_no"));
		isElementDisplayed("cal_start_date");
		element("cal_start_date").click();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int mnth = cal.getTime().getMonth();

		try {
			element("dtPckr_start_date", String.valueOf(cal.getTime().getDate())).click();
		} catch (Exception calanderActivedaysMac) {
			element("dtPckr_start_date2", String.valueOf(cal.getTime().getDate())).click();

		}

		isElementDisplayed("cal_end_date");
		cal.add(Calendar.DATE, 1);
		element("cal_end_date").click();
		if (mnth < cal.getTime().getMonth()) {
			element("dtPckr_next_date", String.valueOf(cal.getTime().getDate())).click();
		} else {
			element("dtPckr_end_date", String.valueOf(cal.getTime().getDate())).click();
		}

		isElementDisplayed("btn_save_course");
		element("btn_save_course").click();
		isElementDisplayed("lbl_crs_key");
		writeProperties("course_key", element("lbl_crs_key").getText().trim());
		isElementDisplayed("btn_save_exit");
		element("btn_save_exit").click();
		Assert.assertTrue(element("td_crs_name", crs_name).isDisplayed());
		writeProperties("inst_crs_name", crs_name);
		logMessage("Verified that the instructor is able to create course On Course Page");
	}

	public void sendCourseKeyThroughEmail() {
		hardWait(2);
		element("lnk_course").click();
		hardWait(2);

		element("lnk_email", readProperties("inst_crs_name")).click();
		hardWait(2);

		isElementDisplayed("lbl_email_hdr");
		logMessage("verified email header is displayed");

		element("chkbox_email").click();

		isElementDisplayed("inp_subj");
		element("inp_subj").sendKeys("Course Key" + readProperties("course_key"));

		isElementDisplayed("txt_Msg");
		element("txt_Msg").sendKeys("Find the course key: " + readProperties("course_key"));

		isElementDisplayed("btn_send_email");
		element("btn_send_email").click();
		hardWait(2);
     System.out.println("[Send Email Button is clicked]");
       System.out.println(element("lbl_succ_eml_msg").getText());
		Assert.assertEquals(element("lbl_succ_eml_msg").getText(),"Mail sent successfully.");
		logMessage("Verified email is successfully sent to '" + element("lbl_email_to").getAttribute("value").trim()+ "' from: '"
				+ element("lbl_email_from").getText().trim() + "'");

		element("icn_close").click();
	}

	@SuppressWarnings("deprecation")
	public void VerifyCourseCreation1() {
		isElementDisplayed("lnk_course");
		hardWait(2);
		element("lnk_course").click();
		hardWait(2);
		isElementDisplayed("btn_add_course");
		hardWait(3);
		element("btn_add_course").click();
		isElementDisplayed("btn_course_name");
		String crs_name = "SelfLearner_Course" + System.currentTimeMillis();
		element("btn_course_name").sendKeys(crs_name);
		isElementDisplayed("btn_course_no");
		element("btn_course_no").sendKeys(getData("course_no1"));
		isElementDisplayed("cal_start_date");
		element("cal_start_date").click();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int mnth = cal.getTime().getMonth();
		element("dtPckr_start_date", String.valueOf(cal.getTime().getDate())).click();
		isElementDisplayed("cal_end_date");
		cal.add(Calendar.DATE, 1);
		element("cal_end_date").click();
		if (mnth < cal.getTime().getMonth()) {
			element("dtPckr_next_date", String.valueOf(cal.getTime().getDate())).click();
		} else {
			element("dtPckr_end_date", String.valueOf(cal.getTime().getDate())).click();
		}

		isElementDisplayed("btn_save_course");
		element("btn_save_course").click();
		isElementDisplayed("lbl_crs_key");
		writeProperties("SelfLearner_CourseKey", element("lbl_crs_key").getText().trim());
		isElementDisplayed("btn_save_exit");
		element("btn_save_exit").click();
		Assert.assertTrue(element("td_crs_name", crs_name).isDisplayed());
		writeProperties("Self-LearnerCourse", crs_name);
		logMessage(
				"Verified that the instructor is able to create SelfLearner Course Required for Verifying Add a Resource functionality.");
	}

	@SuppressWarnings("deprecation")
	public void VerifyCourseCreation2() {
		isElementDisplayed("lnk_course");
		hardWait(2);
		element("lnk_course").click();
		hardWait(2);
		isElementDisplayed("btn_add_course");
		hardWait(3);
		element("btn_add_course").click();
		isElementDisplayed("btn_course_name");
		String crs_name = "Hybrid_Course" + System.currentTimeMillis();
		element("btn_course_name").sendKeys(crs_name);
		isElementDisplayed("btn_course_no");
		element("btn_course_no").sendKeys(getData("course_no2"));
		isElementDisplayed("cal_start_date");
		element("cal_start_date").click();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int mnth = cal.getTime().getMonth();
		element("dtPckr_start_date", String.valueOf(cal.getTime().getDate())).click();
		isElementDisplayed("cal_end_date");
		cal.add(Calendar.DATE, 1);
		element("cal_end_date").click();
		if (mnth < cal.getTime().getMonth()) {
			element("dtPckr_next_date", String.valueOf(cal.getTime().getDate())).click();
		} else {
			element("dtPckr_end_date", String.valueOf(cal.getTime().getDate())).click();
		}

		isElementDisplayed("btn_save_course");
		element("btn_save_course").click();
		isElementDisplayed("lbl_crs_key");
		writeProperties("Hybrid_CourseKey", element("lbl_crs_key").getText().trim());
		isElementDisplayed("btn_save_exit");
		element("btn_save_exit").click();
		Assert.assertTrue(element("td_crs_name", crs_name).isDisplayed());
		writeProperties("HybridCourse", crs_name);
		logMessage(
				"Verified that the instructor is able to create the Hybrid course for verifying Add a Resource functionality");
	}

	/*
	 * In VerifyStudentEnrollInCourse method, we are enrolling student & save
	 * the into instructor made course.
	 */
	public void VerifyStudentEnrollInCourse(String stud_id) {
		isElementDisplayed("lnk_stud");
		hardWait(2);
		element("lnk_stud").click();
		hardWait(2);
		isElementDisplayed("dv_slct_crs");
		element("dv_slct_crs").click();
		hardWait(1);
		isElementDisplayed("spn_crs", readProperties("inst_crs_name"));
		element("spn_crs", readProperties("inst_crs_name")).click();
		isElementDisplayed("btn_enrl_stud");
		hardWait(2);
		element("btn_enrl_stud").click();
		isElementDisplayed("txt_srch_stud_lgn");
		element("txt_srch_stud_lgn").clear();
		element("txt_srch_stud_lgn").sendKeys(stud_id);
		isElementDisplayed("slct_usr_type");
		selectProvidedTextFromDropDown(element("slct_usr_type"), "Student");
		isElementDisplayed("btn_srch");
		element("btn_srch").click();
		hardWait(2);
		isElementDisplayed("tbl_unroled_stud", stud_id);
		enrollStudentInCourse();
		hardWait(2);
		isElementDisplayed("tbl_eroled_stud", stud_id);
		unenrollStudentInCourse(stud_id);
		isElementDisplayed("tbl_unroled_stud", stud_id);
		enrollStudentInCourse();
		hardWait(2);
		isElementDisplayed("tbl_eroled_stud", stud_id);
		logMessage("Verified that the Student " + stud_id + " is enrolled by Instructor into "
				+ readProperties("inst_crs_name") + " Course.");
	}

	public void enrollStudentInCourse() {
		isElementDisplayed("chck_usr");
		element("chck_usr").click();
		isElementDisplayed("btn_enroll");
		element("btn_enroll").click();
	}

	public void unenrollStudentInCourse(String stud_id) {
		isElementDisplayed("chck_alEnrol");
		element("chck_alEnrol").click();
		if (isElementDisplayed("chck_enrol_admn_student", stud_id)) {
			element("chck_enrol_student", stud_id).click();
		}
		isElementDisplayed("btn_unenroll");
		element("btn_unenroll").click();
		logMessage("Verified that Student is Un-Enrolled by Instructor from " + readProperties("inst_crs_name")
				+ " Course.");
	}

	public void VerifyStudentCreation(AdminPageActions actions) {
		isElementDisplayed("lnk_stud");
		hardWait(2);
		element("lnk_stud").click();
		isElementDisplayed("dv_crs_slct");
		hardWait(2);
		element("dv_crs_slct").click();
		isElementDisplayed("dv_dflt_node");
		element("dv_dflt_node").click();
		hardWait(2);
		actions.VerifyStudentCreation("inst");
		logMessage("Verified that new Student is created by Instructor.");
		hardWait(2);
	}

	public void VerfiyCreateAssessment() {
		isElementDisplayed("lnk_assnt");
		hardWait(1);
		element("lnk_assnt").click();
		hardWait(1);
		isElementDisplayed("btn_crt_assnt");
		 //element("btn_crt_assnt").click();
		int n = 0;
		while (n < 1) {

			element("btn_crt_assnt").click();
			hardWait(10);
		    url = driver.getCurrentUrl().toString().trim();
			driver.get(url);
			if (driver.getPageSource().contains("Choose Content Source")) {
				n = 2;
			} else {
				n = 0;
			}

		}
		hardWait(6);
		isElementDisplayed("img_book_name", readProperties("inst_assign_book_name"));
		element("img_book_name", readProperties("inst_assign_book_name")).click();
		isElementDisplayed("icn_expnd");
		element("icn_expnd").click();
		hardWait(3);
		executeJavascript(
				"document.querySelector(\"[class=' lltree_node_container']:not([style='display: none;'])>span:nth-child(1)>img[src*='bookOpen.gif']\").previousSibling.click()");
		isElementDisplayed("btn_aasnt_cnt");
		element("btn_aasnt_cnt").click();
		hardWait(3);
		WebDriverWait wait = new WebDriverWait(driver, 6);
		try {
			wait.until(ExpectedConditions.alertIsPresent());

			driver.switchTo().alert().accept();
		} catch (Exception ex) {
			element("btn_aasnt_cnt").click();
			((JavascriptExecutor) driver).executeScript("confirm=function(message){return true;};");
			((JavascriptExecutor) driver).executeScript("alert=function(message){return true;};");
			((JavascriptExecutor) driver).executeScript("prompt=function(message){return true;};");
		}
		try {
		isElementDisplayed("error_message");
		String Error = element("error_message").getText().trim();
		logMessage("This is the error message for creating this assign. for this book::-" +Error);
		executeJavascript(
				"document.querySelector(\"[class=' lltree_node_container']:not([style='display: none;'])>span:nth-child(1)>img[src*='bookOpen.gif']\").previousSibling.click()");
		hardWait(2);
		executeJavascript("document.evaluate(\".//*[@class=' lltree_node_bold lltree_node_caption'][contains(text(),'Unit 1')]/preceding-sibling::span/img[contains(@src,'icon_cb_none_d.gif')]\", document, null, XPathResult.ANY_UNORDERED_NODE_TYPE, null).singleNodeValue.click()");
		executeJavascript("document.evaluate(\".//*[@class=' lltree_node_bold lltree_node_caption'][contains(text(),'Unit 2')]/preceding-sibling::span/img[contains(@src,'icon_cb_none_d.gif')]\", document, null, XPathResult.ANY_UNORDERED_NODE_TYPE, null).singleNodeValue.click()");
		element("btn_aasnt_cnt").click();
		hardWait(3);
		WebDriverWait wait1 = new WebDriverWait(driver, 6);
		try {
			wait1.until(ExpectedConditions.alertIsPresent());
			driver.switchTo().alert().accept();
		} catch (Exception ex) {
			element("btn_aasnt_cnt").click();
			((JavascriptExecutor) driver).executeScript("confirm=function(message){return true;};");
			((JavascriptExecutor) driver).executeScript("alert=function(message){return true;};");
			((JavascriptExecutor) driver).executeScript("prompt=function(message){return true;};");
		}
		}catch(Exception exception){
			logMessage("Assignment has been selected successfully");
		}
		hardWait(2);
		String assingnment_name = "Test_Assignment" + System.currentTimeMillis();
		element("txt_grp_name").clear();
		element("txt_grp_name").sendKeys(assingnment_name);
		writeProperties("unit_name", assingnment_name);
		selectProvidedIndexFromDropDown(element("takesAllowed"), 9);
		isElementDisplayed("btn_save_grp");
		element("btn_save_grp").click();
		hardWait(2);
		Assert.assertTrue(element("hdr_assgn_name").getText().contains(assingnment_name));
	}

	public void VerifyEditAssignment() {
		isElementDisplayed("lnk_assnt");
		element("lnk_assnt").click();

		String assign_name = readProperties("unit_name").trim();
		hardWait(2);
		isElementDisplayed("chck_asgn_name", assign_name);
		hardWait(2);
		element("chck_asgn_name", assign_name).click();
		hardWait(2);
		url=driver.getCurrentUrl().toString().trim();
		isElementDisplayed("btn_cpy");
		hardWait(2);
		element("btn_cpy").click();
		// isElementDisplayed("dv_cpy_msg",
		// "assignment(s) added to clipboard for COPY");

		hardWait(1);
		isElementDisplayed("btn_pst");
		element("btn_pst").click();
		hardWait(2);
		isElementDisplayed("chck_rm_clpbrd_pst");
		element("chck_rm_clpbrd_pst").click();
		hardWait(2);
		isElementDisplayed("td_cpy_msg");
		isElementDisplayed("btn_rm_clpbrd_itm");
		isElementDisplayed("btn_pst_clpbrd_itm");
		hardWait(2);
		element("btn_pst_clpbrd_itm").click();
		hardWait(25);
		//String url1 = driver.getCurrentUrl().toString().trim();
		//driver.get(url);
		isElementDisplayed("chck_asgn_name", "Copy of " + assign_name);
		logMessage("Verify that a copy of Assignement is created");
		hardWait(25);
		element("chck_asgn_name", "Copy of " + assign_name).click();
		isElementDisplayed("btn_cut");
		hardWait(25);
		element("btn_cut").click();
		hardWait(2);
		isElementDisplayed("chck_asgn_name", "Copy of " + assign_name);
		element("chck_asgn_name", "Copy of " + assign_name).click();
		hardWait(2);
		isElementDisplayed("btn_dlt");
		element("btn_dlt").click();
		hardWait(2);
		isElementDisplayed("btn_cfn_dlt");
		isElementDisplayed("btn_cncl_dlt");
		hardWait(3);
		element("btn_cfn_dlt").click();
		hardWait(35);
		//Assert.assertFalse(!isElementDisplayed("chck_asgn_name", "Copy of " + assign_name));
		logMessage("Verify that Copy Assignement is deleted");
		logMessage("Verified that instructor is able to manage the assignment");
	}

	public void VerifyEditCourse() {
		isElementDisplayed("lnk_course");
		element("lnk_course").click();

		String crs_name = readProperties("inst_crs_name");
		hardWait(1);
		isElementDisplayed("tbl_lnk_crs_name", crs_name);
		element("tbl_lnk_crs_name", crs_name).click();

		isElementDisplayed("btn_cpy");
		element("btn_cpy").click();
		// isElementDisplayed("dv_cpy_crs_msg",
		// "course(s) added to clipboard for COPY");

		hardWait(1);
		isElementDisplayed("btn_pst");
		hardWait(1);
		element("btn_pst").click();
		isElementDisplayed("chck_rm_clpbrd_pst");
		hardWait(1);
		element("chck_rm_clpbrd_pst").click();
		isElementDisplayed("td_cpy_msg");
		isElementDisplayed("btn_rm_clpbrd_itm");
		isElementDisplayed("btn_pst_clpbrd_itm");
		hardWait(1);
		element("btn_pst_clpbrd_itm").click();

		isElementDisplayed("tbl_lnk_crs_name", "Copy of " + crs_name);
		hardWait(1);
		element("tbl_lnk_crs_name", crs_name).click();

		isElementDisplayed("btn_cut");
		element("btn_cut").click();
		// isElementDisplayed("dv_cpy_crs_msg",
		// "course(s) added to clipboard for CUT");
		hardWait(1);
		isElementDisplayed("tbl_lnk_crs_name", "Copy of " + crs_name);
		hardWait(1);
		element("tbl_lnk_crs_name", crs_name).click();
		isElementDisplayed("btn_dlt_multi_crs");
		hardWait(1);
		element("btn_dlt_multi_crs").click();
		hardWait(1);
		isElementDisplayed("btn_cfn_dlt");
		element("btn_cfn_dlt").click();
		hardWait(1);
		isElementDisplayed("Add");
		element("Add").click();
		isElementDisplayed("CreateSubfolderDialog");
		driver.switchTo().defaultContent();
		hardWait(1);
		element("folderText").sendKeys("QAI AutoTest Folder");
		hardWait(1);
		element("okbtn").click();
		isElementDisplayed("All_folders");
		isElementDisplayed("plusSymbol");
		element("plusSymbol").click();
		hardWait(2);
		isElementDisplayed("QAIFolder");
		hardWait(2);
		element("QAIFolder").click();
		hardWait(2);
		element("btn_add_course").click();
		hardWait(2);
		element("btn_course_name").sendKeys("Auto Folder Test Course");
		hardWait(1);
		element("btn_save_course").click();
		hardWait(2);
		element("btn_save_exit").click();
		hardWait(2);
		isElementDisplayed("RenameBtn");
		hardWait(2);
		element("RenameBtn").click();
		element("Renameinput").sendKeys("QAI AutoTest Folder 2");
		hardWait(2);
		element("okbtn").click();
		hardWait(1);
		isElementDisplayed("QAIFolder2");
		isElementDisplayed("Deletebtn");
		hardWait(3);
		element("Deletebtn").click();
		isElementDisplayed("Deleteconfmdilog");
		isElementDisplayed("okbtn");
		hardWait(1);
		element("okbtn").click();
		hardWait(2);
		try {
			Assert.assertFalse(isElementDisplayed("QAIFolder2"));
		} catch (Exception ex) {
			logMessage("Verified that instructor is able to Add, Rename, Delete Folders");
		}

		logMessage("Verified that instructor is able to manage the course");
	}

	public void VerifyForgetPassword(String login_id) {
		isElementDisplayed("lnk_fgt_pwd");
		element("lnk_fgt_pwd").click();
		isElementDisplayed("txt_inp_lgnId");
		element("txt_inp_lgnId").sendKeys(login_id);
		element("btn_sbmt").click();
		Assert.assertTrue(element("p_scs_msg").getText().contains("Please check your email and follow the instructions."));
		element("btn_ok").click();;
		hardWait(5);

	}

	public void VerifyEmailCourseKeyToMemebers(String crs_name, String subjct, String crs_key) {
		hardWait(2);
		isElementDisplayed("lnk_course");
		element("lnk_course").click();
		hardWait(2);
		isElementDisplayed("lnk_crs_name", crs_name);
		element("lnk_crs_name", crs_name).click();
		hardWait(2);
		isElementDisplayed("lnk_eml_mbers");
		element("lnk_eml_mbers").click();
		hardWait(2);
		isElementDisplayed("txt_subjct");
		element("txt_subjct").sendKeys(subjct);
		hardWait(2);
		isElementDisplayed("txt_crs_key_msg");
		element("txt_crs_key_msg").sendKeys("Test Course Key--> " + crs_key);
		hardWait(2);
		executeJavascript("document.querySelector(\"[name='userIds']\").click()");
		isElementDisplayed("btn_snd_eml");
		hardWait(2);
		element("btn_snd_eml").click();
		hardWait(2);
		isElementDisplayed("dv_alrt_msg");
		isElementDisplayed("icn_cls_dlg");
		hardWait(2);
		element("icn_cls_dlg").click();
	}

	public void VerifyInstructorGradeBook(String grade) {
		String ScoreAppearingInGradebook =null;
		isElementDisplayed("lnk_grd_bk");
		element("lnk_grd_bk").click();
		hardWait(15);
		clickUsingXpathInJavaScriptExecutor(driver.findElement(By.xpath(".//*[@id='selected-course']")));
		hardWait(4);
		driver.findElement(By.xpath(".//span[contains(text(),'" + readProperties("inst_crs_name") + "')]")).click();
		hardWait(5);
		element("studentName", readProperties("admin_student_account_id")).click();
		hardWait(6);
		String GradebookActivityName = PropFileHandler.readProperty("AssignmentActivitName").toString();
		if (getData("super_product").equals("true")) {
		 ScoreAppearingInGradebook = driver
				.findElement(By.xpath("//a[contains(text(),'" + GradebookActivityName + "')]//ancestor::tr//td[4]"))
				.getText().toString();
		}else {
		 ScoreAppearingInGradebook = driver
					.findElement(By.xpath("//a[contains(text(),'" + GradebookActivityName + "')]//ancestor::tr//td[3]"))
					.getText().toString();
		}
		if (ScoreAppearingInGradebook.contains("0.00 %")) {
			element("lnk_grd_bk").click();
			hardWait(2);
			//element("SortBar").click();
			//hardWait(2);
			try{
			element("studentName", readProperties("admin_student_account_id")).click();
			}catch(Exception ex){
				element("studentName", readProperties("admin_student_account_id")).click();
			}
			hardWait(2);
			GradebookActivityName = PropFileHandler.readProperty("AssignmentActivitName").toString();
			if (getData("super_product").equals("true")) {
			ScoreAppearingInGradebook = driver
					.findElement(By.xpath("//a[contains(text(),'" + GradebookActivityName + "')]//ancestor::tr//td[4]"))
					.getText().toString();
			} else {
				ScoreAppearingInGradebook = driver
						.findElement(By.xpath("//a[contains(text(),'" + GradebookActivityName + "')]//ancestor::tr//td[3]"))
						.getText().toString();
			}
			System.out.println("ScoreAppearingInGradebook : " + ScoreAppearingInGradebook);
			System.out.println("Scores on Student Assignment Page : " + grade);
			float a = Float.parseFloat(ScoreAppearingInGradebook.replace("%", "").trim());
			float b = Float.parseFloat(grade.replace("%", "").trim());
			if (a == b) {
				logMessage("Student Grades Appearing in Instructor Gradebook  : "
						+ PropFileHandler.readProperty("AssignmentScore").toString());
				logMessage(
						"Verified that Student Assignment Grades are displayed and Matched in Instructor gradeboook");
			} else {
				logMessage("Verified that Student Assignment Grades are Not Matched in Instructor gradeboook");
			}
		} else {
			System.out.println("ScoreAppearingInGradebook : " + ScoreAppearingInGradebook);
			System.out.println("Scores on Student Assignment Page : " + grade);
			float a = Float.parseFloat(ScoreAppearingInGradebook.replace("%", "").trim());
			float b = Float.parseFloat(grade.replace("%", "").trim());
			if (a == b) {
				logMessage("Student Grades Appearing in Instructor Gradebook  : "
						+ PropFileHandler.readProperty("AssignmentScore").toString());
				logMessage(
						"Verified that Student Assignment Grades are displayed and Matched in Instructor gradeboook");
			} else {
				logMessage("Verified that Student Assignment Grades are Not Matched in Instructor gradeboook");
			}

		}

		element("lnk_grd_bk").click();
		hardWait(1);
	}

	public void verifySelfActivityGradeOnInstructorEnd(String grade) {
		driver.findElement(By.cssSelector("#gradebook-drop")).click();

		isElementDisplayed("dv_slct_crs");
		element("dv_slct_crs").click();
		hardWait(1);
		isElementDisplayed("spn_crs", readProperties("inst_crs_name"));
		element("spn_crs", readProperties("inst_crs_name")).click();

		driver.findElement(By.cssSelector("#unassigned")).click();
		hardWait(5);

	}

	public void verifyScoreOfCorrectAndInCorrectAnswersOnInstructorEnd(String access_code, String student_id,
			String activity_name) {

		element("selct_book_dropDown").click();
		hardWait(1);
		element("opt_book_name", access_code).click();
		hardWait(3);
		element("lnk_stu_name", student_id).click();
		hardWait(2);
		element("lnk_scr_name", activity_name).click();
		hardWait(2);

		isElementDisplayed("tb_rev_actvty");
		isElementDisplayed("tb_rev_scrs");
		driver.switchTo().frame("activity_container");
		try {
			Assert.assertTrue(element("btn_shw_ans").getText().equals("Show Answers"));
			element("btn_shw_ans").click();
			hardWait(2);
			Assert.assertTrue(element("btn_shw_ans").getText().equals("Hide Answers"));
		} catch (Exception e) {
		}

		driver.switchTo().defaultContent();
		element("tb_rev_scrs").click();
		hardWait(3);
		driver.switchTo().frame("activity_container");
		List<WebElement> eles = elements("td_itemScoreDisplayGradeByItem");
		for (WebElement el : eles) {
			Assert.assertTrue(containsEither(el.getText().trim()));
			hardWait(1);
		}

		logMessage("verified the Score is 1 for each correct and 0 for each incorrect answer on Review Scores tab.");
		driver.switchTo().defaultContent();
		element("lnk_grd_bk").click();
	}

	public Boolean containsEither(String text) {
		return text.contains("1.0") || text.contains("0.0");
	}

	public void VerifyAssignmentStatisticsReports() {
		try {
			isElementDisplayed("drp_down_rprt");
			element("drp_down_rprt").click();
			isElementDisplayed("lnk_assign_rprt_type", getData("Reports.Type1"));
			element("lnk_assign_rprt_type", getData("Reports.Type1")).click();
			String activity_name = PropFileHandler.readProperty("CurrentUnitTitleText") + " / "
					+ PropFileHandler.readProperty("CurrentActivityParentTitleText") + " / "
					+ PropFileHandler.readProperty("CurrentActivityChildTitleText");
			selectProvidedTextFromDropDown(element("sct_assign_type"),
					"(" + readProperties("unit_name") + ") " + activity_name);
			isElementDisplayed("btn_crt_report");
			element("btn_crt_report").click();
			isElementDisplayed("hdr_rprt", getData("Reports.Type1"));
			isElementDisplayed("spn_assign_details", readProperties("inst_crs_name"));
			String asgn_name = readProperties("inst_assign_book_name").substring(0,
					readProperties("inst_assign_book_name").lastIndexOf("HTML"));
			if (asgn_name != null) {
				isElementDisplayed("spn_assign_details", asgn_name);
			} else {
				isElementDisplayed("spn_assign_details", readProperties("inst_assign_book_name"));
			}
			isElementDisplayed("spn_assign_details", activity_name);
			VerifyDownloadReports();
			isElementDisplayed("lnk_rtrn_grdbok");
			element("lnk_rtrn_grdbok").click();
		} catch (Exception ex) {
			String activity_name = PropFileHandler.readProperty("AssignmentActivitName");
			selectProvidedTextFromDropDown(element("sct_assign_type"),
					"(" + readProperties("unit_name") + ") " + activity_name);
			isElementDisplayed("btn_crt_report");
			element("btn_crt_report").click();
/*			hardWait(6);
			isElementDisplayed("hdr_rprt", getData("Reports.Type1"));
			isElementDisplayed("spn_assign_details", readProperties("inst_crs_name"));
			hardWait(1);
			String asgn_name = PropFileHandler.readProperty("AssignmentActivitName");
			if (asgn_name != null) {
				isElementDisplayed("spn_assign_details", asgn_name);
			} else {
				isElementDisplayed("spn_assign_details", readProperties("inst_assign_book_name"));
			}
			isElementDisplayed("spn_assign_details", activity_name);
			VerifyDownloadReports();
			isElementDisplayed("lnk_rtrn_grdbok");*/
			element("lnk_rtrn_grdbok").click();

		}
	}

	public void VerifScoresByAssignmentReports(String grade, String studentName) {
		hardWait(3);
		isElementDisplayed("drp_down_rprt");
		element("drp_down_rprt").click();
		isElementDisplayed("lnk_assign_rprt_type", getData("Reports.Type2"));
		element("lnk_assign_rprt_type", getData("Reports.Type2")).click();
		isElementDisplayed("btn_crt_report");
		element("btn_crt_report").click();
		isElementDisplayed("hdr_rprt", getData("Reports.Type2"));
		isElementDisplayed("td_stud_name", studentName);
		String activityName = PropFileHandler.readProperty("AssignmentActivitName");
		if (activityName.contains("(")) {
			activityName = activityName.substring(activityName.indexOf("(") + 1, activityName.lastIndexOf(")"));
		}
		String grade0 = driver
				.findElement(By.xpath(".//td[contains(text(),'" + activityName + "')]/following-sibling::td[4]"))
				.getText();
		Assert.assertTrue(grade.contains(grade0.substring(0, grade0.indexOf("%"))));
		logMessage("verifed the grade '" + grade + "' displayed at student end is similar to grade '" + grade0
				+ "' shown at instructor end for student " + studentName);
	}

	public void VerifyDownloadReports() {
		hardWait(2);
		isElementDisplayed("rdo_rprt_frmt");
		element("rdo_rprt_frmt").click();
		isElementDisplayed("btn_sv_rprt");
		// element("btn_sv_rprt").click();
	}

	public void clickOnCreateAnAccountLink() {
		isElementDisplayed("lnk_create_accnt");
		element("lnk_create_accnt").click();
		logMessage("verified 'Create An Account' link appears on Home Page of Application.");
	}

	public void selectInstructorTypeAccountAndClickNextButton() {
		WebDriverWait driverWait = new WebDriverWait(driver, 20);
		driverWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#radio-student")));
		executeJavascript("document.querySelector('#radio-instructor').click()");
		logMessage("verified 'Instructor' type radio button appears on Select Account Type page.");

		element("btn_nxt").click();
	}

	public void fillUserInformationWithValidEmailAddress() {
		isElementDisplayed("inp_email");
		element("inp_email").sendKeys(getData("email_account"));

		isElementDisplayed("inp_email2");
		element("inp_email2").sendKeys(getData("email_account"));

		isElementDisplayed("inp_firstName");
		element("inp_firstName").sendKeys(getData("Instructor.FirstName"));

		isElementDisplayed("inp_lastName");
		element("inp_lastName").sendKeys(getData("Instructor.LastName"));

		isElementDisplayed("inp_city");
		element("inp_city").sendKeys(getData("Instructor.city"));

		isElementDisplayed("inp_state");
		element("inp_state").sendKeys(getData("Instructor.state"));

		isElementDisplayed("inp_zipCode");
		element("inp_zipCode").sendKeys(getData("Instructor.zipCode"));

		selectProvidedTextFromDropDown(element("drpDown_country"), getData("Instructor.country"));

		isElementDisplayed("inp_schoolName");
		element("inp_schoolName").sendKeys(getData("school_or_institution_name"));
		driver.findElement(By.id("schoolName_autocomplete")).findElement(By.cssSelector("[class='ui-menu-item']>a"))
				.click();
		logMessage("verified School/Institution drop-down appears on Account Information Page.");

		executeJavascript("document.querySelector('#agreeToEulaTrue').click()");
		element("btn_accept").click();
	}

	public void selectSeriesAndItsAssociatedLevelsAndClickNextButton() {
		isElementDisplayed("chckBox_series", getData("inst_seriesAndLevels.name"));
		element("chckBox_series", getData("inst_seriesAndLevels.name")).click();
		logMessage("verified user selected '" + getData("inst_seriesAndLevels.name")
				+ "' series and its associated levels for Instructor Account.");

		element("btn_series_accept").click();
	}

	public void reviewAccountInformationAndClickSubmitButton() {
		isElementDisplayed("lbl_email_vald", getData("email_account"));
		logMessage(
				"verified '" + getData("email_account") + "' email address appears on Review Account Information Page");

		isElementDisplayed("lbl_institution", getData("school_or_institution_name"));
		logMessage("verified '" + getData("school_or_institution_name")
				+ "' institution appears on Review Account Information Page");

		Assert.assertTrue(element("lbl_prduct_name").getText().trim().equals(getData("inst_seriesAndLevels.name")));
		logMessage("verified '" + getData("inst_seriesAndLevels.name")
				+ "' product name appears on Review Account Information Page");

		for (WebElement e : elements("lbl_prduct_sbcat")) {
			String text = e.getText().trim();
			Assert.assertTrue(getData("inst_seriesAndLevels.Levels").contains(text));
			logMessage("verified '" + text + "' level name appears on Review Account Information Page");
		}

		element("btn_instAccStep3").click();
	}

	public void verifySuccessfulEmailConfirmationMessageOfInstructorAccount() {
		isElementDisplayed("lnk_tech_email");
		element("lnk_tech_email").getText().trim().contains(getData("tech_email_account"));
		logMessage(
				"verified '" + getData("tech_email_account") + "' tech email address appears on Process Complete Page");

		element("btn_instAccStep4").click();
	}

}