package negativeScenarios;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import frameworkPkg.Helper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import restAPIPkg.Create_Batch_Sync;

public class Invalid_Create_Batch_Sync extends Helper {

	String apiBaseUrl = syncAPIBaseURL, dataset_id = syncAPIDataset_ID, jwtToken = syncAPIJwtToken, syncTaskIdBatch;
	String expiredToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IlJVUXlRME0wTWtOQk5rUkJOVGxETXpaQ056VXhPVVUyUkRZNU5EbEJOVVF6T0VSQk1EQXdNQSJ9.eyJpc3MiOiJodHRwczovL2lkLXNoYWRvdy5zYWdlLmNvbS8iLCJzdWIiOiJhdXRoMHw0MTc3NzcyMjhlNGZmMzBhMmJkNjI2NDdkZmJmN2VjMTEyMTNjZDYyMzYwNzViZDMiLCJhdWQiOlsiU0JDRFMvZ2xvYmFsIiwiaHR0cHM6Ly9zYWdlLWNpZC1zaGFkb3cuc2FnZWlkbm9ucHJvZC5hdXRoMGFwcC5jb20vdXNlcmluZm8iXSwiaWF0IjoxNzA5NzM1MTMzLCJleHAiOjE3MDk3NjM5MzMsImF6cCI6Ilduczl0TlViWTNDdktONXNFb1p3S3pTYzVrd081UVVaIiwic2NvcGUiOiJvcGVuaWQgcHJvZmlsZSBlbWFpbCB1c2VyOmZ1bGwgb2ZmbGluZV9hY2Nlc3MifQ.A7qRTo-MX0knfXQaQWBX0xABLQxCzL3oa3tNfyQf9irdosk27-BwP_LwysRcCqxbVU2xPE8-hRSQ2eJL5w4b43AHU58wYxkTqFQaBGTB5sTltXJJyitzy7pOwONXJyr60m4AtQFHZUtdNTTzpBLOm8fGn82QQxY-jLvB9kDMguxafLgAQnmq3z-cTEUsP2KR94O9fSv2DCPe0vGDaDslDz7Wl8qtcUd8vR-_y8YFv8lw6gEadx0Yl7E2VYHHBJZEI2DJGxPH_RUOePMYOO_Q7ui6r7pn-hfQb8GA7A0fTsjSfA_96khHk3TgpU0j2VHPL9U20O31nMOAipzz8mvL9g";
	String requestBody= Create_Batch_Sync.requestBody;

	@Test(priority = 1)
	public void verify_Create_Sync_Batch_withoutToken_Full_Response_Status() {

		// apiKeyAPI endpoint
		String endpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks";

		// Send POST request
		Response response = RestAssured.given().header("Content-Type", "application/vnd.api+json")
				.header("Accept", "application/vnd.api+json").body(requestBody).post(endpoint);

		// Print response
		// response.prettyPrint();
		// Assert the status code...

		int expectedStatusCode = 401;
		int actualStatusCode = response.getStatusCode();
		assertEquals(actualStatusCode, expectedStatusCode, "Status code is not as expected");

	}

	@Test(priority = 2)
	public void verify_Create_Sync_Batch_invalidToken_Full_Response_Status() {

		// apiKeyAPI endpoint
		String endpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks";

		// Send POST request
		Response response = RestAssured.given().header("Content-Type", "application/vnd.api+json")
				.header("Accept", "application/vnd.api+json").header("Authorization", "Bearer abcd123")
				.body(requestBody).post(endpoint);

		// Print response
		// response.prettyPrint();
		// Assert the status code...

		int expectedStatusCode = 401;
		int actualStatusCode = response.getStatusCode();
		assertEquals(actualStatusCode, expectedStatusCode, "Status code is not as expected");

	}

	@Test(priority = 3)
	public void verify_Create_Sync_Batch_expiredToken_Full_Response_Status() {

		// apiKeyAPI endpoint
		String endpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks";

		// Send POST request
		Response response = RestAssured.given().header("Content-Type", "application/vnd.api+json")
				.header("Accept", "application/vnd.api+json").header("Authorization", "Bearer " + expiredToken)
				.body(requestBody).post(endpoint);

		// Print response
		// response.prettyPrint();
		// Assert the status code...

		int expectedStatusCode = 401;
		int actualStatusCode = response.getStatusCode();
		assertEquals(actualStatusCode, expectedStatusCode, "Status code is not as expected");

	}



}
