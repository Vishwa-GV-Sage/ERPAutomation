Set-ExecutionPolicy -Scope CurrentUser remotesigned
Clear-Host

$ChromeDir = "C:\Program Files\Google\Chrome\Application\chrome.exe"

if (-Not (Test-Path $ChromeDir -PathType Leaf)) {
    Write-Output "Chrome not found in '$ChromeDir'. Please install Chrome or specify a custom Chrome location with -ChromeDir argument."
    Exit 1
}

$apiUrl = "https://googlechromelabs.github.io/chrome-for-testing/last-known-good-versions.json"
$response = Invoke-RestMethod -Uri $apiUrl -Method Get

$stableVersion = $response.channels.Stable.version

$thisScriptRoot = if ($PSScriptRoot -eq "") { "." } else { $PSScriptRoot }
$chromeDriverDir = $thisScriptRoot
$chromeDriverFileLocation = Join-Path $chromeDriverDir "chromedriver.exe"
$chromeVersion = [System.Diagnostics.FileVersionInfo]::GetVersionInfo($ChromeDir).FileVersion
$chromeMajorVersion = $chromeVersion.split(".")[0]

if (-Not (Test-Path $chromeDriverDir -PathType Container)) {
    New-Item -ItemType directory -Path $chromeDriverDir
}

if (Test-Path $chromeDriverFileLocation -PathType Leaf) {
    $chromeDriverFileVersion = (& $chromeDriverFileLocation --version)
    $chromeDriverFileVersionHasMatch = $chromeDriverFileVersion -match "ChromeDriver (\d+\.\d+\.\d+(\.\d+)?)"
    $chromeDriverCurrentVersion = if ($chromeDriverFileVersionHasMatch) { $matches[1] } else { $null }
} else {
    $chromeDriverCurrentVersion = ''
}

Write-Host "Chrome browser version              : $chromeVersion"
Write-Host "Installed ChromeDriver version      : $chromeDriverCurrentVersion"
Write-Host "Online latest ChromeDriver Version  : $stableVersion"

$chromMajorVersion = $chromeVersion.Substring(0, 3)
$installedChromDriverMajorVersion = $chromeDriverCurrentVersion.Substring(0, 3)

if ($chromMajorVersion -gt $installedChromDriverMajorVersion) {
    Write-Host "Update required..."
    $chromeDriverZipLink = "https://edgedl.me.gvt1.com/edgedl/chrome/chrome-for-testing/$stableVersion/win32/chromedriver-win32.zip"
    Write-Output "Will download $chromeDriverZipLink"
     
    $chromeDriverZipFileLocation = Join-Path $chromeDriverDir "chromedriver_win32.zip"
    Write-Output "Zip file download path: $chromeDriverZipFileLocation"

    Invoke-WebRequest -Uri $chromeDriverZipLink -OutFile $chromeDriverZipFileLocation

    $unzipLocation = Join-Path $thisScriptRoot $chromeDriverRelativeDir  # Add this line
  
    Expand-Archive -Path $chromeDriverZipFileLocation -DestinationPath $unzipLocation -Force

    $sourceFolderPath = "$unzipLocation\chromedriver-win32\"
    $destinationFolderPath = $unzipLocation

    if (Test-Path $sourceFolderPath -PathType Container) {
        if (-not (Test-Path $destinationFolderPath -PathType Container)) {
            New-Item -Path $destinationFolderPath -ItemType Directory
        }

        Get-ChildItem $sourceFolderPath | ForEach-Object {
            $destinationFilePath = Join-Path $destinationFolderPath $_.Name
            Move-Item $_.FullName $destinationFilePath -Force
            Write-Host "Moved $($_.Name) to $destinationFilePath"
        }
    } else {
        Write-Host "Source folder not found: $sourceFolderPath"
    }

    $chromeDriverFileVersion = (& $chromeDriverFileLocation --version)
    Write-Output "ChromeDriver updated to version $chromeDriverFileVersion"



} else {
    Write-Host "Hurray!!! Chrome driver is up to date."

    
}
