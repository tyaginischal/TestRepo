package com.github.qa.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.github.qa.base.BasePage;

public class PasswordResetPage extends BasePage {
	
	/** The Log. */
	private static Logger Log = LogManager.getLogger(PasswordResetPage.class.getName());
	
	/** The Email field*/
	private By emailField = By.id("email_field");
	
	/** The Send password reset email button */
	private By sendPassResetEmailBttn = By.xpath("//input[@value='Send password reset email']");
	
	/** The GitHub Logo*/
	private By gitHubLogo = By.xpath("//a[@class='header-logo']");

	public PasswordResetPage(ThreadLocal<WebDriver> driver) {
		super(driver);
		this.driver = driver;
		Log.info("Initializing landing Page Objects");
		PageFactory.initElements(driver.get(), emailField);
	}
	
	/** Method to verify email field on reset password page*/
	public void verifyEmailFieldAndEnterEmail(String email) {
		waitForElementToBecomeVisible(emailField, longWait);
		Assert.assertTrue(isElementPresent(emailField), "User name field is not appearing on the login page");
		setText(emailField, randomid()+email, shortWait);
	}
	public void verifySendPassResetEmailBttnIsDisabled() {
		Assert.assertFalse(verifyIsEnabled(sendPassResetEmailBttn), "Send password reset email button is enabled without solve the puzzle");

	}
	
	public void verifyandClickOnGitHubLogo() {
		Assert.assertTrue(isElementPresent(gitHubLogo), "Git Hub logo is not appearing on the reset password page");
		clickAndWait(gitHubLogo, shortWait);
		
	}
	

}
