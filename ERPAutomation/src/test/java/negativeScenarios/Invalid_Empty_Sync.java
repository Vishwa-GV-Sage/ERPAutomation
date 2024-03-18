package negativeScenarios;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.testng.annotations.Test;

import frameworkPkg.Helper;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Invalid_Empty_Sync extends Helper {

	private String syncBatchID, syncAttachmentId, blobUploadUrl;
	String apiBaseUrl = syncAPIBaseURL, dataset_id = syncAPIDataset_ID, jwtToken = syncAPIJwtToken;

	@Test(priority = 1)
	public void verify_NoZipFile_Empty_Sync_Response_Status() throws IOException, InterruptedException {
		// Step 1: Create Sync Task
		String endpointUrl = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks";

		// Request body
		String requestBody = "{\"data\":{\"type\":\"SyncTask\",\"attributes\":{\"packageType\":\"Full\"}}}";

		// Send POST request and capture response
		Response response = RestAssured.given().header("Content-Type", "application/vnd.api+json")
				.header("Accept", "application/vnd.api+json").header("Authorization", "Bearer " + jwtToken)
				.header("Idempotency-Key", "fd45e434-c20d-4837-a076-a427b180a068").body(requestBody).post(endpointUrl);

		// Print the response
		// response.prettyPrint();

		syncAttachmentId = response.jsonPath().getString("data.relationships.attachment.data.id");
		// Extract id from response and store it in the global variable
		syncBatchID = response.jsonPath().getString("data.id");
		// Assert the status code
		response.then().statusCode(201);

		// Assert attributes in the response
		try {
			assertEquals(response.jsonPath().getString("data.attributes.status"), "Awaiting",
					"Status attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.operationType"), "ToNetwork",
					"Operation type attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.packageType"), "Full",
					"Package type attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.stepName"), "Preparing",
					"Step name attribute is not as expected");
			// SynTask ID should not be null.
			assertNotNull(response.jsonPath().getString("data.id"), "ID is null");
			// Upload URL should not be null.
			assertNotNull("included[0].attributes.uploadUrl", "Upload URL is null");
			// Assert id and type in the response
			assertTrue(response.jsonPath().getString("data.id").matches("[a-f0-9-]{36}"),
					"ID is not in expected format");
			assertEquals(response.jsonPath().getString("data.type"), "SyncTask", "Type attribute is not as expected");

		} catch (AssertionError e) {
			// Log the failure
			System.out.println("Assertion failed: " + e.getMessage());
			throw e;
		}
		blobUploadUrl = response.jsonPath().getString("included[0].attributes.uploadUrl");
		// Step 2: Upload Sync Task Zip File
		String url = blobUploadUrl;
		String file = "";
		/*
		 * HttpURLConnection connection = (HttpURLConnection) new
		 * URL(url).openConnection(); connection.setRequestMethod("PUT");
		 * connection.setRequestProperty("x-ms-blob-type", "BlockBlob");
		 * connection.setDoOutput(true);
		 * 
		 * try (OutputStream outputStream = connection.getOutputStream();
		 * FileInputStream fileInputStream = new FileInputStream(new File(file))) {
		 * byte[] buffer = new byte[4096]; int bytesRead; while ((bytesRead =
		 * fileInputStream.read(buffer)) != -1) { outputStream.write(buffer, 0,
		 * bytesRead); } }
		 * 
		 * int responseCode = connection.getResponseCode(); assert responseCode == 201 :
		 * "File upload failed. Response code: " + responseCode;
		 * 
		 * connection.disconnect();
		 */

		String updateEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id
				+ "/sync-tasks?filter=operation_type%20eq%20%27toNetwork%27&take=3&skip=0";
		String updaterequestBody = "{" + "\"data\": {" + "\"type\": \"synctask\"," + "\"id\": \"" + syncBatchID + "\","
				+ "\"attributes\": {" + "\"summary\": {" + "\"totalInvoices\": 1," + "\"totalPayments\": 2,"
				+ "\"totalCompanies\": 3," + "\"totalContacts\": 4," + "\"totalGlAccounts\": 5,"
				+ "\"totalGlAccountEntries\": 6," + "\"totalCustomFields\": 7" + "}" + "}," + "\"relationships\": {"
				+ "\"attachment\": {" + "\"data\": {" + "\"id\": \"" + syncAttachmentId + "\","
				+ "\"type\": \"attachment\"" + "}" + "}" + "}" + "}}";
		Response updateResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
				.header("Content-Type", "application/vnd.api+json").header("Authorization", "Bearer " + jwtToken)
				.header("Idempotency-Key", "fd45e434-c20d-4837-a076-a427b180a068").body(updaterequestBody)
				.patch(updateEndpoint);

		// Print the response
		// updateResponse.prettyPrint();

		// Assert the status code
		updateResponse.then().statusCode(202);

	}

	@Test(priority = 2)
	public void verify_CourrptZipFile_Empty_Sync_Response_Status() throws IOException, InterruptedException {
		// Step 1: Create Sync Task
		String endpointUrl = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks";

		// Request body
		String requestBody = "{\"data\":{\"type\":\"SyncTask\",\"attributes\":{\"packageType\":\"Full\"}}}";

		// Send POST request and capture response
		Response response = RestAssured.given().header("Content-Type", "application/vnd.api+json")
				.header("Accept", "application/vnd.api+json").header("Authorization", "Bearer " + jwtToken)
				.header("Idempotency-Key", "fd45e434-c20d-4837-a076-a427b180a068").body(requestBody).post(endpointUrl);

		// Print the response
		// response.prettyPrint();

		syncAttachmentId = response.jsonPath().getString("data.relationships.attachment.data.id");
		// Extract id from response and store it in the global variable
		syncBatchID = response.jsonPath().getString("data.id");
		// Assert the status code
		response.then().statusCode(201);

		// Assert attributes in the response
		try {
			assertEquals(response.jsonPath().getString("data.attributes.status"), "Awaiting",
					"Status attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.operationType"), "ToNetwork",
					"Operation type attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.packageType"), "Full",
					"Package type attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.stepName"), "Preparing",
					"Step name attribute is not as expected");
			// SynTask ID should not be null.
			assertNotNull(response.jsonPath().getString("data.id"), "ID is null");
			// Upload URL should not be null.
			assertNotNull("included[0].attributes.uploadUrl", "Upload URL is null");
			// Assert id and type in the response
			assertTrue(response.jsonPath().getString("data.id").matches("[a-f0-9-]{36}"),
					"ID is not in expected format");
			assertEquals(response.jsonPath().getString("data.type"), "SyncTask", "Type attribute is not as expected");

		} catch (AssertionError e) {
			// Log the failure
			System.out.println("Assertion failed: " + e.getMessage());
			throw e;
		}
		blobUploadUrl = response.jsonPath().getString("included[0].attributes.uploadUrl");
		// Step 2: Upload Sync Task Zip File
		String url = blobUploadUrl;
		String file = "C:\\Users\\Public\\Documents\\ERPAutomation\\courrpt zip file.zip";

		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setRequestMethod("PUT");
		connection.setRequestProperty("x-ms-blob-type", "BlockBlob");
		connection.setDoOutput(true);

		try (OutputStream outputStream = connection.getOutputStream();
				FileInputStream fileInputStream = new FileInputStream(new File(file))) {
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = fileInputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
		}

		int responseCode = connection.getResponseCode();
		assert responseCode == 201 : "File upload failed. Response code: " + responseCode;

		connection.disconnect();

		String updateEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id
				+ "/sync-tasks?filter=operation_type%20eq%20%27toNetwork%27&take=3&skip=0";
		String updaterequestBody = "{" + "\"data\": {" + "\"type\": \"synctask\"," + "\"id\": \"" + syncBatchID + "\","
				+ "\"attributes\": {" + "\"summary\": {" + "\"totalInvoices\": 1," + "\"totalPayments\": 2,"
				+ "\"totalCompanies\": 3," + "\"totalContacts\": 4," + "\"totalGlAccounts\": 5,"
				+ "\"totalGlAccountEntries\": 6," + "\"totalCustomFields\": 7" + "}" + "}," + "\"relationships\": {"
				+ "\"attachment\": {" + "\"data\": {" + "\"id\": \"" + syncAttachmentId + "\","
				+ "\"type\": \"attachment\"" + "}" + "}" + "}" + "}}";
		Response updateResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
				.header("Content-Type", "application/vnd.api+json").header("Authorization", "Bearer " + jwtToken)
				.header("Idempotency-Key", "fd45e434-c20d-4837-a076-a427b180a068").body(updaterequestBody)
				.patch(updateEndpoint);

		// Print the response
		// updateResponse.prettyPrint();

		// Assert the status code
		updateResponse.then().statusCode(202);

	}

	@Test(priority = 3)
	public void verify_EmptyZipFile_Empty_Sync_Response_Status() throws IOException, InterruptedException {
		// Step 1: Create Sync Task
		String endpointUrl = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks";

		// Request body
		String requestBody = "{\"data\":{\"type\":\"SyncTask\",\"attributes\":{\"packageType\":\"Full\"}}}";

		// Send POST request and capture response
		Response response = RestAssured.given().header("Content-Type", "application/vnd.api+json")
				.header("Accept", "application/vnd.api+json").header("Authorization", "Bearer " + jwtToken)
				.header("Idempotency-Key", "fd45e434-c20d-4837-a076-a427b180a068").body(requestBody).post(endpointUrl);

		// Print the response
		// response.prettyPrint();

		syncAttachmentId = response.jsonPath().getString("data.relationships.attachment.data.id");
		// Extract id from response and store it in the global variable
		syncBatchID = response.jsonPath().getString("data.id");
		// Assert the status code
		response.then().statusCode(201);

		// Assert attributes in the response
		try {
			assertEquals(response.jsonPath().getString("data.attributes.status"), "Awaiting",
					"Status attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.operationType"), "ToNetwork",
					"Operation type attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.packageType"), "Full",
					"Package type attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.stepName"), "Preparing",
					"Step name attribute is not as expected");
			// SynTask ID should not be null.
			assertNotNull(response.jsonPath().getString("data.id"), "ID is null");
			// Upload URL should not be null.
			assertNotNull("included[0].attributes.uploadUrl", "Upload URL is null");
			// Assert id and type in the response
			assertTrue(response.jsonPath().getString("data.id").matches("[a-f0-9-]{36}"),
					"ID is not in expected format");
			assertEquals(response.jsonPath().getString("data.type"), "SyncTask", "Type attribute is not as expected");

		} catch (AssertionError e) {
			// Log the failure
			System.out.println("Assertion failed: " + e.getMessage());
			throw e;
		}
		blobUploadUrl = response.jsonPath().getString("included[0].attributes.uploadUrl");
		// Step 2: Upload Sync Task Zip File
		String url = blobUploadUrl;
		String file = "C:\\Users\\Public\\Documents\\ERPAutomation\\Empty zip file.zip";

		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setRequestMethod("PUT");
		connection.setRequestProperty("x-ms-blob-type", "BlockBlob");
		connection.setDoOutput(true);

		try (OutputStream outputStream = connection.getOutputStream();
				FileInputStream fileInputStream = new FileInputStream(new File(file))) {
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = fileInputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
		}

		int responseCode = connection.getResponseCode();
		assert responseCode == 201 : "File upload failed. Response code: " + responseCode;

		connection.disconnect();

		String updateEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id
				+ "/sync-tasks?filter=operation_type%20eq%20%27toNetwork%27&take=3&skip=0";
		String updaterequestBody = "{" + "\"data\": {" + "\"type\": \"synctask\"," + "\"id\": \"" + syncBatchID + "\","
				+ "\"attributes\": {" + "\"summary\": {" + "\"totalInvoices\": 1," + "\"totalPayments\": 2,"
				+ "\"totalCompanies\": 3," + "\"totalContacts\": 4," + "\"totalGlAccounts\": 5,"
				+ "\"totalGlAccountEntries\": 6," + "\"totalCustomFields\": 7" + "}" + "}," + "\"relationships\": {"
				+ "\"attachment\": {" + "\"data\": {" + "\"id\": \"" + syncAttachmentId + "\","
				+ "\"type\": \"attachment\"" + "}" + "}" + "}" + "}}";
		Response updateResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
				.header("Content-Type", "application/vnd.api+json").header("Authorization", "Bearer " + jwtToken)
				.header("Idempotency-Key", "fd45e434-c20d-4837-a076-a427b180a068").body(updaterequestBody)
				.patch(updateEndpoint);

		// Print the response
		// updateResponse.prettyPrint();

		// Assert the status code
		updateResponse.then().statusCode(202);

	}

	@Test(priority = 4)
	public void verify_InvalidZipCSV_Data_File_Empty_Sync_Response_Status() throws IOException, InterruptedException {
		// Step 1: Create Sync Task
		String endpointUrl = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks";

		// Request body
		String requestBody = "{\"data\":{\"type\":\"SyncTask\",\"attributes\":{\"packageType\":\"Full\"}}}";

		// Send POST request and capture response
		Response response = RestAssured.given().header("Content-Type", "application/vnd.api+json")
				.header("Accept", "application/vnd.api+json").header("Authorization", "Bearer " + jwtToken)
				.header("Idempotency-Key", "fd45e434-c20d-4837-a076-a427b180a068").body(requestBody).post(endpointUrl);

		// Print the response
		// response.prettyPrint();

		syncAttachmentId = response.jsonPath().getString("data.relationships.attachment.data.id");
		// Extract id from response and store it in the global variable
		syncBatchID = response.jsonPath().getString("data.id");
		// Assert the status code
		response.then().statusCode(201);

		// Assert attributes in the response
		try {
			assertEquals(response.jsonPath().getString("data.attributes.status"), "Awaiting",
					"Status attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.operationType"), "ToNetwork",
					"Operation type attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.packageType"), "Full",
					"Package type attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.stepName"), "Preparing",
					"Step name attribute is not as expected");
			// SynTask ID should not be null.
			assertNotNull(response.jsonPath().getString("data.id"), "ID is null");
			// Upload URL should not be null.
			assertNotNull("included[0].attributes.uploadUrl", "Upload URL is null");
			// Assert id and type in the response
			assertTrue(response.jsonPath().getString("data.id").matches("[a-f0-9-]{36}"),
					"ID is not in expected format");
			assertEquals(response.jsonPath().getString("data.type"), "SyncTask", "Type attribute is not as expected");

		} catch (AssertionError e) {
			// Log the failure
			System.out.println("Assertion failed: " + e.getMessage());
			throw e;
		}
		blobUploadUrl = response.jsonPath().getString("included[0].attributes.uploadUrl");
		// Step 2: Upload Sync Task Zip File
		String url = blobUploadUrl;
		String file = "C:\\Users\\Public\\Documents\\ERPAutomation\\InvalidCsv.zip";

		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setRequestMethod("PUT");
		connection.setRequestProperty("x-ms-blob-type", "BlockBlob");
		connection.setDoOutput(true);

		try (OutputStream outputStream = connection.getOutputStream();
				FileInputStream fileInputStream = new FileInputStream(new File(file))) {
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = fileInputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
		}

		int responseCode = connection.getResponseCode();
		assert responseCode == 201 : "File upload failed. Response code: " + responseCode;

		connection.disconnect();

		String updateEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id
				+ "/sync-tasks?filter=operation_type%20eq%20%27toNetwork%27&take=3&skip=0";
		String updaterequestBody = "{" + "\"data\": {" + "\"type\": \"synctask\"," + "\"id\": \"" + syncBatchID + "\","
				+ "\"attributes\": {" + "\"summary\": {" + "\"totalInvoices\": 1," + "\"totalPayments\": 2,"
				+ "\"totalCompanies\": 3," + "\"totalContacts\": 4," + "\"totalGlAccounts\": 5,"
				+ "\"totalGlAccountEntries\": 6," + "\"totalCustomFields\": 7" + "}" + "}," + "\"relationships\": {"
				+ "\"attachment\": {" + "\"data\": {" + "\"id\": \"" + syncAttachmentId + "\","
				+ "\"type\": \"attachment\"" + "}" + "}" + "}" + "}}";
		Response updateResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
				.header("Content-Type", "application/vnd.api+json").header("Authorization", "Bearer " + jwtToken)
				.header("Idempotency-Key", "fd45e434-c20d-4837-a076-a427b180a068").body(updaterequestBody)
				.patch(updateEndpoint);

		// Print the response
		// updateResponse.prettyPrint();

		// Assert the status code
		updateResponse.then().statusCode(202);

	}

	@Test(priority = 5)
	public void verify_OtherFile_Data_File_Empty_Sync_Response_Status() throws IOException, InterruptedException {
		// Step 1: Create Sync Task
		String endpointUrl = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks";

		// Request body
		String requestBody = "{\"data\":{\"type\":\"SyncTask\",\"attributes\":{\"packageType\":\"Full\"}}}";

		// Send POST request and capture response
		Response response = RestAssured.given().header("Content-Type", "application/vnd.api+json")
				.header("Accept", "application/vnd.api+json").header("Authorization", "Bearer " + jwtToken)
				.header("Idempotency-Key", "fd45e434-c20d-4837-a076-a427b180a068").body(requestBody).post(endpointUrl);

		// Print the response
		// response.prettyPrint();

		syncAttachmentId = response.jsonPath().getString("data.relationships.attachment.data.id");
		// Extract id from response and store it in the global variable
		syncBatchID = response.jsonPath().getString("data.id");
		// Assert the status code
		response.then().statusCode(201);

		// Assert attributes in the response
		try {
			assertEquals(response.jsonPath().getString("data.attributes.status"), "Awaiting",
					"Status attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.operationType"), "ToNetwork",
					"Operation type attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.packageType"), "Full",
					"Package type attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.stepName"), "Preparing",
					"Step name attribute is not as expected");
			// SynTask ID should not be null.
			assertNotNull(response.jsonPath().getString("data.id"), "ID is null");
			// Upload URL should not be null.
			assertNotNull("included[0].attributes.uploadUrl", "Upload URL is null");
			// Assert id and type in the response
			assertTrue(response.jsonPath().getString("data.id").matches("[a-f0-9-]{36}"),
					"ID is not in expected format");
			assertEquals(response.jsonPath().getString("data.type"), "SyncTask", "Type attribute is not as expected");

		} catch (AssertionError e) {
			// Log the failure
			System.out.println("Assertion failed: " + e.getMessage());
			throw e;
		}
		blobUploadUrl = response.jsonPath().getString("included[0].attributes.uploadUrl");
		// Step 2: Upload Sync Task Zip File
		String url = blobUploadUrl;
		String file = "C:\\Users\\Public\\Documents\\ERPAutomation\\annual.csv";

		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setRequestMethod("PUT");
		connection.setRequestProperty("x-ms-blob-type", "BlockBlob");
		connection.setDoOutput(true);

		try (OutputStream outputStream = connection.getOutputStream();
				FileInputStream fileInputStream = new FileInputStream(new File(file))) {
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = fileInputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
		}

		int responseCode = connection.getResponseCode();
		assert responseCode == 201 : "File upload failed. Response code: " + responseCode;

		connection.disconnect();

		String updateEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id
				+ "/sync-tasks?filter=operation_type%20eq%20%27toNetwork%27&take=3&skip=0";
		String updaterequestBody = "{" + "\"data\": {" + "\"type\": \"synctask\"," + "\"id\": \"" + syncBatchID + "\","
				+ "\"attributes\": {" + "\"summary\": {" + "\"totalInvoices\": 1," + "\"totalPayments\": 2,"
				+ "\"totalCompanies\": 3," + "\"totalContacts\": 4," + "\"totalGlAccounts\": 5,"
				+ "\"totalGlAccountEntries\": 6," + "\"totalCustomFields\": 7" + "}" + "}," + "\"relationships\": {"
				+ "\"attachment\": {" + "\"data\": {" + "\"id\": \"" + syncAttachmentId + "\","
				+ "\"type\": \"attachment\"" + "}" + "}" + "}" + "}}";
		Response updateResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
				.header("Content-Type", "application/vnd.api+json").header("Authorization", "Bearer " + jwtToken)
				.header("Idempotency-Key", "fd45e434-c20d-4837-a076-a427b180a068").body(updaterequestBody)
				.patch(updateEndpoint);

		// Print the response
		// updateResponse.prettyPrint();

		// Assert the status code
		updateResponse.then().statusCode(202);

	}

	@Test(priority = 6)
	public void verify_CSVwith_EmptyRow_File_Empty_Sync_Response_Status() throws IOException, InterruptedException {
		// Step 1: Create Sync Task
		String endpointUrl = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks";

		// Request body
		String requestBody = "{\"data\":{\"type\":\"SyncTask\",\"attributes\":{\"packageType\":\"Full\"}}}";

		// Send POST request and capture response
		Response response = RestAssured.given().header("Content-Type", "application/vnd.api+json")
				.header("Accept", "application/vnd.api+json").header("Authorization", "Bearer " + jwtToken)
				.header("Idempotency-Key", "fd45e434-c20d-4837-a076-a427b180a068").body(requestBody).post(endpointUrl);

		// Print the response
		// response.prettyPrint();

		syncAttachmentId = response.jsonPath().getString("data.relationships.attachment.data.id");
		// Extract id from response and store it in the global variable
		syncBatchID = response.jsonPath().getString("data.id");
		// Assert the status code
		response.then().statusCode(201);

		// Assert attributes in the response
		try {
			assertEquals(response.jsonPath().getString("data.attributes.status"), "Awaiting",
					"Status attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.operationType"), "ToNetwork",
					"Operation type attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.packageType"), "Full",
					"Package type attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.stepName"), "Preparing",
					"Step name attribute is not as expected");
			// SynTask ID should not be null.
			assertNotNull(response.jsonPath().getString("data.id"), "ID is null");
			// Upload URL should not be null.
			assertNotNull("included[0].attributes.uploadUrl", "Upload URL is null");
			// Assert id and type in the response
			assertTrue(response.jsonPath().getString("data.id").matches("[a-f0-9-]{36}"),
					"ID is not in expected format");
			assertEquals(response.jsonPath().getString("data.type"), "SyncTask", "Type attribute is not as expected");

		} catch (AssertionError e) {
			// Log the failure
			System.out.println("Assertion failed: " + e.getMessage());
			throw e;
		}
		blobUploadUrl = response.jsonPath().getString("included[0].attributes.uploadUrl");
		// Step 2: Upload Sync Task Zip File
		String url = blobUploadUrl;
		String file = "C:\\Users\\Public\\Documents\\ERPAutomation\\EmptyRows.zip";

		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setRequestMethod("PUT");
		connection.setRequestProperty("x-ms-blob-type", "BlockBlob");
		connection.setDoOutput(true);

		try (OutputStream outputStream = connection.getOutputStream();
				FileInputStream fileInputStream = new FileInputStream(new File(file))) {
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = fileInputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
		}

		int responseCode = connection.getResponseCode();
		assert responseCode == 201 : "File upload failed. Response code: " + responseCode;

		connection.disconnect();

		String updateEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id
				+ "/sync-tasks?filter=operation_type%20eq%20%27toNetwork%27&take=3&skip=0";
		String updaterequestBody = "{" + "\"data\": {" + "\"type\": \"synctask\"," + "\"id\": \"" + syncBatchID + "\","
				+ "\"attributes\": {" + "\"summary\": {" + "\"totalInvoices\": 1," + "\"totalPayments\": 2,"
				+ "\"totalCompanies\": 3," + "\"totalContacts\": 4," + "\"totalGlAccounts\": 5,"
				+ "\"totalGlAccountEntries\": 6," + "\"totalCustomFields\": 7" + "}" + "}," + "\"relationships\": {"
				+ "\"attachment\": {" + "\"data\": {" + "\"id\": \"" + syncAttachmentId + "\","
				+ "\"type\": \"attachment\"" + "}" + "}" + "}" + "}}";
		Response updateResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
				.header("Content-Type", "application/vnd.api+json").header("Authorization", "Bearer " + jwtToken)
				.header("Idempotency-Key", "fd45e434-c20d-4837-a076-a427b180a068").body(updaterequestBody)
				.patch(updateEndpoint);

		// Print the response
		// updateResponse.prettyPrint();

		// Assert the status code
		updateResponse.then().statusCode(202);

	}

	@Test(priority = 7)
	public void verify_ColumnSwapping_Empty_Sync_Response_Status() throws IOException, InterruptedException {
		// Step 1: Create Sync Task
		String endpointUrl = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks";

		// Request body
		String requestBody = "{\"data\":{\"type\":\"SyncTask\",\"attributes\":{\"packageType\":\"Full\"}}}";

		// Send POST request and capture response
		Response response = RestAssured.given().header("Content-Type", "application/vnd.api+json")
				.header("Accept", "application/vnd.api+json").header("Authorization", "Bearer " + jwtToken)
				.header("Idempotency-Key", "fd45e434-c20d-4837-a076-a427b180a068").body(requestBody).post(endpointUrl);

		// Print the response
		// response.prettyPrint();

		syncAttachmentId = response.jsonPath().getString("data.relationships.attachment.data.id");
		// Extract id from response and store it in the global variable
		syncBatchID = response.jsonPath().getString("data.id");
		// Assert the status code
		response.then().statusCode(201);

		// Assert attributes in the response
		try {
			assertEquals(response.jsonPath().getString("data.attributes.status"), "Awaiting",
					"Status attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.operationType"), "ToNetwork",
					"Operation type attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.packageType"), "Full",
					"Package type attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.stepName"), "Preparing",
					"Step name attribute is not as expected");
			// SynTask ID should not be null.
			assertNotNull(response.jsonPath().getString("data.id"), "ID is null");
			// Upload URL should not be null.
			assertNotNull("included[0].attributes.uploadUrl", "Upload URL is null");
			// Assert id and type in the response
			assertTrue(response.jsonPath().getString("data.id").matches("[a-f0-9-]{36}"),
					"ID is not in expected format");
			assertEquals(response.jsonPath().getString("data.type"), "SyncTask", "Type attribute is not as expected");

		} catch (AssertionError e) {
			// Log the failure
			System.out.println("Assertion failed: " + e.getMessage());
			throw e;
		}
		blobUploadUrl = response.jsonPath().getString("included[0].attributes.uploadUrl");
		// Step 2: Upload Sync Task Zip File
		String url = blobUploadUrl;
		String file = "C:\\Users\\Public\\Documents\\ERPAutomation\\ColumnSwapping.zip";

		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setRequestMethod("PUT");
		connection.setRequestProperty("x-ms-blob-type", "BlockBlob");
		connection.setDoOutput(true);

		try (OutputStream outputStream = connection.getOutputStream();
				FileInputStream fileInputStream = new FileInputStream(new File(file))) {
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = fileInputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
		}

		int responseCode = connection.getResponseCode();
		assert responseCode == 201 : "File upload failed. Response code: " + responseCode;

		connection.disconnect();

		String updateEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id
				+ "/sync-tasks?filter=operation_type%20eq%20%27toNetwork%27&take=3&skip=0";
		String updaterequestBody = "{" + "\"data\": {" + "\"type\": \"synctask\"," + "\"id\": \"" + syncBatchID + "\","
				+ "\"attributes\": {" + "\"summary\": {" + "\"totalInvoices\": 1," + "\"totalPayments\": 2,"
				+ "\"totalCompanies\": 3," + "\"totalContacts\": 4," + "\"totalGlAccounts\": 5,"
				+ "\"totalGlAccountEntries\": 6," + "\"totalCustomFields\": 7" + "}" + "}," + "\"relationships\": {"
				+ "\"attachment\": {" + "\"data\": {" + "\"id\": \"" + syncAttachmentId + "\","
				+ "\"type\": \"attachment\"" + "}" + "}" + "}" + "}}";
		Response updateResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
				.header("Content-Type", "application/vnd.api+json").header("Authorization", "Bearer " + jwtToken)
				.header("Idempotency-Key", "fd45e434-c20d-4837-a076-a427b180a068").body(updaterequestBody)
				.patch(updateEndpoint);

		// Print the response
		// updateResponse.prettyPrint();

		// Assert the status code
		updateResponse.then().statusCode(202);

	}
	
	@Test(priority = 8)
	public void verify_AlphaNumeric_Empty_Sync_Response_Status() throws IOException, InterruptedException {
		// Step 1: Create Sync Task
		String endpointUrl = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks";

		// Request body
		String requestBody = "{\"data\":{\"type\":\"SyncTask\",\"attributes\":{\"packageType\":\"Full\"}}}";

		// Send POST request and capture response
		Response response = RestAssured.given().header("Content-Type", "application/vnd.api+json")
				.header("Accept", "application/vnd.api+json").header("Authorization", "Bearer " + jwtToken)
				.header("Idempotency-Key", "fd45e434-c20d-4837-a076-a427b180a068").body(requestBody).post(endpointUrl);

		// Print the response
		// response.prettyPrint();

		syncAttachmentId = response.jsonPath().getString("data.relationships.attachment.data.id");
		// Extract id from response and store it in the global variable
		syncBatchID = response.jsonPath().getString("data.id");
		// Assert the status code
		response.then().statusCode(201);

		// Assert attributes in the response
		try {
			assertEquals(response.jsonPath().getString("data.attributes.status"), "Awaiting",
					"Status attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.operationType"), "ToNetwork",
					"Operation type attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.packageType"), "Full",
					"Package type attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.stepName"), "Preparing",
					"Step name attribute is not as expected");
			// SynTask ID should not be null.
			assertNotNull(response.jsonPath().getString("data.id"), "ID is null");
			// Upload URL should not be null.
			assertNotNull("included[0].attributes.uploadUrl", "Upload URL is null");
			// Assert id and type in the response
			assertTrue(response.jsonPath().getString("data.id").matches("[a-f0-9-]{36}"),
					"ID is not in expected format");
			assertEquals(response.jsonPath().getString("data.type"), "SyncTask", "Type attribute is not as expected");

		} catch (AssertionError e) {
			// Log the failure
			System.out.println("Assertion failed: " + e.getMessage());
			throw e;
		}
		blobUploadUrl = response.jsonPath().getString("included[0].attributes.uploadUrl");
		// Step 2: Upload Sync Task Zip File
		String url = blobUploadUrl;
		String file = "C:\\Users\\Public\\Documents\\ERPAutomation\\123@qwe.zip";

		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setRequestMethod("PUT");
		connection.setRequestProperty("x-ms-blob-type", "BlockBlob");
		connection.setDoOutput(true);

		try (OutputStream outputStream = connection.getOutputStream();
				FileInputStream fileInputStream = new FileInputStream(new File(file))) {
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = fileInputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
		}

		int responseCode = connection.getResponseCode();
		assert responseCode == 201 : "File upload failed. Response code: " + responseCode;

		connection.disconnect();

		String updateEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id
				+ "/sync-tasks?filter=operation_type%20eq%20%27toNetwork%27&take=3&skip=0";
		String updaterequestBody = "{" + "\"data\": {" + "\"type\": \"synctask\"," + "\"id\": \"" + syncBatchID + "\","
				+ "\"attributes\": {" + "\"summary\": {" + "\"totalInvoices\": 1," + "\"totalPayments\": 2,"
				+ "\"totalCompanies\": 3," + "\"totalContacts\": 4," + "\"totalGlAccounts\": 5,"
				+ "\"totalGlAccountEntries\": 6," + "\"totalCustomFields\": 7" + "}" + "}," + "\"relationships\": {"
				+ "\"attachment\": {" + "\"data\": {" + "\"id\": \"" + syncAttachmentId + "\","
				+ "\"type\": \"attachment\"" + "}" + "}" + "}" + "}}";
		Response updateResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
				.header("Content-Type", "application/vnd.api+json").header("Authorization", "Bearer " + jwtToken)
				.header("Idempotency-Key", "fd45e434-c20d-4837-a076-a427b180a068").body(updaterequestBody)
				.patch(updateEndpoint);

		// Print the response
		// updateResponse.prettyPrint();

		// Assert the status code
		updateResponse.then().statusCode(202);

	}
	
	@Test(priority = 9)
	public void verify_ProtectedPDF_Empty_Sync_Response_Status() throws IOException, InterruptedException {
		// Step 1: Create Sync Task
		String endpointUrl = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks";

		// Request body
		String requestBody = "{\"data\":{\"type\":\"SyncTask\",\"attributes\":{\"packageType\":\"Full\"}}}";

		// Send POST request and capture response
		Response response = RestAssured.given().header("Content-Type", "application/vnd.api+json")
				.header("Accept", "application/vnd.api+json").header("Authorization", "Bearer " + jwtToken)
				.header("Idempotency-Key", "fd45e434-c20d-4837-a076-a427b180a068").body(requestBody).post(endpointUrl);

		// Print the response
		// response.prettyPrint();

		syncAttachmentId = response.jsonPath().getString("data.relationships.attachment.data.id");
		// Extract id from response and store it in the global variable
		syncBatchID = response.jsonPath().getString("data.id");
		// Assert the status code
		response.then().statusCode(201);

		// Assert attributes in the response
		try {
			assertEquals(response.jsonPath().getString("data.attributes.status"), "Awaiting",
					"Status attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.operationType"), "ToNetwork",
					"Operation type attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.packageType"), "Full",
					"Package type attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.stepName"), "Preparing",
					"Step name attribute is not as expected");
			// SynTask ID should not be null.
			assertNotNull(response.jsonPath().getString("data.id"), "ID is null");
			// Upload URL should not be null.
			assertNotNull("included[0].attributes.uploadUrl", "Upload URL is null");
			// Assert id and type in the response
			assertTrue(response.jsonPath().getString("data.id").matches("[a-f0-9-]{36}"),
					"ID is not in expected format");
			assertEquals(response.jsonPath().getString("data.type"), "SyncTask", "Type attribute is not as expected");

		} catch (AssertionError e) {
			// Log the failure
			System.out.println("Assertion failed: " + e.getMessage());
			throw e;
		}
		blobUploadUrl = response.jsonPath().getString("included[0].attributes.uploadUrl");
		// Step 2: Upload Sync Task Zip File
		String url = blobUploadUrl;
		String file = "C:\\Users\\Public\\Documents\\ERPAutomation\\dummy-protected.pdf";

		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setRequestMethod("PUT");
		connection.setRequestProperty("x-ms-blob-type", "BlockBlob");
		connection.setDoOutput(true);

		try (OutputStream outputStream = connection.getOutputStream();
				FileInputStream fileInputStream = new FileInputStream(new File(file))) {
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = fileInputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
		}

		int responseCode = connection.getResponseCode();
		assert responseCode == 201 : "File upload failed. Response code: " + responseCode;

		connection.disconnect();

		String updateEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id
				+ "/sync-tasks?filter=operation_type%20eq%20%27toNetwork%27&take=3&skip=0";
		String updaterequestBody = "{" + "\"data\": {" + "\"type\": \"synctask\"," + "\"id\": \"" + syncBatchID + "\","
				+ "\"attributes\": {" + "\"summary\": {" + "\"totalInvoices\": 1," + "\"totalPayments\": 2,"
				+ "\"totalCompanies\": 3," + "\"totalContacts\": 4," + "\"totalGlAccounts\": 5,"
				+ "\"totalGlAccountEntries\": 6," + "\"totalCustomFields\": 7" + "}" + "}," + "\"relationships\": {"
				+ "\"attachment\": {" + "\"data\": {" + "\"id\": \"" + syncAttachmentId + "\","
				+ "\"type\": \"attachment\"" + "}" + "}" + "}" + "}}";
		Response updateResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
				.header("Content-Type", "application/vnd.api+json").header("Authorization", "Bearer " + jwtToken)
				.header("Idempotency-Key", "fd45e434-c20d-4837-a076-a427b180a068").body(updaterequestBody)
				.patch(updateEndpoint);

		// Print the response
		// updateResponse.prettyPrint();

		// Assert the status code
		updateResponse.then().statusCode(202);

	}
	
	@Test(priority = 10)
	public void verify_ProtectedZip_Empty_Sync_Response_Status() throws IOException, InterruptedException {
		// Step 1: Create Sync Task
		String endpointUrl = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks";

		// Request body
		String requestBody = "{\"data\":{\"type\":\"SyncTask\",\"attributes\":{\"packageType\":\"Full\"}}}";

		// Send POST request and capture response
		Response response = RestAssured.given().header("Content-Type", "application/vnd.api+json")
				.header("Accept", "application/vnd.api+json").header("Authorization", "Bearer " + jwtToken)
				.header("Idempotency-Key", "fd45e434-c20d-4837-a076-a427b180a068").body(requestBody).post(endpointUrl);

		// Print the response
		// response.prettyPrint();

		syncAttachmentId = response.jsonPath().getString("data.relationships.attachment.data.id");
		// Extract id from response and store it in the global variable
		syncBatchID = response.jsonPath().getString("data.id");
		// Assert the status code
		response.then().statusCode(201);

		// Assert attributes in the response
		try {
			assertEquals(response.jsonPath().getString("data.attributes.status"), "Awaiting",
					"Status attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.operationType"), "ToNetwork",
					"Operation type attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.packageType"), "Full",
					"Package type attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.stepName"), "Preparing",
					"Step name attribute is not as expected");
			// SynTask ID should not be null.
			assertNotNull(response.jsonPath().getString("data.id"), "ID is null");
			// Upload URL should not be null.
			assertNotNull("included[0].attributes.uploadUrl", "Upload URL is null");
			// Assert id and type in the response
			assertTrue(response.jsonPath().getString("data.id").matches("[a-f0-9-]{36}"),
					"ID is not in expected format");
			assertEquals(response.jsonPath().getString("data.type"), "SyncTask", "Type attribute is not as expected");

		} catch (AssertionError e) {
			// Log the failure
			System.out.println("Assertion failed: " + e.getMessage());
			throw e;
		}
		blobUploadUrl = response.jsonPath().getString("included[0].attributes.uploadUrl");
		// Step 2: Upload Sync Task Zip File
		String url = blobUploadUrl;
		String file = "C:\\Users\\Public\\Documents\\ERPAutomation\\protected.zip";

		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setRequestMethod("PUT");
		connection.setRequestProperty("x-ms-blob-type", "BlockBlob");
		connection.setDoOutput(true);

		try (OutputStream outputStream = connection.getOutputStream();
				FileInputStream fileInputStream = new FileInputStream(new File(file))) {
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = fileInputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
		}

		int responseCode = connection.getResponseCode();
		assert responseCode == 201 : "File upload failed. Response code: " + responseCode;

		connection.disconnect();

		String updateEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id
				+ "/sync-tasks?filter=operation_type%20eq%20%27toNetwork%27&take=3&skip=0";
		String updaterequestBody = "{" + "\"data\": {" + "\"type\": \"synctask\"," + "\"id\": \"" + syncBatchID + "\","
				+ "\"attributes\": {" + "\"summary\": {" + "\"totalInvoices\": 1," + "\"totalPayments\": 2,"
				+ "\"totalCompanies\": 3," + "\"totalContacts\": 4," + "\"totalGlAccounts\": 5,"
				+ "\"totalGlAccountEntries\": 6," + "\"totalCustomFields\": 7" + "}" + "}," + "\"relationships\": {"
				+ "\"attachment\": {" + "\"data\": {" + "\"id\": \"" + syncAttachmentId + "\","
				+ "\"type\": \"attachment\"" + "}" + "}" + "}" + "}}";
		Response updateResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
				.header("Content-Type", "application/vnd.api+json").header("Authorization", "Bearer " + jwtToken)
				.header("Idempotency-Key", "fd45e434-c20d-4837-a076-a427b180a068").body(updaterequestBody)
				.patch(updateEndpoint);

		// Print the response
		// updateResponse.prettyPrint();

		// Assert the status code
		updateResponse.then().statusCode(202);

	}
}
