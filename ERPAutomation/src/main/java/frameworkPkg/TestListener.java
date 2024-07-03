package frameworkPkg;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener{
	
	@Override
    public void onTestStart(ITestResult result) {
        System.out.println("Test case started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test case passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test case failed: " + result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test case skipped: " + result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Not used
    }

    @Override
    public void onStart(ITestContext context) {
        // Not used
    }

    @Override
    public void onFinish(ITestContext context) {
        // Not used
    }

}
