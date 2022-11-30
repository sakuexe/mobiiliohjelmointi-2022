package com.example.webscrapetesting

data class Feature(
    val geometry: Geometry,
    val locode: String,
    val properties: Properties,
    val type: String
)