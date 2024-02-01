package e2e;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VerifyPartialSync2 {
	
	@Test
	public void login() throws InterruptedException {
	System.setProperty("webdriver.chrome.driver", "C:\\Users\\vishwanath.gv\\git\\ERPAutomation\\ERPAutomation\\driverExe\\chromedriver.exe");
	WebDriver driver=new ChromeDriver();
	driver.manage().window().maximize();
	driver.get("https://app.dev.lockstep.io/");
	
	Thread.sleep(10000);
	
	WebElement username=driver.findElement(By.xpath("//input[@id='signInName']"));
	
	Thread.sleep(5000);
	
	WebElement password=driver.findElement(By.xpath("//input[@id='password']"));
	WebElement login=driver.findElement(By.xpath("//button[@id='next']"));
	username.sendKeys("scorpio@grr.la");
	password.sendKeys("Vishwa@123");
	login.click();
	
	Thread.sleep(10000);
	
	String actualUrl="https://app.dev.lockstep.io/";
	String expectedUrl= driver.getCurrentUrl();
	Assert.assertEquals(expectedUrl,actualUrl);
	
	System.out.println("Login Successful");
	
	Thread.sleep(10000);
	
	WebElement syncbutton=driver.findElement(By.xpath("//button[@class='MuiButtonBase-root MuiIconButton-root MuiIconButton-sizeSmall css-6r26lo']"));
	
	Thread.sleep(10000);
	
	syncbutton.click();
	
	Thread.sleep(10000);
	
	System.out.println("Sync initiated by the user");
	
	driver.close();
	
	
	}
	
	

}
