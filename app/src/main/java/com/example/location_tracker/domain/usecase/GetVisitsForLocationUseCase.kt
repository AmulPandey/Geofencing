package com.example.location_tracker.domain.usecase

import com.example.location_tracker.data.repository.VisitRepository
import javax.inject.Inject

class GetVisitsForLocationUseCase @Inject constructor(
    private val visitRepository: VisitRepository
) {
    operator fun invoke(locationId: Long) = visitRepository.getVisitsForLocation(locationId)
}
