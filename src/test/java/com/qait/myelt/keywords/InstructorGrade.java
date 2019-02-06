package com.qait.myelt.keywords;

import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.GetPage;

public class InstructorGrade extends GetPage {

	public InstructorGrade(WebDriver driver, String pageName) {
		super(driver, pageName);
		// TODO Auto-generated constructor stub
	}

	Assignment assign = new Assignment(driver);
	FIBDDEngineActions fibdd = new FIBDDEngineActions(driver);

	public void InstructorAttemptForGrade() {
		while (element("NextActivity").isEnabled()) {
			
		/*Please add Next activity to POR of the class in which this functions is to be used*/
			String engine = assign.detectEngine();
			if (engine.equals("fibdd")) {
				fibdd.attempt_all_answers();
				assign.Submit_activity();

			} else {
				assign.navigate_to_Next_Activity();
			}
		}
	}

}
