package com.example.healthmonitoringapp.viewmodel

import com.example.healthmonitoringapp.data.HealthLog
import com.example.healthmonitoringapp.data.UserGoals
import com.example.healthmonitoringapp.data.UserProfile
import com.example.healthmonitoringapp.util.DateUtils
import com.example.healthmonitoringapp.util.formatOneDecimal

data class DailyLogForm(
    val date: String = DateUtils.today(),
    val weightKg: String = "",
    val calories: String = "",
    val waterGlasses: String = "",
    val steps: String = "",
    val sleepHours: String = "",
    val heartRate: String = "",
    val moodNote: String = ""
) {
    companion object {
        fun from(log: HealthLog?, fallbackDate: String = DateUtils.today()): DailyLogForm {
            return if (log == null) {
                DailyLogForm(date = fallbackDate)
            } else {
                DailyLogForm(
                    date = log.date,
                    weightKg = log.weightKg.formatOneDecimal(),
                    calories = log.calories.toString(),
                    waterGlasses = log.waterGlasses.toString(),
                    steps = log.steps.toString(),
                    sleepHours = log.sleepHours.formatOneDecimal(),
                    heartRate = log.heartRate.toString(),
                    moodNote = log.moodNote
                )
            }
        }
    }
}

data class GoalForm(
    val targetWeightKg: String = "",
    val dailyCalories: String = "",
    val dailyWaterGlasses: String = "",
    val dailySteps: String = "",
    val sleepHours: String = ""
) {
    companion object {
        fun from(goals: UserGoals): GoalForm = GoalForm(
            targetWeightKg = goals.targetWeightKg.formatOneDecimal(),
            dailyCalories = goals.dailyCalories.toString(),
            dailyWaterGlasses = goals.dailyWaterGlasses.toString(),
            dailySteps = goals.dailySteps.toString(),
            sleepHours = goals.sleepHours.formatOneDecimal()
        )
    }
}

data class ProfileForm(
    val name: String = "",
    val heightCm: String = "",
    val age: String = "",
    val gender: String = ""
) {
    companion object {
        fun from(profile: UserProfile): ProfileForm = ProfileForm(
            name = profile.name,
            heightCm = profile.heightCm.formatOneDecimal(),
            age = profile.age.toString(),
            gender = profile.gender
        )
    }
}

data class VitalTrackUiState(
    val logs: List<HealthLog> = emptyList(),
    val goals: UserGoals = UserGoals(),
    val profile: UserProfile = UserProfile(),
    val dailyForm: DailyLogForm = DailyLogForm(),
    val dailyErrors: Map<String, String> = emptyMap(),
    val goalForm: GoalForm = GoalForm.from(UserGoals()),
    val goalErrors: Map<String, String> = emptyMap(),
    val profileForm: ProfileForm = ProfileForm.from(UserProfile()),
    val profileErrors: Map<String, String> = emptyMap(),
    val snackbarMessage: String? = null,
    val showResetConfirmation: Boolean = false
)
