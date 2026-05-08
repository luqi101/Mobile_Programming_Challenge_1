package com.example.healthmonitoringapp.data

data class UserProfile(
    val name: String = "VitalTrack User",
    val heightCm: Double = 170.0,
    val age: Int = 25,
    val gender: String = ""
)
