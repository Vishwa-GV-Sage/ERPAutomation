package e2e;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import frameworkPkg.Helper;
import inboxUiPkg.CreditMemoPage;
import inboxUiPkg.HomePage;
import inboxUiPkg.LoginPage;

public class VerfiyCreditMemoSync extends Helper {
	
	public VerfiyCreditMemoSync() {}
	

	@Test(priority=1)
	public void verifyloginInbox() {
		
		try {
			driver.get(inboxBaseURL);
			Thread.sleep(1000);
			LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
			loginPage.logintoInbox(inboxUser, inboxPassword);
			Thread.sleep(5000);
			
			System.out.println("Login to Inbox is successful via Vishwa Credentials");
			//System.out.println("Sync is initiated by the user");
			Thread.sleep(5000);
		}
		
		catch(Exception e) {e.printStackTrace();}
		}
	
	@Test(priority=2)
	public void initiateFullSync() {
		
		try {
			
			HomePage homePage = PageFactory.initElements(driver, HomePage.class);
			homePage.initiateSync();
			
			System.out.println("Full Sync Initiated Successfully");
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@Test(priority=3)
	public void creditMemoSync() {
		try {
			CreditMemoPage creditmemoPage = PageFactory.initElements(driver, CreditMemoPage.class);
			creditmemoPage.creditMemoSync();
			
			System.out.println("Credit Memo Sync is successful");
			
			}
		
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
