package com.example.healthmonitoringapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.healthmonitoringapp.ui.components.EmptyState
import com.example.healthmonitoringapp.ui.components.SectionHeader
import com.example.healthmonitoringapp.ui.components.ValidatedTextField
import com.example.healthmonitoringapp.viewmodel.VitalTrackUiState

@Composable
fun DailyLogScreen(
    uiState: VitalTrackUiState,
    onDateChange: (String) -> Unit,
    onWeightChange: (String) -> Unit,
    onCaloriesChange: (String) -> Unit,
    onWaterChange: (String) -> Unit,
    onStepsChange: (String) -> Unit,
    onSleepChange: (String) -> Unit,
    onHeartRateChange: (String) -> Unit,
    onMoodChange: (String) -> Unit,
    onSave: () -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    val form = uiState.dailyForm
    val errors = uiState.dailyErrors

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SectionHeader(
                title = "Daily Log",
                subtitle = "Add or update one health entry for a date."
            )
        }
        if (uiState.logs.isEmpty()) {
            item {
                EmptyState(
                    title = "Ready for your first entry",
                    message = "Use realistic values. Invalid numbers will be shown clearly before saving.",
                    icon = Icons.Outlined.EditNote
                )
            }
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
                        value = form.date,
                        onValueChange = onDateChange,
                        label = "Date (YYYY-MM-DD)",
                        error = errors["date"],
                        modifier = Modifier.fillMaxWidth()
                    )
                    ValidatedTextField(
                        value = form.weightKg,
                        onValueChange = onWeightChange,
                        label = "Weight kg",
                        error = errors["weightKg"],
                        keyboardType = KeyboardType.Decimal,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    ValidatedTextField(
                        value = form.calories,
                        onValueChange = onCaloriesChange,
                        label = "Calories",
                        error = errors["calories"],
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    ValidatedTextField(
                        value = form.waterGlasses,
                        onValueChange = onWaterChange,
                        label = "Water",
                        error = errors["waterGlasses"],
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    ValidatedTextField(
                        value = form.steps,
                        onValueChange = onStepsChange,
                        label = "Steps",
                        error = errors["steps"],
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    ValidatedTextField(
                        value = form.sleepHours,
                        onValueChange = onSleepChange,
                        label = "Sleep hours",
                        error = errors["sleepHours"],
                        keyboardType = KeyboardType.Decimal,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    ValidatedTextField(
                        value = form.heartRate,
                        onValueChange = onHeartRateChange,
                        label = "Resting bpm",
                        error = errors["heartRate"],
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    ValidatedTextField(
                        value = form.moodNote,
                        onValueChange = onMoodChange,
                        label = "Mood or note",
                        error = errors["moodNote"],
                        singleLine = false,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onClear,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Clear form")
                }
                Button(
                    onClick = onSave,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Save log",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
