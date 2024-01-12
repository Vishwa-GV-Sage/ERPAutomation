package restAPIPkg;

import org.openqa.selenium.WebDriver;
import frameworkPkg.Helper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.io.File;

public class InvoiceAPI extends Helper {
	WebDriver driver;

	public InvoiceAPI(WebDriver driver) {
		this.driver = driver;
	}

	public void checkInvoiceSyncStatus(String jwtToken, int expectedInvoiceAmount) {

		try {
			// apiKeyAPI endpoint
			String apiKeyEndpoint = invoiceApiBaseURL + "/api/v1/ApiKeys";

			// Request body in JSON format
			String apiKeyRequestBody = "{ \"name\": \"string12423\" }";

			// Make the API call using RestAssured
			Response apiKeyResponse = RestAssured.given().header("Content-Type", ContentType.JSON)
					.header("Authorization", "Bearer " + jwtToken).body(apiKeyRequestBody).when().post(apiKeyEndpoint);

			// Extract apiKey from the response and store it in a variable
			String apiKey = apiKeyResponse.jsonPath().getString("apiKey");
			System.out.println("apiKey: " + apiKey);

			// appEnrollment POST request endpoint
			String appEnrollmentsApiEndpoint = invoiceApiBaseURL + "/api/v1/appenrollments";

			// Request body for the appEnrollment POST request in JSON format
			String appEnrollmentsRequestBody = "[{\"appId\": \"" + intacctAppID + "\"}]";

			// Make the appEnrollment POST request using RestAssured
			Response appEnrollmentsResponse = RestAssured.given().header("Content-Type", ContentType.JSON.toString())
					.header("Authorization", "Bearer " + jwtToken).body(appEnrollmentsRequestBody).when()
					.post(appEnrollmentsApiEndpoint);

			// Extract appEnrollmentId from the response and store it in a variable

			String appEnrollmentId = appEnrollmentsResponse.jsonPath().getString("[0].appEnrollmentId");

			System.out.println("appEnrollmentId: " + appEnrollmentId);

			// uploadZip POST request endpoint
			String uploadZipApiEndpoint = invoiceApiBaseURL + "/api/v1/Sync/zip?appEnrollmentId=" + appEnrollmentId
					+ "&isFullSync=true";

			// File path for the zip file
			String filePath = "C:\\Users\\Public\\Documents\\ERPAutomation\\Sage 50 Zip.zip";

			// Make the uploadZip POST request using RestAssured
			RestAssured.given().header("Authorization", "Bearer " + jwtToken).multiPart("file", new File(filePath))
					.when().post(uploadZipApiEndpoint);

			// GET request QuerySyncendpoint
			String querySyncApiEndpoint = invoiceApiBaseURL
					+ "/api/v1/sync/query?include=details&order=created desc&pageSize=1&appEnrollmentId="
					+ appEnrollmentId;

			// Make the GET request using RestAssured
			Response querySyncResponse = RestAssured.given().header("Authorization", "Bearer " + jwtToken)
					.get(querySyncApiEndpoint);

			// Extract status code from the response and print it
			String statusCode = querySyncResponse.jsonPath().getString("records[0].statusCode");

			// Keep checking the status until it becomes "Success"

			while (!statusCode.equalsIgnoreCase("Success")) {
				// Make the GET request using RestAssured
				Response queryResponse = RestAssured.given().header("Authorization", "Bearer " + jwtToken)
						.get(querySyncApiEndpoint);

				// Extract status code from the response and print it
				statusCode = queryResponse.jsonPath().getString("records[0].statusCode");
				System.out.println("Status Code: " + statusCode);

				// Add a delay before making the next request
				Thread.sleep(5000); // 5 seconds (adjust as needed)
			}

			// GET request invoiceSummaryEndpoint
			String invoiceSummaryApiEndpoint = invoiceApiBaseURL + "/api/v1/Invoices/views/summary";

			// Make the GET invoiceSummaryrequest using RestAssured
			Response invoiceSummaryResponse = RestAssured.given().header("Authorization", "Bearer " + jwtToken)
					.get(invoiceSummaryApiEndpoint);
			// Extracting the actualInvoiceAmount from the response
			Float actualInvoiceAmountFromResponse = invoiceSummaryResponse.jsonPath()
					.getFloat("records[0].invoiceAmount");
			// Print the body of the invoiceSummaryResponse
			System.out.println("Invoice Summary API Response Body: " + invoiceSummaryResponse.getBody().asString());
			// Compare actualInvoiceAmount with the expected value
			if (actualInvoiceAmountFromResponse.equals((float) expectedInvoiceAmount)) {
				System.out.println("Yes found");
			} else {
				System.out.println("No, not found");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void checkIntacctInvoiceSyncStatus(String jwtToken, int expectedInvoiceAmount) {

		try {
			// apiKeyAPI endpoint
			String apiKeyEndpoint = invoiceApiBaseURL + "/api/v1/ApiKeys";

			// Request body in JSON format
			String apiKeyRequestBody = "{ \"name\": \"string12423\" }";

			// Make the API call using RestAssured
			Response apiKeyResponse = RestAssured.given().header("Content-Type", ContentType.JSON)
					.header("Authorization", "Bearer " + jwtToken).body(apiKeyRequestBody).when().post(apiKeyEndpoint);

			// Extract apiKey from the response and store it in a variable
			String apiKey = apiKeyResponse.jsonPath().getString("apiKey");
			System.out.println("apiKey: " + apiKey);

			// appEnrollment POST request endpoint
			String appEnrollmentsApiEndpoint = invoiceApiBaseURL + "/api/v1/appenrollments";

			// Request body for the appEnrollment POST request in JSON format
			String appEnrollmentsRequestBody = "[{\"isActive\":true,\"appId\":\"" + intacctAppID
					+ "\",\"syncScheduleIsActive\":true,\"connectorInfo\":{\"realmId\":\"e2b Teknologies-DEV|E2B USD\",\"username\":\"Admin2\",\"password\":\"Lockstep@123\"}}]";
			;

			// Make the appEnrollment POST request using RestAssured
			Response appEnrollmentsResponse = RestAssured.given().header("Content-Type", ContentType.JSON.toString())
					.header("APIKey", apiKey).body(appEnrollmentsRequestBody).when().post(appEnrollmentsApiEndpoint);

			// Extract appEnrollmentId from the response and store it in a variable

			String appEnrollmentId = appEnrollmentsResponse.jsonPath().getString("[0].appEnrollmentId");

			System.out.println("appEnrollmentId: " + appEnrollmentId);

			// POST request QuerySyncendpoint
			String createSyncApiEndpoint = invoiceApiBaseURL + "/api/v1/sync/";

			// Request body for the Sync POST request in JSON format
			String createSyncRequestBody = "{\"appEnrollmentId\":\"" + appEnrollmentId + "\",\"runFullSync\":true}";
			// Make the GET request using RestAssured
			RestAssured.given().header("Content-Type", ContentType.JSON.toString()).header("APIKey", apiKey)
					.body(createSyncRequestBody).when().post(createSyncApiEndpoint);

			// GET request QuerySyncendpoint
			String querySyncApiEndpoint = invoiceApiBaseURL + "/api/v1/sync/query?filter=appEnrollmentId='"
					+ appEnrollmentId + "'";

			boolean isInProgress = false;
			int attemptCount = 0;
			int maxAttempts = 1;

			do {
				// Make the GET request using RestAssured
				Response querySyncResponse = RestAssured.given().header("Content-Type", "application/json")
						.header("APIKey", apiKey).queryParam("operationType", "Read").when().get(querySyncApiEndpoint);

				// querySyncResponse.prettyPrint();

				// Extract and print the "statusCode" value
				String statusCodeValue = querySyncResponse.jsonPath().getString("records[0].statusCode");
				System.out.println("Status Code: " + statusCodeValue);

				// Check if the status code is now "In Progress"
				isInProgress = "In Progress".equals(statusCodeValue);
				// Increment attempt count
				attemptCount++;

				// Add a delay before the next request
				try {
					Thread.sleep(5000); // Sleep for 5 second (adjust as needed)
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while (!isInProgress && attemptCount < maxAttempts);
			if (isInProgress) {
				System.out.println("Status is now 'In Progress'");
			} else {
				System.out.println("Maximum attempts reached. Status is still 'Connector'");
			}

			String invoiceFilter = "customerName eq Vishwa_Test1 AND invoiceDate eq 2024-01-12";
			// GET request QuerySyncendpoint
			String invoiceSummaryApiEndpoint = invoiceApiBaseURL + "api/v1/invoices/views/summary?filter="
					+ invoiceFilter;
			System.out.println(invoiceSummaryApiEndpoint);
			// Make the GET request using RestAssured
			Response invoiceSummaryResponse = RestAssured.given().header("Content-Type", "application/json")
					.header("APIKey", apiKey).when().get(invoiceSummaryApiEndpoint);

			// Print the full response
			System.out.println("Full Response:");
			invoiceSummaryResponse.prettyPrint();
			/*
			 * // Extract status code from the response and print it String statusCode =
			 * querySyncResponse.jsonPath().getString("records[0].statusCode");
			 * 
			 * // Keep checking the status until it becomes "Success" while
			 * (!statusCode.equalsIgnoreCase("Success")) { // Make the GET request using
			 * RestAssured Response queryResponse =
			 * RestAssured.given().header("Authorization", "Bearer " + jwtToken)
			 * .get(querySyncApiEndpoint);
			 * 
			 * // Extract status code from the response and print it statusCode =
			 * queryResponse.jsonPath().getString("records[0].statusCode");
			 * System.out.println("Status Code: " + statusCode);
			 * 
			 * // Add a delay before making the next request Thread.sleep(5000); // 5
			 * seconds (adjust as needed) }
			 * 
			 * // GET request invoiceSummaryEndpoint String invoiceSummaryApiEndpoint =
			 * invoiceApiBaseURL + "/api/v1/Invoices/views/summary";
			 * 
			 * // Make the GET invoiceSummaryrequest using RestAssured Response
			 * invoiceSummaryResponse = RestAssured.given().header("Authorization",
			 * "Bearer " + jwtToken) .get(invoiceSummaryApiEndpoint); // Extracting the
			 * actualInvoiceAmount from the response Float actualInvoiceAmountFromResponse =
			 * invoiceSummaryResponse.jsonPath() .getFloat("records[0].invoiceAmount"); //
			 * Print the body of the invoiceSummaryResponse
			 * System.out.println("Invoice Summary API Response Body: " +
			 * invoiceSummaryResponse.getBody().asString()); // Compare actualInvoiceAmount
			 * with the expected value if (actualInvoiceAmountFromResponse.equals((float)
			 * expectedInvoiceAmount)) { System.out.println("Yes found"); } else {
			 * System.out.println("No, not found"); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
