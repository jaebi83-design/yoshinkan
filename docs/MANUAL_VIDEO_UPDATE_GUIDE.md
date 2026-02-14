# Manual Video Update Guide

## Overview

Yoshinkan uses a **Video Manifest** system for managing videos. This allows you to:
- Scan your video directory and generate a manifest
- Update videos without rebuilding the app
- Track available videos efficiently
- Add/remove/replace videos as needed

## Update Workflow

```
Your Video Directory (with new/updated videos)
           ↓
  Run Video Scanner
           ↓
  Generate video_manifest.json
           ↓
  Deploy manifest to device/app
           ↓
  App reads manifest on startup
           ↓
  Videos are now available
```

## Components

### VideoScanner
- **File**: `app/src/utility/VideoScanner.kt`
- **Purpose**: Scans a directory for MP4 files
- **Parses**: Filename format to extract attack, technique, energy IDs
- **Generates**: VideoManifest with metadata

### ManifestManager
- **File**: `app/src/utility/ManifestManager.kt`
- **Purpose**: Manages manifest file operations
- **Functions**:
  - Load manifest from app storage
  - Save manifest to app storage
  - Export manifest (backup)
  - Import manifest (restore)

### VideoLibraryManager
- **File**: `app/src/utility/VideoLibraryManager.kt`
- **Purpose**: High-level video library operations
- **Functions**:
  - Initialize video library on app startup
  - Update video library with new videos
  - Check if video exists
  - Get available videos list

## Manual Update Process

### Step 1: Prepare Video Files
1. Organize all videos in one directory (e.g., `/videos/combinations/`)
2. Ensure all files follow naming convention: `{ATTACK}_{TECHNIQUE}_{ENERGY}.mp4`
3. Valid filenames:
   - `KATA_MOCHI_HIJI_SHIME_ICHI.mp4` ✓
   - `SUIGETSU_ZUKI_IKKAJO_OSAE_NI.mp4` ✓
   - `Invalid_name.mp4` ✗

### Step 2: Generate Manifest (Kotlin Code)
Use this code snippet to generate the manifest:

```kotlin
import com.yoshinkan.utility.VideoScanner
import com.yoshinkan.utility.ManifestManager
import com.yoshinkan.model.VideoManifest
import kotlinx.serialization.json.Json

// In your code (e.g., MainActivity or a utility)
val videoDirectoryPath = "/path/to/videos/combinations"
val manifest = VideoScanner.scanVideoDirectory(videoDirectoryPath)

// Save manifest
val context = this // your Android context
ManifestManager.saveManifest(context, manifest)
```

### Step 3: Deploy Updated Manifest
After generating the manifest, it's stored in the app's internal storage.

**For Development:**
1. Run app with scanner code
2. Manifest is automatically saved
3. App reads and uses it

**For Production:**
1. Generate manifest on development machine
2. Transfer manifest to device via adb
3. App loads manifest on next startup

```bash
# Push manifest to device
adb push video_manifest.json /data/data/com.yoshinkan/files/

# Or copy via Android Studio Device File Explorer
```

### Step 4: Verify on App
1. Launch app
2. Check Selection Screen - all videos should be available
3. Select attack/technique/energy combination
4. Verify video plays correctly

## Adding New Videos

### Simple Update
1. Add new video files to directory (correct naming)
2. Run VideoScanner to generate new manifest
3. Save manifest to app
4. App automatically uses new videos on next launch

### Example
```
Directory before:
  KATA_MOCHI_HIJI_SHIME_ICHI.mp4
  SHOMEN_UCHI_IRIMI_NAGE_NI.mp4

Add new video:
  SUIGETSU_ZUKI_IKKAJO_OSAE_ICHI.mp4

Run scanner → manifest.json updated
Deploy to device → done!
```

## Replacing Existing Videos

1. Replace video file in directory (same name)
2. Regenerate manifest (filename stays the same)
3. Deploy manifest to app
4. App uses new video automatically

## Removing Videos

1. Delete video files from directory
2. Regenerate manifest
3. Deploy manifest to app
4. Deleted videos no longer appear in manifest

## Manifest File Format

The generated `video_manifest.json` looks like:

```json
{
  "videos": [
    {
      "fileName": "KATA_MOCHI_HIJI_SHIME_ICHI.mp4",
      "attackId": 1,
      "techniqueId": 1,
      "energyId": 1,
      "displayName": "KATA_MOCHI - HIJI_SHIME - ICHI"
    },
    {
      "fileName": "SUIGETSU_ZUKI_IKKAJO_OSAE_ICHI.mp4",
      "attackId": 11,
      "techniqueId": 2,
      "energyId": 1,
      "displayName": "SUIGETSU_ZUKI - IKKAJO_OSAE - ICHI"
    }
  ]
}
```

## Backup & Restore

### Export Manifest (Backup)
```kotlin
ManifestManager.exportManifest(context, "/path/to/backup/video_manifest.json")
```

### Import Manifest (Restore)
```kotlin
ManifestManager.importManifest(context, "/path/to/backup/video_manifest.json")
```

## Troubleshooting

### Videos Not Found After Update
- Check manifest was properly saved
- Verify video directory path is correct
- Ensure all video files have correct naming

### Invalid Filenames Not Parsed
- VideoScanner ignores invalid filenames silently
- Check naming: `{ATTACK}_{TECHNIQUE}_{ENERGY}.mp4`
- Verify attack/technique/energy are in the mapping

### Manifest Won't Load
- Check file permissions
- Verify JSON formatting
- Look at logcat for error messages

## Implementation in MainActivity

Add this to `MainActivity.kt`:

```kotlin
import com.yoshinkan.utility.VideoLibraryManager
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ... existing code ...

        // Initialize video library on app startup
        lifecycleScope.launch {
            VideoLibraryManager.initializeVideoLibrary(
                this@MainActivity,
                repository,
                "/storage/emulated/0/Videos/yoshinkan/" // your video directory
            )
        }
    }
}
```

## Typical Use Cases

### Initial Setup
1. Create `/videos/combinations/` directory
2. Add all video files (16 attacks × 12 techniques × 2 energy = 384 potential videos)
3. Generate manifest
4. Deploy to device
5. Done!

### Weekly Updates
1. Add new videos to directory
2. Run VideoScanner to generate new manifest
3. Copy manifest to device
4. Restart app

### Fix Incorrect Video
1. Replace video file (same name)
2. No need to regenerate manifest
3. Restart app
4. New video is loaded

## Performance Notes

- Manifest is loaded once on app startup
- Manifest stays in memory during app session
- Video directory is NOT scanned at runtime
- Updating manifest requires manual trigger
- No impact on app performance after initialization

## Storage Location

Manifest is stored in:
```
/data/data/com.yoshinkan/files/video_manifest.json
```

You can view it using:
```bash
adb shell cat /data/data/com.yoshinkan/files/video_manifest.json
```

---

**Manual Video Update System Ready!**

Update your videos anytime without rebuilding the app.
