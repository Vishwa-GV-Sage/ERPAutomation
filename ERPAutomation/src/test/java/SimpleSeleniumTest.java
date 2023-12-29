import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SimpleSeleniumTest {

    public static void main(String[] args) {
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\vishwanath.gv\\git\\ERPAutomation\\ERPAutomation\\driverExe\\chromedriver.exe");

        // Configure ChromeOptions if needed
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");

        // Initialize ChromeDriver
        WebDriver driver = new ChromeDriver(options);

        // Open a webpage (e.g., Google)
        driver.get("https://www.google.com");

        // Sleep for a few seconds to observe the opened browser
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Close the browser
        driver.quit();
    }
}
