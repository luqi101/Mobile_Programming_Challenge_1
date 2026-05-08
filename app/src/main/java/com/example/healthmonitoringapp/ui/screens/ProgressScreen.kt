package com.example.healthmonitoringapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.DirectionsWalk
import androidx.compose.material.icons.automirrored.outlined.ShowChart
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.Scale
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.healthmonitoringapp.ui.components.ChartPoint
import com.example.healthmonitoringapp.ui.components.EmptyState
import com.example.healthmonitoringapp.ui.components.MetricCardData
import com.example.healthmonitoringapp.ui.components.MetricCardGrid
import com.example.healthmonitoringapp.ui.components.SectionHeader
import com.example.healthmonitoringapp.ui.components.TrendChart
import com.example.healthmonitoringapp.util.DateUtils
import com.example.healthmonitoringapp.util.HealthCalculations
import com.example.healthmonitoringapp.util.formatOneDecimal
import com.example.healthmonitoringapp.util.formatSignedOneDecimal
import com.example.healthmonitoringapp.util.formatWholeNumber
import com.example.healthmonitoringapp.viewmodel.VitalTrackUiState

@Composable
fun ProgressScreen(
    uiState: VitalTrackUiState,
    modifier: Modifier = Modifier
) {
    val logs = uiState.logs.sortedBy { it.date }
    val latest = HealthCalculations.latestLog(logs)
    val weightChange = HealthCalculations.weightChange(logs)

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SectionHeader(
                title = "Progress",
                subtitle = "Trends from saved local health logs."
            )
        }

        if (logs.isEmpty()) {
            item {
                EmptyState(
                    title = "No progress yet",
                    message = "Save a daily log or load demo data to see trend charts.",
                    icon = Icons.AutoMirrored.Outlined.ShowChart
                )
            }
        } else {
            item {
                MetricCardGrid(
                    cards = listOf(
                        MetricCardData(
                            title = "Latest weight",
                            value = "${latest?.weightKg?.formatOneDecimal() ?: "--"} kg",
                            subtitle = "Change ${weightChange?.formatSignedOneDecimal() ?: "0.0"} kg",
                            icon = Icons.Outlined.Scale,
                            accent = Color(0xFF1B8A5A)
                        ),
                        MetricCardData(
                            title = "Avg calories",
                            value = HealthCalculations.averageCalories(logs).toInt().formatWholeNumber(),
                            subtitle = "Across ${logs.size} log(s)",
                            icon = Icons.Outlined.LocalFireDepartment,
                            accent = Color(0xFFE8753C)
                        ),
                        MetricCardData(
                            title = "Avg water",
                            value = "${HealthCalculations.averageWater(logs).formatOneDecimal()} glasses",
                            subtitle = "Daily average",
                            icon = Icons.Outlined.WaterDrop,
                            accent = Color(0xFF007C89)
                        ),
                        MetricCardData(
                            title = "Best steps",
                            value = HealthCalculations.bestStepDay(logs)?.steps?.formatWholeNumber() ?: "--",
                            subtitle = HealthCalculations.bestStepDay(logs)?.date?.let(DateUtils::displayDate) ?: "No step data",
                            icon = Icons.AutoMirrored.Outlined.DirectionsWalk,
                            accent = Color(0xFF2563EB)
                        )
                    )
                )
            }
            item {
                TrendChart(
                    title = "Weight trend",
                    points = logs.toChartPoints { it.weightKg.toFloat() },
                    valueSuffix = " kg",
                    accent = Color(0xFF1B8A5A)
                )
            }
            item {
                TrendChart(
                    title = "Calories trend",
                    points = logs.toChartPoints { it.calories.toFloat() },
                    valueSuffix = "",
                    accent = Color(0xFFE8753C)
                )
            }
            item {
                TrendChart(
                    title = "Water trend",
                    points = logs.toChartPoints { it.waterGlasses.toFloat() },
                    valueSuffix = " glasses",
                    accent = Color(0xFF007C89)
                )
            }
            item {
                TrendChart(
                    title = "Steps trend",
                    points = logs.toChartPoints { it.steps.toFloat() },
                    valueSuffix = "",
                    accent = Color(0xFF2563EB)
                )
            }
        }
    }
}

private fun List<com.example.healthmonitoringapp.data.HealthLog>.toChartPoints(
    selector: (com.example.healthmonitoringapp.data.HealthLog) -> Float
): List<ChartPoint> {
    return map { log ->
        ChartPoint(
            label = DateUtils.displayDate(log.date),
            value = selector(log)
        )
    }
}
