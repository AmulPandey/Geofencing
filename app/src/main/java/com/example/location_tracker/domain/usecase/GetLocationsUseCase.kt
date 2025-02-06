package com.example.location_tracker.domain.usecase

import com.example.location_tracker.data.local.entity.Location
import com.example.location_tracker.data.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocationsUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    operator fun invoke(): Flow<List<Location>> = locationRepository.getAllLocations()
}