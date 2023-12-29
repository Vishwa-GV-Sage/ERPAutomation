
Set-ExecutionPolicy RemoteSigned
[string]$ChromeDir="C:\Program Files\Google\Chrome\Application\chrome.exe"





if (-Not (Test-Path $ChromeDir -PathType Leaf)) {
  Write-Output "Chrome not found in '$ChromeDir'. Please, install chrome or specify custom chrome location with -ChromeDir argument."
  Exit 1
}
$filePath = "C:\Users\Public\latestChromedriver.txt"

$thisScriptRoot = if ($PSScriptRoot -eq "") { "." } else { $PSScriptRoot }
$chromeDriverDir = $thisScriptRoot
$chromeDriverFileLocation = $(Join-Path $chromeDriverDir "chromedriver.exe")
$chromeVersion = [System.Diagnostics.FileVersionInfo]::GetVersionInfo($ChromeDir).FileVersion
$chromeMajorVersion = $chromeVersion.split(".")[0]

if (-Not (Test-Path $chromeDriverDir -PathType Container)) {
  New-Item -ItemType directory -Path $chromeDriverDir
}

# Check if the file exists
if (Test-Path $filePath) {
    $chromDriverLatestVersion = Get-Content $filePath
    Write-Host "ChromeDriver latest version: $chromDriverLatestVersion"

#https://edgedl.me.gvt1.com/edgedl/chrome/chrome-for-testing/116.0.5845.96/win32/chromedriver-win32.zip

$chromeDriverZipLink = "https://edgedl.me.gvt1.com/edgedl/chrome/chrome-for-testing/" + $chromDriverLatestVersion + "/win32/chromedriver-win32.zip"
  Write-Output "Will download $chromeDriverZipLink"

  $chromeDriverZipFileLocation = $(Join-Path $chromeDriverDir "chromedriver_win32.zip")
   Write-Output $chromeDriverZipFileLocation
  
  Invoke-WebRequest -Uri $chromeDriverZipLink -OutFile $chromeDriverZipFileLocation

  $unzipLocation=$(Join-Path $thisScriptRoot $chromeDriverRelativeDir)
  
  Expand-Archive $chromeDriverZipFileLocation -DestinationPath $unzipLocation -Force

  $sourceFolderPath = "$unzipLocation\chromedriver-win32\"
  $destinationFolderPath = $unzipLocation

# Check if the source folder exists
if (Test-Path $sourceFolderPath -PathType Container) {
    # Check if the destination folder exists, create if not
    if (-not (Test-Path $destinationFolderPath -PathType Container)) {
        New-Item -Path $destinationFolderPath -ItemType Directory
    }

    # Move all files from source to destination
    Get-ChildItem $sourceFolderPath | ForEach-Object {
        $destinationFilePath = Join-Path $destinationFolderPath $_.Name
        Move-Item $_.FullName $destinationFilePath -Force
        Write-Host "Moved $($_.Name) to $destinationFilePath"
    }
} else {
    Write-Host "Source folder not found: $sourceFolderPath"
}


  $chromeDriverFileVersion = (& $chromeDriverFileLocation --version)
  Write-Output "chromedriver updated to version $chromeDriverFileVersion"
} else {
    Write-Host "File not found: $filePath"
}

