package happyPathScenarios;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import frameworkPkg.Helper;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Positive_Create_Batch_Sync extends Helper {
	private String syncBatchID;

	String apiBaseUrl = syncAPIBaseURL, dataset_id = syncAPIDataset_ID, jwtToken = syncAPIJwtToken;

	@Test(priority = 1)
	public void verify_Create_Sync_Batch_Upload_Full() {

		// apiKeyAPI endpoint
		String endpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks";
		String requestBody = "{\n" + "    \"Data\": {\n" + "        \"Attributes\": {\n"
				+ "            \"PackageType\": \"Full\"\n" + "        },\n" + "        \"Relationships\": {\n"
				+ "            \"Companies\": {\n" + "                \"data\": [\n" + "                    {\n"
				+ "                        \"Id\": \"1041\",\n" + "                        \"Type\": \"Company\"\n"
				+ "                    }\n" + "                ]\n" + "            },\n"
				+ "            \"Contacts\": {\n" + "                \"data\": [\n" + "                    {\n"
				+ "                        \"Id\": \"1360\",\n" + "                        \"Type\": \"Contact\"\n"
				+ "                    }\n" + "                ]\n" + "            },\n"
				+ "            \"Invoices\": {\n" + "                \"data\": [\n" + "                    {\n"
				+ "                        \"Id\": \"183705\",\n" + "                        \"Type\": \"Invoice\"\n"
				+ "                    }\n" + "                ]\n" + "            },\n"
				+ "            \"InvoiceLines\": {\n" + "                \"data\": [\n" + "                    {\n"
				+ "                        \"Id\": \"407628\",\n"
				+ "                        \"Type\": \"InvoiceLine\"\n" + "                    }\n"
				+ "                ]\n" + "            },\n" + "            \"Payments\": {\n"
				+ "                \"data\": [\n" + "                    {\n"
				+ "                        \"Id\": \"183680\",\n" + "                        \"Type\": \"Payment\"\n"
				+ "                    }\n" + "                ]\n" + "            },\n"
				+ "            \"PaymentAllocations\": {\n" + "                \"data\": [\n"
				+ "                    {\n" + "                        \"Id\": \"41701\",\n"
				+ "                        \"Type\": \"PaymentApplication\"\n" + "                    }\n"
				+ "                ]\n" + "            },\n" + "            \"CreditAllocations\": {\n"
				+ "                \"data\": [\n" + "                    {\n"
				+ "                        \"Id\": \"12551\",\n"
				+ "                        \"Type\": \"CreaditMemoApplication\"\n" + "                    }\n"
				+ "                ]\n" + "            },\n" + "            \"FinancialYearSettings\": {\n"
				+ "                \"data\": [\n" + "                    {\n"
				+ "                        \"Id\": \"1\",\n"
				+ "                        \"Type\": \"FinancialYearSetting\"\n" + "                    }\n"
				+ "                ]\n" + "            },\n" + "            \"FinancialAccounts\": {\n"
				+ "                \"data\": [\n" + "                    {\n"
				+ "                        \"Id\": \"119\",\n"
				+ "                        \"Type\": \"FinancialAccount\"\n" + "                    }\n"
				+ "                ]\n" + "            },\n" + "            \"FinancialAccountBalanceHistories\": {\n"
				+ "                \"data\": [\n" + "                    {\n"
				+ "                        \"Id\": \"15\",\n"
				+ "                        \"Type\": \"FinancialAccountBalanceHistory\"\n" + "                    }\n"
				+ "                ]\n" + "            },\n" + "            \"BaseCurrencies\": {\n"
				+ "                \"data\": [\n" + "                    {\n"
				+ "                        \"Id\": \"1\",\n" + "                        \"Type\": \"BaseCurrency\"\n"
				+ "                    }\n" + "                ]\n" + "            },\n"
				+ "            \"CustomFields\": {\n" + "                \"data\": [\n" + "                    {\n"
				+ "                        \"Id\": \"183705\",\n"
				+ "                        \"Type\": \"CustomField\"\n" + "                    }\n"
				+ "                ]\n" + "            },\n" + "            \"JournalEntries\": {\n"
				+ "                \"data\": [\n" + "                    {\n"
				+ "                        \"Id\": \"68736\",\n"
				+ "                        \"Type\": \"JournalEntry\"\n" + "                    }\n"
				+ "                ]\n" + "            },\n" + "            \"JournalEntryLines\": {\n"
				+ "                \"data\": [\n" + "                    {\n"
				+ "                        \"Id\": \"146336\",\n"
				+ "                        \"Type\": \"JournalEntryLine\"\n" + "                    }\n"
				+ "                ]\n" + "            },\n" + "            \"Attachments\": null\n" + "        },\n"
				+ "        \"Id\": null,\n" + "        \"Type\": \"SyncTask\"\n" + "    },\n" + "    \"Included\": [\n"
				+ "        {\n" + "            \"id\": \"1041\",\n" + "            \"type\": \"Company\",\n"
				+ "            \"attributes\": {\n" + "                \"operationType\": \"Insert\",\n"
				+ "                \"companyName\": \"Ford INTACCT corporation\",\n"
				+ "                \"companyType\": \"Customer\",\n" + "                \"NetworkId\": null,\n"
				+ "                \"parentCompanyId\": \"14\",\n"
				+ "                \"parentCompanyErpKey\": \"Company--14\",\n"
				+ "                \"defaultCurrencyCode\": \"USD\",\n"
				+ "                \"externalReference\": \"testsep_11\",\n" + "                \"taxId\": \"\",\n"
				+ "                \"enterpriseId\": \"\",\n" + "                \"enterpriseErpKey\": \"\",\n"
				+ "                \"timeStamp\": \"2023-12-22T13:19:43.083Z\",\n"
				+ "                \"status\": null,\n" + "                \"message\": null\n" + "            },\n"
				+ "            \"relationships\": {\n" + "                \"primaryContact\": {\n"
				+ "                    \"id\": \"1360\",\n" + "                    \"type\": \"Contact\"\n"
				+ "                },\n" + "                \"address\": {\n" + "                    \"data\": {\n"
				+ "                        \"id\": \"1\",\n" + "                        \"type\": \"Address\"\n"
				+ "                    }\n" + "                }\n" + "            }\n" + "        },\n" + "        {\n"
				+ "            \"id\": \"1048\",\n" + "            \"type\": \"Company\",\n"
				+ "            \"attributes\": {\n" + "                \"operationType\": \"Insert\",\n"
				+ "                \"companyName\": \"Ford INTACCT corporation\",\n"
				+ "                \"companyType\": \"Customer\",\n" + "                \"NetworkId\": null,\n"
				+ "                \"parentCompanyId\": \"14\",\n"
				+ "                \"parentCompanyErpKey\": \"Company--14\",\n"
				+ "                \"defaultCurrencyCode\": \"USD\",\n"
				+ "                \"externalReference\": \"testsep_11\",\n" + "                \"taxId\": \"\",\n"
				+ "                \"enterpriseId\": \"\",\n" + "                \"enterpriseErpKey\": \"\",\n"
				+ "                \"timeStamp\": \"2023-12-22T13:19:43.083Z\",\n"
				+ "                \"status\": null,\n" + "                \"message\": null\n" + "            },\n"
				+ "            \"relationships\": {\n" + "                \"primaryContact\": {\n"
				+ "                    \"id\": \"1360\",\n" + "                    \"type\": \"Contact\"\n"
				+ "                },\n" + "                \"address\": {\n" + "                    \"data\": {\n"
				+ "                        \"id\": \"1\",\n" + "                        \"type\": \"Address\"\n"
				+ "                    }\n" + "                }\n" + "            }\n" + "        },\n" + "        {\n"
				+ "            \"id\": \"1099\",\n" + "            \"type\": \"Company\",\n"
				+ "            \"attributes\": {\n" + "                \"operationType\": \"Insert\",\n"
				+ "                \"companyName\": \"Ford INTACCT corporation\",\n"
				+ "                \"companyType\": \"Customer\",\n" + "                \"NetworkId\": null,\n"
				+ "                \"parentCompanyId\": \"14\",\n"
				+ "                \"parentCompanyErpKey\": \"Company--14\",\n"
				+ "                \"defaultCurrencyCode\": \"USD\",\n"
				+ "                \"externalReference\": \"testsep_11\",\n" + "                \"taxId\": \"\",\n"
				+ "                \"enterpriseId\": \"\",\n" + "                \"enterpriseErpKey\": \"\",\n"
				+ "                \"timeStamp\": \"2023-12-22T13:19:43.083Z\",\n"
				+ "                \"status\": null,\n" + "                \"message\": null\n" + "            },\n"
				+ "            \"relationships\": {\n" + "                \"primaryContact\": {\n"
				+ "                    \"id\": \"1360\",\n" + "                    \"type\": \"Contact\"\n"
				+ "                },\n" + "                \"address\": {\n" + "                    \"data\": {\n"
				+ "                        \"id\": \"1\",\n" + "                        \"type\": \"Address\"\n"
				+ "                    }\n" + "                }\n" + "            }\n" + "        },\n" + "        {\n"
				+ "            \"id\": \"1044\",\n" + "            \"type\": \"Company\",\n"
				+ "            \"attributes\": {\n" + "                \"operationType\": \"Insert\",\n"
				+ "                \"companyName\": \"Ford INTACCT corporation\",\n"
				+ "                \"companyType\": \"Customer\",\n" + "                \"NetworkId\": null,\n"
				+ "                \"parentCompanyId\": \"14\",\n"
				+ "                \"parentCompanyErpKey\": \"Company--14\",\n"
				+ "                \"defaultCurrencyCode\": \"USD\",\n"
				+ "                \"externalReference\": \"testsep_11\",\n" + "                \"taxId\": \"\",\n"
				+ "                \"enterpriseId\": \"\",\n" + "                \"enterpriseErpKey\": \"\",\n"
				+ "                \"timeStamp\": \"2023-12-22T13:19:43.083Z\",\n"
				+ "                \"status\": null,\n" + "                \"message\": null\n" + "            },\n"
				+ "            \"relationships\": {\n" + "                \"primaryContact\": {\n"
				+ "                    \"id\": \"1360\",\n" + "                    \"type\": \"Contact\"\n"
				+ "                },\n" + "                \"address\": {\n" + "                    \"data\": {\n"
				+ "                        \"id\": \"1\",\n" + "                        \"type\": \"Address\"\n"
				+ "                    }\n" + "                }\n" + "            }\n" + "        },\n" + "        {\n"
				+ "            \"id\": \"1360\",\n" + "            \"type\": \"Contact\",\n"
				+ "            \"attributes\": {\n" + "                \"contactName\": null,\n"
				+ "                \"title\": null,\n" + "                \"emailAddress\": null,\n"
				+ "                \"phone\": \"09765997203\",\n" + "                \"fax\": \"\",\n"
				+ "                \"address1\": \"666, guruwar peth, Mominpura, Above noble school\",\n"
				+ "                \"address2\": \"pune 42\",\n" + "                \"address3\": null,\n"
				+ "                \"city\": \"pune\",\n" + "                \"stateRegion\": \"Maharashtra\",\n"
				+ "                \"postalCode\": \"411042\",\n" + "                \"countryCode\": \"US\",\n"
				+ "                \"webpageUrl\": null,\n"
				+ "                \"created\": \"2023-12-22T13:19:43.083Z\",\n"
				+ "                \"modified\": \"2023-12-22T13:19:43.083Z\"\n" + "            }\n" + "        },\n"
				+ "        {\n" + "            \"id\": \"183705\",\n" + "            \"type\": \"Invoice\",\n"
				+ "            \"attributes\": {\n" + "                \"networkId\": null,\n"
				+ "                \"operationType\": \"Insert\",\n"
				+ "                \"invoiceReferenceCode\": \"84227\",\n"
				+ "                \"sendImmediately\": false,\n" + "                \"isEInvoice\": false,\n"
				+ "                \"invoiceType\": \"AR Invoice\",\n"
				+ "                \"customerId\": \"B44A68B0-FFE2-4D7A-ACA4-119663F30DC1\",\n"
				+ "                \"totalAmount\": 100.0,\n" + "                \"currencyCode\": \"USD\",\n"
				+ "                \"invoiceDate\": \"2023-12-22T13:19:43.083Z\",\n"
				+ "                \"created\": \"2023-12-22T13:19:43.083Z\",\n"
				+ "                \"isDraft\": true,\n"
				+ "                \"departmentErpKey\": \"1251A2FA-CFDD-440C-97E6-0018A3AEB14B\",\n"
				+ "                \"description\": null,\n" + "                \"discountAmount\": 0,\n"
				+ "                \"outstandingBalanceAmount\": 100.0,\n"
				+ "                \"paymentDueDate\": \"2023-12-22T13:19:43.083Z\",\n"
				+ "                \"paymentPriority\": null,\n" + "                \"purchaseOrderCode\": \"\",\n"
				+ "                \"referenceCode\": \"84227\",\n" + "                \"salesPersonCode\": null,\n"
				+ "                \"salesTaxAmount\": 0,\n" + "                \"termName\": \"\",\n"
				+ "                \"companyErpKey\": \"33E72B9E-11EE-49C0-BC08-8DFBE25A0EB5\",\n"
				+ "                \"timeStamp\": \"2023-12-22T13:19:43.083Z\",\n"
				+ "                \"status\": null,\n" + "                \"message\": null\n" + "            },\n"
				+ "            \"relationships\": {\n" + "                \"lines\": [\n" + "                    {\n"
				+ "                        \"data\": {\n" + "                            \"id\": \"407628\",\n"
				+ "                            \"type\": \"InvoiceLine\"\n" + "                        }\n"
				+ "                    }\n" + "                ]\n" + "            }\n" + "        },\n" + "        {\n"
				+ "            \"id\": \"407628\",\n" + "            \"type\": \"InvoiceLine\",\n"
				+ "            \"attributes\": {\n" + "                \"accountNo\": null,\n"
				+ "                \"accountLabel\": null,\n" + "                \"departmentErpKey\": null,\n"
				+ "                \"description\": \"\",\n" + "                \"lineNumber\": \"1\",\n"
				+ "                \"locationErpKey\": null,\n" + "                \"offsetGlAccountNo\": null,\n"
				+ "                \"productCode\": \"\",\n" + "                \"quantity\": 0,\n"
				+ "                \"unitMeasureCode\": null,\n" + "                \"unitPrice\": 0,\n"
				+ "                \"totalAmount\": 100\n" + "            }\n" + "        },\n" + "        {\n"
				+ "            \"id\": \"183680\",\n" + "            \"type\": \"Payment\",\n"
				+ "            \"attributes\": {\n" + "                \"NetworkId\": null,\n"
				+ "                \"companyId\": \"33E72B9E-11EE-49C0-BC08-8DFBE25A0EB5\",\n"
				+ "                \"CompanyErpKey\": \"Customer--107644\",\n"
				+ "                \"paymentTypeCode\": \"AR Payment\",\n" + "                \"paymentAmount\": 0,\n"
				+ "                \"currencyCode\": \"USD\",\n"
				+ "                \"paymentDate\": \"2023-12-22T13:19:43.083Z\",\n"
				+ "                \"paymentMethodCode\": \"EFT\",\n"
				+ "                \"paymentReference\": \"666\",\n"
				+ "                \"transactionDate\": \"2023-12-22T13:19:43.083Z\",\n"
				+ "                \"transactionCurrencyCode\": \"USD\",\n" + "                \"status\": null,\n"
				+ "                \"timeStamp\": \"2023-12-22T13:19:43.083Z\",\n"
				+ "                \"message\": null\n" + "            },\n" + "            \"relationships\": {\n"
				+ "                \"allocations\": [\n" + "                    {\n"
				+ "                        \"data\": {\n" + "                            \"id\": \"41701\",\n"
				+ "                            \"type\": \"PaymentApplication\"\n" + "                        }\n"
				+ "                    }\n" + "                ]\n" + "            }\n" + "        },\n" + "        {\n"
				+ "            \"id\": \"41701\",\n" + "            \"type\": \"PaymentApplication\",\n"
				+ "            \"attributes\": {\n" + "                \"allocatedAmount\": 100,\n"
				+ "                \"documentId\": \"183705\",\n"
				+ "                \"documentType\": \"AR Invoice\",\n"
				+ "                \"transactionDate\": \"2023-12-22T13:19:43.083Z\",\n"
				+ "                \"timeStamp\": \"2023-12-22T13:19:43.083Z\",\n"
				+ "                \"status\": null,\n" + "                \"message\": null\n" + "            }\n"
				+ "        },\n" + "        {\n" + "            \"id\": \"12551\",\n"
				+ "            \"type\": \"CreaditMemoApplication\",\n" + "            \"attributes\": {\n"
				+ "                \"allocatedAmount\": 0,\n" + "                \"documentId\": \"\",\n"
				+ "                \"documentType\": \"\",\n"
				+ "                \"transactionDate\": \"2023-12-22T13:19:43.083Z\",\n"
				+ "                \"timeStamp\": \"2023-12-22T13:19:43.083Z\",\n"
				+ "                \"status\": null,\n" + "                \"message\": null\n" + "            }\n"
				+ "        },\n" + "        {\n" + "            \"id\": \"68736\",\n"
				+ "            \"type\": \"JournalEntry\",\n" + "            \"attributes\": {\n"
				+ "                \"networkId\": null,\n" + "                \"operationType\": \"Insert\",\n"
				+ "                \"journalType\": \"Journal Entry\",\n" + "                \"journalNumber\": null,\n"
				+ "                \"totalAmount\": 0,\n" + "                \"currencyCode\": null,\n"
				+ "                \"journalDate\": null,\n" + "                \"description\": \"\",\n"
				+ "                \"created\": null,\n" + "                \"isDraft\": null,\n"
				+ "                \"transactionDate\": null,\n" + "                \"companyErpKey\": null,\n"
				+ "                \"timeStamp\": null,\n" + "                \"status\": null,\n"
				+ "                \"message\": null\n" + "            },\n" + "            \"relationships\": {\n"
				+ "                \"lines\": []\n" + "            }\n" + "        },\n" + "        {\n"
				+ "            \"id\": \"146336\",\n" + "            \"type\": \"JournalEntryLine\",\n"
				+ "            \"attributes\": {\n" + "                \"networkId\": null,\n"
				+ "                \"operationType\": \"Insert\",\n" + "                \"journalEntryNumber\": null,\n"
				+ "                \"lineNumber\": null,\n" + "                \"totalAmount\": 0,\n"
				+ "                \"accountCode\": null,\n" + "                \"departmentErpKey\": null,\n"
				+ "                \"locationErpKey\": null,\n" + "                \"offsetGlAccountNo\": null,\n"
				+ "                \"description\": \"\",\n" + "                \"created\": null,\n"
				+ "                \"isDraft\": null,\n" + "                \"transactionDate\": null,\n"
				+ "                \"companyErpKey\": null,\n" + "                \"timeStamp\": null,\n"
				+ "                \"status\": null,\n" + "                \"message\": null\n" + "            }\n"
				+ "        },\n" + "        {\n" + "            \"id\": \"1\",\n"
				+ "            \"type\": \"Address\",\n" + "            \"attributes\": {\n"
				+ "                \"addressLine1\": null,\n" + "                \"addressLine2\": null,\n"
				+ "                \"addressLine3\": null,\n" + "                \"city\": null,\n"
				+ "                \"state\": null,\n" + "                \"postalCode\": null,\n"
				+ "                \"country\": null\n" + "            }\n" + "        }\n" + "    ]\n" + "}";

		// Send POST request
		Response response = RestAssured.given().header("Authorization", "Bearer " + jwtToken)
				.header("Content-Type", "application/vnd.api+json").header("Accept", "application/vnd.api+json")
				.body(requestBody).post(endpoint);
		// Extract id from response and store it in the global variable
		syncBatchID = response.jsonPath().getString("data.id");
		// Print response
		//response.prettyPrint();
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
			assertEquals(queryresponse.jsonPath().getString("data[0].data.attributes.status"), "Awaiting",
					"Status attribute is not as expected");
			assertEquals(queryresponse.jsonPath().getString("data[0].data.attributes.operationType"), "ToNetwork",
					"Operation type attribute is not as expected");
			assertEquals(queryresponse.jsonPath().getString("data[0].data.attributes.packageType"), "Full",
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
			System.out.println("Assertion failed: " + e.getMessage());
			throw e;
		}

		String retrieveEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks/"
				+ syncBatchID + "/?include=Details";

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
			assertEquals( retrieveResponse.jsonPath().getString("data.id"),syncBatchID,
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
		String requestBody = "{\n" + "    \"Data\": {\n" + "        \"Attributes\": {\n"
				+ "            \"PackageType\": \"Partial\"\n" + "        },\n" + "        \"Relationships\": {\n"
				+ "            \"Companies\": {\n" + "                \"data\": [\n" + "                    {\n"
				+ "                        \"Id\": \"1041\",\n" + "                        \"Type\": \"Company\"\n"
				+ "                    }\n" + "                ]\n" + "            },\n"
				+ "            \"Contacts\": {\n" + "                \"data\": [\n" + "                    {\n"
				+ "                        \"Id\": \"1360\",\n" + "                        \"Type\": \"Contact\"\n"
				+ "                    }\n" + "                ]\n" + "            },\n"
				+ "            \"Invoices\": {\n" + "                \"data\": [\n" + "                    {\n"
				+ "                        \"Id\": \"183705\",\n" + "                        \"Type\": \"Invoice\"\n"
				+ "                    }\n" + "                ]\n" + "            },\n"
				+ "            \"InvoiceLines\": {\n" + "                \"data\": [\n" + "                    {\n"
				+ "                        \"Id\": \"407628\",\n"
				+ "                        \"Type\": \"InvoiceLine\"\n" + "                    }\n"
				+ "                ]\n" + "            },\n" + "            \"Payments\": {\n"
				+ "                \"data\": [\n" + "                    {\n"
				+ "                        \"Id\": \"183680\",\n" + "                        \"Type\": \"Payment\"\n"
				+ "                    }\n" + "                ]\n" + "            },\n"
				+ "            \"PaymentAllocations\": {\n" + "                \"data\": [\n"
				+ "                    {\n" + "                        \"Id\": \"41701\",\n"
				+ "                        \"Type\": \"PaymentApplication\"\n" + "                    }\n"
				+ "                ]\n" + "            },\n" + "            \"CreditAllocations\": {\n"
				+ "                \"data\": [\n" + "                    {\n"
				+ "                        \"Id\": \"12551\",\n"
				+ "                        \"Type\": \"CreaditMemoApplication\"\n" + "                    }\n"
				+ "                ]\n" + "            },\n" + "            \"FinancialYearSettings\": {\n"
				+ "                \"data\": [\n" + "                    {\n"
				+ "                        \"Id\": \"1\",\n"
				+ "                        \"Type\": \"FinancialYearSetting\"\n" + "                    }\n"
				+ "                ]\n" + "            },\n" + "            \"FinancialAccounts\": {\n"
				+ "                \"data\": [\n" + "                    {\n"
				+ "                        \"Id\": \"119\",\n"
				+ "                        \"Type\": \"FinancialAccount\"\n" + "                    }\n"
				+ "                ]\n" + "            },\n" + "            \"FinancialAccountBalanceHistories\": {\n"
				+ "                \"data\": [\n" + "                    {\n"
				+ "                        \"Id\": \"15\",\n"
				+ "                        \"Type\": \"FinancialAccountBalanceHistory\"\n" + "                    }\n"
				+ "                ]\n" + "            },\n" + "            \"BaseCurrencies\": {\n"
				+ "                \"data\": [\n" + "                    {\n"
				+ "                        \"Id\": \"1\",\n" + "                        \"Type\": \"BaseCurrency\"\n"
				+ "                    }\n" + "                ]\n" + "            },\n"
				+ "            \"CustomFields\": {\n" + "                \"data\": [\n" + "                    {\n"
				+ "                        \"Id\": \"183705\",\n"
				+ "                        \"Type\": \"CustomField\"\n" + "                    }\n"
				+ "                ]\n" + "            },\n" + "            \"JournalEntries\": {\n"
				+ "                \"data\": [\n" + "                    {\n"
				+ "                        \"Id\": \"68736\",\n"
				+ "                        \"Type\": \"JournalEntry\"\n" + "                    }\n"
				+ "                ]\n" + "            },\n" + "            \"JournalEntryLines\": {\n"
				+ "                \"data\": [\n" + "                    {\n"
				+ "                        \"Id\": \"146336\",\n"
				+ "                        \"Type\": \"JournalEntryLine\"\n" + "                    }\n"
				+ "                ]\n" + "            },\n" + "            \"Attachments\": null\n" + "        },\n"
				+ "        \"Id\": null,\n" + "        \"Type\": \"SyncTask\"\n" + "    },\n" + "    \"Included\": [\n"
				+ "        {\n" + "            \"id\": \"1041\",\n" + "            \"type\": \"Company\",\n"
				+ "            \"attributes\": {\n" + "                \"operationType\": \"Insert\",\n"
				+ "                \"companyName\": \"Ford INTACCT corporation\",\n"
				+ "                \"companyType\": \"Customer\",\n" + "                \"NetworkId\": null,\n"
				+ "                \"parentCompanyId\": \"14\",\n"
				+ "                \"parentCompanyErpKey\": \"Company--14\",\n"
				+ "                \"defaultCurrencyCode\": \"USD\",\n"
				+ "                \"externalReference\": \"testsep_11\",\n" + "                \"taxId\": \"\",\n"
				+ "                \"enterpriseId\": \"\",\n" + "                \"enterpriseErpKey\": \"\",\n"
				+ "                \"timeStamp\": \"2023-12-22T13:19:43.083Z\",\n"
				+ "                \"status\": null,\n" + "                \"message\": null\n" + "            },\n"
				+ "            \"relationships\": {\n" + "                \"primaryContact\": {\n"
				+ "                    \"id\": \"1360\",\n" + "                    \"type\": \"Contact\"\n"
				+ "                },\n" + "                \"address\": {\n" + "                    \"data\": {\n"
				+ "                        \"id\": \"1\",\n" + "                        \"type\": \"Address\"\n"
				+ "                    }\n" + "                }\n" + "            }\n" + "        },\n" + "        {\n"
				+ "            \"id\": \"1048\",\n" + "            \"type\": \"Company\",\n"
				+ "            \"attributes\": {\n" + "                \"operationType\": \"Insert\",\n"
				+ "                \"companyName\": \"Ford INTACCT corporation\",\n"
				+ "                \"companyType\": \"Customer\",\n" + "                \"NetworkId\": null,\n"
				+ "                \"parentCompanyId\": \"14\",\n"
				+ "                \"parentCompanyErpKey\": \"Company--14\",\n"
				+ "                \"defaultCurrencyCode\": \"USD\",\n"
				+ "                \"externalReference\": \"testsep_11\",\n" + "                \"taxId\": \"\",\n"
				+ "                \"enterpriseId\": \"\",\n" + "                \"enterpriseErpKey\": \"\",\n"
				+ "                \"timeStamp\": \"2023-12-22T13:19:43.083Z\",\n"
				+ "                \"status\": null,\n" + "                \"message\": null\n" + "            },\n"
				+ "            \"relationships\": {\n" + "                \"primaryContact\": {\n"
				+ "                    \"id\": \"1360\",\n" + "                    \"type\": \"Contact\"\n"
				+ "                },\n" + "                \"address\": {\n" + "                    \"data\": {\n"
				+ "                        \"id\": \"1\",\n" + "                        \"type\": \"Address\"\n"
				+ "                    }\n" + "                }\n" + "            }\n" + "        },\n" + "        {\n"
				+ "            \"id\": \"1099\",\n" + "            \"type\": \"Company\",\n"
				+ "            \"attributes\": {\n" + "                \"operationType\": \"Insert\",\n"
				+ "                \"companyName\": \"Ford INTACCT corporation\",\n"
				+ "                \"companyType\": \"Customer\",\n" + "                \"NetworkId\": null,\n"
				+ "                \"parentCompanyId\": \"14\",\n"
				+ "                \"parentCompanyErpKey\": \"Company--14\",\n"
				+ "                \"defaultCurrencyCode\": \"USD\",\n"
				+ "                \"externalReference\": \"testsep_11\",\n" + "                \"taxId\": \"\",\n"
				+ "                \"enterpriseId\": \"\",\n" + "                \"enterpriseErpKey\": \"\",\n"
				+ "                \"timeStamp\": \"2023-12-22T13:19:43.083Z\",\n"
				+ "                \"status\": null,\n" + "                \"message\": null\n" + "            },\n"
				+ "            \"relationships\": {\n" + "                \"primaryContact\": {\n"
				+ "                    \"id\": \"1360\",\n" + "                    \"type\": \"Contact\"\n"
				+ "                },\n" + "                \"address\": {\n" + "                    \"data\": {\n"
				+ "                        \"id\": \"1\",\n" + "                        \"type\": \"Address\"\n"
				+ "                    }\n" + "                }\n" + "            }\n" + "        },\n" + "        {\n"
				+ "            \"id\": \"1044\",\n" + "            \"type\": \"Company\",\n"
				+ "            \"attributes\": {\n" + "                \"operationType\": \"Insert\",\n"
				+ "                \"companyName\": \"Ford INTACCT corporation\",\n"
				+ "                \"companyType\": \"Customer\",\n" + "                \"NetworkId\": null,\n"
				+ "                \"parentCompanyId\": \"14\",\n"
				+ "                \"parentCompanyErpKey\": \"Company--14\",\n"
				+ "                \"defaultCurrencyCode\": \"USD\",\n"
				+ "                \"externalReference\": \"testsep_11\",\n" + "                \"taxId\": \"\",\n"
				+ "                \"enterpriseId\": \"\",\n" + "                \"enterpriseErpKey\": \"\",\n"
				+ "                \"timeStamp\": \"2023-12-22T13:19:43.083Z\",\n"
				+ "                \"status\": null,\n" + "                \"message\": null\n" + "            },\n"
				+ "            \"relationships\": {\n" + "                \"primaryContact\": {\n"
				+ "                    \"id\": \"1360\",\n" + "                    \"type\": \"Contact\"\n"
				+ "                },\n" + "                \"address\": {\n" + "                    \"data\": {\n"
				+ "                        \"id\": \"1\",\n" + "                        \"type\": \"Address\"\n"
				+ "                    }\n" + "                }\n" + "            }\n" + "        },\n" + "        {\n"
				+ "            \"id\": \"1360\",\n" + "            \"type\": \"Contact\",\n"
				+ "            \"attributes\": {\n" + "                \"contactName\": null,\n"
				+ "                \"title\": null,\n" + "                \"emailAddress\": null,\n"
				+ "                \"phone\": \"09765997203\",\n" + "                \"fax\": \"\",\n"
				+ "                \"address1\": \"666, guruwar peth, Mominpura, Above noble school\",\n"
				+ "                \"address2\": \"pune 42\",\n" + "                \"address3\": null,\n"
				+ "                \"city\": \"pune\",\n" + "                \"stateRegion\": \"Maharashtra\",\n"
				+ "                \"postalCode\": \"411042\",\n" + "                \"countryCode\": \"US\",\n"
				+ "                \"webpageUrl\": null,\n"
				+ "                \"created\": \"2023-12-22T13:19:43.083Z\",\n"
				+ "                \"modified\": \"2023-12-22T13:19:43.083Z\"\n" + "            }\n" + "        },\n"
				+ "        {\n" + "            \"id\": \"183705\",\n" + "            \"type\": \"Invoice\",\n"
				+ "            \"attributes\": {\n" + "                \"networkId\": null,\n"
				+ "                \"operationType\": \"Insert\",\n"
				+ "                \"invoiceReferenceCode\": \"84227\",\n"
				+ "                \"sendImmediately\": false,\n" + "                \"isEInvoice\": false,\n"
				+ "                \"invoiceType\": \"AR Invoice\",\n"
				+ "                \"customerId\": \"B44A68B0-FFE2-4D7A-ACA4-119663F30DC1\",\n"
				+ "                \"totalAmount\": 100.0,\n" + "                \"currencyCode\": \"USD\",\n"
				+ "                \"invoiceDate\": \"2023-12-22T13:19:43.083Z\",\n"
				+ "                \"created\": \"2023-12-22T13:19:43.083Z\",\n"
				+ "                \"isDraft\": true,\n"
				+ "                \"departmentErpKey\": \"1251A2FA-CFDD-440C-97E6-0018A3AEB14B\",\n"
				+ "                \"description\": null,\n" + "                \"discountAmount\": 0,\n"
				+ "                \"outstandingBalanceAmount\": 100.0,\n"
				+ "                \"paymentDueDate\": \"2023-12-22T13:19:43.083Z\",\n"
				+ "                \"paymentPriority\": null,\n" + "                \"purchaseOrderCode\": \"\",\n"
				+ "                \"referenceCode\": \"84227\",\n" + "                \"salesPersonCode\": null,\n"
				+ "                \"salesTaxAmount\": 0,\n" + "                \"termName\": \"\",\n"
				+ "                \"companyErpKey\": \"33E72B9E-11EE-49C0-BC08-8DFBE25A0EB5\",\n"
				+ "                \"timeStamp\": \"2023-12-22T13:19:43.083Z\",\n"
				+ "                \"status\": null,\n" + "                \"message\": null\n" + "            },\n"
				+ "            \"relationships\": {\n" + "                \"lines\": [\n" + "                    {\n"
				+ "                        \"data\": {\n" + "                            \"id\": \"407628\",\n"
				+ "                            \"type\": \"InvoiceLine\"\n" + "                        }\n"
				+ "                    }\n" + "                ]\n" + "            }\n" + "        },\n" + "        {\n"
				+ "            \"id\": \"407628\",\n" + "            \"type\": \"InvoiceLine\",\n"
				+ "            \"attributes\": {\n" + "                \"accountNo\": null,\n"
				+ "                \"accountLabel\": null,\n" + "                \"departmentErpKey\": null,\n"
				+ "                \"description\": \"\",\n" + "                \"lineNumber\": \"1\",\n"
				+ "                \"locationErpKey\": null,\n" + "                \"offsetGlAccountNo\": null,\n"
				+ "                \"productCode\": \"\",\n" + "                \"quantity\": 0,\n"
				+ "                \"unitMeasureCode\": null,\n" + "                \"unitPrice\": 0,\n"
				+ "                \"totalAmount\": 100\n" + "            }\n" + "        },\n" + "        {\n"
				+ "            \"id\": \"183680\",\n" + "            \"type\": \"Payment\",\n"
				+ "            \"attributes\": {\n" + "                \"NetworkId\": null,\n"
				+ "                \"companyId\": \"33E72B9E-11EE-49C0-BC08-8DFBE25A0EB5\",\n"
				+ "                \"CompanyErpKey\": \"Customer--107644\",\n"
				+ "                \"paymentTypeCode\": \"AR Payment\",\n" + "                \"paymentAmount\": 0,\n"
				+ "                \"currencyCode\": \"USD\",\n"
				+ "                \"paymentDate\": \"2023-12-22T13:19:43.083Z\",\n"
				+ "                \"paymentMethodCode\": \"EFT\",\n"
				+ "                \"paymentReference\": \"666\",\n"
				+ "                \"transactionDate\": \"2023-12-22T13:19:43.083Z\",\n"
				+ "                \"transactionCurrencyCode\": \"USD\",\n" + "                \"status\": null,\n"
				+ "                \"timeStamp\": \"2023-12-22T13:19:43.083Z\",\n"
				+ "                \"message\": null\n" + "            },\n" + "            \"relationships\": {\n"
				+ "                \"allocations\": [\n" + "                    {\n"
				+ "                        \"data\": {\n" + "                            \"id\": \"41701\",\n"
				+ "                            \"type\": \"PaymentApplication\"\n" + "                        }\n"
				+ "                    }\n" + "                ]\n" + "            }\n" + "        },\n" + "        {\n"
				+ "            \"id\": \"41701\",\n" + "            \"type\": \"PaymentApplication\",\n"
				+ "            \"attributes\": {\n" + "                \"allocatedAmount\": 100,\n"
				+ "                \"documentId\": \"183705\",\n"
				+ "                \"documentType\": \"AR Invoice\",\n"
				+ "                \"transactionDate\": \"2023-12-22T13:19:43.083Z\",\n"
				+ "                \"timeStamp\": \"2023-12-22T13:19:43.083Z\",\n"
				+ "                \"status\": null,\n" + "                \"message\": null\n" + "            }\n"
				+ "        },\n" + "        {\n" + "            \"id\": \"12551\",\n"
				+ "            \"type\": \"CreaditMemoApplication\",\n" + "            \"attributes\": {\n"
				+ "                \"allocatedAmount\": 0,\n" + "                \"documentId\": \"\",\n"
				+ "                \"documentType\": \"\",\n"
				+ "                \"transactionDate\": \"2023-12-22T13:19:43.083Z\",\n"
				+ "                \"timeStamp\": \"2023-12-22T13:19:43.083Z\",\n"
				+ "                \"status\": null,\n" + "                \"message\": null\n" + "            }\n"
				+ "        },\n" + "        {\n" + "            \"id\": \"68736\",\n"
				+ "            \"type\": \"JournalEntry\",\n" + "            \"attributes\": {\n"
				+ "                \"networkId\": null,\n" + "                \"operationType\": \"Insert\",\n"
				+ "                \"journalType\": \"Journal Entry\",\n" + "                \"journalNumber\": null,\n"
				+ "                \"totalAmount\": 0,\n" + "                \"currencyCode\": null,\n"
				+ "                \"journalDate\": null,\n" + "                \"description\": \"\",\n"
				+ "                \"created\": null,\n" + "                \"isDraft\": null,\n"
				+ "                \"transactionDate\": null,\n" + "                \"companyErpKey\": null,\n"
				+ "                \"timeStamp\": null,\n" + "                \"status\": null,\n"
				+ "                \"message\": null\n" + "            },\n" + "            \"relationships\": {\n"
				+ "                \"lines\": []\n" + "            }\n" + "        },\n" + "        {\n"
				+ "            \"id\": \"146336\",\n" + "            \"type\": \"JournalEntryLine\",\n"
				+ "            \"attributes\": {\n" + "                \"networkId\": null,\n"
				+ "                \"operationType\": \"Insert\",\n" + "                \"journalEntryNumber\": null,\n"
				+ "                \"lineNumber\": null,\n" + "                \"totalAmount\": 0,\n"
				+ "                \"accountCode\": null,\n" + "                \"departmentErpKey\": null,\n"
				+ "                \"locationErpKey\": null,\n" + "                \"offsetGlAccountNo\": null,\n"
				+ "                \"description\": \"\",\n" + "                \"created\": null,\n"
				+ "                \"isDraft\": null,\n" + "                \"transactionDate\": null,\n"
				+ "                \"companyErpKey\": null,\n" + "                \"timeStamp\": null,\n"
				+ "                \"status\": null,\n" + "                \"message\": null\n" + "            }\n"
				+ "        },\n" + "        {\n" + "            \"id\": \"1\",\n"
				+ "            \"type\": \"Address\",\n" + "            \"attributes\": {\n"
				+ "                \"addressLine1\": null,\n" + "                \"addressLine2\": null,\n"
				+ "                \"addressLine3\": null,\n" + "                \"city\": null,\n"
				+ "                \"state\": null,\n" + "                \"postalCode\": null,\n"
				+ "                \"country\": null\n" + "            }\n" + "        }\n" + "    ]\n" + "}";

		// Send POST request
		Response response = RestAssured.given().header("Authorization", "Bearer " + jwtToken)
				.header("Content-Type", "application/vnd.api+json").header("Accept", "application/vnd.api+json")
				.body(requestBody).post(endpoint);
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
		 //queryresponse.prettyPrint();
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
			System.out.println("Assertion failed: " + e.getMessage());
			throw e;
		}
		// Step 3: Retrieve sync API
		String  retrieveEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks/"
				+ syncBatchID + "/?include=Details";

		Response  retrieveResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
				.header("Authorization", "Bearer " + jwtToken).header("accept", "application/vnd.api+json")
				.get( retrieveEndpoint);

		// Print the response
		// retrieveResponse.prettyPrint();

		// Assert the status code
		 retrieveResponse.then().statusCode(200);

		// Assert attributes in the response
		try {
			assertEquals( retrieveResponse.jsonPath().getString("data.attributes.status"), "Awaiting",
					"Status attribute is not as expected");
			assertEquals( retrieveResponse.jsonPath().getString("data.attributes.operationType"), "ToNetwork",
					"Operation type attribute is not as expected");
			assertEquals( retrieveResponse.jsonPath().getString("data.attributes.packageType"), "Partial",
					"Package type attribute is not as expected");
			assertEquals( retrieveResponse.jsonPath().getString("data.attributes.stepName"), "Ready",
					"Step name attribute is not as expected");

			// Assert id and type in the response
			assertTrue( retrieveResponse.jsonPath().getString("data.id").matches("[a-f0-9-]{36}"),
					"ID is not in expected format");
			assertEquals( retrieveResponse.jsonPath().getString("data.id"),syncBatchID,
					"Sync Batch ID is not matching as expected.");
			assertEquals( retrieveResponse.jsonPath().getString("data.type"), "SyncTask",
					"Type attribute is not as expected");

		} catch (AssertionError e) {
			// Log the failure
			System.out.println("Assertion failed: " + e.getMessage());
			throw e;
		}
	}
}
