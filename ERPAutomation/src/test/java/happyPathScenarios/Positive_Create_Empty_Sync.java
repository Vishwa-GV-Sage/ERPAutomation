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

import org.testng.annotations.Test;

import frameworkPkg.Helper;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Positive_Create_Empty_Sync extends Helper {
	private String syncBatchID, syncAttachmentId, blobUploadUrl;
	String apiBaseUrl = syncAPIBaseURL, dataset_id = syncAPIDataset_ID, jwtToken = syncAPIJwtToken;

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
		updateResponse.then().statusCode(202);

		// Assert attributes in the response
		try {
			assertEquals(updateResponse.jsonPath().getString("data.attributes.status"), "Awaiting",
					"Status attribute is not as expected");
			assertEquals(updateResponse.jsonPath().getString("data.attributes.operationType"), "ToNetwork",
					"Operation type attribute is not as expected");
			assertEquals(updateResponse.jsonPath().getString("data.attributes.packageType"), "Full",
					"Package type attribute is not as expected");
			assertEquals(updateResponse.jsonPath().getString("data.attributes.stepName"), "Ready",
					"Step name attribute is not as expected");
			assertTrue(updateResponse.jsonPath().getString("data.id").matches("[a-f0-9-]{36}"),
					"ID is not in expected format");
			assertEquals(updateResponse.jsonPath().getString("data.type"), "SyncTask",
					"Type attribute is not as expected");

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
		queryresponse.then().statusCode(200);
		// Assert attributes in the response
		try {
			assertEquals(queryresponse.jsonPath().getString("data[0].data.attributes.status"), "Awaiting",
					"Status attribute is not as expected");
			assertEquals(queryresponse.jsonPath().getString("data[0].data.attributes.operationType"), "ToNetwork",
					"Operation type attribute is not as expected");
			assertEquals(queryresponse.jsonPath().getString("data[0].data.attributes.packageType"), "Full",
					"Package type attribute is not as expected");
			assertEquals(queryresponse.jsonPath().getString("data[0].data.attributes.stepName"), "Ready",
					"Step name attribute is not as expected");
			assertEquals(queryresponse.jsonPath().getString("data[0].data.id"), syncBatchID,
					"Sync Task ID is not as expected");
			// Assert id and type in the response
			assertTrue(queryresponse.jsonPath().getString("data[0].data.id").matches("[a-f0-9-]{36}"),
					"ID is not in expected format");
			assertEquals(queryresponse.jsonPath().getString("data[0].data.type"), "SyncTask",
					"Type attribute is not as expected");

		} catch (AssertionError e) {
			// Log the failure
			System.out.println("Assertion failed: " + e.getMessage());
			throw e;
		}
		String retriveTasksEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks/"
				+ syncBatchID + "/?include=Details";

		Response retriveTasksResponse;

		// Define the maximum time to wait in seconds
		int maxWaitTimeInSeconds = 900; // 15 minutes
		int elapsedTimeInSeconds = 0;

		// Keep checking the status until it becomes "Completed" or the maximum wait
		// time is reached
		while (elapsedTimeInSeconds < maxWaitTimeInSeconds) {
			// Send a request to retrieve the tasks
			retriveTasksResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
					.header("Authorization", "Bearer " + jwtToken).get(retriveTasksEndpoint);

			// Assert the status code
			retriveTasksResponse.then().statusCode(200);
			elapsedTimeInSeconds += 10; // Increment by 10 seconds

			// Assert attributes in the response
			try {
				assertEquals(retriveTasksResponse.jsonPath().getString("data.attributes.status"), "Completed",
						"Status attribute is not as expected");
				assertEquals(retriveTasksResponse.jsonPath().getString("data.attributes.operationType"), "ToNetwork",
						"Operation type attribute is not as expected");
				assertEquals(retriveTasksResponse.jsonPath().getString("data.attributes.packageType"), "Full",
						"Package type attribute is not as expected");
				assertEquals(retriveTasksResponse.jsonPath().getString("data.attributes.stepName"), "Finalized",
						"Step name attribute is not as expected");
				assertTrue(retriveTasksResponse.jsonPath().getString("data.id").matches("[a-f0-9-]{36}"),
						"ID is not in expected format");
				assertEquals(retriveTasksResponse.jsonPath().getString("data.type"), "SyncTask",
						"Type attribute is not as expected");
				assertEquals(retriveTasksResponse.jsonPath().getString("data.id"), syncBatchID,
						"Sync Task ID is not as expected");

				// If all assertions pass, break the loop as the status is "Completed"
				break;
			} catch (AssertionError e) {
				// Log the failure
				// System.out.println("Assertion failed: " + e.getMessage());
				// Sleep for a while before checking again

				Thread.sleep(10000); // Sleep for 10 seconds
				// Increment the elapsed time

			}
		}

		if (elapsedTimeInSeconds >= maxWaitTimeInSeconds) {
			// If the maximum wait time is reached and status is not completed
			throw new RuntimeException("Max wait time exceeded. Status is not completed.");
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
			assertEquals(response.jsonPath().getString("data.attributes.packageType"), "Partial",
					"Package type attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.stepName"), "Preparing",
					"Step name attribute is not as expected");
			// Upload URL should not be null.
			assertNotNull("included[0].attributes.uploadUrl", "Upload URL is null");
			// SynTask ID should not be null.
			assertNotNull(response.jsonPath().getString("data.id"), "ID is null");
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
		updateResponse.then().statusCode(202);

		// Assert attributes in the response
		try {
			assertEquals(updateResponse.jsonPath().getString("data.attributes.status"), "Awaiting",
					"Status attribute is not as expected");
			assertEquals(updateResponse.jsonPath().getString("data.attributes.operationType"), "ToNetwork",
					"Operation type attribute is not as expected");
			assertEquals(updateResponse.jsonPath().getString("data.attributes.packageType"), "Partial",
					"Package type attribute is not as expected");
			assertEquals(updateResponse.jsonPath().getString("data.attributes.stepName"), "Ready",
					"Step name attribute is not as expected");
			assertTrue(updateResponse.jsonPath().getString("data.id").matches("[a-f0-9-]{36}"),
					"ID is not in expected format");
			assertEquals(updateResponse.jsonPath().getString("data.type"), "SyncTask",
					"Type attribute is not as expected");

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
		queryresponse.then().statusCode(200);
		// Assert attributes in the response
		try {
			assertEquals(queryresponse.jsonPath().getString("data[0].data.attributes.status"), "Awaiting",
					"Status attribute is not as expected");
			assertEquals(queryresponse.jsonPath().getString("data[0].data.attributes.operationType"), "ToNetwork",
					"Operation type attribute is not as expected");
			assertEquals(queryresponse.jsonPath().getString("data[0].data.attributes.packageType"), "Partial",
					"Package type attribute is not as expected");
			assertEquals(queryresponse.jsonPath().getString("data[0].data.attributes.stepName"), "Ready",
					"Step name attribute is not as expected");

			// Assert id and type in the response
			assertTrue(queryresponse.jsonPath().getString("data[0].data.id").matches("[a-f0-9-]{36}"),
					"ID is not in expected format");
			assertEquals(queryresponse.jsonPath().getString("data[0].data.type"), "SyncTask",
					"Type attribute is not as expected");

		} catch (AssertionError e) {
			// Log the failure
			// System.out.println("Assertion failed: " + e.getMessage());
			throw e;
		}

		String retriveTasksEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks/"
				+ syncBatchID + "/?include=Details";

		Response retriveTasksResponse;

		// Define the maximum time to wait in seconds
		int maxWaitTimeInSeconds = 900; // 15 minutes
		int elapsedTimeInSeconds = 0;

		// Keep checking the status until it becomes "Completed" or the maximum wait
		// time is reached
		while (elapsedTimeInSeconds < maxWaitTimeInSeconds) {

			elapsedTimeInSeconds += 10; // Increment by 10 seconds
			// Send a request to retrieve the tasks
			retriveTasksResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
					.header("Authorization", "Bearer " + jwtToken).get(retriveTasksEndpoint);

			// Assert the status code
			retriveTasksResponse.then().statusCode(200);

			// Assert attributes in the response
			try {
				assertEquals(retriveTasksResponse.jsonPath().getString("data.attributes.status"), "Completed",
						"Status attribute is not as expected");
				assertEquals(retriveTasksResponse.jsonPath().getString("data.attributes.operationType"), "ToNetwork",
						"Operation type attribute is not as expected");
				assertEquals(retriveTasksResponse.jsonPath().getString("data.attributes.packageType"), "Partial",
						"Package type attribute is not as expected");
				assertEquals(retriveTasksResponse.jsonPath().getString("data.attributes.stepName"), "Finalized",
						"Step name attribute is not as expected");
				assertTrue(retriveTasksResponse.jsonPath().getString("data.id").matches("[a-f0-9-]{36}"),
						"ID is not in expected format");
				assertEquals(retriveTasksResponse.jsonPath().getString("data.type"), "SyncTask",
						"Type attribute is not as expected");
				assertEquals(retriveTasksResponse.jsonPath().getString("data.id"), syncBatchID,
						"Sync Task ID is not as expected");

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
			throw new RuntimeException("Max wait time exceeded. Status is not completed.");
		}
	}
}
