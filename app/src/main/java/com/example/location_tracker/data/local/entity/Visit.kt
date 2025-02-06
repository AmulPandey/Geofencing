package com.example.location_tracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "visits")
data class Visit(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val locationId: Long,
    val locationName: String,
    val entryTime: Long,
    val exitTime: Long? = null,
    val duration: Long? = null
)