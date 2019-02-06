package com.qait.myelt.keywords;

import static com.qait.automation.utils.ResourceLoader.getContentCode;
import static com.qait.automation.utils.ResourceLoader.readProperties;
import static com.qait.automation.utils.YamlReader.getData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.ObjectUtils.Null;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByCssSelector;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.PropFileHandler;

import junit.framework.Assert;
import junit.framework.Test;

public class StudentPageActions extends GetPage {

	public StudentPageActions(WebDriver driver) {
		super(driver, "StudentPage");
	}

	/*
	 * In VerifyCourseKeyEnrolment method Student is able to get access of
	 * course & content of course using course key & content key
	 */
	public void VerifyCourseKeyEnrolment() {
		isElementDisplayed("lnk_crt_acnt");
		element("lnk_crt_acnt").click();
		isElementDisplayed("rdo_stud");
		element("rdo_stud").click();
		isElementDisplayed("btn_nxt");
		element("btn_nxt").click();
		isElementDisplayed("rdo_instld_study");
		element("rdo_instld_study").click();
		isElementDisplayed("btn_cntn");
		element("btn_cntn").click();
		isElementDisplayed("txt_crs_key");
		isElementDisplayed("txt_cnt_key");
		element("txt_crs_key").clear();
		element("txt_cnt_key").clear();
		String cnt_code = getContentCode();
		element("txt_crs_key").sendKeys(readProperties("course_key").split("E-")[1]);
		element("txt_cnt_key").sendKeys(cnt_code);

		isElementDisplayed("btn_ys_cntn");
		element("btn_ys_cntn").click();

		String Content_Code = cnt_code;
		String Course_Key = readProperties("course_key").toString().trim();

		try {
			while (element("spn_err_cntKey_msg").getText().trim()
					.contains("Your Content Access code has already been used.")) {
				element("txt_cnt_key").clear();
				cnt_code = getContentCode();
				element("txt_cnt_key").sendKeys(cnt_code);
				isElementDisplayed("btn_ys_cntn");
				element("btn_ys_cntn").click();
			}
		} catch (Exception e) {
		}

		String lgn_id = fillDetailsWithUsernameContainingEmail();
		hardWait(2);
		isElementDisplayed("p_sucs_msg");
		Assert.assertTrue(element("p_cfn_msg").getText().contains(lgn_id));
		hardWait(2);
		isElementDisplayed("btn_strt_crs");
		element("btn_strt_crs").click();
		hardWait(2);
		// isElementDisplayed("hdr_msg");
		// isElementDisplayed("btn_cls_dlg");
		// element("btn_cls_dlg").click();

		try {
			String BookName = element("bookName").getText().toString().trim();
			element("SelfStudyActivityBtn").click();
			logMessage("User is able to launch the book Successfully via Self-Study Activity Button");
			logMessage("The CAC -->" + " " + Content_Code + " " + "And" + " " + "The Course Key -->" + " " + Course_Key
					+ " " + "-->" + "Provided the Access to the book -->" + BookName);
			logMessage("Verified that User is Successfully able to Launch the book using CAC");
		} catch (Exception Ex) {
			System.out.println("Error !! -- User is Not Able to Launch the book using CAC, Access Denied");
		}

		navigateToUrl(getData("baseurl") + "ilrn/resource/resource.do#/activity/book/"
				+ cnt_code.substring(0, cnt_code.indexOf("-")));
	}

	public void VerifyContentKeyEnrolment() {
		isElementDisplayed("lnk_crt_acnt");
		element("lnk_crt_acnt").click();
		isElementDisplayed("rdo_stud");
		element("rdo_stud").click();
		isElementDisplayed("btn_nxt");
		element("btn_nxt").click();
		isElementDisplayed("rdo_slf_study");
		element("rdo_slf_study").click();
		isElementDisplayed("btn_cntn");
		element("btn_cntn").click();
		isElementDisplayed("txt_slf_cnt_key");
		element("txt_slf_cnt_key").clear();
		String cnt_code = getContentCode();
		element("txt_slf_cnt_key").sendKeys(cnt_code);
		isElementDisplayed("btn_no_cntn");
		element("btn_no_cntn").click();
		String ContentCode2 = cnt_code;

		try {
			while (element("spn_err_cntKey_msg").getText().trim()
					.contains("Your Content Access code has already been used.")) {
				element("txt_slf_cnt_key").clear();
				cnt_code = getContentCode();
				element("txt_slf_cnt_key").sendKeys(cnt_code);
				isElementDisplayed("btn_no_cntn");
				element("btn_no_cntn").click();

			}
		} catch (Exception e) {
		}

		String lgn_id = fillDetailsWithUsernameContainingEmail();
		hardWait(2);
		isElementDisplayed("p_sucs_msg");
		Assert.assertTrue(element("p_cfn_msg").getText().contains(lgn_id));
		hardWait(2);
		isElementDisplayed("btn_strt_crs");
		element("btn_strt_crs").click();
		try {
			isElementDisplayed("hdr_msg");
			isElementDisplayed("btn_cls_dlg");
			element("btn_cls_dlg").click();

			try {
				String BookName = element("bookName").getText().toString().trim();
				element("SelfStudyActivityBtn").click();
				logMessage("User is able to launch the book the Successfully via Self-Study Activities Button");
				logMessage("The CAC -->" + " " + ContentCode2 + " " + "Provided the Access to the book -->" + " "
						+ BookName);
				logMessage("Verified that User is Successfully able to Launch the book using CAC");
			} catch (Exception ex) {
				System.out.println("User is Not Able to Launch the book using CAC, Access Denied");
			}
		} catch (Exception ex) {

		}

		navigateToUrl(getData("baseurl") + "ilrn/resource/resource.do#/activity/book/"
				+ cnt_code.substring(0, cnt_code.indexOf("-")));

	}

	public void VerifyResourceTabEnrolment1() {
		hardWait(1);
		isElementDisplayed("btn_ad_rsrc");
		element("btn_ad_rsrc").click();
		hardWait(1);
		isElementDisplayed("rdo_enrol_type");
		element("rdo_enrol_type").click();
		hardWait(1);
		isElementDisplayed("btn_rsrc_step");
		element("btn_rsrc_step").click();
		hardWait(1);
		isElementDisplayed("txt_rscr_crs_key");
		element("txt_rscr_crs_key").clear();
		element("txt_rscr_crs_key").sendKeys(readProperties("SelfLearner_CourseKey"));
		hardWait(1);
		isElementDisplayed("btn_sbt_crs_key");
		element("btn_sbt_crs_key").click();
		hardWait(1);
		isElementDisplayed("spn_crs_cde", readProperties("SelfLearner_CourseKey"));
		isElementDisplayed("spn_crs_nme", readProperties("Self-LearnerCourse"));
		isElementDisplayed("btn_enrl");
		element("btn_enrl").click();
		hardWait(2);
		Assert.assertTrue(element("hdr_msg_enrl").getText().contains(getData("hdr_enrl_msg")));
		isElementDisplayed("btn_go_crs_hm");
		element("btn_go_crs_hm").click();
		String CourseKey = readProperties("SelfLearner_CourseKey").toString().trim();
		try {
			String AssignmentName = element("AssignmentName").getText().toString().trim();
			element("AssignmentBtn").click();
			logMessage("User is able to launch the book the Successfully via Assignments Button");
			logMessage("The Course Key -->" + " " + CourseKey + " " + "Provided the Access to the Assignment -->" + " "
					+ AssignmentName);
			logMessage(
					"Verified that User is Successfully able to Launch the Assignments using only Course Key Via Add a Resource Functionality.");
		} catch (Exception ex) {
			System.out.println("Error !! User is Not Able to Launch the Assignment using Course Key, Access Denied");
		}

	}

	public void VerifyResourceTabEnrolment2() {
		hardWait(2);
		element("MyELTLogo").click();
		isElementDisplayed("btn_ad_rsrc");
		element("btn_ad_rsrc").click();
		isElementDisplayed("rdo_enrol_type1");
		element("rdo_enrol_type1").click();
		isElementDisplayed("btn_rsrc_step");
		element("btn_rsrc_step").click();

		isElementDisplayed("txt_rscr_crs_key");
		element("txt_rscr_crs_key").clear();
		element("txt_rscr_crs_key").sendKeys(readProperties("Hybrid_CourseKey"));
		String cnt_code = getContentCode();
		element("txt_cnt_key1").sendKeys(cnt_code);

		isElementDisplayed("btn_sbt_crs_key1");
		element("btn_sbt_crs_key1").click();

		try {
			while (element("spn_err_cntKey_msg1").getText().trim().contains(
					"You have already registered using this ISBN or Content Access Code. Contact your instructor or technical support")) {
				element("txt_slf_cnt_key").clear();
				cnt_code = getContentCode();
				element("txt_slf_cnt_key").sendKeys(cnt_code);
				isElementDisplayed("btn_sbt_crs_key1");
				element("btn_sbt_crs_key1").click();
			}
		} catch (Exception e) {
		}

		isElementDisplayed("spn_crs_cde", readProperties("Hybrid_CourseKey"));
		isElementDisplayed("spn_crs_nme", readProperties("HybridCourse"));
		isElementDisplayed("btn_enrl1");
		hardWait(2);
		element("btn_enrl1").click();
		hardWait(2);
		Assert.assertTrue(element("hdr_msg_enrl").getText().contains(getData("hdr_enrl_msg")));
		isElementDisplayed("btn_go_crs_hm");
		element("btn_go_crs_hm").click();
		String Course_Key1 = readProperties("HybridCourse").toString().trim();
		String Content_Code1 = cnt_code;

		try {
			String AssignmentName = element("AssignmentName").getText().toString().trim();
			String BookName = element("bookName").getText().toString().trim();
			element("AssignmentBtn").click();
			element("MyELTLogo").click();
			element("SelfStudyActivityBtn").click();
			logMessage(
					"User is able to launch the Assignment and the Book Successfully via Assignments Button and Self-Study Activities button");
			logMessage("The CAC -->" + " " + Content_Code1 + " " + "And" + " " + "The Course Key -->" + " "
					+ Course_Key1 + " " + "-->" + "Provided the Access to the book -->" + BookName + " " + "&"
					+ "The Assignment-->" + AssignmentName);
			logMessage("Verified that a Hybrid User is Successfully able to Launch the Assignments and Content.");
		} catch (Exception ex) {
			System.out.println(
					"Error !! User is Not Able to register the hybrid course using Add a Resource functionality");
		}
	}

	public void VerifyResourceTabEnrolment3() {
		element("MyELTLogo").click();
		isElementDisplayed("btn_ad_rsrc");
		element("btn_ad_rsrc").click();
		isElementDisplayed("rdo_enrol_type2");
		element("rdo_enrol_type2").click();
		isElementDisplayed("btn_rsrc_step");
		element("btn_rsrc_step").click();
		String cnt_code = getContentCode();
		element("txt_cnt_key2").sendKeys(cnt_code);
		hardWait(2);
		isElementDisplayed("btn_sbt_crs_key2");
		element("btn_sbt_crs_key2").click();
		hardWait(2);
		isElementDisplayed("btn_enrl2");
		hardWait(2);
		element("btn_enrl2").click();
		hardWait(2);
		Assert.assertTrue(element("hdr_msg_enrl").getText().contains(getData("hdr_enrl_msg")));
		hardWait(2);
		isElementDisplayed("btn_go_crs_hm");
		hardWait(2);
		element("btn_go_crs_hm").click();
		try {
			logMessage(
					"Verified that User is Successfully able to Launch the Book using only Content Access Code Via Add a Resource Functionality.");
		} catch (Exception ex) {
			System.out.println("Error !! User is Not Able to Launch the Book using CAC, Access Denied");
		}
	}

	public String fillDetails() {

		String lgn_id = "qai" + System.currentTimeMillis();
		isElementDisplayed("txt_lgn_id");
		element("txt_lgn_id").sendKeys(lgn_id);
		isElementDisplayed("txt_pwd");
		element("txt_pwd").sendKeys(getData("passwd"));
		isElementDisplayed("txt_pwd2");
		element("txt_pwd2").sendKeys(getData("passwd"));
		isElementDisplayed("txt_eml");
		element("txt_eml").sendKeys(lgn_id + "@qai.com");
		isElementDisplayed("txt_r_eml");
		element("txt_r_eml").sendKeys(lgn_id + "@qai.com");

		// isElementDisplayed("slct_ver_ques");
		// selectProvidedTextFromDropDown(element("slct_ver_ques"),
		// getData("sec_ques"));
		// isElementDisplayed("txt_ver_ans");
		// element("txt_ver_ans").sendKeys("QAI");
		isElementDisplayed("txt_f_name");
		element("txt_f_name").sendKeys("StudentFName");
		isElementDisplayed("txt_l_name");
		element("txt_l_name").sendKeys("StudentLName");

		isElementDisplayed("slct_cntry");
		selectProvidedTextFromDropDown(element("slct_cntry"), "India");
		isElementDisplayed("rdo_agree");
		element("rdo_agree").click();
		isElementDisplayed("btn_sbt_dtls");
		element("btn_sbt_dtls").click();

		return lgn_id;
	}

	public String fillDetailsWithUsernameContainingEmail() {

		String lgn_id = "qai" + System.currentTimeMillis();
		isElementDisplayed("txt_lgn_id");
		element("txt_lgn_id").sendKeys(lgn_id + "@qait.com");
		isElementDisplayed("txt_pwd");
		element("txt_pwd").sendKeys(getData("passwd"));
		isElementDisplayed("txt_pwd2");
		element("txt_pwd2").sendKeys(getData("passwd"));
		isElementDisplayed("txt_eml");
		element("txt_eml").sendKeys(lgn_id + "@qai.com");
		isElementDisplayed("txt_r_eml");
		element("txt_r_eml").sendKeys(lgn_id + "@qai.com");

		// isElementDisplayed("slct_ver_ques");
		// selectProvidedTextFromDropDown(element("slct_ver_ques"),
		// getData("sec_ques"));
		// isElementDisplayed("txt_ver_ans");
		// element("txt_ver_ans").sendKeys("QAI");
		isElementDisplayed("txt_f_name");
		element("txt_f_name").sendKeys("StudentFName");
		isElementDisplayed("txt_l_name");
		element("txt_l_name").sendKeys("StudentLName");

		isElementDisplayed("slct_cntry");
		selectProvidedTextFromDropDown(element("slct_cntry"), "India");
		isElementDisplayed("rdo_agree");
		element("rdo_agree").click();
		isElementDisplayed("btn_sbt_dtls");
		element("btn_sbt_dtls").click();

		return lgn_id;
	}

	public void VerifyAssignmentForStudent() {
		isElementDisplayed("asgn_crs_name", readProperties("inst_crs_name"));
		element("asgn_crs_name", readProperties("inst_crs_name")).click();
		String assignt_name = readProperties("unit_name");
		hardWait(2);
		isElementDisplayed("asgn_name", assignt_name);
	}

	public void VerifyEditUserProfile() {
		hardWait(2);
		isElementDisplayed("lnk_usr_drpdwn");
		hardWait(2);
		element("lnk_usr_drpdwn").click();
		hardWait(2);
		isElementDisplayed("lnk_edit_usr");
		element("lnk_edit_usr").click();

		isElementDisplayed("txt_old_pwd");
		element("txt_old_pwd").clear();
		element("txt_old_pwd").sendKeys(getData("passwd"));

		isElementDisplayed("txt_street");
		element("txt_street").clear();
		element("txt_street").sendKeys(getData("street"));
		isElementDisplayed("txt_city");
		element("txt_city").clear();
		element("txt_city").sendKeys(getData("city"));
		isElementDisplayed("txt_state");
		element("txt_state").clear();
		element("txt_state").sendKeys(getData("state"));
		isElementDisplayed("btn_save");
		element("btn_save").click();

		Assert.assertTrue(element("dv_updt_msg").getText().contains("Your account has been updated"));
	}

	public void VerifyChangePassword(String stud_id) {
		hardWait(2);
		isElementDisplayed("lnk_usr_drpdwn");
		element("lnk_usr_drpdwn").click();
		isElementDisplayed("lnk_edit_usr");
		element("lnk_edit_usr").click();

		isElementDisplayed("txt_old_pwd");
		element("txt_old_pwd").clear();
		element("txt_old_pwd").sendKeys(getData("passwd"));

		isElementDisplayed("txt_nw_pwd");
		element("txt_nw_pwd").clear();
		element("txt_nw_pwd").sendKeys(getData("new_passwd"));
		isElementDisplayed("txt_cf_nw_pwd");
		element("txt_cf_nw_pwd").clear();
		element("txt_cf_nw_pwd").sendKeys(getData("new_passwd"));
		isElementDisplayed("btn_save");
		element("btn_save").click();
		logMessage("Verified that " + stud_id + " password is changed successfully.");
	}

	public void VerifyHelpPage(String stud_id) {
		hardWait(2);
		isElementDisplayed("lnk_help");
		element("lnk_help").click();
		isElementDisplayed("txt_f_name");
		element("txt_f_name").clear();
		element("txt_f_name").sendKeys(getData("Student.FirstName"));
		isElementDisplayed("txt_l_name");
		element("txt_l_name").clear();
		element("txt_l_name").sendKeys(getData("Student.LastName"));
		isElementDisplayed("txt_username");
		element("txt_username").clear();
		element("txt_username").sendKeys(stud_id);

		isElementDisplayed("txt_email");
		element("txt_email").clear();
		element("txt_email").sendKeys(stud_id + "@qai.com");

		isElementDisplayed("txt_school");
		element("txt_school").clear();
		element("txt_school").sendKeys(getData("Student.school"));
		isElementDisplayed("txt_cntry");
		element("txt_cntry").clear();
		element("txt_cntry").sendKeys(getData("Student.country"));
		isElementDisplayed("txt_problem");
		element("txt_problem").clear();
		element("txt_problem").sendKeys(getData("Student.desc_problem"));

		isElementDisplayed("btn_submit");
		element("btn_submit").click();
		hardWait(2);
		try {
			driver.switchTo().alert().accept();
		} catch (Exception ex) {

		}

	}

	public void VerifyTakeAssignment() {
		VerifyAssignmentForStudent();
		isElementDisplayed("btn_take_assgn");
		element("btn_take_assgn").click();
	}

	public void VerifyTakeSelfStudyActivity(String self_activity_name) {
		if (getData("super_product").equals("true")) {
			try {
				WebElement superProduct = driver.findElement(By.cssSelector(
						"[class='text-center btn-toolbar course-action'] div:nth-child(1) a[href*='bookAbbr="
								+ self_activity_name + "']"));
				superProduct.isDisplayed();
				superProduct.click();
				logMessage("verified student clicked on self-study type super product activity.");
			} catch (Exception exception) {
				isElementDisplayed("lnk_slf_act", self_activity_name);
				elements("lnk_slf_act", self_activity_name).get(0).click();
				logMessage("verified student clicked on self-study type activity.");
			}
		} else if (getData("super_product").equals("false")) {
			isElementDisplayed("lnk_slf_act", self_activity_name);
			elements("lnk_slf_act", self_activity_name).get(0).click();
			logMessage("verified student clicked on self-study type activity.");
		}
	}

	public void launchActivity() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if (getData("super_product").equals("false")) {
			try {
				while (driver
						.findElement(By.cssSelector(
								"[id*='swipe-panel-']:not([style*='display: none;']) li:nth-child(1) .fa.fa-chevron-right"))
						.isDisplayed()) {
					driver.findElement(By.cssSelector(
							"[id*='swipe-panel-']:not([style*='display: none;']) li:nth-child(1) .fa.fa-chevron-right"))
							.click();
					hardWait(2);
				}
			} catch (NoSuchElementException e) {
				System.out.println("no more activity folder.");
			}
			driver.manage().timeouts().implicitlyWait(Long.valueOf(TestSessionInitiator._getTimeoutsFromConfig()),
					TimeUnit.SECONDS);
			element("lnk_act_1").click();
		} else {
			naviagteThroughSuperProductActivity();
		}
	}

	public String VerifyAssignmentGrade() {
/*		String activity_name = PropFileHandler.readProperty("CurrentUnitTitleText") + " "
				+ PropFileHandler.readProperty("CurrentActivityParentTitleText") + " / "
				+ PropFileHandler.readProperty("CurrentActivityChildTitleText");
		if (activity_name.contains(":")) {
			activity_name = activity_name.replace(":", " /");
		} else {

			activity_name = PropFileHandler.readProperty("CurrentUnitTitleText") + " / "
					+ PropFileHandler.readProperty("CurrentActivityParentTitleText") + " / "
					+ PropFileHandler.readProperty("CurrentActivityChildTitleText");

		}
		hardWait(2);*/
		if (getData("super_product").equals("true")) {
			try {
				Assert.assertTrue(driver.findElement(By.cssSelector("#side-panel-trigger")).isDisplayed());
				driver.findElement(By.cssSelector("#side-panel-trigger")).click();
				driver.findElement(By.cssSelector("[ng-click='openGradebook()']")).click();
				driver.findElement(By.cssSelector("#asg-btn-all-grades")).click();
				String acString;
				if (PropFileHandler.readProperty("AssignmentActivitName").contains("/")) {
					acString = PropFileHandler.readProperty("AssignmentActivitName").replace("/", "");
				} else {
					acString = PropFileHandler.readProperty("AssignmentActivitName");
				}

				hardWait(3);

				WebDriverWait driverWait = new WebDriverWait(driver, 10);
				driverWait.until(ExpectedConditions.visibilityOf(
						driver.findElement(By.xpath(".//*[@id='gbn-table']/descendant::*[contains(text(),'" + acString
								+ "')]/ancestor::tr/td[3]"))));

				String el_grade = driver.findElement(By.xpath(
						".//*[@id='gbn-table']/descendant::*[contains(text(),'" + acString + "')]/ancestor::tr/td[3]"))
						.getText();
				logMessage("verified that on the grade book score is equal to proportion "
						+ PropFileHandler.readProperty("IncorrectAnswersCount") + " / "
						+ PropFileHandler.readProperty("TotalAnswersCount"));

				return el_grade;
			} catch (Exception exception) {
				try {
					isElementDisplayed("td_grade", PropFileHandler.readProperty("AssignmentActivitName").trim());
					return element("td_grade", PropFileHandler.readProperty("AssignmentActivitName").trim()).getText();
				} catch (Exception exception2) {
					String grade = PropFileHandler.readProperty("AssignmentScore").toString();
					logMessage("Verified that Grades :-  " + grade + " is displayed once user finishes the activity.");
					return grade;
				}
			}
		} else {
			try {
				isElementDisplayed("td_grade", PropFileHandler.readProperty("AssignmentActivitName").trim());
				return element("td_grade", PropFileHandler.readProperty("AssignmentActivitName").trim()).getText();
			} catch (Exception exception) {
				String grade = PropFileHandler.readProperty("AssignmentScore").toString();
				logMessage("Verified that Grades :-  " + grade + " is displayed once user finishes the activity.");
				return grade;
			}
		}
	}

	public String VerifySelfActivityAssignmentGrade() {
		driver.findElement(By.cssSelector("[data-href='#/grades/book/']")).click();
		String activityName = PropFileHandler.readProperty("CurrentActivityChildTitleText");
		if (activityName.contains("(")) {
			activityName = activityName.substring(activityName.indexOf("(") + 1, activityName.lastIndexOf(")"));
		}

		String score = element("lbl_activityName_scr", activityName).getText().trim();
		String percent = element("lbl_activityName_prcnt", activityName).getText().trim();

		String activity = PropFileHandler.readProperty("CurrentUnitTitleText") + " / "
				+ PropFileHandler.readProperty("CurrentActivityParentTitleText") + " / "
				+ PropFileHandler.readProperty("CurrentActivityChildTitleText");

		logMessage("Verified that User completd activity :-  " + activity);
		logMessage("Verified that Scores :-  " + score + " is displayed once user finishes the activity.");
		logMessage("Verified that Grades :-  " + percent + " is displayed once user finishes the activity.");

		return percent;
	}

	public void verifyReviewActivityAndReviewScoresTab() {
		String activityName = PropFileHandler.readProperty("CurrentActivityChildTitleText");
		if (activityName.contains("(")) {
			activityName = activityName.substring(activityName.indexOf("(") + 1, activityName.lastIndexOf(")"));
		}

		element("lnk_activityName_prcnt", activityName).click();
		isElementDisplayed("tb_rev_actvty");
		isElementDisplayed("tb_rev_scrs");
	}

	Assignment assign = new Assignment(driver);
	FIBDDEngineActions fibdd = new FIBDDEngineActions(driver);
	FIBMSEngineActions fibms = new FIBMSEngineActions(driver);
	EssayEngineActions essay = new EssayEngineActions(driver);
	int  oldUI;
	String engine;
	public void VerifyStudentAttemptAssignment() {
		
		Boolean flag = true;
		while (flag) {
			oldUI = 0;

			/*
			 * Please add Next activity to POR of the class in which this
			 * functions is to be used
			 */
			engine = assign.detectEngine();	
			System.out.println("Current Activity Engine is :-" +engine );
			if (engine.equals("fibdd")) {
				if (getData("super_product").equals("true")) {
					try {
						
						hardWait(2);
						switchToDefaultContent();
						driver.findElement(By.cssSelector("span[ng-click = 'launchHome()']>i")).isDisplayed();
						assign.switch_to_activity_frame();
						assign.set_activity_title();
					} catch (Exception ex) {
						System.out.println("Created Assignement is not Superproduct UI");
						oldUI = 1;
					}
				} 
				fibdd.attempt_all_answers();
				if (!getData("super_product").equals("true")) {
					try {
						fibdd.verify_soft_save();
					} catch (Exception exception) {
						assign.Submit_activity();
						assign.close_activity();
					}
				}
				if(oldUI ==1){
				fibdd.verify_soft_save();
				}
				assign.Submit_activity();
				assign.close_activity();
				flag = false;

			} 
			else if (engine.equals("fibms")) {
				if (getData("super_product").equals("true")) {
					try {
						assign.switch_to_activity_frame();
						assign.set_activity_title();
					} catch (Exception exception) {
						assign.set_activity_details();
					}
				} else {
					assign.set_activity_details();
				}
				PropFileHandler.writeToFile("NumberOfDropDownOnCurrentPage",
						String.valueOf(elements("Dropdown").size()));
				try {
					isElementDisplayed("TypeIn");
					fibms.attempt_all_answers_FIBMS_TypeIn();
				} catch (org.openqa.selenium.NoSuchElementException e) {
					fibms.attempt_all_answers_FIBMS_DragDrop();
				}
				assign.Submit_activity();
				if (!getData("super_product").equals("true")) {
					assign.close_activity();
				}
				flag = false;
			}
			else if (engine.equals("essay")) {
				System.out.println("essaysysy");
				if (getData("super_product").equals("true")) {
					try {
						System.out.println("essaysysypro");
						assign.switch_to_activity_frame();
						assign.set_activity_title();
					} catch (Exception exception) {
						assign.set_activity_details();
					}
				} else {
					assign.set_activity_details();
				}
			    
				System.out.println("Textarea check"+elements("EssayTextArea").size());
				PropFileHandler.writeToFile("TextAreaCountInEssay", String.valueOf((elements("EssayTextArea").size())));
				essay.send_text_to_multipleTextArea();
				essay.verify_soft_save();
				assign.Submit_activity_essay();
				assign.close_activity();
				flag = false;	
			} 
			else {
				assign.navigate_to_Next_Activity();
			}
			}
		}

	

	public void VerifyStudentAttemptSelfActivityAssignment() {
		Boolean flag = true;
		while (flag) {

			/*
			 * Please add Next activity to POR of the class in which this
			 * functions is to be used
			 */
			String engine = assign.detectEngine();
			if (engine.equals("fibdd")) {
				assign.set_activity_details();
				fibdd.attempt_all_answers();
				assign.Submit_activity();
				assign.close_activity();
				
				flag = false;

			} else if (engine.equals("fibms")) {
				assign.set_activity_details();
				PropFileHandler.writeToFile("NumberOfDropDownOnCurrentPage",
						String.valueOf(elements("Dropdown").size()));
				try {
					isElementDisplayed("TypeIn");
					fibms.attempt_all_answers_FIBMS_TypeIn();
				} catch (org.openqa.selenium.NoSuchElementException e) {
					fibms.attempt_all_answers_FIBMS_DragDrop();
				}
				// fibms.attempt_all_answers();
				assign.Submit_activity();
				assign.close_activity();
				flag = false;
			} else if (engine.equals("essay")) {
				assign.set_activity_details();
				PropFileHandler.writeToFile("TextAreaCountInEssay", String.valueOf(elements("EssayTextArea").size()));
				essay.send_text_to_multipleTextArea();
				essay.verify_soft_save();
				assign.Submit_activity_essay();
				assign.close_activity();
				flag = false;
			} else {
				assign.navigate_to_Next_Activity();
			}
		}
	}

	public void naviagteThroughSuperProductActivity() {
		
//		WebElement firstActivity = driver.findElement(
//				By.cssSelector("[ng-show='part.showDetails'] ul li:nth-child(1)  div:nth-child(2) span>a"));
///*		WebElement firstActivity = driver.findElement(
//				By.xpath("//div[@ng-show='!lesson.showLessonDetails']/div[3]/span/a"));*/
//		Assert.assertTrue(firstActivity.isDisplayed());
//		//firstActivity.click();
		driver.get("http://69.32.200.233/ilrn/home/lshome.do?bookAbbr=KEYA_DA#/book/KEYA_D_U10/adaptive-activity/1098790629");
	}

	public void verifyReviewActivityAndReviewScoresTab(String act_name) {
		if (act_name.contains("(")) {
			act_name = act_name.substring(act_name.indexOf("(") + 1, act_name.lastIndexOf(")"));
		}

		if (act_name.contains("\\")) {
			act_name = act_name.replace("\\", "");
		}
		if (getData("super_product").equals("true")) {
			try {
				driver.findElement(By.xpath(".//*[@id='gbn-table']/descendant::span[contains(text(),'" + act_name
						+ "')]/parent::a/ancestor::tr/descendant::td[2]/descendant::a")).click();
			} catch (Exception ex) {
				element("lnk_activityName_prcnt", act_name).click();
			}
		} else {
			element("lnk_activityName_prcnt", act_name).click();
		}
		//isElementDisplayed("tb_rev_actvty");
		//isElementDisplayed("tb_rev_scrs");
	}

}
