@echo off
REM Video Manifest Generator Script
REM Usage: generate_manifest.bat "path\to\video\directory"

if "%~1"=="" (
    echo.
    echo Usage: generate_manifest.bat "path\to\video\directory"
    echo.
    echo Example:
    echo   generate_manifest.bat "C:\Users\Jim\claude-skills\video-decomposer\data\Tachi Waza - Clean"
    echo.
    pause
    exit /b 1
)

echo Generating video manifest...
echo Source directory: %~1
echo.

REM Run the Python script
python generate_manifest.py "%~1"

if %errorlevel% equ 0 (
    echo.
    echo Success! Manifest file has been generated.
) else (
    echo.
    echo Error: Failed to generate manifest. Make sure Python is installed.
)

pause
