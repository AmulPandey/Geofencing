package com.example.location_tracker.data.repository

import android.util.Log
import com.example.location_tracker.data.local.dao.LocationDao
import com.example.location_tracker.data.local.entity.Location
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val locationDao: LocationDao
) {

    fun getAllLocations() = flow {
        val locations = locationDao.getAllLocations().first()
        Log.d("LocationRepository", "Fetched locations: ${locations.size}")
        if (locations.isEmpty()) {
            initializeLocationsIfEmpty()
            emit(locationDao.getAllLocations().first())
        } else {
            emit(locations)
        }
    }


    suspend fun addLocation(location: Location) = locationDao.insertLocation(location)


    suspend fun getLocationById(id: Long) = locationDao.getLocationById(id)


    suspend fun deleteLocation(location: Location) = locationDao.deleteLocation(location)


    suspend fun initializeLocationsIfEmpty() {
        val existingLocations = locationDao.getAllLocations().first()
        Log.d("LocationRepository", "Existing locations count: ${existingLocations.size}")

        if (existingLocations.isEmpty()) {
            val staticLocations = listOf(
                Location(
                    id = 1,
                    name = "Home",
                    latitude = 28.6138,
                    longitude = 77.3732,
                    radius = 500f,
                    createdAt = System.currentTimeMillis()
                ),
                Location(
                    id = 2,
                    name = "Office",
                    latitude = 28.6140,
                    longitude = 77.3735,
                    radius = 300f,
                    createdAt = System.currentTimeMillis()
                )
            )
            staticLocations.forEach { location ->
                locationDao.insertLocation(location)
                Log.d("LocationRepository", "Inserted location: ${location.name}")
            }
        }
    }
}
