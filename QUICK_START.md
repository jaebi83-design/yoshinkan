# Yoshinkan - Quick Start Guide

## 🚀 Get Started in 60 Seconds

### Step 1: Create GitHub Repository (2 minutes)
1. Go to https://github.com/new
2. Name: `Yoshinkan`
3. Leave all initialization options unchecked
4. Click "Create repository"

### Step 2: Push Local Repository (1 minute)
Run these commands in PowerShell/Command Prompt:

```bash
cd "C:\Users\Jim\Documents\AI Programming\Yoshinkan"
git remote add origin https://github.com/YOUR_USERNAME/Yoshinkan.git
git branch -M main
git push -u origin main
```

**Replace `YOUR_USERNAME` with your GitHub username**

### Step 3: Verify (1 minute)
1. Go to https://github.com/YOUR_USERNAME/Yoshinkan
2. Confirm all files are there
3. ✅ Done!

---

## 📱 Run the App

### In Android Studio
1. Open the Yoshinkan project
2. Select "Medium Phone API 36.1" from device dropdown
3. Click "Run" (or press Shift+F10)
4. App launches on emulator

### Expected Result
- Selection screen with three dropdowns
- Choose: Yokomen Uchi → Ikkajo Osae → Ichi
- Display shows: "Attack: Yokomen Uchi | Technique: Ikkajo Osae | Energy: Ichi"
- Play button enabled

---

## 📚 Documentation Quick Links

| Document | Purpose |
|----------|---------|
| [README.md](README.md) | Project overview |
| [CLAUDE.md](CLAUDE.md) | Development context |
| [PROJECT_SUMMARY.md](PROJECT_SUMMARY.md) | Detailed completion status |
| [DEPLOYMENT_CHECKLIST.md](DEPLOYMENT_CHECKLIST.md) | Pre-push verification |
| [GITHUB_SETUP.md](GITHUB_SETUP.md) | Detailed GitHub instructions |
| [QUICK_START.md](QUICK_START.md) | This file - quick reference |

---

## 🎯 Current State

✅ **Complete**: UI, Data Integration, Selection Logic
⏳ **Next Phase**: Video Playback

### What's Working
- 16 Attack selections (from attacks.json)
- 12 Technique selections (from techniques.json)
- 2 Energy flow selections (Ichi, Ni)
- Scrollable dropdown menus
- Play button enable/disable logic
- Selection display

### What's Coming
- Video file playback
- MediaPlayer controls
- MVVM architecture
- Room database persistence

---

## 🔧 Project Info

- **Language**: Kotlin 1.9.0
- **UI**: Jetpack Compose 1.6.0
- **Target**: Android 13+ (API 33+)
- **Device**: Google Pixel
- **Build System**: Gradle 8.13
- **VCS**: Git

---

## 🆘 Troubleshooting

### App won't compile?
- Run: `Build → Clean Project`, then `Build → Rebuild Project`

### Emulator won't launch?
- Ensure Android Emulator is installed
- Try: `AVD Manager → Create Virtual Device`

### Git push fails?
- Check username in command (YOUR_USERNAME)
- Verify GitHub repository is created
- Ensure git is configured: `git config --global user.name "Jim Aebi"`

### App crashes on startup?
- Check Logcat for errors
- Ensure all files are properly saved
- Try Clean and Rebuild

---

## 📞 Key Files Reference

### Source Code
- **MainActivity.kt** - UI and selection logic (171 lines)

### Configuration
- **build.gradle.kts** - Root configuration
- **app/build.gradle** - Module dependencies
- **gradle.properties** - JVM settings

### Data
- **attacks.json** - 16 Aikido attacks
- **techniques.json** - 12 Aikido techniques
- **energy.json** - 2 energy flows

---

## ✨ That's It!

Your Yoshinkan project is ready. Follow these 3 steps to push to GitHub:

1. Create repo on GitHub
2. Run `git push` commands
3. Verify on github.com

**Questions?** Check the detailed docs listed above.

Happy coding! 🎯
