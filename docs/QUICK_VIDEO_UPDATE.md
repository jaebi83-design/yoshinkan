# Quick Video Update Reference

## The Simple Process

### Step 1: Add Videos to Your Directory
```
/videos/combinations/
├── KATA_MOCHI_HIJI_SHIME_ICHI.mp4
├── KATA_MOCHI_HIJI_SHIME_NI.mp4
├── SUIGETSU_ZUKI_IKKAJO_OSAE_ICHI.mp4
└── ... (more videos)
```

### Step 2: Generate Manifest (Kotlin Code)
```kotlin
// In MainActivity.kt or any initialization code
import com.yoshinkan.utility.VideoScanner
import com.yoshinkan.utility.ManifestManager

val directoryPath = "/path/to/your/videos/combinations"
val manifest = VideoScanner.scanVideoDirectory(directoryPath)
ManifestManager.saveManifest(this, manifest)
```

### Step 3: Deploy to Device
The manifest is automatically saved to the app's internal storage:
```
/data/data/com.yoshinkan/files/video_manifest.json
```

### Step 4: Done!
- App reads manifest on startup
- All videos are now available
- User can select and view them

---

## When You Have New/Updated Videos

1. **Add files** to `/videos/combinations/` directory
2. **Run Step 2** again (regenerate manifest)
3. **Restart app**
4. **New videos available!**

---

## File Naming MUST Follow This Pattern

```
{ATTACK}_{TECHNIQUE}_{ENERGY}.mp4
```

### Valid Examples
✓ `KATA_MOCHI_HIJI_SHIME_ICHI.mp4`
✓ `SUIGETSU_ZUKI_IKKAJO_OSAE_NI.mp4`
✓ `RYOTE_MOCHI_SHIHO_NAGE_ICHI.mp4`

### Invalid Examples
✗ `kata_mochi_hiji_shime_ichi.mp4` (lowercase)
✗ `KATA-MOCHI_HIJI-SHIME_ICHI.mp4` (hyphens)
✗ `KATA MOCHI HIJI SHIME ICHI.mp4` (spaces)

---

## The Three Files You Need

1. **VideoScanner.kt** - Reads directory and parses filenames
2. **ManifestManager.kt** - Saves/loads the manifest JSON
3. **VideoLibraryManager.kt** - High-level operations (optional)

---

## What Gets Stored

A JSON manifest like:
```json
{
  "videos": [
    {
      "fileName": "KATA_MOCHI_HIJI_SHIME_ICHI.mp4",
      "attackId": 1,
      "techniqueId": 1,
      "energyId": 1,
      "displayName": "KATA_MOCHI - HIJI_SHIME - ICHI"
    }
  ]
}
```

This tells the app:
- Which videos exist
- Which attack/technique/energy combination they match
- How to display them

---

## That's It!

No rebuilding the app. No recompiling. Just:
1. Add videos
2. Regenerate manifest
3. Deploy
4. Restart app

Done.
