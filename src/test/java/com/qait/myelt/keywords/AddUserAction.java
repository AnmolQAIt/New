package com.qait.myelt.keywords;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import static com.qait.automation.utils.YamlReader.getData;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.qait.automation.getpageobjects.GetPage;

public class AddUserAction extends GetPage {

	WebDriver driver = null;

	public AddUserAction(WebDriver driver) {
		super(driver, "adactions");
		this.driver = driver;
	}

	public void CreateMyELTUsers() {
		// WebDriver browser = null;

		// File chromeDriver = new
		// File("C:\\Users\\ashishsinha\\Desktop\\Automation\\chromedriver.exe");
		// System.setProperty("webdriver.chrome.driver",
		// "C:\\Users\\ashishsinha\\Desktop\\Automation\\chromedriver.exe");
		// browser = new ChromeDriver();
		//
		//

		int NumberofStudents = 40; // Change as per requirement
		int NumberofInstructors = 0; // Change as per requirement
		double rls = 4.11; // Change as per requirement
		String Round = "Stg"; // Change as per requirement
		String email = "ashishsinha@qainfotech.net"; // Change as per
														// requirement
		WebElement iframe;
		WebElement iframe2;
		WebElement iframe3;
		WebElement iframe4;
		WebElement iframe5;
		WebElement iframe6;
		int instructorsid = 2; // No Changes Keep This Constant
		int studentid = 1; // No Changes Keep This Constant

		/* Login Data */
		driver.findElement(By.name("login")).sendKeys("admin_compro@myelt.com");
		driver.findElement(By.name("password")).sendKeys("thomson");
		driver.findElement(By.id("signin-btn")).click();
		/* Login Data Ends */

		/*
		 * Controlling Messages(session - user already login on another machine)
		 * and After login (Announcements)
		 */

		if (driver.getPageSource().contains("Account Currently in Use")) {
			driver.findElement(By.id("signin-btn")).click();
		} else if (driver.getPageSource().contains("You have 6 new  messages ")) {
			hardWait(3);
			driver.findElement(By.id("closeGlobalPopUp")).click();
		}

		else {
			System.out.println("MyELT Automation");
		}

		// browser.get("http://69.32.200.233/ilrn/resource/resource.do#/activity/book/AL_J/section/0");
		// List<WebElement> elem =
		// browser.findElementsByXPath(".//*[@id='ul_0']/li/a/span");

		hardWait(3);
		driver.findElement(By.xpath(".//*[@id='navbar-menu']/ul[2]/li/a")).click(); // click
																					// on
																					// admin
																					// tools
		clickUsingJS(driver.findElement(By.xpath(".//*[@id='navbar-menu']/ul[2]/li/ul/li/a")));

		iframe = driver.findElement(By.xpath("html/body/div[1]/div[4]/div/span/span/div/div/iframe")); // switch
																										// to
																										// frame
																										// for
																										// add
																										// delete
																										// user
		driver.switchTo().frame(iframe);
		hardWait(3);
		driver.findElement(By.xpath(".//a[contains(text(),'Add/Edit/Delete Users')]")).click(); // Click
																								// on
																								// Add
																								// Delete
																								// user
		for (int i = 16; i <= NumberofStudents; i++) {
			// frame on right side

			driver.switchTo().defaultContent();
			iframe3 = driver.findElement(By.xpath(
					"//div[@class='wrap jPanelMenu-panel']//div[@id='content']//div[@class='container']//span[@id='marker_top']//span[@id='marker_top']//div//div//iframe[@class='external-content-frame']"));
			driver.switchTo().frame(iframe3);
			hardWait(1);
			iframe4 = driver.findElement(By.name("bottom_right_frame"));
			driver.switchTo().frame(iframe4);
			driver.findElement(By.xpath("//html//body//a[1]")).click(); // Click
																		// on
																		// Add
																		// New
																		// User
			driver.findElement(By
					.xpath("html/body/form/table/tbody/tr[1]/td[2]/select[@id='user_type']/option[" + studentid + "]"))
					.click();
			driver.findElement(By.xpath("html/body/form/table/tbody/tr[3]/td[2]/input[@id='user_name']"))
					.sendKeys("test_std" + i + "_rls" + rls + "_" + Round + "");
			driver.findElement(By.xpath("html/body/form/table/tbody/tr[4]/td[2]/input[@name='pass_word']"))
					.sendKeys("password");
			driver.findElement(By.xpath("html/body/form/table/tbody/tr[5]/td[2]/input[@name='pass_word2']"))
					.sendKeys("password");
			driver.findElement(By.xpath("html/body/form/table/tbody/tr[6]/td[2]/input[@name='last_name']"))
					.sendKeys("rls" + rls + Round + "");
			driver.findElement(By.xpath("html/body/form/table/tbody/tr[7]/td[2]/input[@name='first_name']"))
					.sendKeys("test_std1");
					// driver.findElement(By.xpath("html/body/form/table/tbody/tr[12]/td[2]/input[@name='verification']")).sendKeys("qai");

			/***
			 * edited the locator for email, email2 ,allowsConcurrentLogins
			 * ,add_user_pressed
			 ***/
			driver.findElement(By.id("e_mail")).sendKeys("" + email + "");// ("html/body/form/table/tbody/tr[18]/td[2]/input[@name='e_mail']")).sendKeys(""+email+"");
			driver.findElement(By.name("e_mail2")).sendKeys("" + email + "");// xpath("html/body/form/table/tbody/tr[19]/td[2]/input[@name='e_mail2']")).sendKeys(""+email+"");
			driver.findElement(By.name("allowsConcurrentLogins")).click();// xpath("html/body/form/table/tbody/tr[32]/td[2]/select[@name='allowsConcurrentLogins']/option[2]")).click();
			driver.findElement(By.name("add_user_pressed")).click();// xpath("html/body/form/center[2]/input[@name='add_user_pressed']")).click();
			System.out.println("User :" + "test_std" + i + "_rls" + rls + "_" + Round + " CreatedSuccessfully");

			// frames on left side

			driver.switchTo().defaultContent();
			hardWait(2);
			iframe3 = driver.findElement(By.xpath(
					"//div[@class='wrap jPanelMenu-panel']//div[@id='content']//div[@class='container']//span[@id='marker_top']//span[@id='marker_top']//div//div//iframe[@class='external-content-frame']"));
			driver.switchTo().frame(iframe3);
			hardWait(2);
			iframe4 = driver.findElement(By.name("top_left_frame"));
			driver.switchTo().frame(iframe4);
			driver.findElement(By.xpath("html/body/form/table/tbody/tr[2]/td[2]/input[@name='user_name']"))
					.sendKeys("test_std" + i + "_rls" + rls + "_" + Round + ""); // UserName
			driver.findElement(By.xpath("html/body/form/center/input[@name='search_button']")).click();

			// Frames on Right Side

			driver.switchTo().defaultContent();
			iframe3 = driver.findElement(By.xpath(
					"//div[@class='wrap jPanelMenu-panel']//div[@id='content']//div[@class='container']//span[@id='marker_top']//span[@id='marker_top']//div//div//iframe[@class='external-content-frame']"));
			driver.switchTo().frame(iframe3);
			hardWait(2);
			iframe4 = driver.findElement(By.name("bottom_right_frame"));
			driver.switchTo().frame(iframe4);
			hardWait(2);
			driver.findElement(By.xpath("html/body/form[@id='main_form']/table[2]/tbody/tr[3]/td/small/a[2]")).click();
			driver.findElement(By
					.xpath("html/body/form[@name='permissionForm']/table[@id='feature_table']/tbody/tr[@class='user_header_row']/td/span[@id='image_container_0']"))
					.click();
			driver.findElement(
					By.xpath("html/body/form/table/tbody/tr[@id='tr_0_0']/td[5]/input[@name='feature.book-view-ALL']"))
					.click(); // Right Permission of All Books
			driver.findElement(
					By.xpath("html/body/form[@name='permissionForm']/p[2]/input[@name='commit_changes_button']"))
					.click(); // Click on commit changes button
			System.out.println("User :" + "test_std" + i + "_rls" + rls + "_" + Round
					+ " Permission Of All Books Given To This User");

			// Frames on Left Side

			driver.switchTo().defaultContent();

			iframe3 = driver.findElement(By.xpath(
					"//div[@class='wrap jPanelMenu-panel']//div[@id='content']//div[@class='container']//span[@id='marker_top']//span[@id='marker_top']//div//div//iframe[@class='external-content-frame']"));
			driver.switchTo().frame(iframe3);
			hardWait(2);
			iframe4 = driver.findElement(By.name("top_left_frame"));
			driver.switchTo().frame(iframe4);
			driver.findElement(By.xpath("html/body/form/center/input[@name='reset_filter']")).click(); // Click
																										// on
																										// Clear
																										// button
																										// on
																										// the
																										// left
																										// side

		}
		System.out.println("All users Successfully Created");

	}

	public void clickUsingJS(WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}

}
