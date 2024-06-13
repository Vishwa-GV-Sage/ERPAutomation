package frameworkPkg;

import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CustomTestNGReporter implements IReporter, ITestListener {

	private int totalTests = 0;
	private int passedTests = 0;
	private int failedTests = 0;
	private int skippedTests = 0;

	@Override
	public void onTestStart(ITestResult result) {
		// Do nothing
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		passedTests++;
	}

	@Override
	public void onTestFailure(ITestResult result) {
		failedTests++;
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		skippedTests++;
	}

	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		try {
			// Specify the absolute path to the desired directory
			String directoryPath = "C:\\Users\\rushabh.patel\\OneDrive - Sage Software, Inc\\Documents\\Test\\";

			// Get current date and time
			LocalDateTime now = LocalDateTime.now();

			// Format the date and time
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy_HHmm");
			String formattedDateTime = now.format(formatter);

			// Append formatted date and time to the file name
			String fileName = Helper.environmentValue + "_Env_" + formattedDateTime + ".txt";

			// Concatenate directory path and file name
			String filePath = directoryPath + fileName;
			// Create the file
			File file = new File(filePath);

			// Create a File object representing the directory
			File directory = new File(directoryPath);
			// Get all files in the directory
			File[] files = directory.listFiles();

			// Check if files array is not null and not empty
			if (files != null && files.length > 0) {
				// Loop through each file and delete it
				for (File file2 : files) {
					if (file2.isFile()) { // Check if it's a file
						file2.delete(); // Delete the file
						System.out.println("Deleted file: " + file2.getName());
					}
				}
			}

			// Create the file again
			FileWriter writer = new FileWriter(filePath);
			for (ISuite suite : suites) {
				totalTests += suite.getAllMethods().size();
			}
			// Write the summary to the file
			writer.write("<b>" + Helper.environmentValue + "</b> env automation Report</br>");
			writer.write("===============================================</br>");

			writer.write("Total tests run: " + totalTests + ", Passes: " + passedTests + ", Failures: " + failedTests
					+ ", Skips: " + skippedTests + "</br>");
			writer.write("===============================================");

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Other methods of ITestListener, like onFinish(), can be implemented if needed
}