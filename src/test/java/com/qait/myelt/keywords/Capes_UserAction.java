package com.qait.myelt.keywords;

import org.openqa.selenium.WebDriver;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.PropFileHandler;

public class Capes_UserAction extends GetPage {

	WebDriver driver = null;

	public Capes_UserAction(WebDriver driver) {
		super(driver, "Assignment");
		this.driver = driver;
	}

	public void CreateCapesUser() {
		// driver.findElement(By.xpath("//input[@name='login']")).sendKeys("qai_stg_admin@myelt.com");
		driver.findElement(By.xpath("//input[@name='login']")).sendKeys("iMEOQA48_admin@myelt.com");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("thomson");
		driver.findElement(By.xpath("//button[@id='signin-btn']")).click();
		// driver.findElement(By.xpath("//span[contains(text(),'Alunos')]")).click();
		// driver.findElement(By.xpath("//a[contains(text(),'Criar conta de
		// aluno')]")).click();
		driver.findElement(By.xpath("//span[contains(text(),'Students')]")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Create Student Account')]")).click();

		System.out.println("***-----------Capes User Credentials-------------***");

		int name = 1;

		for (name = 200; name <= 300; name++) {
			driver.findElement(By.xpath("//input[@id='userName']")).clear();
			driver.findElement(By.xpath("//input[@id='userName']")).sendKeys("iMEOQA48_Rls4.9_User" + name);
			driver.findElement(By.xpath("//input[@id='firstName']")).clear();
			driver.findElement(By.xpath("//input[@id='firstName']")).sendKeys("iMEOQA48_Rls4.9");
			driver.findElement(By.xpath("//input[@id='lastName']")).clear();
			driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys("User" + name);
			driver.findElement(By.xpath("//input[@id='password']")).clear();
			driver.findElement(By.xpath("//input[@id='password']")).sendKeys("password");
			driver.findElement(By.xpath("//input[@id='password2']")).clear();
			driver.findElement(By.xpath("//input[@id='password2']")).sendKeys("password");
			// obsoleted
			// driver.findElement(By.xpath("//input[@id='verificationAnswer']")).clear();
			// //obsoleted
			// driver.findElement(By.xpath("//input[@id='verificationAnswer']")).sendKeys("qai");
			// //obsoleted
			driver.findElement(By.xpath("//input[@id='email']")).clear();
			driver.findElement(By.xpath("//input[@id='email']")).sendKeys("myeltqaautomation@gmail.com");
			driver.findElement(By.xpath("//input[@id='email2']")).clear();
			driver.findElement(By.xpath("//input[@id='email2']")).sendKeys("myeltqaautomation@gmail.com");
			// driver.findElement(By.xpath("//a[contains(text(),'Adicionar
			// usuário')]")).click();
			driver.findElement(By.xpath("//a[contains(text(),'Add user')]")).click();
			hardWait(1);
			System.out.println("UserName : " + "iMEOQA48_Rls4.9_User" + name + " " + " " + "/" + "Password : password");
		}
	}

}
