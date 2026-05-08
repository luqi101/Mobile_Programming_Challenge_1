package com.example.healthmonitoringapp.data

import com.example.healthmonitoringapp.util.DateUtils

object DemoDataFactory {
    fun logs(): List<HealthLog> {
        val weights = listOf(65.8, 65.7, 65.6, 65.5, 65.4, 65.4, 65.3)
        val calories = listOf(2380, 2420, 2350, 2450, 2400, 2320, 2360)
        val water = listOf(9, 10, 9, 10, 11, 10, 10)
        val steps = listOf(11200, 12600, 11850, 13400, 12100, 14800, 13050)
        val sleep = listOf(7.6, 7.8, 8.0, 7.5, 8.2, 7.9, 8.1)
        val heartRates = listOf(60, 59, 58, 60, 57, 56, 58)
        val notes = listOf(
            "Morning run and balanced meals.",
            "Strength training after class.",
            "Good hydration and steady energy.",
            "Long walk plus mobility work.",
            "Rest day with light activity.",
            "High step day and solid sleep.",
            "Healthy routine stayed consistent."
        )

        return weights.indices.map { index ->
            val daysAgo = weights.lastIndex - index
            val date = DateUtils.daysAgo(daysAgo)
            HealthLog(
                id = DateUtils.stableIdForDate(date),
                date = date,
                weightKg = weights[index],
                calories = calories[index],
                waterGlasses = water[index],
                steps = steps[index],
                sleepHours = sleep[index],
                heartRate = heartRates[index],
                moodNote = notes[index]
            )
        }
    }

    fun goals(): UserGoals = UserGoals(
        targetWeightKg = 65.0,
        dailyCalories = 2400,
        dailyWaterGlasses = 10,
        dailySteps = 12000,
        sleepHours = 8.0
    )

    fun profile(): UserProfile = UserProfile(
        name = "Luqman Aadil",
        heightCm = 170.0,
        age = 23,
        gender = ""
    )
}
