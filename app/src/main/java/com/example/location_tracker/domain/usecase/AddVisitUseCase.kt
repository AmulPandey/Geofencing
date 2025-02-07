package com.example.location_tracker.domain.usecase

import com.example.location_tracker.data.local.entity.Visit
import com.example.location_tracker.data.repository.VisitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddVisitUseCase @Inject constructor(
    private val visitRepository: VisitRepository
) {
    suspend operator fun invoke(visit: Visit) {
        visitRepository.addVisit(visit)
    }
}
