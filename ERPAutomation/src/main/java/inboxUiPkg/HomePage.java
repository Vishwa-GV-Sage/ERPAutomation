package inboxUiPkg;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	
	WebDriver driver;
	
	public HomePage(WebDriver driver){
	this.driver=driver;
	}
	
	
	@FindBy(xpath="//button[@class='MuiButtonBase-root MuiIconButton-root MuiIconButton-sizeSmall css-6r26lo']")
	@CacheLookup
	WebElement syncButton;
	
	public void initiateSync() {
		
		try {
			syncButton.click();
			Thread.sleep(10000);
		}
		
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	

}
