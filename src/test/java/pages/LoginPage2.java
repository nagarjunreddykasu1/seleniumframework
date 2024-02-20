package pages;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.By;

import base.ExcelReader;

public class LoginPage2 {
	public final String sheet_TestCases = "TestCases";
	public final String sheet_TestData = "TestData";
	public ExcelReader xlLogin;
	public Properties prop;
	Map<String, String> testdata;
	
	public final By username=By.name("username");
	public final By password=By.name("password");
	public final By btnLogin=By.xpath("//button[@type='submit']");
	public final By errorUserName=By.xpath("(//span[contains(@class,'oxd-input-field-error-message')])[1]");
	public final By errorPassword=By.xpath("(//span[contains(@class,'oxd-input-field-error-message')])[2]");
	public final By errorUserPassword=By.xpath("//div[contains(@class,'oxd-alert-content--error')]/p");
	
	public LoginPage2() throws IOException{
		//To load Login.xlsx file
		xlLogin=new ExcelReader(System.getProperty("user.dir") + "//src//test//java//testdata//Login.xlsx");
	}
	
	public String getTCDescription(ExcelReader xls_obj, String sheetName, String methodName) {
		int rowNum = xls_obj.getFirstDataRowNum(sheetName, "TCID", methodName);
		return xls_obj.getCellData(sheetName, "Description", rowNum);
	}
	
	public Map<String, String> getTestData(ExcelReader xls_obj, String sheetName, String testCaseID) {
		testdata = new HashMap<String, String>();
		int rowNum = xls_obj.getFirstDataRowNum(sheetName, "TCID", testCaseID);

		testdata.put("UserName", xls_obj.getCellData(sheetName, "UserName", rowNum));
		testdata.put("Password", xls_obj.getCellData(sheetName, "Password", rowNum));
		testdata.put("TestData1", xls_obj.getCellData(sheetName, "TestData1", rowNum));
		testdata.put("TestData2", xls_obj.getCellData(sheetName, "TestData2", rowNum));
		
		return testdata;
	}

}
