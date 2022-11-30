package com.example.webscrapetesting

data class Properties(
    val locode: String,
    val name: String,
    val nationality: String,
    val portRestrictions: List<Any>,
    val seaArea: String
)