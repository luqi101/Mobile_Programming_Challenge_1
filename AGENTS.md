# AGENTS.md - VitalTrack Health Monitoring App

## Project Context

This repository is for CS5450 Mobile Programming Challenge 1.

Assigned group:
- Group #1: Health Monitoring App

Required app type:
- Native Android application using Kotlin and Jetpack Compose.
- The challenge PDF title and grading criteria refer to Kotlin/Jetpack Compose.
- Do not convert this project to Flutter.
- Do not add a backend, Firebase, login system, payment system, or web-only implementation.

App name:
- VitalTrack

Goal:
Build a professional-quality health monitoring app with multiple Compose screens, custom styling, useful health-tracking functionality, local data persistence, clean project structure, and a README.pdf-ready documentation source.

The final app must be stable, compile successfully, and be suitable for demonstration on an Android emulator and ideally an actual Android phone.

---

## Absolute Requirements

1. The app must compile without errors.
2. Do not leave broken imports, TODO placeholders in executable code, or incomplete functions.
3. Do not introduce dependencies unless necessary.
4. If adding dependencies, ensure Gradle sync and build compatibility.
5. Use Jetpack Compose best practices.
6. Use Material 3 components.
7. Use a clean ViewModel-based architecture.
8. Use reusable UI components instead of duplicating card/chart code everywhere.
9. App must have multiple screens with navigation.
10. App must preserve user-entered data locally across app restarts.
11. Input fields must validate invalid values gracefully.
12. UI must not crash on empty data.
13. Charts must handle empty, one-point, and multi-point datasets.
14. Do not require internet access.
15. Do not require runtime permissions unless absolutely necessary.
16. Do not use copyrighted brand assets or logos.
17. Do not copy UI/code from an online health app.
18. Keep the app original and submission-safe.
19. Include documentation files for README.pdf generation.
20. Create a demo data feature so screenshots and presentation can be prepared easily.

---

## Target Feature Set

Build the app as a local-first health monitoring app.

Screens:

1. Dashboard
   - Today summary.
   - Weight card.
   - BMI card.
   - Water progress.
   - Calories progress.
   - Steps progress.
   - Heart rate card.
   - Sleep card.
   - Weekly streak or consistency card.
   - Health tip.

2. Daily Log
   - Add or update daily health entry.
   - Fields:
     - weight in kg
     - calories consumed
     - water glasses
     - steps
     - sleep hours
     - resting heart rate
     - mood or note
   - Save button.
   - Clear form button.
   - Validation messages.

3. Progress
   - Weight trend chart.
   - Calories trend chart.
   - Water trend chart.
   - Steps trend chart.
   - Compact stats cards.
   - Empty state when no data exists.

4. Goals
   - Target weight.
   - Daily calorie goal.
   - Daily water glasses goal.
   - Daily steps goal.
   - Sleep hours goal.
   - Save goals.
   - Show current goal progress.

5. Insights
   - BMI calculation.
   - BMI category.
   - Total weight change.
   - Average calories.
   - Average water.
   - Average sleep.
   - Best step day.
   - Personalized recommendations based on values.

6. Profile / About
   - User name.
   - Height in cm.
   - Age.
   - Optional gender field.
   - Save profile.
   - About the app.
   - Course and challenge information.
   - Group member placeholder section.

---

## Architecture

Use this package structure unless the existing project structure requires a minor adaptation:

com.example.vitaltrack
├── MainActivity.kt
├── VitalTrackApp.kt
├── data
├── ui.navigation
├── ui.theme
├── ui.components
├── ui.screens
├── viewmodel
└── util

Use:
- Kotlin data classes for models.
- ViewModel for app state.
- MutableStateFlow / StateFlow for UI state.
- Repository class for persistence.
- SharedPreferences or another simple local persistence method.
- Compose Navigation for screen navigation.
- Material 3 Scaffold and NavigationBar.

Avoid:
- Global mutable state in composables.
- Business logic directly inside composables.
- Hardcoded duplicated calculations.
- Unvalidated numeric parsing.
- Crashes caused by null/empty lists.
- External chart dependencies unless already configured.

---

## UI / Design Requirements

The app should look polished and custom.

Design language:
- Health-focused, modern, clean.
- Use green/teal/blue health palette.
- Rounded cards.
- Soft backgrounds.
- Consistent spacing.
- Meaningful icons using Material Icons if already available.
- Responsive layouts for different screen sizes.

Compose rules:
- Use LazyColumn for scrollable screens.
- Use Scaffold.
- Use NavigationBar for main sections.
- Use reusable cards.
- Use clear typography hierarchy.
- Add content descriptions where appropriate.
- Avoid text overflow.
- Use preview-friendly components where possible.

Charts:
- Implement charts using Compose Canvas.
- Must not crash if the list is empty.
- Must show useful empty state.
- Must render correctly with one data point.
- Must have labels/title.

---

## Data Models

HealthLog should include:
- id
- date
- weightKg
- calories
- waterGlasses
- steps
- sleepHours
- heartRate
- moodNote

UserGoals should include:
- targetWeightKg
- dailyCalories
- dailyWaterGlasses
- dailySteps
- sleepHours

UserProfile should include:
- name
- heightCm
- age
- gender

Calculations:
- BMI = weightKg / (heightMeters * heightMeters)
- BMI category:
  - Underweight: < 18.5
  - Normal: 18.5-24.9
  - Overweight: 25.0-29.9
  - Obese: >= 30
- Weight change = latest weight - first weight
- Goal percentages must be clamped safely between 0 and reasonable display max.
- Avoid division by zero.

---

## Persistence

Persist:
- health logs
- goals
- profile

Persistence requirements:
- App should show existing saved data after restart.
- Add demo data button should populate realistic sample data.
- Reset data button should clear logs only after confirmation.
- Persistence code must be defensive against malformed saved values.

---

## Documentation Requirements

Create documentation source files:
- docs/README.md
- docs/SCREENSHOT_CHECKLIST.md
- docs/PRESENTATION_SCRIPT.md
- docs/PROJECT_STRUCTURE.md

The README.md must be suitable to export as README.pdf and include:
1. Title page information.
2. Course name: CS5450 Mobile Programming.
3. Challenge 1.
4. Group #1: Health Monitoring App.
5. App name: VitalTrack.
6. GitHub repository link placeholder.
7. Setup/configuration instructions.
8. Exact project structure.
9. How to run on emulator.
10. How to run on actual phone.
11. App features.
12. Screenshots section with placeholders.
13. Demo flow.
14. Technology stack.
15. Known limitations.
16. Group member contribution table placeholder.

Do not fake screenshots. Use placeholders and clear instructions for replacing them with real screenshots.

---

## Build / QA Requirements

After implementation:
1. Run Gradle sync mentally by ensuring correct imports and dependencies.
2. Run:
   - ./gradlew clean
   - ./gradlew assembleDebug
3. Fix all compile errors.
4. Do not stop after partial implementation.
5. Check all screens.
6. Check empty state.
7. Check demo data flow.
8. Check save/restart behavior.
9. Check orientation/responsive behavior if possible.
10. Ensure no red code remains.

---

## Code Quality

Use meaningful names.
Keep files focused.
No massive single-file app.
No unused imports.
No dead code.
No debug print spam.
No hardcoded professor/course data inside core business logic except documentation/about screen.
No crashes from invalid input.

All generated code must be final, complete, and submission-ready.