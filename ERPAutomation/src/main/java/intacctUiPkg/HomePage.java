package intacctUiPkg;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import frameworkPkg.BrowserFactory;

public class HomePage {

	WebDriver driver;

	public HomePage(WebDriver driver) {
		this.driver = driver;
	}
	
	@FindBy(how = How.ID, using = "btn-entity")
	@CacheLookup
	WebElement topLevelBtn;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"iaentity\"]/div/ul/li[1]/div[2]/input")
	@CacheLookup
	WebElement searchEntityTxt;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"iaentity\"]/div/ul/li[5]/ul/li[33]/a[2]")
	@CacheLookup
	WebElement selectEntity;
	
	
	public void selectEntity(String entity )
	{
		BrowserFactory.waitForPageToLoadAndElementToBePreseneted(topLevelBtn);
		topLevelBtn.click();
		searchEntityTxt.click();
		searchEntityTxt.sendKeys(entity);
		selectEntity.click();
		
	}
}
