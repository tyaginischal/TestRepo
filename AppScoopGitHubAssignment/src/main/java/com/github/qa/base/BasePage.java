package com.github.qa.base;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.BaseAction;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.github.qa.utils.TestUtils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// TODO: Auto-generated Javadoc
/**
 * Created By : Nischal Tyagi.
 */

public class BasePage {

	/** The Log. */
	private static Logger Log = LogManager.getLogger(BasePage.class.getName());

	/** The driver. */
	protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	/** The wait. */
	protected Wait<WebDriver> wait;

	/** The minimal wait. */
	protected Wait<WebDriver> minimalWait;

	/** The short wait. */
	protected Wait<WebDriver> shortWait;

	/** The long wait. */
	protected Wait<WebDriver> longWait;

	/** The no wait. */
	protected Wait<WebDriver> noWait;


	/** The actions. */

	/**
	 * Instantiates a new base page.
	 *
	 * @param driver the driver
	 */
	// Constructor
	public BasePage(ThreadLocal<WebDriver> driver) {
		this.driver = driver;
		Log.info("Initialized Base Page Class");

		wait = new WebDriverWait(driver.get(), TestUtils.getPropertyAsInt("selenium.default.wait"));
		minimalWait = new WebDriverWait(driver.get(), TestUtils.getPropertyAsInt("selenium.minimal.wait"));
		shortWait = new WebDriverWait(driver.get(), TestUtils.getPropertyAsInt("selenium.short.wait"));
		longWait = new WebDriverWait(driver.get(), TestUtils.getPropertyAsInt("selenium.long.wait"));
		noWait = new FluentWait<>(driver.get()).withTimeout(Duration.of(200, ChronoUnit.MILLIS));

	}

	/**
	 * Goto URL.
	 *
	 * @param url the url
	 */
	// open url method
	public void gotoURL(String url) {
		driver.get().get(url);
	}

	/**
	 * Click.
	 *
	 * @param locator      the locator
	 * @param waitSupplied the wait supplied
	 */
	// Click Method
	@SafeVarargs
	public final void clickAndWait(By locator, Wait<WebDriver>... waitSupplied) {
		Wait<WebDriver> tempWait = TestUtils.getFirstValueOr(waitSupplied, wait);
		WebElement el = tempWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		tempWait.until(ExpectedConditions.elementToBeClickable(el));
		el.click();
	}
	
	
	public final void ClickandPressDelete(By locator)
	{
		WebElement Field=driver.get().findElement(locator);
		Field.sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
		
	}

	/**
	 * Sets the text.
	 *
	 * @param locator      the locator
	 * @param text         the text
	 * @param waitSupplied the wait supplied
	 */
	// Write Text
	@SafeVarargs
	public final void setText(By locator, String text, Wait<WebDriver>... waitSupplied) {
		Wait<WebDriver> tempWait = TestUtils.getFirstValueOr(waitSupplied, wait);
		WebElement el = tempWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		//WebElement el = tempWait.until(ExpectedConditions.elementToBeClickable(locator));
		el.clear();
		el.sendKeys(text);
	}

	@SafeVarargs
	public final void setTextD(By locator, String text, Wait<WebDriver>... waitSupplied) {
		Wait<WebDriver> tempWait = TestUtils.getFirstValueOr(waitSupplied, wait);
		WebElement el = tempWait.until(ExpectedConditions.elementToBeClickable(locator));
		el.clear();
		el.sendKeys(text);
	}

	@SafeVarargs
	public final boolean isSelected(By locator, Wait<WebDriver>... waitSupplied) {
		Wait<WebDriver> tempWait = TestUtils.getFirstValueOr(waitSupplied, wait);
		WebElement el = tempWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		return el.isSelected();

	}

	public final boolean verifyIsEnabled(By locator, Wait<WebDriver>... waitSupplied) {
		Wait<WebDriver> tempWait = TestUtils.getFirstValueOr(waitSupplied, wait);
		WebElement el = tempWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		return el.isEnabled();

	}

	public final String getAttributeValue(By el, String attr) {
		return driver.get().findElement(el).getAttribute(attr);
	}

	/**
	 * Upload file.
	 *
	 * @param locator      the locator
	 * @param filepath     the filepath
	 * @param waitSupplied the wait supplied
	 */
	@SafeVarargs
	public final void uploadFile(By locator, String filepath, Wait<WebDriver>... waitSupplied) {
		Wait<WebDriver> tempWait = TestUtils.getFirstValueOr(waitSupplied, wait);
		WebElement el = tempWait.until(ExpectedConditions.presenceOfElementLocated(locator));
		el.sendKeys(filepath);
	}

	/**
	 * Sets the random text.
	 *
	 * @param elementLocation the element location
	 * @param text            the text
	 */
	// Write Random Text
	public void setRandomText(By elementLocation, String text) {
		driver.get().findElement(elementLocation).clear();
		driver.get().findElement(elementLocation).sendKeys(text + " " + TestUtils.getUniqueString(text));
	}

	/**
	 * Gets the text.
	 *
	 * @param elementLocation the element location
	 * @return the text
	 */
	// Read Text
	public String getText(By elementLocation) {
		getElementIfVisibleUsingXpath(elementLocation, longWait);
		return driver.get().findElement(elementLocation).getText();
	}

	/**
	 * Movetoelement.
	 *
	 * @param elementLocation the element location
	 */
	public void movetoelement(By elementLocation) {

		new Actions(driver.get()).moveToElement(driver.get().findElement(elementLocation)).build().perform();

	}

	@SuppressWarnings("unchecked")
	public boolean verifyTitle(By locator, String title, Wait<WebDriver>... waitSupplied) {
		boolean status = false;

		new Actions(driver.get()).moveToElement(driver.get().findElement(locator)).perform();
		Wait<WebDriver> tempWait = TestUtils.getFirstValueOr(waitSupplied, wait);
		WebElement el = tempWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		;
		String expTitle = el.getAttribute("title");
		String actTitle = title;
		if (expTitle.equals(actTitle))
			;
		{
			status = true;
		}
		return status;
	}

	/**
	 * Movetoelement.
	 *
	 * @param elementLocation the element location
	 */
	public void movetoelement(WebElement elementLocation) {

		new Actions(driver.get()).moveToElement((elementLocation)).build().perform();

	}

	/**
	 * Refresh page.
	 */
	public void refreshPage() {

		driver.get().navigate().refresh();

	}

	/**
	 * Scroll down to element.
	 *
	 * @param locator the locator
	 */
	public void scrollDownToElement(WebElement locator) {
		Point el = locator.getLocation();
		int x = el.getX();
		int y = el.getY();
		JavascriptExecutor js = (JavascriptExecutor) driver.get();
		js.executeScript("window.scrollTo(" + x + "," + y + ")");

	}

	public void Click(By locator) {
		driver.get().findElement(locator).click();
	}
	
	
	
	public void moveToAnElementByJavascript(By locator)
	{
		JavascriptExecutor js = (JavascriptExecutor) driver.get();

		
		js.executeScript("arguments[0].scrollIntoView();", locator);
		
		
		
		
	}

	/**
	 * Scrol to element.
	 *
	 * @param locator the locator
	 */
	public void scrolToElement(WebElement locator) {
		JavascriptExecutor js = (JavascriptExecutor) driver.get();
		js.executeScript("arguments[0].scrollIntoView();", locator);

	}

	public void scrollToElement(By locator) {
		WebElement el = driver.get().findElement(locator);
		JavascriptExecutor js = (JavascriptExecutor) driver.get();
		js.executeScript("arguments[0].scrollIntoView();", el);

	}

	/**
	 * Wait for frame to be present.
	 *
	 * @param frameid      the frameid
	 * @param waitSupplied the wait supplied
	 */
	@SafeVarargs
	public final void waitForFrameToBePresent(String frameid, Wait<WebDriver>... waitSupplied) {
		Wait<WebDriver> tempWait = TestUtils.getFirstValueOr(waitSupplied, wait);
		tempWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameid));

	}
	
	public final void switchToDefaulFrame() {
		driver.get().switchTo().defaultContent();

	}
	
	@SafeVarargs
	public final void waitForFrameToBePresent1(By ele, Wait<WebDriver>... waitSupplied) {
		Wait<WebDriver> tempWait = TestUtils.getFirstValueOr(waitSupplied, wait);
		tempWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(ele));

	}


	/**
	 * Gets the element if visible using xpath.
	 *
	 * @param locator      the xpath
	 * @param waitSupplied the wait supplied
	 * @return the element if visible using xpath
	 */
	@SafeVarargs
	protected final boolean getElementIfVisibleUsingXpath(By locator, Wait<WebDriver>... waitSupplied) {
		try {
			Wait<WebDriver> tempWait = TestUtils.getFirstValueOr(waitSupplied, wait);
			WebElement el = tempWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			return el.isDisplayed();
		} catch (Exception e) {
			return false;
		}
		// return true;
	}

	/**
	 * Wait for element to be clickable.
	 *
	 * @param locator      the locator
	 * @param waitSupplied the wait supplied
	 */
	@SafeVarargs
	public final void waitForElementToBeClickable(By locator, Wait<WebDriver>... waitSupplied) {
		Wait<WebDriver> tempwait = TestUtils.getFirstValueOr(waitSupplied, wait);

		// WebdriverWait wait = new WebdriverWait(driver.get(), 40);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tempwait.until(ExpectedConditions.elementToBeClickable(driver.get().findElement(locator)));
	}

	public final void javascriptButtonClick(By webElement) {
		WebElement el = driver.get().findElement(webElement);
		JavascriptExecutor js = (JavascriptExecutor) driver.get();
		js.executeScript("arguments[0].click();", el);
	}

	/**
	 * Wait forloading overlay to become invisible.
	 *
	 * @param waitSupplied the wait supplied
	 */
	@SafeVarargs
	public final void waitForloadingOverlayToBecomeInvisible(Wait<WebDriver>... waitSupplied) {
		Wait<WebDriver> tempWait = TestUtils.getFirstValueOr(waitSupplied, wait);
		tempWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='pageLoader']")));
	}

	/**
	 * Wait for element to become invisible.
	 *
	 * @param locator      the locator
	 * @param waitSupplied the wait supplied
	 */
	@SafeVarargs
	public final void waitForElementToBecomeInvisible(By locator, Wait<WebDriver>... waitSupplied) {
		try {
			Wait<WebDriver> tempWait = TestUtils.getFirstValueOr(waitSupplied, wait);
			tempWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Switch to new window.
	 */
	public void switchToNewWindow() {
		String parentWindow = driver.get().getWindowHandle();
		Set<String> handles = driver.get().getWindowHandles();
		for (String windowHandle : handles) {
			if (!windowHandle.equals(parentWindow)) {
				driver.get().switchTo().window(windowHandle);
			}

		}
	}

	/**
	 * Check if popup window closed.
	 *
	 * @return true, if successful
	 */
	public boolean checkIfPopupWindowClosed() {
		for (int i = 0; i < 10; i++) {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			}
			Set<String> wHandle = driver.get().getWindowHandles();
			if (wHandle.size() < 2) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Wait till text present.
	 *
	 * @param text         the text
	 * @param waitSupplied the wait supplied
	 */
	@SafeVarargs
	public final void waitTillTextPresent(String text, Wait<WebDriver>... waitSupplied) {

		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {

			public Boolean apply() {
				return driver.get().findElement(By.tagName("body")).getText().contains(text);
			}

			@Override
			public @Nullable Boolean apply(@Nullable WebDriver input) {
				// TODO Auto-generated method stub
				return apply();
			}
		};
		Wait<WebDriver> tempwait = TestUtils.getFirstValueOr(waitSupplied, wait);
		tempwait.until(expectation);
	}

	/**
	 * Select by part of visible text.
	 *
	 * @param locator     the locator
	 * @param partialText the partial text
	 */
	public void selectByPartOfVisibleText(By locator, String partialText) {
		List<WebElement> optionElements = driver.get().findElement(locator).findElements(By.tagName("option"));

		for (WebElement optionElement : optionElements) {
			if (optionElement.getText().contains(partialText)) {
				String optionvalue = optionElement.getAttribute("value");
				new Select(driver.get().findElement(locator)).selectByValue(optionvalue);
				;
				break;
			}
		}
	}

	/**
	 * Select by complete visible text.
	 *
	 * @param locator             the locator
	 * @param completeVisibleText
	 */
	public void selectByCompleteVisibleText(By locator, String partialText) {
		List<WebElement> optionElements = driver.get().findElement(locator).findElements(By.tagName("option"));

		for (WebElement optionElement : optionElements) {
			if (optionElement.getText().equals(partialText)) {
				String optionvalue = optionElement.getAttribute("value");
				new Select(driver.get().findElement(locator)).selectByValue(optionvalue);
				;
				break;
			}
		}
	}

	/**
	 * Handle alert.
	 */
	public void handleAlert() {
		if (isAlertPresent()) {
			Alert alert = driver.get().switchTo().alert();
			System.out.println("Alert Text is" + alert.getText());
			alert.accept();
		}
	}

	/**
	 * Checks if is alert present.
	 *
	 * @return True if JavaScript Alert is present on the page otherwise false
	 */
	public boolean isAlertPresent() {
		try {
			driver.get().switchTo().alert();
			return true;
		} catch (NoAlertPresentException ex) {
			return false;
		}
	}

	/**
	 * Wait till alert present.
	 *
	 * @param waitSupplied the wait supplied
	 */
	@SafeVarargs
	public final void waitTillAlertPresent(Wait<WebDriver>... waitSupplied) {
		try {

			Wait<WebDriver> tempWait = TestUtils.getFirstValueOr(waitSupplied, wait);
			tempWait.until(ExpectedConditions.alertIsPresent());

			Alert alert = driver.get().switchTo().alert();
			alert.accept();

		} catch (NoAlertPresentException noAlert) {
			noAlert.getMessage();
		}
	}

	/**
	 * Press enter.
	 */
	public final void pressEnter() {
		Actions action = new Actions(driver.get());

		action.sendKeys(Keys.ENTER).build().perform();
	}
	
	public final void pressDownKey() {
		Actions action = new Actions(driver.get());

		action.sendKeys(Keys.DOWN).build().perform();
		}

	/**
	 * Javascript button click.
	 *
	 * @param webElement the web element
	 */
	public final void javascriptButtonClick(WebElement webElement) {
		JavascriptExecutor js = (JavascriptExecutor) driver.get();
		js.executeScript("arguments[0].click();", webElement);
	}

	/**
	 * Switch tab.
	 *
	 * @param i the i
	 */
	public final void switchTab(int i) {
		ArrayList<String> tabs = new ArrayList<>(driver.get().getWindowHandles());
		driver.get().switchTo().window(tabs.get(i));
	}

	/**
	 * get Number of Tab Open.
	 *
	 * @param i the i
	 */
	public final int getNumberOfTab() {
		ArrayList<String> tabs = new ArrayList<>(driver.get().getWindowHandles());
		return tabs.size();
	}

	public Boolean isElementPresent(By el) {
		// waitForElement(element);
		// return element.isDisplayed();
		Boolean result = false;
		WebElement element = driver.get().findElement(el);
		try {
		
			if (element.isDisplayed()) {
				result = true;
			}

		} catch (Exception ex) {
		}
		return result;
	}
	
	

	/**
	 * Switch tab and close.
	 *
	 * @param i the i
	 */
	public final void switchTabAndClose(int i) {
		ArrayList<String> tabs = new ArrayList<>(driver.get().getWindowHandles());
		driver.get().switchTo().window(tabs.get(i));
		closeDriver();
	}

	/**
	 * Gets the current window name.
	 *
	 * @return the current window name
	 */
	public final String getCurrentWindowName() {
		return driver.get().getWindowHandle();
	}

	/**
	 * Switch to window by name.
	 *
	 * @param windowName the window name
	 */
	public final void switchToWindowByName(String windowName) {
		driver.get().switchTo().window(windowName);
	}

	/**
	 * Switch window and close.
	 *
	 * @param windowName the window name
	 */
	public final void switchWindowAndClose(String windowName) {
		driver.get().switchTo().window(windowName);
		closeDriver();
	}

	/**
	 * Gets the total tabs.
	 *
	 * @return the total tabs
	 */
	public final int getTotalTabs() {
		ArrayList<String> tabs = new ArrayList<>(driver.get().getWindowHandles());
		return tabs.size();
	}

	/**
	 * Gets the elements if present.
	 *
	 * @param webelement the webelement
	 * @return the elements if present
	 */
	public final List<WebElement> getElementsIfPresent(By webelement) {
		if (getElementIfVisibleUsingXpath(webelement)) {
			List<WebElement> allOptions = driver.get().findElements(webelement);
			return allOptions;
		} else {
			return null;
		}
	}

	/**
	 * Gets the element if present.
	 *
	 * @param webelement the webelement
	 * @return the element if present
	 */
	public final WebElement getElementIfPresent(By webelement) {
		if (getElementIfVisibleUsingXpath(webelement)) {
			return driver.get().findElement(webelement);
		} else {
			return null;
		}
	}

	/**
	 * Gets the element if present.
	 *
	 * @param webelement the webelement
	 * @return the element if present
	 */
	public final WebElement getParentElement(By webelement) {
		WebElement myElement = driver.get().findElement(webelement);
		WebElement parent = myElement.findElement(By.xpath("./.."));
		return parent;
	}

	/**
	 * Gets the element count if present.
	 *
	 * @param webelement the webelement
	 * @return the element count if present
	 */
	public final int getElementCountIfPresent(By webelement) {
		if (getElementIfVisibleUsingXpath(webelement)) {
			List<WebElement> allOptions = driver.get().findElements(webelement);
			// Log.info("Size is "+allOptions.size());
			return allOptions.size();
		} else {
			return 0;
		}
	}

	/**
	 * Close driver.
	 */
	public final void closeDriver() {
		driver.get().close();
	}

	/**
	 * Navigate back.
	 */
	public final void navigateBack() {
		driver.get().navigate().back();
	}

	/**
	 * Gets the current url.
	 *
	 * @return the current url
	 */
	public final String getCurrentUrl() {
		return driver.get().getCurrentUrl();
	}

	public boolean isTextPresentOnPage(String text) {
		return driver.get().findElement(By.tagName("body")).getText().contains(text);
	}

	public void waitForFileDownload(String filepath, String expectedFileName) {
		File file = new File(filepath + "/" + expectedFileName);
		while (!file.exists()) {
			TestUtils.sleep(2);
		}

	}

	public String getScriptTagText(By el) {
		WebElement element = driver.get().findElement((el));
		String htmlCode = (String) ((JavascriptExecutor) driver.get()).executeScript("return arguments[0].innerHTML;",
				element);
		// TestUtils.getSubstring(htmlCode, startingchar, endchar)
		return htmlCode;
	}

	public JSONObject convertStringToJson(String str) throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(str);
		Log.info(json);
		return json;

	}

	public HashMap<String, String> unionOfTwoMap(HashMap<String, String> map1, HashMap<String, String> map2)
			throws ParseException {
		map2.forEach((key, value) -> map1.merge(key, value, (v1, v2) -> v1.equalsIgnoreCase(v2) ? v1 : v1 + "," + v2));
		Log.info(map1);
		return map1;
	}

	/**
	 * Basic functions for SM2 view
	 */

	public String giveAttribute(By locator, String NameofAttribute) {

		WebElement el = driver.get().findElement(locator);
		String att = el.getAttribute(NameofAttribute);

		return att;
	}

	@SafeVarargs
	public final void waitForElementToBecomeVisible(By locator, Wait<WebDriver>... waitSupplied) {
		try {
			Wait<WebDriver> tempWait = TestUtils.getFirstValueOr(waitSupplied, wait);
			tempWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public final void javascriptButtonDoubleClick(WebElement webElement) {
		((JavascriptExecutor) driver.get()).executeScript("var evt = document.createEvent('MouseEvents');"
				+ "evt.initMouseEvent('dblclick',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
				+ "arguments[0].dispatchEvent(evt);", webElement);
	}

	public final void javascriptMouseHover(WebElement webElement) {
		// WebElement el = driver.get().findElement(webElement);
		String javaScript = "var evObj = document.createEvent('MouseEvents');"
				+ "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
				+ "arguments[0].dispatchEvent(evObj);";

		((JavascriptExecutor) driver.get()).executeScript(javaScript, webElement);

//		((JavascriptExecutor) driver.get()).executeScript("var evt = document.createEvent('MouseEvents');"+ 
//			    "evt.initMouseEvent('mouseover',true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"+ 
//			    "arguments[0].dispatchEvent(evt);", webElement);
	}

	@SafeVarargs
	public final void setTextBY(WebElement locator, String text, Wait<WebDriver>... waitSupplied) {
		Wait<WebDriver> tempWait = TestUtils.getFirstValueOr(waitSupplied, wait);
		WebElement el = tempWait.until(ExpectedConditions.visibilityOf(locator));
		el.clear();
		el.sendKeys(text);
	}

	public void verify(boolean actual, boolean expected, String descripton) {
		Assert.assertEquals(actual, expected, descripton);
	}

	public String getAttribute(By locator, String nameOfAttribute) {
		WebElement el = driver.get().findElement(locator);
		return el.getAttribute(nameOfAttribute);
	}

	public void waitForPageLoaded() {

		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		Wait<WebDriver> wait = new WebDriverWait(driver.get(), 20);
		wait.until(expectation);
	}

	public static String randomString() {

		Random r = new Random();
		List<Integer> id = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			int x = r.nextInt(9999);
			while (id.contains(x))
				x = r.nextInt(9999);
			id.add(x);
		}
		String emailId = String.format("sm2" + id.get(0) + "view");
		return emailId;
	}
	
	public String randomNumber() {

		Random r = new Random();
		List<Integer> id = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			int x = r.nextInt(9999);
			while (id.contains(x))
				x = r.nextInt(9999);
			id.add(x);
		}
		String rId = String.format("%04d", id.get(0));
		return rId;
	}
	
	public String randomid() {

		Random r = new Random();
		List<Integer> id = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			int x = r.nextInt(9999);
			while (id.contains(x))
				x = r.nextInt(9999);
			id.add(x);
		}
		String rId = String.format("%04d", id.get(0));
		return rId;
	}

	public boolean verifyTooltip(By locator, String title) {
		boolean status = false;

		new Actions(driver.get()).moveToElement(driver.get().findElement(locator)).perform();
		String expTitle = getAttribute(locator, "title");
		Log.info("Title is-- " + expTitle);
		String actTitle = title;
		if (expTitle.equals(actTitle)) {
			status = true;
		}
		return status;
	}
	
	
	public Point getLocation(By elementLocation) {
		getElementIfVisibleUsingXpath(elementLocation, longWait);
		return driver.get().findElement(elementLocation).getLocation();
	}

}