package com.example.healthmonitoringapp.data

data class HealthLog(
    val id: Long,
    val date: String,
    val weightKg: Double,
    val calories: Int,
    val waterGlasses: Int,
    val steps: Int,
    val sleepHours: Double,
    val heartRate: Int,
    val moodNote: String
)
