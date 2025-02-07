package com.example.location_tracker.data.repository

import com.example.location_tracker.data.local.dao.VisitDao
import com.example.location_tracker.data.local.entity.Visit

import javax.inject.Inject

class VisitRepository @Inject constructor(
    private val visitDao: VisitDao,
    private val locationRepository: LocationRepository
) {

    fun getAllVisits() = visitDao.getAllVisits()

    suspend fun addVisit(visit: Visit) = visitDao.insertVisit(visit)

    suspend fun updateVisit(visit: Visit) = visitDao.updateVisit(visit)

    suspend fun getActiveVisits() = visitDao.getActiveVisits()

    fun getVisitsForLocation(locationId: Long) = visitDao.getVisitsForLocation(locationId)

    suspend fun addVisitForStaticLocation(latitude: Double, longitude: Double) {
        val locations = locationRepository.getAllLocations()
        locations.collect { locationList ->
            locationList.forEach { location ->
                val distance = calculateDistance(latitude, longitude, location.latitude, location.longitude)
                if (distance <= location.radius) {
                    val visit = Visit(
                        locationId = location.id,
                        locationName = location.name,
                        entryTime = System.currentTimeMillis()
                    )
                    visitDao.insertVisit(visit)
                }
            }
        }
    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Float {
        val results = FloatArray(1)
        android.location.Location.distanceBetween(lat1, lon1, lat2, lon2, results)
        return results[0]
    }
}
