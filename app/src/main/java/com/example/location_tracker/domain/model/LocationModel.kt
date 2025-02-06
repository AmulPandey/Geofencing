package com.example.location_tracker.domain.model

data class LocationModel(
    val id: Long = 0,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val radius: Float,
    val createdAt: Long = System.currentTimeMillis()
)
