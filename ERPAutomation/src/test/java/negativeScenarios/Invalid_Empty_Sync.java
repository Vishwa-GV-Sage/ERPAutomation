package negativeScenarios;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.io.IOException;

import org.testng.annotations.Test;

import frameworkPkg.Helper;
import io.restassured.response.Response;
import restAPIPkg.Create_Empty_Sync;

public class Invalid_Empty_Sync extends Helper {


	@Test(priority = 1)
	public void verify_NoZipFile_Empty_Sync_Response_Status() throws IOException, InterruptedException {

		Create_Empty_Sync createEmptySyncObj = new Create_Empty_Sync();
		Response response = createEmptySyncObj.without_Uploading_File_create_Empty_Sync();

		// Verify that the response is not null
		assertNotNull(response, "Response object should not be null");

		// Verify the response status
		String taskStatus = response.jsonPath().getString("data.attributes.status");
		assertEquals(taskStatus, "Failed", "Sync task status should be Failed");

	}

	@Test(priority = 2)
	public void verify_CourrptZipFile_Empty_Sync_Response_Status() throws IOException, InterruptedException {
		Create_Empty_Sync createEmptySyncObj = new Create_Empty_Sync();
		Response response = createEmptySyncObj.with_Uploading_File_create_Empty_Sync(
				"C:\\Users\\Public\\Documents\\ERPAutomation\\courrpt zip file.zip");

		// Verify that the response is not null
		assertNotNull(response, "Response object should not be null");

		// Verify the response status
		String taskStatus = response.jsonPath().getString("data.attributes.status");
		assertEquals(taskStatus, "Failed", "Sync task status should be Failed");

	}

	@Test(priority = 3)
	public void verify_EmptyZipFile_Empty_Sync_Response_Status() throws IOException, InterruptedException {
		Create_Empty_Sync createEmptySyncObj = new Create_Empty_Sync();
		Response response = createEmptySyncObj.with_Uploading_File_create_Empty_Sync(
				"C:\\Users\\Public\\Documents\\ERPAutomation\\EmptyZipFile.zip");
		// Verify that the response is not null
		assertNotNull(response, "Response object should not be null");

		// Verify the response status
		String taskStatus = response.jsonPath().getString("data.attributes.status");
		assertEquals(taskStatus, "Failed", "Sync task status should be Failed");

	}

	@Test(priority = 4)
	public void verify_InvalidZipCSV_Data_File_Empty_Sync_Response_Status() throws IOException, InterruptedException {

		String file = "C:\\Users\\Public\\Documents\\ERPAutomation\\InvalidCsv.zip";

		Create_Empty_Sync createEmptySyncObj = new Create_Empty_Sync();
		Response response = createEmptySyncObj.with_Uploading_File_create_Empty_Sync(file);
		// Verify that the response is not null
		assertNotNull(response, "Response object should not be null");

		// Verify the response status
		String taskStatus = response.jsonPath().getString("data.attributes.status");
		assertEquals(taskStatus, "Failed", "Sync task status should be Failed");

	}

	@Test(priority = 5)
	public void verify_OtherFile_Data_File_Empty_Sync_Response_Status() throws IOException, InterruptedException {

		String file = "C:\\Users\\Public\\Documents\\ERPAutomation\\annual.csv";

		Create_Empty_Sync createEmptySyncObj = new Create_Empty_Sync();
		Response response = createEmptySyncObj.with_Uploading_File_create_Empty_Sync(file);
		// Verify that the response is not null
		assertNotNull(response, "Response object should not be null");

		// Verify the response status
		String taskStatus = response.jsonPath().getString("data.attributes.status");
		assertEquals(taskStatus, "Failed", "Sync task status should be Failed");

	}

	@Test(priority = 6)
	public void verify_CSVwith_EmptyRow_File_Empty_Sync_Response_Status() throws IOException, InterruptedException {

		String file = "C:\\Users\\Public\\Documents\\ERPAutomation\\EmptyRows.zip";
		Create_Empty_Sync createEmptySyncObj = new Create_Empty_Sync();
		Response response = createEmptySyncObj.with_Uploading_File_create_Empty_Sync(file);
		// Verify that the response is not null
		assertNotNull(response, "Response object should not be null");

		// Verify the response status
		String taskStatus = response.jsonPath().getString("data.attributes.status");
		assertEquals(taskStatus, "Failed", "Sync task status should be Failed");
	}

	@Test(priority = 7)
	public void verify_ColumnSwapping_Empty_Sync_Response_Status() throws IOException, InterruptedException {

		String file = "C:\\Users\\Public\\Documents\\ERPAutomation\\ColumnSwapping.zip";

		Create_Empty_Sync createEmptySyncObj = new Create_Empty_Sync();
		Response response = createEmptySyncObj.with_Uploading_File_create_Empty_Sync(file);
		// Verify that the response is not null
		assertNotNull(response, "Response object should not be null");

		// Verify the response status
		String taskStatus = response.jsonPath().getString("data.attributes.status");
		assertEquals(taskStatus, "Failed", "Sync task status should be Failed");
	}

	@Test(priority = 8)
	public void verify_AlphaNumeric_Empty_Sync_Response_Status() throws IOException, InterruptedException {

		String file = "C:\\Users\\Public\\Documents\\ERPAutomation\\123@qwe.zip";

		Create_Empty_Sync createEmptySyncObj = new Create_Empty_Sync();
		Response response = createEmptySyncObj.positive_with_Uploading_File_create_Empty_Sync(file);
		// Verify that the response is not null
		assertNotNull(response, "Response object should not be null");

		// Verify the response status
		String taskStatus = response.jsonPath().getString("data.attributes.status");
		assertEquals(taskStatus, "Failed", "Sync task status should be Failed");
	}

	@Test(priority = 9)
	public void verify_ProtectedPDF_Empty_Sync_Response_Status() throws IOException, InterruptedException {

		String file = "C:\\Users\\Public\\Documents\\ERPAutomation\\dummy-protected.pdf";
		Create_Empty_Sync createEmptySyncObj = new Create_Empty_Sync();
		Response response = createEmptySyncObj.with_Uploading_File_create_Empty_Sync(file);
		// Verify that the response is not null
		assertNotNull(response, "Response object should not be null");

		// Verify the response status
		String taskStatus = response.jsonPath().getString("data.attributes.status");
		assertEquals(taskStatus, "Failed", "Sync task status should be Failed");
	}

	@Test(priority = 10)
	public void verify_ProtectedZip_Empty_Sync_Response_Status() throws IOException, InterruptedException {

		String file = "C:\\Users\\Public\\Documents\\ERPAutomation\\protected.zip";
		Create_Empty_Sync createEmptySyncObj = new Create_Empty_Sync();
		Response response = createEmptySyncObj.with_Uploading_File_create_Empty_Sync(file);
		// Verify that the response is not null
		assertNotNull(response, "Response object should not be null");

		// Verify the response status
		String taskStatus = response.jsonPath().getString("data.attributes.status");
		assertEquals(taskStatus, "Failed", "Sync task status should be Failed");
	}

	@Test(priority = 11)
	public void verify_CSVMissingColumns_Empty_Sync_Response_Status() throws IOException, InterruptedException {

		String file = "C:\\Users\\Public\\Documents\\ERPAutomation\\CsvMissingColumn.zip";

		Create_Empty_Sync createEmptySyncObj = new Create_Empty_Sync();
		Response response = createEmptySyncObj.with_Uploading_File_create_Empty_Sync(file);
		// Verify that the response is not null
		assertNotNull(response, "Response object should not be null");

		// Verify the response status
		String taskStatus = response.jsonPath().getString("data.attributes.status");
		assertEquals(taskStatus, "Failed", "Sync task status should be Failed");
	}

	@Test(priority = 12)
	public void verify_CSV_InvalidErpKey_Empty_Sync_Response_Status() throws IOException, InterruptedException {

		String file = "C:\\Users\\Public\\Documents\\ERPAutomation\\erpKeyInvalid.zip";
		Create_Empty_Sync createEmptySyncObj = new Create_Empty_Sync();
		Response response = createEmptySyncObj.with_Uploading_File_create_Empty_Sync(file);
		// Verify that the response is not null
		assertNotNull(response, "Response object should not be null");

		// Verify the response status
		String taskStatus = response.jsonPath().getString("data.attributes.status");
		assertEquals(taskStatus, "Failed", "Sync task status should be Failed");

	}
	
	@Test(priority = 13)
	public void verify_CSV_missingHeader_Empty_Sync_Response_Status() throws IOException, InterruptedException {

		String file = "C:\\Users\\Public\\Documents\\ERPAutomation\\missingHeader.zip";
		Create_Empty_Sync createEmptySyncObj = new Create_Empty_Sync();
		Response response = createEmptySyncObj.with_Uploading_File_create_Empty_Sync(file);
		// Verify that the response is not null
		assertNotNull(response, "Response object should not be null");

		// Verify the response status
		String taskStatus = response.jsonPath().getString("data.attributes.status");
		assertEquals(taskStatus, "Failed", "Sync task status should be Failed");

	}
}
