package dataGenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import frameworkPkg.Helper;

public class CsvDataGenerator extends Helper {

	private static List<String> companyErpKeys = new LinkedList<>();
	private static List<String> customerErpKeys = new LinkedList<>();
	static String TempFileLocation = "C:\\Users\\Public\\Documents\\Temp\\";

	public static void generateCsvData(int rows) {

		// Clear the temp directory before generating new CSV files
		clearTempDirectory(TempFileLocation);
		String companyCsvFile = TempFileLocation + "company.csv"; // Path for company CSV file
		String contactCsvFile = TempFileLocation + "contact.csv"; // Path for contact CSV file
		String invoiceCsvFile = TempFileLocation + "invoice.csv"; // Path for invoice CSV file
		String paymentCsvFile = TempFileLocation + "payment.csv"; // Path for invoice CSV file

		generateCompanyData(companyCsvFile, rows); // Generate company data
		generateContactData(contactCsvFile, rows); // Generate contact data
		generateInvoiceData(invoiceCsvFile, rows);
		generatePaymentData(paymentCsvFile, rows);

		String zipFile = TempFileLocation + rows + "_csv.zip"; // Path for the zip file

		System.out.println("Creating zip file: " + zipFile);
		zipFiles(new String[] { companyCsvFile, contactCsvFile, invoiceCsvFile, paymentCsvFile }, zipFile);

	}

	private static void clearTempDirectory(String directoryPath) {
		File directory = new File(directoryPath);
		if (!directory.exists() || !directory.isDirectory()) {
			System.err.println("Directory not found: " + directoryPath);
			return;
		}

		File[] files = directory.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isFile()) {
					if (file.delete()) {
						System.out.println("Deleted file: " + file.getName());
					} else {
						System.err.println("Failed to delete file: " + file.getName());
					}
				}
			}
		}
	}

	private static void zipFiles(String[] srcFiles, String zipFile) {
		try {
			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);

			for (String srcFile : srcFiles) {
				File fileToZip = new File(srcFile);
				if (!fileToZip.exists()) {
					System.err.println("File not found: " + srcFile);
					continue;
				}
				FileInputStream fis = new FileInputStream(fileToZip);
				ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
				zos.putNextEntry(zipEntry);

				byte[] bytes = new byte[1024];
				int length;
				while ((length = fis.read(bytes)) >= 0) {
					zos.write(bytes, 0, length);
				}

				fis.close();
			}

			zos.close();
			fos.close();
			System.out.println("Zip file created successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void generateCompanyData(String companyCsvFile, int numberOfRows) {
		// Ensure that the number of rows is even
		if (numberOfRows % 2 != 0) {
			numberOfRows++; // Increment to make it even
		}

		Queue<String> companyTypes = new LinkedList<>(Arrays.asList("Company", "Customer"));

		// Predefined lists of cities, regions, and postal codes in England
		List<String> englandCities = Arrays.asList("London", "Manchester", "Birmingham", "Liverpool", "Leeds",
				"Sheffield", "Bristol", "Nottingham", "Leicester", "Newcastle upon Tyne");
		List<String> englandRegions = Arrays.asList("Greater London", "Greater Manchester", "West Midlands",
				"Merseyside", "West Yorkshire", "South Yorkshire", "Tyne and Wear", "Nottinghamshire", "Leicestershire",
				"Bristol");
		List<String> englandPostalCodes = Arrays.asList("E1 6AN", "M1 1AE", "B1 1AA", "L1 8JQ", "LS1 6ER", "S1 4QZ",
				"BS1 6NB", "NG1 2GR", "LE1 7RH", "NE1 4AG");

		try (FileWriter writer = new FileWriter(companyCsvFile)) {
			// Write header
			writer.append(
					"erpKey,companyName,companyType,isActive,defaultCurrencyCode,address1,address2,city,stateRegion,postalCode,country,phoneNumber,faxNumber,created,modified,taxId,dunsNumber,emailAddress\n");

			// Generate specified number of records
			for (int i = 0; i < numberOfRows; i++) {
				// Generate random data for company
				String erpKey = generateRandomUUID();

				// Get the next company type from the queue
				String companyType = companyTypes.poll();
				if (companyType == null) {
					// If the queue is empty, refill it with Company and Customer
					companyTypes.addAll(Arrays.asList("Company", "Customer"));
					companyType = companyTypes.poll();
				}

				// If the company type is Customer, add its erpKey to the list
				if (companyType.equals("Customer")) {
					customerErpKeys.add(erpKey);
				} else {
					companyErpKeys.add(erpKey);
				}

				boolean isActive = true; // You can set isActive to true by default

				String defaultCurrencyCode = "GBP";
				String companyName = generateRandomString(10); // Generate random company name (10 characters)
				String address1 = generateRandomString(10); // Generate random address (10 characters)
				String address2 = generateRandomString(10); // Generate random address (10 characters)
				String city = englandCities.get(new Random().nextInt(englandCities.size())); // Randomly select a city
																								// from the list
				String stateRegion = englandRegions.get(new Random().nextInt(englandRegions.size())); // Randomly select
																										// a region from
																										// the list
				String postalCode = englandPostalCodes.get(new Random().nextInt(englandPostalCodes.size())); // Randomly
																												// select
																												// a
																												// postal
																												// code
																												// from
																												// the
																												// list
				String country = "GB";
				String phoneNumber = generateRandomPhoneNumber(); // Generate random phone number
				String faxNumber = "";
				String created = generateRandomDate(); // Generate random creation date
				String modified = generateRandomDateAfter(created); // Generate random modification date
				String taxId = String.valueOf(new Random().nextInt(1000000000)); // Generate random tax ID (9
																					// characters)
				String dunsNumber = "";
				String emailAddress = generateRandomString(8) + "@example.com"; // Generate random email address

				// Write data to file
				writer.append(String.join(",", erpKey, companyName, companyType, String.valueOf(isActive),
						defaultCurrencyCode, address1, address2, city, stateRegion, postalCode, country, phoneNumber,
						faxNumber, created, modified, taxId, dunsNumber, emailAddress)).append("\n");
			}

			System.out.println("Company CSV file generated successfully at: " + companyCsvFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void generateContactData(String contactCsvFile, int numberOfRows) {
		numberOfRows = numberOfRows / 2;

		Queue<String> customerErpKeysQueue = new LinkedList<>(customerErpKeys);
		try (FileWriter writer = new FileWriter(contactCsvFile)) {
			// Write header
			writer.append(
					"erpKey,companyErpKey,isActive,contactName,contactCode,roleCode,emailAddress,phone,fax,address1,address2,city,stateRegion,postalCode,countryCode,created,modified\n");
			// Generate specified number of records
			Random random = new Random();
			for (int i = 0; i < numberOfRows; i++) {
				// Generate random data for contact
				String erpKey = generateRandomUUID();
				String companyErpKey = customerErpKeysQueue.poll(); // Get the next companyErpKey from the queue
				if (companyErpKey == null) {
					// If queue is empty, refill it with the companyErpKeys list
					customerErpKeysQueue.addAll(customerErpKeys);
					companyErpKey = customerErpKeysQueue.poll(); // Get the next companyErpKey from the queue
				}
				boolean isActive = true;
				String contactName = generateRandomString(10); // Generate random contact name (10 characters)
				String contactCode = "Registered Address";
				String roleCode = "";
				String emailAddress = generateRandomString(8) + "@customer1.com"; // Generate random email address
				String phone = generateRandomPhoneNumber(); // Generate random phone number
				String fax = "";
				String address1 = "house";
				String address2 = "street";
				String city = "town";
				String stateRegion = "county";
				String postalCode = "NE1 1AA";
				String countryCode = "GB";
				String created = generateRandomDate(); // Generate random creation date
				String modified = generateRandomDateAfter(created); // Generate random modification date

				// Write data to file
				writer.append(String.join(",", erpKey, companyErpKey, String.valueOf(isActive), contactName,
						contactCode, roleCode, emailAddress, phone, fax, address1, address2, city, stateRegion,
						postalCode, countryCode, created, modified)).append("\n");
			}

			System.out.println("Contact CSV file generated successfully at: " + contactCsvFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void generateInvoiceData(String invoiceCsvFile, int numberOfRows) {
		numberOfRows = numberOfRows / 2;
		Queue<String> customerErpKeysQueue = new LinkedList<>(customerErpKeys);
		Queue<String> companyErpKeysQueue = new LinkedList<>(companyErpKeys);
		try (FileWriter writer = new FileWriter(invoiceCsvFile)) {
			// Write header
			writer.append("erpKey,companyErpKey,customerErpKey,referenceCode,"
					+ "invoiceTypeCode,invoiceStatusCode,currencyCode,totalAmount,"
					+ "salesTaxAmount,outstandingBalanceAmount,invoiceDate,postedDate,"
					+ "paymentDueDate,importedDate,created,modified,inDispute\n");

			// Generate specified number of records
			for (int i = 0; i < numberOfRows; i++) {
				// Generate random data for invoice
				String erpKey = generateRandomUUID();

				String companyErpKey = companyErpKeysQueue.poll(); // Get the next companyErpKey from the queue
				if (companyErpKey == null) {
					// If queue is empty, refill it with the companyErpKeys list
					companyErpKeysQueue.addAll(companyErpKeys);
					companyErpKey = companyErpKeysQueue.poll(); // Get the next companyErpKey from the queue
				}

				String customerErpKey = customerErpKeysQueue.poll(); // Get the next companyErpKey from the queue
				if (customerErpKey == null) {
					// If queue is empty, refill it with the companyErpKeys list
					customerErpKeysQueue.addAll(customerErpKeys);
					companyErpKey = customerErpKeysQueue.poll(); // Get the next companyErpKey from the queue
				}

				String referenceCode = generateRandomString(8); // Generate random reference code (8 characters)
				String invoiceTypeCode = "AR Credit Memo";
				String invoiceStatusCode = "Open";
				String currencyCode = "GBP";
				int totalAmount = new Random().nextInt(1000) + 100; // Random total amount between 100 and 1099
				int salesTaxAmount = totalAmount / 6; // Sales tax amount is assumed to be 1/6th of total amount
				int outstandingBalanceAmount = 0; // Assuming no outstanding balance initially
				String invoiceDate = "02-10-2023 00:00";
				String postedDate = "02-10-2023 00:00";
				String paymentDueDate = "02-10-2023 00:00";
				String importedDate = "2/14/2023 10:57";
				String created = "02-10-2023 11:45";
				String modified = "02-10-2023 11:45";
				boolean inDispute = false;

				// Write data to file
				writer.append(String.join(",", erpKey, companyErpKey, customerErpKey, referenceCode, invoiceTypeCode,
						invoiceStatusCode, currencyCode, String.valueOf(totalAmount), String.valueOf(salesTaxAmount),
						String.valueOf(outstandingBalanceAmount), invoiceDate, postedDate, paymentDueDate, importedDate,
						created, modified, String.valueOf(inDispute))).append("\n");
			}

			System.out.println("Invoice CSV file generated successfully at: " + invoiceCsvFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void generatePaymentData(String paymentCsvFile, int numberOfRows) {

		numberOfRows = numberOfRows / 2;
		Queue<String> companyErpKeysQueue = new LinkedList<>(companyErpKeys);

		try (FileWriter writer = new FileWriter(paymentCsvFile)) {
			// Write header
			writer.append("erpKey,companyErpKey,paymentType,isOpen,paymentDate,postDate,paymentAmount,unappliedAmount,"
					+ "tenderType,memoText,currencyCode,created,modified,referenceCode,isVoided,inDispute\n");

			// Generate specified number of records
			Random random = new Random();
			for (int i = 0; i < numberOfRows; i++) {
				// Generate random data for payment
				String erpKey = generateRandomUUID();
				String companyErpKey = companyErpKeysQueue.poll(); // Get the next companyErpKey from the queue
				if (companyErpKey == null) {
					// If queue is empty, refill it with the companyErpKeys list
					companyErpKeysQueue.addAll(companyErpKeys);
					companyErpKey = companyErpKeysQueue.poll(); // Get the next companyErpKey from the queue
				}
				String paymentType = "AR Payment";
				boolean isOpen = false;
				String paymentDate = "10-02-2023";
				String postDate = "10-02-2023";
				int paymentAmount = -240; // Negative value for payment
				int unappliedAmount = 0;
				String tenderType = "Check";
				String memoText = "";
				String currencyCode = "GBP";
				String created = "02-10-2023 11:45";
				String modified = "02-10-2023 11:48";
				String referenceCode = "";
				boolean isVoided = false;
				boolean inDispute = false;

				// Write data to file
				writer.append(String.join(",", erpKey, companyErpKey, paymentType, String.valueOf(isOpen), paymentDate,
						postDate, String.valueOf(paymentAmount), String.valueOf(unappliedAmount), tenderType, memoText,
						currencyCode, created, modified, referenceCode, String.valueOf(isVoided),
						String.valueOf(inDispute))).append("\n");
			}

			System.out.println("Payment CSV file generated successfully at: " + paymentCsvFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Method to generate a random UUID (for erpKey)
	private static String generateRandomUUID() {
		return java.util.UUID.randomUUID().toString().toUpperCase();
	}

	// Method to generate a random string of specified length
	private static String generateRandomString(int length) {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder sb = new StringBuilder(length);
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(characters.charAt(random.nextInt(characters.length())));
		}
		return sb.toString();
	}

	// Method to generate a random phone number
	private static String generateRandomPhoneNumber() {
		Random random = new Random();
		StringBuilder phoneNumber = new StringBuilder();
		phoneNumber.append("0"); // UK format
		for (int i = 0; i < 10; i++) {
			phoneNumber.append(random.nextInt(10));
		}
		return phoneNumber.toString();
	}

	// Method to generate a random date (in dd-MM-yyyy format)
	private static String generateRandomDate() {
		Random random = new Random();
		int day = random.nextInt(28) + 1; // Random day between 1 and 28
		int month = random.nextInt(12) + 1; // Random month between 1 and 12
		int year = 2020 + random.nextInt(5); // Random year between 2020 and 2024
		return String.format("%02d-%02d-%04d", month, day, year);
	}

	private static String generateRandomDateAfter(String date) {
		// Parse day, month, and year from the input date string
		int month = Integer.parseInt(date.substring(0, 2));
		int day = Integer.parseInt(date.substring(3, 5));
		int year = Integer.parseInt(date.substring(6));

		// Increment the day by 1
		day++;

		// Check if the incremented day exceeds the number of days in the month
		if (day > getDaysInMonth(month, year)) {
			// Set day to 1 and increment month if day exceeds maximum days in month
			day = 1;
			month++;

			// Reset month to January and increment year if month exceeds December
			if (month > 12) {
				month = 1;
				year++;
			}
		}

		// Return the modified date in the format "dd-MM-yyyy"
		return String.format("%02d-%02d-%04d", month, day, year);
	}

	// Method to get the number of days in a month
	private static int getDaysInMonth(int month, int year) {
		switch (month) {
		case 2:
			// February has 29 days in leap years, 28 days otherwise
			return (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) ? 29 : 28;
		case 4:
		case 6:
		case 9:
		case 11:
			// April, June, September, and November have 30 days
			return 30;
		default:
			// All other months have 31 days
			return 31;
		}
	}
}
