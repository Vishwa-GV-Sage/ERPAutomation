package negativeScenarios;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import frameworkPkg.Helper;
import io.restassured.response.Response;
import restAPIPkg.Create_Empty_Sync;

public class Invalid_Empty_Sync extends Helper {
	
	
	@Test(priority = 1)
	public void verify_NoZipFile_Empty_Sync_Response_Status() throws IOException, InterruptedException {
		
		
		  Create_Empty_Sync createEmptySyncObj = new Create_Empty_Sync(); Response
		  response = createEmptySyncObj.without_Uploading_File_create_Empty_Sync();
		  
		  // Verify that the response is not null 
		  assertNotNull(response,
		  "Response object should not be null");
		  
		  // Verify the response status String taskStatus =
		String taskStatus=  response.jsonPath().getString("data.attributes.status");
		  assertEquals(taskStatus, "Failed", "Sync task status should be Failed:");
		 

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
		Response response = createEmptySyncObj
				.positive_with_Uploading_File_create_Empty_Sync("C:\\Users\\Public\\Documents\\ERPAutomation\\EmptyZipFile.zip");
		// Verify that the response is not null
		assertNotNull(response, "Response object should not be null");

		// Verify the response status
		String taskStatus = response.jsonPath().getString("data.attributes.status");
		assertEquals(taskStatus, "Completed", "Sync task status should be Failed");

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
		Response response = createEmptySyncObj.positive_with_Uploading_File_create_Empty_Sync(file);
		// Verify that the response is not null
		assertNotNull(response, "Response object should not be null");

		// Verify the response status
		String taskStatus = response.jsonPath().getString("data.attributes.status");
		assertEquals(taskStatus, "Completed", "Sync task status should be Completed");
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
		assertEquals(taskStatus, "Completed", "Sync task status should be Completed");
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
		Response response = createEmptySyncObj.positive_with_Uploading_File_create_Empty_Sync(file);
		// Verify that the response is not null
		assertNotNull(response, "Response object should not be null");

		// Verify the response status
		String taskStatus = response.jsonPath().getString("data.attributes.status");
		assertEquals(taskStatus, "Completed", "Sync task status should be Failed");

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

	@Test(priority = 14)
	public void verify_NestedFolders_Empty_Sync_Response_Status() throws IOException, InterruptedException {

		String file = "C:\\Users\\Public\\Documents\\ERPAutomation\\NestedFolders.zip";
		Create_Empty_Sync createEmptySyncObj = new Create_Empty_Sync();
		Response response = createEmptySyncObj.positive_with_Uploading_File_create_Empty_Sync(file);
		// Verify that the response is not null
		assertNotNull(response, "Response object should not be null");

		// Verify the response status
		String taskStatus = response.jsonPath().getString("data.attributes.status");
		assertEquals(taskStatus, "Completed", "Sync task status should be Failed");

	}

	@Test(priority = 15)
	public void verify_EmptyNestedFolders_Empty_Sync_Response_Status() throws IOException, InterruptedException {

		String file = "C:\\Users\\Public\\Documents\\ERPAutomation\\EmptyNestedFolders.zip";
		Create_Empty_Sync createEmptySyncObj = new Create_Empty_Sync();
		Response response = createEmptySyncObj.positive_with_Uploading_File_create_Empty_Sync(file);
		// Verify that the response is not null
		assertNotNull(response, "Response object should not be null");

		// Verify the response status
		String taskStatus = response.jsonPath().getString("data.attributes.status");
		assertEquals(taskStatus, "Completed", "Sync task status should be Failed");

	}

	@Test(priority = 16)
	public void verify_ExecutableWithCsv_Empty_Sync_Response_Status() throws IOException, InterruptedException {

		String file = "C:\\Users\\Public\\Documents\\ERPAutomation\\executableWithCsv.zip";
		Create_Empty_Sync createEmptySyncObj = new Create_Empty_Sync();
		Response response = createEmptySyncObj.with_Uploading_File_create_Empty_Sync(file);
		// Verify that the response is not null
		assertNotNull(response, "Response object should not be null");

		// Verify the response status
		String taskStatus = response.jsonPath().getString("data.attributes.status");
		assertEquals(taskStatus, "Failed", "Sync task status should be Failed");

	}

	@Test(priority = 17)
	public void verify_Executable_Empty_Sync_Response_Status() throws IOException, InterruptedException {

		String file = "C:\\Users\\Public\\Documents\\ERPAutomation\\executable.zip";
		Create_Empty_Sync createEmptySyncObj = new Create_Empty_Sync();
		Response response = createEmptySyncObj.with_Uploading_File_create_Empty_Sync(file);
		// Verify that the response is not null
		assertNotNull(response, "Response object should not be null");

		// Verify the response status
		String taskStatus = response.jsonPath().getString("data.attributes.status");
		assertEquals(taskStatus, "Failed", "Sync task status should be Failed");

	}

	@Test(priority = 18)
	public void verify_Csv_Only_Header_Empty_Sync_Response_Status() throws IOException, InterruptedException {

		String file = "C:\\Users\\Public\\Documents\\ERPAutomation\\Csv_Only_Header.zip";
		Create_Empty_Sync createEmptySyncObj = new Create_Empty_Sync();
		Response response = createEmptySyncObj.positive_with_Uploading_File_create_Empty_Sync(file);
		// Verify that the response is not null
		assertNotNull(response, "Response object should not be null");

		// Verify the response status
		String taskStatus = response.jsonPath().getString("data.attributes.status");
		assertEquals(taskStatus, "Completed", "Sync task status should be Completed");

	}

	@Test(priority = 19)
	public void verify_CSV_withoutERPKey_Empty_Sync_Response_Status() throws IOException, InterruptedException {

		String file = "C:\\Users\\Public\\Documents\\ERPAutomation\\CSV_withoutERPKey.zip";
		Create_Empty_Sync createEmptySyncObj = new Create_Empty_Sync();
		Response response = createEmptySyncObj.positive_with_Uploading_File_create_Empty_Sync(file);
		// Verify that the response is not null
		assertNotNull(response, "Response object should not be null");

		// Verify the response status
		String taskStatus = response.jsonPath().getString("data.attributes.status");
		assertEquals(taskStatus, "Completed", "Sync task status should be completed");

	}

	@Test(priority = 20)
	public void verify_Empty_DatasetID_Create_Empty_Sync_Response_Status() throws IOException, InterruptedException {

		Create_Empty_Sync createEmptySyncObj = new Create_Empty_Sync();
		int actualStatusCode = createEmptySyncObj.emptyDataSetID(null);
		int expectedStatusCode = 500;

		Assert.assertEquals(actualStatusCode, expectedStatusCode, "The status code is not as expected.");

	}

	@Test(priority = 21)
	public void verify_Invalid_DatasetID_Create_Empty_Sync_Response_Status() throws IOException, InterruptedException {

		Create_Empty_Sync createEmptySyncObj = new Create_Empty_Sync();
		int actualStatusCode = createEmptySyncObj.emptyDataSetID("asdf-asdf");
		int expectedStatusCode = 500;

		Assert.assertEquals(actualStatusCode, expectedStatusCode, "The status code is not as expected.");

	}

	@Test(priority = 22)
	public void verify_Invalid_SyncID_Empty_Sync_Response_Status() throws IOException, InterruptedException {

		Create_Empty_Sync createEmptySyncObj = new Create_Empty_Sync();
		int actualStatusCode = createEmptySyncObj.InvalidSyncID();
		int expectedStatusCode = 400;

		Assert.assertEquals(actualStatusCode, expectedStatusCode, "The status code is not as expected.");

	}

	@Test(priority = 23)
	public void verify_Query_SyncID_which_does_not_exist_Request_Empty_Sync_Response_Status()
			throws IOException, InterruptedException {

		Create_Empty_Sync createEmptySyncObj = new Create_Empty_Sync();
		int actualStatusCode = createEmptySyncObj.query_SyncID_which_does_not_exist();
		int expectedStatusCode = 401;

		Assert.assertEquals(actualStatusCode, expectedStatusCode, "The status code is not as expected.");
	}
	
	@Test(priority = 24)
	public void verify_DatasetID_Invalid_Which_DoesNotExistInDB_Create_Empty_Sync_Response_Status() throws IOException, InterruptedException {

		Create_Empty_Sync createEmptySyncObj = new Create_Empty_Sync();
		int actualStatusCode = createEmptySyncObj.emptyDataSetID("02f0cda6-1187-f10b-6303-d92bb3bde157");
		int expectedStatusCode = 401;

		Assert.assertEquals(actualStatusCode, expectedStatusCode, "The status code is not as expected.");

	}
}
