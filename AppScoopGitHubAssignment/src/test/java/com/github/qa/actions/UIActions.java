package com.github.qa.actions;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.ITestResult;

import com.github.qa.utils.TestUtils;

/**
 * The Class BaseActions.
 *
 * @author Nischal Tyagi
 */
public class UIActions {

	private static Logger Log = LogManager.getLogger(UIActions.class.getName());

	/** The driver. */
	protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	/**
	 * Instantiates a new web actions.
	 *
	 * @param driver the driver
	 */
	public UIActions(ThreadLocal<WebDriver> driver) {

		this.driver = driver;

	}

	/**
	 * Go to url.
	 *
	 * @param url the url
	 */
	public void goToUrl(String url) {
		driver.get().get(url);
	}

	/**
	 * Gets the current url.
	 *
	 * @return the current url
	 */
	public String getCurrentUrl() {
		return driver.get().getCurrentUrl();
	}

	public void captureScreenShotInCaseOfFailure(ITestResult result) {
		Log.info("Capturing Screenshots...");

		String screenShotFolder = TestUtils.getProperty("selenium.screenshotFolder");
		File screenShotSourceFile = ((TakesScreenshot) driver.get()).getScreenshotAs(OutputType.FILE);
		try {
			createFolder(screenShotFolder);
			String fileName = result.getMethod().getMethodName() + "-" + TestUtils.getCurrentDateTime();
			File screenShotTargetFile = getTargetFile(screenShotFolder, fileName, "png");
			Log.info(screenShotTargetFile);
			FileUtils.copyFile(screenShotSourceFile, screenShotTargetFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void createFolder(String folderName) {
		try {
			if (!(new File(folderName).exists())) {
				new File(folderName).mkdir();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	protected File getTargetFile(String folderName, String fileName, String ext) {
		String fullPath = null;
		try {
			String rootPath = new File(".").getCanonicalPath();
			fullPath = String.format("%s//%s//%s.%s", rootPath, folderName, fileName, ext);

		} catch (Exception e) {
			                   e.printStackTrace();
		                      }
		return new File(fullPath);
	}
	

	public String getTargetFilePath(String folderName, String fileName, String ext) throws IOException {
		String rootPath = new File(".").getCanonicalPath();
		String fullPath = String.format("%s//%s//%s.%s", rootPath, folderName, fileName, ext);
		return fullPath;
	}

	public void printconsoleerrors() 
	{
		Log.info("Printing browser console error messags...");
		try {
			LogEntries logEntries = driver.get().manage().logs().get(LogType.BROWSER);
			if (logEntries != null) 
			  {
				for (LogEntry logEntry : logEntries) 
				{
					if (logEntry.getMessage().toLowerCase().contains("error") || logEntry.getLevel().equals("ERROR")) 
					{
						Log.info(("Error Messages in Console:" + logEntry.getTimestamp() + logEntry.getMessage()));
					}
				}
			  }
		   } catch (Exception e) 
		      {
			   Log.info("Could not generate browser console error logs");
		      }
	}

}