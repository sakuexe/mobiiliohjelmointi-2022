package com.example.webscrapetesting

data class TrainLocationItem(
    val departureDate: String,
    val location: Location,
    val speed: Int,
    val timestamp: String,
    val trainNumber: Int
)