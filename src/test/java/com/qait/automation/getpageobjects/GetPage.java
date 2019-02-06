package com.qait.automation.getpageobjects;

import static com.qait.automation.getpageobjects.ObjectFileReader.getELementFromFile;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

public class GetPage extends BaseUi {

	protected WebDriver driver;
	String pageName;

	public GetPage(WebDriver driver, String pageName) {
		super(driver, pageName);
		this.driver = driver;
		this.pageName = pageName;
	}

	// TODO: put this in right place, create dedicated class for frame and
	// window handlers
	protected void switchToNestedFrames(String frameNames) {
		switchToDefaultContent();
		String[] frameIdentifiers = frameNames.split(":");
		for (String frameId : frameIdentifiers) {
			wait.waitForFrameToBeAvailableAndSwitchToIt(getLocator(frameId
					.trim()));
		}
	}

	protected WebElement element(String elementToken) {
		return wait.waitForElementToBeVisible(driver
				.findElement(getLocator(elementToken)));
	}

	protected WebElement element(String elementToken, String replacement) {
		return wait.waitForElementToBeVisible(driver.findElement(getLocator(
				elementToken, replacement)));
	}

	protected WebElement element(String elementToken, String freplacement,
			String sreplacement) {
		return wait.waitForElementToBeVisible(driver.findElement(getLocator(
				elementToken, freplacement, sreplacement)));
	}

	protected List<WebElement> elements(String elementToken, String replacement) {
		return wait.waitForElementsToBeVisible(driver.findElements(getLocator(
				elementToken, replacement)));
	}

	protected void isStringMatching(String actual, String expected) {
		Assert.assertEquals(actual, expected);
		logMessage("ACTUAL STRING : " + actual);
		logMessage("EXPECTED STRING :" + expected);
		logMessage("String compare Assertion passed.");
	}

	protected List<WebElement> elements(String elementToken) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		return driver.findElements(getLocators(locator[1].trim(),
				locator[2].trim()));
	}

	protected boolean isElementDisplayed(String elementName,
			String elementTextReplace) {
		wait.waitForElementToBeVisible(element(elementName, elementTextReplace));
		boolean result = element(elementName, elementTextReplace).isDisplayed();
		assertTrue(result, "Assertion Failed: element '" + elementName
				+ "with text " + elementTextReplace + "' is not displayed.");
		logMessage("Assertion Passed: element " + elementName + " with text "
				+ elementTextReplace + " is displayed.");
		return result;
	}

	protected void verifyElementText(String elementName, String expectedText) {
		wait.waitForElementToBeVisible(element(elementName));
		assertEquals(element(elementName).getText(), expectedText,
				"Assertion Failed: element '" + elementName
						+ "' Text is not as expected: ");
		logMessage("Assertion Passed: element " + elementName
				+ " is visible and Text is " + expectedText);
	}

	protected boolean isElementDisplayed(String elementName) {
		wait.waitForElementToBeVisible(element(elementName));
		boolean result = element(elementName).isDisplayed();
		assertTrue(result, "Assertion Failed: element '" + elementName
				+ "' is not displayed.");
		logMessage("Assertion Passed: element " + elementName
				+ " is displayed.");
		return result;
	}

	protected By getLocator(String elementToken) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		return getLocators(locator[1].trim(), locator[2].trim());
	}

	protected By getLocator(String elementToken, String replacement) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		locator[2] = locator[2].replaceAll("\\$\\{.+\\}", replacement);
		locator[2] = locator[2].replace("$", replacement);
		locator[2] = StringUtils.replace(locator[2],"$",replacement);
		return getLocators(locator[1].trim(), locator[2].trim());
	}

	protected By getLocator(String elementToken, String freplacement,
			String sreplacement) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		locator[2] = locator[2].replaceAll("\\$\\{" + elementToken + "\\}",
				freplacement);
		locator[2] = locator[2].replaceAll("\\#\\{" + elementToken + "\\}",
				sreplacement);
		locator[2] = StringUtils.replace(locator[2], "$", freplacement);
		locator[2]=StringUtils.replace(locator[2], "#", sreplacement);		
		return getLocators(locator[1].trim(), locator[2].trim());
	}

	// TODO rename to distiguish between getlocator and getlocators
	private By getLocators(String locatorType, String locatorValue) {
		switch (Locators.valueOf(locatorType)) {
		case id:
			return By.id(locatorValue);
		case xpath:
			return By.xpath(locatorValue);
		case name:
			return By.name(locatorValue);
		case classname:
			return By.className(locatorValue);
		case css:
			return By.cssSelector(locatorValue);
		case linktext:
			return By.linkText(locatorValue);
		default:
			return By.id(locatorValue);
		}
	}

	protected void navigateToUrl(String url) {
		try {
			driver.navigate().to(new URL(url));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void changeTheURL(String url) {
		driver.navigate().to(url);
	}

	public void dragandDrop(WebElement source, WebElement target){
	Actions builder = new Actions(driver);
	builder.clickAndHold(source).moveToElement(target).perform();
	hardWait(1);
	builder.release(target).build().perform();
	}

}