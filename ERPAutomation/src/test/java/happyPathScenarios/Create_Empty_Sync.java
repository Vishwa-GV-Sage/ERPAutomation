package happyPathScenarios;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Create_Empty_Sync {
	private String syncTaskIdBatch, syncTaskIdEmpty, syncAttachmentId, blobUploadUrl;
	String apiBaseUrl = "https://api-dev.network-eng.sage.com";
	String database_id = "a37459d5-e9d6-4f1b-a6da-9bb8c329b188",
			jwtToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IkdoSWRxS2JCZld5UTNoeWs1Wmd3MiJ9.eyJwaWN0dXJlIjoiIiwiaHR0cHM6Ly9zYWdlLmNvbS9zY2kvYXppIjoiNWE3ZWYxYmYwNGU5MDRlMjZlN2NlMDg5N2U3ZWYyYTI0NTQ4ZDFlZGEyMTEiLCJodHRwczovL3NhZ2UuY29tL3NjaS9hem4iOiJERVZJQ0VOQU1FIiwiaHR0cHM6Ly9zYWdlLmNvbS9zY2kvYXpjIjoiQmFrZXJzIENha2VzIiwiaXNzIjoiaHR0cHM6Ly9pZC5hdXRoLXNoYWRvdy5zYWdlLmNvbS8iLCJzdWIiOiJhdXRoMHxhemlfNWE3ZWYxYmYwNGU5MDRlMjZlN2NlMDg5N2U3ZWYyYTI0NTQ4ZDFlZGEyMTEiLCJhdWQiOlsiaHR0cDovL3NhZ2UuY29tL2RlbW8vYXV0aGlkIiwiaHR0cHM6Ly9zYWdlLWF1dGgtc2hhZG93LnNhZ2VpZG5vbnByb2QuYXV0aDBhcHAuY29tL3VzZXJpbmZvIl0sImlhdCI6MTcwOTcwMDIxNCwiZXhwIjoxNzA5Nzg2NjE0LCJhenAiOiJvc2ZlbWMwUjd4d3hmc3dVQUhOVVU0cEdpTVg2MmtESiIsInNjb3BlIjoib3BlbmlkIHByb2ZpbGUgZW1haWwgYWRkcmVzcyBwaG9uZSBvZmZsaW5lX2FjY2VzcyIsImd0eSI6InBhc3N3b3JkIn0.u1JRh8E-we3KBjEod3ohjgCHYDgfWL0wfTHDeD6jg7RRzZMrrPOZzAx9Q9yBqVCcYudqMO9OQTyApTITRPBplCmnHK6GCmsTIBgjeVgWIIAJzo57cNcuXc9y2I1x2BtTIJYrNAsbOwp2N2fnHA-DXw5kzPIkBznbgVEviYjfwrNdPmds9Mr2XVcmBBFfXWTFLlWIw0_6DicWN-4uXcvYQrXuOzULft6IgR42FGra-Ob0Pi9FwXaXcxEXCGb3Xril-ky4QWNTIJRJVdy7OCUhavYhW6M8PKUCI3a4VeH950Kxj5yyjpKK0_GvzhKTzLhnCnTHOnQJZHZSWSyz5mmisQ";

	@Test(priority = 1)
	public void verify_Create_Sync_Task_Empty() {
		String endpointUrl = apiBaseUrl + "/connectors/erp/datasets/" + database_id + "/sync-tasks";

		// Request body
		String requestBody = "{\"data\":{\"type\":\"SyncTask\",\"attributes\":{\"packageType\":\"Full\"}}}";

		// Send POST request and capture response
		Response response = RestAssured.given().header("Content-Type", "application/vnd.api+json")
				.header("Accept", "application/vnd.api+json").header("Authorization", "Bearer " + jwtToken)
				.body(requestBody).post(endpointUrl);

		// Print the response
		// response.prettyPrint();
		blobUploadUrl = response.jsonPath().getString("included[0].attributes.uploadUrl");
	
		syncAttachmentId = response.jsonPath().getString("data.relationships.attachment.data.id");
		// Extract id from response and store it in the global variable
		syncTaskIdBatch = response.jsonPath().getString("data.id");
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

			// Assert id and type in the response
			assertTrue(response.jsonPath().getString("data.id").matches("[a-f0-9-]{36}"),
					"ID is not in expected format");
			assertEquals(response.jsonPath().getString("data.type"), "SyncTask", "Type attribute is not as expected");

		} catch (AssertionError e) {
			// Log the failure
			System.out.println("Assertion failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(priority = 2)
	public void upload_Sync_Task_Zip_File() throws IOException {
		String url = blobUploadUrl;
		String file = "C:/Users/rushabh.patel/Downloads/Sage 50 Zip file with Debit Memo.zip";

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
	}
	
	@Test(priority = 3)
	public void verify_Update_Synck() {
		String updateEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + database_id + "/sync-tasks?filter=operation_type%20eq%20%27toNetwork%27&take=3&skip=0";
		String requestBody = "{" +
			    "\"data\": {" +
			        "\"type\": \"synctask\"," +
			        "\"id\": \"" + syncTaskIdBatch + "\"," +
			        "\"attributes\": {" +
			            "\"summary\": {" +
			                "\"totalInvoices\": 1," +
			                "\"totalPayments\": 2," +
			                "\"totalCompanies\": 3," +
			                "\"totalContacts\": 4," +
			                "\"totalGlAccounts\": 5," +
			                "\"totalGlAccountEntries\": 6," +
			                "\"totalCustomFields\": 7" +
			            "}" +
			        "}," +
			        "\"relationships\": {" +
			            "\"attachment\": {" +
			                "\"data\": {" +
			                    "\"id\": \"" + syncAttachmentId + "\"," +
			                    "\"type\": \"attachment\"" +
			                "}" +
			            "}" +
			        "}" +
			    "}}";
		Response updateResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
				.header("Content-Type", "application/vnd.api+json")
				.header("Authorization", "Bearer " + jwtToken).body(requestBody).patch(updateEndpoint);
		
		// Print the response
		//updateResponse.prettyPrint();

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
	}

	@Test(priority = 4)
	public void verify_Query_Sync_Task_For_ToNetwork() {
		String queryTasksEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + database_id
				+ "/sync-tasks?filter=operationtype eq 'toNetwork'&take=3&skip=0";
		Response queryresponse = RestAssured.given().header("Content-Type", "application/vnd.api+json")
				.header("Accept", "application/vnd.api+json").header("Authorization", "Bearer " + jwtToken)
				.get(queryTasksEndpoint);
		// Print the response
		//queryresponse.prettyPrint();
		// Assert the status code
		queryresponse.then().statusCode(200);
	}
	


	@Test(priority = 5)
	public void verify_Retrieve_sync_task_for_ToNetwork() {
		String retriveTasksEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + database_id + "/sync-tasks/"
				+ syncTaskIdBatch + "/?include=Details";

		Response retriveTasksResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
				.header("Authorization", "Bearer " + jwtToken).get(retriveTasksEndpoint);

		// Print the response
		// sendTasksResponse.prettyPrint();

		// Assert the status code
		retriveTasksResponse.then().statusCode(200);

		// Assert attributes in the response
		try {
			assertEquals(retriveTasksResponse.jsonPath().getString("data.attributes.status"), "Awaiting",
					"Status attribute is not as expected");
			assertEquals(retriveTasksResponse.jsonPath().getString("data.attributes.operationType"), "ToNetwork",
					"Operation type attribute is not as expected");
			assertEquals(retriveTasksResponse.jsonPath().getString("data.attributes.packageType"), "Full",
					"Package type attribute is not as expected");
			assertEquals(retriveTasksResponse.jsonPath().getString("data.attributes.stepName"), "Ready",
					"Step name attribute is not as expected");
			assertTrue(retriveTasksResponse.jsonPath().getString("data.id").matches("[a-f0-9-]{36}"),
					"ID is not in expected format");
			assertEquals(retriveTasksResponse.jsonPath().getString("data.type"), "SyncTask",
					"Type attribute is not as expected");

		} catch (AssertionError e) {
			// Log the failure
			System.out.println("Assertion failed: " + e.getMessage());
			throw e;
		}
	}

}
