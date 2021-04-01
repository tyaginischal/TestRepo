package com.github.qa.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.github.qa.base.BasePage;
import com.github.qa.utils.TestUtils;

public class LandingPage extends BasePage {

	/** The Log.*/
	private static Logger Log = LogManager.getLogger(LandingPage.class.getName());

	/** The Sign in button*/
	private By signInBttn = By.xpath("//a[contains(text(),'Sign in')]");
	
	/** The Sign up button*/
	private By signUpBttn = By.xpath("//a[contains(text(),'Sign up')]");
	/**
	 * Instantiates the landing page.
	 *
	 * @param driver the driver
	 */
	
	// initialize elements
	public LandingPage(ThreadLocal<WebDriver> driver) {
		super(driver);
		this.driver = driver;
		Log.info("Initializing landing Page Objects");
		PageFactory.initElements(driver.get(), signInBttn);
	}
	
	public void enterValidURL_ToOpenTheApplication() {
		gotoURL(TestUtils.getProperty("url"));
	}

	/** Method to verify and click on the sign in button */
	public void verifyAndClickOnTheSignInButton() {
		waitForElementToBecomeVisible(signInBttn, longWait);
		Assert.assertTrue(isElementPresent(signInBttn), "Sign in button is not appearing on the landing page");
		clickAndWait(signInBttn, longWait);
	}
	
	/** Method to verify and click on the sign up button */
	public void verifyAndClickOnTheSignUpButton() {
		waitForElementToBecomeVisible(signUpBttn, longWait);
		Assert.assertTrue(isElementPresent(signUpBttn), "Sign in button is not appearing on the landing page");
		clickAndWait(signUpBttn, longWait);
	}


}