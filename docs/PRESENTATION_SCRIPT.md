# VitalTrack Presentation Script

## 3-5 Minute Zoom Demo Script

Good afternoon. We are Group #1, and our assigned topic is the Health Monitoring App. Our application is called VitalTrack. It is a native Android application built with Kotlin, Jetpack Compose, and Material 3 for CS5450 Mobile Programming Challenge 1.

VitalTrack is a local-first health monitoring app. It does not require internet access, Firebase, login, Health Connect, wearable integration, or a backend server. That makes it reliable for emulator and phone demonstration because all data is stored locally on the device.

First, I will start on the Dashboard. The Dashboard summarizes the user's latest health log, including weight, BMI, resting heart rate, sleep, water progress, steps progress, calories, weekly consistency, and a health tip. If the app has no data, it shows an empty state instead of crashing. For demonstration, the app includes realistic demo data for a healthy, fit profile.

Next, I will open the Daily Log screen. This form lets the user add or update one health entry for a date. It records weight, calories, water glasses, steps, sleep hours, resting heart rate, and a short mood or note. The form validates invalid numbers and gives clear error messages. After a valid save, the app returns to the Dashboard so the updated summary is visible immediately.

Now I will open the Goals screen. This screen stores target weight, daily calories, daily water glasses, daily steps, and sleep hours. Below the form, the app compares today's values against those goals using progress ring cards.

Next is the Progress screen. This screen shows compact statistics and custom Compose Canvas trend charts. The charts are implemented directly in Compose rather than with an external chart library. They handle empty data and saved demo data safely.

Now I will open Insights. This screen calculates BMI, BMI category, total weight change, average calories, average water, average sleep, best step day, and recommendations based on the user's profile, goals, and latest log. The interpretation note clarifies that this is a class project and not medical advice.

Finally, I will open Profile/About. This screen stores profile details such as name, height, and age. It also includes demo controls and a reset-logs confirmation. The About section identifies the project as a CS5450 Challenge 1 health monitoring app.

The main technical design is separated into UI screens, reusable components, navigation, ViewModel state, data models, utility calculations, validation, and local SharedPreferences persistence. This structure keeps the app stable, readable, and easy to demonstrate.

To conclude, VitalTrack satisfies the challenge requirements as a multi-screen Kotlin/Jetpack Compose app with custom styling, local persistence, charts, validation, screenshots, README.pdf documentation, and GitHub/ZIP submission readiness.
