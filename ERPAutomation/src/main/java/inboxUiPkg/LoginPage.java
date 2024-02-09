package inboxUiPkg;

import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import frameworkPkg.BrowserFactory;
import frameworkPkg.Helper;

public class LoginPage extends Helper {

	WebDriver driver;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(how = How.NAME, using = "Email")
	@CacheLookup
	WebElement username;
	@FindBy(how = How.NAME, using = "Password")
	@CacheLookup
	WebElement password;
	@FindBy(how = How.ID, using = "next")
	@CacheLookup
	WebElement login;

	public void logintoInbox(String use, String pass) {

		BrowserFactory.waitForPageToLoadAndElementToBePreseneted(username);

		username.sendKeys(use);
		password.sendKeys(pass);
		login.click();

	}

	@FindBy(how = How.XPATH, using = "// a[text()='Authenticate']")
	@CacheLookup
	WebElement authenticateLink;

	@FindBy(how = How.ID, using = "encodedToken")
	@CacheLookup
	WebElement jwtTokenTextArea;

	public String getJwtToken() {
		ArrayList<String> initialTabs = new ArrayList<>(driver.getWindowHandles());
		int initialTabCount = driver.getWindowHandles().size();
		String jwtToken = null;
		driver.get(lockStepApiBaseURL);
		BrowserFactory.waitForPageToLoad();
		authenticateLink.click();

		BrowserFactory.waitForNewTabToOpen(initialTabCount);
		ArrayList<String> newTabs = new ArrayList<>(driver.getWindowHandles());
		newTabs.removeAll(initialTabs);

		// Switch to the new tab
		driver.switchTo().window(newTabs.get(0));
		logintoInbox(inboxUser, inboxPassword);
		BrowserFactory.waitForPageToLoadAndElementToBePreseneted(jwtTokenTextArea);
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jwtToken=jwtTokenTextArea.getAttribute("innerHTML");
		//System.out.println(jwtTokenTextArea.getText()+"Hi");
		
		// Parse the HTML string
        Document document = Jsoup.parse(jwtToken);

        // Select all the spans within the div
        Elements spans = document.select("span");

        // Extract and concatenate the text content of each span
        StringBuilder extractedText = new StringBuilder();
        for (Element span : spans) {
            extractedText.append(span.text());
            // Add a dot if the next sibling is not null
            Element nextSibling = span.nextElementSibling();
            if (nextSibling != null) {
                extractedText.append(".");
            }
        }
        
        jwtToken=extractedText.toString();

        //driver.get(lockStepApiSwaggerURL);		
		
		return jwtToken;
	}

}