package base;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Reusable {
	public static WebDriver driver;

	public static void launchApp(String browser, String url){
		if(browser.equalsIgnoreCase("firefox")){
			driver = new FirefoxDriver();
		}else if(browser.equalsIgnoreCase("chrome")){
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("ie")) {
			InternetExplorerOptions options = new InternetExplorerOptions();
			options.setCapability("ignoreProtectedModeSettings", true);
			options.setCapability("disable-popup-blocking", true);
			options.setCapability("ignoreZoomSetting", true);
			driver = new InternetExplorerDriver(options);
		}else if (browser.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
		}
		driver.manage().window().maximize();
		driver.get(url);
	}

	public static WebDriver getDriver(){
		return driver;
	}
	
	public static String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	public static void sendKeys(By locator,String value){
		driver.findElement(locator).sendKeys(value);
	}
	
	public static void sendKeys(String locator,String value){
		getWebElement(locator).sendKeys(value);
	}
	public static void sendKeys(WebElement element,String value){
		element.sendKeys(value);
	}

	public static void click(By locator){
		driver.findElement(locator).click();
	}
	
	public static void click(WebElement element){
		element.click();
	}
	
	public static void click(String locator){
		getWebElement(locator).click();
	}

	public static void clear(By locator){
		driver.findElement(locator).clear();
	}
	public static void clear(WebElement element){
		element.clear();
	}
	

	public static String getText(By locator){
		return driver.findElement(locator).getText();
	}
	
	public static String getText(WebElement element){
		return element.getText();
	}
	

	public static void scroll(WebElement element){
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView();", element);
	}

	public static WebElement getWebElement(String element) {
		WebElement el = null;
		String locatorType = element.split(":")[0];
		String locatorValue = element.split(":")[1];
		switch (locatorType) {
		case "id":
			el = driver.findElement(By.id(locatorValue));
			break;
		case "name":
			el = driver.findElement(By.name(locatorValue));
			break;
		case "css":
			el = driver.findElement(By.cssSelector(locatorValue));
			break;
		case "tag":
			el = driver.findElement(By.tagName(locatorValue));
			break;
		case "link":
			el = driver.findElement(By.linkText(locatorValue));
			break;
		case "partial":
			el = driver.findElement(By.partialLinkText(locatorValue));
			break;
		case "xpath":
			el = driver.findElement(By.xpath(locatorValue));
			break;
		}
		return el;
	}

	public static List<WebElement> getWebElements(String element) {
		List<WebElement> list = null;
		String locatorType = element.split(":")[0];
		String locatorValue = element.split(":")[1];
		switch (locatorType) {
		case "id":
			list = driver.findElements(By.id(locatorValue));
			break;
		case "name":
			list = driver.findElements(By.name(locatorValue));
			break;
		case "css":
			list = driver.findElements(By.cssSelector(locatorValue));
			break;
		case "tag":
			list = driver.findElements(By.tagName(locatorValue));
			break;
		case "link":
			list = driver.findElements(By.linkText(locatorValue));
			break;
		case "partial":
			list = driver.findElements(By.partialLinkText(locatorValue));
			break;
		case "xpath":
			list = driver.findElements(By.xpath(locatorValue));
			break;
		}
		return list;
	}

	public static WebElement getWebElement(By element) {
		return driver.findElement(element);
	}

	public static List<WebElement> getWebElements(By element) {
		return driver.findElements(element);
	}

	public static boolean isChecked(String element) {
		return getWebElement(element).isSelected();
	}

	public static void selectDropdownValue(String element, String value) {
		Select ddl = new Select(getWebElement(element));
		ddl.selectByValue(value);
	}

	public static String getSelectedValue(String element) {
		return new Select(getWebElement(element)).getFirstSelectedOption().getText();
	}

	public static ArrayList<String> getDropdownValues(String element) {
		ArrayList<String> ddlvalues = new ArrayList<String>();
		List<WebElement> list = new Select(getWebElement(element)).getOptions();
		for (WebElement el : list) {
			ddlvalues.add(el.getText().trim());
		}
		return ddlvalues;
	}

	public static ArrayList<String> getDropdownValues(By element) {
		ArrayList<String> ddlvalues = new ArrayList<String>();
		List<WebElement> list = new Select(driver.findElement(element)).getOptions();
		for (WebElement el : list) {
			ddlvalues.add(el.getText().trim());
		}
		return ddlvalues;
	}

	public static String getText(String element) {
		return getWebElement(element).getText();
	}

	public static String getTitle() {
		return driver.getTitle();
	}

	public static void clear(String element) {
		getWebElement(element).clear();
	}

	public static void delay(int seconds) throws InterruptedException {
		Thread.sleep(seconds * 1000);
	}

	public static void compareMessages(String actual, String expected) {
		Assert.assertEquals(actual, expected);
	}

	public static boolean isVisible(String element) {
		return getWebElement(element).isDisplayed();
	}

	public static void waitUptoVisibilityOfElement(String element, int seconds) {
		WebDriverWait wait = new WebDriverWait(getDriver(),Duration.ofSeconds(seconds));
		String locatorType = element.split(":")[0];
		String locatorValue = element.split(":")[1];
		switch (locatorType) {
		case "id":
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
		case "link":
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(locatorValue)));
			break;
		case "xpath":
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
		}
	}

	public static boolean waitUptoVisibilityOfElement(By element, int seconds) {
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(seconds));
		wait.until(ExpectedConditions.visibilityOfElementLocated(element));
		return true;
	}
	
	public static boolean waitUptoVisibilityOfElement(WebElement element, int seconds) {
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(seconds));
		wait.until(ExpectedConditions.visibilityOf(element));
		return true;
	}

	public static void waitForWebelement(String element, int timeUnitsInSeconds) {
		new WebDriverWait(getDriver(), Duration.ofSeconds(timeUnitsInSeconds))
		.until(ExpectedConditions.visibilityOf(getWebElement(element)));
	}

	public String getAttributeValue(String element, String attributename) {
		return getWebElement(element).getAttribute(attributename);
	}

	// To generate screenshot name
	public static String getScreenshotName(String methodName) {
		String fileName = methodName + "_"+ LocalDateTime.now().toString().replace("-", "").replace(":", "").replace(".", "") + ".png";
		return fileName;
	}

	public static String takeScreenshot(String methodName) throws IOException, InterruptedException{
		Thread.sleep(2000);
		TakesScreenshot ts=(TakesScreenshot)getDriver();
		File source=ts.getScreenshotAs(OutputType.FILE);
		String dest=System.getProperty("user.dir")+"//screenshots//"+getScreenshotName(methodName);
		File destination=new File(dest);
		FileUtils.copyFile(source,destination);
		return dest;
	}

	public static void uploadRobot(String filePath) throws AWTException{
		StringSelection sel=new StringSelection(filePath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel, null);	
		Robot robot=new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
	}


	public static ArrayList<WebElement> isDisplayed(ArrayList<WebElement> al){
		ArrayList<WebElement> undisplayed=new ArrayList<>();
		Iterator<WebElement> itr=al.iterator();
		while(itr.hasNext()){
			WebElement element=itr.next();
			if(element.isDisplayed()){
				System.out.println(element+" is displayed.");
			}
			else{
				undisplayed.add(element);
				System.out.println(element+" is not displayed.");
			}
		}

		return undisplayed;

	}

	public static ArrayList<String> compareArrayLists(ArrayList<String> expected,ArrayList<String> actual){
		Iterator<String> itr=expected.iterator();
		while(itr.hasNext()){
			String value=itr.next();
			if(actual.remove(value)){
				itr.remove();
			}
		}
		ArrayList<String> unmatched=new ArrayList<>();
		unmatched.addAll(expected);
		unmatched.addAll(actual);
		return unmatched;

	}

	public static void wait(By locator){
		WebDriverWait wait=new WebDriverWait(getDriver(),Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public static void flunetWait(final By locator){
		Wait<WebDriver> w=new FluentWait<WebDriver>(getDriver()).withTimeout(Duration.ofSeconds(30)).
				pollingEvery(Duration.ofSeconds(3)).ignoring(NoSuchElementException.class);

		w.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public static String getMethodName() {
		return new Exception().getStackTrace()[1].getMethodName();
	}

	public static boolean isSkip(ExcelReader excel, String sheetName, String testcaseID) {
		boolean isSkip = false;
		int rowNumber = excel.getFirstDataRowNum(sheetName, "TCID", testcaseID);
		String runMode = excel.getCellData(sheetName, "Run", rowNumber);
		if (runMode.contentEquals("No") || runMode.isEmpty()) {
			isSkip = true;
		} else {
			isSkip = false;
		}
		return isSkip;
	}

	public static void setResultInExcel(ExcelReader excel, String sheetName, String testcaseID, String result)throws IOException {
		excel.setCellData(sheetName, testcaseID, "Result", result);
	}
	
	public static Alert switchToAlert(){
		return driver.switchTo().alert();
	}
	
	public static String getAlertMessage(){
		return switchToAlert().getText();
	}
	
	public static void clickOKInAlert(){
		switchToAlert().accept();
	}
	
	public static void clickCancelInAlert(){
		switchToAlert().dismiss();
	}
	
	public static void switchToFrame(String idOrName){
		driver.switchTo().frame(idOrName);
	}
	
	public static void switchToFrame(WebElement element){
		driver.switchTo().frame(element);
	}
	
	public static void switchToWindow(String handle){
		driver.switchTo().window(handle);
	}
	
	public static void switchToChildWindow(String parentId){
		Set<String> handles=driver.getWindowHandles();
		Iterator<String>itr=handles.iterator();
		while(itr.hasNext()){
			String childId=itr.next();
			if(!parentId.equals(childId)){
				driver.switchTo().window(childId);
				break;
			}
		}
	}
	
	public static void mouseHover(By element) {
		new Actions(driver).moveToElement(driver.findElement(element)).perform();
	}
	
	public static void close(){
		driver.close();
	}
	
	public static void quit(){
		driver.quit();
	}

}
