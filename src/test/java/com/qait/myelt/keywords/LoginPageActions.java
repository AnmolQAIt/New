package com.qait.myelt.keywords;

import static com.qait.automation.utils.YamlReader.getData;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.GetPage;

public class LoginPageActions extends GetPage {

	public LoginPageActions(WebDriver driver) {
		super(driver, "LoginPage");
	}

	/**
	 * This method enters username and password in the respective fields and
	 * Click on login button on the activation page
	 * 
	 * @param userName
	 *            the username provided from the TestData yml
	 * @param password
	 *            the password provided from the TestData yml
	 */
	
	public void LoginToTheApplication(String userName, String password) {
		hardWait(1);
		verifyPageTitleExact();
		element("inp_Username").clear();
		element("inp_Username").sendKeys(userName);
		element("inp_Password").clear();
		element("inp_Password").sendKeys(password);
		element("login_btn").click();
		try {
			isElementDisplayed("loginMultipleSessionError");
			element("login_btn").click();
		}catch(Exception ex){
		}
		
		// try
		// {
		// if(element("LoginInformationWrong").isDisplayed())
		// {
		// element("inp_Password").clear();
		// element("inp_Password").sendKeys("Password2");
		// element("login_btn").click();
		// }
		// else
		// {
		//
		// }}
		// catch(Exception ex)
		// {
		//
		// }
		//
		// try {
		//
		// isElementDisplayed("dv_close_session");
		// element("login_btn").click();
		//
		// } catch (Exception e1) {
		// }
		// try {
		// isElementDisplayed("ann_header");
		// element("cls_ann").click();
		//
		// } catch (Exception e2) {
		//
		// }

		logMessage("User enters username:- " + userName + " and password:- "
				+ password + " on MyELT Login Page");
		hardWait(2);
	}

	public void VerifyUserIsOnLoginPage() {
		verifyPageTitleExact();
		isElementDisplayed("inp_Username");
		isElementDisplayed("inp_Password");
		isElementDisplayed("login_btn");
		logMessage("Verified that the user is on the MyELT Login Page");
	}

	public void VerifyUserIsOnSignOutPage() {
		try
		{
			hardWait(3);
		isElementDisplayed("validate_sign_out");
		commonlogout();
		hardWait(2);
		}
		catch (Exception Ex)
		{
			try
			{
			driver.navigate().to(
					new URL(getData("baseurl")));
			}
			catch(MalformedURLException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void commonlogout() {
		element("validate_sign_out").click();
	}

	public void VerifyLoginError() {
		// verifyElementText("lbl_UnsuccessfulLoginError",
		// getData("invalid-users.errorMessage"));
		// logMessage("Login unsuccessful error verified");
	}

	public void VerifyRememberMeCheckBox() {
		// isElementDisplayed("chkbox_RememberMe");
		// element("chkbox_RememberMe").click();
		// logMessage("Remember me checked");
		// element("chkbox_RememberMe").click();
		// logMessage("Remember me unchecked");
	}
}
