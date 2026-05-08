package com.example.healthmonitoringapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ShowChart
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.Insights
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class VitalTrackDestination(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    data object Dashboard : VitalTrackDestination("dashboard", "Home", Icons.Outlined.Dashboard)
    data object DailyLog : VitalTrackDestination("daily_log", "Log", Icons.Outlined.AddCircle)
    data object Progress : VitalTrackDestination("progress", "Progress", Icons.AutoMirrored.Outlined.ShowChart)
    data object Goals : VitalTrackDestination("goals", "Goals", Icons.Outlined.Flag)
    data object Insights : VitalTrackDestination("insights", "Insights", Icons.Outlined.Insights)
    data object Profile : VitalTrackDestination("profile", "Profile", Icons.Outlined.Person)

    companion object {
        val bottomBarDestinations = listOf(Dashboard, DailyLog, Progress, Goals, Insights, Profile)
    }
}
