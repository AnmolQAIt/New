/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qait.automation;

import static com.qait.automation.utils.DataReadWrite.getProperty;
import static com.qait.automation.utils.YamlReader.getYamlValue;
import static com.qait.automation.utils.YamlReader.setYamlFilePath;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;

import com.qait.automation.utils.CustomFunctions;
import com.qait.myelt.keywords.AddUserAction;
import com.qait.myelt.keywords.AdminPageActions;
import com.qait.myelt.keywords.Assignment;
import com.qait.myelt.keywords.CommonActions;
import com.qait.myelt.keywords.InstructorPageActions;
import com.qait.myelt.keywords.LoginPageActions;
import com.qait.myelt.keywords.StudentPageActions;
import com.qait.myelt.keywords.TagVerificationAction;
import com.qait.myelt.keywords.Capes_UserAction;
import com.qait.myelt.keywords.AcessCodeVerificationTestActions;

public class TestSessionInitiator {

	protected WebDriver driver;
	private WebDriverFactory wdfactory;
	String browser;
	String seleniumserver;
	String seleniumserverhost;
	String appbaseurl;
	String applicationpath;
	String chromedriverpath;
	String datafileloc = "";
	static int timeout;
	Map<String, Object> chromeOptions = null;
	DesiredCapabilities capabilities;

	/**
	 * Initiating the page objects
	 */
	public CustomFunctions customFunctions;
	public LoginPageActions loginPage;
	public AdminPageActions adminAction;
	public InstructorPageActions instActions;
	public StudentPageActions studActions;
	public Assignment assignment;
	public CommonActions cmnActions;
	public Capes_UserAction cpactions;
	public AddUserAction adactions;
	public AcessCodeVerificationTestActions acvt;
	public TagVerificationAction Tag;

	private void _initPage() {
		loginPage = new LoginPageActions(driver);
		adminAction = new AdminPageActions(driver);
		instActions = new InstructorPageActions(driver);
		studActions = new StudentPageActions(driver);
		customFunctions = new CustomFunctions(driver);
		cmnActions = new CommonActions(driver);
		assignment = new Assignment(driver);
		cpactions=new Capes_UserAction(driver);
		acvt=new AcessCodeVerificationTestActions(driver);
		adactions=new AddUserAction(driver);
	}

	/**
	 * Page object Initiation done
	 */

	public TestSessionInitiator() {
		wdfactory = new WebDriverFactory();
		testInitiator();
	}

	private void testInitiator() {
		setYamlFilePath();
		_configureBrowser();
		_initPage();
	}

	private void _configureBrowser() {
		driver = wdfactory.getDriver(_getSessionConfig());
		driver.manage().window().maximize();
		driver.manage()
				.timeouts()
				.implicitlyWait(
						Integer.parseInt(_getSessionConfig().get("timeout")),
						TimeUnit.SECONDS);
	}

	private Map<String, String> _getSessionConfig() {
		String[] configKeys = { "tier", "browser", "seleniumserver",
				"seleniumserverhost", "timeout", "driverpath" };
		Map<String, String> config = new HashMap<String, String>();
		for (String string : configKeys) {
			config.put(string, getProperty("./Config.properties", string));
		}
		return config;
	}
	
	public static String _getTimeoutsFromConfig() {
		String[] configKeys = { "tier", "browser", "seleniumserver",
				"seleniumserverhost", "timeout", "driverpath" };
		Map<String, String> config = new HashMap<String, String>();
		for (String string : configKeys) {
			config.put(string, getProperty("./Config.properties", string));
		}
		return config.get("timeout");
	}
	

	public void launchApplication() {
		launchApplication(getYamlValue("app_url"));
	}

	public void launchApplication(String applicationpath) {
		Reporter.log("The application url is :- " + applicationpath, true);
		Reporter.log(
				"The test browser is :- " + _getSessionConfig().get("browser"),
				true);
		driver.get(applicationpath);
	}

	public void getURL(String url) {
		driver.manage().deleteAllCookies();
		driver.get(url);
	}

	public void closeBrowserSession() {
		driver.quit();
	}
}