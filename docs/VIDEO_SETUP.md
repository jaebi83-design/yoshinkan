# Video Setup Guide

## Video Directory Structure
```
Yoshinkan/videos/
├── attacks/              # Organized by attack type (optional)
├── techniques/           # Organized by technique (optional)
├── combinations/         # All video files
│   ├── SUIGETSU_ZUKI_IKKAJO_OSAE_ICHI.mp4
│   ├── SUIGETSU_ZUKI_IKKAJO_OSAE_NI.mp4
│   └── ... (more videos)
```

## Video Naming Convention
**Pattern**: `{ATTACK}_{TECHNIQUE}_{ENERGY}.mp4`

### Attack Names (UPPERCASE with underscores)
1. KATA_MOCHI
2. KATATE_AYAGYAKU_MOCHI
3. KATATE_AYAJUN_MOCHI
4. KATATE_MOCHI
5. MUNE_MOCHI
6. RYOKATA_MOCHI
7. RYOSODE_MOCHI
8. RYOTE_MOCHI
9. SHOMEN_UCHI
10. SODE_MOCHI
11. SUIGETSU_ZUKI
12. USHIRO_WAZA_ERI_MOCHI
13. USHIRO_WAZA_KATATE_ERI_MOCHI
14. USHIRO_WAZA_RYOHIJI_MOCHI
15. USHIRO_WAZA_RYOTE_MOCHI
16. YOKOMEN_UCHI

### Technique Names (UPPERCASE with underscores)
1. HIJI_SHIME
2. IKKAJO_OSAE
3. IRIMI_TSUKI
4. NIKAJO_OSAE
5. SANKAJO_OSAE
6. YONKAJO_OSAE
7. IRIMI_NAGE
8. KOTEGAESHI_OSAE
9. HIJI_ATE
10. SHIHO_NAGE
11. SOKUMEN_IRIMI_NAGE
12. TENCHI_NAGE

### Energy Names (UPPERCASE)
1. ICHI
2. NI

## Examples
- `KATA_MOCHI_HIJI_SHIME_ICHI.mp4`
- `SHOMEN_UCHI_IRIMI_NAGE_NI.mp4`
- `RYOTE_MOCHI_SHIHO_NAGE_ICHI.mp4`

## Video Specifications
- **Format**: MP4 (.mp4)
- **Codec**: H.264 or H.265 (HEVC)
- **Resolution**: 1080p (1920x1080) or higher recommended
- **Frame Rate**: 30fps or 60fps
- **Bitrate**: 5-10 Mbps recommended
- **Aspect Ratio**: 16:9

## Adding Videos to the App

### For Development/Testing:
1. Place video files in `Yoshinkan/videos/combinations/` directory
2. Videos should be available on device storage
3. Update video path in VideoPlaybackScreen if needed

### For Production:
1. Package videos in app resources or external storage
2. Consider data size - use video compression to reduce APK size
3. Implement lazy loading for large video libraries

## Updating Videos Without Rebuilding APK
To allow video updates without rebuilding:
1. Store videos in app's external files directory
2. Implement download manager for video updates
3. Create admin panel to upload new videos
4. Sync video metadata with database

## Video Access Path
Currently, videos are accessed from: `/videos/combinations/`

To modify video path:
- Update path in `VideoPlaybackScreen.kt` line with `setVideoURI`
- Use `Uri.fromFile()` for local file system
- Use content providers for secure access
