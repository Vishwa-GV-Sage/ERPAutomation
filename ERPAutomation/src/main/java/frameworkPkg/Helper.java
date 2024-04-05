package frameworkPkg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.nio.file.Files;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class Helper {

	private static final Logger LOGGER = Logger.getLogger(Helper.class.getName());
	static String projectDirectory = System.getProperty("user.dir");
	public static WebDriver driver;
	BrowserFactory obj1;

	// Variables for "syncAPI" object with default values
	public static String syncAPIBaseURL;
	public static String syncAPIDataset_ID;
	public static String syncAPIJwtToken;
	public static String syncAPIversion;
	public static String zipFile;

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

			// Read and set variables for "syncAPI" object
			syncAPIBaseURL = jsonReader.getJsonValue("syncAPI.baseURL");
			syncAPIDataset_ID = jsonReader.getJsonValue("syncAPI.dataset_id");
			syncAPIJwtToken = jsonReader.getJsonValue("syncAPI.jwtToken");
			syncAPIversion = jsonReader.getJsonValue("syncAPI.version");
			zipFile = jsonReader.getJsonValue("syncAPI.zipFile");

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
		// Get the project directory

		// Set environment details as attributes

		// Specify the path to the allure-results folder within the project directory
		String allureResults = projectDirectory + "/allure-results";
		// Create a File object representing the directory
		File directory = new File(allureResults);
		// Check if the directory exists
		if (directory.exists() && directory.isDirectory()) {
			// Get all files in the directory
			File[] files = directory.listFiles();

			// Iterate over each file and delete it
			if (files != null) {
				for (File file : files) {
					if (file.isFile()) {
						// Delete the file
						file.delete();
						System.out.println("Deleted file: " + file.getName());
					}
				}
				System.out.println("All files in allure-results folder deleted successfully.");
			} else {
				System.out.println("No files found in the allure-results folder.");
			}
		} else {
			System.out.println("Specified directory does not exist or is not a directory.");
		}
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
		/*
		 * LOGGER.info("in @BeforeMethod"); Helper.driver = BrowserFactory.getDriver();
		 * 
		 * // Check if driver is null if (driver == null) { throw new
		 * IllegalStateException(
		 * "WebDriver instance is null. Check BrowserFactory.getDriver() implementation."
		 * ); }
		 * 
		 * // Set default values in case they are not set yet setDefaultValues();
		 */
	}

	@AfterMethod
	public void close() {
		// Close any resources or perform cleanup after each test method
		/* Helper.driver.close(); */
	}

	@AfterClass
	public void afterClass() {
		// Any cleanup after the test class
	}

	@AfterSuite
	public void afterSuite() {
		/*
		 * try { // Perform cleanup after the entire test suite driver.quit(); } catch
		 * (Exception e) { handleException("Error during suite cleanup.", e); }
		 */

		String environmentVariable = syncAPIBaseURL; // Or retrieve it from somewhere else

		// Determine the value for the Environment property
		String environmentValue;
		if (environmentVariable.toLowerCase().contains("dev")) {
			environmentValue = "DEV";
		} else if (environmentVariable.toLowerCase().contains("qa")) {
			environmentValue = "QA";
		} else {
			// Handle other cases if necessary
			environmentValue = "Unknown";
		}

		// Create Properties object
		Properties properties = new Properties();
		properties.setProperty("Environment", environmentValue);
		properties.setProperty("Version", syncAPIversion);
		// Write properties to file
		try {
			String filePath = projectDirectory + "/allure-results/environment.properties";
			FileOutputStream fileOut = new FileOutputStream(filePath);
			properties.store(fileOut, "Environment Properties");
			fileOut.close();
			System.out.println("environment.properties file created successfully.");
		} catch (IOException e) {
			System.err.println("Error occurred while creating environment.properties file: " + e.getMessage());
			e.printStackTrace();
		}
		allureReport();
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy_HHmm");
		String timestamp = formatter.format(new Date());
		

		// Destination file path
		String destinationPath = projectDirectory + "/Report/" + environmentValue + "_Env_" + timestamp + ".html";

		// Source and destination file objects
		File sourceFile = new File("allure-report/index.html");
		File destinationFile = new File(destinationPath);

		// Check if index.html file exists
		if (!sourceFile.exists()) {
			System.err.println("index.html file does not exist in allure-report folder.");
			return; // Exit the program if file doesn't exist
		}

		// Move the file
		try {
			Files.move(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			System.out.println("index.html file moved successfully to: " + destinationPath);
			// Execute Allure command

		} catch (IOException e) {
			System.err.println("Error occurred while moving index.html file: " + e.getMessage());
			e.printStackTrace();
		}

	}

	private static void allureReport() {

		try {
			// Path to the batch file
			String batchFilePath = projectDirectory + "/generateAllureReport.bat";

			// Create ProcessBuilder instance
			ProcessBuilder processBuilder = new ProcessBuilder(batchFilePath);

			// Set the directory where the batch file should be executed
			File directory = new File(projectDirectory);
			processBuilder.directory(directory);

			// Start the process
			Process process = processBuilder.start();

			// Wait for the process to finish
			int exitCode = process.waitFor();

			// Print the exit code
			System.out.println("Batch file execution completed with exit code: " + exitCode);

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Method to set default values for variables
	private void setDefaultValues() {

		syncAPIBaseURL = syncAPIBaseURL != null ? syncAPIBaseURL : "defaultBaseURL";
		syncAPIDataset_ID = syncAPIDataset_ID != null ? syncAPIDataset_ID : "defaultUser";
		syncAPIJwtToken = syncAPIJwtToken != null ? syncAPIJwtToken : "syncAPIJwtToken";
		syncAPIversion = syncAPIversion != null ? syncAPIversion : "syncAPIversion";
		zipFile = zipFile != null ? zipFile : "zipFile";

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
