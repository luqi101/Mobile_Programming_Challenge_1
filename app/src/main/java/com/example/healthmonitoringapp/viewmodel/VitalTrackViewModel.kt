package com.example.healthmonitoringapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.healthmonitoringapp.data.DemoDataFactory
import com.example.healthmonitoringapp.data.HealthLog
import com.example.healthmonitoringapp.data.UserGoals
import com.example.healthmonitoringapp.data.UserProfile
import com.example.healthmonitoringapp.data.VitalTrackRepository
import com.example.healthmonitoringapp.util.DateUtils
import com.example.healthmonitoringapp.util.Validation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class VitalTrackViewModel(
    private val repository: VitalTrackRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(loadInitialState())
    val uiState: StateFlow<VitalTrackUiState> = _uiState.asStateFlow()

    fun updateDailyDate(value: String) {
        val current = _uiState.value
        val existing = if (DateUtils.isValidIsoDate(value.trim())) {
            current.logs.firstOrNull { it.date == value.trim() }
        } else {
            null
        }
        val nextForm = existing?.let { DailyLogForm.from(it) } ?: current.dailyForm.copy(date = value)
        _uiState.value = current.copy(dailyForm = nextForm, dailyErrors = current.dailyErrors - "date")
    }

    fun updateDailyWeight(value: String) = updateDailyForm { copy(weightKg = value) }

    fun updateDailyCalories(value: String) = updateDailyForm { copy(calories = value) }

    fun updateDailyWater(value: String) = updateDailyForm { copy(waterGlasses = value) }

    fun updateDailySteps(value: String) = updateDailyForm { copy(steps = value) }

    fun updateDailySleep(value: String) = updateDailyForm { copy(sleepHours = value) }

    fun updateDailyHeartRate(value: String) = updateDailyForm { copy(heartRate = value) }

    fun updateDailyMood(value: String) = updateDailyForm { copy(moodNote = value) }

    fun clearDailyForm() {
        val date = _uiState.value.dailyForm.date.ifBlank { DateUtils.today() }
        _uiState.value = _uiState.value.copy(
            dailyForm = DailyLogForm(date = date),
            dailyErrors = emptyMap(),
            snackbarMessage = "Daily form cleared."
        )
    }

    fun saveDailyLog(): Boolean {
        val current = _uiState.value
        val form = current.dailyForm.copy(
            date = current.dailyForm.date.trim(),
            moodNote = current.dailyForm.moodNote.trim()
        )
        val errors = Validation.validateDailyLog(form)
        if (errors.isNotEmpty()) {
            _uiState.value = current.copy(dailyErrors = errors, snackbarMessage = "Fix the highlighted fields.")
            return false
        }

        val existing = current.logs.firstOrNull { it.date == form.date }
        val log = HealthLog(
            id = existing?.id ?: DateUtils.stableIdForDate(form.date),
            date = form.date,
            weightKg = Validation.parseDouble(form.weightKg),
            calories = Validation.parseInt(form.calories),
            waterGlasses = Validation.parseInt(form.waterGlasses),
            steps = Validation.parseInt(form.steps),
            sleepHours = Validation.parseDouble(form.sleepHours),
            heartRate = Validation.parseInt(form.heartRate),
            moodNote = form.moodNote
        )

        repository.saveLog(log)
        val logs = repository.loadLogs()
        _uiState.value = current.copy(
            logs = logs,
            dailyForm = DailyLogForm.from(log),
            dailyErrors = emptyMap(),
            snackbarMessage = "Daily log saved."
        )
        return true
    }

    fun updateGoalTargetWeight(value: String) = updateGoalForm { copy(targetWeightKg = value) }

    fun updateGoalCalories(value: String) = updateGoalForm { copy(dailyCalories = value) }

    fun updateGoalWater(value: String) = updateGoalForm { copy(dailyWaterGlasses = value) }

    fun updateGoalSteps(value: String) = updateGoalForm { copy(dailySteps = value) }

    fun updateGoalSleep(value: String) = updateGoalForm { copy(sleepHours = value) }

    fun saveGoals() {
        val current = _uiState.value
        val errors = Validation.validateGoals(current.goalForm)
        if (errors.isNotEmpty()) {
            _uiState.value = current.copy(goalErrors = errors, snackbarMessage = "Fix the highlighted goals.")
            return
        }

        val goals = UserGoals(
            targetWeightKg = Validation.parseDouble(current.goalForm.targetWeightKg),
            dailyCalories = Validation.parseInt(current.goalForm.dailyCalories),
            dailyWaterGlasses = Validation.parseInt(current.goalForm.dailyWaterGlasses),
            dailySteps = Validation.parseInt(current.goalForm.dailySteps),
            sleepHours = Validation.parseDouble(current.goalForm.sleepHours)
        )
        repository.saveGoals(goals)
        _uiState.value = current.copy(
            goals = goals,
            goalForm = GoalForm.from(goals),
            goalErrors = emptyMap(),
            snackbarMessage = "Goals saved."
        )
    }

    fun updateProfileName(value: String) = updateProfileForm { copy(name = value) }

    fun updateProfileHeight(value: String) = updateProfileForm { copy(heightCm = value) }

    fun updateProfileAge(value: String) = updateProfileForm { copy(age = value) }

    fun updateProfileGender(value: String) = updateProfileForm { copy(gender = value) }

    fun saveProfile() {
        val current = _uiState.value
        val form = current.profileForm.copy(
            name = current.profileForm.name.trim(),
            gender = current.profileForm.gender.trim()
        )
        val errors = Validation.validateProfile(form)
        if (errors.isNotEmpty()) {
            _uiState.value = current.copy(profileErrors = errors, snackbarMessage = "Fix the highlighted profile fields.")
            return
        }

        val profile = UserProfile(
            name = form.name,
            heightCm = Validation.parseDouble(form.heightCm),
            age = Validation.parseInt(form.age),
            gender = form.gender
        )
        repository.saveProfile(profile)
        _uiState.value = current.copy(
            profile = profile,
            profileForm = ProfileForm.from(profile),
            profileErrors = emptyMap(),
            snackbarMessage = "Profile saved."
        )
    }

    fun populateDemoData() {
        val logs = DemoDataFactory.logs()
        val goals = DemoDataFactory.goals()
        val profile = DemoDataFactory.profile()
        repository.saveLogs(logs)
        repository.saveGoals(goals)
        repository.saveProfile(profile)
        val todayLog = logs.maxByOrNull { it.date }
        _uiState.value = _uiState.value.copy(
            logs = logs,
            goals = goals,
            profile = profile,
            dailyForm = DailyLogForm.from(todayLog, DateUtils.today()),
            dailyErrors = emptyMap(),
            goalForm = GoalForm.from(goals),
            goalErrors = emptyMap(),
            profileForm = ProfileForm.from(profile),
            profileErrors = emptyMap(),
            snackbarMessage = "Demo data loaded."
        )
    }

    fun requestResetLogs() {
        _uiState.value = _uiState.value.copy(showResetConfirmation = true)
    }

    fun dismissResetLogs() {
        _uiState.value = _uiState.value.copy(showResetConfirmation = false)
    }

    fun confirmResetLogs() {
        repository.clearLogs()
        _uiState.value = _uiState.value.copy(
            logs = emptyList(),
            dailyForm = DailyLogForm(date = DateUtils.today()),
            dailyErrors = emptyMap(),
            showResetConfirmation = false,
            snackbarMessage = "Health logs reset."
        )
    }

    fun consumeSnackbar() {
        _uiState.value = _uiState.value.copy(snackbarMessage = null)
    }

    private fun updateDailyForm(update: DailyLogForm.() -> DailyLogForm) {
        _uiState.value = _uiState.value.copy(
            dailyForm = _uiState.value.dailyForm.update(),
            dailyErrors = emptyMap()
        )
    }

    private fun updateGoalForm(update: GoalForm.() -> GoalForm) {
        _uiState.value = _uiState.value.copy(
            goalForm = _uiState.value.goalForm.update(),
            goalErrors = emptyMap()
        )
    }

    private fun updateProfileForm(update: ProfileForm.() -> ProfileForm) {
        _uiState.value = _uiState.value.copy(
            profileForm = _uiState.value.profileForm.update(),
            profileErrors = emptyMap()
        )
    }

    private fun loadInitialState(): VitalTrackUiState {
        val logs = repository.loadLogs()
        val goals = repository.loadGoals()
        val profile = repository.loadProfile()
        val today = DateUtils.today()
        val latestOrToday = logs.firstOrNull { it.date == today } ?: logs.maxByOrNull { it.date }
        return VitalTrackUiState(
            logs = logs,
            goals = goals,
            profile = profile,
            dailyForm = DailyLogForm.from(latestOrToday, today),
            goalForm = GoalForm.from(goals),
            profileForm = ProfileForm.from(profile)
        )
    }
}
