@echo off
REM ============================================================================
REM Yoshinkan Video Update - One Click Solution
REM ============================================================================
REM This script automatically:
REM   1. Navigates to the tools directory
REM   2. Runs the manifest generator
REM   3. Shows success/error message
REM   4. Closes after completion
REM ============================================================================

setlocal enabledelayedexpansion

REM Define the video directory path
set VIDEO_DIR=C:\Users\Jim\claude-skills\video-decomposer\data\Tachi Waza - Clean

REM Define the tools directory
set TOOLS_DIR=%~dp0

echo.
echo ============================================================================
echo Yoshinkan Video Manifest Generator
echo ============================================================================
echo.
echo Video Directory: %VIDEO_DIR%
echo Tools Directory: %TOOLS_DIR%
echo.

REM Check if video directory exists
if not exist "%VIDEO_DIR%" (
    echo ERROR: Video directory not found!
    echo.
    echo Expected location: %VIDEO_DIR%
    echo.
    echo Please check the path and try again.
    echo.
    timeout /t 5
    exit /b 1
)

echo Scanning videos and generating manifest...
echo.

REM Run the Python script
cd /d "%TOOLS_DIR%"
python generate_manifest.py "%VIDEO_DIR%"

REM Check if successful
if %errorlevel% equ 0 (
    echo.
    echo ============================================================================
    echo SUCCESS!
    echo ============================================================================
    echo.
    echo Manifest file created at:
    echo %VIDEO_DIR%\video_manifest.json
    echo.
    echo Your Yoshinkan app is ready to use these videos!
    echo.
    echo Next steps:
    echo   1. Open the Yoshinkan app in Android Studio
    echo   2. The app will automatically detect the new manifest
    echo   3. All videos are now available for selection
    echo.
    timeout /t 3
) else (
    echo.
    echo ============================================================================
    echo ERROR - MANIFEST GENERATION FAILED
    echo ============================================================================
    echo.
    echo Possible causes:
    echo   - Python is not installed or not in PATH
    echo   - Video directory path is incorrect
    echo   - Video files don't match naming convention
    echo.
    echo Naming convention: {ATTACK}_{TECHNIQUE}_{ENERGY}.mp4
    echo Example: KATA_MOCHI_HIJI_SHIME_ICHI.mp4
    echo.
    echo Please check your video files and try again.
    echo.
    timeout /t 5
)

exit /b %errorlevel%
