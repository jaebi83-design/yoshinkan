# Video Manifest Generator Tool

A simple command-line tool to scan your Aikido video directory and automatically generate a manifest file that the Yoshinkan app uses to track available videos.

## What It Does

1. **Scans** your video directory for all MP4 files
2. **Parses** each filename to extract attack, technique, and energy information
3. **Generates** a `video_manifest.json` file
4. **Saves** the manifest in your video directory

## Quick Start

### Step 1: Open Terminal/Command Prompt

On Windows:
- Press `Windows Key + R`
- Type `cmd` and press Enter
- This opens Command Prompt

### Step 2: Navigate to Tools Directory

In Command Prompt, type:
```
cd "C:\Users\Jim\Documents\AI Programming\Yoshinkan\tools"
```

Then press Enter.

### Step 3: Run the Generator

Type this command (replace with your actual video directory path):
```
generate_manifest.bat "C:\Users\Jim\claude-skills\video-decomposer\data\Tachi Waza - Clean"
```

Then press Enter.

### What Happens

The tool will:
1. Scan your video directory
2. Find all `.mp4` files
3. Parse each filename
4. Generate `video_manifest.json` in your video directory
5. Show you a summary of videos found

## Example Output

```
Generating video manifest...
Source directory: C:\Users\Jim\claude-skills\video-decomposer\data\Tachi Waza - Clean

Scanning directory: C:\Users\Jim\claude-skills\video-decomposer\data\Tachi Waza - Clean
Found 48 video files
Manifest saved to: C:\Users\Jim\claude-skills\video-decomposer\data\Tachi Waza - Clean\video_manifest.json

Video Summary:
  - KATA_MOCHI_HIJI_SHIME_ICHI.mp4
  - KATA_MOCHI_HIJI_SHIME_NI.mp4
  - KATATE_MOCHI_IKKAJO_OSAE_ICHI.mp4
  - ... (more videos)
```

## File Naming Requirements

Your videos MUST follow this naming pattern:

```
{ATTACK}_{TECHNIQUE}_{ENERGY}.mp4
```

### Valid Examples
✓ `KATA_MOCHI_HIJI_SHIME_ICHI.mp4`
✓ `SUIGETSU_ZUKI_IKKAJO_OSAE_NI.mp4`
✓ `RYOTE_MOCHI_SHIHO_NAGE_ICHI.mp4`

### Invalid Examples
✗ `kata_mochi_hiji_shime_ichi.mp4` (lowercase - will be rejected)
✗ `KATA MOCHI HIJI SHIME ICHI.mp4` (spaces instead of underscores)
✗ `kata-mochi-hiji-shime-ichi.mp4` (hyphens instead of underscores)

## Manifest File Format

After running the tool, you'll have a `video_manifest.json` file that looks like:

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

## Using the Manifest in Yoshinkan App

Once you have the `video_manifest.json`:

1. The Yoshinkan app will read this manifest when it starts
2. All videos listed in the manifest become available
3. Users can select attack/technique/energy combinations
4. The app plays the corresponding video

## Updating Videos Later

When you add, remove, or replace videos:

1. Modify your video files in the directory
2. **Run the generator again** to update the manifest
3. Restart the Yoshinkan app
4. New/updated videos will be available

## Troubleshooting

### "Command not recognized" error

**Problem**: Batch script won't run

**Solution**:
- Make sure you're in the correct directory: `C:\Users\Jim\Documents\AI Programming\Yoshinkan\tools`
- Type `dir` to see files - you should see `generate_manifest.bat`

### "Kotlin not found" error

**Problem**: Kotlin compiler not installed

**Solution**:
- The tool requires Kotlin to be installed
- Ask for help setting up Kotlin, or use an alternative method

### No videos found

**Problem**: Tool found 0 videos

**Solution**:
- Check that video files exist in the directory
- Verify files are named correctly: `{ATTACK}_{TECHNIQUE}_{ENERGY}.mp4`
- Make sure they're MP4 files (not mkv, avi, etc.)
- Check that attack/technique names match the allowed list

### Invalid filenames warning

**Problem**: Some videos not parsed

**Solution**:
- Check filename format carefully
- Make sure attack/technique names are exactly as specified (case-sensitive, UPPERCASE)
- Use underscores (_) not spaces or hyphens

## Advanced Usage

### Using the Kotlin Script Directly

If the batch file doesn't work, you can run Kotlin directly:

```bash
kotlinc -script VideoManifestGenerator.kt "C:\path\to\videos"
```

### Running from Any Directory

You can run the tool from anywhere by providing the full path:

```bash
"C:\Users\Jim\Documents\AI Programming\Yoshinkan\tools\generate_manifest.bat" "C:\Users\Jim\claude-skills\video-decomposer\data\Tachi Waza - Clean"
```

## Files Included

- `VideoManifestGenerator.kt` - The main Kotlin tool (source code)
- `generate_manifest.bat` - Windows batch script (easy launcher)
- `README_TOOL.md` - This file (instructions)

## Summary

**Quick Process:**
1. Open Command Prompt
2. Navigate to: `C:\Users\Jim\Documents\AI Programming\Yoshinkan\tools`
3. Run: `generate_manifest.bat "path\to\your\videos"`
4. Done! Manifest is generated

That's it! No Android coding required.
