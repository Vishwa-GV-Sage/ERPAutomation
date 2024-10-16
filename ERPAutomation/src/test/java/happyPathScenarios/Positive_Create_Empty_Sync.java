package happyPathScenarios;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.testng.Assert;
import org.testng.annotations.Test;

import frameworkPkg.Helper;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Positive_Create_Empty_Sync extends Helper {
	private String syncBatchID, syncAttachmentId, blobUploadUrl;
	String apiBaseUrl = syncAPIBaseURL, dataset_id = syncAPIDataset_ID, jwtToken = syncAPIJwtToken;
	// Define the maximum time to wait in seconds
	int maxWaitTimeInSeconds = 300;

	@Test(priority = 1)
	public void verify_Create_Sync_Task_Empty_Full() throws IOException, InterruptedException {
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
		Assert.assertEquals(response.getStatusCode(), 201, "Actual Response is :" + response.prettyPrint());
		System.out.println("Status:" + response.jsonPath().getString("data.attributes.status"));
		// Assert attributes in the response
		try {
			assertEquals(response.jsonPath().getString("data.attributes.status"), "Awaiting",
					"Step1: Status attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.operationType"), "ToNetwork",
					"Step1: Operation type attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.packageType"), "Full",
					"Step1: Package type attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.stepName"), "Preparing",
					"Step1: Step name attribute is not as expected");
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
		String file = zipFile;

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

		Assert.assertEquals(updateResponse.getStatusCode(), 202, "Actual Response is :" + updateResponse.prettyPrint());
		// Assert attributes in the response
		try {
			assertEquals(updateResponse.jsonPath().getString("data.attributes.status"), "Awaiting",
					"Step3: Status attribute is not as expected");
			assertEquals(updateResponse.jsonPath().getString("data.attributes.operationType"), "ToNetwork",
					"Step3:Operation type attribute is not as expected");
			assertEquals(updateResponse.jsonPath().getString("data.attributes.packageType"), "Full",
					"Step3:Package type attribute is not as expected");
			assertEquals(updateResponse.jsonPath().getString("data.attributes.stepName"), "Ready",
					"Step3:Step name attribute is not as expected");
			assertTrue(updateResponse.jsonPath().getString("data.id").matches("[a-f0-9-]{36}"),
					"Step3:ID is not in expected format");
			assertEquals(updateResponse.jsonPath().getString("data.type"), "SyncTask",
					"Step3:Type attribute is not as expected");

		} catch (AssertionError e) {
			// Log the failure
			System.out.println("Assertion failed: " + e.getMessage());
			throw e;
		}

		String queryTasksEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id
				+ "/sync-tasks?filter=operationtype eq 'toNetwork'&take=3&skip=0";
		Response queryresponse = RestAssured.given().header("Content-Type", "application/vnd.api+json")
				.header("Accept", "application/vnd.api+json").header("Authorization", "Bearer " + jwtToken)
				.get(queryTasksEndpoint);
		// Print the response
		// queryresponse.prettyPrint();
		// Assert the status code

		// Assert attributes in the response
		Assert.assertEquals(queryresponse.getStatusCode(), 200, "Actual Response is :" + queryresponse.prettyPrint());
		String retriveTasksEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks/"
				+ syncBatchID + "/?include=Details";

		Response retriveTasksResponse;

		int elapsedTimeInSeconds = 0;
		String taskStatus = null;
		// Keep checking the status until it becomes "Completed" or the maximum wait
		// time is reached
		while (elapsedTimeInSeconds < maxWaitTimeInSeconds) {
			// Send a request to retrieve the tasks
			retriveTasksResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
					.header("Authorization", "Bearer " + jwtToken).get(retriveTasksEndpoint);
			if (elapsedTimeInSeconds == 0) {
				retriveTasksResponse.prettyPrint();
			} else {
				System.out.println(elapsedTimeInSeconds + "...");
			}
			// Print the response
			// retriveTasksResponse.prettyPrint();
			// Assert the status code
			Assert.assertEquals(retriveTasksResponse.getStatusCode(), 200);
			elapsedTimeInSeconds += 5; // Increment by 10 seconds

			taskStatus = retriveTasksResponse.jsonPath().getString("data.attributes.status");
			System.out.println(taskStatus);
			// Assert attributes in the response
			try {
				assertEquals(taskStatus, "Completed", "Step4:Status attribute is not as expected");

				// If all assertions pass, break the loop as the status is "Completed"
				break;
			} catch (AssertionError e) {
				// Log the failure
				// System.out.println("Assertion failed: " + e.getMessage());
				// Sleep for a while before checking again
				if (taskStatus.equalsIgnoreCase("Failed")) {
					// throw new RuntimeException("Sync status is" + taskStatus);
					assertEquals(taskStatus, "Completed", "Sync task status is " + taskStatus);
				}
				Thread.sleep(10000); // Sleep for 10 seconds
				// Increment the elapsed time

			}

		}

		if (elapsedTimeInSeconds >= maxWaitTimeInSeconds) {
			// If the maximum wait time is reached and status is not completed
			// throw new RuntimeException("Max wait time exceeded. Status is not
			// completed.");
			assertEquals(taskStatus, "Completed",
					"Sync task status should be Completed after waiting of " + maxWaitTimeInSeconds + " Seconds");
		}
	}

	@Test(priority = 2)
	public void verify_Create_Sync_Task_Empty_Partial() throws IOException, InterruptedException {
		// Step 1: Create Sync Task
		String endpointUrl = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks";

		// Request body
		String requestBody = "{\"data\":{\"type\":\"SyncTask\",\"attributes\":{\"packageType\":\"Partial\"}}}";

		// Send POST request and capture response
		Response response = RestAssured.given().header("Content-Type", "application/vnd.api+json")
				.header("Accept", "application/vnd.api+json").header("Authorization", "Bearer " + jwtToken)
				.header("Idempotency-Key", "fd45e434-c20d-4837-a076-a427b180a068").body(requestBody).post(endpointUrl);

		syncAttachmentId = response.jsonPath().getString("data.relationships.attachment.data.id");
		// Extract id from response and store it in the global variable
		syncBatchID = response.jsonPath().getString("data.id");

		Assert.assertEquals(response.getStatusCode(), 201, "Actual Response is :" + response.prettyPrint());

		// Assert attributes in the response
		try {
			assertEquals(response.jsonPath().getString("data.attributes.status"), "Awaiting",
					"Step1: Status attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.operationType"), "ToNetwork",
					"Step1: Operation type attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.packageType"), "Partial",
					"Step1: Package type attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.stepName"), "Preparing",
					"Step1: Step name attribute is not as expected");
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
		String file = zipFile;
		// String file = "C:\\Users\\Public\\Documents\\Temp\\company.zip";
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
		Assert.assertEquals(updateResponse.getStatusCode(), 202,
				"Actual Update Response is :" + updateResponse.prettyPrint());
		// Assert attributes in the response
		try {
			assertEquals(updateResponse.jsonPath().getString("data.attributes.status"), "Awaiting",
					"Step3: Status attribute is not as expected");
			assertEquals(updateResponse.jsonPath().getString("data.attributes.operationType"), "ToNetwork",
					"Step3:Operation type attribute is not as expected");
			assertEquals(updateResponse.jsonPath().getString("data.attributes.packageType"), "Partial",
					"Step3:Package type attribute is not as expected");
			assertEquals(updateResponse.jsonPath().getString("data.attributes.stepName"), "Ready",
					"Step3:Step name attribute is not as expected");
			assertTrue(updateResponse.jsonPath().getString("data.id").matches("[a-f0-9-]{36}"),
					"Step3:ID is not in expected format");
			assertEquals(updateResponse.jsonPath().getString("data.type"), "SyncTask",
					"Step3:Type attribute is not as expected");

		} catch (AssertionError e) {
			// Log the failure
			System.out.println("Assertion failed: " + e.getMessage());
			throw e;
		}

		String queryTasksEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id
				+ "/sync-tasks?filter=operationtype eq 'toNetwork'&take=3&skip=0";
		Response queryresponse = RestAssured.given().header("Content-Type", "application/vnd.api+json")
				.header("Accept", "application/vnd.api+json").header("Authorization", "Bearer " + jwtToken)
				.get(queryTasksEndpoint);
		Assert.assertEquals(queryresponse.getStatusCode(), 200,
				"Actual Query Response is :" + queryresponse.prettyPrint());
		String retriveTasksEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks/"
				+ syncBatchID + "/?include=Details";

		Response retriveTasksResponse;

		int elapsedTimeInSeconds = 0;
		String taskStatus = null, stepName = null;

		// Keep checking the status until it becomes "Completed" or the maximum wait
		// time is reached
		while (elapsedTimeInSeconds < maxWaitTimeInSeconds) {

			elapsedTimeInSeconds += 10; // Increment by 10 seconds
			// Send a request to retrieve the tasks
			retriveTasksResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
					.header("Authorization", "Bearer " + jwtToken).get(retriveTasksEndpoint);
			// Print the response
			if (elapsedTimeInSeconds == 0) {
				retriveTasksResponse.prettyPrint();
			} else {
				System.out.println(elapsedTimeInSeconds + "...");
			}
			// Assert the status code
			Assert.assertEquals(retriveTasksResponse.getStatusCode(), 200);
			taskStatus = retriveTasksResponse.jsonPath().getString("data.attributes.status");
			System.out.println(taskStatus);
			stepName = retriveTasksResponse.jsonPath().getString("data.attributes.stepName");
			// Assert attributes in the response
			try {
				assertEquals(retriveTasksResponse.jsonPath().getString("data.attributes.status"), "Completed",
						"Step4:Status attribute is not as expected");
				assertEquals(retriveTasksResponse.jsonPath().getString("data.attributes.operationType"), "ToNetwork",
						"Step4:Operation type attribute is not as expected");
				assertEquals(retriveTasksResponse.jsonPath().getString("data.attributes.packageType"), "Partial",
						"Step4:Package type attribute is not as expected");
				assertEquals(retriveTasksResponse.jsonPath().getString("data.attributes.stepName"), "Finalized",
						"Step4:Step name attribute is not as expected");
				assertTrue(retriveTasksResponse.jsonPath().getString("data.id").matches("[a-f0-9-]{36}"),
						"Step4:ID is not in expected format");
				assertEquals(retriveTasksResponse.jsonPath().getString("data.type"), "SyncTask",
						"Step4:Type attribute is not as expected");
				assertEquals(retriveTasksResponse.jsonPath().getString("data.id"), syncBatchID,
						"Step4:Sync Task ID is not as expected");

				// If all assertions pass, break the loop as the status is "Completed"
				break;
			} catch (AssertionError e) {
				// Log the failure
				// System.out.println("Assertion failed: " + e.getMessage());
				// Sleep for a while before checking again

				Thread.sleep(10000); // Sleep for 10 seconds
				// Increment the elapsed time

				// System.out.println(elapsedTimeInSeconds);
			}
		}

		if (elapsedTimeInSeconds >= maxWaitTimeInSeconds) {
			// If the maximum wait time is reached and status is not completed
			// throw new RuntimeException("Max wait time exceeded. Status is not
			// completed.");

			assertEquals(taskStatus, "Completed",
					"Sync task status should be Completed after waiting of " + maxWaitTimeInSeconds + " Seconds");
		}
	}

	@Test(priority = 3)
	public void verify_CompanyIdentifier_Empty_Partial() throws IOException, InterruptedException {
		// Step 1: Create Sync Task
		String endpointUrl = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks";

		// Request body
		String requestBody = "{\"data\":{\"type\":\"SyncTask\",\"attributes\":{\"packageType\":\"Partial\"}}}";

		// Send POST request and capture response
		Response response = RestAssured.given().header("Content-Type", "application/vnd.api+json")
				.header("Accept", "application/vnd.api+json").header("Authorization", "Bearer " + jwtToken)
				.header("Idempotency-Key", "fd45e434-c20d-4837-a076-a427b180a068").body(requestBody).post(endpointUrl);

		syncAttachmentId = response.jsonPath().getString("data.relationships.attachment.data.id");
		// Extract id from response and store it in the global variable
		syncBatchID = response.jsonPath().getString("data.id");

		Assert.assertEquals(response.getStatusCode(), 201, "Actual Response is :" + response.prettyPrint());

		blobUploadUrl = response.jsonPath().getString("included[0].attributes.uploadUrl");

		// Step 2: Upload Sync Task Zip File
		String url = blobUploadUrl;
		//String file = zipFile;
		String file = "C:\\Users\\Public\\Documents\\ERPAutomation\\Company Identifier small zip.zip";
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
		// Step 3: Update sync task
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
		
		Assert.assertEquals(updateResponse.getStatusCode(), 202,
				"Actual Update Response is :" + updateResponse.prettyPrint());
		// Assert attributes in the response
		try {
			assertEquals(updateResponse.jsonPath().getString("data.attributes.status"), "Awaiting",
					"Step3: Status attribute is not as expected");

		} catch (AssertionError e) {
			// Log the failure
			System.out.println("Assertion failed: " + e.getMessage());
			throw e;
		}

		String queryTasksEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id
				+ "/sync-tasks?filter=operationtype eq 'toNetwork'&take=3&skip=0";
		Response queryresponse = RestAssured.given().header("Content-Type", "application/vnd.api+json")
				.header("Accept", "application/vnd.api+json").header("Authorization", "Bearer " + jwtToken)
				.get(queryTasksEndpoint);
		System.out.println("===========================================================================");
		Assert.assertEquals(queryresponse.getStatusCode(), 200,
				"Actual Query Response is :" + queryresponse.prettyPrint());
		String retriveTasksEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks/"
				+ syncBatchID + "/?include=Details";

		Response retriveTasksResponse;

		int elapsedTimeInSeconds = 0;
		String taskStatus = null, stepName = null;

		// Keep checking the status until it becomes "Completed" or the maximum wait
		// time is reached
		while (elapsedTimeInSeconds < maxWaitTimeInSeconds) {

			elapsedTimeInSeconds += 10; // Increment by 10 seconds
			// Send a request to retrieve the tasks
			retriveTasksResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
					.header("Authorization", "Bearer " + jwtToken).get(retriveTasksEndpoint);
			// Print the response
			if (elapsedTimeInSeconds == 0) {
				retriveTasksResponse.prettyPrint();
			} else {
				System.out.println(elapsedTimeInSeconds + "...");
			}
			// Assert the status code
			Assert.assertEquals(retriveTasksResponse.getStatusCode(), 200);
			taskStatus = retriveTasksResponse.jsonPath().getString("data.attributes.status");
			System.out.println(taskStatus);
			stepName = retriveTasksResponse.jsonPath().getString("data.attributes.stepName");
			// Assert attributes in the response
			try {
				assertEquals(retriveTasksResponse.jsonPath().getString("data.attributes.status"), "Completed",
						"Step4:Status attribute is not as expected");
				break;
			} catch (AssertionError e) {

				Thread.sleep(10000); // Sleep for 10 seconds

			}
		}

		if (elapsedTimeInSeconds >= maxWaitTimeInSeconds) {
			assertEquals(taskStatus, "Completed",
					"Sync task status should be Completed after waiting of " + maxWaitTimeInSeconds + " Seconds");
		}
		
		String companiesQueryEndpoint = apiBaseUrl + "/api/v1/Companies/query";
		
		Response companiesQueryResponse=RestAssured.given().header("X-Group-Key", dataset_id).header("Authorization", "Bearer " + syncAPIJwtToken_SageUser)
				.get(companiesQueryEndpoint);
		companiesQueryResponse.prettyPrint();
		
		assertEquals(companiesQueryResponse.jsonPath().getString("records[1].companyIdentifiers[0].type"), "SIRENSIRET",
				"Company Identified attribute type is not as expected");
		assertEquals(companiesQueryResponse.jsonPath().getString("records[1].companyIdentifiers[0].value"), "632012",
				"Company Identified attribute type is not as expected");
		assertEquals(companiesQueryResponse.jsonPath().getString("records[1].companyIdentifiers[0].jurisdiction"), "FR",
				"Company Identified attribute type is not as expected");
		assertEquals(companiesQueryResponse.jsonPath().getString("records[1].companyIdentifiers[0].subjurisdiction"), "FR",
				"Company Identified attribute type is not as expected");

		
		assertEquals(companiesQueryResponse.jsonPath().getString("records[1].companyIdentifiers[1].type"), "EIN_NUMBER",
				"Company Identified attribute type is not as expected");
		assertEquals(companiesQueryResponse.jsonPath().getString("records[1].companyIdentifiers[1].value"), "6320567",
				"Company Identified attribute type is not as expected");
		assertEquals(companiesQueryResponse.jsonPath().getString("records[1].companyIdentifiers[1].jurisdiction"), "FR",
				"Company Identified attribute type is not as expected");
		assertEquals(companiesQueryResponse.jsonPath().getString("records[1].companyIdentifiers[1].subjurisdiction"), "FR",
				"Company Identified attribute type is not as expected");
		
	}
}
