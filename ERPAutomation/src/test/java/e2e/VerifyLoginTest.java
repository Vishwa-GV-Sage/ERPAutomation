package e2e;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import frameworkPkg.*;
import inboxUiPkg.LoginPage;
import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;

public class VerifyLoginTest extends Helper {
	
	public VerifyLoginTest(){}
		
		
  @Test
	public void verifyloginInbox() {
	try {
		
		driver.get(inboxBaseURL);
		Thread.sleep(10000);
		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
		loginPage.logintoInbox(inboxUser, inboxPassword);
		Thread.sleep(10000);
		
		System.out.println("Login to Inbox is Successful and verified");
	}
	catch (Exception e) {e.printStackTrace();}
	}
  
  
}

