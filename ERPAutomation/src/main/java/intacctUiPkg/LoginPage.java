package intacctUiPkg;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LoginPage {

	WebDriver driver;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(how = How.ID, using = "company")
	@CacheLookup
	WebElement companyTxt;
	@FindBy(how = How.ID, using = "login")
	@CacheLookup
	WebElement usernameTxt;
	@FindBy(how = How.ID, using = "passwd")
	@CacheLookup
	WebElement password;
	@FindBy(how = How.ID, using = "retbutton")
	@CacheLookup
	WebElement loginBtn;

	public void logintoIntacct(String company, String use, String pass) {
		
			companyTxt.sendKeys(company);
			usernameTxt.sendKeys(use);
			password.sendKeys(pass);
			loginBtn.click();
		
	}

}
