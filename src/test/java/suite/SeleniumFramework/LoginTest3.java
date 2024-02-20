package suite.SeleniumFramework;

import java.io.IOException;
import java.util.Map;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import base.Log;
import base.Reusable;
import base.Setup;
import pages.LoginPage3;

public class LoginTest3 {
	String sheet_TestCases;
	String sheet_TestData;
	String className;
	LoginPage3 loginPage;


	@BeforeClass
	@Parameters({"browser", "url" })
	public void beforeClass(String browser, String url) throws IOException {
		className = this.getClass().getSimpleName();
		Reusable.launchApp(browser, url);
		loginPage=new LoginPage3(Reusable.getDriver());
		Reusable.waitUptoVisibilityOfElement(loginPage.btnLogin, 60);
		sheet_TestCases = loginPage.sheet_TestCases;
		sheet_TestData = loginPage.sheet_TestData;
	}

	//Verify the page title in Login page
	@Test
	public void TC_Login_01() throws Exception{
		String testcaseID=Reusable.getMethodName();
		boolean isSkip = false;
		try{
			isSkip=Reusable.isSkip(loginPage.xlLogin, sheet_TestCases, testcaseID);
			if (isSkip) {
				throw new SkipException("Skip this Test Case");
			}
			String description=loginPage.getTCDescription(loginPage.xlLogin, sheet_TestCases, testcaseID);
			Setup.extentTest=Setup.reports.createTest(description);
			Setup.extentTest.assignCategory(className);
			int rowNum=loginPage.xlLogin.getRowNum(sheet_TestData, testcaseID);
			String expectedTitle=loginPage.xlLogin.getCellData(sheet_TestData, "TestData1", rowNum);
			String actualTitle=Reusable.getTitle();
			//Assert.assertEquals(expectedTitle, actualTitle);
			if(actualTitle.equals(expectedTitle)){
				System.out.println("Actual and Expected Page titles are Equal...");
				Log.info("Actual and Expected Page titles are Equal...");
				loginPage.xlLogin.setCellData(sheet_TestCases, testcaseID, "Result", "PASS");
			}
			else{
				Log.error("Actual and Expected Page titles are NOT Equal");
				loginPage.xlLogin.setCellData(sheet_TestCases, testcaseID, "Result", "FAIL");
				throw new Exception("Actual and Expected Page titles are NOT Equal");
			}

		}
		catch(Exception e){
			if(isSkip){
				loginPage.xlLogin.setCellData(sheet_TestCases, testcaseID, "Result", "SKIP");
			}
			throw e;
		}
	}

	//Verify the validation messages when clicking on Sign In button without entering user name and password
	@Test
	public void TC_Login_02() throws Exception{
		String testcaseID=Reusable.getMethodName();
		boolean isSkip = false;
		Map<String,String> testdata;
		try{
			isSkip = Reusable.isSkip(loginPage.xlLogin, sheet_TestCases, testcaseID);
			if (isSkip) {
				throw new SkipException("Skip this Test Case");
			}
			String description=loginPage.getTCDescription(loginPage.xlLogin, sheet_TestCases, testcaseID);
			Setup.extentTest=Setup.reports.createTest(description);
			Setup.extentTest.assignCategory(className);
			Reusable.click(loginPage.btnLogin);
			String actualErrUserName=Reusable.getText(loginPage.errorUserName);
			String actualErrPassword=Reusable.getText(loginPage.errorPassword);

			testdata=loginPage.getTestData(loginPage.xlLogin, sheet_TestData, testcaseID);
			String expectedErrUserName=testdata.get("TestData1");
			String expectedErrPassword=testdata.get("TestData2");

			Assert.assertEquals(actualErrUserName, expectedErrUserName);
			Assert.assertEquals(actualErrPassword, expectedErrPassword);
			Reusable.setResultInExcel(loginPage.xlLogin, sheet_TestCases, testcaseID, "PASS");
		}
		catch(Exception e){
			if(isSkip){
				Reusable.setResultInExcel(loginPage.xlLogin, sheet_TestCases, testcaseID, "SKIP");
			}
			else{
				Reusable.setResultInExcel(loginPage.xlLogin, sheet_TestCases, testcaseID, "FAIL");
			}

			throw e;
		}
	}

	//Verify the validation message when clicking on Sign In button without entering valid user name
	@Test
	public void TC_Login_03() throws Exception{
		String testcaseID=Reusable.getMethodName();
		boolean isSkip = false;
		Map<String,String> testdata;
		try{
			isSkip = Reusable.isSkip(loginPage.xlLogin, sheet_TestCases, testcaseID);
			if (isSkip) {
				throw new SkipException("Skip this Test Case");
			}
			String description=loginPage.getTCDescription(loginPage.xlLogin, sheet_TestCases, testcaseID);
			Setup.extentTest=Setup.reports.createTest(description);
			Setup.extentTest.assignCategory(className);
			testdata=loginPage.getTestData(loginPage.xlLogin, sheet_TestData, testcaseID);


			String username=testdata.get("UserName");
			String password=testdata.get("Password");

			Reusable.sendKeys(loginPage.username,username);
			Reusable.sendKeys(loginPage.password,password);
			Reusable.click(loginPage.btnLogin);
			Reusable.delay(3);
			String expectedMsg=testdata.get("TestData1");
			Assert.assertEquals(Reusable.getText(loginPage.errorUserPassword),expectedMsg);
			Reusable.setResultInExcel(loginPage.xlLogin, sheet_TestCases, testcaseID, "PASS");
		}
		catch(Exception e){
			if(isSkip){
				Reusable.setResultInExcel(loginPage.xlLogin, sheet_TestCases, testcaseID, "SKIP");
			}
			else{
				Reusable.setResultInExcel(loginPage.xlLogin, sheet_TestCases, testcaseID, "FAIL");
			}
			throw e;
		}

	}
	//Verify the validation message when clicking on Sign In button without entering valid password
	@Test
	public void TC_Login_04() throws Exception{
		String testcaseID=Reusable.getMethodName();
		boolean isSkip = false;
		Map<String,String> testdata;
		try{
			isSkip = Reusable.isSkip(loginPage.xlLogin, sheet_TestCases, testcaseID);
			if (isSkip) {
				throw new SkipException("Skip this Test Case");
			}
			String description=loginPage.getTCDescription(loginPage.xlLogin, sheet_TestCases, testcaseID);
			Setup.extentTest=Setup.reports.createTest(description);
			Setup.extentTest.assignCategory(className);

			testdata=loginPage.getTestData(loginPage.xlLogin, sheet_TestData, testcaseID);

			Reusable.clear(loginPage.username);

			String username=testdata.get("UserName");
			String password=testdata.get("Password");

			Reusable.sendKeys(loginPage.username,username);
			Reusable.sendKeys(loginPage.password,password);
			Reusable.click(loginPage.btnLogin);
			Reusable.delay(3);
			String expectedMsg=testdata.get("TestData1");
			Assert.assertEquals(Reusable.getText(loginPage.errorUserPassword),expectedMsg);
			Reusable.setResultInExcel(loginPage.xlLogin, sheet_TestCases, testcaseID, "PASS");

		}
		catch(Exception e){
			if(isSkip){
				Reusable.setResultInExcel(loginPage.xlLogin, sheet_TestCases, testcaseID, "SKIP");
			}
			else{
				Reusable.setResultInExcel(loginPage.xlLogin, sheet_TestCases, testcaseID, "FAIL");
			}
			throw e;
		}

	}
	
	@Test
	public void TC_Login_05() throws Exception{
		String testcaseID=Reusable.getMethodName();
		boolean isSkip = false;
		Map<String,String> testdata;
		try{
			isSkip = Reusable.isSkip(loginPage.xlLogin, sheet_TestCases, testcaseID);
			if (isSkip) {
				throw new SkipException("Skip this Test Case");
			}
			String description=loginPage.getTCDescription(loginPage.xlLogin, sheet_TestCases, testcaseID);
			Setup.extentTest=Setup.reports.createTest(description);
			Setup.extentTest.assignCategory(className);

			testdata=loginPage.getTestData(loginPage.xlLogin, sheet_TestData, testcaseID);

			String username=testdata.get("UserName");
			String password=testdata.get("Password");

			Reusable.sendKeys(loginPage.username,username);
			Reusable.sendKeys(loginPage.password,password);
			Reusable.click(loginPage.btnLogin);
			Reusable.delay(3);
			String currentUrl=Reusable.getCurrentUrl();
			if(currentUrl.contains("dashboard")) {
				System.out.println("User logged in and redirected to dashboard page.");
			}
			else {
				System.out.println("User not logged in..");
				throw new Exception("User is not logged in. Please check the login details.");
			}
			Reusable.setResultInExcel(loginPage.xlLogin, sheet_TestCases, testcaseID, "PASS");

		}
		catch(Exception e){
			if(isSkip){
				Reusable.setResultInExcel(loginPage.xlLogin, sheet_TestCases, testcaseID, "SKIP");
			}
			else{
				Reusable.setResultInExcel(loginPage.xlLogin, sheet_TestCases, testcaseID, "FAIL");
			}
			throw e;
		}

	}
	
	/*
	 * @AfterClass public void afterClass(){ Reusable.close(); }
	 */

}
