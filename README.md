# Yoshinkan

An interactive Aikido video representation system for students to explore techniques through attack, technique, and energy flow combinations.

## Overview

Yoshinkan is an Android application that allows Aikido students to:
1. Select an attack type
2. Choose a response technique
3. Select an energy flow (Ichi or Ni)
4. View a video demonstration of how to execute that technique in response to the selected attack

## Project Status

✅ **Complete Project Structure**
✅ **Kotlin Data Classes & Room Entities**
✅ **Jetpack Compose UI with Functional Selection Screens**
✅ **Dropdown Menus with Scrollable Lists**
✅ **Correct Attack Data** (16 types from attacks.json)
✅ **Correct Technique Data** (12 types from techniques.json)
✅ **Working Selection Logic** - Play button enables when all three selections are made
✅ **Tested & Verified** - App runs on emulator without crashes
⏳ **Video Playback Framework** - Next phase
⏳ **MVVM/Repository Architecture** - Next phase
⏳ **Database Integration** - Next phase

## Technology Stack

- **Language**: Kotlin
- **Framework**: Android SDK
- **UI Framework**: Jetpack Compose
- **Database**: Room (SQLite)
- **Video Playback**: Android MediaPlayer
- **Architecture**: MVVM (Model-View-ViewModel)
- **Target Device**: Google Pixel
- **Minimum Android**: API 33 (Android 13)
- **Target Android**: API 35 (Android 16)

## Project Structure

```
Yoshinkan/
├── CLAUDE.md                 # Project context & guidelines
├── README.md                 # This file
├── app/
│   ├── src/
│   │   ├── model/           # Data models
│   │   │   ├── Attack.kt
│   │   │   ├── Technique.kt
│   │   │   ├── EnergyFlow.kt
│   │   │   └── DemonstrationSelection.kt
│   │   ├── database/        # Room database & DAOs
│   │   │   ├── YoshinkanDatabase.kt
│   │   │   ├── AttackDao.kt
│   │   │   ├── TechniqueDao.kt
│   │   │   └── EnergyFlowDao.kt
│   │   ├── repository/      # Data access layer
│   │   │   └── YoshinkanRepository.kt
│   │   ├── viewmodel/       # MVVM ViewModels
│   │   │   └── YoshinkanViewModel.kt
│   │   ├── ui/
│   │   │   ├── screen/      # Compose screens
│   │   │   │   ├── SelectionScreen.kt
│   │   │   │   └── VideoPlaybackScreen.kt
│   │   │   └── theme/       # Material Design 3 theme
│   │   │       └── Theme.kt
│   │   ├── MainActivity.kt   # Entry point
│   │   └── AndroidManifest.xml
│   └── build.gradle         # Build configuration
├── data/                    # Data definitions
│   ├── attacks.json
│   ├── techniques.json
│   ├── energy.json
│   └── video_mapping.json
├── videos/                  # Video library (separate management)
│   └── combinations/        # Videos named as {ATTACK}_{TECHNIQUE}_{ENERGY}.mp4
└── docs/                    # Documentation
    ├── IMPLEMENTATION_GUIDE.md
    ├── DATABASE_SETUP.md
    └── VIDEO_SETUP.md
```

## Data Categories

### Attacks (16 types)
Kata Mochi, Katate Ayagyaku Mochi, Katate Ayajun Mochi, Katate Mochi, Mune Mochi, RyoKata Mochi, RyoSode Mochi, Ryote Mochi, Shomen Uchi, Sode Mochi, Suigetsu Zuki, Ushiro Waza Eri Mochi, Ushiro Waza Katate Eri Mochi, Ushiro Waza RyoHiji Mochi, Ushiro Waza Ryote Mochi, Yokomen Uchi

### Techniques (12 types)
Hiji Shime, Ikkajo Osae, Irimi Tsuki, Nikajo Osae, Sankajo Osae, Yonkajo Osae, Irimi Nage, Kotegaeshi Osae, Hiji Ate, Shiho Nage, Sokumen Irimi Nage, TenChi Nage

### Energy Flows (2 types)
Ichi, Ni

## Video Naming Convention

Videos follow this pattern: `{ATTACK}_{TECHNIQUE}_{ENERGY}.mp4`

**Example**: `SUIGETSU_ZUKI_IKKAJO_OSAE_ICHI.mp4`

The app automatically converts user selections to this filename format.

## Getting Started

### Prerequisites
- Android Studio (latest)
- JDK 17+
- Android SDK 35
- Google Pixel device or emulator

### Setup Steps

1. **Open in Android Studio**
   - Clone/import the project
   - Let Gradle sync dependencies

2. **Configure Videos**
   - Add your video files to `videos/combinations/` directory
   - Ensure correct naming: `{ATTACK}_{TECHNIQUE}_{ENERGY}.mp4`
   - See `docs/VIDEO_SETUP.md` for details

3. **Initialize Database**
   - The app auto-populates database on first launch
   - Or manually add data via MainActivity
   - See `docs/DATABASE_SETUP.md` for details

4. **Build & Run**
   - Click "Run" in Android Studio
   - Select Google Pixel device
   - App will launch on target device

## How It Works

1. **Selection Screen**: User selects attack, technique, and energy flow from dropdown menus
2. **Validation**: Play button enables only when all three selections are made
3. **Video Lookup**: App generates filename from selections
4. **Playback Screen**: Video loads and plays with controls (play, pause, rewind, forward)
5. **Return**: User can return to selection screen to try different combinations

## Documentation

- **IMPLEMENTATION_GUIDE.md**: Detailed architecture and component overview
- **DATABASE_SETUP.md**: Database schema, initialization, and queries
- **VIDEO_SETUP.md**: Video file organization and naming conventions
- **CLAUDE.md**: Project context and development guidelines

## Key Features

✅ Simple, intuitive UI for attack/technique/energy selection
✅ Dropdown menus for easy selection on mobile
✅ Video playback with standard controls
✅ MVVM architecture for clean code organization
✅ Room database for persistent data storage
✅ Jetpack Compose for modern Android UI
✅ Modular, extensible design

## Development Notes

- All database operations use Kotlin Coroutines (non-blocking)
- UI updates use StateFlow for reactive data binding
- Architecture follows MVVM best practices
- Code is Kotlin-only (no Java)
- Compose UI is touch-friendly for mobile

## Future Enhancements

- Video preview thumbnails
- Playback speed controls
- Favorites/bookmarking
- Admin panel for video updates
- Search and filtering
- Multiple camera angles
- Technique difficulty ratings
- Progress tracking

## Support & Troubleshooting

See documentation files for:
- Video setup issues
- Database problems
- Build errors
- UI/UX questions

---

**Created**: 2026-02-13
**Last Updated**: 2026-02-14
**Target Device**: Google Pixel
**Minimum Android**: 13 (API 33)
**Current Build Status**: ✅ Compiling and running successfully on emulator
**Current Features**: Attack/Technique/Energy selection with correct data from JSON files
