package com.qait.myelt.keywords;

import static com.qait.automation.utils.ResourceLoader.getContentCode;
import static com.qait.automation.utils.ResourceLoader.getKey;
import static com.qait.automation.utils.ResourceLoader.getValue;
import static com.qait.automation.utils.ResourceLoader.randomInt;
import static com.qait.automation.utils.ResourceLoader.readProperties;
import static com.qait.automation.utils.ResourceLoader.subStringAfterBrackets;
import static com.qait.automation.utils.ResourceLoader.subStringBeforeBrackets;
import static com.qait.automation.utils.YamlReader.getData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

public class TagVerificationAction extends GetPage {

	public TagVerificationAction(WebDriver driver) {
		super(driver, "Tag");
	}

			
	public void VerifyTag(Map<String, String> Tag, String type) {
		Map map=new HashMap();   
		Set set=map.entrySet();//Converting to Set so that we can traverse  
		    Iterator itr=set.iterator();  
		    while(itr.hasNext()){  
		    	int i=0;
				if (getValue(Tag).equals("true")) {
					System.out.println("1");
					Object code = (String) itr.next();
				    Object country = map.get(code);
				    System.out.println(country);
				}
				else{
					System.out.println("0");
					Object code = (String) itr.next();
				    Object country = map.get(code);
				    System.out.println(country);
				}
			/*try {
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
		}*/
				}
	}

	private Object getValue(Map<String, String> tag) {
		// TODO Auto-generated method stub
		return null;
	}
}

/*
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
	/*if (getData("super_product").equals("true")) {
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
		/*	engine = assign.detectEngine();	
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

			} else if (engine.equals("fibms")) {
				if (getData("super_product").equals("true")) {
					try {
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
			} else if (engine.equals("essay")) {
				if (getData("super_product").equals("true")) {
					try {
						assign.set_activity_title();
					} catch (Exception exception) {
						assign.set_activity_details();
					}
				} else {
					assign.set_activity_details();
				}
				PropFileHandler.writeToFile("TextAreaCountInEssay", String.valueOf(elements("EssayTextArea").size()));
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
	/*		String engine = assign.detectEngine();
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
	/*	driver.get("http://69.32.200.233/ilrn/home/lshome.do?bookAbbr=KEYA_DA#/book/KEYA_D_U10/adaptive-activity/1098790629");
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
}*/
