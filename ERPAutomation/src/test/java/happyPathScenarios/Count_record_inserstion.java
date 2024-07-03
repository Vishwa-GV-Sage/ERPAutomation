package happyPathScenarios;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.annotations.Test;

import dataGenerator.CsvDataGenerator;
import frameworkPkg.Helper;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import restAPIPkg.Create_Batch_Sync;
import restAPIPkg.Create_Empty_Sync;

public class Count_record_inserstion {

	String TempLocation = "C:\\Users\\Public\\Documents\\Temp\\";

	@Test(priority = 1)
	public void verify_2_digit_rows_data_Insertion_Create_Empty_Sync() throws FileNotFoundException, IOException {

		CsvDataGenerator.generateCsvData(30);
		Create_Empty_Sync createEmptySyncObj = new Create_Empty_Sync();
		Response response = createEmptySyncObj
				.positive_with_Uploading_File_create_Empty_Sync(TempLocation + "30_csv.zip",600);
		assertEquals(response.jsonPath().getString("data.attributes.message"), "75 inserted",
				"Message is not as expected");

	}

	@Test(priority = 2)
	public void verify_100_rows_data_Insertion_Create_Empty_Sync() throws FileNotFoundException, IOException {

		CsvDataGenerator.generateCsvData(100);
		Create_Empty_Sync createEmptySyncObj = new Create_Empty_Sync();
		Response response = createEmptySyncObj
				.positive_with_Uploading_File_create_Empty_Sync(TempLocation + "100_csv.zip",1200);
		assertEquals(response.jsonPath().getString("data.attributes.message"), "250 inserted",
				"Message attribute is not as expected");

		// Parse the JSON response
		JsonPath jsonPath = response.jsonPath();

		// Extract the insertCount for Contact
		int contactInsertCount = jsonPath
				.getInt("included.find { it.attributes.report.Contact != null }.attributes.report.Contact.insertCount");
		int companyInsertCount = jsonPath
				.getInt("included.find { it.attributes.report.Company != null }.attributes.report.Company.insertCount");
		assertEquals(companyInsertCount, 100, "Company insertion count is not as expected");
		assertEquals(contactInsertCount, 50, "Contact insertion count is not as expected");
	}

	@Test(priority = 3)
	public void verify_998_rows_data_Insertion_Create_Empty_Sync() throws FileNotFoundException, IOException {
		int rows = 998, halfRows = rows / 2, expectedCount = rows + (halfRows * 3);
		CsvDataGenerator.generateCsvData(rows);
		Create_Empty_Sync createEmptySyncObj = new Create_Empty_Sync();
		Response response = createEmptySyncObj
				.positive_with_Uploading_File_create_Empty_Sync(TempLocation + rows + "_csv.zip",1200);
		// Extract the message string
		String message = response.jsonPath().getString("data.attributes.message");

		// Use a regular expression to extract the numeric part from the message
		String numberPart = message.replaceAll("[^\\d]", "");

		// Convert the cleaned number part to an integer
		int insertCountMessage = Integer.parseInt(numberPart);

		// Perform the assertion on the extracted integer value
		assertEquals(insertCountMessage, expectedCount, "Message attribute is not as expected");

		// Parse the JSON response
		JsonPath jsonPath = response.jsonPath();

		// Extract the insertCount for Contact
		int contactInsertCount = jsonPath
				.getInt("included.find { it.attributes.report.Contact != null }.attributes.report.Contact.insertCount");

		// Extract the insertCount for Company
		int companyInsertCount = jsonPath
				.getInt("included.find { it.attributes.report.Company != null }.attributes.report.Company.insertCount");

		// Perform assertions on the insert counts
		assertEquals(companyInsertCount, rows, "Company insertion count is not as expected");
		assertEquals(contactInsertCount, halfRows, "Contact insertion count is not as expected");
	}

	@Test(priority = 4)
	public void verify_9998_rows_data_Insertion_Create_Empty_Sync() throws FileNotFoundException, IOException {
		int rows = 9998, halfRows = rows / 2, expectedCount = rows + (halfRows * 3);
		CsvDataGenerator.generateCsvData(rows);
		Create_Empty_Sync createEmptySyncObj = new Create_Empty_Sync();
		Response response = createEmptySyncObj
				.positive_with_Uploading_File_create_Empty_Sync(TempLocation + rows + "_csv.zip",1200);
		// Extract the message string
		String message = response.jsonPath().getString("data.attributes.message");

		// Use a regular expression to extract the numeric part from the message
		String numberPart = message.replaceAll("[^\\d]", "");

		// Convert the cleaned number part to an integer
		int insertCountMessage = Integer.parseInt(numberPart);

		// Perform the assertion on the extracted integer value
		assertEquals(insertCountMessage, expectedCount, "Message attribute is not as expected");

		// Parse the JSON response
		JsonPath jsonPath = response.jsonPath();

		// Extract the insertCount for Contact
		int contactInsertCount = jsonPath
				.getInt("included.find { it.attributes.report.Contact != null }.attributes.report.Contact.insertCount");

		// Extract the insertCount for Company
		int companyInsertCount = jsonPath
				.getInt("included.find { it.attributes.report.Company != null }.attributes.report.Company.insertCount");

		// Perform assertions on the insert counts
		assertEquals(companyInsertCount, rows, "Company insertion count is not as expected");
		assertEquals(contactInsertCount, halfRows, "Contact insertion count is not as expected");
	}
	
	
	public void verify_1Lakh_rows_data_Insertion_Create_Empty_Sync() throws FileNotFoundException, IOException {
		int rows = 5000000, halfRows = rows / 2, expectedCount = rows + (halfRows * 3);
		CsvDataGenerator.generateCsvData(rows);
		Create_Empty_Sync createEmptySyncObj = new Create_Empty_Sync();
		Response response = createEmptySyncObj
				.positive_with_Uploading_File_create_Empty_Sync(TempLocation + rows + "_csv.zip");
		// Extract the message string
		String message = response.jsonPath().getString("data.attributes.message");

		// Use a regular expression to extract the numeric part from the message
		String numberPart = message.replaceAll("[^\\d]", "");

		// Convert the cleaned number part to an integer
		int insertCountMessage = Integer.parseInt(numberPart);

		// Perform the assertion on the extracted integer value
		assertEquals(insertCountMessage, expectedCount, "Message attribute is not as expected");

		// Parse the JSON response
		JsonPath jsonPath = response.jsonPath();

		// Extract the insertCount for Contact
		int contactInsertCount = jsonPath
				.getInt("included.find { it.attributes.report.Contact != null }.attributes.report.Contact.insertCount");

		// Extract the insertCount for Company
		int companyInsertCount = jsonPath
				.getInt("included.find { it.attributes.report.Company != null }.attributes.report.Company.insertCount");

		// Perform assertions on the insert counts
		assertEquals(companyInsertCount, rows, "Company insertion count is not as expected");
		assertEquals(contactInsertCount, halfRows, "Contact insertion count is not as expected");
	}

}
