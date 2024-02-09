package e2e;

import java.util.ArrayList;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import frameworkPkg.*;
import inboxUiPkg.LoginPage;

public class SanityTest extends Helper {

	public SanityTest() {
	}

	@Test
	public void verify_Invoice_Sync_Functionality() {
		try {

			
			  driver.get(intacctBaseURL); int initialTabCount =
			  driver.getWindowHandles().size(); BrowserFactory.waitForPageToLoad();
			  
			  intacctUiPkg.LoginPage intacctloginPage = PageFactory.initElements(driver,
			  intacctUiPkg.LoginPage.class);
			  intacctloginPage.logintoIntacct(intacctCompanyID, intacctUserID,
			  intacctPassword);
			  
			  intacctUiPkg.HomePage intacctHomePage = PageFactory.initElements(driver,
			  intacctUiPkg.HomePage.class); // Switch to the new tab 
			  ArrayList<String>
			  initialTabs = new ArrayList<>(driver.getWindowHandles());
			  intacctHomePage.selectEntity("vishwa"); 
			  // Thread.sleep(7000); // Wait for  the new tab to be opened 
			  BrowserFactory.waitForNewTabToOpen(initialTabCount);
			  ArrayList<String> newTabs = new ArrayList<>(driver.getWindowHandles());
			  newTabs.removeAll(initialTabs);
			  
			  // Switch to the new tab driver.switchTo().window(newTabs.get(0));
			  
			  clientERPUiPkg.HomePage clientERPHomePage = PageFactory.initElements(driver,
			  clientERPUiPkg.HomePage.class); clientERPHomePage.goToInvoiceSection(); int
			  expectedInvoiceAmount = clientERPHomePage.createInvoice();
			  System.out.println(expectedInvoiceAmount);
			 

			inboxUiPkg.LoginPage inboxloginPage = PageFactory.initElements(driver, inboxUiPkg.LoginPage.class);
			String jwtToken = inboxloginPage.getJwtToken();
			//System.out.println(jwtToken);
			restAPIPkg.InvoiceAPI invoiceApiObj = PageFactory.initElements(driver, restAPIPkg.InvoiceAPI.class);
			//int expectedInvoiceAmount=500;
			invoiceApiObj.checkIntacctInvoiceSyncStatus(jwtToken, expectedInvoiceAmount);
			System.out.println("Invouce Sync");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}