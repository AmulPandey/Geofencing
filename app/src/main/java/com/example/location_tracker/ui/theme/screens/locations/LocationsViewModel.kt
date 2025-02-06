package com.example.location_tracker.ui.theme.screens.locations

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
    private val locationRepository: LocationRepository,
    private val visitRepository: VisitRepository
) : ViewModel() {

    private val _locationWithVisits = MutableStateFlow<LocationWithVisits?>(null)
    val locationWithVisits: StateFlow<LocationWithVisits?> = _locationWithVisits.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadLocation(locationId: Long) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null


                val location = locationRepository.getLocationById(locationId)
                if (location != null) {
                    visitRepository.getVisitsForLocation(locationId)
                        .collect { visits ->
                            _locationWithVisits.value = LocationWithVisits(
                                location = location,
                                visits = visits
                            )
                        }
                } else {
                    _error.value = "Location not found"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load location details"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteLocation(location: Location) {
        viewModelScope.launch {
            try {
                locationRepository.deleteLocation(location)
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to delete location"
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}