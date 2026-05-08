package com.example.healthmonitoringapp.util

import com.example.healthmonitoringapp.data.HealthLog
import com.example.healthmonitoringapp.data.UserGoals
import com.example.healthmonitoringapp.data.UserProfile

object HealthCalculations {
    fun latestLog(logs: List<HealthLog>): HealthLog? = logs.maxByOrNull { it.date }

    fun firstLog(logs: List<HealthLog>): HealthLog? = logs.minByOrNull { it.date }

    fun bmi(weightKg: Double?, heightCm: Double): Double? {
        if (weightKg == null || !weightKg.isFinite() || !heightCm.isFinite() || weightKg <= 0.0 || heightCm <= 0.0) return null
        val heightMeters = heightCm / 100.0
        return weightKg / (heightMeters * heightMeters)
    }

    fun bmiCategory(bmi: Double?): String {
        return when {
            bmi == null -> "No data"
            bmi < 18.5 -> "Underweight"
            bmi < 25.0 -> "Normal"
            bmi < 30.0 -> "Overweight"
            else -> "Obese"
        }
    }

    fun weightChange(logs: List<HealthLog>): Double? {
        val first = firstLog(logs) ?: return null
        val latest = latestLog(logs) ?: return null
        return latest.weightKg - first.weightKg
    }

    fun averageCalories(logs: List<HealthLog>): Double = averageInts(logs.map { it.calories })

    fun averageWater(logs: List<HealthLog>): Double = averageInts(logs.map { it.waterGlasses })

    fun averageSleep(logs: List<HealthLog>): Double = averageDoubles(logs.map { it.sleepHours })

    fun bestStepDay(logs: List<HealthLog>): HealthLog? = logs.maxByOrNull { it.steps }

    fun weeklyConsistency(logs: List<HealthLog>): Int {
        if (logs.isEmpty()) return 0
        val recentDates = DateUtils.pastWeekDates()
        return logs.count { it.date in recentDates }.coerceIn(0, 7)
    }

    fun progressFraction(current: Double, goal: Double): Float {
        if (goal <= 0.0 || current <= 0.0) return 0f
        return (current / goal).toFloat().coerceIn(0f, 1f)
    }

    fun progressPercentLabel(current: Double, goal: Double): String {
        if (goal <= 0.0 || current <= 0.0) return "0%"
        val percent = ((current / goal) * 100.0).coerceIn(0.0, 150.0).toInt()
        return "$percent%"
    }

    fun recommendations(
        latestLog: HealthLog?,
        goals: UserGoals,
        profile: UserProfile
    ): List<String> {
        val recommendations = mutableListOf<String>()
        val currentBmi = bmi(latestLog?.weightKg, profile.heightCm)

        if (latestLog == null) {
            return listOf("Add a daily log to unlock personalized health insights.")
        }

        if (currentBmi != null && currentBmi >= 30.0) {
            recommendations += "Focus on steady habits: consistent steps, balanced calories, and weekly weight tracking."
        } else if (currentBmi != null && currentBmi < 18.5) {
            recommendations += "Consider nutrient-dense meals and discuss weight goals with a health professional."
        } else {
            recommendations += "Your BMI trend is in a trackable range; keep logging to spot useful patterns."
        }

        if (latestLog.waterGlasses < goals.dailyWaterGlasses) {
            recommendations += "Add ${goals.dailyWaterGlasses - latestLog.waterGlasses} more glass(es) of water to reach today's goal."
        }
        if (latestLog.steps < goals.dailySteps) {
            recommendations += "A short walk can move you closer to the ${goals.dailySteps.formatWholeNumber()} step target."
        }
        if (latestLog.sleepHours < goals.sleepHours) {
            recommendations += "Plan a quieter evening routine to support the ${goals.sleepHours.formatOneDecimal()} hour sleep goal."
        }
        if (latestLog.calories > goals.dailyCalories) {
            recommendations += "Calories are above goal today; use the trend screen to check whether this is a one-day spike."
        }

        return recommendations.distinct().take(4)
    }

    private fun averageInts(values: List<Int>): Double = if (values.isEmpty()) 0.0 else values.average()

    private fun averageDoubles(values: List<Double>): Double {
        val safeValues = values.filter { it.isFinite() }
        return if (safeValues.isEmpty()) 0.0 else safeValues.average()
    }
}
