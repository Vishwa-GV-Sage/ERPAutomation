package inboxUiPkg;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class CompanyPage {
	
	WebDriver driver;
	
	public CompanyPage(WebDriver driver){
	this.driver=driver;
	}
	
	
	@FindBy(xpath="//p[text()='Total 0 Customers']")
	@CacheLookup
	WebElement companySync;
	
	public void companySync() {
		
		try {
			String s1 = companySync.getText();
			Thread.sleep(5000);
			
			String s2 = "Total customers greather than ZERO";
			
			
			Assert.assertNotEquals(s1, s2);
			
			
		}
		
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	

}
