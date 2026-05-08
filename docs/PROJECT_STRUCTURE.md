# VitalTrack Project Structure

Generated folders such as `.gradle/`, `.idea/`, `build/`, and `app/build/` are excluded from this structure.

```text
.
├── .gitignore
├── AGENTS.md
├── MC_Challange1_2026NN.pdf
├── README.md
├── README.pdf
├── build.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
├── settings.gradle.kts
├── gradle/
│   ├── gradle-daemon-jvm.properties
│   ├── libs.versions.toml
│   └── wrapper/
├── app/
│   ├── .gitignore
│   ├── build.gradle.kts
│   ├── proguard-rules.pro
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   ├── java/com/example/healthmonitoringapp/
│       │   │   ├── MainActivity.kt
│       │   │   ├── VitalTrackApp.kt
│       │   │   ├── data/
│       │   │   ├── util/
│       │   │   ├── viewmodel/
│       │   │   └── ui/
│       │   │       ├── components/
│       │   │       ├── navigation/
│       │   │       ├── screens/
│       │   │       └── theme/
│       │   └── res/
│       ├── androidTest/
│       └── test/
├── docs/
│   ├── PROJECT_STRUCTURE.md
│   ├── README.md
│   └── SCREENSHOT_CHECKLIST.md
└── screenshots/
    ├── Screenshot_20260508_164612.png
    ├── Screenshot_20260508_164659.png
    ├── Screenshot_20260508_164708.png
    ├── Screenshot_20260508_164720.png
    ├── Screenshot_20260508_164732.png
    ├── Screenshot_20260508_164745.png
    ├── Screenshot_20260508_164749.png
    ├── Screenshot_20260508_164803.png
    └── Screenshot_20260508_164826.png
```

## Architecture Summary

- `data` contains models, demo seed data, and SharedPreferences persistence.
- `util` contains date handling, formatting, validation, and health calculations.
- `viewmodel` owns StateFlow UI state and write actions.
- `ui.navigation` defines bottom navigation destinations and the NavHost.
- `ui.components` contains reusable cards, fields, progress rings, empty states, and Canvas charts.
- `ui.screens` contains Dashboard, Daily Log, Progress, Goals, Insights, and Profile/About screens.
- `ui.theme` defines the custom Material 3 health palette and typography.
