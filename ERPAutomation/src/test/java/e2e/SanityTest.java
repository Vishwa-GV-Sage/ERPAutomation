package e2e;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import frameworkPkg.*;
import inboxUiPkg.LoginPage;

public class SanityTest extends Helper {
	
	public SanityTest(){}
		
		
  @Test
	public void verifyloginInbox() {
	try {
		
		driver.get(inboxBaseURL);
		Thread.sleep(10000);
		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
		loginPage.logintoInbox(inboxUser, inboxPassword);
		Thread.sleep(10000);
	}
	catch (Exception e) {e.printStackTrace();}
	}
}
