package com.example.healthmonitoringapp.data

data class UserGoals(
    val targetWeightKg: Double = 70.0,
    val dailyCalories: Int = 2200,
    val dailyWaterGlasses: Int = 8,
    val dailySteps: Int = 10000,
    val sleepHours: Double = 8.0
)
