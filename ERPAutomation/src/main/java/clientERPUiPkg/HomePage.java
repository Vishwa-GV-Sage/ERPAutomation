package clientERPUiPkg;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import frameworkPkg.BrowserFactory;

public class HomePage {

	WebDriver driver;

	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(how = How.XPATH, using = "//*[@id=\"main-menu\"]/span[1]")
	@CacheLookup
	WebElement applicationMenu;

	@FindBy(how = How.XPATH, using = "//span[text()='Accounts Receivable']")
	@CacheLookup
	WebElement accountReceivableMenuItem;

	@FindBy(how = How.XPATH, using = "//a[text()='Invoices']")
	@CacheLookup
	WebElement invoiceMenuItem;

	public void goToInvoiceSection() {

		By iframeLocator = By.id("iamain");
		By iframeElement = By.xpath("//*[@id=\"BApplications\"]/a[1]");
		BrowserFactory.waitForPageToLoadAndElementToBePresenetedInFrame(iframeLocator, iframeElement);
		applicationMenu.click();
		accountReceivableMenuItem.click();
		invoiceMenuItem.click();

	}

	@FindBy(how = How.XPATH, using = "//a[text()='Add' and contains(@class, 'btn btn-primary')]")
	@CacheLookup
	WebElement addinvoiceBtn;

	@FindBy(how = How.ID, using = "_obj__CUSTOMERID")
	@CacheLookup
	WebElement customerTxt;

	@FindBy(how = How.ID, using = "_obj__WHENDUE")
	@CacheLookup
	WebElement dueDateTxt;

	@FindBy(how = How.ID, using = "_obj__ITEMS_0_-_obj__ACCOUNTLABEL")
	@CacheLookup
	WebElement lineAccountLabel_1_Txt;

	@FindBy(how = How.ID, using = "_obj__ITEMS_0_-_obj__TRX_AMOUNT")
	@CacheLookup
	WebElement lineTransactionAmount_1_Txt;
	
	
	@FindBy(how = How.XPATH, using = "//button[@id='splitBtn' and @class='btn btn-primary dropdown-toggle' and @data-toggle='dropdown']")
	@CacheLookup
	WebElement postInvoiceDrpBtn;

	
	@FindBy(how = How.ID, using = "savebuttid")
	@CacheLookup
	WebElement postInvoiceBtn;
	
	
	public int createInvoice() {
		By iframeLocator = By.id("iamain");
		int upperLimit = 10000;
		Random random = new Random();
		// Generate a random integer invoice Amount
		int actualInvoiceAmount = random.nextInt(upperLimit);
		// Define the desired date format
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

		// Get the current date.
		LocalDate currentDate = LocalDate.now();

		// Format the current date as a string
		String formattedDate = currentDate.format(formatter);

		// Switch to the iframe
		WebElement iframe = driver.findElement(iframeLocator);
		driver.switchTo().frame(iframe);

		// Wait for the presence of the 'Add' button within the iframe
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		WebElement addInvoiceButtonInFrame = wait.until(ExpectedConditions.elementToBeClickable(addinvoiceBtn));

		// Click the 'Add' button within the iframe
		addinvoiceBtn.click();

		BrowserFactory.waitForPageToLoad();
		customerTxt.click();
		customerTxt.sendKeys("vishwa");

		customerTxt.sendKeys(Keys.TAB, Keys.TAB, Keys.TAB);
		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dueDateTxt.click();

		dueDateTxt.sendKeys(formattedDate);

		// Assuming 'driver' is your WebDriver instance
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// Scroll down to the bottom of the page
		js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
		// Switch back to the main content
		// driver.switchTo().defaultContent();
		lineAccountLabel_1_Txt.click();
		lineAccountLabel_1_Txt.sendKeys("Insurance", Keys.TAB);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lineTransactionAmount_1_Txt.sendKeys(String.valueOf(actualInvoiceAmount));
		postInvoiceDrpBtn.click();
		postInvoiceBtn.click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return actualInvoiceAmount;
	}
}
