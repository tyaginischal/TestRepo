package com.github.qa.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.github.qa.base.BasePage;
import com.github.qa.utils.TestUtils;

public class LoginPage extends BasePage {

	/** The Log.*/
	private static Logger Log = LogManager.getLogger(LoginPage.class.getName());

	/** The user name field*/
	private By userNameField = By.id("login_field");
	
	/** The password field*/
	private By passwordField = By.id("password");
	
	/** The sign in button*/
	private By signInBttn = By.xpath("//input[@value='Sign in']");
	
	/** The user name and password validation message*/
	private By userNameAndPasswordValidationMsg = By.id("js-flash-container");
	
	/** The forgot password link*/
	private By forgotPasswordLink = By.xpath("//a[contains(text(),'Forgot password?')]");

	// initialize elements
	public LoginPage(ThreadLocal<WebDriver> driver) {
		super(driver);
		this.driver = driver;
		Log.info("Initializing landing Page Objects");
		PageFactory.initElements(driver.get(), userNameField);
	}
	
	/** Method to Login Page*/
	public void verifyLoginPage(String pageName) {
		waitForElementToBecomeVisible(userNameField, longWait);
		String url= getCurrentUrl();
		Assert.assertTrue(url.contains(pageName), "Login page is not appeaaring after click on the sign in button");
	}

	/** Method to verify the user name field and enter the user name*/
	public void verifyUserNameFieldAndEnterUserName(String userName) {
		waitForElementToBecomeVisible(userNameField, longWait);
		Assert.assertTrue(isElementPresent(userNameField), "User name field is not appearing on the login page");
	}
	
	/** Method to verify the password field and enter the password*/
	public void verifyPasswordFieldAndEnterPassword(String password) {
		waitForElementToBecomeVisible(userNameField, longWait);
		Assert.assertTrue(isElementPresent(userNameField), "Password field is not appearing on the login page");
	}

	
	/** Method to verify and click on the sign in button */
	public void verifyAndClickOnTheSignInButton() {
		waitForElementToBecomeVisible(signInBttn, longWait);
		Assert.assertTrue(isElementPresent(signInBttn), "Sign in button is not appearing on the login page");
		clickAndWait(signInBttn, longWait);
	}
	
	/** Method to verify validation on blank username and password*/
	public void verifyValidationMsgForUserNameAndPasswordField(String expValidationMsg) {
		waitForElementToBecomeVisible(signInBttn, longWait);
		Assert.assertTrue(isElementPresent(signInBttn), "Sign in button is not appearing on the login page");
		clickAndWait(signInBttn, longWait);
		waitForElementToBecomeVisible(userNameAndPasswordValidationMsg, shortWait);
		String actValidationMsg= getText(userNameAndPasswordValidationMsg);
		Assert.assertTrue(actValidationMsg.contains(expValidationMsg), "Validation message is not appearing if user not entring the user name and password");
	}
	
	/** Method to verify forgot password link and click on it */
	public void verifyForgotPasswordLinkAndClickOnIt() {
		waitForElementToBecomeVisible(forgotPasswordLink, shortWait);
		Assert.assertTrue(isElementPresent(forgotPasswordLink), "Forgot password link is not appearing on the login page");
		clickAndWait(forgotPasswordLink, longWait);
	}
	
	

}
