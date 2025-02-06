package com.example.location_tracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.location_tracker.data.local.dao.LocationDao
import com.example.location_tracker.data.local.dao.VisitDao
import com.example.location_tracker.data.local.entity.Location
import com.example.location_tracker.data.local.entity.Visit

@Database(
    entities = [Location::class, Visit::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
    abstract fun visitDao(): VisitDao
}