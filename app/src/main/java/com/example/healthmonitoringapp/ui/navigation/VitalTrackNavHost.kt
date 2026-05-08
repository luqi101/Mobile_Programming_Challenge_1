package com.example.healthmonitoringapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.healthmonitoringapp.ui.screens.DailyLogScreen
import com.example.healthmonitoringapp.ui.screens.DashboardScreen
import com.example.healthmonitoringapp.ui.screens.GoalsScreen
import com.example.healthmonitoringapp.ui.screens.InsightsScreen
import com.example.healthmonitoringapp.ui.screens.ProfileScreen
import com.example.healthmonitoringapp.ui.screens.ProgressScreen
import com.example.healthmonitoringapp.viewmodel.VitalTrackUiState
import com.example.healthmonitoringapp.viewmodel.VitalTrackViewModel

@Composable
fun VitalTrackNavHost(
    navController: NavHostController,
    uiState: VitalTrackUiState,
    viewModel: VitalTrackViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = VitalTrackDestination.Dashboard.route,
        modifier = modifier
    ) {
        composable(VitalTrackDestination.Dashboard.route) {
            DashboardScreen(
                uiState = uiState,
                onOpenDailyLog = {
                    navController.navigate(VitalTrackDestination.DailyLog.route) {
                        launchSingleTop = true
                    }
                },
                onAddDemoData = viewModel::populateDemoData
            )
        }
        composable(VitalTrackDestination.DailyLog.route) {
            DailyLogScreen(
                uiState = uiState,
                onDateChange = viewModel::updateDailyDate,
                onWeightChange = viewModel::updateDailyWeight,
                onCaloriesChange = viewModel::updateDailyCalories,
                onWaterChange = viewModel::updateDailyWater,
                onStepsChange = viewModel::updateDailySteps,
                onSleepChange = viewModel::updateDailySleep,
                onHeartRateChange = viewModel::updateDailyHeartRate,
                onMoodChange = viewModel::updateDailyMood,
                onSave = {
                    if (viewModel.saveDailyLog()) {
                        navController.navigate(VitalTrackDestination.Dashboard.route) {
                            popUpTo(VitalTrackDestination.Dashboard.route) {
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    }
                },
                onClear = viewModel::clearDailyForm
            )
        }
        composable(VitalTrackDestination.Progress.route) {
            ProgressScreen(uiState = uiState)
        }
        composable(VitalTrackDestination.Goals.route) {
            GoalsScreen(
                uiState = uiState,
                onTargetWeightChange = viewModel::updateGoalTargetWeight,
                onCaloriesChange = viewModel::updateGoalCalories,
                onWaterChange = viewModel::updateGoalWater,
                onStepsChange = viewModel::updateGoalSteps,
                onSleepChange = viewModel::updateGoalSleep,
                onSave = viewModel::saveGoals
            )
        }
        composable(VitalTrackDestination.Insights.route) {
            InsightsScreen(uiState = uiState)
        }
        composable(VitalTrackDestination.Profile.route) {
            ProfileScreen(
                uiState = uiState,
                onNameChange = viewModel::updateProfileName,
                onHeightChange = viewModel::updateProfileHeight,
                onAgeChange = viewModel::updateProfileAge,
                onGenderChange = viewModel::updateProfileGender,
                onSaveProfile = viewModel::saveProfile,
                onAddDemoData = viewModel::populateDemoData,
                onRequestResetLogs = viewModel::requestResetLogs,
                onDismissResetLogs = viewModel::dismissResetLogs,
                onConfirmResetLogs = viewModel::confirmResetLogs
            )
        }
    }
}
