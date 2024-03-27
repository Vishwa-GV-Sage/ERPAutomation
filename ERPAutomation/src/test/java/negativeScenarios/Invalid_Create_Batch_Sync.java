package negativeScenarios;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import frameworkPkg.Helper;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Invalid_Create_Batch_Sync extends Helper {

	String apiBaseUrl = syncAPIBaseURL, dataset_id = syncAPIDataset_ID, jwtToken = syncAPIJwtToken, syncTaskIdBatch;
	String expiredToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IlJVUXlRME0wTWtOQk5rUkJOVGxETXpaQ056VXhPVVUyUkRZNU5EbEJOVVF6T0VSQk1EQXdNQSJ9.eyJpc3MiOiJodHRwczovL2lkLXNoYWRvdy5zYWdlLmNvbS8iLCJzdWIiOiJhdXRoMHw0MTc3NzcyMjhlNGZmMzBhMmJkNjI2NDdkZmJmN2VjMTEyMTNjZDYyMzYwNzViZDMiLCJhdWQiOlsiU0JDRFMvZ2xvYmFsIiwiaHR0cHM6Ly9zYWdlLWNpZC1zaGFkb3cuc2FnZWlkbm9ucHJvZC5hdXRoMGFwcC5jb20vdXNlcmluZm8iXSwiaWF0IjoxNzA5NzM1MTMzLCJleHAiOjE3MDk3NjM5MzMsImF6cCI6Ilduczl0TlViWTNDdktONXNFb1p3S3pTYzVrd081UVVaIiwic2NvcGUiOiJvcGVuaWQgcHJvZmlsZSBlbWFpbCB1c2VyOmZ1bGwgb2ZmbGluZV9hY2Nlc3MifQ.A7qRTo-MX0knfXQaQWBX0xABLQxCzL3oa3tNfyQf9irdosk27-BwP_LwysRcCqxbVU2xPE8-hRSQ2eJL5w4b43AHU58wYxkTqFQaBGTB5sTltXJJyitzy7pOwONXJyr60m4AtQFHZUtdNTTzpBLOm8fGn82QQxY-jLvB9kDMguxafLgAQnmq3z-cTEUsP2KR94O9fSv2DCPe0vGDaDslDz7Wl8qtcUd8vR-_y8YFv8lw6gEadx0Yl7E2VYHHBJZEI2DJGxPH_RUOePMYOO_Q7ui6r7pn-hfQb8GA7A0fTsjSfA_96khHk3TgpU0j2VHPL9U20O31nMOAipzz8mvL9g";
	String requestBody = "{\n" + "    \"Data\": {\n" + "        \"Attributes\": {\n"
			+ "            \"PackageType\": \"Full\"\n" + "        },\n" + "        \"Relationships\": {\n"
			+ "            \"Companies\": {\n" + "                \"data\": [\n" + "                    {\n"
			+ "                        \"Id\": \"1041\",\n" + "                        \"Type\": \"Company\"\n"
			+ "                    }\n" + "                ]\n" + "            },\n" + "            \"Contacts\": {\n"
			+ "                \"data\": [\n" + "                    {\n"
			+ "                        \"Id\": \"1360\",\n" + "                        \"Type\": \"Contact\"\n"
			+ "                    }\n" + "                ]\n" + "            },\n" + "            \"Invoices\": {\n"
			+ "                \"data\": [\n" + "                    {\n"
			+ "                        \"Id\": \"183705\",\n" + "                        \"Type\": \"Invoice\"\n"
			+ "                    }\n" + "                ]\n" + "            },\n"
			+ "            \"InvoiceLines\": {\n" + "                \"data\": [\n" + "                    {\n"
			+ "                        \"Id\": \"407628\",\n" + "                        \"Type\": \"InvoiceLine\"\n"
			+ "                    }\n" + "                ]\n" + "            },\n" + "            \"Payments\": {\n"
			+ "                \"data\": [\n" + "                    {\n"
			+ "                        \"Id\": \"183680\",\n" + "                        \"Type\": \"Payment\"\n"
			+ "                    }\n" + "                ]\n" + "            },\n"
			+ "            \"PaymentAllocations\": {\n" + "                \"data\": [\n" + "                    {\n"
			+ "                        \"Id\": \"41701\",\n"
			+ "                        \"Type\": \"PaymentApplication\"\n" + "                    }\n"
			+ "                ]\n" + "            },\n" + "            \"CreditAllocations\": {\n"
			+ "                \"data\": [\n" + "                    {\n"
			+ "                        \"Id\": \"12551\",\n"
			+ "                        \"Type\": \"CreaditMemoApplication\"\n" + "                    }\n"
			+ "                ]\n" + "            },\n" + "            \"FinancialYearSettings\": {\n"
			+ "                \"data\": [\n" + "                    {\n" + "                        \"Id\": \"1\",\n"
			+ "                        \"Type\": \"FinancialYearSetting\"\n" + "                    }\n"
			+ "                ]\n" + "            },\n" + "            \"FinancialAccounts\": {\n"
			+ "                \"data\": [\n" + "                    {\n" + "                        \"Id\": \"119\",\n"
			+ "                        \"Type\": \"FinancialAccount\"\n" + "                    }\n"
			+ "                ]\n" + "            },\n" + "            \"FinancialAccountBalanceHistories\": {\n"
			+ "                \"data\": [\n" + "                    {\n" + "                        \"Id\": \"15\",\n"
			+ "                        \"Type\": \"FinancialAccountBalanceHistory\"\n" + "                    }\n"
			+ "                ]\n" + "            },\n" + "            \"BaseCurrencies\": {\n"
			+ "                \"data\": [\n" + "                    {\n" + "                        \"Id\": \"1\",\n"
			+ "                        \"Type\": \"BaseCurrency\"\n" + "                    }\n" + "                ]\n"
			+ "            },\n" + "            \"CustomFields\": {\n" + "                \"data\": [\n"
			+ "                    {\n" + "                        \"Id\": \"183705\",\n"
			+ "                        \"Type\": \"CustomField\"\n" + "                    }\n" + "                ]\n"
			+ "            },\n" + "            \"JournalEntries\": {\n" + "                \"data\": [\n"
			+ "                    {\n" + "                        \"Id\": \"68736\",\n"
			+ "                        \"Type\": \"JournalEntry\"\n" + "                    }\n" + "                ]\n"
			+ "            },\n" + "            \"JournalEntryLines\": {\n" + "                \"data\": [\n"
			+ "                    {\n" + "                        \"Id\": \"146336\",\n"
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
			+ "                \"timeStamp\": \"2023-12-22T13:19:43.083Z\",\n" + "                \"status\": null,\n"
			+ "                \"message\": null\n" + "            },\n" + "            \"relationships\": {\n"
			+ "                \"primaryContact\": {\n" + "                    \"id\": \"1360\",\n"
			+ "                    \"type\": \"Contact\"\n" + "                },\n"
			+ "                \"address\": {\n" + "                    \"data\": {\n"
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
			+ "                \"timeStamp\": \"2023-12-22T13:19:43.083Z\",\n" + "                \"status\": null,\n"
			+ "                \"message\": null\n" + "            },\n" + "            \"relationships\": {\n"
			+ "                \"primaryContact\": {\n" + "                    \"id\": \"1360\",\n"
			+ "                    \"type\": \"Contact\"\n" + "                },\n"
			+ "                \"address\": {\n" + "                    \"data\": {\n"
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
			+ "                \"timeStamp\": \"2023-12-22T13:19:43.083Z\",\n" + "                \"status\": null,\n"
			+ "                \"message\": null\n" + "            },\n" + "            \"relationships\": {\n"
			+ "                \"primaryContact\": {\n" + "                    \"id\": \"1360\",\n"
			+ "                    \"type\": \"Contact\"\n" + "                },\n"
			+ "                \"address\": {\n" + "                    \"data\": {\n"
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
			+ "                \"timeStamp\": \"2023-12-22T13:19:43.083Z\",\n" + "                \"status\": null,\n"
			+ "                \"message\": null\n" + "            },\n" + "            \"relationships\": {\n"
			+ "                \"primaryContact\": {\n" + "                    \"id\": \"1360\",\n"
			+ "                    \"type\": \"Contact\"\n" + "                },\n"
			+ "                \"address\": {\n" + "                    \"data\": {\n"
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
			+ "                \"webpageUrl\": null,\n" + "                \"created\": \"2023-12-22T13:19:43.083Z\",\n"
			+ "                \"modified\": \"2023-12-22T13:19:43.083Z\"\n" + "            }\n" + "        },\n"
			+ "        {\n" + "            \"id\": \"183705\",\n" + "            \"type\": \"Invoice\",\n"
			+ "            \"attributes\": {\n" + "                \"networkId\": null,\n"
			+ "                \"operationType\": \"Insert\",\n"
			+ "                \"invoiceReferenceCode\": \"84227\",\n" + "                \"sendImmediately\": false,\n"
			+ "                \"isEInvoice\": false,\n" + "                \"invoiceType\": \"AR Invoice\",\n"
			+ "                \"customerId\": \"B44A68B0-FFE2-4D7A-ACA4-119663F30DC1\",\n"
			+ "                \"totalAmount\": 100.0,\n" + "                \"currencyCode\": \"USD\",\n"
			+ "                \"invoiceDate\": \"2023-12-22T13:19:43.083Z\",\n"
			+ "                \"created\": \"2023-12-22T13:19:43.083Z\",\n" + "                \"isDraft\": true,\n"
			+ "                \"departmentErpKey\": \"1251A2FA-CFDD-440C-97E6-0018A3AEB14B\",\n"
			+ "                \"description\": null,\n" + "                \"discountAmount\": 0,\n"
			+ "                \"outstandingBalanceAmount\": 100.0,\n"
			+ "                \"paymentDueDate\": \"2023-12-22T13:19:43.083Z\",\n"
			+ "                \"paymentPriority\": null,\n" + "                \"purchaseOrderCode\": \"\",\n"
			+ "                \"referenceCode\": \"84227\",\n" + "                \"salesPersonCode\": null,\n"
			+ "                \"salesTaxAmount\": 0,\n" + "                \"termName\": \"\",\n"
			+ "                \"companyErpKey\": \"33E72B9E-11EE-49C0-BC08-8DFBE25A0EB5\",\n"
			+ "                \"timeStamp\": \"2023-12-22T13:19:43.083Z\",\n" + "                \"status\": null,\n"
			+ "                \"message\": null\n" + "            },\n" + "            \"relationships\": {\n"
			+ "                \"lines\": [\n" + "                    {\n" + "                        \"data\": {\n"
			+ "                            \"id\": \"407628\",\n"
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
			+ "                \"paymentMethodCode\": \"EFT\",\n" + "                \"paymentReference\": \"666\",\n"
			+ "                \"transactionDate\": \"2023-12-22T13:19:43.083Z\",\n"
			+ "                \"transactionCurrencyCode\": \"USD\",\n" + "                \"status\": null,\n"
			+ "                \"timeStamp\": \"2023-12-22T13:19:43.083Z\",\n" + "                \"message\": null\n"
			+ "            },\n" + "            \"relationships\": {\n" + "                \"allocations\": [\n"
			+ "                    {\n" + "                        \"data\": {\n"
			+ "                            \"id\": \"41701\",\n"
			+ "                            \"type\": \"PaymentApplication\"\n" + "                        }\n"
			+ "                    }\n" + "                ]\n" + "            }\n" + "        },\n" + "        {\n"
			+ "            \"id\": \"41701\",\n" + "            \"type\": \"PaymentApplication\",\n"
			+ "            \"attributes\": {\n" + "                \"allocatedAmount\": 100,\n"
			+ "                \"documentId\": \"183705\",\n" + "                \"documentType\": \"AR Invoice\",\n"
			+ "                \"transactionDate\": \"2023-12-22T13:19:43.083Z\",\n"
			+ "                \"timeStamp\": \"2023-12-22T13:19:43.083Z\",\n" + "                \"status\": null,\n"
			+ "                \"message\": null\n" + "            }\n" + "        },\n" + "        {\n"
			+ "            \"id\": \"12551\",\n" + "            \"type\": \"CreaditMemoApplication\",\n"
			+ "            \"attributes\": {\n" + "                \"allocatedAmount\": 0,\n"
			+ "                \"documentId\": \"\",\n" + "                \"documentType\": \"\",\n"
			+ "                \"transactionDate\": \"2023-12-22T13:19:43.083Z\",\n"
			+ "                \"timeStamp\": \"2023-12-22T13:19:43.083Z\",\n" + "                \"status\": null,\n"
			+ "                \"message\": null\n" + "            }\n" + "        },\n" + "        {\n"
			+ "            \"id\": \"68736\",\n" + "            \"type\": \"JournalEntry\",\n"
			+ "            \"attributes\": {\n" + "                \"networkId\": null,\n"
			+ "                \"operationType\": \"Insert\",\n"
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
			+ "        },\n" + "        {\n" + "            \"id\": \"1\",\n" + "            \"type\": \"Address\",\n"
			+ "            \"attributes\": {\n" + "                \"addressLine1\": null,\n"
			+ "                \"addressLine2\": null,\n" + "                \"addressLine3\": null,\n"
			+ "                \"city\": null,\n" + "                \"state\": null,\n"
			+ "                \"postalCode\": null,\n" + "                \"country\": null\n" + "            }\n"
			+ "        }\n" + "    ]\n" + "}";

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

	@Test(priority = 4)
	public void verify_Query_Batch_Sync_withoutToken_Response_Status() {

		String queryEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id
				+ "/sync-tasks?filter=operationtype eq 'toNetwork'&take=3&skip=0";
		Response queryResponse = RestAssured.given().header("Content-Type", "application/vnd.api+json")
				.header("Accept", "application/vnd.api+json").get(queryEndpoint);
		// Print the response
		// queryresponse.prettyPrint();
		// Assert the status code
		int expectedStatusCode = 401;
		int actualStatusCode = queryResponse.getStatusCode();
		assertEquals(actualStatusCode, expectedStatusCode, "Status code is not as expected");
	}

	@Test(priority = 5)
	public void verify_Query_Batch_Sync_invalidToken_Response_Status() {

		String queryEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id
				+ "/sync-tasks?filter=operationtype eq 'toNetwork'&take=3&skip=0";
		Response queryResponse = RestAssured.given().header("Content-Type", "application/vnd.api+json")
				.header("Authorization", "Bearer abcd123").header("Accept", "application/vnd.api+json")
				.get(queryEndpoint);
		// Print the response
		// queryresponse.prettyPrint();
		// Assert the status code
		int expectedStatusCode = 401;
		int actualStatusCode = queryResponse.getStatusCode();
		assertEquals(actualStatusCode, expectedStatusCode, "Status code is not as expected");
	}

	@Test(priority = 6)
	public void verify_Query_Batch_Sync_expiredToken_Response_Status() {

		String queryEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id
				+ "/sync-tasks?filter=operationtype eq 'toNetwork'&take=3&skip=0";
		Response queryResponse = RestAssured.given().header("Content-Type", "application/vnd.api+json")
				.header("Authorization", "Bearer " + expiredToken).header("Accept", "application/vnd.api+json")
				.get(queryEndpoint);
		// Print the response
		// queryresponse.prettyPrint();
		// Assert the status code
		int expectedStatusCode = 401;
		int actualStatusCode = queryResponse.getStatusCode();
		assertEquals(actualStatusCode, expectedStatusCode, "Status code is not as expected");
	}

	@Test(priority = 7)
	public void verify_Retrieve_Batch_withoutToken_Sync_Response_Status() {
		// apiKeyAPI endpoint
		String endpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks";
		// Send POST request
		Response response = RestAssured.given().header("Authorization", "Bearer " + jwtToken)
				.header("Content-Type", "application/vnd.api+json").header("Accept", "application/vnd.api+json")
				.body(requestBody).post(endpoint);
		// Extract id from response and store it in the global variable
		syncTaskIdBatch = response.jsonPath().getString("data.id");
		String retrieveEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks/"
				+ syncTaskIdBatch + "/?include=Details";

		Response retrieveResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
				.header("accept", "application/vnd.api+json").get(retrieveEndpoint);

		// Print the response
		// sendTasksResponse.prettyPrint();

		// Assert the status code
		int expectedStatusCode = 401;
		int actualStatusCode = retrieveResponse.getStatusCode();
		assertEquals(actualStatusCode, expectedStatusCode, "Status code is not as expected");

	}

	@Test(priority = 8)
	public void verify_Retrieve_Batch_invalidToken_Sync_Response_Status() {
		// apiKeyAPI endpoint
		String endpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks";
		// Send POST request
		Response response = RestAssured.given().header("Authorization", "Bearer " + jwtToken)
				.header("Content-Type", "application/vnd.api+json").header("Accept", "application/vnd.api+json")
				.body(requestBody).post(endpoint);
		// Extract id from response and store it in the global variable
		syncTaskIdBatch = response.jsonPath().getString("data.id");
		String retrieveEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks/"
				+ syncTaskIdBatch + "/?include=Details";

		Response retrieveResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
				.header("Authorization", "Bearer abcd123").header("accept", "application/vnd.api+json")
				.get(retrieveEndpoint);

		// Print the response
		// sendTasksResponse.prettyPrint();

		// Assert the status code
		int expectedStatusCode = 401;
		int actualStatusCode = retrieveResponse.getStatusCode();
		assertEquals(actualStatusCode, expectedStatusCode, "Status code is not as expected");

	}

	@Test(priority = 9)
	public void verify_Retrieve_Batch_expiredToken_Sync_Response_Status() {
		// apiKeyAPI endpoint
		String endpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks";
		// Send POST request
		Response response = RestAssured.given().header("Authorization", "Bearer " + jwtToken)
				.header("Content-Type", "application/vnd.api+json").header("Accept", "application/vnd.api+json")
				.body(requestBody).post(endpoint);
		// Extract id from response and store it in the global variable
		syncTaskIdBatch = response.jsonPath().getString("data.id");
		String retrieveEndpoint = apiBaseUrl + "/connectors/erp/datasets/" + dataset_id + "/sync-tasks/"
				+ syncTaskIdBatch + "/?include=Details";

		Response retrieveResponse = RestAssured.given().header("Accept", "application/vnd.api+json")
				.header("Authorization", "Bearer " + expiredToken).header("accept", "application/vnd.api+json")
				.get(retrieveEndpoint);

		// Print the response
		// sendTasksResponse.prettyPrint();

		// Assert the status code
		int expectedStatusCode = 401;
		int actualStatusCode = retrieveResponse.getStatusCode();
		assertEquals(actualStatusCode, expectedStatusCode, "Status code is not as expected");

	}

	@Test(priority = 10)
	public void verify_Create_Sync_Batch_withoutDatasetID_Full_Response_Status() {

		// apiKeyAPI endpoint
		String endpoint = apiBaseUrl + "/connectors/erp/datasets/sync-tasks";

		// Send POST request
		Response response = RestAssured.given().header("Content-Type", "application/vnd.api+json")
				.header("Authorization", "Bearer " + jwtToken).header("Accept", "application/vnd.api+json")
				.body(requestBody).post(endpoint);

		// Print response
		// response.prettyPrint();
		// Assert the status code...

		int expectedStatusCode = 404;
		int actualStatusCode = response.getStatusCode();
		assertEquals(actualStatusCode, expectedStatusCode, "Status code is not as expected");

	}
	
	@Test(priority = 11)
	public void verify_Create_Sync_Batch_invalidDatasetID_Full_Response_Status() {

		// apiKeyAPI endpoint
		String endpoint = apiBaseUrl + "/connectors/erp/datasets a11111d5-e9d6-4f1b-a6da-9bb8c329b188/sync-tasks";

		// Send POST request
		Response response = RestAssured.given().header("Content-Type", "application/vnd.api+json")
				.header("Authorization", "Bearer " + jwtToken).header("Accept", "application/vnd.api+json")
				.body(requestBody).post(endpoint);

		// Print response
		// response.prettyPrint();
		
		// Assert the status code...
		int expectedStatusCode = 404;
		int actualStatusCode = response.getStatusCode();
		assertEquals(actualStatusCode, expectedStatusCode, "Status code is not as expected");

	}

}
