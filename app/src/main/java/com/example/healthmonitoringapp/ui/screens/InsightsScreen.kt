package com.example.healthmonitoringapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.DirectionsWalk
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Insights
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.Scale
import androidx.compose.material.icons.outlined.TipsAndUpdates
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.healthmonitoringapp.ui.components.EmptyState
import com.example.healthmonitoringapp.ui.components.MetricCardData
import com.example.healthmonitoringapp.ui.components.MetricCardGrid
import com.example.healthmonitoringapp.ui.components.SectionHeader
import com.example.healthmonitoringapp.util.DateUtils
import com.example.healthmonitoringapp.util.HealthCalculations
import com.example.healthmonitoringapp.util.formatOneDecimal
import com.example.healthmonitoringapp.util.formatSignedOneDecimal
import com.example.healthmonitoringapp.util.formatWholeNumber
import com.example.healthmonitoringapp.viewmodel.VitalTrackUiState

@Composable
fun InsightsScreen(
    uiState: VitalTrackUiState,
    modifier: Modifier = Modifier
) {
    val logs = uiState.logs
    val latest = HealthCalculations.latestLog(logs)
    val bmi = HealthCalculations.bmi(latest?.weightKg, uiState.profile.heightCm)
    val bestSteps = HealthCalculations.bestStepDay(logs)
    val recommendations = HealthCalculations.recommendations(latest, uiState.goals, uiState.profile)

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SectionHeader(
                title = "Insights",
                subtitle = "Calculated from local profile, goals, and logs."
            )
        }

        if (logs.isEmpty()) {
            item {
                EmptyState(
                    title = "No insights yet",
                    message = "Save logs or add demo data to calculate BMI, averages, and recommendations.",
                    icon = Icons.Outlined.Insights
                )
            }
        } else {
            item {
                MetricCardGrid(
                    cards = listOf(
                        MetricCardData(
                            title = "BMI",
                            value = bmi?.formatOneDecimal() ?: "--",
                            subtitle = HealthCalculations.bmiCategory(bmi),
                            icon = Icons.Outlined.Favorite,
                            accent = Color(0xFF007C89)
                        ),
                        MetricCardData(
                            title = "Weight change",
                            value = "${HealthCalculations.weightChange(logs)?.formatSignedOneDecimal() ?: "0.0"} kg",
                            subtitle = "First log to latest",
                            icon = Icons.Outlined.Scale,
                            accent = Color(0xFF1B8A5A)
                        ),
                        MetricCardData(
                            title = "Average calories",
                            value = HealthCalculations.averageCalories(logs).toInt().formatWholeNumber(),
                            subtitle = "Per logged day",
                            icon = Icons.Outlined.LocalFireDepartment,
                            accent = Color(0xFFE8753C)
                        ),
                        MetricCardData(
                            title = "Average water",
                            value = "${HealthCalculations.averageWater(logs).formatOneDecimal()}",
                            subtitle = "Glasses per day",
                            icon = Icons.Outlined.WaterDrop,
                            accent = Color(0xFF007C89)
                        ),
                        MetricCardData(
                            title = "Average sleep",
                            value = "${HealthCalculations.averageSleep(logs).formatOneDecimal()} h",
                            subtitle = "Per logged night",
                            icon = Icons.Outlined.Bedtime,
                            accent = Color(0xFF4F46E5)
                        ),
                        MetricCardData(
                            title = "Best step day",
                            value = bestSteps?.steps?.formatWholeNumber() ?: "--",
                            subtitle = bestSteps?.date?.let(DateUtils::displayDate) ?: "No step data",
                            icon = Icons.AutoMirrored.Outlined.DirectionsWalk,
                            accent = Color(0xFF2563EB)
                        )
                    )
                )
            }
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Personalized recommendations",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        recommendations.forEachIndexed { index, recommendation ->
                            Text(
                                text = "${index + 1}. $recommendation",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "Interpretation note",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "VitalTrack is a class project for personal tracking and demonstration. It does not provide medical diagnosis.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
