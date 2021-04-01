package com.github.qa.uitests;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.github.qa.base.MyScreenRecorder;
import com.relevantcodes.extentreports.LogStatus;

public class GitHubTest extends BaseTest {

	/** The Log. */
	private static Logger Log = LogManager.getLogger(GitHubTest.class.getName());

	/**
	 * GitHub pages verification Flow.
	 * Verify To open the application by entering valid URL
	 * Verify that by clicking on "Sign in" button user is redirected to login page
	 * Verify that user name and password fields are mandatory in login page 
	 * Verify Forgot Password link and click on it 
	 * Verify Reset password page after click on the Forgot Password link
	 * Verify Email field on password reset page and enter invalid email 
	 * Verify Send Password Reset Email button is disabled if user do not solve the puzzle
	 * Verify GitHub Logo on the password reset page and click on it to go back on the landing page
	 * Verify Sign up button on landing page and click on it
	 * Verify Join GitHub page and text after click on the sign up button
	 * Verify Create Your Account text on the join GitHub page
	 * "Verify Email field on join GitHub page and check to enter existing email
	 * Verify create account button is disabled if user enter an existing email or if not solve the puzzle
	 */
	@Test(description = "To verify Open the GitHub application and start verifcaton for Sign in, Sign up and Password reset page")
	public void VerifySignIn_ResetPassword_SignupTestForGitHUbApp() throws Exception {

		test = extent.startTest("Sign In, Sign Up , Reset Password Test on GitHub Application");
		test.log(LogStatus.INFO,
				"Open the GitHub application and start verificaton for Sign in, Sign up and Password reset page");

		MyScreenRecorder.startRecording("VerifySignIn_ResetPassword_SignupTestForGitHUb");
		
		pages.getLandingPage().enterValidURL_ToOpenTheApplication();
		test.log(LogStatus.INFO, "Verify To open the application by entering valid URL");
		
		pages.getLandingPage().verifyAndClickOnTheSignInButton();
		test.log(LogStatus.INFO, "Verify Sign in button and click on it");

		pages.getLoginPage().verifyLoginPage("login");
		test.log(LogStatus.INFO, "Verify login page after click on the sign in button");
		
		pages.getLoginPage().verifyUserNameFieldAndEnterUserName("");
		test.log(LogStatus.INFO, "Verify user name field and enter username if want");
		
		pages.getLoginPage().verifyPasswordFieldAndEnterPassword("");
		test.log(LogStatus.INFO, "Verify password field and enter password if want");
		
		pages.getLoginPage().verifyAndClickOnTheSignInButton();
		test.log(LogStatus.INFO, "Verify Sign in button and click on it without enter the username and password");
		
		pages.getLoginPage().verifyValidationMsgForUserNameAndPasswordField("Incorrect username or password");
		test.log(LogStatus.INFO, "Verify validation message when user trying to sign in without entring the username and password");
		
		pages.getLoginPage().verifyForgotPasswordLinkAndClickOnIt();
		test.log(LogStatus.INFO, "Verify forgot password link and click on it");
		
		pages.getPasswordResetPage().verifyEmailFieldAndEnterEmail("@yopmail.com");
		test.log(LogStatus.INFO, "Verify email field on reset password page and enter email to reset password");
		
		pages.getPasswordResetPage().verifySendPassResetEmailBttnIsDisabled();
		test.log(LogStatus.INFO, "Verify Send Password Reset Email button is disabled if user do not solve the puzzle");
		
		pages.getPasswordResetPage().verifyandClickOnGitHubLogo();
		test.log(LogStatus.INFO, "Verify GitHub Logo on the password reset page and click on it to go back on the landing page");
		
		pages.getLandingPage().verifyAndClickOnTheSignUpButton();
		test.log(LogStatus.INFO, "Verify Sign up button and click on it");
		
		pages.getJoinGitHubPage().verifyJoinGitHubPage();
		test.log(LogStatus.INFO, "Verify Join GitHub page and text after click on the sign up button");
		
		pages.getJoinGitHubPage().verifyCreateYourAccountTextOnJoinGitHubPage();
		test.log(LogStatus.INFO, "Verify Create Your Account text on the join GitHub page");
		
		pages.getJoinGitHubPage().verifyEmailFieldAndEnterEmail("nischaltyagi33@gmail.com");
		test.log(LogStatus.INFO, "Verify Email field on join GitHub page and check to enter existing email");
		
		pages.getJoinGitHubPage().verifyCreateAccountButtonIsDisabledIfUserEnterExistingEmail();
		test.log(LogStatus.INFO, "Verify create account button is disabled if user enter an existing email or if not solve the puzzle");
		
		test.log(LogStatus.PASS, "Sign In, Sign Up , Reset Password pages element and validation Test on GitHub Application");

		MyScreenRecorder.stopRecording();
	}

}
