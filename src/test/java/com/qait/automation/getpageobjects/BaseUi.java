/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qait.automation.getpageobjects;

import static com.qait.automation.getpageobjects.ObjectFileReader.getPageTitleFromFile;
import static com.qait.automation.utils.DataReadWrite.getProperty;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.qait.automation.utils.SeleniumWait;

/**
 *
 * @author prashantshukla
 */
public class BaseUi {

    WebDriver driver;
    protected SeleniumWait wait;
    private String pageName;

    protected BaseUi(WebDriver driver, String pageName) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.pageName = pageName;
        this.wait = new SeleniumWait(driver,
                Integer.parseInt(getProperty("Config.properties", "timeout")));
    }

    protected String getPageTitle() {
        return driver.getTitle();
    }

    protected void logMessage(String message) {
        Reporter.log(message, true);
    }

    protected String getCurrentURL() {
        return driver.getCurrentUrl();
    }

    protected void verifyPageTitleExact() {
        String pageTitle = getPageTitleFromFile(pageName);
        verifyPageTitleExact(pageTitle);
    }

    protected void verifyPageTitleExact(String expectedPagetitle) {
        wait.waitForPageTitleToContain(expectedPagetitle);
//      assertEquals(getPageTitle(), expectedPagetitle, "Test Failed due to page title check on " + pageName);
        logMessage("Assertion Passed: PageTitle for " + pageName
                + " is exactly: '" + expectedPagetitle + "'");
    }

    /**
     * Verification of the page title with the title text provided in the page object repository
     */
    protected void verifyPageTitleContains() {
        String expectedPagetitle = getPageTitleFromFile(pageName).trim();
        verifyPageTitleContains(expectedPagetitle);
    }

    /**
     * this method will get page title of current window and match it partially with the param provided
     *
     * @param expectedPagetitle partial page title text
     */
    protected void verifyPageTitleContains(String expectedPagetitle) {
        wait.waitForPageTitleToContain(expectedPagetitle);
        String actualPageTitle = getPageTitle().trim();
        assertTrue(actualPageTitle.contains(expectedPagetitle),
                "Verifying Actuals Page Title: '" + actualPageTitle
                + "' contains expected Page Title : '"
                + expectedPagetitle + "'.");
        logMessage("Assertion Passed: PageTitle for " + actualPageTitle
                + " contains: '" + expectedPagetitle + "'.");
    }

    protected WebElement getElementByIndex(List<WebElement> elementlist, int index) {
        return elementlist.get(index);
    }

    protected WebElement getElementByExactText(List<WebElement> elementlist, String elementtext) {
        WebElement element = null;
        for (WebElement elem : elementlist) {
            if (elem.getText().equalsIgnoreCase(elementtext.trim())) {
                element = elem;
            }
        }
        // FIXME: handle if no element with the text is found in list
        if (element == null) {
        }
        return element;
    }

    protected WebElement getElementByContainsText(List<WebElement> elementlist, String elementtext) {
        WebElement element = null;
        for (WebElement elem : elementlist) {
            if (elem.getText().contains(elementtext.trim())) {
                element = elem;
            }
        }
        // FIXME: handle if no element with the text is found in list
        if (element == null) {
        }
        return element;
    }

    protected void switchToFrame(WebElement element) {
        //switchToDefaultContent();
        wait.waitForElementToBeVisible(element);
        driver.switchTo().frame(element);
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    protected void executeJavascript(String script) {
        ((JavascriptExecutor) driver).executeScript(script);
    }
    public String executeJavascript2(String script) {
        return (String) ((JavascriptExecutor) driver).executeScript("return " +script +";");

    }
    public void clickUsingXpathInJavaScriptExecutor(WebElement element) {
    	  JavascriptExecutor executor = (JavascriptExecutor) driver;
    	  executor.executeScript("arguments[0].click();", element);
    	 }

    protected void hover(WebElement element) {
        Actions hoverOver = new Actions(driver);
        hoverOver.moveToElement(element).build().perform();
    }

    protected void handleAlert() {
        try {
            switchToAlert().accept();
            logMessage("Alert handled..");
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            System.out.println("No Alert window appeared...");
        }
    }

    private Alert switchToAlert() {
        WebDriverWait wait = new WebDriverWait(driver, 1);
        return wait.until(ExpectedConditions.alertIsPresent());
    }

    protected void changeWindow(int i) {
        hardWait(1);
        Set<String> windows = driver.getWindowHandles();
        if (i > 0) {
            for (int j = 0; j < 5; j++) {
                System.out.println("Windows: " + windows.size());
                if (windows.size() >= 2) {
                    try {
                        Thread.sleep(5000);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    break;
                }
                windows = driver.getWindowHandles();
            }
        }
        String wins[] = windows.toArray(new String[windows.size()]);
        driver.switchTo().window(wins[i]);
        hardWait(1);
        System.out.println("Title: " + driver.switchTo().window(wins[i]).getTitle());
    }

    //FIXME Remove hard Wait option
    protected void hardWait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    protected void closeWindow() {
        hardWait(1);
        driver.close();
    }

    protected void selectProvidedTextFromDropDown(WebElement el, String text) {
        wait.waitForElementToBeVisible(el);
        scrollDown(el);
        Select sel = new Select(el);
        sel.selectByVisibleText(text);
    }


    protected void selectProvidedIndexFromDropDown(WebElement el, int index) {
        wait.waitForElementToBeVisible(el);
        scrollDown(el);
        Select sel = new Select(el);
        sel.selectByIndex(index);
    }
    protected void scrollDown(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(true);", element);
    }

    protected void hoverClick(WebElement element) {
        Actions hoverClick = new Actions(driver);
        hoverClick.moveToElement(element).click().build().perform();
    }

    public void click(WebElement element) {
        try {
            wait.waitForElementToBeVisible(element);
            scrollDown(element);
            element.click();
        } catch (StaleElementReferenceException ex1) {
            wait.waitForElementToBeVisible(element);
            scrollDown(element);
            element.click();
            logMessage("Clicked Element " + element + " after catching Stale Element Exception");
        } catch (Exception ex2) {
            logMessage("Element " + element + " could not be clicked! " + ex2.getMessage());
        }
    }
  
}
