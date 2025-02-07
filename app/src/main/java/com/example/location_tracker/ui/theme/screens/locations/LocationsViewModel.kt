package com.example.location_tracker.ui.theme.screens.locations

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.location_tracker.data.local.entity.Location
import com.example.location_tracker.data.local.entity.Visit
import com.example.location_tracker.data.repository.LocationRepository
import com.example.location_tracker.data.repository.VisitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LocationWithVisits(
    val location: Location,
    val visits: List<Visit>
)

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : ViewModel() {
    val locations: Flow<List<Location>> = locationRepository.getAllLocations()
}