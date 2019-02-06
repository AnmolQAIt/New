package com.qait.myelt.keywords;

import static com.qait.automation.utils.ResourceLoader.getContentCodeDeployedBooks;
import static com.qait.automation.utils.YamlReader.getData;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.GetPage;

import junit.framework.Assert;

public class AcessCodeVerificationTestActions extends GetPage {

	WebDriver driver = null;

	public AcessCodeVerificationTestActions(WebDriver driver) {
		super(driver, "AccessCode");
		this.driver = driver;
	}

	@SuppressWarnings("unused")
	public void VerifyBookAccess() {
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
		String cnt_code = getContentCodeDeployedBooks();
		element("txt_slf_cnt_key").sendKeys(cnt_code);
		isElementDisplayed("btn_no_cntn");
		element("btn_no_cntn").click();
		String ContentCode2 = cnt_code;
		try {
			while (element("spn_err_cntKey_msg").getText().trim()
					.contains("Your Content Access code has already been used.")) {
				element("txt_slf_cnt_key").clear();
				cnt_code = getContentCodeDeployedBooks();
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

	}

	public boolean verifyAllHTMLBookAccess(String code) {
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
		String cnt_code = code;
		element("txt_slf_cnt_key").sendKeys(cnt_code);
		isElementDisplayed("btn_no_cntn");
		element("btn_no_cntn").click();
		try {
			element("spn_err_cntKey_msg").getText().trim().contains("Your Content Access code has already been used.");
		} catch (Exception e) {
			return true;
		}
		return false;
	}

	public Boolean re_EnterHMTLBooksAccessCode(String cnt_code) {
		element("txt_slf_cnt_key").clear();
		element("txt_slf_cnt_key").sendKeys(cnt_code);
		isElementDisplayed("btn_no_cntn");
		element("btn_no_cntn").click();
		try {
			element("spn_err_cntKey_msg").getText().trim().contains("Your Content Access code has already been used.");
		} catch (Exception e) {
			return true;
		}
		return false;
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
		/*** commented code is obsoleted ***/
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

	public void navigateToDeployedHMTLBook() {
		isElementDisplayed("btn_start_myelt");
		element("btn_start_myelt").click();
		logMessage("Verified user clicked on 'Start Using MyELT' button");
	}

	public void verifyDeployedHMTLBookAddedToStudent(String accessCode) {
		Assert.assertTrue(isElementDisplayed("lbl_course_key"));
		String book_title = driver
				.findElement(By.xpath(".//*[contains(text(),'" + accessCode + "')]/preceding-sibling::p/span/a"))
				.getText().trim();
		logMessage("Verified deployed HTML Book with Access Code  '" + accessCode + "' and title: '" + book_title
				+ "'is  addedd to Student.");
	}

}
