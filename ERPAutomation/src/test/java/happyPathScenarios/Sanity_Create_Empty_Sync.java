package happyPathScenarios;

import static org.testng.Assert.assertEquals;
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

public class Sanity_Create_Empty_Sync extends Helper {
	private String syncTaskIdBatch, syncAttachmentId, blobUploadUrl;
	String apiBaseUrl = syncAPIBaseURL, dataset_id = syncAPIDataset_ID, jwtToken = syncAPIJwtToken;

	@Test(priority = 1)
	public void verify_Create_Sync_Task_Empty_Response_Status() {
		String endpointUrl = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks";

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
		int expectedStatusCode = 201;
	    int actualStatusCode = response.getStatusCode();
	    assertEquals(actualStatusCode, expectedStatusCode, "Status code is not as expected");
		
	}

	@Test(priority = 2)
	public void verify_upload_Sync_Zip_File_Response_Status() throws IOException {
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
	}

	@Test(priority = 3)
	public void verify_Update_Sync_Response_Status() {
		String updateEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id
				+ "/sync-tasks?filter=operation_type%20eq%20%27toNetwork%27&take=3&skip=0";
		String requestBody = "{" + "\"data\": {" + "\"type\": \"synctask\"," + "\"id\": \"" + syncTaskIdBatch + "\","
				+ "\"attributes\": {" + "\"summary\": {" + "\"totalInvoices\": 1," + "\"totalPayments\": 2,"
				+ "\"totalCompanies\": 3," + "\"totalContacts\": 4," + "\"totalGlAccounts\": 5,"
				+ "\"totalGlAccountEntries\": 6," + "\"totalCustomFields\": 7" + "}" + "}," + "\"relationships\": {"
				+ "\"attachment\": {" + "\"data\": {" + "\"id\": \"" + syncAttachmentId + "\","
				+ "\"type\": \"attachment\"" + "}" + "}" + "}" + "}}";
		Response updateResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
				.header("Content-Type", "application/vnd.api+json").header("Authorization", "Bearer " + jwtToken)
				.body(requestBody).patch(updateEndpoint);

		// Print the response
		// updateResponse.prettyPrint();

		int expectedStatusCode = 202;
	    int actualStatusCode = updateResponse.getStatusCode();
	    assertEquals(actualStatusCode, expectedStatusCode, "Status code is not as expected");
	}

	@Test(priority = 4)
	public void verify_Query_Sync_Response_Status() {
		String queryTasksEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id
				+ "/sync-tasks?filter=operationtype eq 'toNetwork'&take=3&skip=0";
		Response queryresponse = RestAssured.given().header("Content-Type", "application/vnd.api+json")
				.header("Accept", "application/vnd.api+json").header("Authorization", "Bearer " + jwtToken)
				.get(queryTasksEndpoint);
		// Print the response
		// queryresponse.prettyPrint();
		// Assert the status code
	    int expectedStatusCode = 200;
	    int actualStatusCode = queryresponse.getStatusCode();
	    assertEquals(actualStatusCode, expectedStatusCode, "Status code is not as expected");
	}

	@Test(priority = 5)
	public void verify_Retrieve_Sync_Response_Status() {
		String retriveTasksEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks/"
				+ syncTaskIdBatch + "/?include=Details";

		Response retriveTasksResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
				.header("Authorization", "Bearer " + jwtToken).get(retriveTasksEndpoint);

		// Print the response
		// sendTasksResponse.prettyPrint();

		// Assert the status code
	    int expectedStatusCode = 200;
	    int actualStatusCode = retriveTasksResponse.getStatusCode();
	    assertEquals(actualStatusCode, expectedStatusCode, "Status code is not as expected");
	}

}
