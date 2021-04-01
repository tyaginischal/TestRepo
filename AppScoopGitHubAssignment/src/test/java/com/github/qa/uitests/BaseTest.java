package com.github.qa.uitests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.github.qa.actions.ApiActions;
import com.github.qa.actions.BaseActions;
import com.github.qa.actions.UIActions;
import com.github.qa.base.MyScreenRecorder;
import com.github.qa.pages.PageCollection;
import com.github.qa.utils.TestUtils;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest 
{
	/** The Log. */
	private static Logger Log = LogManager.getLogger(BaseTest.class.getName());
	
	protected static String downloadFolder = System.getProperty("user.dir") + "/" + "resources" + "/" + "downloadedFiles";


	/** The driver. */
	// Use the application driver
	protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	/** The pages. */
	PageCollection pages;

	/** The web actions. */
	UIActions uiActions;

	/** The actions. */
	BaseActions actions = new BaseActions();

	/** The api actions. */
	ApiActions apiActions = new ApiActions();

	/**
	 * Instantiates a new base test.
	 */

	public BaseTest() 
	{
		Log.info("Getting WebDriver Settings");
	}

	/**
	 * Creates the chrome driver.
	 */
	private void createChromeDriver() 
	{
		try {
			Log.info("Found Chrome as Browser");
			// set browser preferences to ignore browser notification pop up and to add
			// console errors
			
			WebDriverManager.chromedriver().setup();
			Map<String, Object> prefs = new HashMap<>();
			prefs.put("profile.default_content_setting_values.notifications", 2);
			prefs.put("download.default_directory", downloadFolder);
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", prefs);

			Log.info("Getting Chromedriver exe from resources");

			System.setProperty("webdriver.chrome.driver", 
					System.getProperty("user.dir") + "/" + "resources" + "/" + "chromedriver.exe");

			Log.info("Launching Chrome and Maximizing it");
			driver.set(new ChromeDriver(options));

			driver.get().manage().window().maximize();

		   } catch (Exception e) 
		      {
			   Log.info("unable to create chromedriver");
			   e.printStackTrace();
		      }
	}

	/**
	 * Driversetup.
	 */
	@BeforeMethod(alwaysRun = true)
	public void driversetup() {
		String browser = TestUtils.getProperty("selenium.browser");

		switch (browser) 
		{
		 case "chrome":
			createChromeDriver();
			break;

		 default:
			Log.info("browser not supported");
			break;
		}
	}

	/**
	 * Initialize pages.
	 */
	@BeforeMethod(alwaysRun = true)
	public void initializePages() {
		pages = new PageCollection(driver);
		uiActions = new UIActions(driver);
	}

	/**
	 * Teardown.
	 *
	 * @param result the result
	 */
		public static ExtentReports extent;
		private static final String BREAK_LINE = "</br>";
		public static ExtentTest test;

		@BeforeSuite
		public void before() {
			extent = new ExtentReports("target/surefire-reports/ExtentReport.html", true);
		}


		@AfterMethod(alwaysRun = true)
		public void afterMainMethod(ITestResult result) throws Exception
		{
			if (result.getStatus() == ITestResult.FAILURE) 
			{
				captureScreenshot(result);
				String methodname= result.getMethod().getMethodName();
				String classname= getClass().getName();
				System.out.println("CLASSNME: "+classname);
				String summary=	"ERROR IN: "+classname+ ":: "+methodname+ ":: "+result.getThrowable().getMessage().split("\\r?\\n")[0];
				System.out.println(summary);
				//createWorkItemInTFS(summary);
				test.log(LogStatus.FAIL, summary);
				driver.get().quit();
			//	test.log(LogStatus.FAIL, result.getThrowable().getMessage());
				extent.endTest(test);
				extent.flush();	
				MyScreenRecorder.stopRecording();
			}
			
			else{
				//driver.quit();
				driver.get().quit();
				//test.log(LogStatus.FAIL, result.getThrowable().getMessage());
				extent.endTest(test);
				extent.flush();
				
			    }
		}	
			
		@AfterSuite
		public void tearDownSuite() {
			//reporter.endReport();
			//driver.quit();
			extent.flush();
			extent.close();
		}
		
		public void captureScreenshot(ITestResult result) throws IOException, InterruptedException {
			try {
				String screenshotName = TestUtils.getFileName(result.getName());
				File screenshot = ((TakesScreenshot) driver.get()).getScreenshotAs(OutputType.FILE);
				String path = TestUtils.getPath();
				String screen = path + "/screenshots/" + screenshotName + ".png";
				File screenshotLocation = new File(screen);
				FileUtils.copyFile(screenshot, screenshotLocation);
				Thread.sleep(2000);
				String image= test.addScreenCapture(screen);
				test.log(LogStatus.FAIL, screenshotName, image);

				Reporter.log(
						"<a href= '" + screen + "'target='_blank' ><img src='" + screen + "'>" + screenshotName + "</a>");
			} catch (Exception e) {
				System.out.println(e.getStackTrace());
			}
		}      		
}           