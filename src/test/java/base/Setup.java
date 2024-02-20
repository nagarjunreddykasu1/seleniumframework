package base;

import java.time.LocalDateTime;

import org.apache.log4j.xml.DOMConfigurator;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Setup {
	
	public static ExtentSparkReporter reporter;
	public static ExtentReports reports;
	public static ExtentTest extentTest;
	
	public static void initialize(){
		//To Load log4j.xml file
		DOMConfigurator.configure("log4j.xml");
		
		//Extent Report configuration
		reporter=new ExtentSparkReporter(System.getProperty("user.dir")+"//reports//ExtentReport_"+LocalDateTime.now().toString().replace("-", "").replace(":", "").replace(".", "")+".html");
		reporter.config().setDocumentTitle("Automation Report");
		reporter.config().setReportName("Automation Test Results");
		reporter.config().setTheme(Theme.STANDARD);
		reports=new ExtentReports();
		reports.setSystemInfo("Selenium Version", "4.17.0");
		reports.setSystemInfo("Project Name", "OrangeHRM");
		reports.setSystemInfo("Author Name", "Nagarjun Reddy");
		reports.attachReporter(reporter);
	}

}
