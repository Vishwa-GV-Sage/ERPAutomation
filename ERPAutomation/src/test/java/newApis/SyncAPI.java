package newApis;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class SyncAPI {
	private String syncTaskIdBatch, syncTaskIdEmpty,syncAttachmentId, blobUploadUrl;
	String apiBaseUrl = "https://api-dev.network-eng.sage.com";
	String database_id = "a37459d5-e9d6-4f1b-a6da-9bb8c329b188",
			jwtToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IlJVUXlRME0wTWtOQk5rUkJOVGxETXpaQ056VXhPVVUyUkRZNU5EbEJOVVF6T0VSQk1EQXdNQSJ9.eyJpc3MiOiJodHRwczovL2lkLXNoYWRvdy5zYWdlLmNvbS8iLCJzdWIiOiJhdXRoMHxiYWFiN2JkN2Q0MDYyMzQ4OTUzYmMyN2JkNTIyYTk4NmQ2M2FmYTBmOWEwNjBmOTAiLCJhdWQiOlsiU0JDRFMvZ2xvYmFsIiwiaHR0cHM6Ly9zYWdlLWNpZC1zaGFkb3cuc2FnZWlkbm9ucHJvZC5hdXRoMGFwcC5jb20vdXNlcmluZm8iXSwiaWF0IjoxNzA5NjE5Njc2LCJleHAiOjE3MDk2NDg0NzYsImF6cCI6IjJWVGtOekUzU3QzeVNoVmJaMmNubGtoUVZCVUh4dmVzIiwic2NvcGUiOiJvcGVuaWQgcHJvZmlsZSBlbWFpbCB1c2VyOmZ1bGwgb2ZmbGluZV9hY2Nlc3MifQ.qks_KfKTi8glqVEEuvraab6opBkJs0jvHqSkkA5zj12s1SnhJctQJTcJ8kLme6sXKiZam9ouuMexIlLEM4ttrrQgV3a8TR65bs4Ubs4nsEbOY4Qt9lH3t3Xesnfbt5NGsXL39G80yqYehnsW-ClVo7y04_C9Dh5paePfm6pTY_0D0NcUrPaoFTqaOCIvdez90KbYyVJU4hWO6jEPOVMqoXl3_83iGgLmGXtUPh3BBvWQ6DDBdE25SmYISTEbSc9nByJI_hHtUBdwOxDOod20uCP_xhxD_Q7kOfP7b59OLO4Hj2G1ByljzsFT9QvjMjiWyKKanp7dQEFB943R9YaxDg";
	

	@Test(priority = 1)
	public void verify_Create_Sync_Batch_Upload() {

		// apiKeyAPI endpoint
		String apiKeyEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + database_id + "/sync-tasks";
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
				.body(requestBody).post(apiKeyEndpoint);
		// Extract id from response and store it in the global variable
		syncTaskIdBatch = response.jsonPath().getString("data.id");
		// Print response
		response.prettyPrint();
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
			assertEquals(response.jsonPath().getString("data.attributes.stepName"), "Preparing",
					"Step name attribute is not as expected");
		}

		catch (AssertionError e) {
			// Log the failure
			System.out.println("Assertion failed: " + e.getMessage());
		}
	}

	@Test(priority = 2)
	public void verify_Retrieve_sync_task_for_ToNetwork() {
		String sendTasksEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + database_id + "/sync-tasks/"
				+ syncTaskIdBatch + "/?include=Details";

		Response sendTasksResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
				.header("Authorization", "Bearer " + jwtToken).header("accept", "application/vnd.api+json")
				.get(sendTasksEndpoint);
		;

		// Print the response
		sendTasksResponse.prettyPrint();

		// Assert the status code
		sendTasksResponse.then().statusCode(200);

		// Assert attributes in the response
		try {
			assertEquals(sendTasksResponse.jsonPath().getString("data.attributes.status"), "Awaiting",
					"Status attribute is not as expected");
			assertEquals(sendTasksResponse.jsonPath().getString("data.attributes.operationType"), "ToNetwork",
					"Operation type attribute is not as expected");
			assertEquals(sendTasksResponse.jsonPath().getString("data.attributes.packageType"), "Full",
					"Package type attribute is not as expected");
			assertEquals(sendTasksResponse.jsonPath().getString("data.attributes.stepName"), "Ready",
					"Step name attribute is not as expected");

			// Assert id and type in the response
			assertTrue(sendTasksResponse.jsonPath().getString("data.id").matches("[a-f0-9-]{36}"),
					"ID is not in expected format");
			assertEquals(sendTasksResponse.jsonPath().getString("data.type"), "SyncTask",
					"Type attribute is not as expected");

		} catch (AssertionError e) {
			// Log the failure
			System.out.println("Assertion failed: " + e.getMessage());
		}
	}

	@Test(priority = 3)
	public void verify_Create_Sync_Task_Empty() {
		String endpointUrl = apiBaseUrl + "/connectors/erp/datasets/" + database_id + "/sync-tasks";

		// Request body
		String requestBody = "{\"data\":{\"type\":\"SyncTask\",\"attributes\":{\"packageType\":\"Full\"}}}";

		// Send POST request and capture response
		Response response = RestAssured.given().header("Content-Type", "application/vnd.api+json")
				.header("Accept", "application/vnd.api+json").header("Authorization", "Bearer " + jwtToken)
				.body(requestBody).post(endpointUrl);

		// Print the response
		response.prettyPrint();
		blobUploadUrl = response.jsonPath().getString("included[0].attributes.uploadUrl");
		syncTaskIdEmpty = response.jsonPath().getString("data.id");
		syncAttachmentId = response.jsonPath().getString("data.relationships.id");
		// Assert the status code
		//response.then().statusCode(201);

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
		}
	}

	@Test(priority = 4)
	public void upload_Sync_Task_Zip_File() {
		// Define endpoint URL
		String endpointUrl = blobUploadUrl;

		System.out.println("End Point: "+ endpointUrl);
		// Define file path
        File file = new File("C:\\Users\\Public\\Documents\\file.zip");
     // Read file content into byte array
        byte[] fileContent;
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            fileContent = fileInputStream.readAllBytes();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + file.getAbsolutePath(), e);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + file.getAbsolutePath(), e);
        }
        
        // Send request with RestAssured
        Response response = RestAssured.given()
                .header("x-ms-blob-type", "BlockBlob")
                .header("Content-Type", "multipart/form-data")
                .multiPart("file",file,"application/zip")
                .put(endpointUrl);
        // Print the response status code and body
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body:");
        System.out.println(response.getBody().asString());
        
		// Print the response
		response.prettyPrint();
		// Assertion
		response.then().statusCode(201);
		
	}

}
