package com.example.location_tracker.domain.usecase

import com.example.location_tracker.data.local.entity.Location
import com.example.location_tracker.data.repository.LocationRepository
import com.example.location_tracker.service.GeofencingService
import javax.inject.Inject

class AddLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    private val geofencingService: GeofencingService
) {
    suspend operator fun invoke(location: Location) {
        locationRepository.addLocation(location)
        geofencingService.addGeofence(location)
    }
}