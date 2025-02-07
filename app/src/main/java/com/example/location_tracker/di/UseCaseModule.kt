package com.example.location_tracker.di


import com.example.location_tracker.domain.usecase.GetLocationsUseCase
import com.example.location_tracker.domain.usecase.AddVisitUseCase
import com.example.location_tracker.domain.usecase.GetVisitsUseCase
import com.example.location_tracker.domain.usecase.UpdateVisitUseCase
import com.example.location_tracker.data.repository.LocationRepository
import com.example.location_tracker.data.repository.VisitRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetLocationsUseCase(locationRepository: LocationRepository): GetLocationsUseCase {
        return GetLocationsUseCase(locationRepository)
    }

    @Provides
    @Singleton
    fun provideAddVisitUseCase(visitRepository: VisitRepository): AddVisitUseCase {
        return AddVisitUseCase(visitRepository)
    }

    @Provides
    @Singleton
    fun provideGetVisitsUseCase(visitRepository: VisitRepository): GetVisitsUseCase {
        return GetVisitsUseCase(visitRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateVisitUseCase(visitRepository: VisitRepository): UpdateVisitUseCase {
        return UpdateVisitUseCase(visitRepository)
    }
}
