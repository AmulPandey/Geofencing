package com.example.location_tracker.domain.model

data class VisitModel(
    val id: Long = 0,
    val locationId: Long,
    val locationName: String,
    val entryTime: Long,
    val exitTime: Long? = null,
    val duration: Long? = null
)