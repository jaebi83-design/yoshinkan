# Yoshinkan Implementation Guide

## Project Overview
Yoshinkan is an Android application that allows Aikido students to select an attack, technique, and energy flow to view video demonstrations of how to respond.

## Architecture
The application follows the **MVVM (Model-View-ViewModel)** architecture pattern:

- **Model**: Data classes (Attack, Technique, EnergyFlow, DemonstrationSelection)
- **View**: Jetpack Compose UI screens (SelectionScreen, VideoPlaybackScreen)
- **ViewModel**: YoshinkanViewModel manages state and data flow

## Key Components

### 1. Data Models
- **Attack.kt**: Represents an Aikido attack type
- **Technique.kt**: Represents an Aikido technique response
- **EnergyFlow.kt**: Represents energy flow dynamics (Ichi, Ni)
- **DemonstrationSelection.kt**: Holds user's current selection and generates video filenames

### 2. Database Layer
- **YoshinkanDatabase.kt**: Room database singleton
- **AttackDao.kt**: Data access object for attacks
- **TechniqueDao.kt**: Data access object for techniques
- **EnergyFlowDao.kt**: Data access object for energy flows

### 3. Repository Pattern
- **YoshinkanRepository.kt**: Provides unified interface for data access

### 4. UI Layer
- **SelectionScreen.kt**: Main selection interface with dropdowns for:
  - Attack selection
  - Technique selection
  - Energy flow selection
  - Play button (enabled only when all selections are made)
  - Reset button

- **VideoPlaybackScreen.kt**: Video playback interface with controls:
  - Play/Pause
  - Rewind/Forward
  - Back to selection

### 5. Theme
- **Theme.kt**: Material Design 3 theming for light and dark modes

## Video Naming Convention
Videos follow the naming pattern: `{ATTACK}_{TECHNIQUE}_{ENERGY}.mp4`

Example: `SUIGETSU_ZUKI_IKKAJO_OSAE_ICHI.mp4`

The `DemonstrationSelection.getVideoFileName()` function automatically converts user selections to this format.

## Setup Instructions

### Prerequisites
- Android Studio (latest version)
- Java Development Kit (JDK) 17 or higher
- Android SDK 35 (Android 16)
- Minimum SDK: Android 13 (API 33)

### Initial Setup
1. Clone or create the project in Android Studio
2. Add your video files to the `/videos/` directory following the naming convention
3. Populate the Room database with attack, technique, and energy data
4. Build and run the project

### Database Initialization
The database can be populated in two ways:
1. **Pre-populate with JSON files**: Load from `/data/` JSON files on first app launch
2. **Manual insertion**: Insert data programmatically in MainActivity

## Data Flow
1. User launches app → MainActivity loads ViewModel
2. ViewModel initializes database and loads all attacks, techniques, and energy flows
3. SelectionScreen displays dropdown menus populated from database
4. User selects Attack → ViewModel updates selection state
5. User selects Technique → ViewModel updates selection state
6. User selects Energy Flow → ViewModel updates selection state
7. User clicks "Play" → ViewModel generates video filename
8. App navigates to VideoPlaybackScreen with the generated filename
9. VideoView loads and plays the video from `/videos/` directory

## Future Enhancements
- Implement actual video loading from file system
- Add video preview thumbnails
- Implement playback speed controls
- Add favorites/bookmarking system
- Create admin panel for updating videos without rebuilding APK
- Add search and filtering capabilities
- Implement video rotation and different view angles

## Dependencies
- AndroidX Core
- AndroidX Lifecycle
- AndroidX Room
- AndroidX Compose
- Kotlin Coroutines
- Material Design 3

## Testing
Unit tests can be added for:
- ViewModel state management
- Repository data operations
- Video filename generation
- Database operations

Instrumentation tests can verify:
- UI navigation flow
- Database persistence
- Video file loading

## Troubleshooting
- **No videos playing**: Verify video files are in correct `/videos/` directory with correct naming
- **Database not populating**: Ensure JSON files are properly formatted and loaded on app startup
- **UI not responsive**: Check ViewModel coroutine scope and ensure proper state flow usage
