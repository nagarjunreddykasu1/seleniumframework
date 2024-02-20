package base;

import java.io.IOException;
import java.util.Arrays;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class Listener implements ITestListener{
	
	@Override
	public void onStart(ITestContext context) {
		Setup.initialize();
	}

	@Override
	public void onFinish(ITestContext context) {
		Setup.reports.flush();
		Reusable.quit();
	}
	
	@Override
	public void onTestStart(ITestResult it) {
		System.out.println(it.getMethod().getMethodName() + ": Testcase has been started.");
		Log.info(it.getMethod().getMethodName() + ": Testcase has been started.");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String methodName=result.getMethod().getMethodName();
		String logText="<b>Test Method "+methodName+" Successfull</b>";
		Markup markup =MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		Setup.extentTest.log(Status.PASS, markup);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String methodName=result.getMethod().getMethodName();
		String exceptionMessage=Arrays.toString(result.getThrowable().getStackTrace());
		Setup.extentTest.fail("<details><summary><b><font color=red>Excepion Occurred. Click here to see details.</font></b></summary>"+
				exceptionMessage.replaceAll(",", "<br>")+"</details> \n");

		String path = null;
		try{
			path = Reusable.takeScreenshot(methodName);
		}catch (IOException | InterruptedException e1) {
			e1.printStackTrace();
		}
		try{
			Setup.extentTest.fail("<b><font color=red>"+"Screenshot of Failure"+"</font></b>",MediaEntityBuilder.createScreenCaptureFromPath(path).build());
		}
		catch(Exception e){
			Setup.extentTest.fail("Test Failed and Couldn't attach screenshot...");
		}
		String logText="<b>Test Method "+methodName+" Failed</b>";
		Markup markup =MarkupHelper.createLabel(logText, ExtentColor.RED);
		Setup.extentTest.log(Status.FAIL, markup);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		String methodName=result.getMethod().getMethodName();
		System.out.println(result.getMethod().getMethodName() + ": Testcase has been skipped.");
		String logText="<b>Test Method "+methodName+" Skipped</b>";
		Markup markup =MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
		Setup.extentTest.log(Status.SKIP, markup);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	

}
