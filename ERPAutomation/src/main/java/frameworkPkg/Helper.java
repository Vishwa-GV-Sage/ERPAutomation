package frameworkPkg;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class Helper {

	private static final Logger LOGGER = Logger.getLogger(Helper.class.getName());

	public static WebDriver driver;
	BrowserFactory obj1;

	// Variables for "inbox" object with default values
	public static String inboxBaseURL;
	public static String inboxUser;
	public static String inboxPassword;

	// Variables for "intacct" object with default values
	public static String intacctBaseURL;
	public static String intacctCompanyID;
	public static String intacctUserID;
	public static String intacctPassword;

	// Variables for "lockStepApi" object with default values
	public static String lockStepApiBaseURL;
	public static String lockStepApiSwaggerURL;

	// Variables for "invoiceAPI" object with default values
	public static String invoiceApiBaseURL;

	// Variables for "appID" object with default values
	public static String intacctAppID;
	public static String sage50AppID;

	// Default constructor
	public Helper() {
		try {
			init(getDefaultConfigFilePath());
		} catch (Exception e) {
			handleException("Error during initialization.", e);
		}
	}

	// Parameterized constructor
	public Helper(String[] args) {
		try {
			String configFile = args.length > 0 ? args[0] : "DevEnvConfig.json";
			LOGGER.info("Received configuration file path: " + configFile);
			init(getConfigFilePath(configFile));
		} catch (Exception e) {
			handleException("Error during initialization.", e);
		}
	}

	// Additional constructor to accept the configuration file path
	public Helper(String configFilePath) throws FileNotFoundException {
		init(configFilePath);
	}

	// Common initialization method
	private void init(String configFilePath) throws FileNotFoundException {
		try {
			LOGGER.info("Using configuration file: " + configFilePath);

			// Read JSON file and set global variables
			ReadEnvConfigJSON jsonReader = new ReadEnvConfigJSON(configFilePath);

			// Read and set variables for "inbox" object
			inboxBaseURL = jsonReader.getJsonValue("inbox.baseURL");
			inboxUser = jsonReader.getJsonValue("inbox.user");
			inboxPassword = jsonReader.getJsonValue("inbox.password");

			// Read and set variables for "intacct" object
			intacctBaseURL = jsonReader.getJsonValue("intacct.baseURL");
			intacctCompanyID = jsonReader.getJsonValue("intacct.companyID");
			intacctUserID = jsonReader.getJsonValue("intacct.userID");
			intacctPassword = jsonReader.getJsonValue("intacct.password");

			// Read and set variables for "lockStepApi" object
			lockStepApiBaseURL = jsonReader.getJsonValue("lockStepApi.baseURL");
			lockStepApiSwaggerURL = jsonReader.getJsonValue("lockStepApi.swaggerURL");

			// Read and set variables for "InvoiceAPI" object
			invoiceApiBaseURL = jsonReader.getJsonValue("invoiceApi.baseURL");

			// Read and set variables for "appID" object
			intacctAppID = jsonReader.getJsonValue("appID.intacct");
			sage50AppID = jsonReader.getJsonValue("appID.sage50");

			// Set default values if JSON values are null
			setDefaultValues();
		} catch (Exception e) {
			handleException("Error reading configuration file.", e);
		}
	}

	@BeforeSuite
	public void beforeSuite() {
		// Any additional setup for the suite
	}

	@BeforeClass
	public void beforeClass() {
		String configFile = System.getProperty("configFile", "DevEnvConfig.json");
		LOGGER.info("Received configuration file path: " + configFile);
		try {
			init(getConfigFilePath(configFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@BeforeMethod
	public void beforeMethodClass() {
		LOGGER.info("in @BeforeMethod");
		Helper.driver = BrowserFactory.getDriver();

		// Check if driver is null
		if (driver == null) {
			throw new IllegalStateException(
					"WebDriver instance is null. Check BrowserFactory.getDriver() implementation.");
		}

		// Set default values in case they are not set yet
		setDefaultValues();
	}

	@AfterMethod
	public void close() {
		// Close any resources or perform cleanup after each test method
		Helper.driver.close();
	}

	@AfterClass
	public void afterClass() {
		// Any cleanup after the test class
	}

	@AfterSuite
	public void afterSuite() {
		try {
			// Perform cleanup after the entire test suite
			driver.quit();
		} catch (Exception e) {
			handleException("Error during suite cleanup.", e);
		}
	}

	// Method to set default values for variables
	private void setDefaultValues() {
		inboxBaseURL = inboxBaseURL != null ? inboxBaseURL : "defaultBaseURL";
		inboxUser = inboxUser != null ? inboxUser : "defaultUser";
		inboxPassword = inboxPassword != null ? inboxPassword : "defaultPassword";
		intacctBaseURL = intacctBaseURL != null ? intacctBaseURL : "defaultBaseURL";
		intacctCompanyID = intacctCompanyID != null ? intacctCompanyID : "defaultCompanyID";
		intacctUserID = intacctUserID != null ? intacctUserID : "defaultUserID";
		intacctPassword = intacctPassword != null ? intacctPassword : "defaultPassword";
		lockStepApiBaseURL = lockStepApiBaseURL != null ? lockStepApiBaseURL : "defaultBaseURL";
		lockStepApiSwaggerURL = lockStepApiSwaggerURL != null ? lockStepApiSwaggerURL : "defaultSwaggerURL";
		invoiceApiBaseURL = invoiceApiBaseURL != null ? invoiceApiBaseURL : "defaultBaseURL";

		intacctAppID = intacctAppID != null ? intacctAppID : "defaultBaseURL";
		sage50AppID = sage50AppID != null ? sage50AppID : "defaultBaseURL";

	}

	private String getDefaultConfigFilePath() {
		return getConfigFilePath("DevEnvConfig.json");
	}

	private String getConfigFilePath(String configFile) {
		return System.getProperty("user.dir") + File.separator + "envDetails" + File.separator + configFile;
	}

	private void handleException(String message, Exception e) {
		LOGGER.log(Level.SEVERE, message, e);
		setDefaultValues(); // Set default values in case of an exception
	}
}
