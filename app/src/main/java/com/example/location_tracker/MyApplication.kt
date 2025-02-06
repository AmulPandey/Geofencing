package com.example.location_tracker

import android.app.Application
import androidx.room.Room
import com.example.location_tracker.data.local.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    lateinit var database: AppDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "geofencing_database"
        ).build()
    }
}