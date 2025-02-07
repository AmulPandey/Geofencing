package com.example.location_tracker.domain.usecase

import com.example.location_tracker.data.local.entity.Location
import com.example.location_tracker.data.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetLocationByIdUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    operator fun invoke(locationId: Long): Flow<Location?> {
        return locationRepository.getAllLocations().map { locations ->
            locations.find { it.id == locationId }
        }
    }
}
