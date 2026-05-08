package com.example.healthmonitoringapp.data

import android.content.Context
import android.content.SharedPreferences
import com.example.healthmonitoringapp.util.DateUtils
import org.json.JSONArray
import org.json.JSONObject

class VitalTrackRepository(context: Context) {
    private val prefs: SharedPreferences = context.applicationContext.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )

    fun loadLogs(): List<HealthLog> {
        val raw = prefs.getString(KEY_LOGS, null) ?: return emptyList()
        return runCatching {
            val array = JSONArray(raw)
            buildList {
                for (index in 0 until array.length()) {
                    val json = array.optJSONObject(index) ?: continue
                    val log = parseLog(json)
                    if (log != null) add(log)
                }
            }.sortedBy { it.date }
        }.getOrDefault(emptyList())
    }

    fun saveLog(log: HealthLog) {
        val updated = loadLogs()
            .filterNot { it.date == log.date }
            .plus(log)
            .sortedBy { it.date }
        saveLogs(updated)
    }

    fun saveLogs(logs: List<HealthLog>) {
        val array = JSONArray()
        logs.sortedBy { it.date }.forEach { log ->
            array.put(
                JSONObject()
                    .put("id", log.id)
                    .put("date", log.date)
                    .put("weightKg", log.weightKg)
                    .put("calories", log.calories)
                    .put("waterGlasses", log.waterGlasses)
                    .put("steps", log.steps)
                    .put("sleepHours", log.sleepHours)
                    .put("heartRate", log.heartRate)
                    .put("moodNote", log.moodNote)
            )
        }
        prefs.edit().putString(KEY_LOGS, array.toString()).apply()
    }

    fun clearLogs() {
        prefs.edit().remove(KEY_LOGS).apply()
    }

    fun loadGoals(): UserGoals {
        val raw = prefs.getString(KEY_GOALS, null) ?: return UserGoals()
        return runCatching {
            val json = JSONObject(raw)
            val defaults = UserGoals()
            val targetWeight = json.optDouble("targetWeightKg", defaults.targetWeightKg)
            val sleepHours = json.optDouble("sleepHours", defaults.sleepHours)
            UserGoals(
                targetWeightKg = targetWeight.takeIf { it.isFinite() && it in 20.0..400.0 } ?: defaults.targetWeightKg,
                dailyCalories = json.optInt("dailyCalories", defaults.dailyCalories).takeIf { it in 500..10000 } ?: defaults.dailyCalories,
                dailyWaterGlasses = json.optInt("dailyWaterGlasses", defaults.dailyWaterGlasses).takeIf { it in 1..30 } ?: defaults.dailyWaterGlasses,
                dailySteps = json.optInt("dailySteps", defaults.dailySteps).takeIf { it in 100..100000 } ?: defaults.dailySteps,
                sleepHours = sleepHours.takeIf { it.isFinite() && it in 1.0..24.0 } ?: defaults.sleepHours
            )
        }.getOrDefault(UserGoals())
    }

    fun saveGoals(goals: UserGoals) {
        val json = JSONObject()
            .put("targetWeightKg", goals.targetWeightKg)
            .put("dailyCalories", goals.dailyCalories)
            .put("dailyWaterGlasses", goals.dailyWaterGlasses)
            .put("dailySteps", goals.dailySteps)
            .put("sleepHours", goals.sleepHours)
        prefs.edit().putString(KEY_GOALS, json.toString()).apply()
    }

    fun loadProfile(): UserProfile {
        val raw = prefs.getString(KEY_PROFILE, null) ?: return UserProfile()
        return runCatching {
            val json = JSONObject(raw)
            val defaults = UserProfile()
            val height = json.optDouble("heightCm", defaults.heightCm)
            UserProfile(
                name = json.optString("name", defaults.name).take(40).ifBlank { defaults.name },
                heightCm = height.takeIf { it.isFinite() && it in 80.0..250.0 } ?: defaults.heightCm,
                age = json.optInt("age", defaults.age).takeIf { it in 1..120 } ?: defaults.age,
                gender = json.optString("gender", defaults.gender).take(30)
            )
        }.getOrDefault(UserProfile())
    }

    fun saveProfile(profile: UserProfile) {
        val json = JSONObject()
            .put("name", profile.name)
            .put("heightCm", profile.heightCm)
            .put("age", profile.age)
            .put("gender", profile.gender)
        prefs.edit().putString(KEY_PROFILE, json.toString()).apply()
    }

    private fun parseLog(json: JSONObject): HealthLog? {
        val date = json.optString("date")
        if (!DateUtils.isValidIsoDate(date)) return null
        val weightKg = json.optDouble("weightKg")
        val sleepHours = json.optDouble("sleepHours")
        val calories = json.optInt("calories", -1)
        val waterGlasses = json.optInt("waterGlasses", -1)
        val steps = json.optInt("steps", -1)
        val heartRate = json.optInt("heartRate", -1)

        if (!weightKg.isFinite() || weightKg !in 20.0..400.0) return null
        if (!sleepHours.isFinite() || sleepHours !in 0.0..24.0) return null
        if (calories !in 0..10000) return null
        if (waterGlasses !in 0..30) return null
        if (steps !in 0..100000) return null
        if (heartRate !in 30..220) return null

        return HealthLog(
            id = json.optLong("id", DateUtils.stableIdForDate(date)),
            date = date,
            weightKg = weightKg,
            calories = calories,
            waterGlasses = waterGlasses,
            steps = steps,
            sleepHours = sleepHours,
            heartRate = heartRate,
            moodNote = json.optString("moodNote").take(120)
        )
    }

    private companion object {
        const val PREFS_NAME = "vital_track_preferences"
        const val KEY_LOGS = "health_logs_json"
        const val KEY_GOALS = "user_goals_json"
        const val KEY_PROFILE = "user_profile_json"
    }
}
