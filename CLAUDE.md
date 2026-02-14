# Project Context for Claude

## Overview
Yoshinkan is an interactive Aikido video representation system. It provides a user interface where Aikido students can input attack types, techniques, and energy flows to generate corresponding video demonstrations.

## Project Information
- **Location**: C:\Users\Jim\Documents\AI Programming\Yoshinkan
- **Project Name**: Yoshinkan
- **Type**: Interactive Aikido Training Application (Android)
- **Platform**: Android Mobile Application
- **Created**: 2026-02-13

## Core Features
The application allows users to:
1. **Select/Input an Attack** - Choose or specify an attack type (e.g., grab, strike, etc.)
2. **Select/Input a Technique** - Choose an Aikido technique to apply
3. **Select/Input Energy Flow** - Define the energy dynamics of the response
4. **Generate Demonstration** - Receive a video showing the resulting technique execution

## Development Guidelines
- Follow best practices for clean, maintainable code
- Prioritize security and avoid common vulnerabilities
- Keep implementations simple and focused on requirements
- Use descriptive variable and function names
- Design the UI with Aikido practitioners in mind

## Project Structure
```
Yoshinkan/
├── CLAUDE.md                 # Project context
├── README.md                 # Project overview
├── app/                      # Android application source
│   ├── src/                  # Source code
│   ├── res/                  # Resources (layouts, strings)
│   └── build.gradle          # Build configuration
├── data/                     # Attack and technique definitions (JSON/database)
├── videos/                   # Separate video content library
│   ├── attacks/              # Videos organized by attack type
│   ├── techniques/           # Videos organized by technique
│   └── combinations/         # Videos for specific attack-technique-energy combinations
└── docs/                     # Technical documentation
```

## Technologies Used
- **Language**: Kotlin
- **Framework**: Android SDK
- **Target Device**: Google Pixel
- **Minimum Android Version**: Android 13 (API level 33)
- **Target Android Version**: Android 16 (current)
- **UI**: Android Jetpack Compose
- **Video Playback**: Android MediaPlayer
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Room (SQLite) for local data storage

## Key Components
1. **Selection UI** - Spinners/dropdown menus for attack, technique, and energy flow on mobile screens
2. **Video Playback** - Efficient video display on mobile devices
3. **Data Layer** - Local database of attacks, techniques, and their video mappings
4. **Video Library** - Stored video files organized by attack-technique-energy combinations
5. **Navigation** - Mobile-optimized navigation flow for Android

## Video Management
- Videos stored in separate `/videos/` directory, independent from APK
- Videos can be added, modified, or deleted without rebuilding the app
- App loads video references from data/metadata files
- Supports offline playback of downloaded videos
- Video delivery: TBD (embedded in app resources, downloaded on-demand, or streamed)

## Notes for Claude
- Android-first design: optimize for mobile screens and touch interaction
- Videos decoupled from app code for independent updates
- Consider offline functionality for video access
- Focus on performance and battery efficiency on mobile devices
- Ensure responsive UI on various screen sizes

## Additional Context
Aikido knowledge: Oshicon leverages Aikido principles where techniques are responses to attacks with specific energy dynamics. The system should be educational and accessible to students of varying levels.
