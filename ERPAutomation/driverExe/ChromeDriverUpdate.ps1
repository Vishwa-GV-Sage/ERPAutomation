Set-ExecutionPolicy RemoteSigned
#powershell -ExecutionPolicy ByPass -File .\ChromeDriverUpdate.ps1
[string]$ChromeDir="C:\Program Files\Google\Chrome\Application\chrome.exe"





if (-Not (Test-Path $ChromeDir -PathType Leaf)) {
  Write-Output "Chrome not found in '$ChromeDir'. Please, install chrome or specify custom chrome location with -ChromeDir argument."
  Exit 1
}

$thisScriptRoot = if ($PSScriptRoot -eq "") { "." } else { $PSScriptRoot }


$chromeDriverDir = $thisScriptRoot
$chromeDriverFileLocation = $(Join-Path $chromeDriverDir "chromedriver.exe")
$chromeVersion = [System.Diagnostics.FileVersionInfo]::GetVersionInfo($ChromeDir).FileVersion
$chromeMajorVersion = $chromeVersion.split(".")[0]

if (-Not (Test-Path $chromeDriverDir -PathType Container)) {
  New-Item -ItemType directory -Path $chromeDriverDir
}

if (Test-Path $chromeDriverFileLocation -PathType Leaf) {
  # get version of curent chromedriver.exe
  $chromeDriverFileVersion = (& $chromeDriverFileLocation --version)
  $chromeDriverFileVersionHasMatch = $chromeDriverFileVersion -match "ChromeDriver (\d+\.\d+\.\d+(\.\d+)?)"
  $chromeDriverCurrentVersion = $matches[1]

  if (-Not $chromeDriverFileVersionHasMatch) {
    Exit
  }
}
else {
  # if chromedriver.exe not found, will download it
  $chromeDriverCurrentVersion = ''
}


  $chromeDriverExpectedVersion = $chromeVersion
#  $chromeDriverVersionUrl = "https://chromedriver.storage.googleapis.com/LATEST_RELEASE_" + $chromeDriverExpectedVersion
  $chromeDriverVersionUrl = "https://edgedl.me.gvt1.com/edgedl/chrome/chrome-for-testing/" + $chromeDriverExpectedVersion
 # https://edgedl.me.gvt1.com/edgedl/chrome/chrome-for-testing/116.0.5845.96/win64/chromedriver-win64.zip
Write-Output "Will download $chromeDriverVersionUrl"
$chromeDriverLatestVersion = Invoke-RestMethod -Uri $chromeDriverVersionUrl

Write-Output "chrome version:       $chromeVersion"
Write-Output "chromedriver version: $chromeDriverCurrentVersion"
Write-Output "chromedriver latest:  $chromeDriverLatestVersion"

# will update chromedriver.exe if MAJOR.MINOR.PATCH
$needUpdateChromeDriver = $chromeDriverCurrentVersion -ne $chromeDriverLatestVersion
if ($needUpdateChromeDriver) {
  $chromeDriverZipLink = "https://chromedriver.storage.googleapis.com/" + $chromeDriverLatestVersion + "/chromedriver_win32.zip"
  Write-Output "Will download $chromeDriverZipLink"

  $chromeDriverZipFileLocation = $(Join-Path $chromeDriverDir "chromedriver_win32.zip")

  Invoke-WebRequest -Uri $chromeDriverZipLink -OutFile $chromeDriverZipFileLocation
  Expand-Archive $chromeDriverZipFileLocation -DestinationPath $(Join-Path $thisScriptRoot $chromeDriverRelativeDir) -Force
  Remove-Item -Path $chromeDriverZipFileLocation -Force
  $chromeDriverFileVersion = (& $chromeDriverFileLocation --version)
  Write-Output "chromedriver updated to version $chromeDriverFileVersion"
}
else {
  Write-Output "chromedriver is actual"
}