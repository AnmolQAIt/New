package com.qait.myelt.tests;

import static com.qait.automation.utils.YamlReader.getData;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;

public class AddUser {

	TestSessionInitiator test;

	@BeforeClass
	public void start_test_Session() {
		test = new TestSessionInitiator();
	
}
	
	@Test
	public void Step01_Launch_Application() {
		test.launchApplication(getData("baseurl"));
	}
	
	@Test(dependsOnMethods = "Step01_Launch_Application")
	public void Step02_CreateUser() {
		test.adactions.CreateMyELTUsers();
	}
	
}
