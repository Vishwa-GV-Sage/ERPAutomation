package frameworkPkg;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BrowserFactory {

	public static WebDriver driver;
	public static String driverFileLocation = System.getProperty("user.dir") + "\\driverExe\\";

	public BrowserFactory() {

	}

	public static WebDriver getDriver() {
		if (driver == null) {
			ChromeDriverUpdater chromeDriverUpdater = new ChromeDriverUpdater();
			try {
				chromeDriverUpdater.checkChromeDriver();
			} catch (NumberFormatException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}

			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			options.setPageLoadStrategy(PageLoadStrategy.NONE);
			options.setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe"); // Set the correct path

			// System.out.println(driverFileLocation + "chromedriver.exe");
			// System.setProperty("webdriver.chrome.driver", driverFileLocation +
			// "chromedriver.exe");
			// System.out.println("System property: webdriver.chrome.driver = " +
			// System.getProperty("webdriver.chrome.driver"));
			// Try to initialize the ChromeDriver
			try {
				driver = new ChromeDriver(options);
				driver.manage().window().maximize();
				driver.manage().deleteAllCookies();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
			} catch (Exception e) {
				// Handle the exception (print the stack trace, log the error, etc.)
				e.printStackTrace();
			}
		}
		return driver;
	}

	public static WebDriver getDriver(String browserName) {
		if (driver == null) {
			if (browserName.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver", "D:Softwaresjarsgeckodriver-v0.23.0-win64geckodriver.exe");
				driver = new FirefoxDriver();
				driver.manage().window().maximize();
				driver.manage().deleteAllCookies();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
			} else if (browserName.equalsIgnoreCase("chrome")) {
				System.out.println("in chrome");
				System.setProperty("webdriver.chrome.driver", "D:chromedriver.exe");
				driver = new ChromeDriver();
				driver.manage().window().maximize();
				driver.manage().deleteAllCookies();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
			} else if (browserName.equalsIgnoreCase("IE")) {
				System.setProperty("webdriver.ie.driver",
						"D:SoftwaresjarsIEDriverServer_Win32_3.14.0IEDriverServer.exe");
				driver = new InternetExplorerDriver();
				driver.manage().window().maximize();
				driver.manage().deleteAllCookies();
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
			}
		}
		return driver;
	}

	public static void waitForPageToLoad() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
	}

	public static void waitForPageToLoadAndElementToBePreseneted(WebElement element) {
	    ExpectedCondition<Boolean> jsLoadCondition = driver ->
	            ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	    wait.until(jsLoadCondition);

	    // Additionally, wait for the presence of the element to ensure the page is fully loaded
	    wait.until(ExpectedConditions.visibilityOf(element));
	}


	public static void waitForNewTabToOpen(int initialTabCount) {
	    ExpectedCondition<Boolean> newTabCondition = webDriver -> webDriver.getWindowHandles().size() > initialTabCount;

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	    wait.until(newTabCondition);

	    // Switch to the new tab
	    ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
	    driver.switchTo().window(tabs.get(tabs.size() - 1));

	    // Additionally, wait for the page in the new tab to be fully loaded
	    waitForPageToLoad();
	}


	public static void waitForPageToLoadAndElementToBePresenetedInFrame(By frameLocator, By elementLocator) {
	    ExpectedCondition<Boolean> jsLoadCondition = driver ->
	            ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	    wait.until(jsLoadCondition);

	    // Switch to the iframe
	    WebElement iframe = driver.findElement(frameLocator);
	    driver.switchTo().frame(iframe);

	    // Wait for the presence of the element to ensure the iframe is fully loaded
	    wait.until(ExpectedConditions.presenceOfElementLocated(elementLocator));

	    // Switch back to the main content
	    driver.switchTo().defaultContent();
	}

}
