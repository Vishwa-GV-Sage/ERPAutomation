package dataGenerator;


import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import frameworkPkg.Helper;

public class CompanyDataCsv {

    

    public static void generateCompanyCSV(int numberOfRows) {
    	String csvFile = Helper.TempFileLocation + "company.csv"; // Construct the full file path
        try (FileWriter writer = new FileWriter(csvFile)) {
            // Write header
            writer.append("erpKey,companyName,companyType,isActive,defaultCurrencyCode,address1,address2,city,stateRegion,postalCode,country,phoneNumber,faxNumber,created,modified,taxId,dunsNumber,emailAddress\n");

            // Generate specified number of records
            Random random = new Random();
            for (int i = 0; i < numberOfRows; i++) {
                // Generate random data
                String erpKey = generateRandomUUID();
                String companyName = generateRandomString(10); // Generate random company name (10 characters)
                String companyType = "Company";
                boolean isActive = random.nextBoolean();
                String defaultCurrencyCode = "GBP";
                String address1 = generateRandomString(10); // Generate random address (10 characters)
                String address2 = generateRandomString(10); // Generate random address (10 characters)
                String city = generateRandomString(10); // Generate random city (10 characters)
                String stateRegion = generateRandomString(10); // Generate random state/region (10 characters)
                String postalCode = generateRandomString(6); // Generate random postal code (6 characters)
                String country = "GB";
                String phoneNumber = generateRandomPhoneNumber(); // Generate random phone number
                String faxNumber = "";
                String created = generateRandomDate(); // Generate random creation date
                String modified = generateRandomDateAfter(created); // Generate random modification date
                String taxId = generateRandomString(9); // Generate random tax ID (9 characters)
                String dunsNumber = "";
                String emailAddress = generateRandomString(8) + "@example.com"; // Generate random email address

                // Write data to file
                writer.append(String.join(",", erpKey, companyName, companyType, String.valueOf(isActive), defaultCurrencyCode, address1, address2, city, stateRegion, postalCode, country, phoneNumber, faxNumber, created, modified, taxId, dunsNumber, emailAddress))
                        .append("\n");
            }

            System.out.println("CSV file generated successfully!" + csvFile);
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
        return String.format("%02d-%02d-%04d",  month,day, year);
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

        // Return the modified date in the format "MM-dd-yyyy"
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
