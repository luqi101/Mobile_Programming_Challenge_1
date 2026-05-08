package com.example.healthmonitoringapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.DirectionsWalk
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.MonitorHeart
import androidx.compose.material.icons.outlined.Scale
import androidx.compose.material.icons.outlined.TipsAndUpdates
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.healthmonitoringapp.ui.components.EmptyState
import com.example.healthmonitoringapp.ui.components.MetricCard
import com.example.healthmonitoringapp.ui.components.MetricCardData
import com.example.healthmonitoringapp.ui.components.MetricCardGrid
import com.example.healthmonitoringapp.ui.components.ProgressRingCard
import com.example.healthmonitoringapp.ui.components.SectionHeader
import com.example.healthmonitoringapp.util.DateUtils
import com.example.healthmonitoringapp.util.HealthCalculations
import com.example.healthmonitoringapp.util.formatOneDecimal
import com.example.healthmonitoringapp.util.formatWholeNumber
import com.example.healthmonitoringapp.viewmodel.VitalTrackUiState

@Composable
fun DashboardScreen(
    uiState: VitalTrackUiState,
    onOpenDailyLog: () -> Unit,
    onAddDemoData: () -> Unit,
    modifier: Modifier = Modifier
) {
    val latest = HealthCalculations.latestLog(uiState.logs)
    val bmi = HealthCalculations.bmi(latest?.weightKg, uiState.profile.heightCm)
    val consistency = HealthCalculations.weeklyConsistency(uiState.logs)
    val recommendations = HealthCalculations.recommendations(latest, uiState.goals, uiState.profile)

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SectionHeader(
                title = "VitalTrack",
                subtitle = "Today for ${uiState.profile.name}. Last log: ${latest?.date?.let(DateUtils::displayDate) ?: "none yet"}"
            )
        }

        if (latest == null) {
            item {
                EmptyState(
                    title = "No health logs yet",
                    message = "Create your first daily log or load demo data for presentation screenshots."
                )
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onOpenDailyLog,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Add log")
                    }
                    OutlinedButton(
                        onClick = onAddDemoData,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Demo data")
                    }
                }
            }
        } else {
            item {
                MetricCardGrid(
                    cards = listOf(
                        MetricCardData(
                            title = "Weight",
                            value = "${latest.weightKg.formatOneDecimal()} kg",
                            subtitle = "Target ${uiState.goals.targetWeightKg.formatOneDecimal()} kg",
                            icon = Icons.Outlined.Scale,
                            accent = Color(0xFF1B8A5A)
                        ),
                        MetricCardData(
                            title = "BMI",
                            value = bmi?.formatOneDecimal() ?: "--",
                            subtitle = HealthCalculations.bmiCategory(bmi),
                            icon = Icons.Outlined.Favorite,
                            accent = Color(0xFF007C89)
                        ),
                        MetricCardData(
                            title = "Heart rate",
                            value = "${latest.heartRate} bpm",
                            subtitle = "Resting heart rate",
                            icon = Icons.Outlined.MonitorHeart,
                            accent = Color(0xFFE0564A)
                        ),
                        MetricCardData(
                            title = "Sleep",
                            value = "${latest.sleepHours.formatOneDecimal()} h",
                            subtitle = "Goal ${uiState.goals.sleepHours.formatOneDecimal()} h",
                            icon = Icons.Outlined.Bedtime,
                            accent = Color(0xFF4F46E5)
                        )
                    )
                )
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ProgressRingCard(
                        title = "Water",
                        percentLabel = HealthCalculations.progressPercentLabel(
                            latest.waterGlasses.toDouble(),
                            uiState.goals.dailyWaterGlasses.toDouble()
                        ),
                        detail = "${latest.waterGlasses}/${uiState.goals.dailyWaterGlasses} glasses",
                        progress = HealthCalculations.progressFraction(
                            latest.waterGlasses.toDouble(),
                            uiState.goals.dailyWaterGlasses.toDouble()
                        ),
                        accent = Color(0xFF007C89),
                        modifier = Modifier.weight(1f)
                    )
                    ProgressRingCard(
                        title = "Steps",
                        percentLabel = HealthCalculations.progressPercentLabel(
                            latest.steps.toDouble(),
                            uiState.goals.dailySteps.toDouble()
                        ),
                        detail = "${latest.steps.formatWholeNumber()}/${uiState.goals.dailySteps.formatWholeNumber()}",
                        progress = HealthCalculations.progressFraction(
                            latest.steps.toDouble(),
                            uiState.goals.dailySteps.toDouble()
                        ),
                        accent = Color(0xFF2563EB),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            item {
                MetricCardGrid(
                    cards = listOf(
                        MetricCardData(
                            title = "Calories",
                            value = latest.calories.formatWholeNumber(),
                            subtitle = "Goal ${uiState.goals.dailyCalories.formatWholeNumber()}",
                            icon = Icons.Outlined.LocalFireDepartment,
                            accent = Color(0xFFE8753C)
                        ),
                        MetricCardData(
                            title = "Water",
                            value = "${latest.waterGlasses} glasses",
                            subtitle = HealthCalculations.progressPercentLabel(
                                latest.waterGlasses.toDouble(),
                                uiState.goals.dailyWaterGlasses.toDouble()
                            ),
                            icon = Icons.Outlined.WaterDrop,
                            accent = Color(0xFF007C89)
                        ),
                        MetricCardData(
                            title = "Steps",
                            value = latest.steps.formatWholeNumber(),
                            subtitle = "Daily movement",
                            icon = Icons.AutoMirrored.Outlined.DirectionsWalk,
                            accent = Color(0xFF2563EB)
                        ),
                        MetricCardData(
                            title = "Weekly streak",
                            value = "$consistency/7",
                            subtitle = "Days logged this week",
                            icon = Icons.Outlined.CalendarMonth,
                            accent = Color(0xFF1B8A5A)
                        )
                    )
                )
            }
            item {
                MetricCard(
                    title = "Health tip",
                    value = "Next best action",
                    subtitle = recommendations.firstOrNull() ?: "Keep logging daily to build insights.",
                    icon = Icons.Outlined.TipsAndUpdates,
                    accent = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                Button(
                    onClick = onOpenDailyLog,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Update daily log",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
