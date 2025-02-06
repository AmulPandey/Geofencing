package com.example.location_tracker.data.repository

import com.example.location_tracker.data.local.dao.LocationDao
import com.example.location_tracker.data.local.entity.Location
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val locationDao: LocationDao
) {

    fun getAllLocations() = locationDao.getAllLocations()


    suspend fun addLocation(location: Location) = locationDao.insertLocation(location)


    suspend fun getLocationById(id: Long) = locationDao.getLocationById(id)


    suspend fun deleteLocation(location: Location) = locationDao.deleteLocation(location)


    suspend fun insertStaticLocations() {
        val staticLocations = listOf(
            Location(name = "Home", latitude = 28.584669, longitude = 77.352006, radius = 100f),
            Location(name = "Office", latitude = 28.584669, longitude = 77.352006, radius = 200f)
        )
        staticLocations.forEach { location ->
            locationDao.insertLocation(location)
        }
    }
}
