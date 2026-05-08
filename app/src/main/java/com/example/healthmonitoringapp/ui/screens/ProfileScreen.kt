package com.example.healthmonitoringapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.healthmonitoringapp.ui.components.SectionHeader
import com.example.healthmonitoringapp.ui.components.ValidatedTextField
import com.example.healthmonitoringapp.viewmodel.VitalTrackUiState

@Composable
fun ProfileScreen(
    uiState: VitalTrackUiState,
    onNameChange: (String) -> Unit,
    onHeightChange: (String) -> Unit,
    onAgeChange: (String) -> Unit,
    onGenderChange: (String) -> Unit,
    onSaveProfile: () -> Unit,
    onAddDemoData: () -> Unit,
    onRequestResetLogs: () -> Unit,
    onDismissResetLogs: () -> Unit,
    onConfirmResetLogs: () -> Unit,
    modifier: Modifier = Modifier
) {
    val form = uiState.profileForm
    val errors = uiState.profileErrors

    if (uiState.showResetConfirmation) {
        AlertDialog(
            onDismissRequest = onDismissResetLogs,
            title = { Text("Reset health logs?") },
            text = {
                Text("This clears saved daily logs only. Profile and goals stay available.")
            },
            confirmButton = {
                TextButton(onClick = onConfirmResetLogs) {
                    Text("Reset logs")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissResetLogs) {
                    Text("Cancel")
                }
            }
        )
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SectionHeader(
                title = "Profile & About",
                subtitle = "Personal details, demo tools, and challenge information."
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
                    Text(
                        text = "User profile",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    ValidatedTextField(
                        value = form.name,
                        onValueChange = onNameChange,
                        label = "Name",
                        error = errors["name"],
                        modifier = Modifier.fillMaxWidth()
                    )
                    ValidatedTextField(
                        value = form.heightCm,
                        onValueChange = onHeightChange,
                        label = "Height cm",
                        error = errors["heightCm"],
                        keyboardType = KeyboardType.Decimal,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    ValidatedTextField(
                        value = form.age,
                        onValueChange = onAgeChange,
                        label = "Age",
                        error = errors["age"],
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    ValidatedTextField(
                        value = form.gender,
                        onValueChange = onGenderChange,
                        label = "Gender (optional)",
                        error = errors["gender"],
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(
                        onClick = onSaveProfile,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Save profile",
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
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
                    Text(
                        text = "Demo controls",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Use demo data to prepare screenshots and a reliable class presentation.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = onAddDemoData,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Load demo")
                        }
                        OutlinedButton(
                            onClick = onRequestResetLogs,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Reset logs")
                        }
                    }
                }
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
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "About VitalTrack",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "VitalTrack is a local-first health monitoring app for CS5450 Mobile Programming Challenge 1.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Group #1: Health Monitoring App. Built with native Android, Kotlin, Jetpack Compose, Material 3, ViewModel state, and SharedPreferences persistence.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
