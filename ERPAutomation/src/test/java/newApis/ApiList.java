package newApis;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapper;
import io.restassured.response.Response;

public class ApiList {
	String apiBaseUrl = null;
	String database_id = null, jwtToken = null;

	@Test
	public void verify_observability_events() {

		// apiKeyAPI endpoint
		String apiKeyEndpoint = apiBaseUrl + "/datasets/" + database_id + "/observability/events";
		String requestBody = "{\n" + "  \"data\": {\n" + "    \"type\": \"Event\",\n" + "    \"attributes\": {\n"
				+ "      \"type\": \"SyncTaskEvent\",\n" + "      \"logs\": [\n" + "        {\n"
				+ "          \"type\": \"Heartbeat\",\n"
				+ "          \"id\": \"497f6eca-6276-4993-bfeb-53cbbbba6f08\",\n" + "          \"attributes\": {\n"
				+ "            \"severity\": \"Info\",\n" + "            \"detail\": \"string\",\n"
				+ "            \"timestamp\": \"2019-08-24T14:15:22Z\"\n" + "          }\n" + "        }\n"
				+ "      ]\n" + "    },\n" + "    \"relationships\": {\n" + "      \"syncTask\": {\n"
				+ "        \"type\": \"SyncTask\",\n" + "        \"id\": \"497f6eca-6276-4993-bfeb-53cbbbba6f08\"\n"
				+ "      }\n" + "    }\n" + "  }\n" + "}";

		Response response = RestAssured.given().header("Accept", "application/vnd.api+json")
				.header("Authorization", "Bearer " + jwtToken).header("Content-Type", "application/vnd.api+json")
				.header("Idempotency-Key", "").header("traceparent", "").header("tracestate", "").body(requestBody)
				.when().post(apiKeyEndpoint);

		response.prettyPrint();
		// Assert the status code...
		response.then().statusCode(200);
	}

	@Test
	public void verify_send() {
		String sendTasksEndpoint = apiBaseUrl + "/datasets/" + database_id + "/send-tasks";

		String sendTasksRequestBody = "{\n" + "  \"data\": {\n" + "    \"type\": \"SendTransactions\",\n"
				+ "    \"relationships\": {\n" + "      \"invoices\": {\n" + "        \"data\": [\n" + "          {\n"
				+ "            \"type\": \"Invoice\",\n" + "            \"id\": \"string\"\n" + "          }\n"
				+ "        ]\n" + "      }\n" + "    }\n" + "  }\n" + "}";

		Response sendTasksResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
				.header("Authorization", "Bearer " + jwtToken).header("Content-Type", "application/vnd.api+json")
				.header("Idempotency-Key", "").header("traceparent", "").header("tracestate", "")
				.body(sendTasksRequestBody).when().post(sendTasksEndpoint);

		// Print the response
		sendTasksResponse.prettyPrint();

		// Assert the status code
		sendTasksResponse.then().statusCode(200);
	}

	@Test
	public void verify_QuerySyncTask() {
		String syncTasksEndpoint = apiBaseUrl + "/datasets/" + database_id + "/sync-tasks";

		Response syncTasksResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
				.header("Authorization", "Bearer " + jwtToken).header("traceparent", "").header("tracestate", "").when()
				.get(syncTasksEndpoint);

		// Print the response
		syncTasksResponse.prettyPrint();

		// Assert the status code
		syncTasksResponse.then().statusCode(200);
	}

	@Test
	public void verify_create_sync() {
		String createSyncEndpoint = apiBaseUrl + "/datasets/" + database_id + "/sync-tasks";

		String createSyncRequestBody = "{\n" + "  \"data\": {\n" + "    \"type\": \"SyncTask\",\n"
				+ "    \"attributes\": {\n" + "      \"totalBatches\": 0,\n" + "      \"batchNumber\": 0,\n"
				+ "      \"packageType\": \"Full\"\n" + "    },\n" + "    \"relationships\": {\n"
				+ "      \"syncTask\": {\n" + "        \"data\": {\n" + "          \"type\": \"SyncTask\",\n"
				+ "          \"id\": \"497f6eca-6276-4993-bfeb-53cbbbba6f08\"\n" + "        }\n" + "      }\n"
				+ "    }\n" + "  }\n" + "}";

		Response createSyncResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
				.header("Authorization", "Bearer " + jwtToken).header("Content-Type", "application/vnd.api+json")
				.header("Idempotency-Key", "").header("traceparent", "").header("tracestate", "")
				.body(createSyncRequestBody).when().post(createSyncEndpoint);

		// Print the response
		createSyncResponse.prettyPrint();

		// Assert the status code
		createSyncResponse.then().statusCode(200); // Update the expected status code based on your API response.
	}

	@Test
	public void verify_patch_sync() {
		String patchSyncEndpoint = apiBaseUrl + "/datasets/" + database_id + "/sync-tasks";

		String patchSyncRequestBody = "{\n" + "  \"data\": {\n" + "    \"type\": \"SyncTask\",\n" + "    \"meta\": {\n"
				+ "      \"totalInvoices\": 0,\n" + "      \"totalPayments\": 0,\n" + "      \"totalCompanies\": 0,\n"
				+ "      \"totalContacts\": 0,\n" + "      \"totalGlAccounts\": 0,\n"
				+ "      \"totalGlAccountEntries\": 0,\n" + "      \"totalCustomFields\": 0\n" + "    },\n"
				+ "    \"relationships\": {\n" + "      \"attachment\": {\n" + "        \"data\": {\n"
				+ "          \"type\": \"Attachment\",\n"
				+ "          \"id\": \"497f6eca-6276-4993-bfeb-53cbbbba6f08\"\n" + "        }\n" + "      }\n"
				+ "    }\n" + "  }\n" + "}";

		Response patchSyncResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
				.header("Authorization", "Bearer " + jwtToken).header("Content-Type", "application/vnd.api+json")
				.header("Idempotency-Key", "").header("traceparent", "").header("tracestate", "")
				.body(patchSyncRequestBody).when().patch(patchSyncEndpoint);

		// Print the response
		patchSyncResponse.prettyPrint();

		// Assert the status code
		patchSyncResponse.then().statusCode(200); // Update the expected status code based on your API response.
	}

	@Test
	public void verify_get_sync_by_id() {
		String syncTasksByIdEndpoint = apiBaseUrl + "/datasets/" + database_id + "/sync-tasks/id";

		Response syncTasksByIdResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
				.header("Authorization", "Bearer " + jwtToken).header("traceparent", "").header("tracestate", "").when()
				.get(syncTasksByIdEndpoint);

		// Print the response
		syncTasksByIdResponse.prettyPrint();

		// Assert the status code
		syncTasksByIdResponse.then().statusCode(200); // Update the expected status code based on your API response.
	}

	@Test
	public void verify_delete_sync_by_id() {
		String syncTasksByIdEndpoint = apiBaseUrl + "/datasets/" + database_id + "/sync-tasks/id";

		Response deleteSyncByIdResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
				.header("Authorization", "Bearer " + jwtToken).header("traceparent", "").header("tracestate", "").when()
				.delete(syncTasksByIdEndpoint);

		// Print the response
		deleteSyncByIdResponse.prettyPrint();

		// Assert the status code
		deleteSyncByIdResponse.then().statusCode(204); // Update the expected status code based on your API response.
	}
}
