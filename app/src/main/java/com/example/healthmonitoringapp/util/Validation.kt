package com.example.healthmonitoringapp.util

import com.example.healthmonitoringapp.viewmodel.DailyLogForm
import com.example.healthmonitoringapp.viewmodel.GoalForm
import com.example.healthmonitoringapp.viewmodel.ProfileForm

object Validation {
    fun validateDailyLog(form: DailyLogForm): Map<String, String> {
        val errors = mutableMapOf<String, String>()
        if (!DateUtils.isValidIsoDate(form.date.trim())) {
            errors["date"] = "Use YYYY-MM-DD."
        }
        validateDouble(form.weightKg, "weightKg", "Weight", 20.0, 400.0, errors)
        validateInt(form.calories, "calories", "Calories", 0, 10000, errors)
        validateInt(form.waterGlasses, "waterGlasses", "Water", 0, 30, errors)
        validateInt(form.steps, "steps", "Steps", 0, 100000, errors)
        validateDouble(form.sleepHours, "sleepHours", "Sleep", 0.0, 24.0, errors)
        validateInt(form.heartRate, "heartRate", "Heart rate", 30, 220, errors)
        if (form.moodNote.length > 120) {
            errors["moodNote"] = "Keep notes under 120 characters."
        }
        return errors
    }

    fun validateGoals(form: GoalForm): Map<String, String> {
        val errors = mutableMapOf<String, String>()
        validateDouble(form.targetWeightKg, "targetWeightKg", "Target weight", 20.0, 400.0, errors)
        validateInt(form.dailyCalories, "dailyCalories", "Calories", 500, 10000, errors)
        validateInt(form.dailyWaterGlasses, "dailyWaterGlasses", "Water", 1, 30, errors)
        validateInt(form.dailySteps, "dailySteps", "Steps", 100, 100000, errors)
        validateDouble(form.sleepHours, "sleepHours", "Sleep", 1.0, 24.0, errors)
        return errors
    }

    fun validateProfile(form: ProfileForm): Map<String, String> {
        val errors = mutableMapOf<String, String>()
        val name = form.name.trim()
        if (name.isBlank()) {
            errors["name"] = "Name is required."
        } else if (name.length > 40) {
            errors["name"] = "Keep name under 40 characters."
        }
        validateDouble(form.heightCm, "heightCm", "Height", 80.0, 250.0, errors)
        validateInt(form.age, "age", "Age", 1, 120, errors)
        if (form.gender.length > 30) {
            errors["gender"] = "Keep gender under 30 characters."
        }
        return errors
    }

    fun parseDouble(value: String): Double = value.trim().toDouble()

    fun parseInt(value: String): Int = value.trim().toInt()

    private fun validateDouble(
        value: String,
        field: String,
        label: String,
        min: Double,
        max: Double,
        errors: MutableMap<String, String>
    ) {
        val parsed = value.trim().toDoubleOrNull()
        when {
            parsed == null -> errors[field] = "$label must be a number."
            !parsed.isFinite() -> errors[field] = "$label must be a valid number."
            parsed < min || parsed > max -> errors[field] = "$label must be between ${min.formatOneDecimal()} and ${max.formatOneDecimal()}."
        }
    }

    private fun validateInt(
        value: String,
        field: String,
        label: String,
        min: Int,
        max: Int,
        errors: MutableMap<String, String>
    ) {
        val parsed = value.trim().toIntOrNull()
        when {
            parsed == null -> errors[field] = "$label must be a whole number."
            parsed < min || parsed > max -> errors[field] = "$label must be between $min and ${max.formatWholeNumber()}."
        }
    }
}
