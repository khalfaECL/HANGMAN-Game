$ErrorActionPreference = 'Stop'

$root = Split-Path -Parent $MyInvocation.MyCommand.Path | Split-Path -Parent
Set-Location $root

if (-not $env:JAVA_HOME) {
    throw "JAVA_HOME is not set. Point it to a JDK 17+ before running this script."
}

$mvn = "mvn"
if (-not (Get-Command mvn -ErrorAction SilentlyContinue)) {
    $mvn = "C:\\Users\\Lenovo\\AppData\\Local\\Programs\\Apache\\apache-maven-3.9.9\\bin\\mvn.cmd"
}

& $mvn clean package

$jpackage = Join-Path $env:JAVA_HOME "bin\\jpackage.exe"
if (-not (Test-Path $jpackage)) {
    throw "jpackage.exe not found in JAVA_HOME. Install JDK 17+ and set JAVA_HOME."
}

$output = "dist"
New-Item -ItemType Directory -Force -Path $output | Out-Null

& $jpackage `
  --type exe `
  --name "MotsPendule" `
  --app-version "1.0.0" `
  --input "target" `
  --main-jar "mots-pendule-1.0.0.jar" `
  --main-class "com.mots.pendule.app.Main" `
  --arguments "--gui" `
  --win-menu `
  --win-shortcut `
  --win-menu-group "MotsPendule" `
  --dest $output

Write-Host "Installer created in $output"
