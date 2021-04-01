package com.github.qa.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.github.qa.base.BasePage;

public class JoinGitHubPage extends BasePage{
	
	/** The Log. */
	private static Logger Log = LogManager.getLogger(JoinGitHubPage.class.getName());
	
	/** The Page Name*/
	private By joinGitHubPageName = By.xpath("//div[contains(text(),'Join GitHub')]");
	
	/** The Create your account text on the join GitHub page */
	private By createYourAccountTextPage = By.xpath("//h1[contains(text(),'Create your account')]");
	
	/** The Create account button */
	private By createAccountButton = By.xpath("//button[contains(text(),'Create account')]");
	
	/** The Email field*/
	private By emailField = By.id("user_email");

	public JoinGitHubPage(ThreadLocal<WebDriver> driver) {
		super(driver);
		this.driver = driver;
		Log.info("Initializing join github Page Objects");
		PageFactory.initElements(driver.get(), joinGitHubPageName);
	}
	
	/** Method to verify Join GitHub Page*/
	public void verifyJoinGitHubPage() {
		waitForElementToBecomeVisible(joinGitHubPageName, longWait);
		Assert.assertTrue(isElementPresent(joinGitHubPageName), "join GitHub page is not appeaaring after click on the sign up button");
	}
	
	/** Method to verify Create your account text on join GitHub page*/
	public void verifyCreateYourAccountTextOnJoinGitHubPage() {
		waitForElementToBecomeVisible(joinGitHubPageName, longWait);
		Assert.assertTrue(isElementPresent(createYourAccountTextPage), "Create Your Account text is not appeaaring on the join GitHub page");
	}
	
	/** Method to verify the email field and enter the email */
	public void verifyEmailFieldAndEnterEmail(String email) {
		waitForElementToBecomeVisible(emailField, longWait);
		Assert.assertTrue(isElementPresent(emailField), "Email field is not appearing on the login page");
		setText(emailField, email, shortWait);
	}
	
	/** Method to verify Create account button is disabled if user enter the existing email/ user name */
	public void verifyCreateAccountButtonIsDisabledIfUserEnterExistingEmail() {
		Assert.assertFalse(verifyIsEnabled(createAccountButton), "Create  Account button is enabled if user enter the existing email to create new account");
	}

}
