package com.github.qa.pages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.interactions.internal.BaseAction;

// TODO: Auto-generated Javadoc
/**
 * Created By : 
 */

public class PageCollection 
{
	/** The Log. */
	private static Logger Log = LogManager.getLogger(PageCollection.class.getName());

	/** The driver. */
	protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	/** The login page. */
	private PasswordResetPage passwordResetPage;
	
	private LandingPage landingPage;
	
	private LoginPage loginPage;
	
	private JoinGitHubPage joinGitHubPage;
		
	

		
	/**
	 * Instantiates a new page collection.
	 *
	 * @param driver the driver
	 */
	public PageCollection(ThreadLocal<WebDriver> driver) 
	{
		this.driver = driver;
		Log.info("Initialized Page Collection Class");
	}

	/**
	 * Gets the login page.
	 *
	 * @return the login page
	 */
	

	public PasswordResetPage getPasswordResetPage() 
	{
		return (passwordResetPage == null) ? passwordResetPage = new PasswordResetPage(driver) : passwordResetPage;
	}
	
	public LandingPage getLandingPage() 
	{
		return (landingPage == null) ? landingPage = new LandingPage(driver) : landingPage;
	}

	public LoginPage getLoginPage() 
	{
		return (loginPage == null) ? loginPage = new LoginPage(driver) : loginPage;
	}
	
	public JoinGitHubPage getJoinGitHubPage() 
	{
		return (joinGitHubPage == null) ? joinGitHubPage = new JoinGitHubPage(driver) : joinGitHubPage;
	}
}