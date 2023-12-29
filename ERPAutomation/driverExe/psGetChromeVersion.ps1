Set-ExecutionPolicy -Scope CurrentUser remotesigned
cls
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
  write-host $chromeDriverCurrentVersion
  if (-Not $chromeDriverFileVersionHasMatch) {
    Exit
  }
}
else {
  # if chromedriver.exe not found, will download it
  $chromeDriverCurrentVersion = ''
}



Write-Output "chrome version:       $chromeVersion"

# Write the Chrome version to a file named 'chromeversion.txt' in the Public folder
$publicFolderPath = "C:\Users\Public"
$versionFilePath = Join-Path $publicFolderPath "chromeversion.txt"


# Delete the 'chromeversion.txt' file if it exists
if (Test-Path $versionFilePath -PathType Leaf) {
  Remove-Item -Path $versionFilePath -Force
}



$chromeDriverCurrentVersion | Out-File -FilePath $versionFilePath -Encoding utf8
Write-Output "Chrome version written to: $versionFilePath"