package happyPathScenarios;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import frameworkPkg.Helper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import restAPIPkg.Create_Batch_Sync;

public class Positive_Create_Batch_Sync extends Helper {
	private String syncBatchID;

	String apiBaseUrl = syncAPIBaseURL, dataset_id = syncAPIDataset_ID, jwtToken = syncAPIJwtToken;
	

	@Test(priority = 1)
	public void verify_Create_Sync_Batch_Upload_Full() {

		// apiKeyAPI endpoint
		String endpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks";
		String requestBody = Create_Batch_Sync.getBatchSyncBody("Full");
		// Send POST request
		Response response = RestAssured.given().header("Authorization", "Bearer " + jwtToken)
				.header("Content-Type", "application/vnd.api+json").header("Accept", "application/vnd.api+json")
				.header("Idempotency-Key", "fd45e434-c20d-4837-a076-a427b180a068").body(requestBody).post(endpoint);
		// Extract id from response and store it in the global variable
		syncBatchID = response.jsonPath().getString("data.id");
		// Print response
		// response.prettyPrint();
		// Assert the status code...
		response.then().statusCode(201);

		// Assert attributes in the response
		try {
			assertEquals(response.jsonPath().getString("data.attributes.status"), "Awaiting",
					"Status attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.operationType"), "ToNetwork",
					"Operation type attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.packageType"), "Full",
					"Package type attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.stepName"), "Ready",
					"Step name attribute is not as expected");

			// SynTask ID should not be null.
			assertNotNull("data.id", "ID is null");
			// Assert id and type in the response
			assertTrue(response.jsonPath().getString("data.id").matches("[a-f0-9-]{36}"),
					"ID is not in expected format");
		}

		catch (AssertionError e) {
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
			assertEquals(queryresponse.jsonPath().getString("data[0].attributes.status"), "Awaiting",
					"Status attribute is not as expected");
			assertEquals(queryresponse.jsonPath().getString("data[0].attributes.operationType"), "ToNetwork",
					"Operation type attribute is not as expected");
			assertEquals(queryresponse.jsonPath().getString("data[0].attributes.packageType"), "Full",
					"Package type attribute is not as expected");
			assertEquals(queryresponse.jsonPath().getString("data[0].attributes.stepName"), "Ready",
					"Step name attribute is not as expected");

			// Assert id and type in the response
			assertTrue(queryresponse.jsonPath().getString("data[0].id").matches("[a-f0-9-]{36}"),
					"ID is not in expected format");
			assertEquals(queryresponse.jsonPath().getString("data[0].type"), "SyncTask",
					"Type attribute is not as expected");

		} catch (AssertionError e) {
			// Log the failure
			System.out.println("Assertion failed: " + e.getMessage());
			throw e;
		}

		String retrieveEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks/" + syncBatchID
				+ "/?include=Details";

		Response retrieveResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
				.header("Authorization", "Bearer " + jwtToken).header("accept", "application/vnd.api+json")
				.get(retrieveEndpoint);

		// Print the response
		// sendTasksResponse.prettyPrint();

		// Assert the status code
		retrieveResponse.then().statusCode(200);

		// Assert attributes in the response
		try {
			assertEquals(retrieveResponse.jsonPath().getString("data.attributes.status"), "Awaiting",
					"Status attribute is not as expected");
			assertEquals(retrieveResponse.jsonPath().getString("data.attributes.operationType"), "ToNetwork",
					"Operation type attribute is not as expected");
			assertEquals(retrieveResponse.jsonPath().getString("data.attributes.packageType"), "Full",
					"Package type attribute is not as expected");
			assertEquals(retrieveResponse.jsonPath().getString("data.attributes.stepName"), "Ready",
					"Step name attribute is not as expected");

			// Assert id and type in the response
			assertTrue(retrieveResponse.jsonPath().getString("data.id").matches("[a-f0-9-]{36}"),
					"ID is not in expected format");
			assertEquals(retrieveResponse.jsonPath().getString("data.id"), syncBatchID,
					"Sync Batch ID is not matching as expected.");
			assertEquals(retrieveResponse.jsonPath().getString("data.type"), "SyncTask",
					"Type attribute is not as expected");

		} catch (AssertionError e) {
			// Log the failure
			System.out.println("Assertion failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(priority = 2)
	public void verify_Create_Sync_Batch_Upload_Partial() {

		// API endpoint
		String endpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks";
		String requestBody = Create_Batch_Sync.getBatchSyncBody("Partial");
		
		// Send POST request
		Response response = RestAssured.given().header("Authorization", "Bearer " + jwtToken)
				.header("Content-Type", "application/vnd.api+json").header("Accept", "application/vnd.api+json")
				.header("Idempotency-Key", "fd45e434-c20d-4837-a076-a427b180a068").body(requestBody).post(endpoint);
		// Extract id from response and store it in the global variable
		syncBatchID = response.jsonPath().getString("data.id");

		// Print response
		// response.prettyPrint();
		// Assert the status code...
		response.then().statusCode(201);

		// Assert attributes in the response
		try {
			assertEquals(response.jsonPath().getString("data.attributes.status"), "Awaiting",
					"Status attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.operationType"), "ToNetwork",
					"Operation type attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.packageType"), "Partial",
					"Package type attribute is not as expected");
			assertEquals(response.jsonPath().getString("data.attributes.stepName"), "Ready",
					"Step name attribute is not as expected");
			// SynTask ID should not be null.
			assertNotNull("data.id", "ID is null");
			// Assert id and type in the response
			assertTrue(response.jsonPath().getString("data.id").matches("[a-f0-9-]{36}"),
					"ID is not in expected format");
		}

		catch (AssertionError e) {
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
			assertEquals(queryresponse.jsonPath().getString("data[0].attributes.status"), "Awaiting",
					"Status attribute is not as expected");
			assertEquals(queryresponse.jsonPath().getString("data[0].attributes.operationType"), "ToNetwork",
					"Operation type attribute is not as expected");
			assertEquals(queryresponse.jsonPath().getString("data[0].attributes.packageType"), "Partial",
					"Package type attribute is not as expected");
			assertEquals(queryresponse.jsonPath().getString("data[0].attributes.stepName"), "Ready",
					"Step name attribute is not as expected");

			// Assert id and type in the response
			assertTrue(queryresponse.jsonPath().getString("data[0].id").matches("[a-f0-9-]{36}"),
					"ID is not in expected format");
			assertEquals(queryresponse.jsonPath().getString("data[0].type"), "SyncTask",
					"Type attribute is not as expected");

		} catch (AssertionError e) {
			// Log the failure
			System.out.println("Assertion failed: " + e.getMessage());
			throw e;
		}
		// Step 3: Retrieve sync API
		String retrieveEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks/" + syncBatchID
				+ "/?include=Details";

		Response retrieveResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
				.header("Authorization", "Bearer " + jwtToken).header("accept", "application/vnd.api+json")
				.get(retrieveEndpoint);

		// Print the response
		// retrieveResponse.prettyPrint();

		// Assert the status code
		retrieveResponse.then().statusCode(200);

		// Assert attributes in the response
		try {
			assertEquals(retrieveResponse.jsonPath().getString("data.attributes.status"), "Awaiting",
					"Status attribute is not as expected");
			assertEquals(retrieveResponse.jsonPath().getString("data.attributes.operationType"), "ToNetwork",
					"Operation type attribute is not as expected");
			assertEquals(retrieveResponse.jsonPath().getString("data.attributes.packageType"), "Partial",
					"Package type attribute is not as expected");
			assertEquals(retrieveResponse.jsonPath().getString("data.attributes.stepName"), "Ready",
					"Step name attribute is not as expected");

			// Assert id and type in the response
			assertTrue(retrieveResponse.jsonPath().getString("data.id").matches("[a-f0-9-]{36}"),
					"ID is not in expected format");
			assertEquals(retrieveResponse.jsonPath().getString("data.id"), syncBatchID,
					"Sync Batch ID is not matching as expected.");
			assertEquals(retrieveResponse.jsonPath().getString("data.type"), "SyncTask",
					"Type attribute is not as expected");

		} catch (AssertionError e) {
			// Log the failure
			System.out.println("Assertion failed: " + e.getMessage());
			throw e;
		}
	}
}
