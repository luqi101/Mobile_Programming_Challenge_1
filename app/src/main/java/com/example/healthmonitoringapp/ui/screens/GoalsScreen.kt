package com.example.healthmonitoringapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.healthmonitoringapp.ui.components.EmptyState
import com.example.healthmonitoringapp.ui.components.ProgressRingCard
import com.example.healthmonitoringapp.ui.components.SectionHeader
import com.example.healthmonitoringapp.ui.components.ValidatedTextField
import com.example.healthmonitoringapp.util.HealthCalculations
import com.example.healthmonitoringapp.util.formatOneDecimal
import com.example.healthmonitoringapp.util.formatWholeNumber
import com.example.healthmonitoringapp.viewmodel.VitalTrackUiState

@Composable
fun GoalsScreen(
    uiState: VitalTrackUiState,
    onTargetWeightChange: (String) -> Unit,
    onCaloriesChange: (String) -> Unit,
    onWaterChange: (String) -> Unit,
    onStepsChange: (String) -> Unit,
    onSleepChange: (String) -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    val form = uiState.goalForm
    val errors = uiState.goalErrors
    val latest = HealthCalculations.latestLog(uiState.logs)

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SectionHeader(
                title = "Goals",
                subtitle = "Set targets used by dashboard and insights."
            )
        }
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ValidatedTextField(
                        value = form.targetWeightKg,
                        onValueChange = onTargetWeightChange,
                        label = "Target weight kg",
                        error = errors["targetWeightKg"],
                        keyboardType = KeyboardType.Decimal,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    ValidatedTextField(
                        value = form.dailyCalories,
                        onValueChange = onCaloriesChange,
                        label = "Calories",
                        error = errors["dailyCalories"],
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    ValidatedTextField(
                        value = form.dailyWaterGlasses,
                        onValueChange = onWaterChange,
                        label = "Water glasses",
                        error = errors["dailyWaterGlasses"],
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    ValidatedTextField(
                        value = form.dailySteps,
                        onValueChange = onStepsChange,
                        label = "Steps",
                        error = errors["dailySteps"],
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    ValidatedTextField(
                        value = form.sleepHours,
                        onValueChange = onSleepChange,
                        label = "Sleep hours",
                        error = errors["sleepHours"],
                        keyboardType = KeyboardType.Decimal,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(
                        onClick = onSave,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Save goals",
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        if (latest == null) {
            item {
                EmptyState(
                    title = "Goal progress needs logs",
                    message = "Save a daily log to compare current values against goals."
                )
            }
        } else {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ProgressRingCard(
                        title = "Calories",
                        percentLabel = HealthCalculations.progressPercentLabel(
                            latest.calories.toDouble(),
                            uiState.goals.dailyCalories.toDouble()
                        ),
                        detail = "${latest.calories.formatWholeNumber()} / ${uiState.goals.dailyCalories.formatWholeNumber()}",
                        progress = HealthCalculations.progressFraction(
                            latest.calories.toDouble(),
                            uiState.goals.dailyCalories.toDouble()
                        ),
                        accent = Color(0xFFE8753C),
                        modifier = Modifier.weight(1f)
                    )
                    ProgressRingCard(
                        title = "Water",
                        percentLabel = HealthCalculations.progressPercentLabel(
                            latest.waterGlasses.toDouble(),
                            uiState.goals.dailyWaterGlasses.toDouble()
                        ),
                        detail = "${latest.waterGlasses} / ${uiState.goals.dailyWaterGlasses} glasses",
                        progress = HealthCalculations.progressFraction(
                            latest.waterGlasses.toDouble(),
                            uiState.goals.dailyWaterGlasses.toDouble()
                        ),
                        accent = Color(0xFF007C89),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ProgressRingCard(
                        title = "Steps",
                        percentLabel = HealthCalculations.progressPercentLabel(
                            latest.steps.toDouble(),
                            uiState.goals.dailySteps.toDouble()
                        ),
                        detail = "${latest.steps.formatWholeNumber()} / ${uiState.goals.dailySteps.formatWholeNumber()}",
                        progress = HealthCalculations.progressFraction(
                            latest.steps.toDouble(),
                            uiState.goals.dailySteps.toDouble()
                        ),
                        accent = Color(0xFF2563EB),
                        modifier = Modifier.weight(1f)
                    )
                    ProgressRingCard(
                        title = "Sleep",
                        percentLabel = HealthCalculations.progressPercentLabel(
                            latest.sleepHours,
                            uiState.goals.sleepHours
                        ),
                        detail = "${latest.sleepHours.formatOneDecimal()} / ${uiState.goals.sleepHours.formatOneDecimal()} hours",
                        progress = HealthCalculations.progressFraction(
                            latest.sleepHours,
                            uiState.goals.sleepHours
                        ),
                        accent = Color(0xFF4F46E5),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
