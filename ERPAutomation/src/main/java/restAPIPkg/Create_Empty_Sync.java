package restAPIPkg;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.testng.Assert;

import frameworkPkg.Helper;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Create_Empty_Sync extends Helper {

	private String syncBatchID, syncAttachmentId, blobUploadUrl;

	public Response without_Uploading_File_create_Empty_Sync() {
		String endpointUrl = syncAPIBaseURL + "/connectors/erp/datasets/" + syncAPIDataset_ID + "/sync-tasks";

		// Request body
		String requestBody = "{\"data\":{\"type\":\"SyncTask\",\"attributes\":{\"packageType\":\"Full\"}}}";

		// Send POST request and capture response
		Response response = RestAssured.given().header("Content-Type", "application/vnd.api+json")
				.header("Accept", "application/vnd.api+json").header("Authorization", "Bearer " + syncAPIJwtToken)
				.header("Idempotency-Key", "fd45e434-c20d-4837-a076-a427b180a068").body(requestBody).post(endpointUrl);
		syncAttachmentId = response.jsonPath().getString("data.relationships.attachment.data.id");
		// Extract id from response and store it in the global variable
		syncBatchID = response.jsonPath().getString("data.id");
		// Assert the status code
		int actualStatusCode = response.getStatusCode();
		int expectedStatusCode = 201;
		
		// Asserting the status code
		Assert.assertEquals(actualStatusCode, expectedStatusCode, "The status code is not as expected.");

		String updateEndpoint = syncAPIBaseURL + "/connectors/erp/datasets/" + syncAPIDataset_ID
				+ "/sync-tasks?filter=operation_type%20eq%20%27toNetwork%27&take=3&skip=0";
		String updaterequestBody = "{" + "\"data\": {" + "\"type\": \"synctask\"," + "\"id\": \"" + syncBatchID + "\","
				+ "\"attributes\": {" + "\"summary\": {" + "\"totalInvoices\": 1," + "\"totalPayments\": 2,"
				+ "\"totalCompanies\": 3," + "\"totalContacts\": 4," + "\"totalGlAccounts\": 5,"
				+ "\"totalGlAccountEntries\": 6," + "\"totalCustomFields\": 7" + "}" + "}," + "\"relationships\": {"
				+ "\"attachment\": {" + "\"data\": {" + "\"id\": \"" + syncAttachmentId + "\","
				+ "\"type\": \"attachment\"" + "}" + "}" + "}" + "}}";
		Response updateResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
				.header("Content-Type", "application/vnd.api+json").header("Authorization", "Bearer " + syncAPIJwtToken)
				.header("Idempotency-Key", "fd45e434-c20d-4837-a076-a427b180a068").body(updaterequestBody)
				.patch(updateEndpoint);
		updateResponse.then().statusCode(202);
		Response retriveTasksResponse = null;
		return retrieveTasks_Failed(syncBatchID, retriveTasksResponse);

	}
	public Response with_Uploading_File_create_Empty_Sync(String zipFile) throws FileNotFoundException, IOException {
		
		String endpointUrl = syncAPIBaseURL + "/connectors/erp/datasets/" + syncAPIDataset_ID + "/sync-tasks";

		// Request body
		String requestBody = "{\"data\":{\"type\":\"SyncTask\",\"attributes\":{\"packageType\":\"Full\"}}}";

		// Send POST request and capture response
		Response response = RestAssured.given().header("Content-Type", "application/vnd.api+json")
				.header("Accept", "application/vnd.api+json").header("Authorization", "Bearer " + syncAPIJwtToken)
				.header("Idempotency-Key", "fd45e434-c20d-4837-a076-a427b180a068").body(requestBody).post(endpointUrl);
		syncAttachmentId = response.jsonPath().getString("data.relationships.attachment.data.id");
		// Extract id from response and store it in the global variable
		syncBatchID = response.jsonPath().getString("data.id");
		// Assert the status code
		response.then().statusCode(201);
		blobUploadUrl = response.jsonPath().getString("included[0].attributes.uploadUrl");
		// Step 2: Upload Sync Task Zip File
		String url = blobUploadUrl;
		
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setRequestMethod("PUT");
		connection.setRequestProperty("x-ms-blob-type", "BlockBlob");
		connection.setDoOutput(true);

		try (OutputStream outputStream = connection.getOutputStream();
				FileInputStream fileInputStream = new FileInputStream(new File(zipFile))) {
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = fileInputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
		}

		int responseCode = connection.getResponseCode();
		assert responseCode == 201 : "File upload failed. Response code: " + responseCode;
		
		String updateEndpoint = syncAPIBaseURL + "/connectors/erp/datasets/" + syncAPIDataset_ID
				+ "/sync-tasks?filter=operation_type%20eq%20%27toNetwork%27&take=3&skip=0";
		String updaterequestBody = "{" + "\"data\": {" + "\"type\": \"synctask\"," + "\"id\": \"" + syncBatchID + "\","
				+ "\"attributes\": {" + "\"summary\": {" + "\"totalInvoices\": 1," + "\"totalPayments\": 2,"
				+ "\"totalCompanies\": 3," + "\"totalContacts\": 4," + "\"totalGlAccounts\": 5,"
				+ "\"totalGlAccountEntries\": 6," + "\"totalCustomFields\": 7" + "}" + "}," + "\"relationships\": {"
				+ "\"attachment\": {" + "\"data\": {" + "\"id\": \"" + syncAttachmentId + "\","
				+ "\"type\": \"attachment\"" + "}" + "}" + "}" + "}}";
		Response updateResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
				.header("Content-Type", "application/vnd.api+json").header("Authorization", "Bearer " + syncAPIJwtToken)
				.header("Idempotency-Key", "fd45e434-c20d-4837-a076-a427b180a068").body(updaterequestBody)
				.patch(updateEndpoint);
		updateResponse.then().statusCode(202);
		Response retriveTasksResponse = null;
		return retrieveTasks_Failed(syncBatchID, retriveTasksResponse);
	}
	
	private Response retrieveTasks_Failed(String syncBatchID, Response retriveTasksResponse) {
		String retriveTasksEndpoint = syncAPIBaseURL + "/connectors/erp/datasets/" + syncAPIDataset_ID + "/sync-tasks/"
				+ syncBatchID + "/?include=Details";

		// Define the maximum time to wait in seconds
		int maxWaitTimeInSeconds = 60; // 1 minute
		int elapsedTimeInSeconds = 0;

		// Keep checking the status until it becomes "Failed" or the maximum wait
		// time is reached
		while (elapsedTimeInSeconds < maxWaitTimeInSeconds) {
			// Send a request to retrieve the tasks
			retriveTasksResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
					.header("Authorization", "Bearer " + syncAPIJwtToken).get(retriveTasksEndpoint);
			// Print the response
			//retriveTasksResponse.prettyPrint();
			// Assert the status code
			retriveTasksResponse.then().statusCode(200);
			elapsedTimeInSeconds += 10; // Increment by 10 seconds
			//System.out.println(elapsedTimeInSeconds);

			String taskStatus = retriveTasksResponse.jsonPath().getString("data.attributes.status");
			//System.out.println(taskStatus);
			// Assert attributes in the response
			try {
				assertEquals(retriveTasksResponse.jsonPath().getString("data.attributes.status"), "Failed",
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

				// If all assertions pass, break the loop as the status is "Failed"
				break;
			} catch (AssertionError e) {

				// Sleep for a while before checking again
				if (taskStatus.equalsIgnoreCase("Failed")) {
					throw new RuntimeException("Sync status is " + taskStatus);
				}
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				} // Sleep for 10 seconds
					// Increment the elapsed time
			}
		}

		if (elapsedTimeInSeconds >= maxWaitTimeInSeconds) {
			// If the maximum wait time is reached and status is not Failed
			throw new RuntimeException("Max wait time exceeded. Status is not Failed.");
		}

		return retriveTasksResponse;
	}

}
