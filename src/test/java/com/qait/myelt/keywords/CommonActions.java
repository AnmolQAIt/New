package com.qait.myelt.keywords;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;

import static com.qait.automation.utils.ResourceLoader.readProperties;
import static com.qait.automation.utils.YamlReader.getData;

public class CommonActions extends GetPage {

	public CommonActions(WebDriver driver) {
		super(driver, "CommonPage");
	}

	/*
	 * In VerifyChangeLanguage method check Espanyol & Protugues language
	 * whether the About MyELT & Help link are changed according to language
	 * selected.
	 */
	public void VerifyChangeLanguage() {
		hardWait(2);
		isElementDisplayed("lnk_lang_drpdwn");
		element("lnk_lang_drpdwn").click();
		isElementDisplayed("lnk_lang", "2");
		element("lnk_lang", "2").click();
		hardWait(2);
		Assert.assertTrue(element("lnk_abt").getText().trim()
				.equals(getData("Language.Espanyol.About MyELT")));
		Assert.assertTrue(element("lnk_hlp").getText().trim()
				.equals(getData("Language.Espanyol.Help")));
		element("lnk_lang_drpdwn").click();
		isElementDisplayed("lnk_lang", "3");
		element("lnk_lang", "3").click();
		hardWait(2);
		Assert.assertTrue(element("lnk_abt").getText().trim()
				.equals(getData("Language.Protugues.About MyELT")));
		Assert.assertTrue(element("lnk_hlp").getText().trim()
				.equals(getData("Language.Protugues.Help")));		
		element("lnk_lang_drpdwn").click();
		isElementDisplayed("lnk_lang", "1");
		element("lnk_lang", "1").click();
		hardWait(2);
		logMessage("Verify that student is able to switch between the languages which are appearing on the language drop down");
	}

	public void VerifyGridAandListView() 
	{
	    element("MyELTLogo").click();
    	isElementDisplayed("dv_grid");
		element("dv_grid").click();
		isElementDisplayed("dv_grid_cnt");
		isElementDisplayed("dv_list");
		element("dv_list").click();
		isElementDisplayed("dv_list_cnt");
		logMessage("Verify that student will be able to view the books in List view/Grid View.(Grid View is selected by default).");
	}

	public void VerifyAnnouncements() {
		hardWait(2);
		isElementDisplayed("lnk_usr_drpdwn");
		element("lnk_usr_drpdwn").click();
		isElementDisplayed("lnk_annoc");
		element("lnk_annoc").click();
		isElementDisplayed("hdr_annoc");
		isElementDisplayed("tb_annoc");
		isElementDisplayed("img_home");
		element("img_home").click();
		logMessage("Verify that student will be able to view all the messages(Announcements)");
	}

	/*
	 * VerifyAllCourseType method display all the activiies, instructor-led as
	 * well as self-study activities according to choose of type button
	 */
	public void VerifyAllCoursesType() {
		isElementDisplayed("img_all_btn");
		element("img_all_btn").click();
		isElementDisplayed("btn_act");
		hardWait(3);
		isElementDisplayed("img_inst_btn");
		hardWait(3);
		element("img_inst_btn").click();
		hardWait(3);
		isElementDisplayed("btn_act");
		hardWait(3);

		isElementDisplayed("img_slf_btn");
		element("img_slf_btn").click();
		hardWait(4);
		isElementDisplayed("btn_act");

		hardWait(4);
		element("img_all_btn").click();
		hardWait(4);
		logMessage("Verify that student will be able to view all books and courses, instructor led resources and Self-Study Activities.");
	}

	public void VerifySendEmailToInstructor() {
		isElementDisplayed("img_eml");
		element("img_eml").click();
		hardWait(1);

		isElementDisplayed("txt_sub");
		element("txt_sub").sendKeys(getData("email.subject"));
		isElementDisplayed("txt_msg");
		element("txt_msg").sendKeys(
				getData("email.subject") + "\n\nThanks\n"
						+ readProperties("admin_student_account_id"));
		isElementDisplayed("btn_snd_eml");
		element("btn_snd_eml").click();
		hardWait(1);

		isElementDisplayed("dv_msg");
		isElementDisplayed("btn_cls_dlg");
		element("btn_cls_dlg").click();
		logMessage("Verify that student is able to send email to instructor.");
	}

}
