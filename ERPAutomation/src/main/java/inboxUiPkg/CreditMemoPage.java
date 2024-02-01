package inboxUiPkg;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class CreditMemoPage {
	
	WebDriver driver;
	
	public CreditMemoPage(WebDriver driver){
	this.driver=driver;
	}
	
	
	@FindBy(xpath="//p[text()='Total 0 Invoices']")
	@CacheLookup
	WebElement creditMemoSync;
	
	public void creditMemoSync() {
		
		try {
			String s1 = creditMemoSync.getText();
			Thread.sleep(5000);
			
			String s2 = "Total Credit Memos greather than ZERO";
			
			Assert.assertNotEquals(s1, s2);
					
		}
		
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	

}
