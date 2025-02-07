package com.example.location_tracker.domain.usecase

import com.example.location_tracker.data.local.entity.Visit
import com.example.location_tracker.data.repository.VisitRepository
import dagger.Module
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVisitsUseCase @Inject constructor(
    private val visitRepository: VisitRepository
) {
    operator fun invoke(): Flow<List<Visit>> = visitRepository.getAllVisits()
}