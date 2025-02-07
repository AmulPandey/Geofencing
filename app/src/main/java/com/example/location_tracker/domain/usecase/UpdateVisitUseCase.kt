package com.example.location_tracker.domain.usecase


import com.example.location_tracker.data.local.entity.Visit
import com.example.location_tracker.data.repository.VisitRepository
import javax.inject.Inject

class UpdateVisitUseCase @Inject constructor(
    private val visitRepository: VisitRepository
) {
    suspend operator fun invoke(visit: Visit) {
        visitRepository.updateVisit(visit)
    }
}
