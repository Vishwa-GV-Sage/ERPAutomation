package frameworkPkg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ChromeDriverUpdater {
	public void checkChromeDriver() throws NumberFormatException, IOException {

		String chromeDriverFileLocation = System.getProperty("user.dir") + "\\driverExe\\";
		int installedChromeversion = Integer.parseInt(getInstalledChromeBrowserVersion());

		int usingChromeDriverVersion = Integer
				.parseInt(getCurrentChromeDriverVersion(chromeDriverFileLocation + "chromedriver.exe"));
		//String[] chromeDriverVersion = getLatestChromeDriverVersion();
		//updateToLatestChromeDriver(chromeDriverVersion[1], chromeDriverFileLocation);
		if (usingChromeDriverVersion < installedChromeversion) {
			String[] chromeDriverVersion = getLatestChromeDriverVersion();
			int latestAvailabeChromeDriver = Integer.parseInt(chromeDriverVersion[0]);
			if (usingChromeDriverVersion < latestAvailabeChromeDriver) {
			updateToLatestChromeDriver(chromeDriverVersion[1], chromeDriverFileLocation);
			}

		} else {
			System.out.println("Hurray!! Chrome Driver is up to date.");
		}
	}

	private static void updateToLatestChromeDriver(String chromeDriverVersion, String chromeDriverDir) {

		String chromeDriverZipLink = "https://edgedl.me.gvt1.com/edgedl/chrome/chrome-for-testing/"
				+ chromeDriverVersion + "/win32/chromedriver-win32.zip";
		System.out.println("Will download " + chromeDriverZipLink);

		String chromeDriverZipFileLocation = Path.of(chromeDriverDir, "chromedriver_win32.zip").toString();
		System.out.println("Zip file download path: " + chromeDriverZipFileLocation);

		try {
			downloadFile(chromeDriverZipLink, chromeDriverZipFileLocation);

			String unzipLocation = chromeDriverDir;
			System.out.println("Unzip location: " + unzipLocation);
			unzipFile(chromeDriverZipFileLocation, unzipLocation);

			String sourceFolderPath = Path.of(unzipLocation, "chromedriver-win32").toString();
			String destinationFolderPath = unzipLocation;

			File sourceFolder = new File(sourceFolderPath);
			if (sourceFolder.isDirectory()) {
				File destinationFolder = new File(destinationFolderPath);
				if (!destinationFolder.isDirectory()) {
					destinationFolder.mkdirs();
				}

				File[] files = sourceFolder.listFiles();
				if (files != null) {
					for (File file : files) {
						File destinationFile = new File(destinationFolder, file.getName());
						Files.move(file.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
						System.out.println("Moved " + file.getName() + " to " + destinationFile.getAbsolutePath());
					}
				}
			} else {
				System.out.println("Source folder not found: " + sourceFolderPath);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void downloadFile(String sourceUrl, String destinationPath) throws IOException {
		URL url = new URL(sourceUrl);
		try (InputStream in = url.openStream(); OutputStream out = new FileOutputStream(destinationPath)) {
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
		}
	}

	private static void unzipFile(String zipFilePath, String destinationFolderPath) throws IOException {
		try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath))) {
			ZipEntry entry;
			while ((entry = zipInputStream.getNextEntry()) != null) {
				if (!entry.isDirectory()) {
					String entryName = entry.getName();
					Path entryPath = Path.of(destinationFolderPath, entryName);
					if (!Files.exists(entryPath.getParent())) {
						Files.createDirectories(entryPath.getParent());
					}

					try (OutputStream outputStream = new FileOutputStream(entryPath.toFile())) {
						byte[] buffer = new byte[4096];
						int bytesRead;
						while ((bytesRead = zipInputStream.read(buffer)) != -1) {
							outputStream.write(buffer, 0, bytesRead);
						}
					}
				}
				zipInputStream.closeEntry();
			}
		}
	}

	private static String[] getLatestChromeDriverVersion() {
		String jsonUrl = "https://googlechromelabs.github.io/chrome-for-testing/last-known-good-versions.json";
		String stableVersionPrefix = null, stableVersion = null;

		try {
			URL url = new URL(jsonUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			int responseCode = connection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				StringBuilder response = new StringBuilder();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				String jsonResponse = response.toString();

				JSONParser parser = new JSONParser();
				JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);
				JSONObject channels = (JSONObject) jsonObject.get("channels");
				JSONObject stable = (JSONObject) channels.get("Stable");
				stableVersion = (String) stable.get("version");

				System.out.println("Latest Chrome Driver    : " + stableVersion);
				int dotIndex = stableVersion.indexOf('.');
				if (dotIndex > 0) {
					stableVersionPrefix = stableVersion.substring(0, dotIndex);
				}
			} else {
				System.out.println("HTTP Request Failed with response code: " + responseCode);
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		return new String[] { stableVersionPrefix, stableVersion };
	}

	private static String getCurrentChromeDriverVersion(String chromeDriverFileLocation) {

		String chromeDriverFileVersion = null, chromeDriverCurrentVersion = null;
		File chromeDriverFile = new File(chromeDriverFileLocation);
		if (chromeDriverFile.exists() && chromeDriverFile.isFile()) {
			chromeDriverFileVersion = getChromeDriverFileVersion(chromeDriverFile);
			chromeDriverCurrentVersion = getChromeDriverVersion(chromeDriverFileVersion);
			System.out.println("Current Chrome Driver   : " + chromeDriverCurrentVersion);

			int indexOfFirstPeriod = chromeDriverCurrentVersion.indexOf('.');

			if (indexOfFirstPeriod != -1) {
				chromeDriverCurrentVersion = chromeDriverCurrentVersion.substring(0, indexOfFirstPeriod);

			} else {
				System.out.println("No period found in the input.");
			}

		} else {
			chromeDriverCurrentVersion = "";
			System.out.println(chromeDriverCurrentVersion);
		}
		return chromeDriverCurrentVersion;
	}

	private static String getChromeDriverFileVersion(File file) {
		try {
			ProcessBuilder processBuilder = new ProcessBuilder(file.getAbsolutePath(), "--version");
			Process process = processBuilder.start();

			try (java.util.Scanner scanner = new java.util.Scanner(process.getInputStream()).useDelimiter("\\A")) {
				String versionOutput = scanner.hasNext() ? scanner.next() : "";
				return versionOutput;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	private static String getChromeDriverVersion(String versionOutput) {
		Pattern pattern = Pattern.compile("ChromeDriver (\\d+\\.\\d+\\.\\d+(\\.\\d+)?)");
		Matcher matcher = pattern.matcher(versionOutput);
		if (matcher.find()) {
			return matcher.group(1);
		} else {
			return "";
		}
	}

	public static String getInstalledChromeBrowserVersion() {
		try {
			// Command to run
			String command = "reg query \"HKEY_CURRENT_USER\\Software\\Google\\Chrome\\BLBeacon\" /v version";

			// Create ProcessBuilder
			ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
			processBuilder.redirectErrorStream(true);

			// Start the process
			Process process = processBuilder.start();

			// Read the process output
			InputStream inputStream = process.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

			String line;
			while ((line = reader.readLine()) != null) {
				// Check if the line contains the version information
				if (line.contains("version")) {
					// Extract the version value using split
					String[] parts = line.split("\\s+");
					if (parts.length >= 3) {
						String version = parts[parts.length - 1];
						System.out.println("Installed Chrome Browser: " + version);
						// Extract characters before the first period
						int dotIndex = version.indexOf('.');
						if (dotIndex > 0) {
							return version.substring(0, dotIndex);
						}
					}
				}
			}

			// Wait for the process to complete
			int exitCode = process.waitFor();
			System.out.println("Process exited with code: " + exitCode);

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
